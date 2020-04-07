package com.application.base.config;

import com.application.base.util.PdfBuildServer;
import com.application.base.util.SealBuildServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 孤狼
 * @NAME: InitBuilderConfig
 * @DESC: InitBuilderConfig类设计
 **/
@Configuration
public class InitBuilderConfig {
	
	@Bean
	public PdfBuildServer getPdfBuilderServer(){
		PdfBuildServer server = new PdfBuildServer();
		return server;
	}
	
	@Bean
	public SealBuildServer getSealBuildServer(){
		SealBuildServer server = new SealBuildServer();
		return server;
	}
	
}
