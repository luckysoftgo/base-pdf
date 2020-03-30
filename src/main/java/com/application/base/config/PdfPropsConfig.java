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
	private DataPath dataPath=new DataPath();
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
	
	public DataPath getDataPath() {
		return dataPath;
	}
	
	public void setDataPath(DataPath dataPath) {
		this.dataPath = dataPath;
	}
	
	public String getWaterMark() {
		return waterMark;
	}
	
	public void setWaterMark(String waterMark) {
		this.waterMark = waterMark;
	}
	
	/**
	 * 内部关系.
	 */
	public class DataPath{
		/**
		 * 文件路径.
		 */
		private String window;
		/**
		 * 文件路径.
		 */
		private String linux;
		
		public String getWindow() {
			return window;
		}
		
		public void setWindow(String window) {
			this.window = window;
		}
		
		public String getLinux() {
			return linux;
		}
		
		public void setLinux(String linux) {
			this.linux = linux;
		}
	}
}
