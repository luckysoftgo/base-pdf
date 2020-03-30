package com.application.base.config;

import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * @author : 孤狼
 * @NAME: FreeMarkerConfig
 * @DESC: FreeMarkerViewResolver类设计
 **/
@Configuration
public class FreeMarkerConfig {
	
	@Bean
	public ViewResolver viewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setCache(false);
		resolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
		resolver.setRequestContextAttribute("request");
		resolver.setExposeSpringMacroHelpers(true);
		resolver.setExposeRequestAttributes(true);
		resolver.setExposeSessionAttributes(true);
		resolver.setSuffix(".ftl");
		resolver.setOrder(1);
		resolver.setViewClass(FreeMarkerView.class);
		resolver.setContentType("text/html;charset=UTF-8");
		return resolver;
	}
	
	@Bean
	public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
		FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
		factory.setTemplateLoaderPaths("classpath:/templetes/", "src/main/resources/templetes/");
		factory.setDefaultEncoding("UTF-8");
		FreeMarkerConfigurer result = new FreeMarkerConfigurer();
		freemarker.template.Configuration configuration = factory.createConfiguration();
		configuration.setClassicCompatible(true);
		result.setConfiguration(configuration);
		Properties settings = new Properties();
		settings.put("tag_syntax", "auto_detect");
		settings.put("output_encoding", "UTF-8");
		settings.put("locale", "zh_CN");
		settings.put("template_update_delay", "1000");
		settings.put("default_encoding", "UTF-8");
		settings.put("number_format", "0.##########");
		settings.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
		settings.put("classic_compatible", true);
		settings.put("template_exception_handler", "ignore");
		result.setFreemarkerSettings(settings);
		return result;
	}
}
