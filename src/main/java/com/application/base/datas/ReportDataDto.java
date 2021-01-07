package com.application.base.datas;

import com.application.base.docx4j.vo.DocxDataVO;
import com.application.base.docx4j.vo.DocxImgVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：admin
 * @description: 接收参数对象.
 * @modified By：
 * @version: 1.0.0
 */
public class ReportDataDto implements java.io.Serializable {
	
	/**
	 * 模板id.
	 */
	private String templeteId;
	
	/**
	 * 模板上全局唯一的 key-value 值
	 */
	private Map<String, String> uniqueDataMap;
	
	/**
	 * 模板单表的数据集构成
	 */
	private ArrayList<Map<String, Object>> tableDatas;
	
	/**
	 * 模板多表的数据集构成
	 */
	private ArrayList<DocxDataVO> tablesDatas;
	
	/**
	 * 要查找的模板中的文本
	 */
	private String searchText;
	
	/**
	 * 要查找的模板中图片的url
	 */
	private String imageUrl;
	
	/**
	 * 多图片的配置信息
	 */
	private List<DocxImgVO> imgInfos;
	
	/**
	 * 生成图片的 json 串
	 */
	private String imageJson;
	
	/**
	 * 生成图片的 json 串
	 */
	private List<String> imagesJsons;
	
	
	public String getTempleteId() {
		return templeteId;
	}
	
	public void setTempleteId(String templeteId) {
		this.templeteId = templeteId;
	}
	
	public Map<String, String> getUniqueDataMap() {
		return uniqueDataMap;
	}
	
	public void setUniqueDataMap(Map<String, String> uniqueDataMap) {
		this.uniqueDataMap = uniqueDataMap;
	}
	
	public ArrayList<Map<String, Object>> getTableDatas() {
		return tableDatas;
	}
	
	public void setTableDatas(ArrayList<Map<String, Object>> tableDatas) {
		this.tableDatas = tableDatas;
	}
	
	public ArrayList<DocxDataVO> getTablesDatas() {
		return tablesDatas;
	}
	
	public void setTablesDatas(ArrayList<DocxDataVO> tablesDatas) {
		this.tablesDatas = tablesDatas;
	}
	
	public String getSearchText() {
		return searchText;
	}
	
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public List<DocxImgVO> getImgInfos() {
		return imgInfos;
	}
	
	public void setImgInfos(List<DocxImgVO> imgInfos) {
		this.imgInfos = imgInfos;
	}
	
	public String getImageJson() {
		return imageJson;
	}
	
	public void setImageJson(String imageJson) {
		this.imageJson = imageJson;
	}
	
	public List<String> getImagesJsons() {
		return imagesJsons;
	}
	
	public void setImagesJsons(List<String> imagesJsons) {
		this.imagesJsons = imagesJsons;
	}
}
