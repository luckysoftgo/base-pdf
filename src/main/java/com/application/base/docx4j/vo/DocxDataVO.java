package com.application.base.docx4j.vo;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author ：孤狼
 * @description: 数据存储的对象
 * https://blog.csdn.net/u011837804/article/details/106666949/
 * https://www.chendd.cn/information/viewInformation/other/250.a
 * https://www.deathearth.com/1028.html
 * @modified By：
 * @version: 1.0.0
 */
public class DocxDataVO {
	
	/**
	 * 数据对应的下标项
	 */
	private Integer tableIndex;
	
	/**
	 * 对应的模块key
	 */
	private String moduleKey;
	
	/**
	 * 数据集合
	 */
	private ArrayList<Map<String, Object>> docxDataList;
	
	public DocxDataVO() {
	}
	
	public DocxDataVO(String moduleKey, ArrayList<Map<String, Object>> docxDataList) {
		this.moduleKey = moduleKey;
		this.docxDataList = docxDataList;
	}
	
	public DocxDataVO(Integer tableIndex, ArrayList<Map<String, Object>> docxDataList) {
		this.tableIndex = tableIndex;
		this.docxDataList = docxDataList;
	}
	
	public Integer getTableIndex() {
		return tableIndex;
	}
	
	public void setTableIndex(Integer tableIndex) {
		this.tableIndex = tableIndex;
	}
	
	public String getModuleKey() {
		return moduleKey;
	}
	
	public void setModuleKey(String moduleKey) {
		this.moduleKey = moduleKey;
	}
	
	public ArrayList<Map<String, Object>> getDocxDataList() {
		return docxDataList;
	}
	
	public void setDocxDataList(ArrayList<Map<String, Object>> docxDataList) {
		this.docxDataList = docxDataList;
	}
}
