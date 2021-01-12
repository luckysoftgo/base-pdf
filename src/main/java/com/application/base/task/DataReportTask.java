package com.application.base.task;

import com.alibaba.fastjson.JSON;
import com.application.base.config.Constants;
import com.application.base.config.PdfPropsConfig;
import com.application.base.datas.dto.CRResult;
import com.application.base.datas.dto.FinanceInfo;
import com.application.base.service.PdfInfoService;
import com.application.base.util.DateUtils;
import com.application.base.util.ImageUtils;
import com.application.base.util.toolpdf.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : 孤狼
 * @NAME: ReportTask
 * @DESC: ReportTask类设计
 **/

@Slf4j
@Configuration
@EnableScheduling
public class DataReportTask {
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private PdfInfoService pdfInfoService;
	
	@Autowired
	private PdfPropsConfig pdfPropsConfig;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Scheduled(cron = "${pdf.taskCron}")
	private void execute() {
		//log.info("总的生成报告任务开始执行!");
		//execCreditReport();
		//execCrAuthReport();
		//log.info("总的生成报告任务执行结束!");
	}
	
	/**
	 * CR报告.
	 *
	 * @return
	 */
	public void execCrAuthReport() {
		//在天威认证中的key
		String value = Objects.toString(redisTemplate.opsForList().rightPop(Constants.CRAUTH_REPORT_KEY), "");
		value = "1258721492083531777@33@92610822MA707NAX4Q@府谷县满肚麻辣香锅@E:/home/pdf/resources/data/crauth/";
		if (StringUtils.isNotEmpty(value)) {
			String[] credits = value.split("@");
			//按照目前的模板来操作.
			String reportType = Constants.REPORT_TYPE_CRAUTH;
			String userId = credits[0];
			String entId = credits[1];
			String creditCode = credits[2];
			String companyName = credits[3];
			String reportPath = pdfPropsConfig.getDataPath();
			File file = new File(reportPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			log.info("creditCode:{},companyName:{},reportPath:{},", creditCode, companyName, reportPath);
			//存储位置:类型重置;
			try {
				Map<String, Object> dataMap = new HashMap<>(16);
				if (pdfPropsConfig.getDataSource().equalsIgnoreCase("tianyancha")){
					dataMap = pdfInfoService.getTianYanChaPdfMap(reportType, creditCode, companyName);
				}else {
					log.info("没有找到相应的数据源,不能完成生产操作.");
				}
				CRResult crResult = getCrResult(); //dataService.getCrResult(Long.parseLong(userId),Long.parseLong(entId));
				//获取数据.
				if (crResult!=null){
					//生成图片.
					String imagePath = ImageUtils.getImage(crResult,pdfPropsConfig,reportType,creditCode);
					dataMap.put("crImage",imagePath);
					String point = crResult.getCrLevel().trim().replaceAll("CR","");
					dataMap.put("crResult","locat locat_"+point);
					crResult.setA1(trimZero(Objects.toString(crResult.getA1())));
					crResult.setA7(trimZero(Objects.toString(crResult.getA7())));
					crResult.setA8(trimZero(Objects.toString(crResult.getA8())));
					crResult.setA9(trimZero(Objects.toString(crResult.getA9())));
					crResult.setOA(trimZero(Objects.toString(crResult.getOA())));
					crResult.setAA(trimZero(Objects.toString(crResult.getAA())));
					crResult.setCrAmount(trimZero(Objects.toString(crResult.getCrAmount())));
					dataMap.put("crData",crResult);
				}
				//pdf标头的图片位置.
				dataMap.put("imagePath", pdfPropsConfig.getImgUrl());
				dataMap.put("companyName",companyName);
				dataMap.put("reportDate", DateUtils.getNowDate());
				dataMap.put("reportNo","CR"+creditCode);
				//财务概要数据
				FinanceInfo finInfo = this.getFinSummaryInfo(creditCode);
				if (finInfo != null) {
					dataMap.put(Constants.CRAUTH_REPORT_TEMPLATE_FININFO_KEY , finInfo);
				}
				freemarker.template.Configuration configuration = freeMarkerConfigurer.getConfiguration();
				try {
					configuration.setClassForTemplateLoading(DataReportTask.class.getClass(), "/templetes");
				} catch (Exception e) {
				}
				String dataPath = pdfPropsConfig.getDataPath();
				dataPath = dataPath + CommonUtils.getSplit() + reportType + CommonUtils.getSplit();
				pdfInfoService.createHtml(dataPath, companyName, Constants.CRAUTH_REPORT_TEMPLATE_NAME, creditCode, configuration, dataMap);
				boolean result = pdfInfoService.changeHtmlToPdf(pdfPropsConfig.getFontPath(), dataPath, companyName, creditCode, null, pdfPropsConfig.getWaterMark(), null);
				if (!result){
					//生成PDF失败，再次放入redis队列，等待下次处理
					redisTemplate.opsForList().leftPush(Constants.CRAUTH_REPORT_KEY, value);
					log.info("生成CR报告失败，将企业信息：" + value +" 再次放入redis队列，等待下次处理");
				}
			} catch (Exception e) {
				//生成PDF失败，再次放入redis队列，等待下次处理
				redisTemplate.opsForList().leftPush(Constants.CRAUTH_REPORT_KEY, value);
				log.error("生成CR报告失败，将企业信息：" + value +" 再次放入redis队列，等待下次处理", e);
			}
		}
	}

	
	/**
	 * 去0 操作.
	 *
	 * @param s
	 * @return
	 */
	private static String trimZero(String s) {
		if (s.indexOf(".") > 0) {
			// 去掉多余的0
			s = s.replaceAll("0+?$", "");
			// 如最后一位是.则去掉
			s = s.replaceAll("[.]$", "");
		}
		return s;
	}
	
	/**
	 * 信用报告.
	 *
	 * @return
	 */
	public void execCreditReport() {
		//上线用.
		String value = ""; //Objects.toString(redisTemplate.opsForList().rightPop(Constants.CREDIT_REPORT_KEY), "");
		value = "916100005637708187@陕西西部资信股份有限公司@E:/home/pdf/resources/data/credit/";
		if (StringUtils.isNotEmpty(value)) {
			String[] credits = value.split("@");
			String reportType = Constants.REPORT_TYPE_CREDIT;
			String creditCode = credits[0];
			String companyName = credits[1];
			String reportPath = pdfPropsConfig.getDataPath();
			;
			File file = new File(reportPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			log.info("execCreditReport: creditCode:{},companyName:{},reportPath:{},", creditCode, companyName, reportPath);
			try {
				Map<String, Object> dataMap = new HashMap<>(16);
				if (pdfPropsConfig.getDataSource().equalsIgnoreCase("tianyancha")){
					dataMap = pdfInfoService.getTianYanChaPdfMap(reportType, creditCode, companyName);
				}else {
					log.info("没有找到对应的数据源,不能提供数据");
				}
				//pdf标头的图片位置.
				dataMap.put("companyName", companyName);
				dataMap.put("reportNo", creditCode);
				dataMap.put("imagePath", pdfPropsConfig.getImgUrl());
				dataMap.put("reportDate", DateUtils.getNowDate());
				freemarker.template.Configuration configuration = freeMarkerConfigurer.getConfiguration();
				try {
					configuration.setClassForTemplateLoading(DataReportTask.class.getClass(), "/templetes");
				} catch (Exception e) {
				}
				String dataPath = pdfPropsConfig.getDataPath();
				dataPath = dataPath + CommonUtils.getSplit() + reportType + CommonUtils.getSplit();
				pdfInfoService.createHtml(dataPath, companyName,  Constants.CREDIT_REPORT_TEMPLATE_NAME, creditCode, configuration, dataMap);
				boolean result = pdfInfoService.changeHtmlToPdf(pdfPropsConfig.getFontPath(), dataPath, companyName, creditCode, null, pdfPropsConfig.getWaterMark(), null);
				if (!result) {
					//生成PDF失败，再次放入redis队列，等待下次处理
					redisTemplate.opsForList().leftPush(Constants.CREDIT_REPORT_KEY, value);
					log.info("生成信用报告失败，将企业信息：" + value +" 再次放入redis队列，等待下次处理");
				}
			} catch (Exception e) {
				//生成PDF失败，再次放入redis队列，等待下次处理
				redisTemplate.opsForList().leftPush(Constants.CREDIT_REPORT_KEY, value);
				log.info("生成信用报告失败，将企业信息：" + value +" 再次放入redis队列，等待下次处理", e);
			}
		}
	}
	
	/**
	 * 获得实例.
	 * @return
	 */
	private CRResult getCrResult() {
		String json="{\"a1\":\"100.0000\",\"a1Desc\":\"企业规模评分\",\"a1Level\":\"L5\",\"a1LevelDesc\":\"信用好\",\"a7\":\"36" +
				".8421\",\"a7Desc\":\"企业背景评分\",\"a7Level\":\"L2\",\"a7LevelDesc\":\"信用较差\",\"a8\":\"36.8421\"," +
				"\"a8Desc\":\"财务状况评分\",\"a8Level\":\"L4\",\"a8LevelDesc\":\"信用较好\",\"a9\":\"80.0000\"," +
				"\"a9Desc\":\"行业展望评分\",\"a9Level\":\"L4\",\"a9LevelDesc\":\"信用较好\",\"aA\":\"66.6456\"," +
				"\"aADesc\":\"目标企业信用评分\",\"aALevel\":\"L4\",\"aALevelDesc\":\"信用较好\",\"code\":\"0\"," +
				"\"companyName\":\"小猫钓鱼\",\"crAmount\":\"1000\",\"crLevel\":\"CR3\",\"crLevelDesc\":\"风险低于平均水平\"," +
				"\"crOriginalAmt\":\"1000.0001\",\"crRuleAmt\":\"0\",\"crSuggest\":\"可以正常信贷条件与其交易\"," +
				"\"crTime\":\"2020-04-27 15:21:50\",\"desc\":\"SUCCESS\",\"evaluteId\":30," +
				"\"financeDate\":\"2018-12-31\",\"oA\":\"3\",\"oADesc\":\"信用级别\",\"oALevel\":\"Level3\"," +
				"\"oALevelDesc\":\"信用级别:CR3\"}";
		return JSON.parseObject(json,CRResult.class);
	}
	
	/**
	 * 根据统一社会信用代码查询企业财务数据并组织成CR 报告中需要的格式
	 * @param creditCode
	 * @return
	 */
	private FinanceInfo getFinSummaryInfo(String creditCode) {
		FinanceInfo finInfo = null;
		// 查询财务数据
		List<Map<String, Object>> entEmpfinInfoList = new ArrayList<>();  //dataService.getEntEmpfinInfoByCreditCode(creditCode);
		if (entEmpfinInfoList != null && !entEmpfinInfoList.isEmpty()) {
			finInfo = FinanceInfo.builder().build();
			
			String thisYear = DateUtils.getYear();
			finInfo.setThisYear(thisYear);
			
			String lastOne = DateUtils.getLastNumYear(1);
			finInfo.setLastOne(lastOne);
			
			String lastTwo = DateUtils.getLastNumYear(2);
			finInfo.setLastTwo(lastTwo);
			
			for (Map<String, Object> entEmpfinInfo : entEmpfinInfoList) {
				if (entEmpfinInfo.get("infoKey").equals(lastTwo)) {
					switch ((String)entEmpfinInfo.get("infoId")) {
						case "yysr" :
							finInfo.setLastTwoYysr((String)entEmpfinInfo.get("infoValue"));
							break;
						case "jlr" :
							finInfo.setLastTwoJlr((String)entEmpfinInfo.get("infoValue"));
							break;
						case "zcze" :
							finInfo.setLastTwoZcze((String)entEmpfinInfo.get("infoValue"));
							break;
						case "fzhj" :
							finInfo.setLastTwoFzhj((String)entEmpfinInfo.get("infoValue"));
							break;
						default:
							break;
					}
				} else if (entEmpfinInfo.get("infoKey").equals(lastOne)) {
					switch ((String)entEmpfinInfo.get("infoId")) {
						case "yysr" :
							finInfo.setLastOneYysr((String)entEmpfinInfo.get("infoValue"));
							break;
						case "jlr" :
							finInfo.setLastOneJlr((String)entEmpfinInfo.get("infoValue"));
							break;
						case "zcze" :
							finInfo.setLastOneZcze((String)entEmpfinInfo.get("infoValue"));
							break;
						case "fzhj" :
							finInfo.setLastOneFzhj((String)entEmpfinInfo.get("infoValue"));
							break;
						default:
							break;
					}
				} else if (entEmpfinInfo.get("infoKey").equals(thisYear)) {
					switch ((String)entEmpfinInfo.get("infoId")) {
						case "yysr" :
							finInfo.setThisYearYysr((String)entEmpfinInfo.get("infoValue"));
							break;
						case "jlr" :
							finInfo.setThisYearJlr((String)entEmpfinInfo.get("infoValue"));
							break;
						case "zcze" :
							finInfo.setThisYearZcze((String)entEmpfinInfo.get("infoValue"));
							break;
						case "fzhj" :
							finInfo.setThisYearFzhj((String)entEmpfinInfo.get("infoValue"));
							break;
						default:
							break;
					}
				}
			}
		}
		return finInfo;
	}
}
