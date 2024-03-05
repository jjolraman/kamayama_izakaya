package com.izakaya.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class IzakayaWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(IzakayaWebsiteApplication.class, args);
	}

}
