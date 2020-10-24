package com.plmm;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@EnableDubbo
@ServletComponentScan 
@EnableCaching
@SpringBootApplication
public class WebCommonApplication {
	public static void main(String[] args) {
        System.setProperty("dubbo.application.logger", "slf4j");
        SpringApplication.run(WebCommonApplication.class, args);
    }
}