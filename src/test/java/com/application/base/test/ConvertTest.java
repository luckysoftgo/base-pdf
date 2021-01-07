package com.application.base.test;

import com.application.base.util.OfficeOperateUtil;

import java.util.Arrays;

/**
 * @author ：admin
 * @date ：2021-1-7
 * @description: 类型转换
 * @modified By：
 * @version: 1.0.0
 */
public class ConvertTest {
	
	/**
	 * 测试的主入口
	 *
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String basePath = "E:\\home\\pdf\\resources\\data\\";
		String docxPath = basePath + "temp11.docx";
		String pdfPath = basePath + "temp11.pdf";
		String htmlPath = basePath + "temp11.html";
		String imageDir = basePath + "image\\";
		//处理设置.
		//OfficeOperateUtil.docxFile2HtmlFile(docxPath, htmlPath, imageDir, Arrays.asList(new String[]{"<p><br /></p>"}));
		//OfficeOperateUtil.docxFile2PdfFile(docxPath, pdfPath, imageDir, null,Arrays.asList(new String[]{"<p><br /></p>"}));
		OfficeOperateUtil.docxFile2Files(docxPath, htmlPath, pdfPath, imageDir, null, Arrays.asList(new String[]{"<p><br /></p>"}));
		System.out.println("转换完成!");
	}
}
