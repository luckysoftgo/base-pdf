package com.application.base.asponse;

import com.aspose.words.Shape;
import com.aspose.words.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author ：admin
 * @date ：2021-1-15
 * @description: asponse 的实现方式
 * @modified By：
 * @version: 1.0.0
 */
@Slf4j
@Component
public class AsponseClient {
	
	/**
	 * 加载 license 信息
	 */
	static {
		try {
			InputStream stream = AsponseClient.class.getClassLoader().getResourceAsStream("license.xml");
			License aposeLic = new License();
			aposeLic.setLicense(stream);
		} catch (Exception e) {
			log.info("加载license的信息失败了,失败信息是:{}", e.getMessage());
		}
	}
	
	/**
	 * word 转成 pdf
	 *
	 * @param docxPath
	 * @param pdfPath
	 * @return
	 * @throws Exception
	 */
	public boolean docxFile2Files(String docxPath, String htmlPath, String pdfPath) throws Exception {
		if (StringUtils.isBlank(docxPath)) {
			log.info("AsponseClient.docxFile2Files 传入的docx文档的路径为空!");
			return false;
		}
		if (StringUtils.isBlank(htmlPath)) {
			log.info("AsponseClient.docxFile2Files 传入的html文档的路径为空!");
			return false;
		}
		if (StringUtils.isBlank(pdfPath)) {
			log.info("AsponseClient.docxFile2Files 传入的pdf文档的路径为空!");
			return false;
		}
		createFile(htmlPath, Boolean.TRUE);
		createFile(pdfPath, Boolean.TRUE);
		// Specify LoadFormat of input word document
		LoadOptions options = new LoadOptions();
		options.setEncoding(StandardCharsets.UTF_8);
		options.setAllowTrailingWhitespaceForListItems(false);
		Document doc = new Document(docxPath, options);
		//可以设置其类型.
		HtmlSaveOptions htmlOptions = new HtmlSaveOptions();
		htmlOptions.setSaveFormat(SaveFormat.HTML);
		htmlOptions.setEncoding(StandardCharsets.UTF_8);
		//图片以 Base64 的方式展现.
		htmlOptions.setExportImagesAsBase64(true);
		doc.save(htmlPath, htmlOptions);
		
		//可以设置其类型.
		PdfSaveOptions pdfOpts = new PdfSaveOptions();
		pdfOpts.setSaveFormat(SaveFormat.PDF);
		//图片以 Base64 的方式展现.
		doc.save(pdfPath, pdfOpts);
		return true;
	}
	
	/**
	 * doc 转成 docx
	 *
	 * @param docPath
	 * @param docxPath
	 * @return
	 * @throws Exception
	 */
	public boolean convertDoc2Docx(String docPath, String docxPath) {
		if (StringUtils.isBlank(docxPath)) {
			log.info("AsponseClient.convertDoc2Docx 传入的docx文档的路径为空!");
			return false;
		}
		if (StringUtils.isBlank(docPath)) {
			log.info("AsponseClient.convertDoc2Docx 传入的doc文档的路径为空!");
			return false;
		}
		try {
			// Specify LoadFormat of input word document
			LoadOptions options = new LoadOptions();
			options.setLoadFormat(LoadFormat.DOC);
			options.setEncoding(StandardCharsets.UTF_8);
			options.setAllowTrailingWhitespaceForListItems(false);
			// Load source DOCX file
			Document document = new Document(docPath, options);
			// Convert DOCX to DOC file
			document.save(docxPath, SaveFormat.DOCX);
			return true;
		} catch (Exception e) {
			log.error("AsponseClient.convertDoc2Docx 中 DOC 转 DOCX 失败了,信息是:{}", e.getMessage());
			return false;
		}
	}
	
