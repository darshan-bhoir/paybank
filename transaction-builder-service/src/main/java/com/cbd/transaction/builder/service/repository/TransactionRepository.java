package com.cbd.transaction.builder.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbd.transaction.builder.service.model.TransactionDTO;

public interface TransactionRepository extends JpaRepository<TransactionDTO, Long>{

	  List<TransactionDTO> findByTransactionRefContaining(String transactionRef);
}
