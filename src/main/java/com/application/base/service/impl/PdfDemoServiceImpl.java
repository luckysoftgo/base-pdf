package com.application.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.application.base.config.PdfPropsConfig;
import com.application.base.service.PdfDemoService;
import com.application.base.util.CommonUtils;
import com.application.base.util.PdfOperUtils;
import com.application.base.util.PhantomJsUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author : 孤狼
 * @NAME: PdfDemoServiceImpl
 * @DESC: PdfDemoServiceImpl类设计
 **/
@Service
public class PdfDemoServiceImpl implements PdfDemoService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PdfPropsConfig pdfPropsConfig;
	
	@Override
	public boolean createHtml(FreeMarkerConfigurer freemarkerConfig, Map<String, Object> map) {
		String htmlRealPath=getDataPath();
		String htmlFilePath = htmlRealPath + "html" + System.getProperty("file.separator")+ String.valueOf(map.get("companyName"))+".html";
		File htmlFile = new File(htmlFilePath);
		Configuration configuration = freemarkerConfig.getConfiguration();
		File parentFile = new File(htmlRealPath);
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		// html文件存在则删除
		if (htmlFile.exists()) {
			htmlFile.delete();
		}
		try {
			// 获得模板对象
			String reportName = Objects.toString(map.get("reportName"));
			Template template = configuration.getTemplate(reportName);
			// 创建文件
			htmlFile.createNewFile();
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			// 合并输出 创建页面文件
			template.process(map, out);
			out.flush();
			out.close();
			map.put("htmlFilePath", htmlFilePath);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean changeHtmlToPdf(Map<String, Object> map, String sign, String encrypt) {
		try {
			String pdfRealPath=getDataPath();
			String htmlFileName = Objects.toString(map.get("htmlFilePath"));
			String pdfFileName = Objects.toString(map.get("companyName"))+".pdf";
			String pdfFilePath = pdfRealPath +"pdf"+ System.getProperty("file.separator") + pdfFileName;
			File pdfParentFile = new File(pdfRealPath);
			File file = new File(pdfFilePath);
			if (!pdfParentFile.exists()) {
				pdfParentFile.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			// HTML文件转字符串
			String htmlContent = PhantomJsUtils.getHtmlContent(htmlFileName);
			PhantomJsUtils.htmlToPdf(pdfRealPath, pdfFileName, htmlContent);
			dealAfter(pdfRealPath,pdfFileName,map,sign,encrypt);
			map.put("pdfFilePath", pdfFilePath);
			map.put("documentName", pdfFileName);
			return true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 处理后续事宜.
	 * @param pdfFileName
	 * @param map
	 * @param sign
	 * @param encrypt
	 */
	private boolean dealAfter(String pdfRealPath,String pdfFileName, Map<String, Object> map, String sign, String encrypt) {
		String inputFile= pdfRealPath + System.getProperty("file.separator") + pdfFileName;
		File file = new File(pdfRealPath+"pdf"+ System.getProperty("file.separator") +"watermark");
		if (!file.exists()){
			file.mkdirs();
		}
		String outputFile = pdfRealPath+"pdf"+ System.getProperty("file.separator") +"watermark"+ System.getProperty("file.separator")+ pdfFileName;
		if (CommonUtils.isNotBlank(sign) && CommonUtils.isBlank(encrypt)){
			String watermark = pdfPropsConfig.getWaterMark();
			outputFile = pdfRealPath+"pdf"+ System.getProperty("file.separator")+ pdfFileName;
			return PdfOperUtils.waterMark(inputFile,outputFile,watermark,null,null);
		}else if (CommonUtils.isNotBlank(encrypt) && CommonUtils.isBlank(sign)){
			Map<String,Object> baseInfo = (Map<String, Object>) map.get("baseInfo");
			if (!baseInfo.isEmpty()){
				String userPass = Objects.toString(baseInfo.get("creditCode"));
				if (CommonUtils.isNotBlank(userPass)){
					return PdfOperUtils.readOnly(inputFile,userPass,userPass);
				}
			}
		}else if (CommonUtils.isNotBlank(sign) && CommonUtils.isNotBlank(encrypt)){
			String watermark = pdfPropsConfig.getWaterMark();
			boolean result = PdfOperUtils.waterMark(inputFile,outputFile,watermark,null,null);
			if (!result){
				return result;
			}
			File existFile=new File(inputFile);
			if (existFile.exists()){
				existFile.delete();
			}
			PdfOperUtils.copyFile(outputFile,inputFile);
			Map<String,Object> baseInfo = (Map<String, Object>) map.get("baseInfo");
			if (!baseInfo.isEmpty()){
				String userPass = Objects.toString(baseInfo.get("creditCode"));
				if (CommonUtils.isNotBlank(userPass)){
					return PdfOperUtils.readOnly(inputFile,userPass,userPass);
				}
			}else{
				logger.info("没有取得认证信息,无法进行加密!");
				return false;
			}
			
		}
		return false;
	}
	
	@Override
	public Map<String, Object> getPdfMap() {
		Map<String,Object> params = new HashMap<>();
		params.put("companyName","小猫钓鱼营销策划有限公司");
		params.put("impagePath",pdfPropsConfig.getImgUrl());
		params.put("reportVerson","测试版");
		params.put("reportNo","CREDIT2020");
		Map<String,Object> baseInfo = new HashMap<>();
		baseInfo.put("regStatus", "在业");
		baseInfo.put("estiblishTime", "2013-10-23");
		baseInfo.put("regCapital", "120万元人民币");
		baseInfo.put("regInstitute", "地球村99号");
		baseInfo.put("staffNumRange", "None");
		baseInfo.put("businessScope","企业品牌策划;企业管理策划;经济信息咨询;商务信息咨询;企业形象策划");
		baseInfo.put("taxNumber", "9161013aaa003296T");
		baseInfo.put("industry", "商务服务业");
		baseInfo.put("regLocation", "地球村77号");
		baseInfo.put("legalPersonName", "徐乃兴");
		baseInfo.put("regNumber", "61010xxx9192478");
		baseInfo.put("creditCode", "9161013aaa003296T");
		baseInfo.put("phoneNumber", "029-83754183");
		baseInfo.put("name", "小猫钓鱼营销策划有限公司");
		baseInfo.put("fromTime", "2013-10-23");
		baseInfo.put("companyOrgType", "有限责任公司");
		baseInfo.put("logo", "http://img3.cha.com/api/4dedcd1c667319c22cf88d765b58404c.png");
		baseInfo.put("updatetime", "2019-12-13");
		baseInfo.put("orgNumber", "081131800");
		baseInfo.put("email", "100010@qq.com");
		
		params.put("baseInfo",baseInfo);
		Map<String,Object> outline = new HashMap<>();
		outline.put("name","小猫钓鱼营销策划有限公司");
		//信用二维码.
		outline.put("regstatus","在业");
		//续存年限
		outline.put("subsist",null);
		//企业标签
		outline.put("tags","娱乐");
		//股权变更
		outline.put("stockright",0);
		outline.put("patentcount",0);
		outline.put("copyright",0);
		outline.put("trademark",0);
		outline.put("punish",0);
		outline.put("website","www.website.com");
		params.put("outline",outline);
		params.put("creditCode", "9161013aaa003296T");
		getImageData(params);
		
		return params;
	}
	
	/**
	 * 仪表盘和雷达图数据设置.
	 */
	private void getImageData(Map<String, Object> params) {
		params.put("creditscore","99");
		params.put("creditTag","信用良好");
		Integer[] infoArray = {87,78,99,66};
		params.put("creditability", JSON.toJSONString(infoArray));
	}
	
	/**
	 * 信用积分
	 * */
	@Override
	public String createScoreImg(Map<String, Object> map) {
		String tyshxydm = Objects.toString(map.get("creditCode"));
		String creditTag = Objects.toString(map.get("creditTag"));
		String score = Objects.toString(map.get("creditscore"),"0");
		Integer creditscore = Integer.parseInt(score);
		String options = "option = {\n" +
				"        series: [{\n" +
				"            name: '工业企业信用',\n" +
				"            type: 'gauge',\n" +
				"            radius: '60%',\n" +
				"            min: 30,\n" +
				"            max: 150,\n" +
				"            splitNumber: 12,\n" +
				"            startAngle: 210,\n" +
				"            endAngle: -30,\n" +
				"            axisLine: {\n" +
				"                lineStyle: {\n" +
				"                    color: [\n" +
				"                        [0.083, '#d60e03'],\n" +
				"                        [0.167, '#f20e02'],\n" +
				"                        [0.25, '#fa2f00'],\n" +
				"                        [0.333, '#ec671c'],\n" +
				"                        [0.417, '#fa5800'],\n" +
				"                        [0.5, '#fa8700'],\n" +
				"                        [0.583, '#ffbb00'],\n" +
				"                        [0.667, '#fae000'],\n" +
				"                        [0.75, '#fbe360'],\n" +
				"                        [0.833, '#b4d43e'],\n" +
				"                        [0.917, '#69d728'],\n" +
				"                        [1, '#4db90d'],\n" +
				"                    ],\n" +
				"                    width: 8\n" +
				"                }\n" +
				"            },\n" +
				"            splitLine: {\n" +
				"                show: true,\n" +
				"            },\n" +
				"            axisTick: {\n" +
				"                show: false\n" +
				"            },\n" +
				"            pointer: {\n" +
				"                show: true,\n" +
				"                length: '70%',\n" +
				"                width: 5,\n" +
				"            },\n" +
				"            title: {\n" +
				"                show: false\n" +
				"            },\n" +
				"            axisLabel: {\n" +
				"                show: true,\n" +
				"                fontSize: 8\n" +
				"            },\n" +
				"            detail: {\n" +
				"                formatter: function () {\n" +
				"                    return '"+creditTag+"'\n" +
				"                },\n" +
				"                offsetCenter: [0, '75%'],\n" +
				"                fontSize: '16',\n" +
				"                fontWeight: 700\n" +
				"            },\n" +
				"            data: [{\n" +
				"                value: "+creditscore+"\n" +
				"            }]\n" +
				"        },\n" +
				"            {\n" +
				"                name: '信用等级',\n" +
				"                title: {\n" +
				"                    color: \"#444\",\n" +
				"                    fontSize: '16',\n" +
				"                    offsetCenter: [0, '-55%']\n" +
				"                },\n" +
				"                type: 'gauge',\n" +
				"                radius: '50%',\n" +
				"                min: 30,\n" +
				"                max: 150,\n" +
				"                splitNumber: 12,\n" +
				"                startAngle: 210,\n" +
				"                endAngle: -30,\n" +
				"                axisLine: {\n" +
				"                    show: false,\n" +
				"                    lineStyle: {\n" +
				"                        color: [\n" +
				"                            [0.083, '#d60e03'],\n" +
				"                            [0.167, '#f20e02'],\n" +
				"                            [0.25, '#fa2f00'],\n" +
				"                            [0.333, '#ec671c'],\n" +
				"                            [0.417, '#fa5800'],\n" +
				"                            [0.5, '#fa8700'],\n" +
				"                            [0.583, '#ffbb00'],\n" +
				"                            [0.667, '#fae000'],\n" +
				"                            [0.75, '#fbe360'],\n" +
				"                            [0.833, '#b4d43e'],\n" +
				"                            [0.917, '#69d728'],\n" +
				"                            [1, '#4db90d'],\n" +
				"                        ],\n" +
				"                        width: 3\n" +
				"                    }\n" +
				"                },\n" +
				"                detail: {\n" +
				"                    formatter: function () {\n" +
				"                        return "+creditscore+"\n" +
				"                    },\n" +
				"                    offsetCenter: [0, '60%'],\n" +
				"                    fontSize: 16,\n" +
				"                    fontWeight: 700\n" +
				"                },\n" +
				"                splitLine: {\n" +
				"                    show: false,\n" +
				"                },\n" +
				"                axisLabel: {\n" +
				"                    show: false\n" +
				"                },\n" +
				"                pointer: {\n" +
				"                    show: false\n" +
				"                },\n" +
				"                data: [{\n" +
				"                    value: "+creditscore+"\n" +
				"                }]\n" +
				"            }\n" +
				"        ]\n" +
				"    };";
		String type = "socre";
		String scorePath = getDataPath()+tyshxydm+System.getProperty("file.separator");
		return PhantomJsUtils.generateImgEChart(scorePath,options,tyshxydm,type);
	}
	
	/**
	 * 雷达图
	 * */
	@Override
	public String createRadarImg(Map<String, Object> map) {
		String tyshxydm = Objects.toString(map.get("creditCode"));
		String creditablity = Objects.toString(map.get("creditability"));
		String options = "option = {\n" +
				"        color: \"#333\",\n" +
				"        tooltip: {\n" +
				"            trigger: \"item\"\n" +
				"        },\n" +
				"        radar: [\n" +
				"            {\n" +
				"                indicator: [\n" +
				"                    {\n" +
				"                        text: \"基本素质\",\n" +
				"                        max: 100\n" +
				"                    },\n" +
				"                    {\n" +
				"                        text: \"行政能力\",\n" +
				"                        max: 100\n" +
				"                    },\n" +
				"                    {\n" +
				"                        text: \"产品潜力\",\n" +
				"                        max: 100\n" +
				"                    },\n" +
				"                    {\n" +
				"                        text: \"金融活动\",\n" +
				"                        max: 100\n" +
				"                    }\n" +
				"                ],\n" +
				"                center: [\"50%\", \"50%\"],\n" +
				"                radius: '60%',\n" +
				"                startAngle: 90,\n" +
				"                splitNumber: 5,\n" +
				"                shape: \"circle\",\n" +
				"                name: {\n" +
				"                    show: true,\n" +
				"                    formatter: \"{value}\",\n" +
				"                    fontSize:'16',\n" +
				"                    textStyle: {\n" +
				"                        color: \"#999\"\n" +
				"                    }\n" +
				"                },\n" +
				"                nameGap: 5,\n" +
				"                splitArea: {\n" +
				"                    areaStyle: {\n" +
				"                        color: [\n" +
				"                            \"rgba(114, 172, 209, 0.2)\",\n" +
				"                            \"rgba(114, 172, 209, 0.4)\",\n" +
				"                            \"rgba(114, 172, 209, 0.6)\",\n" +
				"                            \"rgba(114, 172, 209, 0.8)\",\n" +
				"                            \"rgba(114, 172, 209, 1)\"\n" +
				"                        ],\n" +
				"                        shadowColor: \"rgba(0, 0, 0, 0.3)\",\n" +
				"                        shadowBlur: 10\n" +
				"                    }\n" +
				"                },\n" +
				"                axisLine: {\n" +
				"                    lineStyle: {\n" +
				"                        color: \"rgba(255, 255, 255, 0.5)\"\n" +
				"                    }\n" +
				"                },\n" +
				"                splitLine: {\n" +
				"                    lineStyle: {\n" +
				"                        color: \"rgba(255, 255, 255, 0.5)\"\n" +
				"                    }\n" +
				"                }\n" +
				"            }\n" +
				"        ],\n" +
				"        series: [\n" +
				"            {\n" +
				"                type: \"radar\",\n" +
				"                itemStyle:{\n" +
				"                    normal: {\n" +
				"                        label:{\n" +
				"                           show:true,\n" +
				"                           color:'red',\n" +
				"                           position:'insideLeft',\n" +
				"                           fontWeight:'bold',\n" +
				"                           fontSize:30\n" +
				"                        },\n" +
				"                        areaStyle: {\n" +
				"                            type: \"default\",\n" +
				"                        },\n" +
				"                        color: \"rgba(255, 255, 255, 0.5)\",\n" +
				"                        borderColor: \"rgba(0,0,0,0.5)\",\n" +
				"                        shadowColor: \"rgba(0,0,0,0.5)\"\n" +
				"                    }\n" +
				"                },\n" +
				"                data: [\n" +
				"                    {\n" +
				"                        value: "+creditablity+",\n" +
				"                        name: \"信用能力\",\n" +
				"                        symbol: \"circle\",\n" +
				"                        symbolSize: 5,\n" +
				"                    }\n" +
				"                ]\n" +
				"            }\n" +
				"        ]\n" +
				"    };";
		String type = "image";
		String imgPath = getDataPath()+tyshxydm+System.getProperty("file.separator");
		return PhantomJsUtils.generateImgEChart(imgPath,options,tyshxydm,type);
	}
	
	/**
	 * 获取文件的路径.
	 * @return
	 */
	private String getDataPath(){
		PdfPropsConfig.DataPath dataPath = pdfPropsConfig.getDataPath();
		String realPath ="";
		if (CommonUtils.isLinux()){
			realPath = dataPath.getLinux();
		}else{
			realPath = dataPath.getWindow();
		}
		return realPath;
	}
}
