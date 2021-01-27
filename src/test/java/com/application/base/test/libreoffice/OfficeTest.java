package com.application.base.test.libreoffice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * @author ：admin
 * @date ：2021-1-26
 * @description:
 * @modified By：
 * @version: 1.0.0
 */
public class OfficeTest {
	
	private static final Logger logger = LoggerFactory.getLogger(OfficeTest.class);
	
	public static void main(String[] args) throws NullPointerException {
		long start = System.currentTimeMillis();
		String srcPath = "E:\\home\\pdf\\resources\\data\\result_test8.docx";
		String desPath = "E:\\home\\pdf\\resources\\data\\tmp\\";
		srcPath = "E:\\home\\pdf\\resources\\data\\result_test14.docx";
		
		//执行的命令:
		String command = officeHome() + " --headless --invisible --convert-to docx " + srcPath + " --outdir " + desPath;
		exec(command);
		
		//执行的命令:
		/*
		command = officeHome() + " --headless --convert-to pdf:writer_pdf_Export " + srcPath + " --outdir " + desPath;
		exec(command);
		
		command = officeHome() + " --headless --invisible --convert-to html " + srcPath + " --outdir " + desPath;
		exec(command);
		*/
		
		long end = System.currentTimeMillis();
		System.out.println("用时:" + (end - start) / 1000 + " s");
	}
	
	public static boolean exec(String command) {
		Process process;// Process可以控制该子进程的执行或获取该子进程的信息
		try {
			logger.debug("exec cmd : {}", command);
			process = Runtime.getRuntime().exec(command);// exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
			// 下面两个可以获取输入输出流
			InputStream errorStream = process.getErrorStream();
			InputStream inputStream = process.getInputStream();
			while (errorStream.read() != -1) {
				System.out.println(errorStream.read());
			}
			errorStream.close();
		} catch (IOException e) {
			logger.error(" exec {} error", command, e);
			return false;
		}
		int exitStatus = 0;
		try {
			exitStatus = process.waitFor();// 等待子进程完成再往下执行，返回值是子线程执行完毕的返回值,返回0表示正常结束
			// 第二种接受返回值的方法
			int i = process.exitValue(); // 接收执行完毕的返回值
			logger.debug("i----" + i);
		} catch (InterruptedException e) {
			logger.error("InterruptedException  exec {}", command, e);
			return false;
		}
		if (exitStatus != 0) {
			logger.error("exec cmd exitStatus {}", exitStatus);
		} else {
			logger.debug("exec cmd exitStatus {}", exitStatus);
		}
		process.destroy(); // 销毁子进程
		process = null;
		return true;
	}
	
	public static String officeHome() {
		String osName = System.getProperty("os.name");
		if (Pattern.matches("Linux.*", osName)) {
			return "/opt/libreoffice5.3/program/soffice";
		} else if (Pattern.matches("Windows.*", osName)) {
			return "D:\\installer\\libreoffice\\program\\soffice.exe";
		}
		return null;
	}
	
}