	/**
	 * docx 转成 doc
	 *
	 * @param docxPath
	 * @param docPath
	 * @return
	 * @throws Exception
	 */
	public boolean convertDocx2Doc(String docxPath, String docPath) {
		if (StringUtils.isBlank(docxPath)) {
			log.info("AsponseClient.convertDocx2Doc 传入的docx文档的路径为空!");
			return false;
		}
		if (StringUtils.isBlank(docPath)) {
			log.info("AsponseClient.convertDocx2Doc 传入的doc文档的路径为空!");
			return false;
		}
		try {
			// Specify LoadFormat of input word document
			LoadOptions options = new LoadOptions();
			options.setLoadFormat(LoadFormat.DOCX);
			options.setEncoding(StandardCharsets.UTF_8);
			options.setAllowTrailingWhitespaceForListItems(false);
			// Load source DOCX file
			Document document = new Document(docxPath, options);
			// Convert DOCX to DOC file
			document.save(docPath, SaveFormat.DOC);
			return true;
		} catch (Exception e) {
			log.error("AsponseClient.convertDocx2Doc 中 DOCX 转 DOC 失败了,信息是:{}", e.getMessage());
			return false;
		}
	}
	
	/**
	 * docx 转成 docx
	 *
	 * @param sourceDocxPath
	 * @param targetDocxPath
	 * @return
	 * @throws Exception
	 */
	public boolean convertDocx2Docx(String sourceDocxPath, String targetDocxPath) {
		if (StringUtils.isBlank(sourceDocxPath)) {
			log.info("AsponseClient.convertDocx2Docx 传入的源docx文档的路径为空!");
			return false;
		}
		if (StringUtils.isBlank(targetDocxPath)) {
			log.info("AsponseClient.convertDocx2Docx 传入的目的docx文档的路径为空!");
			return false;
		}
		try {
			// Specify LoadFormat of input word document
			LoadOptions options = new LoadOptions();
			options.setLoadFormat(LoadFormat.DOCX);
			options.setEncoding(StandardCharsets.UTF_8);
			options.setAllowTrailingWhitespaceForListItems(false);
			// Load source DOCX file
			Document document = new Document(sourceDocxPath, options);
			// Convert DOCX to DOCX file
			document.save(targetDocxPath, SaveFormat.DOCX);
			return true;
		} catch (Exception e) {
			log.error("AsponseClient.convertDocx2Docx 中 DOCX 转 DOCX 失败了,信息是:{}", e.getMessage());
			return false;
		}
	}
	
