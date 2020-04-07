package com.application.base.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 孤狼
 * @NAME: PropsConfig
 * @DESC: PropsConfig类设计
 **/
@Configuration
@ConfigurationProperties(prefix = "pdf")
public class PdfPropsConfig {
	
	/**
	 * 文件获取的路径.
	 */
	private String imgUrl;
	/**
	 * 数据路径.
	 */
	private String dataPath;
	/**
	 * phantomjs的路径.
	 */
	private String phantomjsPath;
	/**
	 * convertjs的路径.
	 */
	private String convetJsPath;
	/**
	 * 水印字.
	 */
	private String waterMark;
	
	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getDataPath() {
		return dataPath;
	}
	
	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
	
	public String getPhantomjsPath() {
		return phantomjsPath;
	}
	
	public void setPhantomjsPath(String phantomjsPath) {
		this.phantomjsPath = phantomjsPath;
	}
	
	public String getConvetJsPath() {
		return convetJsPath;
	}
	
	public void setConvetJsPath(String convetJsPath) {
		this.convetJsPath = convetJsPath;
	}
	
	public String getWaterMark() {
		return waterMark;
	}
	
	public void setWaterMark(String waterMark) {
		this.waterMark = waterMark;
	}
}
