package com.cbd.transaction.builder.service.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BeneficiaryAccountDTO {

	private long id;
	private String beneficiaryIban;
	private String beneficiaryAccountId;
	private String beneficiaryAccountName;
	private String beneficiaryBankName;
	private String beneficiaryBankCode;
	private String beneficiaryBankAddress;
	private BigDecimal balance;
	private String currency;

	public BeneficiaryAccountDTO() {
		
	}

	public BeneficiaryAccountDTO(String beneficiaryIban, String beneficiaryAccountId, String beneficiaryAccountName, String beneficiaryBankName, String beneficiaryBankCode, String beneficiaryBankAddress, BigDecimal balance, String currency) {
		this.beneficiaryIban = beneficiaryIban;
		this.beneficiaryAccountId = beneficiaryAccountId;
		this.beneficiaryAccountName = beneficiaryAccountName;
		this.beneficiaryBankName = beneficiaryBankName;
		this.beneficiaryBankCode = beneficiaryBankCode;
		this.beneficiaryBankAddress = beneficiaryBankAddress;
		this.balance = balance;
		this.currency = currency;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBeneficiaryIban() {
		return beneficiaryIban;
	}

	public void setBeneficiaryIban(String beneficiaryIban) {
		this.beneficiaryIban = beneficiaryIban;
	}

	public String getBeneficiaryAccountId() {
		return beneficiaryAccountId;
	}

	public void setBeneficiaryAccountId(String beneficiaryAccountId) {
		this.beneficiaryAccountId = beneficiaryAccountId;
	}

	public String getBeneficiaryAccountName() {
		return beneficiaryAccountName;
	}

	public void setBeneficiaryAccountName(String beneficiaryAccountName) {
		this.beneficiaryAccountName = beneficiaryAccountName;
	}

	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}

	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}

	public String getBeneficiaryBankCode() {
		return beneficiaryBankCode;
	}

	public void setBeneficiaryBankCode(String beneficiaryBankCode) {
		this.beneficiaryBankCode = beneficiaryBankCode;
	}

	public String getBeneficiaryBankAddress() {
		return beneficiaryBankAddress;
	}

	public void setBeneficiaryBankAddress(String beneficiaryBankAddress) {
		this.beneficiaryBankAddress = beneficiaryBankAddress;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "BeneficiaryAccountDTO{" +
				"id=" + id +
				", beneficiaryIban='" + beneficiaryIban + '\'' +
				", beneficiaryAccountId='" + beneficiaryAccountId + '\'' +
				", beneficiaryAccountName='" + beneficiaryAccountName + '\'' +
				", beneficiaryBankName='" + beneficiaryBankName + '\'' +
				", beneficiaryBankCode='" + beneficiaryBankCode + '\'' +
				", beneficiaryBankAddress='" + beneficiaryBankAddress + '\'' +
				", balance=" + balance +
				", currency='" + currency + '\'' +
				'}';
	}
}
