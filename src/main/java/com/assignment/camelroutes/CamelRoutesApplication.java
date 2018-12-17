package com.assignment.camelroutes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;


@EnableJms
@SpringBootApplication
@ImportResource("classpath*:/camel-context.xml")
public class CamelRoutesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelRoutesApplication.class, args);
	}

}

