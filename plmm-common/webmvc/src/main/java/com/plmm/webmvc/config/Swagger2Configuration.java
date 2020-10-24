package com.plmm.webmvc.config;

import com.plmm.webmvc.filter.ControllerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Configuration implements WebMvcConfigurer {

	Logger logger = LoggerFactory.getLogger(Swagger2Configuration.class);
	@Value("${plmm.host.f5:127.0.0.1}")
	String origins;
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(origins);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(controllerInterceptor());
	}
	
	@Bean
	public ControllerInterceptor controllerInterceptor(){
		return new ControllerInterceptor();
	}
	
	@Bean
    public Docket createDocApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("plmm") // 默认就是 default
                .apiInfo(createApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.plmm"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo createApiInfo(){
        return new ApiInfoBuilder()
                .title("数据平台系统Restful Apis")
                .description("数据平台系统Restful Apis详细说明")
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("http://localhost:8080")
                .contact(new Contact("plmm","http://www.plmm.com.cn","demo@plmm.com.cn"))
                .version("1.0.0")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}