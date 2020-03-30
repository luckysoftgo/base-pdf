package com.application.base.web;

import com.application.base.config.PdfPropsConfig;
import com.application.base.service.PdfDemoService;
import com.application.base.util.CommonUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@Controller
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
			Map<String,Object> params = pdfDemoService.getPdfMap();
			params.put("reportName","newReport.ftl");
			params.put("radarImg",pdfDemoService.createRadarImg(params));
			params.put("scoreImg",pdfDemoService.createScoreImg(params));
			boolean result = pdfDemoService.createHtml(freeMarkerConfigurer,params);
			if (result){
				result=pdfDemoService.changeHtmlToPdf(params,sign,encrypt);
			}
			if (result){
				resultMap.put("status", "200");
				resultMap.put("data",params);
				resultMap.put("msg", "处理成功");
			}else {
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
				PdfPropsConfig.DataPath dataPath = pdfPropsConfig.getDataPath();
				if(CommonUtils.isLinux()){
					pdfFilePath=dataPath.getLinux()+System.getProperty("file.separator")+pdfName;
				}else{
					pdfFilePath=dataPath.getWindow()+System.getProperty("file.separator")+pdfName;
				}
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
