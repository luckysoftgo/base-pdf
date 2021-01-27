package com.application.base.libreoffice;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：admin
 * @description: LibreOffice 配置信息
 * https://www.it1352.com/1550673.html
 * <p>
 * --convert-to html:HTML
 * --convert-to html:draw_html_Export                 # force "Draw" to generate the HTML
 * --convert-to mediawiki:MediaWiki_Web               # generate MediaWiki output
 * --convert-to csv:"Text - txt - csv (StarCalc)"     # generate CSV spreadsheet output
 * --convert-to pptx:"Impress MS PowerPoint 2007 XML" # generate PPTX
 * --convert-to ppt:"MS PowerPoint 97"                # generate PPT
 * --convert-to wmf:impress_wmf_Export                # force "Impress" to generate the WMF
 * --convert-to wmf:draw_wmf_Export                   # force "Draw" to generate the WMF
 * --convert-to emf:impress_emf_Export                # force "Impress" to generate the EMF
 * --convert-to emf:draw_emf_Export                   # force "Draw" to generate the EMF
 * --convert-to svg:impress_svg_Export                # force "Impress" to generate the SVG
 * --convert-to svg:draw_svg_Export                   # force "Draw" to generate the SVG
 * --convert-to xlsx:"Calc MS Excel 2007 XML"         # generate XLSX
 * --convert-to xls:"MS Excel 97"                     # generate XLS like Excel 97
 * --convert-to xls:"MS Excel 95"                     # generate XLS like Excel 95
 * --convert-to xls:"MS Excel 5.0/95"                 # generate XLS like Excel 5.0/95
 * @modified By：
 * @version: 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "libreoffice")
public class LibreOfficeConfig {
	
	/**
	 * 可执行的LibreOffice的地址：
	 * windows: D:\installer\libreoffice\program\soffice.exe
	 * linux: /opt/libreoffice6.4/program/soffice
	 */
	private String libreofficeHome;
	
	/**
	 * 执行pdf转换的命令
	 */
	private String execPdfCommand = " --headless --invisible --convert-to pdf:writer_pdf_Export ";
	
	/**
	 * 执行html转换的命令
	 */
	private String execHtmlCommand = " --headless --invisible --convert-to html ";
	
}
