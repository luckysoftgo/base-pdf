package com.application.base.datas.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author : 孤狼
 * @NAME: TycRelationInfo
 * @DESC: TycRelationInfo 类设计
 **/
@Data
@Builder
public class TycRelationInfo {
	
	private Long id;
	
	private String tycId;
	
	private String creditCode;
	
	private String companyName;
	
	private String businessType;
	
	private String requestUrl;
	
	private String dataJson;
	
	public TycRelationInfo(){}
	
	public TycRelationInfo(String tycId, String creditCode, String companyName, String businessType, String requestUrl
			, String dataJson) {
		this.tycId = tycId;
		this.creditCode = creditCode;
		this.companyName = companyName;
		this.businessType = businessType;
		this.requestUrl = requestUrl;
		this.dataJson = dataJson;
	}
	
	public TycRelationInfo(Long id, String tycId, String creditCode, String companyName, String businessType,
	                       String requestUrl, String dataJson) {
		this.id = id;
		this.tycId = tycId;
		this.creditCode = creditCode;
		this.companyName = companyName;
		this.businessType = businessType;
		this.requestUrl = requestUrl;
		this.dataJson = dataJson;
	}
}
