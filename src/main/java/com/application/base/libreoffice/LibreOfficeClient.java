package com.application.base.libreoffice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ：admin
 * @description: 利用LibreOffice处理报告
 * @modified By：
 * @version: 1.0.0
 */
@Slf4j
@Component
public class LibreOfficeClient {
	
	@Autowired
	private LibreOfficeConfig officeConfig;
	
	//@Resource
	//private DocumentConverter documentConverter;
	
	/**
	 * 执行转换
	 * @param docxPath:源 word 的绝对路径.
	 * @param convertPath: 转换后的文件的存储路径.
	 * @return
	 * @throws Exception
	 */
	/*
	public boolean execConvert(String docxPath,String convertPath) throws Exception{
		// 具体转换方法，参数是java.io.File
		try {
			documentConverter.convert(new File(docxPath)).to(new File(convertPath)).execute();
		}catch (Exception e){
			log.error("执行转换异常了,异常信息是:{}",e.getMessage());
			return false;
		}
		return true;
	}
	*/
	
	
	/**
	 * 执行转换
	 *
	 * @param docxPath:源   word 的绝对路径.
	 * @param convertPath: 转换后的文件的存储路径.
	 * @return
	 * @throws Exception
	 */
	public boolean execConvertPdf(String docxPath, String convertPath) throws Exception {
		String officeHome = officeConfig.getLibreofficeHome() == null ? "D:\\installer\\libreoffice\\program\\soffice.exe" : officeConfig.getLibreofficeHome();
		//执行的命令:
		String command = officeHome + officeConfig.getExecPdfCommand() + docxPath + " --outdir " + convertPath;
		//执行结果
		return execCommand(command);
	}
	
	/**
	 * 执行转换
	 *
	 * @param execCommand:执行的命令
	 * @param docxPath:源        word 的绝对路径.
	 * @param convertPath:      转换后的文件的存储路径.
	 * @return
	 * @throws Exception
	 */
	public boolean execConvertFile(String execCommand, String docxPath, String convertPath) throws Exception {
		String officeHome = officeConfig.getLibreofficeHome() == null ? "D:\\installer\\libreoffice\\program\\soffice.exe" : officeConfig.getLibreofficeHome();
		//执行的命令:
		String command = officeHome + " " + execCommand + " " + docxPath + " --outdir " + convertPath;
		//执行结果
		return execCommand(command);
	}
	
	/**
	 * 执行命令
	 *
	 * @param command
	 * @return
	 */
	private boolean execCommand(String command) {
		// Process可以控制该子进程的执行或获取该子进程的信息
		Process process;
		try {
			log.debug("LibreOfficeClient.execCommand command : {}", command);
			// exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
			process = Runtime.getRuntime().exec(command);
			// 下面两个可以获取输入输出流
			InputStream errorStream = process.getErrorStream();
			InputStream inputStream = process.getInputStream();
			//等待执行完成
			while (errorStream.read() != -1) {
			}
			while (inputStream.read() != -1) {
			}
			errorStream.close();
			inputStream.close();
		} catch (IOException e) {
			log.error("LibreOfficeClient.execCommand command {} error", command, e.getMessage());
			return false;
		}
		int exitStatus = 0;
		try {
			// 等待子进程完成再往下执行，返回值是子线程执行完毕的返回值,返回0表示正常结束
			exitStatus = process.waitFor();
			// 第二种接受返回值的方法
			// 接收执行完毕的返回值
			int value = process.exitValue();
			log.debug("接收执行完毕的返回值是:{}", value);
		} catch (InterruptedException e) {
			log.error("LibreOfficeClient InterruptedException  execCommand {}", command, e);
			return false;
		}
		log.error("LibreOfficeClient.execCommand cmd exitStatus {}", exitStatus);
		// 销毁子进程
		process.destroy();
		process = null;
		return true;
	}
}
