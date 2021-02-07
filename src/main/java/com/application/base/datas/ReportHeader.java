package com.application.base.datas;

import java.io.Serializable;

/**
 * @author ：孤狼
 * @description: 请求头信息
 * @modified By：
 * @version: 1.0.0
 */
public class ReportHeader implements Serializable {
	
	/**
	 * 允许访问服务的认证码
	 */
	private String authCode;
	
	/**
	 * 系统编码
	 */
	private String sysCode;
	
	/**
	 * 系统的名称
	 */
	private String sysName;
	
	/**
	 * 应用所在的IP
	 */
	private String appIp;
	
	/**
	 * 应用所在的端口
	 */
	private String appPort;
	
	/**
	 * 系统负责人Id
	 */
	private String systemUser;
	
	/**
	 * 系统负责人名称
	 */
	private String systemName;
	
	public String getAuthCode() {
		return authCode;
	}
	
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	public String getSysCode() {
		return sysCode;
	}
	
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	public String getSysName() {
		return sysName;
	}
	
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	public String getAppIp() {
		return appIp;
	}
	
	public void setAppIp(String appIp) {
		this.appIp = appIp;
	}
	
	public String getAppPort() {
		return appPort;
	}
	
	public void setAppPort(String appPort) {
		this.appPort = appPort;
	}
	
	public String getSystemUser() {
		return systemUser;
	}
	
	public void setSystemUser(String systemUser) {
		this.systemUser = systemUser;
	}
	
	public String getSystemName() {
		return systemName;
	}
	
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
}
