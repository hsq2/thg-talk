package com.thg.accelerator.THGTalk;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableAutoConfiguration
public class SwaggerGeneration {

  public SwaggerGeneration() {
  }


  @Bean
  public ApiInfo metaData() {

    Contact contact = new Contact("Rehman", "https://www.thg.com/", "khanr@thehutgroup.com");
    return new ApiInfoBuilder()

        .title("Tasks Management System")
        .description("Your First Flavour of Spring Boot")
        .version("1")
        .license("THG Accelerator license 1")
        .contact(contact)
        .licenseUrl("Open Source")
        .build();
  }

  @Bean
  public Docket api() {
    // localhost:8080/v2/api-docs
    //localhost:8080/swagger-ui.html
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(metaData()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();

  }
}
