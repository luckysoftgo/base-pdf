package com.application.base.datas;

import com.application.base.docx4j.vo.DocxDataVO;
import com.application.base.docx4j.vo.DocxImageVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：孤狼
 * @description: 请求体信息
 * @modified By：
 * @version: 1.0.0
 */
public class ReportBody implements Serializable {
	
	/**
	 * 模板id.
	 */
	private String templeteId;
	
	/**
	 * 系统code.
	 */
	private String systemCode;
	
	/**
	 * 子系统code.
	 */
	private String itemSystemCode;
	
	/**
	 * 模板上全局唯一的 key-value 值
	 */
	private Map<String, String> uniqueDataMap;
	
	/**
	 * 模板多表的数据集构成
	 */
	private ArrayList<DocxDataVO> tablesDatas;

	/**
	 * 多图片的配置信息
	 */
	private List<DocxImageVO> imageInfos;
	
	public String getTempleteId() {
		return templeteId;
	}
	
	public void setTempleteId(String templeteId) {
		this.templeteId = templeteId;
	}
	
	public String getSystemCode() {
		return systemCode;
	}
	
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	
	public String getItemSystemCode() {
		return itemSystemCode;
	}
	
	public void setItemSystemCode(String itemSystemCode) {
		this.itemSystemCode = itemSystemCode;
	}
	
	public Map<String, String> getUniqueDataMap() {
		return uniqueDataMap;
	}
	
	public void setUniqueDataMap(Map<String, String> uniqueDataMap) {
		this.uniqueDataMap = uniqueDataMap;
	}
	
	public ArrayList<DocxDataVO> getTablesDatas() {
		return tablesDatas;
	}
	
	public void setTablesDatas(ArrayList<DocxDataVO> tablesDatas) {
		this.tablesDatas = tablesDatas;
	}
	
	public List<DocxImageVO> getImageInfos() {
		return imageInfos;
	}
	
	public void setImageInfos(List<DocxImageVO> imageInfos) {
		this.imageInfos = imageInfos;
	}
}
