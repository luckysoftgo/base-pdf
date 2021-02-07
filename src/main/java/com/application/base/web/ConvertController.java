package com.application.base.web;

import com.application.base.datas.ReportDataInfo;
import com.application.base.service.ConvertService;
import com.application.base.util.GenericVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
	public GenericVO wordSymbol2files(@RequestBody ReportDataInfo dataDto) {
		boolean result = convertService.buildReportCheck(dataDto.getReportHeader());
		GenericVO genericVO = new GenericVO();
		if (!result) {
			genericVO.setCode(10000);
			genericVO.setMsg("未认证到报告中心,请完成报告中心的注册认证流程");
		} else {
			Map<String, String> resultMap = convertService.wordSymbol2files(dataDto.getReportBody());
			if (resultMap.size() > 0) {
				genericVO.setCode(HttpStatus.OK.value());
				genericVO.setMsg("处理成功");
				genericVO.setData(resultMap);
			} else {
				genericVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				genericVO.setMsg("处理失败");
			}
		}
		return genericVO;
	}
	
	/**
	 * 动态数据生成报告
	 */
	@PostMapping(value = "/automaticInfo2files")
	@ResponseBody
	public GenericVO automaticInfo2files(@RequestBody ReportDataInfo dataDto) {
		boolean result = convertService.buildReportCheck(dataDto.getReportHeader());
		GenericVO genericVO = new GenericVO();
		if (!result) {
			genericVO.setCode(10000);
			genericVO.setMsg("未认证到报告中心,请完成报告中心的注册认证流程");
		} else {
			Map<String, String> resultMap = convertService.automaticInfo2files(dataDto.getReportBody());
			if (resultMap.size() > 0) {
				genericVO.setCode(HttpStatus.OK.value());
				genericVO.setMsg("处理成功");
				genericVO.setData(resultMap);
			} else {
				genericVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				genericVO.setMsg("处理失败");
			}
		}
		return genericVO;
	}
}
