package com.application.base.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.Charset;

/**
 * @author :admin
 * @description:办公软件操作 * 使用poi+itextpdf进行word转pdf
 * * 先将word转成html，再将html转成pdf
 * @modified By:
 * @version:1.0.0
 */
@Slf4j
public class OfficeOperateUtil {
	
	/**
	 * 字符编码集申明.
	 */
	private static final String ENCODING = "UTF-8";
	
	/**
	 * doc结尾的word文件转换成html,pdf文件.
	 *
	 * @param docPath        doc文件路径
	 * @param htmlPath       生成 html文件路径
	 * @param pdfPath        生成 pdf 文件路径
	 * @param imageDir       如果有图片,图片的路径.
	 * @param rectangle      :纸张大小设置. com.itextpdf.text.PageSize 中定义的对象,默认是 A4 纸张大小的文件
	 * @param replaceSymbols 要替换的字符.
	 * @return
	 */
	public static boolean docFile2Files(String docPath, String htmlPath, String pdfPath, String imageDir, Rectangle rectangle, java.util.List<String> replaceSymbols) {
		String htmlContent = docFile2HtmlContent(docPath, imageDir);
		if (StringUtils.isBlank(htmlContent)) {
			log.info("docFile2Files:通过获取以 doc 结尾的 word 文件html内容为空");
			return false;
		}
		htmlContent = formatHtmlContent(htmlContent);
		if (!(replaceSymbols == null || replaceSymbols.isEmpty())) {
			for (String symbol : replaceSymbols) {
				htmlContent = htmlContent.replaceAll(symbol, "");
			}
		}
		boolean result = htmlContentToHtmlFile(htmlContent, htmlPath);
		result = htmlContentToPdfFile(htmlContent, pdfPath, rectangle);
		return result;
	}
	
	/**
	 * docx 结尾的word文件转换成 html,pdf文件.
	 *
	 * @param docxPath       docx文件路径
	 * @param htmlPath       生成 html文件路径
	 * @param pdfPath        生成 pdf 文件路径
	 * @param imageDir       如果有图片,图片的路径.
	 * @param rectangle      :纸张大小设置. com.itextpdf.text.PageSize 中定义的对象,默认是 A4 纸张大小的文件
	 * @param replaceSymbols 要替换的字符.
	 * @return
	 */
	public static boolean docxFile2Files(String docxPath, String htmlPath, String pdfPath, String imageDir, Rectangle rectangle, java.util.List<String> replaceSymbols) {
		String htmlContent = docxFile2HtmlContent(docxPath, imageDir);
		if (StringUtils.isBlank(htmlContent)) {
			log.info("docxFile2Files:通过获取以 doc 结尾的 word 文件html内容为空");
			return false;
		}
		htmlContent = formatHtmlContent(htmlContent);
		if (!(replaceSymbols == null || replaceSymbols.isEmpty())) {
			for (String symbol : replaceSymbols) {
				htmlContent = htmlContent.replaceAll(symbol, "");
			}
		}
		boolean result = htmlContentToHtmlFile(htmlContent, htmlPath);
		result = htmlContentToPdfFile(htmlContent, pdfPath, rectangle);
		return result;
	}
	
	/**
	 * doc结尾的word文件转换成html文件.
	 *
	 * @param docPath        doc文件路径
	 * @param htmlPath       生成 html文件路径
	 * @param imageDir       如果有图片,图片的路径.
	 * @param replaceSymbols 文本中要替换的字符集合
	 * @return true or false
	 */
	public static boolean docFile2HtmlFile(String docPath, String htmlPath, String imageDir, java.util.List<String> replaceSymbols) {
		String htmlContent = docFile2HtmlContent(docPath, imageDir);
		if (StringUtils.isBlank(htmlContent)) {
			log.info("docFile2HtmlFile:通过获取以 doc 结尾的 word 文件html内容为空");
			return false;
		}
		htmlContent = formatHtmlContent(htmlContent);
		if (!(replaceSymbols == null || replaceSymbols.isEmpty())) {
			for (String symbol : replaceSymbols) {
				htmlContent = htmlContent.replaceAll(symbol, "");
			}
		}
		return htmlContentToHtmlFile(htmlContent, htmlPath);
	}
	
