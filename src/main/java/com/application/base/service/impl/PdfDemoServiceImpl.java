package com.application.base.service.impl;

import com.application.base.service.PdfDemoService;
import com.application.base.util.PdfBuildServer;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: PdfDemoServiceImpl
 * @DESC: PdfDemoServiceImpl类设计
 **/
@Service
public class PdfDemoServiceImpl implements PdfDemoService {
	
	@Autowired
	private PdfBuildServer pdfBuildServer;
	
	@Override
	public boolean createHtml(String dataPath, String companyName, String reportName, String creditCode,
	                          Configuration configuration, Map<String, Object> dataMap) {
		return pdfBuildServer.createHtmlContent(dataPath,companyName,reportName,creditCode,configuration,dataMap);
	}
	
	@Override
	public boolean changeHtmlToPdf(String fontLocal,String dataPath,String companyName,String creditCode,String sign,String watermark,String encrypt) {
		return pdfBuildServer.convertHtmlToPdf(fontLocal,dataPath,companyName,creditCode,sign,watermark,encrypt);
	}
	
	@Override
	public boolean changeHtmlToPdf(String fontLocal, String dataPath, String companyName, String creditCode,
	                               String sign, String watermark, String encrypt, String seal, String sealText) {
		return pdfBuildServer.convertHtmlToPdf(fontLocal,dataPath,companyName,creditCode,sign,watermark,encrypt,seal,sealText);
	}
	
	@Override
	public Map<String, Object> getPdfMap(String imagePath) {
		return pdfBuildServer.getDefaultMap(imagePath);
	}
	
	@Override
	public String createRadarImg(String phantomJsPath,String convetJsPath,String dataPath,String creditCode,String creditAbility) {
		return pdfBuildServer.createDefRadarImg(phantomJsPath,convetJsPath,dataPath,creditCode,creditAbility);
	}
	
	@Override
	public String createScoreImg(String phantomJsPath, String convetJsPath, String dataPath, String creditCode, Integer creditScore) {
		return pdfBuildServer.createDefScoreImg(phantomJsPath,convetJsPath,dataPath,creditCode,creditScore);
	}
	
}
