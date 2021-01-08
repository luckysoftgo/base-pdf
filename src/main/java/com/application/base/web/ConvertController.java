package com.application.base.web;

import com.application.base.datas.ReportDataDto;
import com.application.base.docx4j.vo.DocxDataVO;
import com.application.base.docx4j.vo.DocxImageVO;
import com.application.base.service.ConvertService;
import com.application.base.util.GenericVO;
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
		Map<String, String> resultMap = convertService.wordImg2files(templeteId, mappingMap, dataDto.getImageVO());
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
		List<DocxImageVO> imageInfos = dataDto.getImageInfos();
		Map<String, String> resultMap = convertService.wordImgs2files(templeteId, mappingMap, imageInfos);
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
	 * 服务生成报告-test11 - wordAutomaticImg2files
	 */
	@PostMapping(value = "/wordAutomaticImg2files")
	@ResponseBody
	public GenericVO wordAutomaticImg2files(@RequestBody ReportDataDto dataDto) {
		String templeteId = dataDto.getTempleteId();
		Map<String, String> mappingMap = dataDto.getUniqueDataMap();
		DocxImageVO imgVO = dataDto.getImageVO();
		Map<String, String> resultMap = convertService.wordAutomaticImg2files(templeteId, mappingMap, imgVO);
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
	 * 服务生成报告-test12 - wordAutomaticImgs2files
	 */
	@PostMapping(value = "/wordAutomaticImgs2files")
	@ResponseBody
	public GenericVO wordAutomaticImgs2files(@RequestBody ReportDataDto dataDto) {
		String templeteId = dataDto.getTempleteId();
		Map<String, String> mappingMap = dataDto.getUniqueDataMap();
		List<DocxImageVO> imgInfos = dataDto.getImageInfos();
		Map<String, String> resultMap = convertService.wordAutomaticImgs2files(templeteId, mappingMap, imgInfos);
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
