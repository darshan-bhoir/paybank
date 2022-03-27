package com.cbd.master.data.service.repository;

import java.util.List;

import com.cbd.master.data.service.model.BeneficiaryAccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaryRepository extends JpaRepository<BeneficiaryAccountDTO, Long>{

	  List<BeneficiaryAccountDTO> findByBeneficiaryIbanContaining(String iban);
}
