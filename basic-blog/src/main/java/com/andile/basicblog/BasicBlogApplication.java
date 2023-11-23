package com.andile.basicblog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Basic Blog REST API",
				description = "Description of the Basic Blog REST API",
				version = "v1.0",
				contact = @Contact(
						name = "Andile Khumalo",
						email = "andile.khumalo@clickatell.com"
				)
		)
)
public class BasicBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicBlogApplication.class, args);
	}

}
