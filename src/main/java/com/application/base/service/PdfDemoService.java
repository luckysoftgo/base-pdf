package com.application.base.service;

import freemarker.template.Configuration;

import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: PdfDemoService
 * @DESC: PdfDemoService类设计
 **/
public interface PdfDemoService {
	
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
	public boolean createHtml(String dataPath, String companyName, String reportName, String creditCode, Configuration configuration, Map<String, Object> dataMap) ;
	
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
	public boolean changeHtmlToPdf(String fontLocal,String dataPath,String companyName,String creditCode,String sign,String watermark,String encrypt);
	
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
	public boolean changeHtmlToPdf(String fontLocal,String dataPath,String companyName,String creditCode,String sign,String watermark,String encrypt,String seal,String sealText);
	
	/**
	 * 获取 pdf 要使用的 map 中的数.
	 * @return
	 */
	public Map<String,Object> getPdfMap(String imagePath);
	
	/**
	 * 雷达图
	 * @param phantomJsPath
	 * @param convetJsPath
	 * @param dataPath
	 * @param creditCode
	 * @param creditAbility
	 * @return
	 */
	public String createRadarImg(String phantomJsPath,String convetJsPath,String dataPath,String creditCode,String creditAbility);
	
	/**
	 * 积分图
	 * @param phantomJsPath
	 * @param convetJsPath
	 * @param dataPath
	 * @param creditCode
	 * @param creditScore
	 * @return
	 */
	public String createScoreImg(String phantomJsPath,String convetJsPath,String dataPath,String creditCode,Integer creditScore) ;
	
}
