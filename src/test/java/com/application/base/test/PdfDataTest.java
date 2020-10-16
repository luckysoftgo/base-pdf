package com.application.base.test;

import com.application.base.util.toolpdf.PhantomJsUtil;

/**
 * @author : 孤狼
 * @NAME: PdfDataTest
 * @DESC: PdfDataTest类设计
 **/
public class PdfDataTest {
	
	public static void main(String[] args) {
		String fontLocal="D:\\phantomjs211\\data\\static\\fonts\\SIMSUN.TTC";
		String pdfPath="D:\\phantomjs211\\data\\credit\\916100005637708187\\test.pdf";
		String htmlPath = "D:\\phantomjs211\\data\\credit\\916100005637708187\\陕西西部资信股份有限公司.html";
		String content = PhantomJsUtil.getHtmlContent(htmlPath);
		System.out.println("转换pdf："+PhantomJsUtil.convertHtmlToPdf(fontLocal,pdfPath,content));;
	}
	
}
