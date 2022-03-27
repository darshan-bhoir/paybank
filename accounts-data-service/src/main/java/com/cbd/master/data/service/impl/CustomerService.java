package com.cbd.master.data.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cbd.master.data.service.exception.BadRequestException;
import com.cbd.master.data.service.exception.EntityNotFoundException;
import com.cbd.master.data.service.model.BeneficiaryAccountDTO;
import com.cbd.master.data.service.model.CustomerAccountDTO;
import com.cbd.master.data.service.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	private Logger logger = LoggerFactory.getLogger(CustomerService.class);


	public List<CustomerAccountDTO> getAllCustomerAccounts(){
		List<CustomerAccountDTO> customerAccountDTOList = new ArrayList<>();
		customerRepository.findAll().forEach(customerAccountDTO -> {
			customerAccountDTOList.add(customerAccountDTO);
		});
		return customerAccountDTOList;
	}

	public CustomerAccountDTO getCustomerAccount(String iban){
		List<CustomerAccountDTO> customerAccounts = customerRepository.findByCustomerIbanContaining(iban);
		if(Objects.nonNull(customerAccounts) && !customerAccounts.isEmpty()){
			return customerAccounts.get(0);
		}else{
			String errorMessage = String.format("Data for Customer with Iban - %s does not exist", iban);
			logger.error(errorMessage);
			throw new EntityNotFoundException("S002",errorMessage);
		}
	}

	public CustomerAccountDTO addCustomerAccount(CustomerAccountDTO customerAccountDTO) {
		if(StringUtils.hasText(customerAccountDTO.getCustomerAccountId())) {
			List<CustomerAccountDTO> customerAccounts = customerRepository.findByCustomerIbanContaining(customerAccountDTO.getCustomerIban());
			if(customerAccounts.isEmpty()) {
				CustomerAccountDTO customerAccount = customerRepository
						.save(new CustomerAccountDTO(
								customerAccountDTO.getCustomerId(),
								customerAccountDTO.getCustomerIban(),
								customerAccountDTO.getCustomerAccountId(),
								customerAccountDTO.getCustomerBankId(),
								customerAccountDTO.getCustomerBankName(),
								customerAccountDTO.getBalance(),
								customerAccountDTO.getCurrency()));
				return customerAccount;
			}else {
				String errorMessage = String.format("Data for Customer with Iban - %s already exist", customerAccountDTO.getCustomerIban());
				logger.error(errorMessage);
				throw new BadRequestException("S002", errorMessage);
			}
		}else {
			String errorMessage = String.format("Customer Iban cannot be null or empty");
			logger.error(errorMessage);
			throw new BadRequestException("S001",errorMessage);
		}

	}

	public CustomerAccountDTO updateCustomer(CustomerAccountDTO customerAccountDTO) {
		if(StringUtils.hasText(customerAccountDTO.getCustomerIban())) {
			List<CustomerAccountDTO> customerAccounts = customerRepository.findByCustomerIbanContaining(customerAccountDTO.getCustomerIban());
			if(!customerAccounts.isEmpty()) {
				CustomerAccountDTO customerAccount = customerAccounts.get(0);
				customerAccount.setCustomerIban(customerAccountDTO.getCustomerIban());
				customerAccount.setCustomerAccountId(customerAccountDTO.getCustomerAccountId());
				customerAccount.setCustomerBankId(customerAccountDTO.getCustomerBankId());
				customerAccount.setCustomerBankName(customerAccountDTO.getCustomerBankName());
				customerAccount.setBalance(customerAccountDTO.getBalance());
				customerAccount.setCurrency(customerAccountDTO.getCurrency());
				return customerRepository.save(customerAccount);
			}else {
				String errorMessage = String.format("Data for Customer with Iban - %s does not exist", customerAccountDTO.getCustomerIban());
				logger.error(errorMessage);
				throw new EntityNotFoundException("S002",errorMessage);
			} 

		}else {
			String errorMessage = String.format("Customer Iban cannot be null or empty");
			logger.error(errorMessage);
			throw new BadRequestException("S001",errorMessage);
		}
	}

	public void deleteCustomer(String customerIban) {
		if(StringUtils.hasText(customerIban)) {
			List<CustomerAccountDTO> customerAccounts = customerRepository.findByCustomerIbanContaining(customerIban);
			if(!customerAccounts.isEmpty()) {
				customerRepository.delete(customerAccounts.get(0));
			}else {
				String errorMessage = String.format("Data for Customer with Customer Iban - %s does not exist", customerIban);
				logger.error(errorMessage);
				throw new EntityNotFoundException("S002",errorMessage);
			} 
		}else {
			String errorMessage = String.format("Customer Iban cannot be null or empty");
			logger.error(errorMessage);
			throw new BadRequestException("S001",errorMessage);
		}

	}
}
