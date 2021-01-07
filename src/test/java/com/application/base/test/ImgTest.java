package com.application.base.test;

import com.application.base.util.toolpdf.PhantomJsUtil;

/**
 * @author ：admin
 * @date ：2021-1-6
 * @description: 图片生成的Test
 * @modified By：
 * @version: 1.0.0
 */
public class ImgTest {
	private static String phantomjsPath = "E://home//pdf//resources//phantomjs//window//phantomjs.exe";
	private static String convetJsPath = "E://home//pdf//resources//phantomjs//echartsconvert//echarts-convert.js";
	
	/**
	 * 测试生成的可能性.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		testImg1();
		testImg2();
		testImg3();
		testImg4();
	}
	
	/**
	 * 线图
	 */
	private static void testImg1() {
		String echartsOptions = "option = {\n" +
				"    title: {\n" +
				"        text: '折线图堆叠'\n" +
				"    },\n" +
				"    tooltip: {\n" +
				"        trigger: 'axis'\n" +
				"    },\n" +
				"    legend: {\n" +
				"        data: ['邮件营销', '联盟广告', '视频广告', '直接访问', '搜索引擎']\n" +
				"    },\n" +
				"    grid: {\n" +
				"        left: '3%',\n" +
				"        right: '4%',\n" +
				"        bottom: '3%',\n" +
				"        containLabel: true\n" +
				"    },\n" +
				"    toolbox: {\n" +
				"        feature: {\n" +
				"            saveAsImage: {}\n" +
				"        }\n" +
				"    },\n" +
				"    xAxis: {\n" +
				"        type: 'category',\n" +
				"        boundaryGap: false,\n" +
				"        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']\n" +
				"    },\n" +
				"    yAxis: {\n" +
				"        type: 'value'\n" +
				"    },\n" +
				"    series: [\n" +
				"        {\n" +
				"            name: '邮件营销',\n" +
				"            type: 'line',\n" +
				"            stack: '总量',\n" +
				"            data: [120, 132, 101, 134, 90, 230, 210]\n" +
				"        },\n" +
				"        {\n" +
				"            name: '联盟广告',\n" +
				"            type: 'line',\n" +
				"            stack: '总量',\n" +
				"            data: [220, 182, 191, 234, 290, 330, 310]\n" +
				"        },\n" +
				"        {\n" +
				"            name: '视频广告',\n" +
				"            type: 'line',\n" +
				"            stack: '总量',\n" +
				"            data: [150, 232, 201, 154, 190, 330, 410]\n" +
				"        },\n" +
				"        {\n" +
				"            name: '直接访问',\n" +
				"            type: 'line',\n" +
				"            stack: '总量',\n" +
				"            data: [320, 332, 301, 334, 390, 330, 320]\n" +
				"        },\n" +
				"        {\n" +
				"            name: '搜索引擎',\n" +
				"            type: 'line',\n" +
				"            stack: '总量',\n" +
				"            data: [820, 932, 901, 934, 1290, 1330, 1320]\n" +
				"        }\n" +
				"    ]\n" +
				"};\n";
		String filePath = PhantomJsUtil.generateImgEChart(phantomjsPath, convetJsPath, "E:\\home\\pdf\\resources\\data\\", echartsOptions, "AAA");
		System.out.println("文件路径的地址是:" + filePath);
	}
	
