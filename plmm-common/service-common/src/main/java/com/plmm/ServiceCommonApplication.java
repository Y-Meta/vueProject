package com.plmm;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class ServiceCommonApplication {
  
  public static void main(String[] args) {
//      System.setProperty("dubbo.application.logger", "slf4j");
      SpringApplication.run(ServiceCommonApplication.class, args);
  }
  
}
