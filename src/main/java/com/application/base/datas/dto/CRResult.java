package com.application.base.datas.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import java.io.Serializable;

/**
 *  @author : 孤狼
 */
@Data
public class CRResult implements Serializable{

	private String A1;
	private String A1Desc="企业规模评分";
	private String A1Level;
	private String A1LevelDesc;
	
	private String A7;
	private String A7Desc="企业背景评分";
	private String A7Level;
	private String A7LevelDesc;
	
	private String A8;
	private String A8Desc="财务状况评分";
	private String A8Level;
	private String A8LevelDesc;
	
	private String A9;
	private String A9Desc="行业展望评分";
	private String A9Level;
	private String A9LevelDesc;
	
	private String OA;
	private String OADesc="信用级别";
	private String OALevel;
	private String OALevelDesc;
	
	private String AA;
	private String AADesc="目标企业信用评分";
	private String AALevel;
	private String AALevelDesc;
	
	private String crLevel;
	private String crAmount;
	private String crOriginalAmt;
	private String crRuleAmt;
	private String crLevelDesc="授信额度:万元";
	private String crSuggest;
	
	private String companyName;
	private String financeDate;
	private String desc;
	private String code;
	/**
	 * 评分时间.
	 */
	private String crTime;
	/**
	 * 评级的存储的主键.
	 */
	@JsonSerialize(using= ToStringSerializer.class)
	private Long evaluteId;
	
}
