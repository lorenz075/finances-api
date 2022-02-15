package com.finances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableSpringDataWebSupport
@EnableSwagger2
public class FinancesControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancesControlApplication.class, args);
	}

}
