package com.plmm.webmvc.config;
//package com.plmm.webmvc.config;
//
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.unit.DataSize;
//import org.springframework.util.unit.DataUnit;
//
//import javax.servlet.MultipartConfigElement;
//
///**
// * @ProjectName: plmmProject
// * @Description:
// * @Author: qiguohui
// * @Date: 2020/4/28 10:40
// */
//@Configuration
//public class MultipartConfig {
//
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //文件最大10M,DataUnit提供5中类型B,KB,MB,GB,TB
//        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
//        // 设置总上传数据总大小10M
////        factory.setMaxRequestSize(DataSize.of(10, DataUnit.MEGABYTES));
//
//        return factory.createMultipartConfig();
//    }
//}
