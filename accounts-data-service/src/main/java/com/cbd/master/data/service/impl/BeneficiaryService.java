package com.cbd.master.data.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cbd.master.data.service.exception.BadRequestException;
import com.cbd.master.data.service.model.BeneficiaryAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cbd.master.data.service.exception.EntityNotFoundException;
import com.cbd.master.data.service.repository.BeneficiaryRepository;


@Service
public class BeneficiaryService {

	@Autowired
	BeneficiaryRepository beneficiaryRepository;
	
	private Logger logger = LoggerFactory.getLogger(BeneficiaryService.class);

	public List<BeneficiaryAccountDTO> getAllBeneficiaries(){
		List<BeneficiaryAccountDTO> airports = new ArrayList<>();
		beneficiaryRepository.findAll().forEach(airport -> {
			airports.add(airport);
		});
		return airports;
	}

	public BeneficiaryAccountDTO getBeneficiary(String iban){
		List<BeneficiaryAccountDTO> beneficiaryAccountDTOList = beneficiaryRepository.findByBeneficiaryIbanContaining(iban);
		if(Objects.nonNull(beneficiaryAccountDTOList) && !beneficiaryAccountDTOList.isEmpty()){
			return beneficiaryAccountDTOList.get(0);
		}else {
			String errorMessage = String.format("Data for Beneficiary with Iban - %s does not exist", iban);
			logger.error(errorMessage);
			throw new EntityNotFoundException("S002",errorMessage);
		}
	}

	public BeneficiaryAccountDTO addBeneficiary(BeneficiaryAccountDTO beneficiaryAccountDTO) {
		if(StringUtils.hasText(beneficiaryAccountDTO.getBeneficiaryIban())) {
			List<BeneficiaryAccountDTO> beneficiaryAccountDTOList = beneficiaryRepository.findByBeneficiaryIbanContaining(beneficiaryAccountDTO.getBeneficiaryIban());
			if(beneficiaryAccountDTOList.isEmpty()) {
				BeneficiaryAccountDTO beneficiaryAccount = beneficiaryRepository
						.save(new BeneficiaryAccountDTO(
								beneficiaryAccountDTO.getBeneficiaryIban(),
								beneficiaryAccountDTO.getBeneficiaryAccountId(),
								beneficiaryAccountDTO.getBeneficiaryAccountName(),
								beneficiaryAccountDTO.getBeneficiaryBankName(),
								beneficiaryAccountDTO.getBeneficiaryBankCode(),
								beneficiaryAccountDTO.getBeneficiaryBankAddress(),
								beneficiaryAccountDTO.getBalance(),
								beneficiaryAccountDTO.getCurrency()
								)
							);
				return beneficiaryAccount;
			}else {
				String errorMessage = String.format("Beneficiary Information for iban - %s already exist", beneficiaryAccountDTO.getBeneficiaryIban());
				logger.error(errorMessage);
				throw new BadRequestException("S002", errorMessage);
			}
		}else {
			String errorMessage = String.format("Beneficiary Iban cannot be null or empty");
			logger.error(errorMessage);
			throw new BadRequestException("S001",errorMessage);
		}

	}

	public BeneficiaryAccountDTO updateBeneficiary(BeneficiaryAccountDTO beneficiaryAccountDTO) {
		if(StringUtils.hasText(beneficiaryAccountDTO.getBeneficiaryIban())) {
			List<BeneficiaryAccountDTO> beneficiaryAccountDTOList = beneficiaryRepository.findByBeneficiaryIbanContaining(beneficiaryAccountDTO.getBeneficiaryIban());
			if(!beneficiaryAccountDTOList.isEmpty()) {
				BeneficiaryAccountDTO entity = beneficiaryAccountDTOList.get(0);
				entity.setBeneficiaryAccountId(beneficiaryAccountDTO.getBeneficiaryAccountId());
				entity.setBeneficiaryAccountName(beneficiaryAccountDTO.getBeneficiaryAccountName());
				entity.setBeneficiaryBankName(beneficiaryAccountDTO.getBeneficiaryBankName());
				entity.setBeneficiaryBankCode(beneficiaryAccountDTO.getBeneficiaryBankCode());
				entity.setBeneficiaryBankAddress(beneficiaryAccountDTO.getBeneficiaryBankAddress());
				entity.setBalance(beneficiaryAccountDTO.getBalance());
				entity.setCurrency(beneficiaryAccountDTO.getCurrency());
				return beneficiaryRepository.save(entity);
			}else {
				String errorMessage = String.format("Data for Beneficiary with Iban - %s does not exist", beneficiaryAccountDTO.getBeneficiaryIban());
				logger.error(errorMessage);
				throw new EntityNotFoundException("S002",errorMessage);
			}
		}else {
			String errorMessage = String.format("Beneficiary Iban cannot be null or empty");
			logger.error(errorMessage);
			throw new BadRequestException("S001",errorMessage);
		}
	}

	public void deleteBeneficiary(String iban) {
		if(StringUtils.hasText(iban)) {
			List<BeneficiaryAccountDTO> beneficiaryAccountDTOList = beneficiaryRepository.findByBeneficiaryIbanContaining(iban);
			if(!beneficiaryAccountDTOList.isEmpty()) {
				beneficiaryRepository.delete(beneficiaryAccountDTOList.get(0));
			}else {
				String errorMessage = String.format("Data for Beneficiary with Iban - %s does not exist", iban);
				logger.error(errorMessage);
				throw new EntityNotFoundException("S002",errorMessage);
			} 
		}else {
			String errorMessage = String.format("Beneficiary Iban cannot be null or empty");
			logger.error(errorMessage);
			throw new BadRequestException("S001",errorMessage);
		}

	}
}
