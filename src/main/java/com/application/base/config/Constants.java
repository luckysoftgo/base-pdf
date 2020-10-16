package com.application.base.config;

/**
 * @author : 孤狼
 * @NAME: Constants
 * @DESC: Constants 类设计
 **/
public class Constants {
	
	/**
	 * crauth
	 */
    public static final String  REPORT_TYPE_CRAUTH = "crauth";
	/**
	 * credit
	 */
	public static final String  REPORT_TYPE_CREDIT = "credit";
	
	/**
	 * 需要生成CR报告的缓存数据KEY
	 */
	public static final String CRAUTH_REPORT_KEY = "dataCrAuth";
	/**
	 * 需要生成信用报告的缓存数据KEY
	 */
	public static final String CREDIT_REPORT_KEY = "dataCredit";
	
	/**
	 * 征信报告模板文件
	 */
	public static final String CREDIT_REPORT_TEMPLATE_NAME = "compCredit.ftl";
	/**
	 * CR报告模板文件
	 */
	public static final String CRAUTH_REPORT_TEMPLATE_NAME = "compCrAuth.ftl";
	
	/**
	 * CR报告模板文件中财务概要信息KEY
	 */
	public static final String CRAUTH_REPORT_TEMPLATE_FININFO_KEY = "finInfo";
	
	/**
	 * 定义调用天眼查返回结果code
	 */
	public static final Integer SUCCESS_CODE = 200;
	public static final Integer HTTP_REQ_CODE = 300;
	public static final Integer HTTP_ERR_CODE = 500;
 
}



