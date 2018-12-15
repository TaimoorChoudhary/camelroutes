package com.assignment.camelroutes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;


@EnableJms
@SpringBootApplication
//@ImportResource("classpath*:/springintegration.xml")
public class CamelRoutesApplication {

//	@Bean
//	ServletRegistrationBean servletRegistrationBean() {
//		ServletRegistrationBean servlet = new ServletRegistrationBean
//				(new CamelHttpTransportServlet(), contextPath+"/*");
//		servlet.setName("CamelServlet");
//		return servlet;
//	}

	public static void main(String[] args) {
		SpringApplication.run(CamelRoutesApplication.class, args);
	}

}

