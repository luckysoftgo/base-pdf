package com.application.base.util.toolpdf;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author : 孤狼
 * @NAME: CommonUtils
 * @DESC: CommonUtils类设计
 **/
public class CommonUtils {
	
	/**
	 * 获取文件地址.
	 * @param values
	 * @return
	 */
	public static String getLocal(String... values){
		if (values==null && values.length==0){
			return "";
		}
		StringBuffer buffer = new StringBuffer("");
		Properties props = System.getProperties();
		for (String value : values) {
			buffer.append(value+props.getProperty("file.separator"));
		}
		return buffer.toString();
	}
	
	/**
	 * 分隔符号.
	 * @return
	 */
	public static String getSplit(){
	    return System.getProperty("file.separator");
	}
	
	/**
	 * 是否是linux系统
	 * @return
	 */
	public static boolean isLinux(){
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.startsWith("linux")){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否是linux系统
	 * @return
	 */
	public static boolean isWindow(){
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.startsWith("win")){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为空.
	 * @param cs
	 * @return
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}
	
	/**
	 * 判断是否为空.
	 * @param cs
	 * @return
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	
	
	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件
	 * @param filePath
	 * @return
	 */
	public static byte[] readFileByByte(String filePath) {
		if (CommonUtils.isBlank(filePath)){
			return null;
		}
		InputStream in = null;
		File file = new File(filePath);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try{
			in = new FileInputStream(file);
			byte[] buf = new byte[1024];
			int length = 0;
			while((length = in.read(buf)) != -1) {
				out.write(buf,0, length);
			}
			return out.toByteArray();
		}catch(Exception e1) {
			try {
				return Files.readAllBytes(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}finally {
			try {
				if (in!=null){
					in.close();
				}
				if (out!=null){
					out.flush();
					out.close();
				}
			}catch (Exception e){}
		}
		return null;
	}
	
	/**
	 * 創建文件路徑
	 * @param filePath
	 */
	public static void createDir(String filePath){
		File file=new File(filePath);
		if (!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 * 非空验证统一方法
	 * @param args
	 */
	public static String nullValidation(Map<String, Object> params, String... args) {
		StringBuffer buffer = new StringBuffer("");
		for(String key : args){
			String value = Objects.toString(params.get(key),"");
			if(StringUtils.isEmpty(value)) {
				buffer.append("必输字段:"+key+"的值为空!,");
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 获得 request 请求的参数
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getRequestParams(HttpServletRequest request) {
		Map<String, Object> paramsMap = new HashMap<>();
		Enumeration<?> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = Objects.toString(parameterNames.nextElement());
			try {
				String value = null;
				String[] values = request.getParameterValues(name);
				if (values != null && values.length == 1) {
					value = values[0];
					paramsMap.put(name, value.trim());
				}
			} catch (Exception e) {
				System.out.println("获取页面参数时异常,参数名称【{"+name+"}】异常信息【{"+e.toString()+"}】");
			}
		}
		return paramsMap;
	}
}
