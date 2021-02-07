package com.application.base.datas;

/**
 * @author ：孤狼
 * @description: 接收参数对象.
 * @modified By：
 * @version: 1.0.0
 */
public class ReportDataInfo implements java.io.Serializable {
	
	/**
	 * 请求头信息
	 */
	private ReportHeader reportHeader;
	
	/**
	 * 请求体信息
	 */
	private ReportBody reportBody;
	
	public ReportDataInfo() {
	}
	
	public ReportDataInfo(ReportHeader reportHeader, ReportBody reportBody) {
		this.reportHeader = reportHeader;
		this.reportBody = reportBody;
	}
	
	public ReportHeader getReportHeader() {
		return reportHeader;
	}
	
	public void setReportHeader(ReportHeader reportHeader) {
		this.reportHeader = reportHeader;
	}
	
	public ReportBody getReportBody() {
		return reportBody;
	}
	
	public void setReportBody(ReportBody reportBody) {
		this.reportBody = reportBody;
	}
}
