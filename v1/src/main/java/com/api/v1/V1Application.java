package com.api.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootApplication
public class V1Application {

	public static void main(String[] args) {
		var modules = ApplicationModules.of(V1Application.class);
		modules.verify();
		SpringApplication.run(V1Application.class, args);
	}

}
