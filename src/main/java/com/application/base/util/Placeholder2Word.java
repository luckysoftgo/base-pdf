package com.application.base.util;

import cn.hutool.core.util.ObjectUtil;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：admin
 * @description: 替换Word中的占位符
 * @modified By：孤狼
 * @version: 1.0.0
 */
public class Placeholder2Word {
	
	/**
	 * 测试效果 ！
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String filepath = "E:\\home\\pdf\\resources\\data\\test.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\test1.docx";
		Map<String, String> dataMap = new LinkedHashMap<>();
		dataMap.put("${name}", "张三");
		dataMap.put("${password}", "123456");
		dataMap.put("${address}", "西安高新国际");
		boolean result = build2Word(filepath, dataMap, tofilepath);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
	}
	
	/**
	 * 设置最大Text类型节点个数 如果超过此值，在删除占位符时可能会重复计算导致错误
	 */
	private static int MAX_TEXT_SIZE = 1000000;
	
	/**
	 * 生成 word文档
	 *
	 * @param docxOrginFile
	 * @param dataMap
	 * @param docxNewFile
	 * @return
	 */
	public static boolean build2Word(String docxOrginFile, Map<String, String> dataMap, String docxNewFile) {
		try {
			WordprocessingMLPackage template = WordprocessingMLPackage.load(new File(docxOrginFile));
			List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
			searchAndReplace(texts, dataMap);
			template.save(new File(docxNewFile));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 递归获取所有的节点
	 *
	 * @param obj      当前文档
	 * @param toSearch 要查询的节点类型
	 * @return java.util.List<java.lang.Object>
	 */
	private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
		List<Object> result = new ArrayList<>();
		if (obj instanceof JAXBElement) {
			obj = ((JAXBElement<?>) obj).getValue();
		}
		if (obj.getClass().equals(toSearch)) {
			result.add(obj);
		} else if (obj instanceof ContentAccessor) {
			List<?> children = ((ContentAccessor) obj).getContent();
			for (Object child : children) {
				result.addAll(getAllElementFromObject(child, toSearch));
			}
		}
		return result;
	}
	
	/**
	 * 查找并且替换占位符
	 *
	 * @param texts  当前文档所有的Text类型节点
	 * @param values 要替换的占位符key\value
	 * @return void
	 */
	public static void searchAndReplace(List<Object> texts, Map<String, String> values) {
		// 存储占位符 位置信息集合
		List<int[]> placeholderList = getPlaceholderList(texts, values);
		if (ObjectUtil.isEmpty(placeholderList)) {
			return;
		}
		int[] currentPlaceholder;
		// 删除元素占位符
		for (int i = 0; i < texts.size(); i++) {
			if (ObjectUtil.isEmpty(placeholderList)) {
				break;
			}
			currentPlaceholder = placeholderList.get(0);
			Text textElement = (Text) texts.get(i);
			String v = textElement.getValue();
			StringBuilder nval = new StringBuilder();
			char[] textChars = v.toCharArray();
			for (int j = 0; j < textChars.length; j++) {
				char c = textChars[j];
				if (null == currentPlaceholder) {
					nval.append(c);
					continue;
				}
				// 计算是否需要排除当前节点
				int start = currentPlaceholder[0] * MAX_TEXT_SIZE + currentPlaceholder[1];
				int end = currentPlaceholder[2] * MAX_TEXT_SIZE + currentPlaceholder[3];
				int cur = i * MAX_TEXT_SIZE + j;
				// 排除'$'和'}'两个字符之间的字符
				if (!(cur >= start && cur <= end)) {
					nval.append(c);
				}
				
				if (j > currentPlaceholder[3] && i >= currentPlaceholder[2]) {
					placeholderList.remove(0);
					if (ObjectUtil.isEmpty(placeholderList)) {
						currentPlaceholder = null;
						continue;
					}
					currentPlaceholder = placeholderList.get(0);
				}
			}
			textElement.setValue(nval.toString());
		}
	}
	
	/**
	 * 获取占位符信息，并且在占位符后面填充值
	 *
	 * @param texts  Text类型节点集合
	 * @param values 要替换的占位符key\value
	 * @return java.util.List<int [ ]>
	 */
	public static List<int[]> getPlaceholderList(List<Object> texts, Map<String, String> values) {
		// 标识忽略
		int ignoreTg = 0;
		// 标识已读取到'$'字符
		int startTg = 1;
		// 标识已读取到'{'字符
		int readTg = 2;
		// 当前标识
		int modeTg = ignoreTg;
		
		// 存储占位符 位置信息集合
		List<int[]> placeholderList = new ArrayList<>();
		// 当前占位符 0：'$'字符Text在texts中下标
		//           1：'$'字符在Text.getValue().toCharArray()数组下标
		//           2: '}'字符Text在texts中下标
		//           3：'}'字符在Text.getValue().toCharArray()数组下标
		int[] currentPlaceholder = new int[4];
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < texts.size(); i++) {
			Text textElement = (Text) texts.get(i);
			StringBuilder newVal = new StringBuilder();
			String text = textElement.getValue();
			StringBuilder textSofar = new StringBuilder();
			char[] textChars = text.toCharArray();
			for (int col = 0; col < textChars.length; col++) {
				char c = textChars[col];
				textSofar.append(c);
				switch (c) {
					case '$': {
						modeTg = startTg;
						sb.append(c);
					}
					break;
					case '{': {
						if (modeTg == startTg) {
							sb.append(c);
							modeTg = readTg;
							currentPlaceholder[0] = i;
							currentPlaceholder[1] = col - 1;
						} else {
							if (modeTg == readTg) {
								sb = new StringBuilder();
								modeTg = ignoreTg;
							}
						}
					}
					break;
					case '}': {
						if (modeTg == readTg) {
							modeTg = ignoreTg;
							sb.append(c);
							newVal.append(textSofar.toString());
							newVal.append(null == values.get(sb.toString()) ? sb.toString() : values.get(sb.toString()));
							textSofar = new StringBuilder();
							currentPlaceholder[2] = i;
							currentPlaceholder[3] = col;
							placeholderList.add(currentPlaceholder);
							currentPlaceholder = new int[4];
							sb = new StringBuilder();
						} else if (modeTg == startTg) {
							modeTg = ignoreTg;
							sb = new StringBuilder();
						}
					}
					default: {
						if (modeTg == readTg) {
							sb.append(c);
						} else if (modeTg == startTg) {
							modeTg = ignoreTg;
							sb = new StringBuilder();
						}
					}
				}
			}
			newVal.append(textSofar.toString());
			textElement.setValue(newVal.toString());
		}
		return placeholderList;
	}
}
