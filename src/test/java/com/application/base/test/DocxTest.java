package com.application.base.test;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.application.base.docx4j.Placeholder2WordClient;
import com.application.base.docx4j.vo.DocxDataVO;
import com.application.base.docx4j.vo.DocxImageVO;
import com.application.base.util.OfficeOperateUtil;
import com.application.base.util.toolpdf.PhantomJsUtil;
import com.itextpdf.text.PageSize;
import org.docx4j.Docx4J;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：孤狼
 * @date ：2020-12-30
 * @description: 转换测试
 * @modified By：
 * @version: 1.0.0
 */
public class DocxTest {
	
	private static String phantomjsPath = "E://home//pdf//resources//phantomjs//window//phantomjs.exe";
	private static String convetJsPath = "E://home//pdf//resources//phantomjs//echartsconvert//echarts-convert.js";
	
	private static Placeholder2WordClient client = new Placeholder2WordClient();
	
	/**
	 * 测试.
	 *
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		test1();
		test2();
		test3();
		test4();
		test5();
		test6();
		test7();
		test8();
		test9();
		test10();
		test11();
		test12();
		test13();
		test131();
		test132();
		test14();
		test15();
		System.exit(1);
	}
	
	/**
	 * 测试.
	 */
	public static void test1() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test1.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp1.docx";
		Map<String, String> dataMap = new LinkedHashMap<>();
		dataMap.put("name", "张三");
		dataMap.put("genger", "男");
		dataMap.put("age", "40");
		dataMap.put("phone", "18888888888");
		dataMap.put("workyears", "10");
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test1");
		tmpMap.put("uniqueDataMap", dataMap);
		System.out.println(JSON.toJSONString(tmpMap));
		boolean result = client.convert2Word(filepath, dataMap, tofilepath);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 测试.
	 */
	public static void test2() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test2.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp2.docx";
		Map<String, String> dataMap = new LinkedHashMap<>();
		dataMap.put("title", "个人信息");
		dataMap.put("name", "张三");
		dataMap.put("genger", "男");
		dataMap.put("age", "40");
		dataMap.put("phone", "18888888888");
		dataMap.put("workyears", "10");
		dataMap.put("begin", "2006.09");
		dataMap.put("end", "2010.07");
		dataMap.put("basic", "基本信息");
		dataMap.put("education", "教育信息");
		boolean result = client.convert2Word(filepath, dataMap, tofilepath);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 测试.
	 */
	public static void test3() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test3.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp3.docx";
		Map<String, String> uniqueDataMap = new LinkedHashMap<>();
		uniqueDataMap.put("title", "个人信息");
		uniqueDataMap.put("name", "张三");
		uniqueDataMap.put("genger", "男");
		uniqueDataMap.put("age", "40");
		uniqueDataMap.put("phone", "18888888888");
		uniqueDataMap.put("workyears", "10");
		uniqueDataMap.put("begin", "2006.09");
		uniqueDataMap.put("end", "2010.07");
		uniqueDataMap.put("basic", "基本信息");
		uniqueDataMap.put("education", "教育信息");
		uniqueDataMap.put("experience", "工作经历");
		ArrayList<Map<String, Object>> linkedList = new ArrayList<>();
		Map<String, Object> dataMap1 = new LinkedHashMap<>();
		dataMap1.put("work.begin", "2010.04");
		dataMap1.put("work.end", "2015.06");
		dataMap1.put("work.company", "中电十五所");
		dataMap1.put("work.postion", "软件工程师");
		linkedList.add(dataMap1);
		Map<String, Object> dataMap2 = new LinkedHashMap<>();
		dataMap2.put("work.begin", "2015.06");
		dataMap2.put("work.end", "2019.06");
		dataMap2.put("work.company", "航天科工二院");
		dataMap2.put("work.postion", "高级软件工程师");
		linkedList.add(dataMap2);
		Map<String, Object> dataMap3 = new LinkedHashMap<>();
		dataMap3.put("work.begin", "2019.06");
		dataMap3.put("work.end", "至今");
		dataMap3.put("work.company", "中国航天科工集团");
		dataMap3.put("work.postion", "高级软件工程师");
		linkedList.add(dataMap3);
		
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test3");
		tmpMap.put("uniqueDataMap", uniqueDataMap);
		tmpMap.put("tableDatas", linkedList);
		System.out.println(JSON.toJSONString(tmpMap));
		
