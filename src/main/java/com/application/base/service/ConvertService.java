package com.application.base.service;

import com.application.base.datas.ReportBody;
import com.application.base.datas.ReportHeader;

import java.util.Map;

/**
 * @author ：孤狼
 * @description: 转换接口
 * @modified By：
 * @version: 1.0.0
 */
public interface ConvertService {
	
	/**
	 * 校验是否可以调用服务生成.
	 *
	 * @param reportHeader 校验信息
	 * @return
	 */
	public Boolean buildReportCheck(ReportHeader reportHeader);
	
	/**
	 * 占位符替换
	 *
	 * @param reportBody 生成报告的数据集合
	 * @return
	 */
	public Map<String, String> wordSymbol2files(ReportBody reportBody);
	
	/**
	 * 占位符 + 表格或图片 替换
	 *
	 * @param reportBody 生成报告的数据集合
	 * @return
	 */
	public Map<String, String> automaticInfo2files(ReportBody reportBody);
	
}
