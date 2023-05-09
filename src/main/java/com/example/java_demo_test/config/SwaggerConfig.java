//package com.example.java_demo_test.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration  //�� spring ���� class ���t�m��
//@EnableOpenApi  //�q�L�������ӱҥ� swagger
//@EnableWebMvc  //�ޤJ DelegatingWebMvcConfiguration �t�m���A�ñҥ� spring MVC
//public class SwaggerConfig {
//	
//	//���f�T���A�]�t: ���D�B�����B�����B�p���H��
//	private ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
//			.title("java_demo_test Restful API")  //���D
//			.description("java_demo_test API")  //����
////			.termsOfServiceUrl("urn:tos")  //�A�ȱ��ں��}�A�q�{ urn:tos
////			.contact(new Contact("DEFAULT", "", ""))  //�p���H
////			.license("Apache 2.0")  //�\�i
////			.version("v3")  //����
////			.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.txt")
//			.build();
//	
//	@Bean
//	public Docket api() {
//		//��^���ɪ����n�T��
//		return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(DEFAULT_API_INFO)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.example.java_demo_test.controller"))
//				.paths(PathSelectors.any())
//				.build();
//	}
//}
