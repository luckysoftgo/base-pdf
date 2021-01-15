package com.application.base.test.docx4j;

import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * @author ：admin
 * @date ：2021-1-14
 * @description: https://blog.csdn.net/qq_35598240/article/details/84439929
 * @modified By：
 * @version: 1.0.0
 */
public class Docx4jUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(Docx4jUtils.class);
	
	public static void main(String[] args) throws FileNotFoundException {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test15.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp15.docx";
		Map<String, String> uniqueDataMap = new LinkedHashMap<>();
		uniqueDataMap.put("title", "长安信托");
		uniqueDataMap.put("cust-name", "张三");
		uniqueDataMap.put("cust-cert-name", "身份证");
		uniqueDataMap.put("org-name", "西部证券");
		uniqueDataMap.put("cust-cert-no", "1101011990d3d75752");
		uniqueDataMap.put("cust-cert-addr", "北京市朝阳区");
		uniqueDataMap.put("cust-post-code", "100816");
		uniqueDataMap.put("cust-phone", "18888888888");
		uniqueDataMap.put("cust-email", "zhangsan@163.com");
		uniqueDataMap.put("代理人姓名", "李四");
		uniqueDataMap.put("代理人性别", "女");
		uniqueDataMap.put("代理人证件名称", "身份证");
		uniqueDataMap.put("代理人证件号码", "1101011990d3d75763");
		uniqueDataMap.put("代理人联系电话", "18888888880");
		uniqueDataMap.put("代理人通讯地址", "北京市海淀区");
		uniqueDataMap.put("代理人与委托人关系", "客户");
		
		uniqueDataMap.put("benefit-level", "5");
		uniqueDataMap.put("benefit-percent", "50%");
		uniqueDataMap.put("benefit-date", "2020-2050");
		uniqueDataMap.put("bendfit-unit", "年");
		uniqueDataMap.put("benefit-allname", "收益权收益");
		uniqueDataMap.put("benefit-level-date", "2020-01-20");
		uniqueDataMap.put("constract-amount", "20000000");
		uniqueDataMap.put("china-amount", "贰千万");
		uniqueDataMap.put("trans-amount", "10000");
		uniqueDataMap.put("trans-money", "3000");
		uniqueDataMap.put("constract-no", "2020-11990d3d75");
		
		uniqueDataMap.put("product-name", "家族信托");
		uniqueDataMap.put("amount", "1000");
		uniqueDataMap.put("constract-date", "2019-10-28");
		
		uniqueDataMap.put("account-name", "孤狼1号");
		uniqueDataMap.put("bank-no", "6214888167891");
		uniqueDataMap.put("open-bank", "陕西招商银行");
		uniqueDataMap.put("mark-date", "2020-10-10");
		replaceDocUseDoc4j(new FileInputStream(filepath), uniqueDataMap, new FileOutputStream(tofilepath));
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	
	/**
	 * 替换变量并下载word文档
	 *
	 * @param inputStream
	 * @param map
	 * @param response
	 * @param fileName
	 */
	public static void downloadDocUseDoc4j(InputStream inputStream, Map<String, String> map,
	                                       HttpServletResponse response, String fileName) {
		try {
			// 设置响应头
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".docx");
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
			OutputStream outs = response.getOutputStream();
			replaceDocUseDoc4j(inputStream, map, outs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 替换变量并输出word文档
	 *
	 * @param inputStream
	 * @param map
	 * @param outputStream
	 */
	public static void replaceDocUseDoc4j(InputStream inputStream, Map<String, String> map,
	                                      OutputStream outputStream) {
		try {
			WordprocessingMLPackage doc = WordprocessingMLPackage.load(inputStream);
			MainDocumentPart mainDocumentPart = doc.getMainDocumentPart();
			if (null != map && !map.isEmpty()) {
				// 将${}里的内容结构层次替换为一层
				cleanDocumentPart(mainDocumentPart);
				// 替换文本内容
				mainDocumentPart.variableReplace(map);
			}
			// 输出word文件
			doc.save(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * cleanDocumentPart
	 *
	 * @param documentPart
	 */
	public static boolean cleanDocumentPart(MainDocumentPart documentPart) throws Exception {
		if (documentPart == null) {
			return false;
		}
		Document document = documentPart.getContents();
		String wmlTemplate = XmlUtils.marshaltoString(document, true, false, Context.jc);
		document = (Document) XmlUtils.unwrap(DocxVariableClearUtils.doCleanDocumentPart(wmlTemplate, Context.jc));
		documentPart.setContents(document);
		return true;
	}
	
	/**
	 * 清扫 docx4j 模板变量字符,通常以${variable}形式
	 * <p>
	 * XXX: 主要在上传模板时处理一下, 后续
	 *
	 * @author liliang
	 * @since 2018-11-07
	 */
	private static class DocxVariableClearUtils {
		
		
		/**
		 * 去任意XML标签
		 */
		private static final Pattern XML_PATTERN = Pattern.compile("<[^>]*>");
		
		private DocxVariableClearUtils() {
		}
		
		/**
		 * start符号
		 */
		private static final char PREFIX = '$';
		
		/**
		 * 中包含
		 */
		private static final char LEFT_BRACE = '{';
		
		/**
		 * 结尾
		 */
		private static final char RIGHT_BRACE = '}';
		
		/**
		 * 未开始
		 */
		private static final int NONE_START = -1;
		
		/**
		 * 未开始
		 */
		private static final int NONE_START_INDEX = -1;
		
		/**
		 * 开始
		 */
		private static final int PREFIX_STATUS = 1;
		
		/**
		 * 左括号
		 */
		private static final int LEFT_BRACE_STATUS = 2;
		
		/**
		 * 右括号
		 */
		private static final int RIGHT_BRACE_STATUS = 3;
		
		
		/**
		 * doCleanDocumentPart
		 *
		 * @param wmlTemplate
		 * @param jc
		 * @return
		 * @throws JAXBException
		 */
		private static Object doCleanDocumentPart(String wmlTemplate, JAXBContext jc) throws JAXBException {
			// 进入变量块位置
			int curStatus = NONE_START;
			// 开始位置
			int keyStartIndex = NONE_START_INDEX;
			// 当前位置
			int curIndex = 0;
			char[] textCharacters = wmlTemplate.toCharArray();
			StringBuilder documentBuilder = new StringBuilder(textCharacters.length);
			documentBuilder.append(textCharacters);
			// 新文档
			StringBuilder newDocumentBuilder = new StringBuilder(textCharacters.length);
			// 最后一次写位置
			int lastWriteIndex = 0;
			for (char c : textCharacters) {
				switch (c) {
					case PREFIX:
						// TODO 不管其何状态直接修改指针,这也意味着变量名称里面不能有PREFIX
						keyStartIndex = curIndex;
						curStatus = PREFIX_STATUS;
						break;
					case LEFT_BRACE:
						if (curStatus == PREFIX_STATUS) {
							curStatus = LEFT_BRACE_STATUS;
						}
						break;
					case RIGHT_BRACE:
						if (curStatus == LEFT_BRACE_STATUS) {
							// 接上之前的字符
							newDocumentBuilder.append(documentBuilder.substring(lastWriteIndex, keyStartIndex));
							// 结束位置
							int keyEndIndex = curIndex + 1;
							// 替换
							String rawKey = documentBuilder.substring(keyStartIndex, keyEndIndex);
							// 干掉多余标签
							String mappingKey = XML_PATTERN.matcher(rawKey).replaceAll("");
							if (!mappingKey.equals(rawKey)) {
								char[] rawKeyChars = rawKey.toCharArray();
								// 保留原格式
								StringBuilder rawStringBuilder = new StringBuilder(rawKey.length());
								// 去掉变量引用字符
								for (char rawChar : rawKeyChars) {
									if (rawChar == PREFIX || rawChar == LEFT_BRACE || rawChar == RIGHT_BRACE) {
										continue;
									}
									rawStringBuilder.append(rawChar);
								}
								// FIXME 要求变量连在一起
								String variable = mappingKey.substring(2, mappingKey.length() - 1);
								int variableStart = rawStringBuilder.indexOf(variable);
								if (variableStart > 0) {
									rawStringBuilder = rawStringBuilder.replace(variableStart, variableStart + variable.length(), mappingKey);
								}
								newDocumentBuilder.append(rawStringBuilder.toString());
							} else {
								newDocumentBuilder.append(mappingKey);
							}
							lastWriteIndex = keyEndIndex;
							curStatus = NONE_START;
							keyStartIndex = NONE_START_INDEX;
						}
					default:
						break;
				}
				curIndex++;
			}
			// 余部
			if (lastWriteIndex < documentBuilder.length()) {
				newDocumentBuilder.append(documentBuilder.substring(lastWriteIndex));
			}
			return XmlUtils.unmarshalString(newDocumentBuilder.toString(), jc);
		}
	}
}
