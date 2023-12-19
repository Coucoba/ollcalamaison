package com.univrouen.ollcalamaison;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class OllcalamaisonApplication {

	public static void main(String[] args) {
		SpringApplication.run(OllcalamaisonApplication.class, args);
	}

}
