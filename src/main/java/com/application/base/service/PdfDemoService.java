package com.application.base.service;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: PdfDemoService
 * @DESC: PdfDemoService类设计
 **/
public interface PdfDemoService {
	
	/**
	 * 生成html页面
	 * @param freemarkerConfig
	 * @param map
	 */
	public boolean createHtml(FreeMarkerConfigurer freemarkerConfig, Map<String, Object> map);
	
	/**
	 * 将html页面转成pdf
	 * @param map
	 */
	public boolean changeHtmlToPdf(Map<String, Object> map,String sign,String encrypt);
	
	/**
	 * 获取 pdf 要使用的 map 中的数.
	 * @return
	 */
	public Map<String,Object> getPdfMap();
	
	/**
	 * 雷达图
	 * @param params
	 * @return
	 */
	public String createRadarImg(Map<String,Object> params);
	
	/**
	 * 积分图
	 * @param params
	 * @return
	 */
	public String createScoreImg(Map<String,Object> params);
	
}
