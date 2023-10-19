package com.herryboro.order66;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Order66Application {

	public static void main(String[] args) {
		SpringApplication.run(Order66Application.class, args);
	}

}
