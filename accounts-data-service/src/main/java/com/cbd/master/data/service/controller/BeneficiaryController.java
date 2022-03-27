package com.cbd.master.data.service.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import com.cbd.master.data.service.impl.BeneficiaryService;
import com.cbd.master.data.service.model.BeneficiaryAccountDTO;
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

import com.sun.istack.NotNull;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/master")
public class BeneficiaryController {

	@Autowired
	private BeneficiaryService beneficiaryService;

	@GetMapping("/beneficiary")
	public ResponseEntity<List<BeneficiaryAccountDTO>> getAllBeneficiaries() {
		try {
			List<BeneficiaryAccountDTO> airports = beneficiaryService.getAllBeneficiaries();
					if (airports.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
					}
			return new ResponseEntity<>(airports, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/beneficiary/{iban}")
	public ResponseEntity<BeneficiaryAccountDTO> getBeneficiary(@PathVariable("iban") @NotNull String iban) {
		try {
			BeneficiaryAccountDTO beneficiaryAccountDTO = beneficiaryService.getBeneficiary(iban);
			if (Objects.isNull(beneficiaryAccountDTO)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(beneficiaryAccountDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/beneficiary")
	public ResponseEntity<BeneficiaryAccountDTO> addBeneficiary(@RequestBody BeneficiaryAccountDTO beneficiaryAccountDTO) {
		BeneficiaryAccountDTO airport = beneficiaryService.addBeneficiary(beneficiaryAccountDTO);
		if (Objects.isNull(airport)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BeneficiaryAccountDTO>(airport, HttpStatus.OK);
	}

	@DeleteMapping("/beneficiary/{iban}")
	public ResponseEntity<HttpStatus> deleteBeneficiary(@PathVariable("iban") @NotNull String iban) {
		try {
			beneficiaryService.deleteBeneficiary(iban);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/beneficiary")
	public ResponseEntity<BeneficiaryAccountDTO> updateBeneficiaryData(@RequestBody @Valid BeneficiaryAccountDTO beneficiaryAccountDTO) {
		try {
			BeneficiaryAccountDTO beneficiaryAccount = beneficiaryService.updateBeneficiary(beneficiaryAccountDTO);
			return new ResponseEntity<>(beneficiaryAccount, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
