package com.cbd.master.data.service;

import java.math.BigDecimal;
import java.util.Arrays;

import com.cbd.master.data.service.model.BeneficiaryAccountDTO;
import com.cbd.master.data.service.model.CustomerAccountDTO;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cbd.master.data.service.repository.BeneficiaryRepository;
import com.cbd.master.data.service.repository.CustomerRepository;

@SpringBootApplication
public class MasterDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterDataServiceApplication.class, args);
	}

	@Bean
	public ApplicationRunner initializer(BeneficiaryRepository beneficiaryRepository, CustomerRepository customerRepository) {

		beneficiaryRepository.saveAll(Arrays.asList(
				new BeneficiaryAccountDTO("1234567890AE", "12343", "Test-Beneficiary-Account", "Test-Beneficiary-Bank", "Test-Beneficiary-BankCode", "Test-Beneficiary-BankAddr", new BigDecimal(1000), "EUR")
		));

		return args -> customerRepository.saveAll(Arrays.asList(
				new CustomerAccountDTO("123456", "0987654321AE", "1234567234", "Test_Cust_BankId", "CBD", new BigDecimal("1000"), "EUR")
		));
	}
}


