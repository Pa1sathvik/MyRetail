package org.target.myretail;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = "com.target")
public class MyRetailApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MyRetailApplication.class, args);
	}
	
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	 /**
     * Initialize Swagger documentation
     * @return
     */
    @SuppressWarnings("deprecation")
	@Bean
    public Docket productApi() {
        ApiInfo apiInfo =
                new ApiInfo("My Retail API", "API to store and retrieve data from MyRetail", "1",
                        "http://terms-of-service.url",
                        "Vutukuri Sathvik", "License", "http://licenseurl");
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/products.*"))
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false);
    }
}
