package com.application.base.datas.tianyancha;

import com.alibaba.fastjson.JSON;
import com.application.base.config.Constants;
import com.application.base.config.PdfPropsConfig;
import com.application.base.datas.HttpConnUtil;
import com.application.base.datas.dto.TycRelationInfo;
import com.application.base.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : 孤狼
 * @NAME: TianYanChaDataProducer
 * @DESC: TianYanChaDataProducer 类设计
 **/
@Slf4j
@Component
public class TianYanChaDataProducer {
	
	@Autowired
	protected PdfPropsConfig propsConfig;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	/**
	 * 错误信息
	 */
	protected static final Map<String, String> errMap = new HashMap<>(16);
	/**
	 * 对象
	 */
	protected static final String NULLL_STR ="-";
	
	/**
	 * 错误信息总结.
	 */
	static {
		errMap.put("300000", "无数据");
		errMap.put("300001", "请求失败");
		errMap.put("300002", "账号失效");
		errMap.put("300003", "账号过期");
		errMap.put("300004", "访问频率过快");
		errMap.put("300005", "无权限访问此api");
		errMap.put("300006", "余额不足");
		errMap.put("300007", "剩余次数不足");
		errMap.put("300008", "缺少必要参数");
		errMap.put("300009", "账号信息有误");
		errMap.put("300010", "URL不存在");
	}
	
	/**
	 * url
	 * @return
	 */
	protected String getBasicUrl() {
		return propsConfig.getTycBasicUrl()==null ? "http://open.api.tianyancha.com/services" : propsConfig.getTycBasicUrl();
	}
	
	/**
	 * token
	 * @return
	 */
	protected String getToken() {
		return propsConfig.getTycToken()==null ? "4c00c062-3a13-4b3e-9e35-7ddce7f196717" : propsConfig.getTycToken();
	}
	
