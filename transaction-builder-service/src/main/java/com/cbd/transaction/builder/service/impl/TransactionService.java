package com.cbd.transaction.builder.service.impl;

import com.cbd.transaction.builder.service.exception.InsufficientBalanceException;
import com.cbd.transaction.builder.service.model.BeneficiaryAccountDTO;
import com.cbd.transaction.builder.service.model.CustomerAccountDTO;
import com.cbd.transaction.builder.service.model.TransactionDTO;
import com.cbd.transaction.builder.service.model.TransactionStatus;
import com.cbd.transaction.builder.service.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@PropertySource("classpath:application.properties")
public class TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	RestTemplate restTemplate;

	@Value("${get.cbd.customer.account.url}")
	private String getCustomerAccountURL;

	@Value("${get.cbd.beneficiary.account.url}")
	private String getBeneficiaryAccountURL;

	@Value("${upd.cbd.customer.account.url}")
	private String updCustomerAccountURL;

	@Value("${upd.cbd.beneficiary.account.url}")
	private String updBeneficiaryAccountURL;

	private Logger logger = LoggerFactory.getLogger(TransactionService.class);

	public TransactionDTO executeTransaction(TransactionDTO transactionDTO){
		try {
			CustomerAccountDTO customerAccountDTO = getCustomerAccountDetail(transactionDTO.getCustomerIban());
			BeneficiaryAccountDTO beneficiaryAccountDTO = getBeneficiaryAccountDetail(transactionDTO.getBeneficiaryIban());

			MonetaryAmount transactionAmount = Monetary.getDefaultAmountFactory()
					.setCurrency(transactionDTO.getTransactionCurrency())
					.setNumber(transactionDTO.getTransactionAmount())
					.create();

			customerAccountDTO = debitCustomerBankAccount(customerAccountDTO, transactionAmount);
			beneficiaryAccountDTO = creditBeneficiaryAccount(beneficiaryAccountDTO, transactionAmount);

			updateCustomerAccount(customerAccountDTO);
			updateBeneficiaryAccount(beneficiaryAccountDTO);
			transactionDTO.setTransactionRef(String.valueOf(Math.random()));
			transactionDTO.setTransactionDateTime(LocalDateTime.now());
			transactionDTO.setTransactionStatus(TransactionStatus.COMPLETED.name());
		} catch (Exception e){
			String errorMessage = String.format("Unable to execute transaction for Iban - %s due to exception %s ", transactionDTO.getCustomerIban(), e.getMessage());
			logger.error(errorMessage);
			transactionDTO.setTransactionStatus(TransactionStatus.FAILED.name());
		}
		transactionDTO = transactionRepository.save(transactionDTO);
		return transactionDTO;
	}

	private CustomerAccountDTO getCustomerAccountDetail(String iban){
		CustomerAccountDTO customerAccountDTO = new CustomerAccountDTO();
		String requestURL= new StringBuilder(getCustomerAccountURL).append(iban).toString();
		HttpEntity httpRequestEntity = new HttpEntity<>(getRequestHeaders());
		ResponseEntity<CustomerAccountDTO> customerAccountResponse = restTemplate.exchange(requestURL, HttpMethod.GET, httpRequestEntity, CustomerAccountDTO.class);
		if (Objects.nonNull(customerAccountResponse) && customerAccountResponse.getStatusCode().is2xxSuccessful()) {
			customerAccountDTO = customerAccountResponse.getBody();
		}else {
			String errorMessage = String.format("Unable to fetch customer bank account info for Iban - %s Response received %s URL %s ", iban, customerAccountResponse.getStatusCode(), getCustomerAccountURL);
			logger.error(errorMessage);
		}
		return  customerAccountDTO;
	}

	private CustomerAccountDTO updateCustomerAccount(CustomerAccountDTO customerAccountDTO){
		HttpEntity httpRequestEntity = new HttpEntity<>(customerAccountDTO, getRequestHeaders());
		ResponseEntity<CustomerAccountDTO> customerAccountResponse = restTemplate.exchange(updCustomerAccountURL, HttpMethod.PUT, httpRequestEntity, CustomerAccountDTO.class);
		if (Objects.nonNull(customerAccountResponse) && customerAccountResponse.getStatusCode().is2xxSuccessful()) {
			customerAccountDTO = customerAccountResponse.getBody();
		}else {
			String errorMessage = String.format("Unable to update customer bank account info for Iban - %s Response received %s URL %s ", customerAccountDTO.getCustomerIban(), customerAccountResponse.getStatusCode(), updCustomerAccountURL);
			logger.error(errorMessage);
		}
		return  customerAccountDTO;
	}

	private BeneficiaryAccountDTO updateBeneficiaryAccount(BeneficiaryAccountDTO beneficiaryAccountDTO){
		HttpEntity httpRequestEntity = new HttpEntity<>(beneficiaryAccountDTO, getRequestHeaders());
		ResponseEntity<BeneficiaryAccountDTO> beneficiaryAccountResponse = restTemplate.exchange(updBeneficiaryAccountURL, HttpMethod.PUT, httpRequestEntity, BeneficiaryAccountDTO.class);
		if (Objects.nonNull(beneficiaryAccountResponse) && beneficiaryAccountResponse.getStatusCode().is2xxSuccessful()) {
			beneficiaryAccountDTO = beneficiaryAccountResponse.getBody();
		}else {
			String errorMessage = String.format("Unable to update customer bank account info for Iban - %s Response received %s URL %s ", beneficiaryAccountDTO.getBeneficiaryIban(), beneficiaryAccountResponse.getStatusCode(), updBeneficiaryAccountURL);
			logger.error(errorMessage);
		}
		return  beneficiaryAccountDTO;
	}

	private BeneficiaryAccountDTO getBeneficiaryAccountDetail(String iban){
		BeneficiaryAccountDTO beneficiaryAccountDTO = new BeneficiaryAccountDTO();
		String requestURL= new StringBuilder(getBeneficiaryAccountURL).append(iban).toString();
		HttpEntity httpRequestEntity = new HttpEntity<>(getRequestHeaders());
		ResponseEntity<BeneficiaryAccountDTO> beneficiaryAccountResponse = restTemplate.exchange(requestURL, HttpMethod.GET, httpRequestEntity, BeneficiaryAccountDTO.class);
		if (Objects.nonNull(beneficiaryAccountResponse) && beneficiaryAccountResponse.getStatusCode().is2xxSuccessful()) {
			beneficiaryAccountDTO = beneficiaryAccountResponse.getBody();
		}else {
			String errorMessage = String.format("Unable to fetch beneficiary bank account info for Iban - %s Response received %s URL %s ", iban, beneficiaryAccountResponse.getStatusCode(), getBeneficiaryAccountURL);
			logger.error(errorMessage);
		}
		return  beneficiaryAccountDTO;
	}

	private CustomerAccountDTO debitCustomerBankAccount(CustomerAccountDTO customerAccountDTO, MonetaryAmount monetaryAmount){
		CurrencyConversion conversionToCustAccCurrency = MonetaryConversions.getConversion(customerAccountDTO.getCurrency());
		MonetaryAmount convertedMonetaryAmount = monetaryAmount.with(conversionToCustAccCurrency);
		BigDecimal transactionAmount = new BigDecimal(convertedMonetaryAmount.toString());
		if(customerAccountDTO.getBalance().compareTo(transactionAmount)==1){
			BigDecimal netBalance = customerAccountDTO.getBalance().subtract(transactionAmount);
			customerAccountDTO.setBalance(netBalance);
			return customerAccountDTO;
		}else {
			String errorMessage = String.format("Insufficient balance in customer account for Iban - %s ", customerAccountDTO.getCustomerIban());
			logger.error(errorMessage);
			throw new InsufficientBalanceException("S003", errorMessage);
		}
	}

	private BeneficiaryAccountDTO creditBeneficiaryAccount(BeneficiaryAccountDTO beneficiaryAccountDTO, MonetaryAmount monetaryAmount){
		CurrencyConversion conversionToCustAccCurrency = MonetaryConversions.getConversion(beneficiaryAccountDTO.getCurrency());
		MonetaryAmount convertedMonetaryAmount = monetaryAmount.with(conversionToCustAccCurrency);
		BigDecimal transactionAmount = new BigDecimal(convertedMonetaryAmount.toString());
		BigDecimal netBalance = beneficiaryAccountDTO.getBalance().add(transactionAmount);
		beneficiaryAccountDTO.setBalance(netBalance);
		return beneficiaryAccountDTO;
	}

	private HttpHeaders getRequestHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return  headers;
	}


	public String getGetCustomerAccountURL() {
		return getCustomerAccountURL;
	}

	public void setGetCustomerAccountURL(String getCustomerAccountURL) {
		this.getCustomerAccountURL = getCustomerAccountURL;
	}

	public String getGetBeneficiaryAccountURL() {
		return getBeneficiaryAccountURL;
	}

	public void setGetBeneficiaryAccountURL(String getBeneficiaryAccountURL) {
		this.getBeneficiaryAccountURL = getBeneficiaryAccountURL;
	}

	public String getUpdCustomerAccountURL() {
		return updCustomerAccountURL;
	}

	public void setUpdCustomerAccountURL(String updCustomerAccountURL) {
		this.updCustomerAccountURL = updCustomerAccountURL;
	}

	public String getUpdBeneficiaryAccountURL() {
		return updBeneficiaryAccountURL;
	}

	public void setUpdBeneficiaryAccountURL(String updBeneficiaryAccountURL) {
		this.updBeneficiaryAccountURL = updBeneficiaryAccountURL;
	}
}
