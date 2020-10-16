package com.application.base.datas.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : 孤狼
 * @NAME: FinanceInfo
 * @DESC: FinanceInfo 类设计
 **/
@Builder
@Data
public class FinanceInfo implements Serializable {
	
	/**
	 * 年度
	 */
	private String lastTwo;
    private String lastOne;
    private String thisYear;
	/**
	 * 营业收入
	 */
	private String lastTwoYysr;
    private String lastOneYysr;
    private String thisYearYysr;
	/**
	 * 净利润
	 */
	private String lastTwoJlr;
    private String lastOneJlr;
    private String thisYearJlr;
	/**
	 * 资产总额
	 */
	private String lastTwoZcze;
    private String lastOneZcze;
    private String thisYearZcze;
	/**
	 * 负债合计
	 */
	private String lastTwoFzhj;
    private String lastOneFzhj;
    private String thisYearFzhj;

}
