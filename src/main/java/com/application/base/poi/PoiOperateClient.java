package com.application.base.poi;

import com.application.base.config.PdfPropsConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ：admin
 * @description: poi 方式转换
 * 更多信息,后续补充.
 * @modified By：
 * @version: 1.0.0
 */
@Slf4j
@Component
public class PoiOperateClient {
	
	/**
	 * 字体路径.
	 */
	@Autowired
	private PdfPropsConfig propsConfig;
	
	/**
	 * 利用poi将word 转 pdf
	 *
	 * @param docxPath
	 * @param pdfPath
	 * @return
	 */
	public boolean convertWord2Pdf(String docxPath, String pdfPath) throws Exception {
		try {
			XWPFDocument document;
			InputStream doc = new FileInputStream(docxPath);
			document = new XWPFDocument(doc);
			PdfOptions options = PdfOptions.create();
			LocalTextFontRegistry fontRegistry = new LocalTextFontRegistry(propsConfig.getFontPath());
			//设置字体.
			options.fontProvider(fontRegistry);
			OutputStream out = new FileOutputStream(pdfPath);
			PdfConverter.getInstance().convert(document, out, options);
			doc.close();
			out.close();
		} catch (Exception e) {
			log.error("将完整的word转换成pdf失败了,失败信息是：{}", e.getMessage());
			return false;
		}
		return true;
	}
}
