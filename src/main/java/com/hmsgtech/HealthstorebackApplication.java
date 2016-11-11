package com.hmsgtech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = { "classpath:/application.properties", "classpath:/sms.properties" })
@SpringBootApplication
public class HealthstorebackApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthstorebackApplication.class, args);
	}
}
