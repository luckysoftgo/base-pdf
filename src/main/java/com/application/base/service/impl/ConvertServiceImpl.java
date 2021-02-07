package com.application.base.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.application.base.asponse.AsponseClient;
import com.application.base.config.PdfPropsConfig;
import com.application.base.datas.ReportBody;
import com.application.base.datas.ReportHeader;
import com.application.base.docx4j.Placeholder2WordClient;
import com.application.base.docx4j.vo.DocxImageVO;
import com.application.base.service.ConvertService;
import com.application.base.util.toolpdf.PhantomJsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：孤狼
 * @date ：2021-1-7
 * @description: 转换实现
 * @modified By：
 * @version: 1.0.0
 */
@Slf4j
@Service
public class ConvertServiceImpl implements ConvertService {
	
	@Autowired
	private PdfPropsConfig pdfPropsConfig;
	
	@Autowired
	private Placeholder2WordClient wordClient;
	
	@Autowired
	private AsponseClient asponseClient;
	
	@Override
	public Boolean buildReportCheck(ReportHeader reportHeader) {
		//TODO 请求校验的信息
		return true;
	}
	
	@Override
	public Map<String, String> wordSymbol2files(ReportBody reportBody) {
		String templeteId = reportBody.getTempleteId();
		String docxOrginFile = pdfPropsConfig.getDataPath() + templeteId + ".docx";
		String docxNewFile = pdfPropsConfig.getDataPath() + "temp_" + templeteId + ".docx";
		String pdfPath = pdfPropsConfig.getDataPath() + "temp_" + templeteId + ".pdf";
		String htmlPath = pdfPropsConfig.getDataPath() + "temp_" + templeteId + ".html";
		Map<String, String> resutMap = new HashMap<>();
		try {
			boolean result = wordClient.convert2Word(docxOrginFile, reportBody.getUniqueDataMap(), docxNewFile);
			if (result) {
				//result = OfficeOperateUtil.docxFile2Files(docxNewFile, htmlPath, pdfPath, imageDir, null, Arrays.asList(new String[]{"<p><br /></p>"}));
				result = asponseClient.docxFile2Files(docxNewFile, htmlPath, pdfPath);
			}
			if (result) {
				resutMap.put("pdfPath", pdfPath);
				resutMap.put("htmlPath", htmlPath);
				resutMap.put("docxPath", docxNewFile);
			}
		} catch (Exception e) {
			log.error("文件处理出现了异常,异常信息是:{}", e.getMessage());
		}
		return resutMap;
	}
	
	@Override
	public Map<String, String> automaticInfo2files(ReportBody reportBody) {
		String templeteId = reportBody.getTempleteId();
		String docxOrginFile = pdfPropsConfig.getDataPath() + templeteId + ".docx";
		String docxNewFile = pdfPropsConfig.getDataPath() + "temp_" + templeteId + ".docx";
		String pdfPath = pdfPropsConfig.getDataPath() + "temp_" + templeteId + ".pdf";
		String htmlPath = pdfPropsConfig.getDataPath() + "temp_" + templeteId + ".html";
		String imageDir = pdfPropsConfig.getDataPath() + "image\\";
		List<DocxImageVO> imgInfos = reportBody.getImageInfos();
		List<DocxImageVO> finalImgInfos = new ArrayList<>();
		if (null != imgInfos && imgInfos.size() > 0) {
			for (DocxImageVO imageVO : imgInfos) {
				DocxImageVO tmpVO = new DocxImageVO();
				//图片的绝对路径.
				String imagePath = imageVO.getImagePath();
				//插入图片的位置.
				String searchText = imageVO.getSearchText();
				String imageJson = imageVO.getEchartsOptions();
				String imageName = imageVO.getImageName() == null ? MD5.create().digestHex(imageJson) : imageVO.getImageName();
				if (StringUtils.isBlank(imagePath)) {
					imagePath = PhantomJsUtil.generateImgEChart(pdfPropsConfig.getPhantomjsPath(), pdfPropsConfig.getConvetJsPath(), pdfPropsConfig.getDataPath(), imageJson, imageName);
				}
				tmpVO.setImagePath(imagePath);
				tmpVO.setSearchText(searchText);
				tmpVO.setImageName(imageName);
				finalImgInfos.add(tmpVO);
			}
		}
		Map<String, String> resutMap = new HashMap<>();
		try {
			boolean result = wordClient.automaticInfo2files(docxOrginFile, docxNewFile, reportBody.getUniqueDataMap(), reportBody.getTablesDatas(), finalImgInfos, Boolean.TRUE);
			if (result) {
				//result = OfficeOperateUtil.docxFile2Files(docxNewFile, htmlPath, pdfPath, imageDir, null, Arrays.asList(new String[]{"<p><br /></p>"}));
				result = asponseClient.docxFile2Files(docxNewFile, htmlPath, pdfPath);
			}
			if (result) {
				resutMap.put("pdfPath", pdfPath);
				resutMap.put("htmlPath", htmlPath);
				resutMap.put("docxPath", docxNewFile);
			}
		} catch (Exception e) {
			log.error("文件处理出现了异常,异常信息是:{}", e.getMessage());
		}
		return resutMap;
	}
	
}
