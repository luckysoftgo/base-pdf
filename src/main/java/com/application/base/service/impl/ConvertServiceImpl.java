package com.application.base.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.application.base.config.PdfPropsConfig;
import com.application.base.docx4j.Placeholder2WordClient;
import com.application.base.docx4j.vo.DocxDataVO;
import com.application.base.docx4j.vo.DocxImageVO;
import com.application.base.service.ConvertService;
import com.application.base.util.OfficeOperateUtil;
import com.application.base.util.toolpdf.PhantomJsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
	
	private String basePath = "E:\\home\\pdf\\resources\\data\\";
	
	@Autowired
	private PdfPropsConfig pdfPropsConfig;
	
	@Autowired
	private Placeholder2WordClient wordClient;
	
	@Override
	public Map<String, String> wordSymbol2files(String templeteId, Map<String, String> mappingMap) {
		String docxOrginFile = basePath + templeteId + ".docx";
		String docxNewFile = basePath + "temp_" + templeteId + ".docx";
		String pdfPath = basePath + "temp_" + templeteId + ".pdf";
		String htmlPath = basePath + "temp_" + templeteId + ".html";
		String imageDir = basePath + "image\\";
		Map<String, String> resutMap = new HashMap<>();
		try {
			boolean result = wordClient.convert2Word(docxOrginFile, mappingMap, docxNewFile);
			if (result) {
				result = OfficeOperateUtil.docxFile2Files(docxNewFile, htmlPath, pdfPath, imageDir, null, Arrays.asList(new String[]{"<p><br /></p>"}));
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
	public Map<String, String> wordTable2files(String templeteId, Map<String, String> uniqueDataMap, ArrayList<Map<String, Object>> tableDatas) {
		String docxOrginFile = basePath + templeteId + ".docx";
		String docxNewFile = basePath + "temp_" + templeteId + ".docx";
		String pdfPath = basePath + "temp_" + templeteId + ".pdf";
		String htmlPath = basePath + "temp_" + templeteId + ".html";
		String imageDir = basePath + "image\\";
		Map<String, String> resutMap = new HashMap<>();
		try {
			boolean result = wordClient.convert2TableWord(docxOrginFile, uniqueDataMap, docxNewFile, tableDatas, null);
			if (result) {
				result = OfficeOperateUtil.docxFile2Files(docxNewFile, htmlPath, pdfPath, imageDir, null, Arrays.asList(new String[]{"<p><br /></p>"}));
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
	public Map<String, String> wordTables2files(String templeteId, Map<String, String> uniqueuniqueDataMap, ArrayList<DocxDataVO> tablesDatas) {
		String docxOrginFile = basePath + templeteId + ".docx";
		String docxNewFile = basePath + "temp_" + templeteId + ".docx";
		String pdfPath = basePath + "temp_" + templeteId + ".pdf";
		String htmlPath = basePath + "temp_" + templeteId + ".html";
		String imageDir = basePath + "image\\";
		Map<String, String> resutMap = new HashMap<>();
		try {
			boolean result = wordClient.convert2TablesWord(docxOrginFile, uniqueuniqueDataMap, docxNewFile, tablesDatas);
			if (result) {
				result = OfficeOperateUtil.docxFile2Files(docxNewFile, htmlPath, pdfPath, imageDir, null, Arrays.asList(new String[]{"<p><br /></p>"}));
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
	public Map<String, String> wordImg2files(String templeteId, Map<String, String> uniqueDataMap, DocxImageVO imageVO) {
		String docxOrginFile = basePath + templeteId + ".docx";
		String docxNewFile = basePath + "temp_" + templeteId + ".docx";
		String pdfPath = basePath + "temp_" + templeteId + ".pdf";
		String htmlPath = basePath + "temp_" + templeteId + ".html";
		String imageDir = basePath + "image\\";
		Map<String, String> resutMap = new HashMap<>();
		try {
			boolean result = wordClient.convert2ImgWord(docxOrginFile, uniqueDataMap, docxNewFile, imageVO.getSearchText(), imageVO.getImagePath(), Boolean.TRUE);
			if (result) {
				result = OfficeOperateUtil.docxFile2Files(docxNewFile, htmlPath, pdfPath, imageDir, null, Arrays.asList(new String[]{"<p><br /></p>"}));
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
	public Map<String, String> wordImgs2files(String templeteId, Map<String, String> uniqueDataMap, List<DocxImageVO> imgInfos) {
		String docxOrginFile = basePath + templeteId + ".docx";
		String docxNewFile = basePath + "temp_" + templeteId + ".docx";
		String pdfPath = basePath + "temp_" + templeteId + ".pdf";
		String htmlPath = basePath + "temp_" + templeteId + ".html";
		String imageDir = basePath + "image\\";
		Map<String, String> resutMap = new HashMap<>();
		try {
			boolean result = wordClient.convert2ImgsWord(docxOrginFile, uniqueDataMap, docxNewFile, imgInfos, Boolean.FALSE);
			if (result) {
				result = OfficeOperateUtil.docxFile2Files(docxNewFile, htmlPath, pdfPath, imageDir, null, Arrays.asList(new String[]{"<p><br /></p>"}));
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
	public Map<String, String> wordAutomaticImg2files(String templeteId, Map<String, String> uniqueDataMap, DocxImageVO imageVO) {
		String imageJson = imageVO.getEchartsOptions();
		String imageName = imageVO.getImageName() == null ? MD5.create().digestHex(imageJson) : imageVO.getImageName();
		String searchText = imageVO.getSearchText();
		String imagePath = PhantomJsUtil.generateImgEChart(pdfPropsConfig.getPhantomjsPath(), pdfPropsConfig.getConvetJsPath(), "E:\\home\\pdf\\resources\\data\\", imageJson, imageName);
		log.info("文件路径的地址是:" + imagePath);
		String docxOrginFile = basePath + templeteId + ".docx";
		String docxNewFile = basePath + "temp_" + templeteId + ".docx";
		String pdfPath = basePath + "temp_" + templeteId + ".pdf";
		String htmlPath = basePath + "temp_" + templeteId + ".html";
		String imageDir = basePath + "image\\";
		Map<String, String> resutMap = new HashMap<>();
		try {
			boolean result = wordClient.convert2ImgWord(docxOrginFile, uniqueDataMap, docxNewFile, searchText, imagePath, Boolean.FALSE);
			if (result) {
				result = OfficeOperateUtil.docxFile2Files(docxNewFile, htmlPath, pdfPath, imageDir, null, Arrays.asList(new String[]{"<p><br /></p>"}));
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
	public Map<String, String> wordAutomaticImgs2files(String templeteId, Map<String, String> uniqueDataMap, List<DocxImageVO> imgInfos) {
		String docxOrginFile = basePath + templeteId + ".docx";
		String docxNewFile = basePath + "temp_" + templeteId + ".docx";
		String pdfPath = basePath + "temp_" + templeteId + ".pdf";
		String htmlPath = basePath + "temp_" + templeteId + ".html";
		String imageDir = basePath + "image\\";
		List<DocxImageVO> finalImgInfos = new ArrayList<>();
		for (DocxImageVO imageVO : imgInfos) {
			DocxImageVO tmpVO = new DocxImageVO();
			String imageJson = imageVO.getEchartsOptions();
			String imageName = imageVO.getImageName() == null ? MD5.create().digestHex(imageJson) : imageVO.getImageName();
			String imagePath = PhantomJsUtil.generateImgEChart(pdfPropsConfig.getPhantomjsPath(), pdfPropsConfig.getConvetJsPath(), "E:\\home\\pdf\\resources\\data\\", imageJson, imageName);
			tmpVO.setImagePath(imagePath);
			tmpVO.setSearchText(imageVO.getSearchText());
			tmpVO.setImageName(imageName);
			finalImgInfos.add(tmpVO);
		}
		Map<String, String> resutMap = new HashMap<>();
		try {
			boolean result = wordClient.convert2ImgsWord(docxOrginFile, uniqueDataMap, docxNewFile, finalImgInfos, Boolean.FALSE);
			if (result) {
				result = OfficeOperateUtil.docxFile2Files(docxNewFile, htmlPath, pdfPath, imageDir, null, Arrays.asList(new String[]{"<p><br /></p>"}));
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
