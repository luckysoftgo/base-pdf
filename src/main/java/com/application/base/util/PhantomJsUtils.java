package com.application.base.util;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author : 孤狼
 * @NAME: PhantomJsUtils
 * @DESC: PhantomJsUtils类设计
 **/
public class PhantomJsUtils {
	
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String[] args) {
		String fontLocl =CommonUtils.getLocal(System.getProperty("user.dir"),"src","main","resources","static","font");
		fontLocl=fontLocl+"SIMSUN.TTC";
		System.out.println(fontLocl);
		System.out.println(writeFile("d:\\aa\\","aaabbb",new String[]{"aa","bb"}));
		System.out.println("===========1:"+System.getProperty("user.dir"));
		System.out.println("===========os.name:"+System.getProperties().getProperty("os.name"));
		System.out.println("===========file.separator1:"+System.getProperties().getProperty("file.separator"));
	}
	
	/**
	 * 获得生成的echarts图的地址.
	 * @param filePath
	 * @param echartsOptions
	 * @param uniqueTag
	 * @return
	 */
	public static String generateImgEChart(String filePath,String echartsOptions,String... uniqueTag) {
		String type = "linux";
		String exec = "phantomjs";
		if (!CommonUtils.isLinux()){
			type="window";
			exec = "phantomjs.exe";
		}
		String phantomJsPath =CommonUtils.getLocal(System.getProperty("user.dir"),"src","main","resources","phantomjs",type)+exec;
		String convetJsPath =CommonUtils.getLocal(System.getProperty("user.dir"),"src","main","resources","phantomjs","echartsconvert")+"echarts-convert.js";
		
		String dataPath = writeFile(filePath,echartsOptions,uniqueTag);
		String fileName = getFileName(uniqueTag) + ".png";
		String imgPath = filePath + fileName;
		try {
			File file = new File(imgPath);
			if(file.exists()){
				file.delete();
			}
			File dir = new File(file.getParent());
			dir.mkdirs();
			file.createNewFile();
			String cmd = phantomJsPath+" " + convetJsPath + " -infile " + dataPath + " -outfile " + imgPath;
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));
			String line = "";
			while ((line = input.readLine()) != null) {
				//TODO something.
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
		}
		return imgPath;
	}
	
	/**
	 * 生成base64字节码返回
	 * @param echartsOptions
	 * @param uniqueTag
	 * @return
	 */
	public static String generBase64EChart(String filePath,String echartsOptions,String... uniqueTag) {
		String imgPath = generateImgEChart(filePath,echartsOptions,uniqueTag);
		return getImgContent(imgPath);
	}
	
	/**
	 * 获得文件名字.
	 * @param uniqueTag
	 * @return
	 */
	private static String getFileName(String... uniqueTag){
		StringBuffer buffer = new StringBuffer();
		if (uniqueTag.length==1){
			buffer.append(uniqueTag[0]);
		}else {
			for (int i = 0; i <uniqueTag.length ; i++) {
				buffer.append(uniqueTag[i]);
				if (i!=uniqueTag.length-1){
					buffer.append("-");
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 写文件操作
	 * @param echartsOptions
	 * @param uniqueTag
	 * @return
	 */
	public static String writeFile(String jsonPath,String echartsOptions,String... uniqueTag) {
		String fileName=getFileName(uniqueTag)+".json";
		String dataPath=jsonPath+fileName;
		try {
			File file = new File(dataPath);
			if(file.exists()){
				file.delete();
			}
			File dir = new File(file.getParent());
			dir.mkdirs();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(echartsOptions);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataPath;
	}
	
	/**
	 * 获取html文件字符流
	 * @param htmlFilePath
	 * @return
	 */
	public static String getHtmlContent(String htmlFilePath) {
		String htmlStr = "";
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(htmlFilePath)), "UTF-8"));
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				htmlStr += lineTxt;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlStr;
	}
	
	/**
	 * 使用ITextRenderer 将html转换成pdf.
	 * @param pdfPath
	 * @param fileName
	 * @param htmlstr
	 */
	public static void htmlToPdf(String pdfPath, String fileName, String htmlstr) {
		FileOutputStream os = null;
		try {
			Thread.sleep(2000);
			//注意这里为啥要写这个，主要是替换成这样的字体，如果不设置中文有可能显示不出来。
			//htmlstr = htmlstr.replaceAll("\"", "'").replaceAll("<style>", "<style>body{font-family:SimSun;font-size:14px;}");
			ITextRenderer renderer = new ITextRenderer();
			ITextFontResolver fontResolver = renderer.getFontResolver();
			String fontLocl =CommonUtils.getLocal(System.getProperty("user.dir"),"src","main","resources","static","font");
			fontLocl=fontLocl+"SIMSUN.TTC";
			fontResolver.addFont(fontLocl, "Identity-H", false);
			File file = new File(pdfPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			os = new FileOutputStream(pdfPath + System.getProperty("file.separator") + fileName);
			renderer.setDocumentFromString(htmlstr);
			renderer.layout();
			System.out.println("PDF总页数:" + renderer.getRootBox().getLayer().getPages().size());
			renderer.createPDF(os);
			renderer.finishPDF();
			System.out.println("文件转换成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
		}
	}
	
	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgPath
	 * @return
	 */
	public static String getImgContent(String imgPath) {
		// 待处理的图片
		String imgFile = imgPath;
		InputStream in = null;
		byte[] data = null;
		// 返回Base64编码过的字节数组字符串
		String encode = null;
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			// 读取图片字节数组
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			encode = encoder.encode(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return encode;
	}
}
