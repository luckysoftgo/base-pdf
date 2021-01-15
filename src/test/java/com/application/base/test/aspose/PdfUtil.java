package com.application.base.test.aspose;

import com.application.base.asponse.AsponseClient;
import com.aspose.words.Document;
import com.aspose.words.License;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author ：admin
 * @date ：2021-1-14
 * @description:
 * @modified By：
 * @version: 1.0.0
 */
public class PdfUtil {
	
	public static void main(String[] args) throws Exception {
		
		//doc2file("E:\\home\\pdf\\resources\\data\\temp_test5.docx","E:\\home\\pdf\\resources\\data\\temp_test51.pdf",SaveFormat.PDF);
		
		String filepath = "E:\\home\\pdf\\resources\\data\\temp7.docx";
		String pdfpath = "E:\\home\\pdf\\resources\\data\\7pdf.pdf";
		String htmlpath = "E:\\home\\pdf\\resources\\data\\7pdf.html";
		String pngpath = "E:\\home\\pdf\\resources\\data\\7pdf.png";
		//doc2file(filepath,pdfpath,SaveFormat.PDF);
		//doc2file(filepath,pngpath,SaveFormat.PNG);
		AsponseClient client = new AsponseClient();
		client.convertDocx2Html(filepath, htmlpath);
		client.convertDocx2Pdf(filepath, pdfpath);
		client.convertDocx2Png(filepath, pngpath);
		System.out.println("完成操作...");
	}
	
	
	private static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = PdfUtil.class.getClassLoader().getResourceAsStream("license.xml");
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @param wordPath 需要被转换的word全路径带文件名
	 * @param pdfPath  转换之后pdf的全路径带文件名
	 */
	public static void doc2file(String wordPath, String pdfPath, Integer value) {
		// 验证License 若不验证则转化出的pdf文档会有水印产生
		if (!getLicense()) {
			return;
		}
		try {
			long old = System.currentTimeMillis();
			//新建一个pdf文档
			File file = new File(pdfPath);
			FileOutputStream os = new FileOutputStream(file);
			//Address是将要被转化的word文档
			Document doc = new Document(wordPath);
			//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
			doc.save(os, value);
			
			long now = System.currentTimeMillis();
			os.flush();
			os.close();
			//转化用时
			System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
