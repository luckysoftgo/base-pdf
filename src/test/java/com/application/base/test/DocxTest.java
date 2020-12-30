package com.application.base.test;

import com.application.base.docx4j.DocxDataVO;
import com.application.base.docx4j.Placeholder2WordClient;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author ：admin
 * @date ：2020-12-30
 * @description: 转换测试
 * @modified By：
 * @version: 1.0.0
 */
public class DocxTest {
	
	private static Placeholder2WordClient client = new Placeholder2WordClient();
	
	/**
	 * 测试.
	 *
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		//test1();
		test2();
		//test3();
		//test4();
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
		boolean result = client.convert2Word(filepath, dataMap, tofilepath);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) / 1000 + "秒");
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
		System.out.println("转换花费时间为" + (end - start) / 1000 + "秒");
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
		LinkedList<Map<String, Object>> linkedList = new LinkedList<>();
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
		boolean result = client.convert2TableWord(filepath, uniqueDataMap, tofilepath, linkedList, 0);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) / 1000 + "秒");
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
		
		LinkedList<Map<String, Object>> linkedList1 = new LinkedList<>();
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
		
		LinkedList<Map<String, Object>> linkedList2 = new LinkedList<>();
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
		
		LinkedList<Map<String, Object>> linkedList3 = new LinkedList<>();
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
		
		LinkedList<DocxDataVO> linkedList = new LinkedList<>();
		linkedList.add(new DocxDataVO(0, linkedList2));
		linkedList.add(new DocxDataVO(1, linkedList1));
		linkedList.add(new DocxDataVO(2, linkedList3));
		boolean result = client.convert2TablesWord(filepath, uniqueDataMap, tofilepath, linkedList);
		if (result) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
		long end = System.currentTimeMillis();
		System.out.println("转换花费时间为" + (end - start) / 1000 + "秒");
	}
}
