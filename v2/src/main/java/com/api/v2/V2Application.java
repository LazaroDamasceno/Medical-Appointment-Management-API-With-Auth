package com.api.v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class V2Application {

	public static void main(String[] args) {
		var modules = ApplicationModules.of(V2Application.class);
		modules.forEach(System.out::println);
		modules.verify();
		SpringApplication.run(V2Application.class, args);
	}

}
