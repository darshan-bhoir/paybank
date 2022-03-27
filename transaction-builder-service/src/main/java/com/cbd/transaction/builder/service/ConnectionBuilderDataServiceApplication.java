package com.cbd.transaction.builder.service;

import java.util.Arrays;

import com.cbd.transaction.builder.service.repository.TransactionRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cbd.transaction.builder.service.model.TransactionDTO;

@SpringBootApplication
public class ConnectionBuilderDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectionBuilderDataServiceApplication.class, args);
	}
	
//	@Bean
//	public ApplicationRunner initializer() {
//		return;
//	}

}
