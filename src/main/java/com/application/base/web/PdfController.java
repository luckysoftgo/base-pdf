package com.application.base.web;

import com.alibaba.fastjson.JSON;
import com.application.base.config.PdfPropsConfig;
import com.application.base.service.PdfDemoService;
import com.application.base.util.toolpdf.CommonUtils;
import freemarker.template.Configuration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author : 孤狼
 * @NAME: PdfController
 * @DESC: PdfController类设计
 **/
@RestController
@RequestMapping("/pdf")
public class PdfController {
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private PdfDemoService pdfDemoService;
	
	@Autowired
	private PdfPropsConfig pdfPropsConfig;
	
	/**
	 * api pdf 创建.
	 */
	@RequestMapping(value = "/createPdf", method = RequestMethod.GET)
	public void createPdf(HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> resultMap = new HashMap<>();
		try {
			//是否签名水印.
			String sign = Objects.toString(request.getParameter("sign"),"");
			//是否加密只读.
			String encrypt = Objects.toString(request.getParameter("encrypt"),"");
			//是否印章.
			String seal = Objects.toString(request.getParameter("seal"),"");
			
			Map<String,Object> dataMap = pdfDemoService.getPdfMap(pdfPropsConfig.getImgUrl());
			Integer[] infoArray = {87,78,99,66};
			String creditAbility = JSON.toJSONString(infoArray);
			String dataPath = pdfPropsConfig.getDataPath();
			String radarImg= pdfDemoService.createRadarImg(pdfPropsConfig.getPhantomjsPath(),pdfPropsConfig.getConvetJsPath(),dataPath,"9161013aaa003296T",creditAbility);
			String scoreImg= pdfDemoService.createScoreImg(pdfPropsConfig.getPhantomjsPath(),pdfPropsConfig.getConvetJsPath(),dataPath,"9161013aaa003296T",78);
			dataMap.put("radarImg",radarImg);
			dataMap.put("scoreImg",scoreImg);
			dataMap.put("creditscore",78);
			dataMap.put("creditability",creditAbility);
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
			response.getWriter().println(resultMap);
		} catch (Exception e) {
			try {
				resultMap.put("status", "500");
				resultMap.put("msg", "处理失败");
				response.getWriter().println(resultMap);
			}catch (Exception ex){}
		}
	}
	
	/**
	 * 下载已经存在的报告
	 */
	@RequestMapping(value = "/downloadPdf", method = RequestMethod.GET)
	public void downloadPdf(HttpServletRequest request, HttpServletResponse response){
		InputStream fis = null;
		try {
			String pdfFilePath = Objects.toString(request.getParameter("pdfFilePath"));
			String companyName = Objects.toString(request.getParameter("companyName"));
			companyName = companyName.replaceAll("<em>", "").replaceAll("</em>", "");
			String pdfName = companyName + ".pdf";
			if (CommonUtils.isBlank(pdfFilePath)){
				pdfFilePath = pdfFilePath+System.getProperty("file.separator")+pdfName;
			}
			String header = request.getHeader("User-Agent").toUpperCase();
			if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
				// IE下载文件名空格变+号问题
				pdfName = pdfName.replace("+", "%20");
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/force-download");
			//通过设置头信息给文件命名，也即是，在前端，文件流被接受完还原成原文件的时候会以你传递的文件名来命名
			pdfName = URLEncoder.encode(pdfName, "utf-8");
			response.addHeader("Content-Disposition", String.format("attachment; filename=\"%s\"",pdfName ));
			
			File file = new File(pdfFilePath);
			fis = new FileInputStream(file.getAbsolutePath());
			IOUtils.copy(fis, response.getOutputStream());
		} catch (Exception e) {
			Map<String, Object> map = new HashMap<>();
			map.put("status","500");
			try {
				response.getWriter().println(map);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
