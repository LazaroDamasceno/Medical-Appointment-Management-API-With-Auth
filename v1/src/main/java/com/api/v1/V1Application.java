package com.api.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.modulith.core.ApplicationModules;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class V1Application {

	public static void main(String[] args) {
		var modules = ApplicationModules.of(V1Application.class);
		modules.verify();
		SpringApplication.run(V1Application.class, args);
	}
}
