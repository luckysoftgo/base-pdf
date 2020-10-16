package com.application.base.datas.tianyancha;

import com.alibaba.fastjson.JSON;
import com.application.base.datas.dto.TycRelationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : 孤狼
 * @NAME: TianYanChaCrAuthDataProducer
 * @DESC: TianYanChaCrAuthDataProducer 类设计
 **/
@Slf4j
@Component
public class TianYanChaCrAuthDataProducer extends TianYanChaDataProducer {
	
	/**
	 * 基本信息--出资人和出资信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	@Override
	public Map<String, Object> getTycCompanyHolderInfo(String creditCode, String tycCompanyId, String companyName, Integer pageNum){
		String businessType = TianYanChaConst.HOLDER_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/holder";
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("GDMC","出资人名称");
		metadata.put("GDLX","出资人类型");
		metadata.put("RJCZ","认缴出资（万元）");
		metadata.put("CZZB","岀资占比");
		metadata.put("DJRQ","认缴出资日期");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerHolder(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","出资人及出资信息");
		instanceDataMap.put("tagId","e07cb622dc4a434dbfc3f00b0e2b8ef4");
		instanceDataMap.put("tagName","出资人及出资信息");
		instanceDataMap.put("instance",instanceMap);
		
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 对外投资信息--对外投资
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	@Override
	public Map<String, Object> getTycCompanyTzanliInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.TOUZI_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/findTzanli";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("BTZQYMC","被投资企业名称");
		metadata.put("CREDITCODE","统一社会信用代码");
		metadata.put("COMPANYTYPE","企业类型");
		metadata.put("JYZT","经营状态");
		metadata.put("ZCZB","注册资本（万元）");
		metadata.put("CZBL","出资比例");
		metadata.put("FDNAME","法定代表人（姓名）");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerTzanli(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","对外投资信息");
		instanceDataMap.put("tagId","b6754c69d6f84c78b0d4fb1c97ffb5f6");
		instanceDataMap.put("tagName","对外投资信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 风险信息-- 法律诉讼信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	@Override
	public Map<String, Object> getTycCompanyLawSuitInfo(String creditCode, String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.LAWSUIT_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/lawSuit";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("KTRQ","开庭日期");
		metadata.put("AJMC","案件名称");
		metadata.put("AH","案号");
		metadata.put("AY","案由");
		metadata.put("AJSF","案件身份");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerLawSuitInfo(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","风险信息");
		instanceDataMap.put("tagId","c325e566c6d648d4b1874a06qhtd3245d");
		instanceDataMap.put("tagName","法律诉讼");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 风险信息--行政处罚
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	@Override
	public Map<String, Object> getTycCompanyPunishmentInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.PUNISHMENT_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/punishmentInfo";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("CFRQ","处罚日期");
		metadata.put("JDWSH","决定文书号");
		metadata.put("CFLX","处罚类型");
		metadata.put("CFSY","处罚事由");
		metadata.put("CFJG","检查实施机关");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerPunishMent(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","风险信息");
		instanceDataMap.put("tagId","709de3ea22444116856e2bba796c2c4c");
		instanceDataMap.put("tagName","行政处罚");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 风险信息--股权出质
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyEquityInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.EQUITY_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/equityInfo";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("SLDJRQ","股权出质设立登记日期");
		metadata.put("DJBH","登记编号");
		metadata.put("CZRMC","出质人名称");
		metadata.put("ZQRMC","质权人名称");
		metadata.put("GQCZE","股权出质额");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerEquityInfo(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","风险信息");
		instanceDataMap.put("tagId","709de3ea22444116856e2bba35971d");
		instanceDataMap.put("tagName","股权出质");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 *股权出质
	 * @param data
	 * @param dataMap
	 */
	private void handlerEquityInfo(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("SLDJRQ",NULLL_STR);
			tmpMap.put("DJBH",NULLL_STR);
			tmpMap.put("CZRMC",NULLL_STR);
			tmpMap.put("ZQRMC",NULLL_STR);
			tmpMap.put("GQCZE",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("SLDJRQ",NULLL_STR);
				tmpMap.put("DJBH",NULLL_STR);
				tmpMap.put("CZRMC",NULLL_STR);
				tmpMap.put("ZQRMC",NULLL_STR);
				tmpMap.put("GQCZE",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("SLDJRQ",handlerDate(Objects.toString(map.get("regDate"),"")));
				tmpMap.put("DJBH",Objects.toString(map.get("regNumber"),""));
				tmpMap.put("CZRMC",Objects.toString(map.get("pledgor"),""));
				tmpMap.put("ZQRMC",Objects.toString(map.get("pledgee"),""));
				tmpMap.put("GQCZE",Objects.toString(map.get("equityAmount"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 监管信息
	 * @param data
	 * @param dataMap
	 */
	@Override
	protected void handlerPunishMent(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("CFRQ",NULLL_STR);
			tmpMap.put("JDWSH",NULLL_STR);
			tmpMap.put("CFLX",NULLL_STR);
			tmpMap.put("CFSY",NULLL_STR);
			tmpMap.put("CFJG",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("CFRQ",NULLL_STR);
				tmpMap.put("JDWSH",NULLL_STR);
				tmpMap.put("CFLX",NULLL_STR);
				tmpMap.put("CFSY",NULLL_STR);
				tmpMap.put("CFJG",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("CFRQ", Objects.toString(map.get("decisionDate"), ""));
				tmpMap.put("JDWSH", Objects.toString(map.get("punishNumber"), ""));
				tmpMap.put("CFLX", Objects.toString(map.get("type"), ""));
				tmpMap.put("CFSY", Objects.toString(map.get("remarks"), ""));
				tmpMap.put("CFJG", Objects.toString(map.get("departmentName"), ""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 法律诉讼信息
	 * @param data
	 * @param dataMap
	 */
	@Override
	protected void handlerLawSuitInfo(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("KTRQ",NULLL_STR);
			tmpMap.put("AJMC",NULLL_STR);
			tmpMap.put("AH",NULLL_STR);
			tmpMap.put("AY",NULLL_STR);
			tmpMap.put("AJSF",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("KTRQ",NULLL_STR);
				tmpMap.put("AJMC",NULLL_STR);
				tmpMap.put("AH",NULLL_STR);
				tmpMap.put("AY",NULLL_STR);
				tmpMap.put("AJSF",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("KTRQ",handlerDate(Objects.toString(map.get("eventTime"),"")));
				tmpMap.put("AJMC",Objects.toString(map.get("title"),""));
				tmpMap.put("AH",Objects.toString(map.get("caseno"),""));
				tmpMap.put("AY",Objects.toString(map.get("plaintiffsApp"),""));
				tmpMap.put("AJSF",Objects.toString(map.get("casetype"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 对外投资信息
	 * @param data
	 * @param dataMap
	 */
	@Override
	protected void handlerTzanli(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("BTZQYMC",NULLL_STR);
			tmpMap.put("CREDITCODE",NULLL_STR);
			tmpMap.put("COMPANYTYPE",NULLL_STR);
			tmpMap.put("JYZT",NULLL_STR);
			tmpMap.put("ZCZB",NULLL_STR);
			tmpMap.put("CZBL",NULLL_STR);
			tmpMap.put("FDNAME",NULLL_STR);
			data.add(tmpMap);
		}else{
			String reMap = Objects.toString(dataMap.get("items"),"");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap,List.class);
			if (itemMap==null || itemMap.size()==0){
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("BTZQYMC",NULLL_STR);
				tmpMap.put("CREDITCODE",NULLL_STR);
				tmpMap.put("COMPANYTYPE",NULLL_STR);
				tmpMap.put("JYZT",NULLL_STR);
				tmpMap.put("ZCZB",NULLL_STR);
				tmpMap.put("CZBL",NULLL_STR);
				tmpMap.put("FDNAME",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("CREDITCODE",Objects.toString(map.get("creditCode"),""));
				tmpMap.put("COMPANYTYPE",Objects.toString(map.get("hangye1"),""));
				tmpMap.put("ZCZB",Objects.toString(map.get("register"),""));
				tmpMap.put("BTZQYMC",Objects.toString(map.get("company"),""));
				tmpMap.put("FDNAME",Objects.toString(map.get("legalPersonName"),""));
				tmpMap.put("CZBL",Objects.toString(map.get("percent"),""));
				tmpMap.put("JYZT",Objects.toString(map.get("regStatus"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 获取出资人及出资信息
	 * @param data
	 * @param dataMap
	 */
	@Override
	protected void handlerHolder(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("GDMC",NULLL_STR);
			tmpMap.put("GDLX",NULLL_STR);
			tmpMap.put("CZZB",NULLL_STR);
			tmpMap.put("RJCZ",NULLL_STR);
			tmpMap.put("DJRQ",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("GDMC",NULLL_STR);
				tmpMap.put("GDLX",NULLL_STR);
				tmpMap.put("CZZB",NULLL_STR);
				tmpMap.put("RJCZ",NULLL_STR);
				tmpMap.put("DJRQ",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				String GDMC = Objects.toString(map.get("name"), "");
				String capital = Objects.toString(map.get("capital"), "");
				List<Map<String, Object>> capitalMap = (List<Map<String, Object>>) JSON.parseObject(capital, List.class);
				String CZZB = "", RJCZ = "", DJRQ = "",GDLX="";
				if (capitalMap != null && capitalMap.size() > 0) {
					Map<String, Object> infoMap = capitalMap.get(0);
					CZZB = Objects.toString(infoMap.get("percent"), "");
					RJCZ = Objects.toString(infoMap.get("amomon"), "");
					GDLX = Objects.toString(infoMap.get("type"),"");
					DJRQ = handlerDate(Objects.toString(infoMap.get("time"), ""));
				}
				tmpMap.put("GDMC", GDMC);
				tmpMap.put("GDLX", GDLX);
				tmpMap.put("CZZB", CZZB);
				tmpMap.put("RJCZ", RJCZ);
				tmpMap.put("DJRQ", DJRQ);
				data.add(tmpMap);
			}
		}
	}
}
