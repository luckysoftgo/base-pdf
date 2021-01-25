package com.application.base.docx4j;

import lombok.extern.slf4j.Slf4j;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author ：admin
 * @date ：2021-1-25
 * @description: 标识符去除
 * @modified By：
 * @version: 1.0.0
 */
@Slf4j
@Component
public class DocxVariableClearClient {
	
	/**
	 * 去任意XML标签
	 */
	private final Pattern XML_PATTERN = Pattern.compile("<[^>]*>");
	
	private DocxVariableClearClient() {
	}
	
	/**
	 * start符号
	 */
	private final char PREFIX = '$';
	/**
	 * 中包含
	 */
	private final char LEFT_BRACE = '{';
	/**
	 * 结尾
	 */
	private final char RIGHT_BRACE = '}';
	/**
	 * 未开始
	 */
	private final int NONE_START = -1;
	/**
	 * 未开始
	 */
	private final int NONE_START_INDEX = -1;
	/**
	 * 开始
	 */
	private final int PREFIX_STATUS = 1;
	/**
	 * 左括号
	 */
	private final int LEFT_BRACE_STATUS = 2;
	/**
	 * 右括号
	 */
	private final int RIGHT_BRACE_STATUS = 3;
	
	
	/**
	 * 替换变量并下载word文档
	 *
	 * @param inputStream
	 * @param map
	 * @param response
	 * @param fileName
	 */
	public void downloadDocUseDoc4j(InputStream inputStream, Map<String, String> map,
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
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 替换变量并输出word文档
	 *
	 * @param inputStream
	 * @param map
	 * @param outputStream
	 */
	public void replaceDocUseDoc4j(InputStream inputStream, Map<String, String> map, OutputStream outputStream) {
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
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 清除模板中的样式,支持 {中文} 方式
	 *
	 * @param documentPart
	 * @return
	 * @throws Exception
	 */
	public boolean cleanDocumentPart(MainDocumentPart documentPart) throws Exception {
		if (documentPart == null) {
			return false;
		}
		Document document = documentPart.getContents();
		String wmlTemplate = XmlUtils.marshaltoString(document, true, false, Context.jc);
		document = (Document) XmlUtils.unwrap(doCleanDocumentPart(wmlTemplate, Context.jc));
		documentPart.setContents(document);
		return true;
	}
	
	/**
	 * doCleanDocumentPart
	 *
	 * @param wmlTemplate
	 * @param jc
	 * @return
	 * @throws JAXBException
	 */
	private Object doCleanDocumentPart(String wmlTemplate, JAXBContext jc) throws JAXBException {
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