	/**
	 * docx结尾的word文件转换成html文件.
	 *
	 * @param docxPath       doc文件路径
	 * @param htmlPath       生成 html文件路径
	 * @param imageDir       如果有图片,图片的路径.
	 * @param replaceSymbols 文本中要替换的字符集合
	 * @return true or false
	 */
	public static boolean docxFile2HtmlFile(String docxPath, String htmlPath, String imageDir, java.util.List<String> replaceSymbols) {
		String htmlContent = docxFile2HtmlContent(docxPath, imageDir);
		if (StringUtils.isBlank(htmlContent)) {
			log.info("docxFile2HtmlFile:通过获取以 doc 结尾的 word 文件html内容为空");
			return false;
		}
		htmlContent = formatHtmlContent(htmlContent);
		if (!(replaceSymbols == null || replaceSymbols.isEmpty())) {
			for (String symbol : replaceSymbols) {
				htmlContent = htmlContent.replaceAll(symbol, "");
			}
		}
		return htmlContentToHtmlFile(htmlContent, htmlPath);
	}
	
	/**
	 * doc结尾的word文件转换成 pdf文件.
	 *
	 * @param docPath        doc文件路径
	 * @param pdfPath        生成 html文件路径
	 * @param imageDir       如果有图片,图片的路径.
	 * @param rectangle      :纸张大小设置. com.itextpdf.text.PageSize 中定义的对象,默认是 A4 纸张大小的文件
	 * @param replaceSymbols 文本中要替换的字符集合
	 * @return true or false
	 */
	public static boolean docFile2PdfFile(String docPath, String pdfPath, String imageDir, Rectangle rectangle, java.util.List<String> replaceSymbols) {
		String htmlContent = docFile2HtmlContent(docPath, imageDir);
		if (StringUtils.isBlank(htmlContent)) {
			log.info("docFile2PdfFile:通过获取以 doc 结尾的 word 文件html内容为空");
			return false;
		}
		htmlContent = formatHtmlContent(htmlContent);
		if (!(replaceSymbols == null || replaceSymbols.isEmpty())) {
			for (String symbol : replaceSymbols) {
				htmlContent = htmlContent.replaceAll(symbol, "");
			}
		}
		return htmlContentToPdfFile(htmlContent, pdfPath, rectangle);
	}
	
