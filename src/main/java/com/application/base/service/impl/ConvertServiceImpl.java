package com.application.base.service.impl;

import com.application.base.docx4j.Placeholder2WordClient;
import com.application.base.docx4j.vo.DocxDataVO;
import com.application.base.docx4j.vo.DocxImgVO;
import com.application.base.service.ConvertService;
import com.application.base.util.OfficeOperateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ：admin
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
	public Map<String, String> wordImg2files(String templeteId, Map<String, String> uniqueDataMap, String searchText, String imageUrl) {
		String docxOrginFile = basePath + templeteId + ".docx";
		String docxNewFile = basePath + "temp_" + templeteId + ".docx";
		String pdfPath = basePath + "temp_" + templeteId + ".pdf";
		String htmlPath = basePath + "temp_" + templeteId + ".html";
		String imageDir = basePath + "image\\";
		Map<String, String> resutMap = new HashMap<>();
		try {
			boolean result = wordClient.convert2ImgWord(docxOrginFile, uniqueDataMap, docxNewFile, searchText, imageUrl, Boolean.TRUE);
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
	public Map<String, String> wordImgs2files(String templeteId, Map<String, String> uniqueDataMap, List<DocxImgVO> imgInfos) {
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
}
