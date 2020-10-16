package com.application.base.datas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author : 孤狼
 * @NAME: HttpConnUtil
 * @DESC: HttpConnUtil 类设计
 **/
public class HttpConnUtil {

	private static Logger log = LoggerFactory.getLogger(HttpConnUtil.class.getName());
	
	/**
	 * post请求
	 */
	private static final String HTTP_POST = "POST";
	/**
	 * get请求
	 */
	private static final String HTTP_GET = "GET";
	/**
	 * utf-8字符编码
	 */
	private static final String CHARSET_UTF_8 = "utf-8";
	
	/**
	 HTTP内容类型。如果未指定ContentType，默认为TEXT/HTML
	 */
	private static final String CONTENT_TYPE_TEXT_HTML = "text/xml";
	
	/**
	 HTTP内容类型。相当于form表单的形式，提交暑假
	 */
	private static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";
	
	/**
	 * 请求超时时间
	 */
	private static final int SEND_REQUEST_TIME_OUT = 50000;
	
	/**
	 将读超时时间
	 */
	private static final int READ_TIME_OUT = 50000;
	
	/**
	 * 定义调用天眼查返回结果code
	 */
	private static final Integer SUCCESS_CODE = 200;
	private static final Integer HTTP_REQ_CODE = 300;
	private static final Integer HTTP_ERR_CODE = 500;
	
	/**
	 *
	 * @param urlStr
	 *            请求地址
	 * @param body
	 *            请求发送内容
	 * @return 返回内容
	 */
	public static Map<String,Object> postRequest(String urlStr,String token, String body) {
		return requestMethod(HTTP_POST,urlStr,token,body);
	}
	
	/**
	 *
	 * @param urlStr
	 *            请求地址
	 * @param body
	 *            请求发送内容
	 * @return 返回内容
	 */
	public static Map<String,Object> getRequest(String urlStr,String token, String body) {
		return requestMethod(HTTP_GET,urlStr,token,body);
	}
	
	/**
	 *
	 * @param requestType
	 *            请求类型
	 * @param urlStr
	 *            请求地址
	 * @param body
	 *            请求发送内容
	 * @return 返回内容
	 */
	public static Map<String,Object> requestMethod(String requestType,String urlStr,String token,String body) {
		Map<String,Object> resultMap = new HashMap<>();
		//是否有http正文提交
		boolean isDoInput = false;
		if (body != null && body.length() > 0) {
			isDoInput = true;
		}
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		try {
			// 统一资源
			URL url = new URL(urlStr);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 设置通用的请求属性
			httpURLConnection.setRequestProperty("Authorization", token);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true, 默认情况下是false;
			if (isDoInput) {
				httpURLConnection.setDoOutput(true);
				httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.length()));
			}
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			httpURLConnection.setDoInput(true);
			// 设置一个指定的超时值（以毫秒为单位）
			httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
			// 将读超时设置为指定的超时，以毫秒为单位。
			httpURLConnection.setReadTimeout(READ_TIME_OUT);
			// Post 请求不能使用缓存
			if (requestType.equalsIgnoreCase(HTTP_POST)) {
				httpURLConnection.setUseCaches(false);
			}else {
				httpURLConnection.setUseCaches(true);
			}
			// 设置字符编码
			httpURLConnection.setRequestProperty("Accept-Charset", CHARSET_UTF_8);
			// 设置内容类型
			httpURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE_FORM_URL);
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod(requestType);
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			// 如果在已打开连接（此时 connected 字段的值为 true）的情况下调用 connect 方法，则忽略该调用。
			httpURLConnection.connect();
			if (isDoInput) {
				outputStream = httpURLConnection.getOutputStream();
				outputStreamWriter = new OutputStreamWriter(outputStream);
				outputStreamWriter.write(body);
				outputStreamWriter.flush();
			}
			if (httpURLConnection.getResponseCode() >= HTTP_REQ_CODE) {
				resultMap.put("code",HTTP_REQ_CODE);
				resultMap.put("msg","Http request bad!");
				return resultMap;
			}
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = httpURLConnection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream,CHARSET_UTF_8);
				reader = new BufferedReader(inputStreamReader);
				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
					//resultBuffer.append("\n");
				}
			}
		} catch (Exception e) {
			resultMap.put("code",HTTP_ERR_CODE);
			resultMap.put("msg","Http请求异常了,异常信息是:"+e.getMessage());
			return resultMap;
		} finally {// 关闭流
			try {
				if (outputStreamWriter != null) {
					outputStreamWriter.close();
				}
			} catch (Exception e) {
			}
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (Exception e) {
			}
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
			}
			try {
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
			} catch (Exception e) {
			}
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
			}
		}
		resultMap.put("code",SUCCESS_CODE);
		resultMap.put("msg","Http请求成功!");
		resultMap.put("data",resultBuffer.toString());
		return resultMap;
	}
	
	/**
	 * 将map集合的键值对转化成：key1=value1&key2=value2 的形式
	 * @param parameterMap
	 *            需要转化的键值对集合
	 * @return 字符串
	 */
	public static String convertGetStrParamter(Map<String,Object> parameterMap) {
		StringBuffer parameterBuffer = new StringBuffer();
		int index = 0;
		if (parameterMap != null && parameterMap.size()>0) {
			try {
				for (String key : parameterMap.keySet()) {
					String value = Objects.toString(parameterMap.get(key),"");
					if (index == 0) {
						parameterBuffer.append("?" + key + "=" + URLEncoder.encode(value, "UTF-8"));
					} else {
						parameterBuffer.append("&" + key + "=" + URLEncoder.encode(value, "UTF-8"));
					}
					index++;
				}
			}catch (Exception e){
				log.error("获取相应字段进行UTF-8编码设置出现了异常,异常信息是:{}",e.getMessage());
			}
		}
		return parameterBuffer.toString();
	}
	
	/**
	 * 将map集合的键值对转化成：key1=value1&key2=value2 的形式
	 * @param parameterMap
	 *            需要转化的键值对集合
	 * @return 字符串
	 */
	public static String convertGetStr(Map<String,Object> parameterMap) {
		StringBuffer parameterBuffer = new StringBuffer();
		int index = 0;
		if (parameterMap != null && parameterMap.size()>0) {
			try {
				for (String key : parameterMap.keySet()) {
					String value = Objects.toString(parameterMap.get(key),"");
					if (index == 0) {
						parameterBuffer.append("?" + key + "=" + value);
					} else {
						parameterBuffer.append("&" + key + "=" + value);
					}
					index++;
				}
			}catch (Exception e){
				log.error("获取相应字段进行UTF-8编码设置出现了异常,异常信息是:{}",e.getMessage());
			}
		}
		return parameterBuffer.toString();
	}
}
