package com.application.base.test.poi;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：admin
 * @date ：2021-1-14
 * @description:
 * @modified By：
 * @version: 1.0.0
 */
public class Word2Pdf {
	private final static String DOC_X = "E:\\home\\pdf\\resources\\data\\result_test14.docx";
	private final static String PDF = "E:\\home\\pdf\\resources\\data\\result_test141.pdf";
	
	public static void main(String[] args) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("${name}", "dz");
			param.put("${sex}", "male");
			Map<String, Object> image = new HashMap<String, Object>();
			image.put("width", 300);
			image.put("height", 300);
			image.put("type", "png");
			image.put("content", WordUtil.inputStream2ByteArray(new FileInputStream("D:\\3.png"), true));
			param.put("${image}", image);
			XWPFDocument doc = WordUtil.generateWord(param, DOC_X);
			// 在没有字体的服务器上发布要用到下面 options,同时在resource目录下加入字体文件， windows 服务器上可不加
			PdfOptions options = PdfOptions.create();
			options.fontProvider(new IFontProvider() {
				
				@Override
				public Font getFont(String familyName, String encoding, float size, int style, java.awt.Color color) {
					try {
						BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
						Font fontChinese = new Font(bfChinese, size, style, color);
						if (familyName != null)
							fontChinese.setFamily(familyName);
						return fontChinese;
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			});
			OutputStream out = new FileOutputStream(PDF);
			PdfConverter.getInstance().convert(doc, out, options);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