	/**
	 * 饼图
	 */
	private static void testImg2() {
		String echartsOptions = "option = {\n" +
				"    tooltip: {\n" +
				"        trigger: 'item',\n" +
				"        formatter: '{a} <br/>{b}: {c} ({d}%)'\n" +
				"    },\n" +
				"    legend: {\n" +
				"        orient: 'vertical',\n" +
				"        left: 10,\n" +
				"        data: ['直达', '营销广告', '搜索引擎', '邮件营销', '联盟广告', '视频广告', '百度', '谷歌', '必应', '其他']\n" +
				"    },\n" +
				"    series: [\n" +
				"        {\n" +
				"            name: '访问来源',\n" +
				"            type: 'pie',\n" +
				"            selectedMode: 'single',\n" +
				"            radius: [0, '30%'],\n" +
				"\n" +
				"            label: {\n" +
				"                position: 'inner'\n" +
				"            },\n" +
				"            labelLine: {\n" +
				"                show: false\n" +
				"            },\n" +
				"            data: [\n" +
				"                {value: 335, name: '直达', selected: true},\n" +
				"                {value: 679, name: '营销广告'},\n" +
				"                {value: 1548, name: '搜索引擎'}\n" +
				"            ]\n" +
				"        },\n" +
				"        {\n" +
				"            name: '访问来源',\n" +
				"            type: 'pie',\n" +
				"            radius: ['40%', '55%'],\n" +
				"            label: {\n" +
				"                formatter: '{a|{a}}{abg|}\\n{hr|}\\n  {b|{b}：}{c}  {per|{d}%}  ',\n" +
				"                backgroundColor: '#eee',\n" +
				"                borderColor: '#aaa',\n" +
				"                borderWidth: 1,\n" +
				"                borderRadius: 4,\n" +
				"                // shadowBlur:3,\n" +
				"                // shadowOffsetX: 2,\n" +
				"                // shadowOffsetY: 2,\n" +
				"                // shadowColor: '#999',\n" +
				"                // padding: [0, 7],\n" +
				"                rich: {\n" +
				"                    a: {\n" +
				"                        color: '#999',\n" +
				"                        lineHeight: 22,\n" +
				"                        align: 'center'\n" +
				"                    },\n" +
				"                    // abg: {\n" +
				"                    //     backgroundColor: '#333',\n" +
				"                    //     width: '100%',\n" +
				"                    //     align: 'right',\n" +
				"                    //     height: 22,\n" +
				"                    //     borderRadius: [4, 4, 0, 0]\n" +
				"                    // },\n" +
				"                    hr: {\n" +
				"                        borderColor: '#aaa',\n" +
				"                        width: '100%',\n" +
				"                        borderWidth: 0.5,\n" +
				"                        height: 0\n" +
				"                    },\n" +
				"                    b: {\n" +
				"                        fontSize: 16,\n" +
				"                        lineHeight: 33\n" +
				"                    },\n" +
				"                    per: {\n" +
				"                        color: '#eee',\n" +
				"                        backgroundColor: '#334455',\n" +
				"                        padding: [2, 4],\n" +
				"                        borderRadius: 2\n" +
				"                    }\n" +
				"                }\n" +
				"            },\n" +
				"            data: [\n" +
				"                {value: 335, name: '直达'},\n" +
				"                {value: 310, name: '邮件营销'},\n" +
				"                {value: 234, name: '联盟广告'},\n" +
				"                {value: 135, name: '视频广告'},\n" +
				"                {value: 1048, name: '百度'},\n" +
				"                {value: 251, name: '谷歌'},\n" +
				"                {value: 147, name: '必应'},\n" +
				"                {value: 102, name: '其他'}\n" +
				"            ]\n" +
				"        }\n" +
				"    ]\n" +
				"};";
		String filePath = PhantomJsUtil.generateImgEChart(phantomjsPath, convetJsPath, "E:\\home\\pdf\\resources\\data\\", echartsOptions, "BBB");
		System.out.println("文件路径的地址是:" + filePath);
	}
	
	
	/**
	 * 热力图
	 */
	private static void testImg3() {
		String echartsOptions = "option = {\n" +
				"    title: {\n" +
				"        text: '漏斗图',\n" +
				"        subtext: '纯属虚构'\n" +
				"    },\n" +
				"    tooltip: {\n" +
				"        trigger: 'item',\n" +
				"        formatter: \"{a} <br/>{b} : {c}%\"\n" +
				"    },\n" +
				"    toolbox: {\n" +
				"        feature: {\n" +
				"            dataView: {readOnly: false},\n" +
				"            restore: {},\n" +
				"            saveAsImage: {}\n" +
				"        }\n" +
				"    },\n" +
				"    legend: {\n" +
				"        data: ['展现','点击','访问','咨询','订单']\n" +
				"    },\n" +
				"\n" +
				"    series: [\n" +
				"        {\n" +
				"            name:'漏斗图',\n" +
				"            type:'funnel',\n" +
				"            left: '10%',\n" +
				"            top: 60,\n" +
				"            //x2: 80,\n" +
				"            bottom: 60,\n" +
				"            width: '80%',\n" +
				"            // height: {totalHeight} - y - y2,\n" +
				"            min: 0,\n" +
				"            max: 100,\n" +
				"            minSize: '0%',\n" +
				"            maxSize: '100%',\n" +
				"            sort: 'descending',\n" +
				"            gap: 2,\n" +
				"            label: {\n" +
				"                show: true,\n" +
				"                position: 'inside'\n" +
				"            },\n" +
				"            labelLine: {\n" +
				"                length: 10,\n" +
				"                lineStyle: {\n" +
				"                    width: 1,\n" +
				"                    type: 'solid'\n" +
				"                }\n" +
				"            },\n" +
				"            itemStyle: {\n" +
				"                borderColor: '#fff',\n" +
				"                borderWidth: 1\n" +
				"            },\n" +
				"            emphasis: {\n" +
				"                label: {\n" +
				"                    fontSize: 20\n" +
				"                }\n" +
				"            },\n" +
				"            data: [\n" +
				"                {value: 60, name: '访问'},\n" +
				"                {value: 40, name: '咨询'},\n" +
				"                {value: 20, name: '订单'},\n" +
				"                {value: 80, name: '点击'},\n" +
				"                {value: 100, name: '展现'}\n" +
				"            ]\n" +
				"        }\n" +
				"    ]\n" +
				"};\n";
		String filePath = PhantomJsUtil.generateImgEChart(phantomjsPath, convetJsPath, "E:\\home\\pdf\\resources\\data\\", echartsOptions, "CCC");
		System.out.println("文件路径的地址是:" + filePath);
	}
	