	/**
	 * docx结尾的word文件转换成 pdf 文件.
	 *
	 * @param docxPath       doc文件路径
	 * @param pdfPath        生成 html文件路径
	 * @param imageDir       如果有图片,图片的路径.
	 * @param rectangle      :纸张大小设置. com.itextpdf.text.PageSize 中定义的对象,默认是 A4 纸张大小的文件
	 * @param replaceSymbols 文本中要替换的字符集合
	 * @return true or false
	 */
	public static boolean docxFile2PdfFile(String docxPath, String pdfPath, String imageDir, Rectangle rectangle, java.util.List<String> replaceSymbols) {
		String htmlContent = docxFile2HtmlContent(docxPath, imageDir);
		if (StringUtils.isBlank(htmlContent)) {
			log.info("docxFile2PdfFile:通过获取以 doc 结尾的 word 文件html内容为空");
			return false;
		}
		htmlContent = formatHtmlContent(htmlContent);
		if (!(replaceSymbols == null || replaceSymbols.isEmpty())) {
			for (String symbol : replaceSymbols) {
				htmlContent = htmlContent.replaceAll(symbol, "");
			}
		}
		return htmlContentToPdfFile(htmlContent, pdfPath, rectangle);
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
	 * 将 doc 格式文件转成 html 的内容
	 *
	 * @param docPath  doc文件路径
	 * @param imageDir doc文件中图片存储目录
	 * @return html
	 */
	public static String docFile2HtmlContent(String docPath, String imageDir) {
		if (StringUtils.isBlank(docPath)) {
			log.info("docFile2HtmlContent:传入的以doc结尾的文件路径为空");
			return "";
		}
		if (StringUtils.isBlank(imageDir)) {
			log.info("docFile2HtmlContent:传入的图片路径为空");
			return "";
		}
		createFile(imageDir, null);
		String content = null;
		ByteArrayOutputStream baos = null;
		try {
			HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(docPath));
			WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
			wordToHtmlConverter.setPicturesManager(new PicturesManager() {
				@Override
				public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
					File file = new File(imageDir + suggestedName);
					FileOutputStream fos = null;
					try {
						fos = new FileOutputStream(file);
						fos.write(content);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							if (fos != null) {
								fos.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return imageDir + suggestedName;
				}
			});
			wordToHtmlConverter.processDocument(wordDocument);
			Document htmlDocument = wordToHtmlConverter.getDocument();
			DOMSource domSource = new DOMSource(htmlDocument);
			baos = new ByteArrayOutputStream();
			StreamResult streamResult = new StreamResult(baos);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			serializer.transform(domSource, streamResult);
		} catch (Exception e) {
			log.error("docFile2HtmlContent:doc结尾的文件转换失败,失败信息是:{}", e.getMessage());
		} finally {
			try {
				if (baos != null) {
					content = new String(baos.toByteArray(), ENCODING);
					baos.close();
				}
			} catch (Exception e) {
				log.error("docFile2HtmlContent:关闭流服务失败,失败信息是:{}", e.getMessage());
			}
		}
		return content;
	}
	
	/**
	 * 将 docx 格式文件转成 html 的内容
	 *
	 * @param docxPath docx文件路径
	 * @param imageDir docx文件中图片存储目录
	 * @return html
	 */
	public static String docxFile2HtmlContent(String docxPath, String imageDir) {
		if (StringUtils.isBlank(docxPath)) {
			log.info("docxFile2HtmlContent:传入的以doc结尾的文件路径为空");
			return "";
		}
		if (StringUtils.isBlank(imageDir)) {
			log.info("docxFile2HtmlContent:传入的图片路径为空");
			return "";
		}
		createFile(imageDir, null);
		String content = null;
		FileInputStream in = null;
		ByteArrayOutputStream baos = null;
		try {
			// 1> 加载文档到XWPFDocument
			in = new FileInputStream(new File(docxPath));
			XWPFDocument document = new XWPFDocument(in);
			// 2> 解析XHTML配置（这里设置IURIResolver来设置图片存放的目录）
			XHTMLOptions options = XHTMLOptions.create();
			// 存放word中图片的目录
			options.setExtractor(new FileImageExtractor(new File(imageDir)));
			options.URIResolver(new BasicURIResolver(imageDir));
			options.setIgnoreStylesIfUnused(false);
			options.setFragment(true);
			// 3> 将XWPFDocument转换成XHTML
			baos = new ByteArrayOutputStream();
			XHTMLConverter.getInstance().convert(document, baos, options);
		} catch (Exception e) {
			log.error("docxFile2HtmlContent:docx结尾的文件转换失败,失败信息是:{}", e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (baos != null) {
					content = new String(baos.toByteArray(), ENCODING);
					baos.close();
				}
			} catch (Exception e) {
				log.error("docxFile2HtmlContent:关闭流服务失败,失败信息是:{}", e.getMessage());
			}
		}
		return content;
	}
	
	/**
	 * 使用 jsoup 规范化 html
	 *
	 * @param htmlContent html内容
	 * @return 规范化后的html
	 */
	private static String formatHtmlContent(String htmlContent) {
		if (StringUtils.isBlank(htmlContent)) {
			log.info("formatHtmlContent:传入的文件内容为空");
			return "";
		}
		org.jsoup.nodes.Document doc = Jsoup.parse(htmlContent);
		// 去除过大的宽度
		String style = doc.attr("style");
		if (StringUtils.isNotEmpty(style) && style.contains("width")) {
			doc.attr("style", "");
			//doc.attr("style", "90%");
		}
		Elements divs = doc.select("div");
		for (Element div : divs) {
			String divStyle = div.attr("style");
			if (StringUtils.isNotEmpty(divStyle) && divStyle.contains("width")) {
				div.attr("style", "");
				// div.attr("style", "90%");
			}
		}
		//jsoup生成闭合标签
		doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
		doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
		return doc.html();
	}
	
	/**
	 * html内容转化成 pdf文件
	 *
	 * @param htmlContent   :html内容
	 * @param outputPdfPath :输出pdf路径，带文件名称
	 * @param rectangle     :纸张大小设置. com.itextpdf.text.PageSize 中定义的对象,默认是 A4 纸张大小的文件
	 */
	public static boolean htmlContentToPdfFile(String htmlContent, String outputPdfPath, Rectangle rectangle) {
		if (StringUtils.isBlank(htmlContent)) {
			log.info("htmlContentToPdfFile:传入的文件内容为空");
			return false;
		}
		if (StringUtils.isBlank(outputPdfPath)) {
			log.info("htmlContentToPdfFile:传入的生成 pdf 文件的路径为空");
			return false;
		}
		createFile(outputPdfPath, Boolean.TRUE);
		com.itextpdf.text.Document document = null;
		ByteArrayInputStream bais = null;
		try {
			// 纸
			if (null == rectangle) {
				rectangle = PageSize.A4;
			}
			document = new com.itextpdf.text.Document(rectangle);
			File file = new File(outputPdfPath);
			// 笔
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			// html转pdf
			bais = new ByteArrayInputStream(htmlContent.getBytes());
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, bais,
					Charset.forName(ENCODING), new FontProvider() {
						@Override
						public boolean isRegistered(String s) {
							return false;
						}
						
						@Override
						public Font getFont(String s, String s1, boolean embedded, float size, int style, BaseColor baseColor) {
							// 配置字体
							Font font = null;
							try {
								// 方案一:使用本地字体(本地需要有字体)
								//BaseFont bf = BaseFont.createFont("c:/Windows/Fonts/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
								//BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/seguisym.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
								// 方案二:使用jar包:iTextAsian，这样只需一个jar包就可以了
								BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
								font = new Font(bf, size, style, baseColor);
								font.setColor(baseColor);
							} catch (Exception e) {
								e.printStackTrace();
							}
							return font;
						}
					});
			return true;
		} catch (Exception e) {
			log.error("htmlContentToPdfFile:html文本内容转化成 pdf 失败,失败信息是:{}", e.getMessage());
			return false;
		} finally {
			if (document != null) {
				document.close();
			}
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					log.error("htmlContentToPdfFile:关闭文件流失败,失败信息是:{}", e.getMessage());
				}
			}
		}
	}
	
	
	/**
	 * docx 转换成 html文件.
	 *
	 * @param htmlContent    html格式的文件.
	 * @param outputHtmlPath 输出html文件的路径.
	 */
	public static boolean htmlContentToHtmlFile(String htmlContent, String outputHtmlPath) {
		if (StringUtils.isBlank(htmlContent)) {
			log.info("htmlContentToHtmlFile:传入的文件内容为空");
			return false;
		}
		if (StringUtils.isBlank(outputHtmlPath)) {
			log.info("htmlContentToHtmlFile:传入的生成 html 文件的路径为空");
			return false;
		}
		createFile(outputHtmlPath, Boolean.TRUE);
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			File file = new File(outputHtmlPath);
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(fos, ENCODING));
			writer.write(htmlContent);
			writer.close();
			fos.close();
			return true;
		} catch (Exception e) {
			log.error("htmlContentToHtmlFile :html文本内容转化成html文件失败,失败信息是:{}", e.getMessage());
			return false;
		}
	}
	
}