		boolean result = client.convert2TableWord(filepath, uniqueDataMap, tofilepath, linkedList, 0, null);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	
	/**
	 * 测试.
	 */
	public static void test4() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test4.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp4.docx";
		Map<String, String> uniqueDataMap = new LinkedHashMap<>();
		uniqueDataMap.put("title", "个人信息");
		uniqueDataMap.put("name", "张三");
		uniqueDataMap.put("genger", "男");
		uniqueDataMap.put("age", "40");
		uniqueDataMap.put("phone", "18888888888");
		uniqueDataMap.put("workyears", "10");
		uniqueDataMap.put("begin", "2006.09");
		uniqueDataMap.put("end", "2010.07");
		uniqueDataMap.put("basic", "基本信息");
		uniqueDataMap.put("education", "教育信息");
		uniqueDataMap.put("experience", "工作经历");
		uniqueDataMap.put("products", "工作成果");
		uniqueDataMap.put("team", "团队情况");
		
		ArrayList<Map<String, Object>> linkedList1 = new ArrayList<>();
		Map<String, Object> dataMap1 = new LinkedHashMap<>();
		dataMap1.put("work.begin", "2010.04");
		dataMap1.put("work.end", "2015.06");
		dataMap1.put("work.company", "中电十五所");
		dataMap1.put("work.postion", "软件工程师");
		linkedList1.add(dataMap1);
		Map<String, Object> dataMap2 = new LinkedHashMap<>();
		dataMap2.put("work.begin", "2015.06");
		dataMap2.put("work.end", "2019.06");
		dataMap2.put("work.company", "航天科工二院");
		dataMap2.put("work.postion", "高级软件工程师");
		linkedList1.add(dataMap2);
		Map<String, Object> dataMap3 = new LinkedHashMap<>();
		dataMap3.put("work.begin", "2019.06");
		dataMap3.put("work.end", "至今");
		dataMap3.put("work.company", "中国航天科工集团");
		dataMap3.put("work.postion", "高级软件工程师");
		linkedList1.add(dataMap3);
		
		ArrayList<Map<String, Object>> linkedList2 = new ArrayList<>();
		Map<String, Object> dataMap4 = new LinkedHashMap<>();
		dataMap4.put("product.name", "采购OA");
		dataMap4.put("product.no", "20121006AAAAA");
		dataMap4.put("product.price", "100W");
		linkedList2.add(dataMap4);
		Map<String, Object> dataMap5 = new LinkedHashMap<>();
		dataMap5.put("product.name", "供应链系统");
		dataMap5.put("product.no", "20160908BBBBB");
		dataMap5.put("product.price", "150W");
		linkedList2.add(dataMap5);
		
		ArrayList<Map<String, Object>> linkedList3 = new ArrayList<>();
		Map<String, Object> dataMap6 = new LinkedHashMap<>();
		dataMap6.put("team.count", "10");
		dataMap6.put("team.depart", "技术部");
		dataMap6.put("team.leader", "王五");
		linkedList3.add(dataMap6);
		Map<String, Object> dataMap7 = new LinkedHashMap<>();
		dataMap7.put("team.count", "15");
		dataMap7.put("team.depart", "信息部");
		dataMap7.put("team.leader", "李四");
		linkedList3.add(dataMap7);
		Map<String, Object> dataMap8 = new LinkedHashMap<>();
		dataMap8.put("team.count", "20");
		dataMap8.put("team.depart", "信息技术部");
		dataMap8.put("team.leader", "赵璐");
		linkedList3.add(dataMap8);
		
		ArrayList<DocxDataVO> linkedList = new ArrayList<>();
		linkedList.add(new DocxDataVO(0, linkedList2));
		linkedList.add(new DocxDataVO(1, linkedList1));
		linkedList.add(new DocxDataVO(2, linkedList3));
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test4");
		tmpMap.put("uniqueDataMap", uniqueDataMap);
		tmpMap.put("tablesDatas", linkedList);
		System.out.println(JSON.toJSONString(tmpMap));
		
