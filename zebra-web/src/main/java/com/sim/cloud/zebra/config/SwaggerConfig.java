package com.sim.cloud.zebra.config;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.*;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liuxianbing
 * @version 2017年6月21日 上午9:50:58
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = { "com.sim.cloud.zebra.web" })
public class SwaggerConfig extends WebMvcConfigurationSupport {

	@Bean
	public Docket swaggerSpringMvcPlugin() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("business-api").select() // 选择那些路径和api会生成document
				.apis(RequestHandlerSelectors.basePackage("com.sim.cloud.zebra.web"))
				// .paths(paths())
				// .apis(RequestHandlerSelectors.any()) // 对所有api进行监控
				.paths(PathSelectors.any()) // 对所有路径进行监控
				.build().securitySchemes(securitySchemes()).securityContexts(securityContexts());
	}

	private List<ApiKey> securitySchemes() {
		return newArrayList(new ApiKey("clientId", "客户端ID", "header"), new ApiKey("clientSecret", "客户端秘钥", "header"),
				new ApiKey("accessToken", "客户端访问标识", "header"));
	}

	private List<SecurityContext> securityContexts() {
		return newArrayList(SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/*.*")).build());
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return newArrayList(new SecurityReference("clientId", authorizationScopes),
				new SecurityReference("clientSecret", authorizationScopes),
				new SecurityReference("accessToken", authorizationScopes));
	}
	// @Bean
	// public Docket platformApi() {
	// return new
	// Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).forCodeGeneration(true);
	// }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("自定义主题").termsOfServiceUrl("http://blog.csdn.net/yangshijin1988")
				.description("此API提供接口调用").license("License Version 2.0")
				.licenseUrl("http://blog.csdn.net/yangshijin1988").version("2.0").build();
	}

	// private ApiInfo apiInfo() {
	// return new ApiInfoBuilder().title("Lantern-API").description("©2017
	// Copyright. Powered By Lantern.")
	// // .termsOfServiceUrl("")
	// .contact(new Contact("Lantern", "",
	// "liuxianbing@163.com")).license("Apache License Version 2.0")
	// .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE").version("2.0").build();
	// }

}