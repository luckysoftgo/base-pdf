package com.application.base.test.poi;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class WordPdfTool {
	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\result_test14.docx";
		String outpath = "E:\\home\\pdf\\resources\\data\\result_test141.pdf";
		filepath = "E:\\home\\pdf\\resources\\data\\result_test8.docx";
		outpath = "E:\\home\\pdf\\resources\\data\\result_test81.pdf";
		
		XWPFDocument document;
		InputStream doc = new FileInputStream(filepath);
		document = new XWPFDocument(doc);
		PdfOptions options = PdfOptions.create();
		OutputStream out = new FileOutputStream(outpath);
		PdfConverter.getInstance().convert(document, out, options);
		
		doc.close();
		out.close();
		
		long end = System.currentTimeMillis();
		System.out.println("转换成功,耗时:" + (end - start) / 1000);
	}
	
}
