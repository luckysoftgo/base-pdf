package com.application.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 孤狼
 * @NAME: PropsConfig
 * @DESC: PropsConfig类设计
 **/
@Data
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
	 * 字体的路径.
	 */
	private String fontPath;
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
	/**
	 * 天眼查的token
	 */
	private String tycToken;
	/**
	 * 基础调用地址
	 */
	private String tycBasicUrl;
	/**
	 * 选择数据源
	 */
	private String dataSource;
}
