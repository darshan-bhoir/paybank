package com.cbd.master.data.service.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cbd.master.data.service.impl.CustomerService;
import com.cbd.master.data.service.model.CustomerAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/master")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customer")
	public ResponseEntity<List<CustomerAccountDTO>> getAllCustomerAccount() {
		try {
			List<CustomerAccountDTO> customerAccounts = customerService.getAllCustomerAccounts();
			if (Objects.isNull(customerAccounts)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customerAccounts, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customer/{iban}")
	public ResponseEntity<CustomerAccountDTO> getCustomerAccount(@PathVariable("iban") @NotNull String iban) {
		try {
			CustomerAccountDTO customerAccount = customerService.getCustomerAccount(iban);
			if (Objects.isNull(customerAccount)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customerAccount, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/customer")
	public ResponseEntity<CustomerAccountDTO> addCustomer(@RequestBody CustomerAccountDTO customerAccountDTO) {
			CustomerAccountDTO customerAccount = customerService.addCustomerAccount(customerAccountDTO);
			if (Objects.isNull(customerAccount)) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(customerAccount, HttpStatus.OK);
	}

	@DeleteMapping("/customer/{iban}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("iban") @NotNull String iban) {
		try {
			customerService.deleteCustomer(iban);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/customer")
	public ResponseEntity<CustomerAccountDTO> updateCustomer(@RequestBody @Valid CustomerAccountDTO customerAccountDTO) {
		try {
			CustomerAccountDTO customerAccount = customerService.updateCustomer(customerAccountDTO);
			return new ResponseEntity<>(customerAccount, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
