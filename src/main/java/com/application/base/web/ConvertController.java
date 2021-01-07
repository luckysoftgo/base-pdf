package com.application.base.web;

import com.application.base.config.PdfPropsConfig;
import com.application.base.datas.ReportDataDto;
import com.application.base.docx4j.vo.DocxDataVO;
import com.application.base.docx4j.vo.DocxImgVO;
import com.application.base.service.ConvertService;
import com.application.base.util.GenericVO;
import com.application.base.util.toolpdf.PhantomJsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: ConvertController
 * @DESC: ConvertController类设计
 **/
@Slf4j
@RestController
@RequestMapping("/convert")
public class ConvertController {
	
	@Autowired
	private ConvertService convertService;
	@Autowired
	private PdfPropsConfig pdfPropsConfig;
	
	/**
	 * 服务生成报告-test1,test2,test5,test6,test7,test8
	 */
	@PostMapping(value = "/wordSymbol2files")
	@ResponseBody
	public GenericVO wordSymbol2files(@RequestBody ReportDataDto dataDto) {
		String templeteId = dataDto.getTempleteId();
		Map<String, String> mappingMap = dataDto.getUniqueDataMap();
		Map<String, String> resultMap = convertService.wordSymbol2files(templeteId, mappingMap);
		GenericVO genericVO = new GenericVO();
		if (resultMap.size() > 0) {
			genericVO.setCode(HttpStatus.OK.value());
			genericVO.setMsg("处理成功");
			genericVO.setData(resultMap);
		} else {
			genericVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			genericVO.setMsg("处理失败");
		}
		return genericVO;
	}
	
	
	/**
	 * 服务生成报告-test3
	 */
	@PostMapping(value = "/wordTable2files")
	@ResponseBody
	public GenericVO wordTable2files(@RequestBody ReportDataDto dataDto) {
		String templeteId = dataDto.getTempleteId();
		Map<String, String> mappingMap = dataDto.getUniqueDataMap();
		ArrayList<Map<String, Object>> tableDatas = dataDto.getTableDatas();
		Map<String, String> resultMap = convertService.wordTable2files(templeteId, mappingMap, tableDatas);
		GenericVO genericVO = new GenericVO();
		if (resultMap.size() > 0) {
			genericVO.setCode(HttpStatus.OK.value());
			genericVO.setMsg("处理成功");
			genericVO.setData(resultMap);
		} else {
			genericVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			genericVO.setMsg("处理失败");
		}
		return genericVO;
	}
	
	/**
	 * 服务生成报告-test4
	 */
	@PostMapping(value = "/wordTables2files")
	@ResponseBody
	public GenericVO wordTables2files(@RequestBody ReportDataDto dataDto) {
		String templeteId = dataDto.getTempleteId();
		Map<String, String> mappingMap = dataDto.getUniqueDataMap();
		ArrayList<DocxDataVO> tablesDatas = dataDto.getTablesDatas();
		Map<String, String> resultMap = convertService.wordTables2files(templeteId, mappingMap, tablesDatas);
		GenericVO genericVO = new GenericVO();
		if (resultMap.size() > 0) {
			genericVO.setCode(HttpStatus.OK.value());
			genericVO.setMsg("处理成功");
			genericVO.setData(resultMap);
		} else {
			genericVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			genericVO.setMsg("处理失败");
		}
		return genericVO;
	}
	
	/**
	 * 服务生成报告-test9
	 */
	@PostMapping(value = "/wordImg2files")
	@ResponseBody
	public GenericVO wordImg2files(@RequestBody ReportDataDto dataDto) {
		String templeteId = dataDto.getTempleteId();
		Map<String, String> mappingMap = dataDto.getUniqueDataMap();
		String searchText = dataDto.getSearchText();
		String imageUrl = dataDto.getImageUrl();
		Map<String, String> resultMap = convertService.wordImg2files(templeteId, mappingMap, searchText, imageUrl);
		GenericVO genericVO = new GenericVO();
		if (resultMap.size() > 0) {
			genericVO.setCode(HttpStatus.OK.value());
			genericVO.setMsg("处理成功");
			genericVO.setData(resultMap);
		} else {
			genericVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			genericVO.setMsg("处理失败");
		}
		return genericVO;
	}
	
	/**
	 * 服务生成报告-test10
	 */
	@PostMapping(value = "/wordImgs2files")
	@ResponseBody
	public GenericVO wordImgs2files(@RequestBody ReportDataDto dataDto) {
		String templeteId = dataDto.getTempleteId();
		Map<String, String> mappingMap = dataDto.getUniqueDataMap();
		List<DocxImgVO> imgInfos = dataDto.getImgInfos();
		Map<String, String> resultMap = convertService.wordImgs2files(templeteId, mappingMap, imgInfos);
		GenericVO genericVO = new GenericVO();
		if (resultMap.size() > 0) {
			genericVO.setCode(HttpStatus.OK.value());
			genericVO.setMsg("处理成功");
			genericVO.setData(resultMap);
		} else {
			genericVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			genericVO.setMsg("处理失败");
		}
		return genericVO;
	}
	
	/**
	 * 服务生成报告-test11
	 */
	@PostMapping(value = "/wordAutomaticImg2files")
	@ResponseBody
	public GenericVO wordAutomaticImg2files(@RequestBody ReportDataDto dataDto) {
		String templeteId = dataDto.getTempleteId();
		Map<String, String> mappingMap = dataDto.getUniqueDataMap();
		String imageJson = dataDto.getImageJson();
		String imageUrl = PhantomJsUtil.generateImgEChart(pdfPropsConfig.getPhantomjsPath(), pdfPropsConfig.getConvetJsPath(), "E:\\home\\pdf\\resources\\data\\", imageJson, "AAA");
		log.info("文件路径的地址是:" + imageUrl);
		List<DocxImgVO> imgInfos = new ArrayList<>();
		imgInfos.add(new DocxImgVO("插入图片:", imageUrl));
		Map<String, String> resultMap = convertService.wordImgs2files(templeteId, mappingMap, imgInfos);
		GenericVO genericVO = new GenericVO();
		if (resultMap.size() > 0) {
			genericVO.setCode(HttpStatus.OK.value());
			genericVO.setMsg("处理成功");
			genericVO.setData(resultMap);
		} else {
			genericVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			genericVO.setMsg("处理失败");
		}
		return genericVO;
	}
	
}
