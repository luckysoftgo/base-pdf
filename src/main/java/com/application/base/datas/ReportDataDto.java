package com.application.base.datas;


import com.application.base.docx4j.vo.DocxDataVO;
import com.application.base.docx4j.vo.DocxImageVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：孤狼
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
	 * 系统id.
	 */
	private String systemId;
	
	/**
	 * 项目id.
	 */
	private String projectId;
	
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
	 * 单个图片信息的传入
	 */
	private DocxImageVO imageVO;
	
	/**
	 * 多图片的配置信息
	 */
	private List<DocxImageVO> imageInfos;
	
	public ReportDataDto() {
	
	}
	
	public ReportDataDto(String templeteId, String systemId, String projectId, Map<String, String> uniqueDataMap, ArrayList<Map<String, Object>> tableDatas, ArrayList<DocxDataVO> tablesDatas, DocxImageVO imageVO, List<DocxImageVO> imageInfos) {
		this.templeteId = templeteId;
		this.systemId = systemId;
		this.projectId = projectId;
		this.uniqueDataMap = uniqueDataMap;
		this.tableDatas = tableDatas;
		this.tablesDatas = tablesDatas;
		this.imageVO = imageVO;
		this.imageInfos = imageInfos;
	}
	
	public String getTempleteId() {
		return templeteId;
	}
	
	public void setTempleteId(String templeteId) {
		this.templeteId = templeteId;
	}
	
	public String getSystemId() {
		return systemId;
	}
	
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getProjectId() {
		return projectId;
	}
	
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	
	public DocxImageVO getImageVO() {
		return imageVO;
	}
	
	public void setImageVO(DocxImageVO imageVO) {
		this.imageVO = imageVO;
	}
	
	public List<DocxImageVO> getImageInfos() {
		return imageInfos;
	}
	
	public void setImageInfos(List<DocxImageVO> imageInfos) {
		this.imageInfos = imageInfos;
	}
}
