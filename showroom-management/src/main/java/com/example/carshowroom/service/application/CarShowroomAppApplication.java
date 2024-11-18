package com.example.carshowroom.service.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@SpringBootApplication(scanBasePackages = "com.example.carshowroom")
@EnableJpaRepositories(basePackages = "com.example.carshowroom.dao.repository")
@EntityScan(basePackages = "com.example.carshowroom.dao.models")
public class CarShowroomAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarShowroomAppApplication.class, args);
	}

}
