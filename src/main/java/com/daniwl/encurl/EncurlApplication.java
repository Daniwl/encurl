package com.daniwl.encurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EncurlApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncurlApplication.class, args);
	}

}
