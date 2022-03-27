package com.cbd.transaction.builder.service.controller;

import javax.validation.Valid;

import com.cbd.transaction.builder.service.model.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cbd.transaction.builder.service.impl.TransactionService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/transaction")
	public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
		TransactionDTO transaction = transactionService.executeTransaction(transactionDTO);
		return new ResponseEntity<>(transaction, HttpStatus.OK);
	}

}