	/**
	 * 雷达图
	 */
	private static void testImg4() {
		String echartsOptions = "option = {\n" +
				"    title: {\n" +
				"        text: '浏览器占比变化',\n" +
				"        subtext: '纯属虚构',\n" +
				"        top: 10,\n" +
				"        left: 10\n" +
				"    },\n" +
				"    tooltip: {\n" +
				"        trigger: 'item',\n" +
				"        backgroundColor: 'rgba(0,0,250,0.2)'\n" +
				"    },\n" +
				"    legend: {\n" +
				"        type: 'scroll',\n" +
				"        bottom: 10,\n" +
				"        data: (function (){\n" +
				"            var list = [];\n" +
				"            for (var i = 1; i <=28; i++) {\n" +
				"                list.push(i + 2000 + '');\n" +
				"            }\n" +
				"            return list;\n" +
				"        })()\n" +
				"    },\n" +
				"    visualMap: {\n" +
				"        top: 'middle',\n" +
				"        right: 10,\n" +
				"        color: ['red', 'yellow'],\n" +
				"        calculable: true\n" +
				"    },\n" +
				"    radar: {\n" +
				"        indicator: [\n" +
				"            { text: 'IE8-', max: 400},\n" +
				"            { text: 'IE9+', max: 400},\n" +
				"            { text: 'Safari', max: 400},\n" +
				"            { text: 'Firefox', max: 400},\n" +
				"            { text: 'Chrome', max: 400}\n" +
				"        ]\n" +
				"    },\n" +
				"    series: (function (){\n" +
				"        var series = [];\n" +
				"        for (var i = 1; i <= 28; i++) {\n" +
				"            series.push({\n" +
				"                name: '浏览器（数据纯属虚构）',\n" +
				"                type: 'radar',\n" +
				"                symbol: 'none',\n" +
				"                lineStyle: {\n" +
				"                    width: 1\n" +
				"                },\n" +
				"                emphasis: {\n" +
				"                    areaStyle: {\n" +
				"                        color: 'rgba(0,250,0,0.3)'\n" +
				"                    }\n" +
				"                },\n" +
				"                data: [{\n" +
				"                    value: [\n" +
				"                        (40 - i) * 10,\n" +
				"                        (38 - i) * 4 + 60,\n" +
				"                        i * 5 + 10,\n" +
				"                        i * 9,\n" +
				"                        i * i /2\n" +
				"                    ],\n" +
				"                    name: i + 2000 + ''\n" +
				"                }]\n" +
				"            });\n" +
				"        }\n" +
				"        return series;\n" +
				"    })()\n" +
				"};";
		String filePath = PhantomJsUtil.generateImgEChart(phantomjsPath, convetJsPath, "E:\\home\\pdf\\resources\\data\\", echartsOptions, "DDD");
		System.out.println("文件路径的地址是:" + filePath);
	}
}
