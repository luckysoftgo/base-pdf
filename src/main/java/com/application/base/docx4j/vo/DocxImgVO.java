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
public class DocxImgVO {
	
	/**
	 * 要查找的文本内容
	 */
	private String searchText;
	
	/**
	 * 要插入的图片的绝对位置
	 */
	private String imgPath;
	
	public DocxImgVO() {
	}
	
	public DocxImgVO(String searchText, String imgPath) {
		this.searchText = searchText;
		this.imgPath = imgPath;
	}
	
	public String getSearchText() {
		return searchText;
	}
	
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public String getImgPath() {
		return imgPath;
	}
	
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
}
