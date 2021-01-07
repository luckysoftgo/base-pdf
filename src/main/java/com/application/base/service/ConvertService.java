package com.application.base.service;

import com.application.base.docx4j.vo.DocxDataVO;
import com.application.base.docx4j.vo.DocxImgVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：admin
 * @description: 转换接口
 * @modified By：
 * @version: 1.0.0
 */
public interface ConvertService {
	
	/**
	 * 文件转换
	 *
	 * @param templeteId    模板id
	 * @param uniqueDataMap 要替换的字符
	 * @return
	 */
	public Map<String, String> wordSymbol2files(String templeteId, Map<String, String> uniqueDataMap);
	
	/**
	 * 文件转换
	 *
	 * @param templeteId    模板id
	 * @param uniqueDataMap 要替换的字符
	 * @param tableDatas    表上的数据
	 * @return
	 */
	public Map<String, String> wordTable2files(String templeteId, Map<String, String> uniqueDataMap, ArrayList<Map<String, Object>> tableDatas);
	
	
	/**
	 * 文件转换
	 *
	 * @param templeteId    模板id
	 * @param uniqueDataMap 要替换的字符
	 * @param tablesDatas   表上的数据(动态数据)
	 * @return
	 */
	public Map<String, String> wordTables2files(String templeteId, Map<String, String> uniqueDataMap, ArrayList<DocxDataVO> tablesDatas);
	
	/**
	 * 文件转换
	 *
	 * @param templeteId    模板id.
	 * @param uniqueDataMap 数据集合.
	 * @param searchText    图片开始插入的位置.
	 * @param imageUrl      图片的绝对路径.
	 * @return
	 */
	public Map<String, String> wordImg2files(String templeteId, Map<String, String> uniqueDataMap, String searchText, String imageUrl);
	
	/**
	 * 文件转换
	 *
	 * @param templeteId    模板id.
	 * @param uniqueDataMap 数据集合.
	 * @param imgInfos      图片信息对.
	 * @return
	 */
	public Map<String, String> wordImgs2files(String templeteId, Map<String, String> uniqueDataMap, List<DocxImgVO> imgInfos);
	
}
