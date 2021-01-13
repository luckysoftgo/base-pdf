package com.application.base.docx4j;

import cn.hutool.core.util.ObjectUtil;
import com.application.base.docx4j.vo.DocxDataVO;
import com.application.base.docx4j.vo.DocxImageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.Docx4J;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.finders.ClassFinder;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.org.apache.poi.util.IOUtils;
import org.docx4j.wml.*;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author ：孤狼
 * https://www.docx4java.org/trac/docx4j
 * https://github.com/plutext/docx4j
 * https://blog.csdn.net/qq_38542834/article/details/84303174
 * @description: doc4j处理word问题.
 * @modified By：
 * @version: 1.0.0
 */
@Slf4j
@Component
public class Placeholder2WordClient {
	
	/**
	 * docx 文档转换成 doc 文档.
	 *
	 * @param sourcePath
	 * @param targetPath
	 * @throws Exception
	 */
	public void convertDocx2Doc(String sourcePath, String targetPath) throws Exception {
		if (StringUtils.isBlank(sourcePath) || StringUtils.isBlank(targetPath)) {
			throw new Exception("传入的文件路径为空!");
		}
		//将docx转换为符合doc格式规范的xml文档，再由xml更改后缀名为doc的方式达到docx转换doc格式的目的
		File templateFile = new File(sourcePath);
		int len = sourcePath.lastIndexOf("\\/") + 1;
		String name = sourcePath.substring(len, sourcePath.length()).split("\\.")[0];
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateFile);
		File outFile = new File(templateFile.getParent() + "/out/", "tmp_" + name + ".xml");
		Docx4J.save(wordMLPackage, outFile, Docx4J.FLAG_SAVE_FLAT_XML);
		outFile.renameTo(new File(targetPath));
		wordMLPackage.reset();
	}
	
	/**
	 * Word 转换成 html
	 *
	 * @param sourcePath
	 * @throws Exception
	 */
	public void convertDocx2Html(String sourcePath) throws Exception {
		if (StringUtils.isBlank(sourcePath)) {
			throw new Exception("传入的文件路径为空!");
		}
		File templateFile = new File(sourcePath);
		int len = sourcePath.lastIndexOf("\\/") + 1;
		String name = sourcePath.substring(len, sourcePath.length()).split("\\.")[0];
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateFile);
		HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
		String folder = templateFile.getParent() + "/out/";
		//生成的html文件与图片文件夹之类的放置同一个目录下
		htmlSettings.setImageDirPath(folder + name + "_files");
		htmlSettings.setImageTargetUri(name + "_files");
		htmlSettings.setWmlPackage(wordMLPackage);
		File outFile = new File(folder, name);
		Docx4J.toHTML(htmlSettings, new FileOutputStream(outFile), Docx4J.FLAG_NONE);
		wordMLPackage.reset();
	}
	
	/**
	 * Word转换成 Pdf
	 *
	 * @param sourcePath:绝对路径
	 * @param targetPath:绝对路径.
	 * @throws Exception
	 */
	public void convertDocx2Pdf(String sourcePath, String targetPath) throws Exception {
		if (StringUtils.isBlank(sourcePath) || StringUtils.isBlank(targetPath)) {
			throw new Exception("传入的文件路径为空!");
		}
		File templateFile = new File(sourcePath);
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateFile);
		File outFile = new File(targetPath);
		Docx4J.toPDF(wordMLPackage, new FileOutputStream(outFile));
		wordMLPackage.reset();
	}
	
	/**
	 * 生成 word文档
	 * 没有表格的情况下使用这种方式.
	 *
	 * @param docxOrginFile
	 * @param dataMap
	 * @param docxNewFile
	 * @return
	 */
	public boolean convert2Word(String docxOrginFile, Map<String, String> dataMap, String docxNewFile) throws Exception {
		if (StringUtils.isBlank(docxOrginFile) || StringUtils.isBlank(docxNewFile)) {
			throw new Exception("传入的文件路径为空!");
		}
		if (null == dataMap || dataMap.isEmpty()) {
			throw new Exception("传入的数据为空!");
		}
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(docxOrginFile));
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		documentPart.variableReplace(dataMap);
		Docx4J.save(wordMLPackage, new File(docxNewFile));
		//释放资源
		wordMLPackage.reset();
		return true;
	}
	
	
	/**
	 * 生成 word文档
	 * 有一个表格的情况
	 *
	 * @param docxOrginFile
	 * @param unqiueDataMap    ：已经存在的全局的 key - value 集合.
	 * @param linkedList       :单个表单情况下的列表的数据
	 * @param tableIndex       : word 中表格的下标,从0开始: 0,1,2,3,4,5
	 * @param replaceRowIndex: word 中要替换的表格的下标,从0开始: 0,1,2,3,4,5
	 * @return
	 */
	public boolean convert2TableWord(String docxOrginFile, Map<String, String> unqiueDataMap, String docxNewFile, ArrayList<Map<String, Object>> linkedList, Integer tableIndex, Integer replaceRowIndex) throws Exception {
		if (StringUtils.isBlank(docxOrginFile) || StringUtils.isBlank(docxNewFile)) {
			throw new Exception("传入的文件路径为空!");
		}
		if (null == unqiueDataMap || unqiueDataMap.isEmpty() || null == linkedList || linkedList.isEmpty()) {
			throw new Exception("传入的数据为空!");
		}
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(docxOrginFile));
		ClassFinder find = new ClassFinder(Tbl.class);
		new TraversalUtil(wordMLPackage.getMainDocumentPart().getContent(), find);
		int index = tableIndex == null ? 0 : tableIndex.intValue();
		//第几个表.
		Tbl table = (Tbl) find.results.get(index);
		//开始要替换的行
		int tableRowIndex = replaceRowIndex == null ? 1 : replaceRowIndex;
		//第二行约定为模板${}
		Tr dynamicTr = (Tr) table.getContent().get(tableRowIndex);
		//获取模板行的xml数据
		String dynamicTrXml = XmlUtils.marshaltoString(dynamicTr);
		//动态变更的表
		LinkedList<Tr> automaticTrs = new LinkedList<>();
		for (Map<String, Object> dataMap : linkedList) {
			//填充模板行数据
			Tr newTr = (Tr) XmlUtils.unmarshallFromTemplate(dynamicTrXml, dataMap);
			//这种处理处理无法处理末尾行的问题.
			//table.getContent().add(newTr);
			if (newTr != null) {
				automaticTrs.add(newTr);
			}
		}
		//删除模板行的占位行.
		table.getContent().remove(tableRowIndex);
		//在原来位置上进行信息追加.
		table.getContent().addAll(tableRowIndex, automaticTrs);
		//设置全局的变量替换
		wordMLPackage.getMainDocumentPart().variableReplace(unqiueDataMap);
		Docx4J.save(wordMLPackage, new File(docxNewFile));
		//释放资源
		wordMLPackage.reset();
		return true;
	}
	
	/**
	 * 生成 word文档
	 * 有多个表格的情况生成,数据给入的时候,一定要注意 :
	 * 存放数据的顺序,就是对应的表格上要填充的数据.
	 *
	 * @param docxOrginFile
	 * @param unqiueDataMap ：已经存在的全局的 key - value 集合.
	 * @param linkedList    :单个表单情况下的列表的数据
	 * @return
	 */
	public boolean convert2TablesWord(String docxOrginFile, Map<String, String> unqiueDataMap, String docxNewFile, ArrayList<DocxDataVO> linkedList) throws JAXBException {
		try {
			if (StringUtils.isBlank(docxOrginFile) || StringUtils.isBlank(docxNewFile)) {
				throw new Exception("传入的文件路径为空!");
			}
			if (null == unqiueDataMap || unqiueDataMap.isEmpty() || null == linkedList || linkedList.isEmpty()) {
				throw new Exception("传入的数据为空!");
			}
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(docxOrginFile));
			for (int i = 0; i < linkedList.size(); i++) {
				DocxDataVO dataVO = linkedList.get(i);
				ClassFinder find = new ClassFinder(Tbl.class);
				new TraversalUtil(wordMLPackage.getMainDocumentPart().getContent(), find);
				//第文档中对应的第几个表.
				int tableIndex = dataVO.getTableIndex() == null ? i : dataVO.getTableIndex();
				Tbl table = (Tbl) find.results.get(tableIndex);
				//开始要替换的行
				int tableRowIndex = dataVO.getReplaceRowIndex() == null ? 1 : dataVO.getReplaceRowIndex();
				//第二行约定为模板${}
				Tr dynamicTr = (Tr) table.getContent().get(tableRowIndex);
				//获取模板行的xml数据
				String dynamicTrXml = XmlUtils.marshaltoString(dynamicTr);
				ArrayList<Map<String, Object>> docxDataList = linkedList.get(i).getDocxDataList();
				//动态变更的表
				LinkedList<Tr> automaticTrs = new LinkedList<>();
				for (Map<String, Object> dataMap : docxDataList) {
					//填充模板行数据
					Tr newTr = (Tr) XmlUtils.unmarshallFromTemplate(dynamicTrXml, dataMap);
					//这种处理处理无法处理末尾行的问题.
					//table.getContent().add(newTr);
					if (newTr != null) {
						automaticTrs.add(newTr);
					}
				}
				//删除模板行的占位行
				table.getContent().remove(tableRowIndex);
				//在原来位置上进行信息追加.
				table.getContent().addAll(tableRowIndex, automaticTrs);
			}
			//设置全局的变量替换
			wordMLPackage.getMainDocumentPart().variableReplace(unqiueDataMap);
			Docx4J.save(wordMLPackage, new File(docxNewFile));
			//释放资源
			wordMLPackage.reset();
			return true;
		} catch (Exception e) {
			throw new JAXBException("生成文档失败!");
		}
	}
	
	
	/**
	 * 生成带图片插入的 word
	 *
	 * @param docxOrginFile
	 * @param unqiueDataMap
	 * @param docxNewFile
	 * @param searchText:文本替换位置.
	 * @param imgPath
	 * @param contains:          true 标识包涵; false 等于
	 * @return
	 * @throws JAXBException
	 */
	public boolean convert2ImgWord(String docxOrginFile, Map<String, String> unqiueDataMap, String docxNewFile, String searchText, String imgPath, Boolean contains) throws JAXBException {
		try {
			if (StringUtils.isBlank(docxOrginFile) || StringUtils.isBlank(docxNewFile)) {
				throw new Exception("传入的文件路径为空!");
			}
			if (null == unqiueDataMap || unqiueDataMap.isEmpty()) {
				throw new Exception("传入的数据为空!");
			}
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(docxOrginFile));
			ObjectFactory factory = Context.getWmlObjectFactory();
			//得到主段落
			MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
			//提取正文
			Document document = documentPart.getContents();
			//提取所有段落
			List<Object> paragraphs = document.getBody().getContent();
			//拿到指定段落,替换图片.
			insertImgInfo(searchText, imgPath, wordMLPackage, factory, paragraphs, contains);
			//替换占位符所占有的值.
			documentPart.variableReplace(unqiueDataMap);
			//保存文件.
			Docx4J.save(wordMLPackage, new File(docxNewFile));
			//释放资源
			wordMLPackage.reset();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new JAXBException("生成文档失败!");
		}
	}
	
	/**
	 * 在段落中插入图片
	 *
	 * @param searchText
	 * @param imgPath
	 * @param wordMLPackage
	 * @param factory
	 * @param paragraphs
	 * @param contains:     true 标识包涵; false 等于
	 * @throws Exception
	 */
	private void insertImgInfo(String searchText, String imgPath, WordprocessingMLPackage wordMLPackage, ObjectFactory factory, List<Object> paragraphs, Boolean contains) throws Exception {
		P paragraph = new P();
		for (Object object : paragraphs) {
			//找到文本位置.
			if (null == contains || contains.booleanValue() == true) {
				if (object.toString().contains(searchText)) {
					paragraph = (P) object;
					break;
				}
			} else if (contains.booleanValue() == false) {
				if (object.toString().equalsIgnoreCase(searchText)) {
					paragraph = (P) object;
					break;
				}
			}
		}
		//要添加的内容
		// R对象是匿名的复杂类型
		R run = factory.createR();
		// drawing理解为画布？
		Drawing drawing = getDraw(imgPath, wordMLPackage, factory);
		//段落设置换行.
		run.getContent().add(new Br());
		//将文字和图片按照顺序放入R中
		run.getContent().add(drawing);
		//段落中添加图片
		paragraph.getContent().add(run);
	}
	
	
	/**
	 * 生成带图片插入的 word
	 *
	 * @param docxOrginFile
	 * @param unqiueDataMap
	 * @param docxNewFile
	 * @param imgInfos:     searchText 要插入的图片的位置;imgPath 图片的绝对位置.
	 * @param contains:     true 标识包涵; false 等于
	 * @return
	 * @throws JAXBException
	 */
	public boolean convert2ImgsWord(String docxOrginFile, Map<String, String> unqiueDataMap, String docxNewFile, List<DocxImageVO> imgInfos, Boolean contains) throws JAXBException {
		try {
			if (StringUtils.isBlank(docxOrginFile) || StringUtils.isBlank(docxNewFile)) {
				throw new Exception("传入的文件路径为空!");
			}
			if (null == unqiueDataMap || unqiueDataMap.isEmpty()) {
				throw new Exception("传入的数据为空!");
			}
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(docxOrginFile));
			ObjectFactory factory = Context.getWmlObjectFactory();
			//得到主段落
			MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
			//提取正文
			Document document = documentPart.getContents();
			//提取所有段落
			List<Object> paragraphs = document.getBody().getContent();
			//循环查找要替换的图片位置.
			for (DocxImageVO imgVO : imgInfos) {
				String searchText = imgVO.getSearchText();
				String imgPath = imgVO.getImagePath();
				insertImgInfo(searchText, imgPath, wordMLPackage, factory, paragraphs, contains);
			}
			//替换占位符所占的位置值.
			documentPart.variableReplace(unqiueDataMap);
			//保存新生成的文件.
			Docx4J.save(wordMLPackage, new File(docxNewFile));
			//释放资源
			wordMLPackage.reset();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new JAXBException("生成文档失败!");
		}
	}
	
	
	/**
	 * 创建图片对象.
	 *
	 * @param imgPath
	 * @param mlPackage
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	public Drawing getDraw(String imgPath, WordprocessingMLPackage mlPackage, ObjectFactory factory) throws Exception {
		InputStream is = new FileInputStream(imgPath);
		byte[] bytes = IOUtils.toByteArray(is);
		// 创建一个行内图片
		BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(mlPackage, bytes);
		//设置图片最大宽度
		int docPrId = 0;
		int cNvPrId = 1;
		//可以有很多的构造函数,具体可以根据构造函数来处理.
		Inline inline = imagePart.createImageInline("Filename hint", "Alternative text", docPrId, cNvPrId, false);
		// drawing理解为画布
		Drawing drawing = factory.createDrawing();
		drawing.getAnchorOrInline().add(inline);
		return drawing;
	}
	
	
	/**
	 * 设置最大Text类型节点个数 如果超过此值，在删除占位符时可能会重复计算导致错误
	 */
	public int MAX_TEXT_SIZE = 1000000;
	
	/**
	 * 生成 word文档
	 * 没有表格的情况下使用这种方式.
	 *
	 * @param docxOrginFile
	 * @param dataMap
	 * @param docxNewFile
	 * @return
	 */
	public boolean convertToWord(String docxOrginFile, Map<String, String> dataMap, String docxNewFile) throws Exception {
		if (StringUtils.isBlank(docxOrginFile) || StringUtils.isBlank(docxNewFile)) {
			throw new Exception("传入的文件路径为空!");
		}
		if (null == dataMap || dataMap.isEmpty()) {
			throw new Exception("传入的数据为空!");
		}
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(docxOrginFile));
		List<Object> texts = getAllElementFromObject(wordMLPackage.getMainDocumentPart(), Text.class);
		searchAndReplace(texts, dataMap);
		wordMLPackage.save(new File(docxNewFile));
		//释放资源
		wordMLPackage.reset();
		return true;
	}
	
	/**
	 * 递归获取所有的节点
	 *
	 * @param obj      当前文档
	 * @param toSearch 要查询的节点类型
	 * @return java.util.List<java.lang.Object>
	 */
	public List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
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
	public void searchAndReplace(List<Object> texts, Map<String, String> values) {
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
	public List<int[]> getPlaceholderList(List<Object> texts, Map<String, String> values) {
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
