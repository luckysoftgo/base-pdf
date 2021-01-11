package com.application.base.util;

/**
 * @author ：孤狼
 * @description: 返回结果对象
 * @modified By：
 * @version: 1.0.0
 */
public class GenericVO<T> {
	/**
	 * 返回的结果 code
	 */
	private Integer code;
	/**
	 * 描述信息
	 */
	private String msg;
	/**
	 * 数据结果集
	 */
	private T data;
	
	public GenericVO() {
	}
	
	public GenericVO(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public GenericVO(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
}
