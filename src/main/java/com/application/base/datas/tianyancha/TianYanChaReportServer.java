package com.application.base.datas.tianyancha;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: TianYanChaReportServer
 * @DESC: TianYanChaReportServer 类设计
 **/
@Slf4j
@Component
public class TianYanChaReportServer {

	@Autowired
	private TianYanChaCreditDataProducer creditDataProducer;
	
	@Autowired
	private TianYanChaCrAuthDataProducer crAuthDataProducer;
	
	/**
	 * 获取数据集合
	 * @param creditCode
	 * @param companyName
	 */
	public Map<String,Object> getCrAuthReportData(String creditCode, String companyName) {
		Map<String,Object> finalMap = new HashMap<>(16);
		String tycCompanyId = crAuthDataProducer.getTycCompanyId(creditCode, companyName);
		if (StringUtils.isEmpty(tycCompanyId)){
			throw new RuntimeException("调用天眼查服务出错,错误信息详细看日志输出");
		}
		Map<String, Object> baseInfo = crAuthDataProducer.getTycCompanyBasicInfo(creditCode, tycCompanyId, companyName);
		baseInfo.put("companyName",baseInfo.get("name"));
		finalMap.put("baseInfo", baseInfo);
		Map<String,Object> reportDatas = new HashMap<>();
		reportDatas.put("出资人及出资信息", crauthEntChuZi(creditCode,tycCompanyId,companyName));
		reportDatas.put("对外投资信息", crauthEntTouZi(creditCode,tycCompanyId,companyName));
		reportDatas.put("风险信息", crauthEntFengXian(creditCode,tycCompanyId,companyName));
		reportDatas.put("知识产权信息", crauthEntChanQuan(creditCode,tycCompanyId,companyName));
		reportDatas.put("经营信息", crauthEntJingYing(creditCode,tycCompanyId,companyName));
		finalMap.put("reportDatas",reportDatas);
		return finalMap;
	}
	
	private List<Map<String,Object>> crauthEntChuZi(String creditCode, String tycCompanyId, String companyName) {
		List<Map<String,Object>> dataList = new ArrayList<>();
		dataList.add(crAuthDataProducer.getTycCompanyHolderInfo(creditCode,tycCompanyId,companyName,1));
		return dataList;
	}
	
	private List<Map<String,Object>> crauthEntTouZi(String creditCode, String tycCompanyId, String companyName) {
		List<Map<String,Object>> dataList = new ArrayList<>();
		dataList.add(crAuthDataProducer.getTycCompanyTzanliInfo(creditCode,tycCompanyId,companyName,1));
		return dataList;
	}
	