	/**
	 * 获取天眼查信息
	 * @param url
	 * @param params
	 * @return
	 */
	protected Map<String,Object> getTianYanChaInfo(String url,Map<String, Object> params){
		String token = getToken();
		url = url + HttpConnUtil.convertGetStrParamter(params);
		log.info("请求天眼查的地址是:{}",url);
		Map<String,Object> dataMap = new HashMap<>(16);
		//返回结果.
		Map<String,Object> resultMap = HttpConnUtil.getRequest(url,token,null);
		Integer code = (Integer) resultMap.get("code");
		if (code.intValue()== Constants.SUCCESS_CODE){
			String result = (String) resultMap.get("data");
			log.info("天眼查返回结果信息：" + result);
			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> returnMap = JSON.parseObject(result, Map.class);
				String errorCcode = String.valueOf(returnMap.get("error_code"));
				if (errMap.containsKey(errorCcode)) {
					log.info("结果状态为：" + errMap.get(errorCcode));
				}else{
					dataMap = (Map<String, Object>) returnMap.get("result");
				}
			} catch (Exception e) {
				log.error("解析天眼查获得的结果出现异常,异常信息是:{}",e.getMessage());
			}
		}
		return dataMap;
	}
	
	/**
	 * 基本信息--查询企业的id
	 * @param companyName
	 * @return
	 */
	public String getTycCompanyId(String creditCode,String companyName){
		String businessType = TianYanChaConst.TIANYANCHA_ID;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		if (relationInfo!=null){
			return relationInfo.getTycId();
		}
		String url = getBasicUrl() + "/v4/open/searchV2";
		//返回结果
		Map<String, Object> params = new HashMap<>();
		params.put("word", companyName);
		Map<String,Object> dataMap = getTianYanChaInfo(url,params);
		if (dataMap.isEmpty()){
			log.info("通过企业名称：{}没有找到企业id信息",companyName);
			return "";
		}
		String reMap = Objects.toString(dataMap.get("items"),"");
		List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap,List.class);
		String tycCompanyId = "0";
		if (itemMap!=null && itemMap.size()>0){
			for (Map<String, Object> map : itemMap) {
				String company = Objects.toString(map.get("name"),"");
				company = company.replaceAll("<em>", "").replaceAll("</em>", "");
				// 与企业名称进行比较
				if (company.trim().equalsIgnoreCase(companyName)) {
					tycCompanyId = Objects.toString(map.get("id"),"");
				}
			}
		}
		if(!"0".equalsIgnoreCase(tycCompanyId)){
			addRecordInfo(TycRelationInfo.builder().tycId(tycCompanyId).creditCode(creditCode).companyName(companyName).businessType(businessType).requestUrl(url).dataJson(JSON.toJSONString(dataMap)).build());
		}
		return tycCompanyId;
	}
	
	/**
	 * 基本信息--获取企业基本信息
	 * @param tycCompanyId
	 * @param companyName
	 * @return
	 */
	public Map<String, Object> getTycCompanyBasicInfo(String creditCode,String tycCompanyId,String companyName){
		String businessType = TianYanChaConst.BASE_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String, Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/baseinfoV2";
			//返回结果
			Map<String, Object> params = new HashMap<>();
			params.put("id", tycCompanyId);
			params.put("name", companyName);
			dataMap = getTianYanChaInfo(url, params);
			if (dataMap.size()>0){
				addRecordInfo(TycRelationInfo.builder().tycId(tycCompanyId).creditCode(creditCode).companyName(companyName).businessType(businessType).requestUrl(url).dataJson(JSON.toJSONString(dataMap)).build());
			}
		}
		if (dataMap.isEmpty()){
			log.info("通过企业Id：{}和企业名称：{}没有找到企业信息",tycCompanyId,companyName);
		}else {
			handlerDate(dataMap, "updatetime", "fromTime", "estiblishTime", "approvedTime");
		}
		return dataMap;
	}
	
	
	/**
	 * 基本信息--股权出质信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyHolderInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
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
		metadata.put("CZZB","出资比例");
		metadata.put("RJCZ","认缴出资（万元）");
		metadata.put("DJRQ","出资时间");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerHolder(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","企业登记信息");
		instanceDataMap.put("tagId","e07cb622dc4a434dbfc3f00b0e2b8ef4");
		instanceDataMap.put("tagName","出资人及出资信息");
		instanceDataMap.put("instance",instanceMap);
		
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 执行操作.
	 * @param creditCode
	 * @param tycCompanyId
	 * @param companyName
	 * @param pageNum
	 * @param businessType
	 * @param url
	 * @return
	 */
	protected Map<String, Object> execute(String creditCode, String tycCompanyId, String companyName, Integer pageNum,
	                                    String businessType, String url) {
		Map<String, Object> dataMap = new HashMap<>();
		Map<String, Object> params = new LinkedHashMap();
		params.put("id", tycCompanyId);
		params.put("name", companyName);
		params.put("pageNum", pageNum == null ? 1 : pageNum);
		dataMap = getTianYanChaInfo(url, params);
		if (dataMap.size() > 0) {
			addRecordInfo(TycRelationInfo.builder().tycId(tycCompanyId).creditCode(creditCode).companyName(companyName).businessType(businessType).requestUrl(url).dataJson(JSON.toJSONString(dataMap)).build());
		}
		return dataMap;
	}
	
	/**
	 * 执行操作
	 * @param creditCode
	 * @param tycCompanyId
	 * @param companyName
	 * @param pageNum
	 * @param businessType
	 * @param url
	 * @return
	 */
	protected Map<String, Object> executeNull(String creditCode, String tycCompanyId, String companyName,
	                                        Integer pageNum, String businessType, String url) {
		Map<String, Object> dataMap;
		Map<String, Object> params = new LinkedHashMap();
		params.put("id", tycCompanyId);
		params.put("name", companyName);
		params.put("pageNum", pageNum == null ? 1 : pageNum);
		dataMap = new HashMap<>(16); //getTianYanChaInfo(url,params);
		if (dataMap.size() > 0) {
			addRecordInfo(TycRelationInfo.builder().tycId(tycCompanyId).creditCode(creditCode).companyName(companyName).businessType(businessType).requestUrl(url).dataJson(JSON.toJSONString(dataMap)).build());
		}
		return dataMap;
	}
	
	/**
	 * 基本信息--获得分支机构信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyBranchInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.BRANCH_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/branch";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("FZJG","企业名称");
		metadata.put("FDDBRXM","负责人");
		metadata.put("CLRQ","成立日期");
		metadata.put("JYZT","经营状态");
		instanceMap.put("metadata",metadata);
			List<Map<String,String>> data = new ArrayList<>(16);
		handlerBranch(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","企业登记信息");
		instanceDataMap.put("tagId","6266af9b382b4d6dae184389afb8f8a4");
		instanceDataMap.put("tagName","分支机构信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 基本信息--对外投资
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
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
		metadata.put("FDNAME","法定代表人（姓名）");
		metadata.put("CZBL","出资比例");
		metadata.put("CLRQ","成立日期");
		metadata.put("JYZT","经营状态");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerTzanli(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","企业登记信息");
		instanceDataMap.put("tagId","b6754c69d6f84c78b0d4fb1c97ffb5f6");
		instanceDataMap.put("tagName","对外投资");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 基本信息--变更记录
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyChangeInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.CHANGE_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/changeinfo";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("CHANGEITEM","变更项目");
		metadata.put("CHANGEBEFORE","变更前内容");
		metadata.put("CHANGEAFTER","变更后内容");
		metadata.put("CHAGEDATE","变更日期");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		
		handlerChange(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","企业登记信息");
		instanceDataMap.put("tagId","f905c58a07cd4ffe9b674d049b231b01");
		instanceDataMap.put("tagName","变更记录");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 基本信息--资质信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyCertificateInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.CERTIFICATE_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/certificate";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("ZZMC","证书名称");
		metadata.put("ZSBH","证书编号");
		metadata.put("RDRQ","签发日期");
		metadata.put("ZZJZQ","有效期限");
		metadata.put("RDJGQC","发证机关");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerCertificate(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","企业登记信息");
		instanceDataMap.put("tagId","633811d0d2fc4ecd9c924f1b2f776f01");
		instanceDataMap.put("tagName","资质信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 法人高管信息--主要管理人员信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyStaffInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.STAFF_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/staff";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("GGNAME","名称");
		metadata.put("GGPOST","职位");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerStaff(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","法人高管信息");
		instanceDataMap.put("tagId","537e98f6acf94557994c7556b1a846d2");
		instanceDataMap.put("tagName","主要管理人员信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 监管信息--行政处罚
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
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
		metadata.put("CCRQ","日期");
		metadata.put("CCFS","类型");
		metadata.put("CCJG","结果");
		metadata.put("SSJG","检查实施机关");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerPunishMent(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","行政监管信息");
		instanceDataMap.put("tagId","709de3ea22444116856e2bba796c2c4c");
		instanceDataMap.put("tagName","监管信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 监管信息-- 经营异常信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyAbnormalInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.ABNORMAL_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/abnormal";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("LRRQ","列入日期");
		metadata.put("LRYCMLYY","列入经营异常名录原因");
		metadata.put("JDJG","列入决定机关");
		metadata.put("YCRQ","移出日期");
		metadata.put("YCYCMLYY","移出经营异常名录原因");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerAbnoraml(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","行政监管信息");
		instanceDataMap.put("tagId","3169e3c81d6f476ca81fb537748ccafe");
		instanceDataMap.put("tagName","经营异常信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 监管信息-- 行政许可信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyLicenseInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.LICENSE_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/getLicense";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("LICENSENO","许可文书编号");
		metadata.put("LICENSENAME","许可文件名称");
		metadata.put("STARTDATE","起始日期");
		metadata.put("ENDDATE","许可截止日期");
		metadata.put("XKJG","许可机关");
		metadata.put("XKNR","许可内容");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerLicense(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","行政监管信息");
		instanceDataMap.put("tagId","ea9fa1cac64b41da86ea5d640e55e746");
		instanceDataMap.put("tagName","行政许可信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 监管信息-- 行政处罚信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyCreditInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.CREDITCHINA_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/creditChinaV2";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("CFRQ","处罚日期");
		metadata.put("CFJDSWH","决定书文号");
		metadata.put("CFLB","处罚类别");
		metadata.put("CFSY","处罚事由");
		metadata.put("CFJGMC","处罚机构");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerCredit(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","行政监管信息");
		instanceDataMap.put("tagId","2112afd0ff4740269c031b9a3f2065bd");
		instanceDataMap.put("tagName","行政处罚信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 监管信息-- 行政奖励信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyRewardInfo(String creditCode, String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.REWARD_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/reward";
			//返回结果
			dataMap = executeNull(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("JLSYRQ","奖励日期");
		metadata.put("JLXS","奖励事项");
		metadata.put("RDWSH","证书编号");
		metadata.put("JLMC","荣誉事项内容");
		metadata.put("SYJG","奖励机关");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerReward(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","行政监管信息");
		instanceDataMap.put("tagId","8664ae2c43da4f629dd5c25ea5ddedaa");
		instanceDataMap.put("tagName","行政奖励信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	
	
	/**
	 * 监管信息-- 守信激励
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyExcitationInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.EXCITATION_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/excitation";
			//返回结果
			dataMap = executeNull(creditCode, tycCompanyId, companyName, pageNum, businessType,url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("FBND","发布年度");
		metadata.put("JLLX","类型");
		metadata.put("FBDW","发布单位");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerExcitation(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","行政监管信息");
		instanceDataMap.put("tagId","393967310f644946a5340a414c4a9536");
		instanceDataMap.put("tagName","守信激励");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 监管信息-- 失信惩戒
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyDishonestInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.DISHONEST_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/dishonest";
			//返回结果
			dataMap = executeNull(creditCode, tycCompanyId, companyName, pageNum, businessType,url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("FBND","发布年度");
		metadata.put("WFSS","违法事实");
		metadata.put("FBDW","发布单位");
		
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerDishonest(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","行政监管信息");
		instanceDataMap.put("tagId","c979e566c6d648d4b1347a06eead912d");
		instanceDataMap.put("tagName","失信惩戒");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 司法信息-- 立案信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyCaseInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.CASE_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/caseinfo";
			//返回结果
			dataMap = executeNull(creditCode, tycCompanyId, companyName, pageNum, businessType,url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("LASJ","立案时间");
		metadata.put("AH","案号");
		metadata.put("AJZT","案件状态");
		metadata.put("BG","被告人/被告/被上诉人/被申请人");
		metadata.put("YG","公诉人/原告/上诉人/申请人");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerCaseInfo(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","司法信息");
		instanceDataMap.put("tagId","c979e566c6d648d4b1347a06aaad365d");
		instanceDataMap.put("tagName","立案信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 司法信息-- 开庭公告
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyCourtAnnouncement(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.COURTANNOUNCEMENT_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/courtAnnouncement";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("KTRQ","开庭日期");
		metadata.put("AH","案号");
		metadata.put("AY","案由");
		metadata.put("BG","被告人/被告/被上诉人/被申请人");
		metadata.put("YG","公诉人/原告/上诉人/申请人");
		metadata.put("KTFY","开庭法院");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerCourtInfo(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","司法信息");
		instanceDataMap.put("tagId","c979e566c6d648d4b1347a06awed3697d");
		instanceDataMap.put("tagName","开庭公告");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 司法信息-- 法律诉讼信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
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
		metadata.put("FY","法院");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerLawSuitInfo(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","司法信息");
		instanceDataMap.put("tagId","c325e566c6d648d4b1874a06qhtd3245d");
		instanceDataMap.put("tagName","法律诉讼信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 司法信息-- 被执行人信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyZhixingInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.ZHIXING_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/zhixinginfo";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("LASJ","立案时间");
		metadata.put("ZXBD","执行标的");
		metadata.put("AH","案号");
		metadata.put("ZXFY","执行法院");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerZhixingInfo(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","司法信息");
		instanceDataMap.put("tagId","b345e566c6d648d4b78ba06qhtd7913c");
		instanceDataMap.put("tagName","被执行人信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 认定名录-- 招投标信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyBiddingInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.BIDS_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/bids";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("SPSJ","发布时间");
		metadata.put("XMMC","项目名称");
		metadata.put("CGR","采购人");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerBidsInfo(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","认定名录");
		instanceDataMap.put("tagId","b345e566c6d648d4bweba4152dsfaw26q");
		instanceDataMap.put("tagName","招投标信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 知识产权-- 专利信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyPatentsInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.PATENTS_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/patents";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("ZLMC","专利名称");
		metadata.put("LX","类型");
		metadata.put("SQGBH","申请公布号");
		metadata.put("SQH","申请号");
		metadata.put("SQGBR","申请公布日");
		metadata.put("FLZT","法律状态");
		metadata.put("SQRQ","申请日期");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerPatentsInfo(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","知识产权");
		instanceDataMap.put("tagId","t345e566c6d648d4bwebawastbsytt23345");
		instanceDataMap.put("tagName","专利信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 知识产权-- 商标信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyTradeMark(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.TRADEMARK_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/tm";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("SQRQ","申请日期");
		metadata.put("SBTP","商标图片");
		metadata.put("SBMC","商标名称");
		metadata.put("ZCH","注册号");
		metadata.put("LB","类别");
		metadata.put("ZT","状态");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerTradeMarkInfo(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","知识产权");
		instanceDataMap.put("tagId","o345e566c6d648d4bwe8jwq=c92098qa");
		instanceDataMap.put("tagName","商标信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 知识产权-- 著作权信息
	 * @param companyName
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getTycCompanyCopyRegInfo(String creditCode,String tycCompanyId,String companyName,Integer pageNum){
		String businessType = TianYanChaConst.COPYREG_INFO;
		TycRelationInfo relationInfo = getRecordResult(creditCode,companyName,businessType);
		Map<String,Object> dataMap = new HashMap<>(16);
		if (relationInfo!=null){
			dataMap = JSON.parseObject(relationInfo.getDataJson(),Map.class);
		}else {
			String url = getBasicUrl() + "/v4/open/copyReg";
			//返回结果
			dataMap = execute(creditCode, tycCompanyId, companyName, pageNum, businessType, url);
		}
		Map<String, Object> resultMap = new HashMap<>(16);
		//step 1
		Map<String, Object> instanceMap = new HashMap<>(16);
		Map<String,String> metadata = new HashMap<>(16);
		metadata.put("DJPZRQ","登记批准日期");
		metadata.put("RJQC","软件全称");
		metadata.put("DJH","登记号");
		metadata.put("FLHMC","分类号名称");
		metadata.put("BBH","版本号");
		instanceMap.put("metadata",metadata);
		List<Map<String,String>> data = new ArrayList<>(16);
		handlerCopyRegInfo(data,dataMap);
		instanceMap.put("data",data);
		//step 2
		Map<String, Object> instanceDataMap = new HashMap<>(16);
		instanceDataMap.put("tycCompanyId",tycCompanyId);
		instanceDataMap.put("labelName","知识产权");
		instanceDataMap.put("tagId","13adfafasdgbn2168ccc3135ds88wtexd8");
		instanceDataMap.put("tagName","著作权信息");
		instanceDataMap.put("instance",instanceMap);
		resultMap.put("instanceData",instanceDataMap);
		return resultMap;
	}
	
	/**
	 * 著作权信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerCopyRegInfo(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("DJPZRQ",NULLL_STR);
			tmpMap.put("RJQC",NULLL_STR);
			tmpMap.put("DJH",NULLL_STR);
			tmpMap.put("FLHMC",NULLL_STR);
			tmpMap.put("BBH",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("DJPZRQ",NULLL_STR);
				tmpMap.put("RJQC",NULLL_STR);
				tmpMap.put("DJH",NULLL_STR);
				tmpMap.put("FLHMC",NULLL_STR);
				tmpMap.put("BBH",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("DJPZRQ",handlerDate(Objects.toString(map.get("publishtime"),"")));
				tmpMap.put("RJQC",Objects.toString(map.get("simplename"),""));
				tmpMap.put("DJH",Objects.toString(map.get("regnum"),""));
				tmpMap.put("FLHMC",Objects.toString(map.get("fullname"),""));
				tmpMap.put("BBH",Objects.toString(map.get("version"),""));
				data.add(tmpMap);
			}
		}
		
	}
	
	/**
	 * 商标信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerTradeMarkInfo(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("SQRQ",NULLL_STR);
			tmpMap.put("SBTP",NULLL_STR);
			tmpMap.put("SBMC",NULLL_STR);
			tmpMap.put("ZCH",NULLL_STR);
			tmpMap.put("LB",NULLL_STR);
			tmpMap.put("ZT",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("SQRQ",NULLL_STR);
				tmpMap.put("SBTP",NULLL_STR);
				tmpMap.put("SBMC",NULLL_STR);
				tmpMap.put("ZCH",NULLL_STR);
				tmpMap.put("LB",NULLL_STR);
				tmpMap.put("ZT",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("SQRQ",handlerDate(Objects.toString(map.get("appDate"),"")));
				tmpMap.put("SBTP",Objects.toString(map.get("tmPic"),""));
				tmpMap.put("SBMC",Objects.toString(map.get("intCls"),""));
				tmpMap.put("ZCH",Objects.toString(map.get("regNo"),""));
				tmpMap.put("LB",Objects.toString(map.get("tmClass"),""));
				tmpMap.put("ZT",Objects.toString(map.get("status"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 专利信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerPatentsInfo(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("ZLMC",NULLL_STR);
			tmpMap.put("LX",NULLL_STR);
			tmpMap.put("SQGBH",NULLL_STR);
			tmpMap.put("SQH",NULLL_STR);
			tmpMap.put("SQGBR",NULLL_STR);
			tmpMap.put("FLZT",NULLL_STR);
			tmpMap.put("SQRQ",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("ZLMC",NULLL_STR);
				tmpMap.put("LX",NULLL_STR);
				tmpMap.put("SQGBH",NULLL_STR);
				tmpMap.put("SQH",NULLL_STR);
				tmpMap.put("SQGBR",NULLL_STR);
				tmpMap.put("FLZT",NULLL_STR);
				tmpMap.put("SQRQ",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("ZLMC",Objects.toString(map.get("title"),""));
				tmpMap.put("LX",Objects.toString(map.get("patentType"),""));
				tmpMap.put("SQGBH",Objects.toString(map.get("applicationPublishNum"),""));
				tmpMap.put("SQH",Objects.toString(map.get("appnumber"),""));
				tmpMap.put("SQGBR",Objects.toString(map.get("pubDate"),""));
				tmpMap.put("FLZT",Objects.toString(map.get("lprs"),""));
				tmpMap.put("SQRQ",Objects.toString(map.get("applicationTime"),""));
				data.add(tmpMap);
			}
		}
	}
	
	
	/**
	 * 招投标信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerBidsInfo(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("SPSJ",NULLL_STR);
			tmpMap.put("XMMC",NULLL_STR);
			tmpMap.put("CGR",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("SPSJ",NULLL_STR);
				tmpMap.put("XMMC",NULLL_STR);
				tmpMap.put("CGR",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("SPSJ",handlerDate(Objects.toString(map.get("publishTime"),"")));
				tmpMap.put("XMMC",Objects.toString(map.get("title"),""));
				tmpMap.put("CGR",Objects.toString(map.get("purchaser"),""));
				data.add(tmpMap);
			}
		}
	}
	
	
	/**
	 * 被执行人
	 * @param data
	 * @param dataMap
	 */
	protected void handlerZhixingInfo(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("LASJ",NULLL_STR);
			tmpMap.put("ZXBD",NULLL_STR);
			tmpMap.put("AH",NULLL_STR);
			tmpMap.put("ZXFY",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("LASJ",NULLL_STR);
				tmpMap.put("ZXBD",NULLL_STR);
				tmpMap.put("AH",NULLL_STR);
				tmpMap.put("ZXFY",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("LASJ",handlerDate(Objects.toString(map.get("caseCreateTime"),"")));
				tmpMap.put("ZXBD",Objects.toString(map.get("pname"),""));
				tmpMap.put("AH",Objects.toString(map.get("caseCode"),""));
				tmpMap.put("ZXFY",Objects.toString(map.get("execCourtName"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 法律诉讼信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerLawSuitInfo(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("KTRQ",NULLL_STR);
			tmpMap.put("AJMC",NULLL_STR);
			tmpMap.put("AH",NULLL_STR);
			tmpMap.put("AY",NULLL_STR);
			tmpMap.put("AJSF",NULLL_STR);
			tmpMap.put("FY",NULLL_STR);
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
				tmpMap.put("FY",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("KTRQ",handlerDate(Objects.toString(map.get("eventTime"),"")));
				tmpMap.put("AJMC",Objects.toString(map.get("title"),""));
				tmpMap.put("AH",Objects.toString(map.get("caseno"),""));
				tmpMap.put("AY",Objects.toString(map.get("plaintiffsApp"),""));
				tmpMap.put("AJSF",Objects.toString(map.get("casetype"),""));
				tmpMap.put("FY",Objects.toString(map.get("court"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 开庭公告
	 * @param data
	 * @param dataMap
	 */
	protected void handlerCourtInfo(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("KTRQ",NULLL_STR);
			tmpMap.put("AH",NULLL_STR);
			tmpMap.put("AY",NULLL_STR);
			tmpMap.put("BG",NULLL_STR);
			tmpMap.put("YG",NULLL_STR);
			tmpMap.put("KTFY",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("KTRQ",NULLL_STR);
				tmpMap.put("AH",NULLL_STR);
				tmpMap.put("AY",NULLL_STR);
				tmpMap.put("BG",NULLL_STR);
				tmpMap.put("YG",NULLL_STR);
				tmpMap.put("KTFY",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("KTRQ",Objects.toString(map.get("publishdate"),""));
				tmpMap.put("AH",Objects.toString(map.get("bltnno"),""));
				tmpMap.put("AY",Objects.toString(map.get("content"),""));
				tmpMap.put("BG",Objects.toString(map.get("party2"),""));
				tmpMap.put("YG",Objects.toString(map.get("dealgradename"),""));
				tmpMap.put("KTFY",Objects.toString(map.get("courtcode"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 案件信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerCaseInfo(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("LASJ",NULLL_STR);
			tmpMap.put("AH",NULLL_STR);
			tmpMap.put("AJZT",NULLL_STR);
			tmpMap.put("BG",NULLL_STR);
			tmpMap.put("YG",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("LASJ",NULLL_STR);
				tmpMap.put("AH",NULLL_STR);
				tmpMap.put("AJZT",NULLL_STR);
				tmpMap.put("BG",NULLL_STR);
				tmpMap.put("YG",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("LASJ",Objects.toString(map.get("casetime"),""));
				tmpMap.put("AH",Objects.toString(map.get("caseno"),""));
				tmpMap.put("AJZT",Objects.toString(map.get("status"),""));
				tmpMap.put("BG",Objects.toString(map.get("defendant"),""));
				tmpMap.put("YG",Objects.toString(map.get("plaintiff"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 失信惩戒
	 * @param data
	 * @param dataMap
	 */
	protected void handlerDishonest(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("FBND",NULLL_STR);
			tmpMap.put("WFSS",NULLL_STR);
			tmpMap.put("FBDW",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("FBND",NULLL_STR);
				tmpMap.put("WFSS",NULLL_STR);
				tmpMap.put("FBDW",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("FBND",Objects.toString(map.get("year"),""));
				tmpMap.put("WFSS",Objects.toString(map.get("caseContent"),""));
				tmpMap.put("FBDW",Objects.toString(map.get("publishName"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 守信激励
	 * @param data
	 * @param dataMap
	 */
	protected void handlerExcitation(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("FBND",NULLL_STR);
			tmpMap.put("JLLX",NULLL_STR);
			tmpMap.put("FBDW",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("FBND",NULLL_STR);
				tmpMap.put("JLLX",NULLL_STR);
				tmpMap.put("FBDW",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("FBND",Objects.toString(map.get("year"),""));
				tmpMap.put("JLLX",Objects.toString(map.get("type"),""));
				tmpMap.put("FBDW",Objects.toString(map.get("name"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 行政奖励信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerReward(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("JLSYRQ",NULLL_STR);
			tmpMap.put("JLXS",NULLL_STR);
			tmpMap.put("RDWSH",NULLL_STR);
			tmpMap.put("JLMC",NULLL_STR);
			tmpMap.put("SYJG",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("JLSYRQ",NULLL_STR);
				tmpMap.put("JLXS",NULLL_STR);
				tmpMap.put("RDWSH",NULLL_STR);
				tmpMap.put("JLMC",NULLL_STR);
				tmpMap.put("SYJG",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("JLSYRQ",handlerDate(Objects.toString(map.get("decisionDate"),"")));
				tmpMap.put("JLXS",Objects.toString(map.get("rewardItem"),""));
				tmpMap.put("RDWSH",Objects.toString(map.get("rewardNumber"),""));
				tmpMap.put("JLMC",Objects.toString(map.get("rewardName"),""));
				tmpMap.put("SYJG",Objects.toString(map.get("departmentName"),""));
				data.add(tmpMap);
			}
		}
	}
	
	
	/**
	 * 行政处罚.
	 * @param data
	 * @param dataMap
	 */
	protected void handlerCredit(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("CFRQ",NULLL_STR);
			tmpMap.put("CFJDSWH",NULLL_STR);
			tmpMap.put("CFLB",NULLL_STR);
			tmpMap.put("CFSY",NULLL_STR);
			tmpMap.put("CFJGMC",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("CFRQ",NULLL_STR);
				tmpMap.put("CFJDSWH",NULLL_STR);
				tmpMap.put("CFLB",NULLL_STR);
				tmpMap.put("CFSY",NULLL_STR);
				tmpMap.put("CFJGMC",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("CFRQ",handlerDate(Objects.toString(map.get("decisionDate"),"")));
				tmpMap.put("CFJDSWH",Objects.toString(map.get("punishNumber"),""));
				tmpMap.put("CFLB",Objects.toString(map.get("type"),""));
				tmpMap.put("CFSY",Objects.toString(map.get("reason"),""));
				tmpMap.put("CFJGMC",Objects.toString(map.get("departmentName"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 行政许可信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerLicense(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("LICENSENO",NULLL_STR);
			tmpMap.put("LICENSENAME",NULLL_STR);
			tmpMap.put("STARTDATE",NULLL_STR);
			tmpMap.put("ENDDATE",NULLL_STR);
			tmpMap.put("XKJG",NULLL_STR);
			tmpMap.put("XKNR",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("LICENSENO",NULLL_STR);
				tmpMap.put("LICENSENAME",NULLL_STR);
				tmpMap.put("STARTDATE",NULLL_STR);
				tmpMap.put("ENDDATE",NULLL_STR);
				tmpMap.put("XKJG",NULLL_STR);
				tmpMap.put("XKNR",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("LICENSENO",Objects.toString(map.get("licencenumber"),""));
				tmpMap.put("LICENSENAME",Objects.toString(map.get("licencename"),""));
				tmpMap.put("STARTDATE",Objects.toString(map.get("fromdate"),""));
				tmpMap.put("ENDDATE",Objects.toString(map.get("todate"),""));
				tmpMap.put("XKJG",Objects.toString(map.get("department"),""));
				tmpMap.put("XKNR",Objects.toString(map.get("scope"),""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 经营异常信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerAbnoraml(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("LRRQ",NULLL_STR);
			tmpMap.put("LRYCMLYY",NULLL_STR);
			tmpMap.put("JDJG",NULLL_STR);
			tmpMap.put("YCRQ",NULLL_STR);
			tmpMap.put("YCYCMLYY",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("LRRQ",NULLL_STR);
				tmpMap.put("LRYCMLYY",NULLL_STR);
				tmpMap.put("JDJG",NULLL_STR);
				tmpMap.put("YCRQ",NULLL_STR);
				tmpMap.put("YCYCMLYY",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("LRRQ", Objects.toString(map.get("putDate"), ""));
				tmpMap.put("LRYCMLYY", Objects.toString(map.get("putReason"), ""));
				tmpMap.put("JDJG", Objects.toString(map.get("putDepartment"), ""));
				tmpMap.put("YCRQ", Objects.toString(map.get("removeDate"), ""));
				tmpMap.put("YCYCMLYY", Objects.toString(map.get("removeReason"), ""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 监管信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerPunishMent(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("CCRQ",NULLL_STR);
			tmpMap.put("CCFS",NULLL_STR);
			tmpMap.put("CCJG",NULLL_STR);
			tmpMap.put("SSJG",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("CCRQ", NULLL_STR);
				tmpMap.put("CCFS", NULLL_STR);
				tmpMap.put("CCJG", NULLL_STR);
				tmpMap.put("SSJG", NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("CCRQ", Objects.toString(map.get("decisionDate"), ""));
				tmpMap.put("CCFS", Objects.toString(map.get("type"), ""));
				tmpMap.put("CCJG", Objects.toString(map.get("remarks"), ""));
				tmpMap.put("SSJG", Objects.toString(map.get("departmentName"), ""));
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 主要管理人员信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerStaff(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("GGNAME",NULLL_STR);
			tmpMap.put("GGPOST",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("GGNAME", NULLL_STR);
				tmpMap.put("GGPOST", NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("GGNAME", Objects.toString(map.get("name"), ""));
				tmpMap.put("GGPOST", Objects.toString(map.get("typeSore"), ""));
				data.add(tmpMap);
			}
		}
	}
	
	
	/**
	 * 资质信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerCertificate(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String,String> tmpMap = new HashMap<>(16);
			tmpMap.put("ZZMC",NULLL_STR);
			tmpMap.put("ZSBH",NULLL_STR);
			tmpMap.put("RDRQ",NULLL_STR);
			tmpMap.put("ZZJZQ",NULLL_STR);
			tmpMap.put("RDJGQC",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String,String> tmpMap = new HashMap<>(16);
				tmpMap.put("ZZMC",NULLL_STR);
				tmpMap.put("ZSBH",NULLL_STR);
				tmpMap.put("RDRQ",NULLL_STR);
				tmpMap.put("ZZJZQ",NULLL_STR);
				tmpMap.put("RDJGQC",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("ZZMC", Objects.toString(map.get("certificateName"), ""));
				tmpMap.put("ZSBH", Objects.toString(map.get("certNo"), ""));
				String detail = Objects.toString(map.get("detail"), "");
				List<Map<String, Object>> detailMap = (List<Map<String, Object>>) JSON.parseObject(detail, List.class);
				String RDRQ = "", ZZJZQ = "", RDJGQC = "";
				for (Map<String, Object> tmpVal : detailMap) {
					String key = Objects.toString(tmpVal.get("title"), "");
					String value = Objects.toString(tmpVal.get("content"), "");
					if (key.trim().equalsIgnoreCase("颁证日期")) {
						RDRQ = value;
					}
					if (key.trim().equalsIgnoreCase("有效期")) {
						ZZJZQ = value;
					}
					if (key.trim().equalsIgnoreCase("发证机关")) {
						RDJGQC = value;
					}
				}
				tmpMap.put("RDRQ", RDRQ);
				tmpMap.put("ZZJZQ", ZZJZQ);
				tmpMap.put("RDJGQC", RDJGQC);
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 变更记录.
	 * @param data
	 * @param dataMap
	 */
	protected void handlerChange(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("CHANGEITEM",NULLL_STR);
			tmpMap.put("CHANGEBEFORE",NULLL_STR);
			tmpMap.put("CHANGEAFTER",NULLL_STR);
			tmpMap.put("CHAGEDATE",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"), "");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap, List.class);
			if (itemMap == null || itemMap.size() == 0) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("CHANGEITEM",NULLL_STR);
				tmpMap.put("CHANGEBEFORE",NULLL_STR);
				tmpMap.put("CHANGEAFTER",NULLL_STR);
				tmpMap.put("CHAGEDATE",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("CHANGEITEM", Objects.toString(map.get("changeItem"), ""));
				tmpMap.put("CHANGEBEFORE", Objects.toString(map.get("contentBefore"), "").replaceAll("<em>", "").replaceAll("</em>", ""));
				tmpMap.put("CHANGEAFTER", Objects.toString(map.get("contentAfter"), "").replaceAll("<em>", "").replaceAll("</em>", ""));
				tmpMap.put("CHAGEDATE", Objects.toString(map.get("changeTime"), ""));
				data.add(tmpMap);
			}
		}
	}
	
	
	/**
	 * 对外投资信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerTzanli(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("BTZQYMC",NULLL_STR);
			tmpMap.put("FDNAME",NULLL_STR);
			tmpMap.put("CZBL",NULLL_STR);
			tmpMap.put("CLRQ",NULLL_STR);
			tmpMap.put("JYZT",NULLL_STR);
			data.add(tmpMap);
		}else{
			
			String reMap = Objects.toString(dataMap.get("items"),"");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap,List.class);
			if (itemMap==null || itemMap.size()==0){
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("BTZQYMC",NULLL_STR);
				tmpMap.put("FDNAME",NULLL_STR);
				tmpMap.put("CZBL",NULLL_STR);
				tmpMap.put("CLRQ",NULLL_STR);
				tmpMap.put("JYZT",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("BTZQYMC",Objects.toString(map.get("company"),""));
				tmpMap.put("FDNAME",Objects.toString(map.get("legalPersonName"),""));
				tmpMap.put("CZBL",Objects.toString(map.get("percent"),""));
				tmpMap.put("CLRQ",handlerDate(Objects.toString(map.get("estiblishTime"),"")));
				tmpMap.put("JYZT",Objects.toString(map.get("regStatus"),""));
				data.add(tmpMap);
			}
		}
	}
	
	
	/**
	 * 分支内信息
	 * @param data
	 * @param dataMap
	 */
	protected void handlerBranch(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("FZJG",NULLL_STR);
			tmpMap.put("FDDBRXM",NULLL_STR);
			tmpMap.put("CLRQ",NULLL_STR);
			tmpMap.put("JYZT",NULLL_STR);
			data.add(tmpMap);
		}else {
			String reMap = Objects.toString(dataMap.get("items"),"");
			List<Map<String, Object>> itemMap = (List<Map<String, Object>>) JSON.parseObject(reMap,List.class);
			if (itemMap==null || itemMap.size()==0){
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("FZJG",NULLL_STR);
				tmpMap.put("FDDBRXM",NULLL_STR);
				tmpMap.put("CLRQ",NULLL_STR);
				tmpMap.put("JYZT",NULLL_STR);
				data.add(tmpMap);
			}
			for (Map<String, Object> map : itemMap) {
				Map<String, String> tmpMap = new HashMap<>(16);
				tmpMap.put("FZJG",Objects.toString(map.get("name"),""));
				tmpMap.put("FDDBRXM",Objects.toString(map.get("legalPersonName"),""));
				tmpMap.put("CLRQ",handlerDate(Objects.toString(map.get("estiblishTime"),"")));
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
	protected void handlerHolder(List<Map<String, String>> data, Map<String, Object> dataMap) {
		if (dataMap.isEmpty()){
			Map<String, String> tmpMap = new HashMap<>(16);
			tmpMap.put("GDMC",NULLL_STR);
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
				String CZZB = "", RJCZ = "", DJRQ = "";
				if (capitalMap != null && capitalMap.size() > 0) {
					Map<String, Object> infoMap = capitalMap.get(0);
					CZZB = Objects.toString(infoMap.get("percent"), "");
					RJCZ = Objects.toString(infoMap.get("amomon"), "");
					DJRQ = handlerDate(Objects.toString(infoMap.get("time"), ""));
				}
				tmpMap.put("GDMC", GDMC);
				tmpMap.put("CZZB", CZZB);
				tmpMap.put("RJCZ", RJCZ);
				tmpMap.put("DJRQ", DJRQ);
				data.add(tmpMap);
			}
		}
	}
	
	/**
	 * 格式化时间
	 * @param dataMap
	 * @param times
	 */
	protected void handlerDate(Map<String, Object> dataMap, String... times) {
		for (String time : times) {
			String value = Objects.toString(dataMap.get(time),"");
			if (StringUtils.isEmpty(value)){
				continue;
			}
			try {
				dataMap.put(time, DateUtils.getFormatDate(value,DateUtils.YMD_PATTERN));
			}catch (Exception e){
			}
		}
	}
	
	/**
	 * 格式化时间
	 * @param value
	 */
	protected String handlerDate(String value) {
		if (StringUtils.isEmpty(value)){
			return NULLL_STR;
		}
		try {
			return DateUtils.getFormatDate(value,DateUtils.YMD_PATTERN);
		}catch (Exception e){
			log.error("格式化时间:{}出现了异常,异常信息是:{}",value,e.getMessage());
			return "";
		}
	}
	
	/**
	 * 获取对应的值
	 * @param dataMap
	 * @param key
	 * @return
	 */
	protected static String getMapVal(Map<String,Object> dataMap,String key){
		return Objects.toString(dataMap.get(key),"-");
	}
	
	/**
	 * 获取对应的值
	 * @param creditCode
	 * @param companyName
	 * @param businessType
	 * @return
	 */
	protected TycRelationInfo getRecordResult(String creditCode,String companyName,String businessType){
		String sql = "select * from xyd_tyc_relation where credit_code=? and company_name=? and business_type=? limit 1 ";
		List<Map<String, Object>> datas = jdbcTemplate.queryForList(sql,creditCode,companyName,businessType);
		if (datas!=null && datas.size()>0){
			TycRelationInfo relationInfo = new TycRelationInfo();
			for (Map<String, Object> map : datas) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					String key = entry.getKey();
					if (key.equalsIgnoreCase("tyc_id")){
						relationInfo.setTycId(entry.getValue().toString());
					}
					if (key.equalsIgnoreCase("credit_code")){
						relationInfo.setCreditCode(entry.getValue().toString());
					}
					if (key.equalsIgnoreCase("company_name")){
						relationInfo.setCompanyName(entry.getValue().toString());
					}
					if (key.equalsIgnoreCase("business_type")){
						relationInfo.setBusinessType(entry.getValue().toString());
					}
					if (key.equalsIgnoreCase("request_url")){
						relationInfo.setRequestUrl(entry.getValue().toString());
					}
					if (key.equalsIgnoreCase("data_json")){
						relationInfo.setDataJson(entry.getValue().toString());
					}
				}
			}
			return relationInfo;
		}
		return null;
	}
	
	/**
	 * 添加信息
	 * @param info
	 * @return
	 */
	protected boolean addRecordInfo(TycRelationInfo info){
		String sql = "INSERT INTO xyd_tyc_relation (tyc_id,credit_code,company_name,business_type,request_url,data_json) VALUES (?,?,?,?,?,?);";
		return jdbcTemplate.update(sql,info.getTycId(),info.getCreditCode(),info.getCompanyName(),info.getBusinessType(),info.getRequestUrl(),info.getDataJson()) > 0;
	}
}
