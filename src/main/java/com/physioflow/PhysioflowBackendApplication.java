package com.physioflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PhysioflowBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhysioflowBackendApplication.class, args);
	}
}