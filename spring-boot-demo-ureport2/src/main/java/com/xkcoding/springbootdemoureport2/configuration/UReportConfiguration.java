package com.xkcoding.springbootdemoureport2.configuration;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:ureport-context.xml")
public class UReportConfiguration {

	@Bean
	public ServletRegistrationBean initUReport() {
		return new ServletRegistrationBean(new UReportServlet(), "/ureport/*");
	}
}
