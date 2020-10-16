package com.application.base.service;

import freemarker.template.Configuration;

import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: PdfInfoService
 * @DESC: PdfDemoService 类设计
 **/
public interface PdfInfoService {
	
	/**
	 * 生成html页面
	 * @param dataPath
	 * @param companyName
	 * @param reportName
	 * @param creditCode
	 * @param configuration
	 * @param dataMap
	 * @return
	 */
	public boolean createHtml(String dataPath, String companyName, String reportName, String creditCode,
	                          Configuration configuration, Map<String, Object> dataMap) ;
	
	/**
	 * 将html页面转成pdf
	 * @param fontLocal
	 * @param dataPath
	 * @param companyName
	 * @param creditCode
	 * @param sign
	 * @param watermark
	 * @param encrypt
	 * @return
	 */
	public boolean changeHtmlToPdf(String fontLocal, String dataPath, String companyName, String creditCode,
	                               String sign, String watermark, String encrypt);
	
	/**
	 * 获取 pdf 要使用的 map 中的数.
	 * @return
	 */
	public Map<String,Object> getPdfMap(String imagePath);
	
	/**
	 * 获取 pdf 要使用的 map 中的数.
	 * @param reportType:credit 信用报告,crauth CR认证报告.
	 * @param creditCode 公司统一社会信用代码
	 * @param companyName 公司名称
	 * @return
	 */
	public Map<String,Object> getTianYanChaPdfMap(String reportType, String creditCode, String companyName) throws Exception;
	
	/**
	 * 雷达图
	 * @param phantomJsPath
	 * @param convetJsPath
	 * @param dataPath
	 * @param creditCode
	 * @param creditAbility
	 * @return
	 */
	public String createRadarImg(String phantomJsPath, String convetJsPath, String dataPath, String creditCode, String creditAbility);
	
	/**
	 * 积分图
	 * @param phantomJsPath
	 * @param convetJsPath
	 * @param dataPath
	 * @param creditCode
	 * @param creditScore
	 * @return
	 */
	public String createScoreImg(String phantomJsPath, String convetJsPath, String dataPath, String creditCode, Integer creditScore) ;
	
}
