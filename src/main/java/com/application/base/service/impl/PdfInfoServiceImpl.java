package com.application.base.service.impl;

import com.application.base.config.Constants;
import com.application.base.datas.tianyancha.TianYanChaReportServer;
import com.application.base.service.PdfInfoService;
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
public class PdfInfoServiceImpl implements PdfInfoService {
	
	@Autowired
	private PdfBuildServer pdfBuildServer;
	@Autowired
	private TianYanChaReportServer reportServer;
	
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
	public Map<String, Object> getPdfMap(String imagePath) {
		return pdfBuildServer.getDefaultMap(imagePath);
	}
	
	@Override
	public Map<String, Object> getTianYanChaPdfMap(String reportType, String creditCode, String companyName) throws Exception {
		if (reportType.equalsIgnoreCase(Constants.REPORT_TYPE_CREDIT)){
			return reportServer.getCreditReportData(creditCode,companyName);
		}else if(reportType.equalsIgnoreCase(Constants.REPORT_TYPE_CRAUTH)){
			return reportServer.getCrAuthReportData(creditCode,companyName);
		}
		return null;
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
