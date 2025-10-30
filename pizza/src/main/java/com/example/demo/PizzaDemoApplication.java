package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication(
		scanBasePackages = {"com.example.demo", "com.example.pizza-api"}
)
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class PizzaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaDemoApplication.class, args);
	}

}