	private List<Map<String,Object>> crauthEntFengXian(String creditCode, String tycCompanyId, String companyName) {
		List<Map<String,Object>> dataList = new ArrayList<>();
		dataList.add(crAuthDataProducer.getTycCompanyLawSuitInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(crAuthDataProducer.getTycCompanyCreditInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(crAuthDataProducer.getTycCompanyEquityInfo(creditCode,tycCompanyId,companyName,1));
		return dataList;
	}
	
	private List<Map<String,Object>> crauthEntChanQuan(String creditCode, String tycCompanyId, String companyName) {
		List<Map<String,Object>> dataList = new ArrayList<>();
		dataList.add(crAuthDataProducer.getTycCompanyTradeMark(creditCode,tycCompanyId,companyName,1));
		dataList.add(crAuthDataProducer.getTycCompanyPatentsInfo(creditCode,tycCompanyId,companyName,1));
		return dataList;
	}
	
	private List<Map<String,Object>> crauthEntJingYing(String creditCode, String tycCompanyId, String companyName) {
		List<Map<String,Object>> dataList = new ArrayList<>();
		dataList.add(crAuthDataProducer.getTycCompanyBiddingInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(crAuthDataProducer.getTycCompanyExcitationInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(crAuthDataProducer.getTycCompanyCertificateInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(crAuthDataProducer.getTycCompanyLicenseInfo(creditCode,tycCompanyId,companyName,1));
		return dataList;
	}
	
	/**
	 * 获取数据集合.
	 * @param creditCode
	 * @param companyName
	 * @return
	 */
	public Map<String,Object> getCreditReportData(String creditCode,String companyName){
		Map<String,Object> finalMap = new HashMap<>(16);
		String tycCompanyId = creditDataProducer.getTycCompanyId(creditCode, companyName);
		if (StringUtils.isEmpty(tycCompanyId)){
			throw new RuntimeException("调用天眼查服务出错,错误信息详细看日志输出");
		}
		Map<String, Object> baseInfo = creditDataProducer.getTycCompanyBasicInfo(creditCode, tycCompanyId, companyName);
		baseInfo.put("companyName",baseInfo.get("name"));
		finalMap.put("baseInfo", baseInfo);
		Map<String,Object> reportDatas = new HashMap<>();
		reportDatas.put("企业登记信息", creditEntDengji(creditCode,tycCompanyId,companyName));
		reportDatas.put("法人高管信息", creditEntGaoguan(creditCode,tycCompanyId,companyName));
		reportDatas.put("监管信息", creditEntJianGuan(creditCode,tycCompanyId,companyName));
		reportDatas.put("司法信息", creditEntSifa(creditCode,tycCompanyId,companyName));
		reportDatas.put("认定名录", creditEntMingLu(creditCode,tycCompanyId,companyName));
		reportDatas.put("知识产权", creditEntChanQuan(creditCode,tycCompanyId,companyName));
		finalMap.put("reportDatas",reportDatas);
		return finalMap;
	}
	
	private List<Map<String,Object>> creditEntChanQuan(String creditCode, String tycCompanyId, String companyName) {
		List<Map<String,Object>> dataList = new ArrayList<>();
		dataList.add(creditDataProducer.getTycCompanyPatentsInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyTradeMark(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyCopyRegInfo(creditCode,tycCompanyId,companyName,1));
		return dataList;
	}
	
	private List<Map<String,Object>> creditEntMingLu(String creditCode, String tycCompanyId, String companyName) {
		List<Map<String,Object>> dataList = new ArrayList<>();
		dataList.add(creditDataProducer.getTycCompanyBiddingInfo(creditCode,tycCompanyId,companyName,1));
		return dataList;
	}
	
	private List<Map<String,Object>> creditEntSifa(String creditCode, String tycCompanyId, String companyName) {
		List<Map<String,Object>> dataList = new ArrayList<>();
		dataList.add(creditDataProducer.getTycCompanyCaseInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyCourtAnnouncement(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyLawSuitInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyZhixingInfo(creditCode,tycCompanyId,companyName,1));
		return dataList;
	}
	
	private List<Map<String,Object>> creditEntJianGuan(String creditCode, String tycCompanyId, String companyName) {
		List<Map<String,Object>> dataList = new ArrayList<>();
		dataList.add(creditDataProducer.getTycCompanyPunishmentInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyAbnormalInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyLicenseInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyCreditInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyRewardInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyExcitationInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyDishonestInfo(creditCode,tycCompanyId,companyName,1));
		return dataList;
	}
	
	private List<Map<String,Object>> creditEntGaoguan(String creditCode, String tycCompanyId, String companyName) {
		List<Map<String,Object>> dataList = new ArrayList<>();
		dataList.add(creditDataProducer.getTycCompanyStaffInfo(creditCode,tycCompanyId,companyName,1));
		return dataList;
	}
	
	private List<Map<String,Object>> creditEntDengji(String creditCode, String tycCompanyId, String companyName) {
		List<Map<String,Object>> dataList = new ArrayList<>();
		dataList.add(creditDataProducer.getTycCompanyHolderInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyBranchInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyTzanliInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyChangeInfo(creditCode,tycCompanyId,companyName,1));
		dataList.add(creditDataProducer.getTycCompanyCertificateInfo(creditCode,tycCompanyId,companyName,1));
		return dataList;
	}
	
	
}
