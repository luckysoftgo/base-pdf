package com.application.base.util;

import com.application.base.config.PdfPropsConfig;
import com.application.base.datas.dto.CRResult;
import com.application.base.util.toolpdf.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author : 孤狼
 * @NAME: ImageUtils
 * @DESC: ImageUtils类设计
 **/
public class ImageUtils {
	
	/**
	 * 获得CR 的报告信息
	 * @param crResult
	 * @param pdfPropsConfig
	 * @param reportType
	 * @param creditCode
	 * @return
	 */
	public static String getImage(CRResult crResult, PdfPropsConfig pdfPropsConfig, String reportType, String creditCode){
		String AAstr = crResult.getAA();
		String creditgrade = crResult.getCrLevel();
		if(StringUtils.isNotEmpty(creditgrade)){
			Double grade = Double.parseDouble(AAstr);
			int point=getPointx(grade,creditgrade);
			try {
				String srcPath = pdfPropsConfig.getImgUrl()+ CommonUtils.getSplit() + "cr_img.png";
				String pointimg = pdfPropsConfig.getImgUrl()+ CommonUtils.getSplit()+ "crpoint_img.png";
				// 上传文件路径
				String destFile=pdfPropsConfig.getDataPath()+ CommonUtils.getSplit()+reportType+ CommonUtils.getSplit()+creditCode+ CommonUtils.getSplit();
				String fileName = creditCode + ".png" ;
				destFile = destFile + fileName;
				File df=new File(destFile);
				if (!df.getParentFile().exists()){
					df.getParentFile().mkdirs();
				}if (!df.exists()){
					df.createNewFile();
				}
				convertImageAddPoint(srcPath,pointimg,destFile,point);
				return destFile;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 分数和信用等级瞄点.
	 * @param grade
	 * @param creditgrade:cr1,cr2,cr3,cr4,cr5,cr6,cr7
	 * @return
	 */
	private static int getPointx(Double grade,String creditgrade){
		int cr1xlenth=4;
		int cr2xlenth=24;
		int cr3xlenth=49;
		int cr4xlenth=100;
		int cr5xlenth=55;
		int cr6xlenth=13;
		int cr7xlenth=28;
		int x=0;
		if(grade!=null && !StringUtils.isEmpty(creditgrade)){
			if("CR1".equalsIgnoreCase(creditgrade)){
				x=cr1xlenth;
			}else if("CR2".equalsIgnoreCase(creditgrade)){
				x=cr2xlenth+cr1xlenth;
			}else if("CR3".equalsIgnoreCase(creditgrade)){
				x=cr3xlenth+cr1xlenth+cr2xlenth;
			}else if("CR4".equalsIgnoreCase(creditgrade)){
				x=cr4xlenth+cr1xlenth+cr2xlenth+cr3xlenth;
			}else if("CR5".equalsIgnoreCase(creditgrade)){
				x=cr5xlenth+cr1xlenth+cr2xlenth+cr3xlenth+cr4xlenth;
			}else if("CR6".equalsIgnoreCase(creditgrade)){
				x=cr6xlenth+cr1xlenth+cr2xlenth+cr3xlenth+cr4xlenth+cr5xlenth;
			}else if("CR7".equalsIgnoreCase(creditgrade)){
				x=cr7xlenth+cr1xlenth+cr2xlenth+cr3xlenth+cr4xlenth+cr5xlenth+cr6xlenth;
			}
			x=x-36+7;
		}
		return x;
	}
	
	/**
	 * 处理图片，将pointimg固定原图片上，并生产新的图片
	 * @param srcPath
	 * @param pointimg
	 * @param destFile
	 */
	public static void convertImageAddPoint(String srcPath,String pointimg,String destFile,int x){
		try {
			BufferedImage big = ImageIO.read(new File(srcPath));
			Graphics2D gd = big.createGraphics();
			gd.setBackground(Color.white);
			BufferedImage small = ImageIO.read(new File(pointimg));
			int y = 2;
			gd.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			//在图形和图像中实现混合和透明效果
			gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,1));
			gd.dispose();
			ImageIO.write(big, "png", new File(destFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
