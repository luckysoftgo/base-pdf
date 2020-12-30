package com.application.base.test;

import com.alibaba.fastjson.JSON;
import com.application.base.PdfApplication;
import com.application.base.config.Constants;
import com.application.base.config.PdfPropsConfig;
import com.application.base.datas.dto.CRResult;
import com.application.base.datas.dto.FinanceInfo;
import com.application.base.service.PdfDemoService;
import com.application.base.service.PdfInfoService;
import com.application.base.task.DataReportTask;
import com.application.base.util.DateUtils;
import com.application.base.util.ImageUtils;
import com.application.base.util.toolpdf.CommonUtils;
import com.application.base.web.PdfController;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.*;

/**
 * @author : 孤狼
 * @NAME: BasicTest
 * @DESC: BasicTest类设计
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PdfApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicTest {
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	private PdfDemoService pdfDemoService;
	@Autowired
	private PdfPropsConfig pdfPropsConfig;
	@Autowired
	private PdfInfoService pdfInfoService;
	
	@Test
	public void testDemoPdf() {
		String json = "";
		Map<String, Object> resultMap = new HashMap<>();
		try {
			//是否签名水印.
			String sign = "true";
			//是否加密只读.
			String encrypt = "true";
			//是否印章.
			String seal = "true";
			Map<String, Object> dataMap = pdfDemoService.getPdfMap(pdfPropsConfig.getImgUrl());
			Integer[] infoArray = {87, 78, 99, 66};
			String creditAbility = JSON.toJSONString(infoArray);
			String dataPath = pdfPropsConfig.getDataPath();
			String radarImg = pdfDemoService.createRadarImg(pdfPropsConfig.getPhantomjsPath(), pdfPropsConfig.getConvetJsPath(), dataPath, "9161013aaa003296T", creditAbility);
			String scoreImg = pdfDemoService.createScoreImg(pdfPropsConfig.getPhantomjsPath(), pdfPropsConfig.getConvetJsPath(), dataPath, "9161013aaa003296T", 78);
			dataMap.put("radarImg", radarImg);
			dataMap.put("scoreImg", scoreImg);
			dataMap.put("creditscore", 78);
			dataMap.put("creditability", creditAbility);
			dataMap.put("creditTag", "信用良好");
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			try {
				//指定freemarker的模板地址.
				//configuration.setDirectoryForTemplateLoading(new File("D:/phantomjs211/data/phantomjs/templetes"));
				//本类中的模板地址.
				configuration.setClassForTemplateLoading(PdfController.class.getClass(), "/templetes");
			} catch (Exception e) {
			}
			pdfDemoService.createHtml(dataPath, "小猫钓鱼营销策划有限公司", "pathReport.ftl", "9161013aaa003296T", configuration, dataMap);
			//server.convertHtmlToPdf(pdfPropsConfig.getFontPath(),dataPath,"小猫钓鱼营销策划有限公司","9161013aaa003296T","DataServer","资信云","B",null,null);
			//server.convertHtmlToPdf(pdfPropsConfig.getFontPath(),dataPath,"小猫钓鱼营销策划有限公司","9161013aaa003296T","DataServer","资信云",null,"C","小猫钓鱼营销策划有限公司");
			//server.convertHtmlToPdf(pdfPropsConfig.getFontPath(),dataPath,"小猫钓鱼营销策划有限公司","9161013aaa003296T",null,null,"B","C","小猫钓鱼营销策划有限公司");
			boolean result = pdfDemoService.changeHtmlToPdf(pdfPropsConfig.getFontPath(), dataPath, "小猫钓鱼营销策划有限公司", "9161013aaa003296T", sign, pdfPropsConfig.getWaterMark(), encrypt, seal, "小猫钓鱼营销策划有限公司");
			System.out.println("完成操作");
			if (result) {
				resultMap.put("status", "200");
				resultMap.put("data", dataMap);
				resultMap.put("msg", "处理成功");
			} else {
				resultMap.put("status", "10001");
				resultMap.put("msg", "处理失败");
			}
		} catch (Exception e) {
			try {
				resultMap.put("status", "500");
				resultMap.put("msg", "处理失败");
			} catch (Exception ex) {
			}
		}
	}
	
	@Test
	public void testInfoCreditPdf() {
		String json = getCreditData();
		try {
			String companyName = "孤狼县满肚麻辣香锅";
			String creditCode = "92610822MA707NAX4Q";
			String reportType = Constants.REPORT_TYPE_CREDIT;
			Map<String, Object> dataMap = new HashMap<>();
			try {
				dataMap = JSON.parseObject(json, Map.class);
			} catch (Exception e) {
				e.printStackTrace();
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
			pdfInfoService.createHtml(dataPath, companyName, Constants.CREDIT_REPORT_TEMPLATE_NAME, creditCode, configuration, dataMap);
			boolean result = pdfInfoService.changeHtmlToPdf(pdfPropsConfig.getFontPath(), dataPath, companyName, creditCode, null, pdfPropsConfig.getWaterMark(), null);
			if (!result) {
				log.info("生成信用报告失败，将企业信息再次放入redis队列，等待下次处理");
			}
		} catch (Exception e) {
			log.info("生成信用报告失败，将企业信息再次放入redis队列，等待下次处理", e);
		}
	}
	
	
	@Test
	public void testInfoCrReportPdf() {
		String json = getCrReportData();
		try {
			String companyName = "孤狼县满肚麻辣香锅";
			String creditCode = "92610822MA707NAX4Q";
			String reportType = Constants.REPORT_TYPE_CRAUTH;
			Map<String, Object> dataMap = new HashMap<>();
			try {
				dataMap = JSON.parseObject(json, Map.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			CRResult crResult = getCrResult(); //dataService.getCrResult(Long.parseLong(userId),Long.parseLong(entId));
			//获取数据.
			if (crResult != null) {
				//生成图片.
				String imagePath = ImageUtils.getImage(crResult, pdfPropsConfig, reportType, creditCode);
				dataMap.put("crImage", imagePath);
				String point = crResult.getCrLevel().trim().replaceAll("CR", "");
				dataMap.put("crResult", "locat locat_" + point);
				crResult.setA1(trimZero(Objects.toString(crResult.getA1())));
				crResult.setA7(trimZero(Objects.toString(crResult.getA7())));
				crResult.setA8(trimZero(Objects.toString(crResult.getA8())));
				crResult.setA9(trimZero(Objects.toString(crResult.getA9())));
				crResult.setOA(trimZero(Objects.toString(crResult.getOA())));
				crResult.setAA(trimZero(Objects.toString(crResult.getAA())));
				crResult.setCrAmount(trimZero(Objects.toString(crResult.getCrAmount())));
				dataMap.put("crData", crResult);
			}
			//pdf标头的图片位置.
			dataMap.put("imagePath", pdfPropsConfig.getImgUrl());
			dataMap.put("companyName", companyName);
			dataMap.put("reportDate", DateUtils.getNowDate());
			dataMap.put("reportNo", "CR" + creditCode);
			//财务概要数据
			FinanceInfo finInfo = this.getFinSummaryInfo(creditCode);
			if (finInfo != null) {
				dataMap.put(Constants.CRAUTH_REPORT_TEMPLATE_FININFO_KEY, finInfo);
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
			if (!result) {
				log.info("生成CR报告失败，将企业信息再次放入redis队列，等待下次处理");
			}
		} catch (Exception e) {
			//生成PDF失败，再次放入redis队列，等待下次处理
			log.error("生成CR报告失败，将企业信息再次放入redis队列，等待下次处理", e);
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
	 * 信用数据.
	 *
	 * @return
	 */
	public String getCreditData() {
		return "{\"reportVersion\":\"基础版\",\"baseInfo\":{\"actualCapital\":\"-\",\"regStatus\":\"-\",\"estiblishTime\":\"2014-12-30\",\"regCapital\":\"10\",\"regInstitute\":\"孤狼县工商行政管理局\",\"companyName\":\"孤狼县满肚麻辣香锅\",\"taxNumber\":\"92610822MA707NAX4Q\",\"businessScope\":\"中餐类制售（不含凉菜、生食海产品、裱花蛋糕）（有效期至：2017.12.29）（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\"industry\":\"正餐服务\",\"regLocation\":\"陕西省我市孤狼县兴茂国际\",\"RYGM\":\"-\",\"EMAIL\":\"-\",\"QYWZ\":\"-\",\"legalPersonName\":\"曾建平\",\"LZDH\":\"-\",\"regNumber\":\"612723610033224\",\"approvedTime\":\"2014-12-30\",\"companyOrgType\":\"个体\",\"orgNumber\":\"-\",\"NSRZZ\":\"-\",\"CBRS\":\"-\"},\"creditCode\":\"92610822MA707NAX4Q\",\"imagePath\":\"\",\"companyName\":\"孤狼县满肚麻辣香锅\",\"blankSpace\":\"-\",\"reportDatas\":{\"企业登记信息\":[{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"企业登记信息\",\"tagId\":\"e07cb622dc4a434dbfc3f00b0e2b8ef4\",\"tagName\":\"出资人及出资信息\",\"instance\":{\"data\":[{\"CZZB\":\"-\",\"GDMC\":\"-\",\"DJRQ\":\"-\",\"RJCZ\":\"-\"}],\"metadata\":{\"GDMC\":\"出资人名称\",\"CZZB\":\"出资比例\",\"RJCZ\":\"认缴出资（万元）\",\"DJRQ\":\"出资时间\"}}},\"labelName\":\"企业登记信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"企业登记信息\",\"tagId\":\"6266af9b382b4d6dae184389afb8f8a4\",\"tagName\":\"分支机构信息\",\"instance\":{\"data\":[{\"FZJG\":\"-\",\"FDDBRXM\":\"-\",\"CLRQ\":\"-\",\"JYZT\":\"-\"}],\"metadata\":{\"FZJG\":\"企业名称\",\"FDDBRXM\":\"负责人\",\"CLRQ\":\"成立日期\",\"JYZT\":\"经营状态\"}}},\"labelName\":\"企业登记信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"企业登记信息\",\"tagId\":\"b6754c69d6f84c78b0d4fb1c97ffb5f6\",\"tagName\":\"对外投资\",\"instance\":{\"data\":[{\"XY010101\":\"-\",\"XY023703\":\"-\",\"XY010105\":\"-\",\"JYZT\":\"-\",\"CZBL\":\"-\"}],\"metadata\":{\"XY010101\":\"被投资企业名称\",\"XY010105\":\"法定代表人（姓名）\",\"CZBL\":\"出资比例\",\"XY023703\":\"成立日期\",\"JYZT\":\"经营状态\"}}},\"labelName\":\"企业登记信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"企业登记信息\",\"tagId\":\"f905c58a07cd4ffe9b674d049b231b01\",\"tagName\":\"变更记录\",\"instance\":{\"data\":[{\"XY010101\":\"-\",\"XY010804\":\"-\",\"XY010803\":\"-\",\"XY010802\":\"-\"}],\"metadata\":{\"XY010101\":\"变更项目\",\"XY010803\":\"变更前内容\",\"XY010804\":\"变更后内容\",\"XY010802\":\"变更日期\"}}},\"labelName\":\"企业登记信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"企业登记信息\",\"tagId\":\"633811d0d2fc4ecd9c924f1b2f776f01\",\"tagName\":\"资质信息\",\"instance\":{\"data\":[{\"ZSBH\":\"-\",\"RDRQ\":\"-\",\"ZZMC\":\"-\",\"ZZJZQ\":\"-\",\"RDJGQC\":\"-\"}],\"metadata\":{\"ZZMC\":\"证书名称\",\"ZSBH\":\"证书编号\",\"RDRQ\":\"签发日期\",\"ZZJZQ\":\"有效期限\",\"RDJGQC\":\"发证机关\"}}},\"labelName\":\"企业登记信息\"}],\"法人高管信息\":[{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"法人高管信息\",\"tagId\":\"537e98f6acf94557994c7556b1a846d2\",\"tagName\":\"主要管理人员信息\",\"instance\":{\"data\":[{\"XY011002\":\"-\",\"XY010105\":\"-\"}],\"metadata\":{\"XY010105\":\"姓名\",\"XY011002\":\"职位\"}}},\"labelName\":\"法人高管信息\"}],\"行政监管信息\":[{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"行政监管信息\",\"tagId\":\"709de3ea22444116856e2bba796c2c4c\",\"tagName\":\"监管信息\",\"instance\":{\"data\":[{\"CCRQ\":\"-\",\"CCFS\":\"-\",\"CCJG\":\"-\",\"SSJG\":\"-\"}],\"metadata\":{\"CCRQ\":\"日期\",\"CCFS\":\"类型\",\"CCJG\":\"结果\",\"SSJG\":\"检查实施机关\"}}},\"labelName\":\"行政监管信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"行政监管信息\",\"tagId\":\"3169e3c81d6f476ca81fb537748ccafe\",\"tagName\":\"经营异常信息\",\"instance\":{\"data\":[{\"XY023102\":\"2015-07-09\",\"XY023101\":\"个体工商户未按照《个体工商户年度报告办法》规定报送年度报告\",\"XY023105\":\"孤狼县工商行政管理局\",\"XY023104\":\"-\",\"XY023103\":\"-\"}],\"metadata\":{\"XY023102\":\"列入日期\",\"XY023101\":\"列入经营异常名录原因\",\"XY023105\":\"列入决定机关\",\"XY023104\":\"移出日期\",\"XY023103\":\"移出经营异常名录原因\"}}},\"labelName\":\"行政监管信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"行政监管信息\",\"tagId\":\"ea9fa1cac64b41da86ea5d640e55e746\",\"tagName\":\"行政许可信息\",\"instance\":{\"data\":[{\"XK_YXQZ\":\"-\",\"XK_XKWS\":\"-\",\"XKJG\":\"-\",\"GSJZQ\":\"-\",\"XK_XKZS\":\"-\",\"XKNR\":\"-\"}],\"metadata\":{\"XK_XKWS\":\"许可文书编号\",\"XK_XKZS\":\"许可文件名称\",\"XK_YXQZ\":\"起始日期\",\"GSJZQ\":\"许可截止日期\",\"XKJG\":\"许可机关\",\"XKNR\":\"许可内容\"}}},\"labelName\":\"行政监管信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"行政监管信息\",\"tagId\":\"2112afd0ff4740269c031b9a3f2065bd\",\"tagName\":\"行政处罚信息\",\"instance\":{\"data\":[{\"CFJDSWH\":\"-\",\"CFLBY\":\"-\",\"CFSY\":\"-\",\"CFJGMC\":\"-\",\"CFSXQ\":\"-\"}],\"metadata\":{\"CFSXQ\":\"处罚日期\",\"CFJDSWH\":\"决定书文号\",\"CFLBY\":\"处罚类别\",\"CFSY\":\"处罚事由\",\"CFJGMC\":\"处罚机构\"}}},\"labelName\":\"行政监管信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"行政监管信息\",\"tagId\":\"8664ae2c43da4f629dd5c25ea5ddedaa\",\"tagName\":\"行政奖励信息\",\"instance\":{\"data\":[{\"SYJG\":\"-\",\"RDWSH\":\"-\",\"JLMC\":\"-\",\"JLSYRQ\":\"-\",\"JLXS\":\"-\"}],\"metadata\":{\"JLSYRQ\":\"奖励日期\",\"JLXS\":\"奖励事项\",\"RDWSH\":\"证书编号\",\"JLMC\":\"荣誉事项内容\",\"SYJG\":\"奖励机关\"}}},\"labelName\":\"行政监管信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"行政监管信息\",\"tagId\":\"393967310f644946a5340a414c4a9536\",\"tagName\":\"守信激励\",\"instance\":{\"data\":[{\"RDRQ\":\"-\",\"RYMC\":\"-\",\"RDJGQC\":\"-\"}],\"metadata\":{\"RDRQ\":\"发布年度\",\"RYMC\":\"类型\",\"RDJGQC\":\"发布单位\"}}},\"labelName\":\"行政监管信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"行政监管信息\",\"tagId\":\"c979e566c6d648d4b1347a06eead912d\",\"tagName\":\"失信惩戒\",\"instance\":{\"data\":[{\"LRRQ\":\"-\",\"RDJG\":\"-\",\"LRSY\":\"-\"}],\"metadata\":{\"LRRQ\":\"发布年度\",\"LRSY\":\"违法事实\",\"RDJG\":\"发布单位\"}}},\"labelName\":\"行政监管信息\"}],\"司法信息\":[{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"司法信息\",\"tagId\":\"\",\"tagName\":\"立案信息\",\"instance\":{\"data\":[{\"BG\":\"-\",\"AH\":\"-\",\"AJZT\":\"-\",\"YG\":\"-\",\"LASJ\":\"-\"}],\"metadata\":{\"LASJ\":\"立案时间\",\"AH\":\"案号\",\"AJZT\":\"案件状态\",\"BG\":\"被告人/被告/被上诉人/被申请人\",\"YG\":\"公诉人/原告/上诉人/申请人\"}}},\"labelName\":\"司法信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"司法信息\",\"tagId\":\"\",\"tagName\":\"开庭公告\",\"instance\":{\"data\":[{\"BG\":\"-\",\"AH\":\"-\",\"AY\":\"-\",\"KTRQ\":\"-\",\"YG\":\"-\",\"KTFY\":\"-\"}],\"metadata\":{\"KTRQ\":\"开庭日期\",\"AH\":\"案号\",\"AY\":\"案由\",\"BG\":\"被告人/被告/被上诉人/被申请人\",\"YG\":\"公诉人/原告/上诉人/申请人\",\"KTFY\":\"开庭法院\"}}},\"labelName\":\"司法信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"司法信息\",\"tagId\":\"\",\"tagName\":\"法律诉讼信息\",\"instance\":{\"data\":[{\"AJMC\":\"-\",\"AJSF\":\"-\",\"FY\":\"-\",\"AH\":\"-\",\"AY\":\"-\",\"KTRQ\":\"-\"}],\"metadata\":{\"KTRQ\":\"开庭日期\",\"AJMC\":\"案件名称\",\"AH\":\"案号\",\"AY\":\"案由\",\"AJSF\":\"案件身份\",\"FY\":\"法院\"}}},\"labelName\":\"司法信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"司法信息\",\"tagId\":\"\",\"tagName\":\"被执行人信息\",\"instance\":{\"data\":[{\"AH\":\"-\",\"ZXBD\":\"-\",\"ZXFY\":\"-\",\"LASJ\":\"-\"}],\"metadata\":{\"LASJ\":\"立案时间\",\"ZXBD\":\"执行标的\",\"AH\":\"案号\",\"ZXFY\":\"执行法院\"}}},\"labelName\":\"司法信息\"}],\"认定名录\":[{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"认定名录\",\"tagId\":\"f4bf7e94279b460580b7cf2868950c66\",\"tagName\":\"招投标信息\",\"instance\":{\"data\":[{\"SPSJ\":\"-\",\"XMMC\":\"-\",\"CGR\":\"-\"}],\"metadata\":{\"SPSJ\":\"发布时间\",\"XMMC\":\"项目名称\",\"CGR\":\"采购人\"}}},\"labelName\":\"认定名录\"}],\"知识产权\":[{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"知识产权\",\"tagId\":\"\",\"tagName\":\"专利信息\",\"instance\":{\"data\":[{\"FLZT\":\"-\",\"ZLMC\":\"-\",\"SQGBH\":\"-\",\"SQH\":\"-\",\"SQRQ\":\"-\",\"LX\":\"-\",\"SQGBR\":\"-\"}],\"metadata\":{\"ZLMC\":\"专利名称\",\"LX\":\"类型\",\"SQGBH\":\"申请公布号\",\"SQH\":\"申请号\",\"SQGBR\":\"申请公布日\",\"FLZT\":\"法律状态\",\"SQRQ\":\"申请日期\"}}},\"labelName\":\"知识产权\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"知识产权\",\"tagId\":\"\",\"tagName\":\"商标信息\",\"instance\":{\"data\":[{\"SBMC\":\"-\",\"LB\":\"-\",\"ZT\":\"-\",\"SQRQ\":\"-\",\"SBTP\":\"-\",\"ZCH\":\"-\"}],\"metadata\":{\"SQRQ\":\"申请日期\",\"SBTP\":\"商标图片\",\"SBMC\":\"商标名称\",\"ZCH\":\"注册号\",\"LB\":\"类别\",\"ZT\":\"状态\"}}},\"labelName\":\"知识产权\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"知识产权\",\"tagId\":\"\",\"tagName\":\"著作权信息\",\"instance\":{\"data\":[{\"DJH\":\"-\",\"FLHMC\":\"-\",\"BBH\":\"-\",\"RJQC\":\"-\",\"DJPZRQ\":\"-\"}],\"metadata\":{\"DJPZRQ\":\"登记批准日期\",\"RJQC\":\"软件全称\",\"DJH\":\"登记号\",\"FLHMC\":\"分类号名称\",\"BBH\":\"版本号\"}}},\"labelName\":\"知识产权\"}]},\"reportNo\":\"CREDIT2020BASE\",\"templeteReportName\":\"newReport.ftl\"}";
	}
	
	/**
	 * 信用数据.
	 *
	 * @return
	 */
	public String getCrReportData() {
		return "{\"reportVersion\":\"基础版\",\"baseInfo\":{\"actualCapital\":\"-\",\"regStatus\":\"-\",\"estiblishTime\":\"2014-12-30\",\"regCapital\":\"10\",\"regInstitute\":\"孤狼工商行政管理局\",\"companyName\":\"孤狼满肚麻辣香锅\",\"taxNumber\":\"92610822MA707NAX4Q\",\"businessScope\":\"中餐类制售（不含凉菜、生食海产品、裱花蛋糕）（有效期至：2017.12.29）（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\"industry\":\"正餐服务\",\"regLocation\":\"陕西省我市孤狼兴茂国际\",\"RYGM\":\"-\",\"EMAIL\":\"-\",\"QYWZ\":\"-\",\"legalPersonName\":\"曾建平\",\"regNumber\":\"612723610033224\",\"LXDH\":\"15661727766\",\"approvedTime\":\"2014-12-30\",\"companyOrgType\":\"个体\",\"orgNumber\":\"-\",\"NSRZZ\":\"-\",\"CBRS\":\"-\"},\"creditCode\":\"92610822MA707NAX4Q\",\"imagePath\":\"\",\"companyName\":\"孤狼满肚麻辣香锅\",\"blankSpace\":\"-\",\"reportDatas\":{\"出资人及出资信息\":[{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"出资人及出资信息\",\"tagId\":\"e07cb622dc4a434dbfc3f00b0e2b8ef4\",\"tagName\":\"出资人及出资信息\",\"instance\":{\"data\":[{\"CZZB\":\"-\",\"GDMC\":\"-\",\"DJRQ\":\"-\",\"GDLX\":\"-\",\"RJCZ\":\"-\"}],\"metadata\":{\"GDMC\":\"出资人名称\",\"GDLX\":\"出资人类型\",\"RJCZ\":\"认缴出资额（万元）\",\"CZZB\":\"岀资占比\",\"DJRQ\":\"认缴出资日期\"}}},\"labelName\":\"出资人及出资信息\"}],\"对外投资信息\":[{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"对外投资信息\",\"tagId\":\"b6754c69d6f84c78b0d4fb1c97ffb5f6\",\"tagName\":\"对外投资信息\",\"instance\":{\"data\":[{\"XY010133\":\"-\",\"QYLX\":\"-\",\"XY010101\":\"-\",\"ZCZB\":\"-\",\"JYZT\":\"-\",\"XY010105\":\"-\",\"CZBL\":\"-\"}],\"metadata\":{\"XY010101\":\"被投资企业名称\",\"XY010133\":\"统一社会信用代码\",\"QYLX\":\"企业类型\",\"JYZT\":\"经营状态\",\"ZCZB\":\"注册资本（万元）\",\"CZBL\":\"出资比例\",\"XY010105\":\"法定代表人（姓名）\"}}},\"labelName\":\"对外投资信息\"}],\"风险信息\":[{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"风险信息\",\"tagId\":\"\",\"tagName\":\"法律诉讼\",\"instance\":{\"data\":[{\"AJMC\":\"-\",\"AJSF\":\"-\",\"AH\":\"-\",\"AY\":\"-\",\"KTRQ\":\"-\"}],\"metadata\":{\"KTRQ\":\"开庭日期\",\"AJMC\":\"案件名称\",\"AY\":\"案由\",\"AJSF\":\"案件身份\",\"AH\":\"案号\"}}},\"labelName\":\"风险信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"风险信息\",\"tagId\":\"2112afd0ff4740269c031b9a3f2065bd\",\"tagName\":\"行政处罚\",\"instance\":{\"data\":[{\"CFJDSWH\":\"-\",\"CFLBY\":\"-\",\"CFSY\":\"-\",\"CFJGMC\":\"-\",\"CFSXQ\":\"-\"}],\"metadata\":{\"CFSXQ\":\"处罚日期\",\"CFJDSWH\":\"决定书文号\",\"CFLBY\":\"处罚类别\",\"CFSY\":\"处罚事由\",\"CFJGMC\":\"处罚机构\"}}},\"labelName\":\"风险信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"风险信息\",\"tagId\":\"9ef31b794879486b8cb0704166eb2de0\",\"tagName\":\"股权出质\",\"instance\":{\"data\":[{\"XY011201\":\"-\",\"XY011204\":\"-\",\"XY010105\":\"-\",\"XY011205\":\"-\",\"XY011207\":\"-\"}],\"metadata\":{\"XY011207\":\"股权出质设立登记日期\",\"XY011201\":\"登记编号\",\"XY011205\":\"出质人名称\",\"XY010105\":\"质权人名称\",\"XY011204\":\"股权出质额\"}}},\"labelName\":\"风险信息\"}],\"知识产权信息\":[{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"知识产权信息\",\"tagId\":\"\",\"tagName\":\"商标信息\",\"instance\":{\"data\":[{\"SBMC\":\"-\",\"LB\":\"-\",\"ZT\":\"-\",\"SQRQ\":\"-\",\"SBTP\":\"-\",\"ZCH\":\"-\"}],\"metadata\":{\"SBTP\":\"商标图片\",\"SQRQ\":\"申请日期\",\"SBMC\":\"商标名称\",\"ZCH\":\"注册号\",\"LB\":\"类别\",\"ZT\":\"状态\"}}},\"labelName\":\"知识产权信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"知识产权信息\",\"tagId\":\"\",\"tagName\":\"专利信息\",\"instance\":{\"data\":[{\"FLZT\":\"-\",\"ZLMC\":\"-\",\"SQGBH\":\"-\",\"SQH\":\"-\",\"SQRQ\":\"-\",\"LX\":\"-\",\"SQGBR\":\"-\"}],\"metadata\":{\"ZLMC\":\"专利名称\",\"LX\":\"类型\",\"SQGBH\":\"申请公布号\",\"SQH\":\"申请号\",\"SQGBR\":\"申请公布日\",\"FLZT\":\"法律状态\",\"SQRQ\":\"申请日期\"}}},\"labelName\":\"知识产权信息\"}],\"经营信息\":[{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"经营信息\",\"tagId\":\"f4bf7e94279b460580b7cf2868950c66\",\"tagName\":\"招投标\",\"instance\":{\"data\":[{\"SPSJ\":\"-\",\"XMMC\":\"-\",\"CGR\":\"-\"}],\"metadata\":{\"SPSJ\":\"发布时间\",\"XMMC\":\"项目名称\",\"CGR\":\"采购人\"}}},\"labelName\":\"经营信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"经营信息\",\"tagId\":\"393967310f644946a5340a414c4a9536\",\"tagName\":\"守信激励\",\"instance\":{\"data\":[{\"RDRQ\":\"-\",\"RYMC\":\"-\",\"RDJGQC\":\"-\"}],\"metadata\":{\"RDRQ\":\"发布年度\",\"RYMC\":\"类型\",\"RDJGQC\":\"发布单位\"}}},\"labelName\":\"经营信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"经营信息\",\"tagId\":\"633811d0d2fc4ecd9c924f1b2f776f01\",\"tagName\":\"资质证书\",\"instance\":{\"data\":[{\"ZSBH\":\"-\",\"RDRQ\":\"-\",\"ZZMC\":\"-\",\"ZZJZQ\":\"-\",\"RDJGQC\":\"-\"}],\"metadata\":{\"ZZMC\":\"资质名称\",\"ZSBH\":\"证书编号\",\"RDRQ\":\"签发日期\",\"ZZJZQ\":\"有效期限\",\"RDJGQC\":\"发证机关\"}}},\"labelName\":\"经营信息\"},{\"instanceData\":{\"companyId\":\"92610822MA707NAX4Q\",\"labelName\":\"经营信息\",\"tagId\":\"ea9fa1cac64b41da86ea5d640e55e746\",\"tagName\":\"行政许可\",\"instance\":{\"data\":[{\"XK_YXQZ\":\"-\",\"XK_XKWS\":\"-\",\"XKJG\":\"-\",\"GSJZQ\":\"-\",\"XK_XKZS\":\"-\",\"XKNR\":\"-\"}],\"metadata\":{\"XK_XKWS\":\"许可文书编号\",\"XK_XKZS\":\"许可文件名称\",\"XK_YXQZ\":\"起始日期\",\"GSJZQ\":\"许可截止日期\",\"XKJG\":\"许可机关\",\"XKNR\":\"许可内容\"}}},\"labelName\":\"经营信息\"}]},\"reportNo\":\"CR2020BASE\",\"templeteReportName\":\"CRReport.ftl\"}";
	}
	
	
	/**
	 * 根据统一社会信用代码查询企业财务数据并组织成CR 报告中需要的格式
	 *
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
					switch ((String) entEmpfinInfo.get("infoId")) {
						case "yysr":
							finInfo.setLastTwoYysr((String) entEmpfinInfo.get("infoValue"));
							break;
						case "jlr":
							finInfo.setLastTwoJlr((String) entEmpfinInfo.get("infoValue"));
							break;
						case "zcze":
							finInfo.setLastTwoZcze((String) entEmpfinInfo.get("infoValue"));
							break;
						case "fzhj":
							finInfo.setLastTwoFzhj((String) entEmpfinInfo.get("infoValue"));
							break;
						default:
							break;
					}
				} else if (entEmpfinInfo.get("infoKey").equals(lastOne)) {
					switch ((String) entEmpfinInfo.get("infoId")) {
						case "yysr":
							finInfo.setLastOneYysr((String) entEmpfinInfo.get("infoValue"));
							break;
						case "jlr":
							finInfo.setLastOneJlr((String) entEmpfinInfo.get("infoValue"));
							break;
						case "zcze":
							finInfo.setLastOneZcze((String) entEmpfinInfo.get("infoValue"));
							break;
						case "fzhj":
							finInfo.setLastOneFzhj((String) entEmpfinInfo.get("infoValue"));
							break;
						default:
							break;
					}
				} else if (entEmpfinInfo.get("infoKey").equals(thisYear)) {
					switch ((String) entEmpfinInfo.get("infoId")) {
						case "yysr":
							finInfo.setThisYearYysr((String) entEmpfinInfo.get("infoValue"));
							break;
						case "jlr":
							finInfo.setThisYearJlr((String) entEmpfinInfo.get("infoValue"));
							break;
						case "zcze":
							finInfo.setThisYearZcze((String) entEmpfinInfo.get("infoValue"));
							break;
						case "fzhj":
							finInfo.setThisYearFzhj((String) entEmpfinInfo.get("infoValue"));
							break;
						default:
							break;
					}
				}
			}
		}
		return finInfo;
	}
	
	/**
	 * 获得实例.
	 *
	 * @return
	 */
	private CRResult getCrResult() {
		String json = "{\"a1\":\"100.0000\",\"a1Desc\":\"企业规模评分\",\"a1Level\":\"L5\",\"a1LevelDesc\":\"信用好\",\"a7\":\"36" +
				".8421\",\"a7Desc\":\"企业背景评分\",\"a7Level\":\"L2\",\"a7LevelDesc\":\"信用较差\",\"a8\":\"36.8421\"," +
				"\"a8Desc\":\"财务状况评分\",\"a8Level\":\"L4\",\"a8LevelDesc\":\"信用较好\",\"a9\":\"80.0000\"," +
				"\"a9Desc\":\"行业展望评分\",\"a9Level\":\"L4\",\"a9LevelDesc\":\"信用较好\",\"aA\":\"66.6456\"," +
				"\"aADesc\":\"目标企业信用评分\",\"aALevel\":\"L4\",\"aALevelDesc\":\"信用较好\",\"code\":\"0\"," +
				"\"companyName\":\"小猫钓鱼\",\"crAmount\":\"1000\",\"crLevel\":\"CR3\",\"crLevelDesc\":\"风险低于平均水平\"," +
				"\"crOriginalAmt\":\"1000.0001\",\"crRuleAmt\":\"0\",\"crSuggest\":\"可以正常信贷条件与其交易\"," +
				"\"crTime\":\"2020-04-27 15:21:50\",\"desc\":\"SUCCESS\",\"evaluteId\":30," +
				"\"financeDate\":\"2018-12-31\",\"oA\":\"3\",\"oADesc\":\"信用级别\",\"oALevel\":\"Level3\"," +
				"\"oALevelDesc\":\"信用级别:CR3\"}";
		return JSON.parseObject(json, CRResult.class);
	}
}
