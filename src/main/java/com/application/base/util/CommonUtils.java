package com.application.base.util;

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
		StringBuffer buffer = new StringBuffer("");
		Properties props = System.getProperties();
		for (String value : values) {
			buffer.append(value+props.getProperty("file.separator"));
		}
		return buffer.toString();
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
	
}