		boolean result = client.convert2TablesWord(filepath, uniqueDataMap, tofilepath, linkedList);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	
	/**
	 * 测试.
	 */
	public static void test5() throws Exception {
		long start = System.currentTimeMillis();
		
		String filepath = "E:\\home\\pdf\\resources\\data\\test5.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp5.docx";
		
		Map<String, String> uniqueDataMap = new LinkedHashMap<>();
		uniqueDataMap.put("date", DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd"));
		uniqueDataMap.put("client", "张三");
		uniqueDataMap.put("account-manager", "李四");
		uniqueDataMap.put("company", "青云科技");
		uniqueDataMap.put("residence", "高新国际");
		uniqueDataMap.put("mobilephone", "18888888888");
		uniqueDataMap.put("email", "test@126.com");
		uniqueDataMap.put("address", "陕西西安高新四路");
		uniqueDataMap.put("reationship", "客户关系");
		uniqueDataMap.put("name", "长安国际");
		uniqueDataMap.put("insourced", "AAAAA");
		uniqueDataMap.put("insource", "BBBBB");
		uniqueDataMap.put("policy-holder", "CCCCC");
		
		uniqueDataMap.put("account", "10000.00");
		uniqueDataMap.put("insourceNo", "2021010508");
		uniqueDataMap.put("acceptNo", "2021002021");
		uniqueDataMap.put("e-number", "88888-9999");
		uniqueDataMap.put("invoceType", "平安车险");
		uniqueDataMap.put("constName", "长安电子信托系统");
		
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test5");
		tmpMap.put("uniqueDataMap", uniqueDataMap);
		System.out.println(JSON.toJSONString(tmpMap));
		
		boolean result = client.convert2Word(filepath, uniqueDataMap, tofilepath);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 表格类型的模板处理.
	 */
	public static void test6() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test6.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp6.docx";
		Map<String, String> uniqueDataMap = new LinkedHashMap<>();
		uniqueDataMap.put("point", "49");
		uniqueDataMap.put("a-num", "√");
		uniqueDataMap.put("b-num", "");
		uniqueDataMap.put("c-num", "");
		uniqueDataMap.put("d-num", "√");
		uniqueDataMap.put("e-num", "");
		uniqueDataMap.put("f-num", "");
		uniqueDataMap.put("name", "长安国际");
		uniqueDataMap.put("m-sex", "√");
		uniqueDataMap.put("f-sex", "");
		uniqueDataMap.put("idcard", "√");
		uniqueDataMap.put("passport", "");
		uniqueDataMap.put("bootlet", "");
		uniqueDataMap.put("idNo", "11111111111111111111");
		uniqueDataMap.put("email", "test@126.com");
		uniqueDataMap.put("national", "中华人民共和国");
		uniqueDataMap.put("address", "陕西西安高新四路");
		uniqueDataMap.put("mobilephone", "18888888888");
		uniqueDataMap.put("phone", "029-8756328");
		
		uniqueDataMap.put("owner", "√");
		uniqueDataMap.put("spouse", "");
		uniqueDataMap.put("parent", "");
		uniqueDataMap.put("children", "√");
		uniqueDataMap.put("brother", "");
		uniqueDataMap.put("account-name", "长安电子信托");
		uniqueDataMap.put("open-bank", "招商银行陕西分行");
		uniqueDataMap.put("account-no", "2021001002003");
		boolean result = client.convert2Word(filepath, uniqueDataMap, tofilepath);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 表格类型的模板处理.
	 */
	public static void test7() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test7.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp7.docx";
		Map<String, String> uniqueDataMap = new LinkedHashMap<>();
		uniqueDataMap.put("point", "49");
		uniqueDataMap.put("a-num", "√");
		uniqueDataMap.put("b-num", "");
		uniqueDataMap.put("c-num", "");
		uniqueDataMap.put("d-num", "√");
		uniqueDataMap.put("e-num", "");
		uniqueDataMap.put("f-num", "");
		uniqueDataMap.put("name", "长安国际");
		uniqueDataMap.put("m-sex", "√");
		uniqueDataMap.put("f-sex", "");
		uniqueDataMap.put("idcard", "√");
		uniqueDataMap.put("passport", "");
		uniqueDataMap.put("bootlet", "");
		uniqueDataMap.put("idNo", "111111111111");
		uniqueDataMap.put("email", "test@126.com");
		uniqueDataMap.put("national", "中华人民共和国");
		uniqueDataMap.put("address", "陕西西安高新四路");
		uniqueDataMap.put("mobilephone", "18888888888");
		uniqueDataMap.put("phone", "029-8756328");
		
		uniqueDataMap.put("owner", "√");
		uniqueDataMap.put("spouse", "");
		uniqueDataMap.put("parent", "");
		uniqueDataMap.put("children", "√");
		uniqueDataMap.put("brother", "");
		uniqueDataMap.put("account-name", "长安电子信托");
		uniqueDataMap.put("open-bank", "招商银行陕西分行");
		uniqueDataMap.put("account-no", "2021001002003");
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		
		tmpMap.put("templeteId", "test7");
		tmpMap.put("uniqueDataMap", uniqueDataMap);
		System.out.println(JSON.toJSONString(tmpMap));
		
		boolean result = client.convert2Word(filepath, uniqueDataMap, tofilepath);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 测试多页模板的响应速度.
	 */
	public static void test8() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test8.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp8.docx";
		Map<String, String> uniqueDataMap = new LinkedHashMap<>();
		
		//67 page
		uniqueDataMap.put("year", "2021");
		uniqueDataMap.put("month", "01");
		uniqueDataMap.put("day", "08");
		uniqueDataMap.put("clientName", "西伯利亚狼");
		//61 page
		uniqueDataMap.put("year1", "2020");
		uniqueDataMap.put("month1", "01");
		uniqueDataMap.put("day1", "08");
		uniqueDataMap.put("clientName1", "西伯利亚狼1");
		
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test8");
		tmpMap.put("uniqueDataMap", uniqueDataMap);
		System.out.println(JSON.toJSONString(tmpMap));
		
		boolean result = client.convert2Word(filepath, uniqueDataMap, tofilepath);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 单个图片插入
	 */
	public static void test9() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test9.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp9.docx";
		String imageUrl = "E:\\home\\pdf\\resources\\data\\test9.png";
		String searchText = "插入图片";
		Map<String, String> dataMap = new LinkedHashMap<>();
		dataMap.put("name", "张三");
		dataMap.put("genger", "男");
		dataMap.put("age", "40");
		dataMap.put("phone", "18888888888");
		dataMap.put("workyears", "10");
		
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test9");
		tmpMap.put("uniqueDataMap", dataMap);
		DocxImageVO imageVO = new DocxImageVO();
		imageVO.setSearchText(searchText);
		imageVO.setImagePath(imageUrl);
		tmpMap.put("imageVO", imageVO);
		System.out.println(JSON.toJSONString(tmpMap));
		boolean result = client.convert2ImgWord(filepath, dataMap, tofilepath, searchText, imageUrl, Boolean.TRUE);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 多图片位置插入.
	 */
	public static void test10() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test10.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp10.docx";
		String imageUrl = "E:\\home\\pdf\\resources\\data\\test9.png";
		Map<String, String> dataMap = new LinkedHashMap<>();
		dataMap.put("name", "张三");
		dataMap.put("genger", "男");
		dataMap.put("age", "40");
		dataMap.put("phone", "18888888888");
		dataMap.put("workyears", "10");
		List<DocxImageVO> imgInfos = new ArrayList<>();
		imgInfos.add(new DocxImageVO("插入图片:", imageUrl));
		imgInfos.add(new DocxImageVO("确认插入图片:", imageUrl));
		imgInfos.add(new DocxImageVO("再次确认插入图片:", imageUrl));
		
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test10");
		tmpMap.put("uniqueDataMap", dataMap);
		tmpMap.put("imageInfos", imgInfos);
		System.out.println(JSON.toJSONString(tmpMap));
		
		boolean result = client.convert2ImgsWord(filepath, dataMap, tofilepath, imgInfos, Boolean.FALSE);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 多图片位置插入.
	 */
	public static void test11() throws Exception {
		long start = System.currentTimeMillis();
		String searchText = "插入图片:";
		String imageDir = "E:\\home\\pdf\\resources\\data\\image";
		String filepath = "E:\\home\\pdf\\resources\\data\\test11.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp11.docx";
		String htmlfilepath = "E:\\home\\pdf\\resources\\data\\temp11.html";
		String targetfilepath = "E:\\home\\pdf\\resources\\data\\temp11.pdf";
		String echartsOptions = "option={title:{text:'折线图堆叠'},tooltip:{trigger:'axis'},legend:{data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']},grid:{left:'3%',right:'4%',bottom:'3%',containLabel:true},toolbox:{feature:{saveAsImage:{}}},xAxis:{type:'category',boundaryGap:false,data:['周一','周二','周三','周四','周五','周六','周日']},yAxis:{type:'value'},series:[{name:'邮件营销',type:'line',stack:'总量',data:[120,132,101,134,90,230,210]},{name:'联盟广告',type:'line',stack:'总量',data:[220,182,191,234,290,330,310]},{name:'视频广告',type:'line',stack:'总量',data:[150,232,201,154,190,330,410]},{name:'直接访问',type:'line',stack:'总量',data:[320,332,301,334,390,330,320]},{name:'搜索引擎',type:'line',stack:'总量',data:[820,932,901,934,1290,1330,1320]}]};";
		String filePath = PhantomJsUtil.generateImgEChart(phantomjsPath, convetJsPath, "E:\\home\\pdf\\resources\\data\\", echartsOptions, "test11");
		Map<String, String> dataMap = new LinkedHashMap<>();
		dataMap.put("name", "张三");
		dataMap.put("genger", "男");
		dataMap.put("age", "40");
		dataMap.put("phone", "18888888888");
		dataMap.put("workyears", "10");
		List<DocxImageVO> imgInfos = new ArrayList<>();
		imgInfos.add(new DocxImageVO(searchText, filePath));
		
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test11");
		tmpMap.put("uniqueDataMap", dataMap);
		DocxImageVO imageVO = new DocxImageVO();
		imageVO.setEchartsOptions(echartsOptions);
		imageVO.setSearchText(searchText);
		tmpMap.put("imageVO", imageVO);
		System.out.println(JSON.toJSONString(tmpMap));
		
		boolean result = client.convert2ImgsWord(filepath, dataMap, tofilepath, imgInfos, Boolean.FALSE);
		if (result) {
			OfficeOperateUtil.docxFile2Files(tofilepath, htmlfilepath, targetfilepath, imageDir, null, null);
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 多图片位置插入.
	 */
	public static void test12() throws Exception {
		long start = System.currentTimeMillis();
		String imageDir = "E:\\home\\pdf\\resources\\data\\image";
		String filepath = "E:\\home\\pdf\\resources\\data\\test12.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp12.docx";
		String htmlfilepath = "E:\\home\\pdf\\resources\\data\\temp12.html";
		String targetfilepath = "E:\\home\\pdf\\resources\\data\\temp12.pdf";
		String echartsOptions1 = "option={title:{text:'折线图堆叠'},tooltip:{trigger:'axis'},legend:{data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']},grid:{left:'3%',right:'4%',bottom:'3%',containLabel:true},toolbox:{feature:{saveAsImage:{}}},xAxis:{type:'category',boundaryGap:false,data:['周一','周二','周三','周四','周五','周六','周日']},yAxis:{type:'value'},series:[{name:'邮件营销',type:'line',stack:'总量',data:[120,132,101,134,90,230,210]},{name:'联盟广告',type:'line',stack:'总量',data:[220,182,191,234,290,330,310]},{name:'视频广告',type:'line',stack:'总量',data:[150,232,201,154,190,330,410]},{name:'直接访问',type:'line',stack:'总量',data:[320,332,301,334,390,330,320]},{name:'搜索引擎',type:'line',stack:'总量',data:[820,932,901,934,1290,1330,1320]}]};";
		String filePath1 = PhantomJsUtil.generateImgEChart(phantomjsPath, convetJsPath, "E:\\home\\pdf\\resources\\data\\", echartsOptions1, "test12-1");
		String echartsOptions2 = "option={xAxis:{type:'category',data:['Mon','Tue','Wed','Thu','Fri','Sat','Sun']},yAxis:{type:'value'},series:[{data:[120,200,150,80,70,110,130],type:'bar',showBackground:true,backgroundStyle:{color:'rgba(220, 220, 220, 0.8)'}}]};";
		String filePath2 = PhantomJsUtil.generateImgEChart(phantomjsPath, convetJsPath, "E:\\home\\pdf\\resources\\data\\", echartsOptions2, "test12-2");
		String echartsOptions3 = "option={tooltip:{trigger:'item',formatter:'{a} <br/>{b}: {c} ({d}%)'},legend:{orient:'vertical',left:10,data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']},series:[{name:'访问来源',type:'pie',radius:['50%','70%'],avoidLabelOverlap:false,label:{show:false,position:'center'},emphasis:{label:{show:true,fontSize:'30',fontWeight:'bold'}},labelLine:{show:false},data:[{value:335,name:'直接访问'},{value:310,name:'邮件营销'},{value:234,name:'联盟广告'},{value:135,name:'视频广告'},{value:1548,name:'搜索引擎'}]}]};";
		String filePath3 = PhantomJsUtil.generateImgEChart(phantomjsPath, convetJsPath, "E:\\home\\pdf\\resources\\data\\", echartsOptions3, "test12-3");
		
		Map<String, String> dataMap = new LinkedHashMap<>();
		dataMap.put("name", "张三");
		dataMap.put("genger", "男");
		dataMap.put("age", "40");
		dataMap.put("phone", "18888888888");
		dataMap.put("workyears", "10");
		List<DocxImageVO> imgInfos = new ArrayList<>();
		imgInfos.add(new DocxImageVO("插入图片:", filePath1));
		imgInfos.add(new DocxImageVO("确认插入图片:", filePath2));
		imgInfos.add(new DocxImageVO("再次确认插入图片:", filePath3));
		
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test12");
		tmpMap.put("uniqueDataMap", dataMap);
		List<DocxImageVO> imageInfos = new ArrayList<>();
		DocxImageVO imageVO1 = new DocxImageVO();
		imageVO1.setEchartsOptions(echartsOptions1);
		imageVO1.setSearchText("插入图片:");
		DocxImageVO imageVO2 = new DocxImageVO();
		imageVO2.setEchartsOptions(echartsOptions2);
		imageVO2.setSearchText("确认插入图片:");
		DocxImageVO imageVO3 = new DocxImageVO();
		imageVO3.setEchartsOptions(echartsOptions3);
		imageVO3.setSearchText("再次确认插入图片:");
		imageInfos.add(imageVO1);
		imageInfos.add(imageVO2);
		imageInfos.add(imageVO3);
		tmpMap.put("imageInfos", imageInfos);
		System.out.println(JSON.toJSONString(tmpMap));
		
		boolean result = client.convert2ImgsWord(filepath, dataMap, tofilepath, imgInfos, Boolean.FALSE);
		if (result) {
			OfficeOperateUtil.docxFile2Files(tofilepath, htmlfilepath, targetfilepath, imageDir, null, null);
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 测试.
	 */
	public static void test13() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test13.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp13.docx";
		Map<String, String> uniqueDataMap = new LinkedHashMap<>();
		uniqueDataMap.put("title", "个人信息");
		uniqueDataMap.put("name", "张三");
		uniqueDataMap.put("genger", "男");
		uniqueDataMap.put("age", "40");
		uniqueDataMap.put("phone", "18888888888");
		uniqueDataMap.put("workyears", "10");
		uniqueDataMap.put("begin", "2006.09");
		uniqueDataMap.put("end", "2010.07");
		uniqueDataMap.put("basic", "基本信息");
		uniqueDataMap.put("education", "教育信息");
		uniqueDataMap.put("experience", "工作经历");
		uniqueDataMap.put("totalAmount", "75000");
		ArrayList<Map<String, Object>> linkedList = new ArrayList<>();
		Map<String, Object> dataMap1 = new LinkedHashMap<>();
		dataMap1.put("work.begin", "2010.04");
		dataMap1.put("work.end", "2015.06");
		dataMap1.put("work.company", "中电十五所");
		dataMap1.put("work.postion", "软件工程师");
		dataMap1.put("work.salary", "15000");
		linkedList.add(dataMap1);
		Map<String, Object> dataMap2 = new LinkedHashMap<>();
		dataMap2.put("work.begin", "2015.06");
		dataMap2.put("work.end", "2019.06");
		dataMap2.put("work.company", "航天科工二院");
		dataMap2.put("work.postion", "高级软件工程师");
		dataMap2.put("work.salary", "25000");
		linkedList.add(dataMap2);
		Map<String, Object> dataMap3 = new LinkedHashMap<>();
		dataMap3.put("work.begin", "2019.06");
		dataMap3.put("work.end", "至今");
		dataMap3.put("work.company", "中国航天科工集团");
		dataMap3.put("work.postion", "高级软件工程师");
		dataMap3.put("work.salary", "35000");
		linkedList.add(dataMap3);
		
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test13");
		tmpMap.put("uniqueDataMap", uniqueDataMap);
		tmpMap.put("tableDatas", linkedList);
		System.out.println(JSON.toJSONString(tmpMap));
		boolean result = client.convert2TableWord(filepath, uniqueDataMap, tofilepath, linkedList, 0, null);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 测试.
	 */
	public static void test131() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test13-1.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp13-1.docx";
		Map<String, String> uniqueDataMap = new LinkedHashMap<>();
		uniqueDataMap.put("title", "个人信息");
		uniqueDataMap.put("name", "张三");
		uniqueDataMap.put("genger", "男");
		uniqueDataMap.put("age", "40");
		uniqueDataMap.put("phone", "18888888888");
		uniqueDataMap.put("workyears", "10");
		uniqueDataMap.put("begin", "2006.09");
		uniqueDataMap.put("end", "2010.07");
		uniqueDataMap.put("basic", "基本信息");
		uniqueDataMap.put("education", "教育信息");
		uniqueDataMap.put("experience", "工作经历");
		uniqueDataMap.put("totalAmount", "75000");
		ArrayList<Map<String, Object>> linkedList = new ArrayList<>();
		Map<String, Object> dataMap1 = new LinkedHashMap<>();
		dataMap1.put("work.begin", "2010.04");
		dataMap1.put("work.end", "2015.06");
		dataMap1.put("work.company", "中电十五所");
		dataMap1.put("work.postion", "软件工程师");
		dataMap1.put("work.salary", "15000");
		linkedList.add(dataMap1);
		Map<String, Object> dataMap2 = new LinkedHashMap<>();
		dataMap2.put("work.begin", "2015.06");
		dataMap2.put("work.end", "2019.06");
		dataMap2.put("work.company", "航天科工二院");
		dataMap2.put("work.postion", "高级软件工程师");
		dataMap2.put("work.salary", "25000");
		linkedList.add(dataMap2);
		Map<String, Object> dataMap3 = new LinkedHashMap<>();
		dataMap3.put("work.begin", "2019.06");
		dataMap3.put("work.end", "至今");
		dataMap3.put("work.company", "中国航天科工集团");
		dataMap3.put("work.postion", "高级软件工程师");
		dataMap3.put("work.salary", "35000");
		linkedList.add(dataMap3);
		
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test13");
		tmpMap.put("uniqueDataMap", uniqueDataMap);
		tmpMap.put("tableDatas", linkedList);
		System.out.println(JSON.toJSONString(tmpMap));
		boolean result = client.convert2TableWord(filepath, uniqueDataMap, tofilepath, linkedList, 0, 2);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 测试.
	 */
	public static void test132() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test13-2.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp13-2.docx";
		Map<String, String> uniqueDataMap = new LinkedHashMap<>();
		uniqueDataMap.put("title", "个人信息");
		uniqueDataMap.put("name", "张三");
		uniqueDataMap.put("genger", "男");
		uniqueDataMap.put("age", "40");
		uniqueDataMap.put("phone", "18888888888");
		uniqueDataMap.put("workyears", "10");
		uniqueDataMap.put("begin", "2006.09");
		uniqueDataMap.put("end", "2010.07");
		uniqueDataMap.put("basic", "基本信息");
		uniqueDataMap.put("education", "教育信息");
		uniqueDataMap.put("experience", "工作经历");
		uniqueDataMap.put("totalAmount", "75000");
		ArrayList<Map<String, Object>> linkedList = new ArrayList<>();
		Map<String, Object> dataMap1 = new LinkedHashMap<>();
		dataMap1.put("work.begin", "2010.04");
		dataMap1.put("work.end", "2015.06");
		dataMap1.put("work.company", "中电十五所");
		dataMap1.put("work.postion", "软件工程师");
		dataMap1.put("work.salary", "15000");
		linkedList.add(dataMap1);
		Map<String, Object> dataMap2 = new LinkedHashMap<>();
		dataMap2.put("work.begin", "2015.06");
		dataMap2.put("work.end", "2019.06");
		dataMap2.put("work.company", "航天科工二院");
		dataMap2.put("work.postion", "高级软件工程师");
		dataMap2.put("work.salary", "25000");
		linkedList.add(dataMap2);
		Map<String, Object> dataMap3 = new LinkedHashMap<>();
		dataMap3.put("work.begin", "2019.06");
		dataMap3.put("work.end", "至今");
		dataMap3.put("work.company", "中国航天科工集团");
		dataMap3.put("work.postion", "高级软件工程师");
		dataMap3.put("work.salary", "35000");
		linkedList.add(dataMap3);
		
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test13");
		tmpMap.put("uniqueDataMap", uniqueDataMap);
		tmpMap.put("tableDatas", linkedList);
		System.out.println(JSON.toJSONString(tmpMap));
		boolean result = client.convert2TableWord(filepath, uniqueDataMap, tofilepath, linkedList, 0, 2);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 测试.
	 */
	public static void test14() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\test14.docx";
		String tofilepath = "E:\\home\\pdf\\resources\\data\\temp14.docx";
		Map<String, String> uniqueDataMap = new LinkedHashMap<>();
		uniqueDataMap.put("title", "长安信托");
		uniqueDataMap.put("cust-name", "张三");
		uniqueDataMap.put("cust-cert-name", "身份证");
		uniqueDataMap.put("org-name", "西部证券");
		uniqueDataMap.put("cust-cert-no", "1101011990d3d75752");
		uniqueDataMap.put("cust-cert-addr", "北京市朝阳区");
		uniqueDataMap.put("cust-post-code", "100816");
		uniqueDataMap.put("cust-phone", "18888888888");
		uniqueDataMap.put("cust-email", "zhangsan@163.com");
		uniqueDataMap.put("agent-name", "李四");
		uniqueDataMap.put("agent-gender", "女");
		uniqueDataMap.put("agent-cert-name", "身份证");
		uniqueDataMap.put("agent-cert-no", "1101011990d3d75763");
		uniqueDataMap.put("agent-phone", "18888888880");
		uniqueDataMap.put("agent-addr", "北京市海淀区");
		uniqueDataMap.put("agent-relationship", "客户");
		
		uniqueDataMap.put("benefit-level", "5");
		uniqueDataMap.put("benefit-percent", "50%");
		uniqueDataMap.put("benefit-date", "2020-2050");
		uniqueDataMap.put("bendfit-unit", "年");
		uniqueDataMap.put("benefit-allname", "收益权收益");
		uniqueDataMap.put("benefit-level-date", "2020-01-20");
		uniqueDataMap.put("constract-amount", "20000000");
		uniqueDataMap.put("china-amount", "贰千万");
		uniqueDataMap.put("trans-amount", "10000");
		uniqueDataMap.put("trans-money", "3000");
		uniqueDataMap.put("constract-no", "2020-11990d3d75");
		
		uniqueDataMap.put("product-name", "家族信托");
		uniqueDataMap.put("amount", "1000");
		uniqueDataMap.put("constract-date", "2019-10-28");
		
		uniqueDataMap.put("account-name", "孤狼1号");
		uniqueDataMap.put("bank-no", "6214888167891");
		uniqueDataMap.put("open-bank", "陕西招商银行");
		uniqueDataMap.put("mark-date", "2020-10-10");
		
		Map<String, Object> tmpMap = new LinkedHashMap<>();
		tmpMap.put("templeteId", "test14");
		tmpMap.put("uniqueDataMap", uniqueDataMap);
		System.out.println(JSON.toJSONString(tmpMap));
		
		boolean result = client.convert2Word(filepath, uniqueDataMap, tofilepath);
		if (result) {
			String htmlPath = "E:\\home\\pdf\\resources\\data\\html14.html";
			String pdfPath = "E:\\home\\pdf\\resources\\data\\pdf14.pdf";
			String imageDir = "E:\\home\\pdf\\resources\\data\\image\\";
			OfficeOperateUtil.docxFile2Files(tofilepath, htmlPath, pdfPath, imageDir, PageSize.A4, null);
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	/**
	 * 测试.
	 */
	public static void test15() throws Exception {
		long start = System.currentTimeMillis();
		String filepath = "E:\\home\\pdf\\resources\\data\\temp14.docx";
		String pdfpath = "E:\\home\\pdf\\resources\\data\\pdf14.pdf";
		String imageDirPath = "E:\\home\\pdf\\resources\\data\\html14.html";
		
		//docx 2 pdf
		File templateFile = new File(filepath);
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateFile);
		File outFile = new File(pdfpath);
		setFontMapper(wordMLPackage);
		Docx4J.toPDF(wordMLPackage, new FileOutputStream(outFile));
		wordMLPackage.reset();
		
		/*
		//docx 2 html
		File templateFile = new File(filepath);
		int len = filepath.lastIndexOf("\\/") + 1;
		String name = filepath.substring(len, filepath.length()).split("\\.")[0];
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateFile);
		HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
		String folder = templateFile.getParent() + "/out/";
		//生成的html文件与图片文件夹之类的放置同一个目录下
		htmlSettings.setImageDirPath(imageDirPath);
		htmlSettings.setImageTargetUri(name + "_files");
		htmlSettings.setWmlPackage(wordMLPackage);
		File outFile = new File(imageDirPath);
		Docx4J.toHTML(htmlSettings, new FileOutputStream(outFile), Docx4J.FLAG_NONE);
		wordMLPackage.reset();
		*/
		
		/*
		XWPFDocument document;
		try (InputStream doc = Files.newInputStream(Paths.get(filepath))) {
			document = new XWPFDocument(doc);
		}
		PdfOptions options = PdfOptions.create();
		try (OutputStream out = Files.newOutputStream(Paths.get(tofilepath))) {
			PdfConverter.getInstance().convert(document, out, options);
		}
		*/
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) + "毫秒");
	}
	
	private static void setFontMapper(WordprocessingMLPackage mlPackage) throws Exception {
		Mapper fontMapper = new IdentityPlusMapper();
		fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
		fontMapper.put("宋体", PhysicalFonts.get("SimSun"));
		fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
		fontMapper.put("黑体", PhysicalFonts.get("SimHei"));
		fontMapper.put("楷体", PhysicalFonts.get("KaiTi"));
		fontMapper.put("新宋体", PhysicalFonts.get("NSimSun"));
		fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
		fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
		fontMapper.put("宋体扩展", PhysicalFonts.get("simsun-extB"));
		fontMapper.put("仿宋", PhysicalFonts.get("FangSong"));
		fontMapper.put("仿宋_GB2312", PhysicalFonts.get("FangSong_GB2312"));
		fontMapper.put("幼圆", PhysicalFonts.get("YouYuan"));
		fontMapper.put("华文宋体", PhysicalFonts.get("STSong"));
		fontMapper.put("华文中宋", PhysicalFonts.get("STZhongsong"));
		
		mlPackage.setFontMapper(fontMapper);
	}
	
}
