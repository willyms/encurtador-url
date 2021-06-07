package com.desafio.encurtadorurl;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

@SpringBootApplication
public class EncurtadorUrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncurtadorUrlApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${app.title}") String appTitle,
								 @Value("${app.description}") String appDesciption,
								 @Value("${app.version}") String appVersion) {
		return new OpenAPI()
				.info(new Info()
						.title(appTitle)
						.version(appVersion)
						.description(appDesciption)
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

}