	/**
	 * word 转成 pdf
	 *
	 * @param docxPath
	 * @param pdfPath
	 * @return
	 * @throws Exception
	 */
	public boolean convertDocx2Pdf(String docxPath, String pdfPath) throws Exception {
		if (StringUtils.isBlank(docxPath)) {
			log.info("AsponseClient.convertDocx2Pdf 传入的docx文档的路径为空!");
			return false;
		}
		if (StringUtils.isBlank(pdfPath)) {
			log.info("AsponseClient.convertDocx2Pdf 传入的pdf文档的路径为空!");
			return false;
		}
		createFile(pdfPath, Boolean.TRUE);
		// Specify LoadFormat of input word document
		LoadOptions options = new LoadOptions();
		options.setEncoding(StandardCharsets.UTF_8);
		options.setAllowTrailingWhitespaceForListItems(false);
		Document doc = new Document(docxPath, options);
		
		//可以设置其类型.
		PdfSaveOptions opts = new PdfSaveOptions();
		opts.setSaveFormat(SaveFormat.PDF);
		//图片以 Base64 的方式展现.
		doc.save(pdfPath, opts);
		return true;
	}
	
	
	/**
	 * word 转成 pdf
	 *
	 * @param docxPath
	 * @param pdfPath
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	public boolean convertDocx2Pdf(String docxPath, String pdfPath, Map<String, String> dataMap) throws Exception {
		if (StringUtils.isBlank(docxPath)) {
			log.info("AsponseClient.convertDocx2Pdf 传入的docx文档的路径为空!");
			return false;
		}
		if (StringUtils.isBlank(pdfPath)) {
			log.info("AsponseClient.convertDocx2Pdf 传入的pdf文档的路径为空!");
			return false;
		}
		createFile(pdfPath, Boolean.TRUE);
		// Specify LoadFormat of input word document
		LoadOptions options = new LoadOptions();
		options.setEncoding(StandardCharsets.UTF_8);
		options.setAllowTrailingWhitespaceForListItems(false);
		Document doc = new Document(docxPath, options);
		if (dataMap != null && dataMap.size() > 0) {
			for (Map.Entry<String, String> entry : dataMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				doc.getRange().replace(key, value, true, false);
			}
		}
		//可以设置其类型.
		PdfSaveOptions opts = new PdfSaveOptions();
		opts.setSaveFormat(SaveFormat.PDF);
		//图片以 Base64 的方式展现.
		doc.save(pdfPath, opts);
		return true;
	}
	
	/**
	 * word 转成 pdf
	 *
	 * @param docxPath
	 * @param pdfPath
	 * @param listMap
	 * @return
	 * @throws Exception
	 */
	public boolean convertDocx2Pdf(String docxPath, String pdfPath, List<Map<String, String>> listMap) throws Exception {
		if (StringUtils.isBlank(docxPath)) {
			log.info("AsponseClient.convertDocx2Pdf 传入的docx文档的路径为空!");
			return false;
		}
		if (StringUtils.isBlank(pdfPath)) {
			log.info("AsponseClient.convertDocx2Pdf 传入的pdf文档的路径为空!");
			return false;
		}
		createFile(pdfPath, Boolean.TRUE);
		// Specify LoadFormat of input word document
		LoadOptions options = new LoadOptions();
		options.setEncoding(StandardCharsets.UTF_8);
		options.setAllowTrailingWhitespaceForListItems(false);
		Document doc = new Document(docxPath, options);
		if (listMap != null && listMap.size() > 0) {
			for (Map<String, String> dataMap : listMap) {
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					doc.getRange().replace(key, value, true, false);
				}
			}
		}
		//可以设置其类型.
		PdfSaveOptions opts = new PdfSaveOptions();
		opts.setSaveFormat(SaveFormat.PDF);
		//图片以 Base64 的方式展现.
		doc.save(pdfPath, opts);
		return true;
	}
	
	/**
	 * word 转成 html
	 *
	 * @param docxPath
	 * @param htmlPath
	 * @return
	 * @throws Exception
	 */
	public boolean convertDocx2Html(String docxPath, String htmlPath) throws Exception {
		if (StringUtils.isBlank(docxPath)) {
			log.info("AsponseClient.convertDocx2Html 传入的docx文档的路径为空!");
			return false;
		}
		if (StringUtils.isBlank(htmlPath)) {
			log.info("AsponseClient.convertDocx2Html 传入的html文档的路径为空!");
			return false;
		}
		createFile(htmlPath, Boolean.TRUE);
		// Specify LoadFormat of input word document
		LoadOptions options = new LoadOptions();
		options.setEncoding(StandardCharsets.UTF_8);
		options.setAllowTrailingWhitespaceForListItems(false);
		Document doc = new Document(docxPath, options);
		//可以设置其类型.
		HtmlSaveOptions opts = new HtmlSaveOptions();
		opts.setSaveFormat(SaveFormat.HTML);
		opts.setEncoding(StandardCharsets.UTF_8);
		//图片以 Base64 的方式展现.
		opts.setExportImagesAsBase64(true);
		doc.save(htmlPath, opts);
		return true;
	}
	
	/**
	 * word 转成 png
	 *
	 * @param docxPath
	 * @param pngPath
	 * @return
	 * @throws Exception
	 */
	public boolean convertDocx2Png(String docxPath, String pngPath) throws Exception {
		if (StringUtils.isBlank(docxPath)) {
			log.info("AsponseClient.convertDocx2Png 传入的docx文档的路径为空!");
			return false;
		}
		if (StringUtils.isBlank(pngPath)) {
			log.info("AsponseClient.convertDocx2Png 传入的png文档的路径为空!");
			return false;
		}
		createFile(pngPath, Boolean.TRUE);
		// Specify LoadFormat of input word document
		LoadOptions options = new LoadOptions();
		options.setEncoding(StandardCharsets.UTF_8);
		options.setAllowTrailingWhitespaceForListItems(false);
		Document doc = new Document(docxPath, options);
		//可以设置其类型.
		ImageSaveOptions opts = new ImageSaveOptions(SaveFormat.PNG);
		opts.setPrettyFormat(true);
		opts.setUseAntiAliasing(true);
		opts.setJpegQuality(80);
		opts.setSaveFormat(SaveFormat.PNG);
		//opts.setPageCount(doc.getPageCount());
		opts.setResolution(200);
		//图片以 Base64 的方式展现.
		doc.save(pngPath, opts);
		return true;
	}
	
	/**
	 * 创建文件
	 *
	 * @param filePath:文件路径             :可能是文件夹，也可能是文件
	 * @param createFile:如果是文件,是否需要创建文件
	 */
	private static void createFile(String filePath, Boolean createFile) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				if (createFile.booleanValue() == true) {
					file.createNewFile();
				}
			}
		} catch (Exception e) {
			log.error("创建文件失败,失败信息是:{}", e.getMessage());
		}
	}
	
	
	/**
	 * 插入水印操作
	 *
	 * @param doc           The input document.
	 * @param watermarkText Text of the watermark.
	 */
	private void insertWatermarkText(Document doc, String watermarkText) throws Exception {
		// Create a watermark shape. This will be a WordArt shape.
		// You are free to try other shape types as watermarks.
		Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
		// Set up the text of the watermark.
		watermark.getTextPath().setText(watermarkText);
		watermark.getTextPath().setFontFamily("Arial");
		watermark.setWidth(500);
		watermark.setHeight(100);
		// Text will be directed from the bottom-left to the top-right corner.
		watermark.setRotation(-40);
		// Remove the following two lines if you need a solid black text.
		watermark.getFill().setColor(Color.GRAY); // Try LightGray to get more Word-style watermark
		watermark.setStrokeColor(Color.GRAY); // Try LightGray to get more Word-style watermark
		// Place the watermark in the page center.
		watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
		watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
		watermark.setWrapType(WrapType.NONE);
		watermark.setVerticalAlignment(VerticalAlignment.CENTER);
		watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
		// Create a new paragraph and append the watermark to this paragraph.
		Paragraph watermarkPara = new Paragraph(doc);
		watermarkPara.appendChild(watermark);
		// Insert the watermark into all headers of each document section.
		for (Section sect : doc.getSections()) {
			// There could be up to three different headers in each section, since we want
			// the watermark to appear on all pages, insert into all headers.
			insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
			insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
			insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
		}
	}
	
	/**
	 * 插入水印
	 *
	 * @param watermarkPara
	 * @param sect
	 * @param headerType
	 * @throws Exception
	 */
	private static void insertWatermarkIntoHeader(Paragraph watermarkPara, Section sect, int headerType) throws Exception {
		HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
		if (header == null) {
			// There is no header of the specified type in the current section, create it.
			header = new HeaderFooter(sect.getDocument(), headerType);
			sect.getHeadersFooters().add(header);
		}
		// Insert a clone of the watermark into the header.
		header.appendChild(watermarkPara.deepClone(true));
	}
	
	/**
	 * 移除全部水印
	 *
	 * @param doc
	 * @throws Exception
	 */
	private static void removeWatermark(Document doc) throws Exception {
		for (Section sect : doc.getSections()) {
			// There could be up to three different headers in each section, since we want
			// the watermark to appear on all pages, insert into all headers.
			removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_PRIMARY);
			removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_FIRST);
			removeWatermarkFromHeader(sect, HeaderFooterType.HEADER_EVEN);
		}
	}
	
	/**
	 * 移除指定Section的水印
	 *
	 * @param sect
	 * @param headerType
	 * @throws Exception
	 */
	private static void removeWatermarkFromHeader(Section sect, int headerType) throws Exception {
		HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
		if (header != null) {
			header.removeAllChildren();
		}
	}
}
