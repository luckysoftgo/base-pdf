package com.application.base.poi;

import fr.opensagres.xdocreport.itext.extension.font.AbstractFontRegistry;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ：admin
 * @date ：2021-1-27
 * @description: 本地字体
 * @modified By：
 * @version: 1.0.0
 */
public class LocalTextFontRegistry extends AbstractFontRegistry {
	
	/**
	 * 字体样式.
	 */
	private String fontName;
	
	public LocalTextFontRegistry(String fontPath) {
		this.fontName = fontPath;
	}
	
	@Override
	protected String resolveFamilyName(String familyName, int style) {
		if (StringUtils.isBlank(fontName)) {
			//默认window系统的字体库位置.
			fontName = "C:\\Windows\\Fonts\\SIMSUN.TTC,0";
		}
		return fontName;
	}
}