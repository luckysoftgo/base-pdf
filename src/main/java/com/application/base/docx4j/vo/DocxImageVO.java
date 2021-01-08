package com.application.base.docx4j.vo;

/**
 * @author ：admin
 * @description: 图片类型插入word模板
 * https://my.oschina.net/wumiaowang/blog/828293
 * https://blog.csdn.net/qq_40868449/article/details/103541381
 * https://blog.csdn.net/qq_31204765/article/details/51870238
 * https://www.cnblogs.com/cxxjohnson/p/7899256.html
 * @modified By：
 * @version: 1.0.0
 */
public class DocxImageVO {
	
	/**
	 * 要查找的文本内容
	 */
	private String searchText;
	
	/**
	 * echarts生成图的json串信息.
	 */
	private String echartsOptions;
	
	/**
	 * echarts生成图的图片名称.
	 */
	private String imageName;
	
	/**
	 * 要插入的图片的绝对位置
	 */
	private String imagePath;
	
	public DocxImageVO() {
	}
	
	public DocxImageVO(String searchText, String imagePath) {
		this.searchText = searchText;
		this.imagePath = imagePath;
	}
	
	public DocxImageVO(String searchText, String echartsOptions, String imageName, String imagePath) {
		this.searchText = searchText;
		this.echartsOptions = echartsOptions;
		this.imageName = imageName;
		this.imagePath = imagePath;
	}
	
	public String getSearchText() {
		return searchText;
	}
	
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public String getEchartsOptions() {
		return echartsOptions;
	}
	
	public void setEchartsOptions(String echartsOptions) {
		this.echartsOptions = echartsOptions;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
