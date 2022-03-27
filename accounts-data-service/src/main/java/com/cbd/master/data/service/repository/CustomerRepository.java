package com.cbd.master.data.service.repository;

import java.util.List;

import com.cbd.master.data.service.model.CustomerAccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerAccountDTO, Long>{

	  List<CustomerAccountDTO> findByCustomerIbanContaining(String customerIban);
}
