package com.cbd.transaction.builder.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class TransactionDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "transactionRef")
	@NotNull
	private String transactionRef;
	
	@Column(name = "customerIban")
	@NotNull
	private String customerIban;
	
	@Column(name = "beneficiaryIban")
	@NotNull
	private String beneficiaryIban;
	
	@Column(name = "transactionAmount")
	@NotNull
	private BigDecimal transactionAmount;
	
	@Column(name = "transactionCurrency")
	@NotNull
	private String transactionCurrency;

	@Column(name = "transactionDateTime")
	@NotNull
	private LocalDateTime transactionDateTime;

	@Column(name = "debitDescription")
	@NotNull
	private String debitDescription;

	@Column(name = "creditDescription")
	@NotNull
	private String creditDescription;

	@Column(name = "transactionStatus")
	@NotNull
	private String transactionStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTransactionRef() {
		return transactionRef;
	}

	public void setTransactionRef(String transactionRef) {
		this.transactionRef = transactionRef;
	}

	public String getCustomerIban() {
		return customerIban;
	}

	public void setCustomerIban(String customerIban) {
		this.customerIban = customerIban;
	}

	public String getBeneficiaryIban() {
		return beneficiaryIban;
	}

	public void setBeneficiaryIban(String beneficiaryIban) {
		this.beneficiaryIban = beneficiaryIban;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	public LocalDateTime getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(LocalDateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getDebitDescription() {
		return debitDescription;
	}

	public void setDebitDescription(String debitDescription) {
		this.debitDescription = debitDescription;
	}

	public String getCreditDescription() {
		return creditDescription;
	}

	public void setCreditDescription(String creditDescription) {
		this.creditDescription = creditDescription;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public TransactionDTO() {
		
	}

	public TransactionDTO(String transactionRef, String customerIban, String beneficiaryIban, BigDecimal transactionAmount, String transactionCurrency, LocalDateTime transactionDateTime, String debitDescription, String creditDescription, String transactionStatus) {
		this.transactionRef = transactionRef;
		this.customerIban = customerIban;
		this.beneficiaryIban = beneficiaryIban;
		this.transactionAmount = transactionAmount;
		this.transactionCurrency = transactionCurrency;
		this.transactionDateTime = transactionDateTime;
		this.debitDescription = debitDescription;
		this.creditDescription = creditDescription;
		this.transactionStatus = transactionStatus;
	}

	@Override
	public String toString() {
		return "TransactionDTO{" +
				"id=" + id +
				", transactionRef='" + transactionRef + '\'' +
				", customerIban='" + customerIban + '\'' +
				", beneficiaryIban='" + beneficiaryIban + '\'' +
				", transactionAmount=" + transactionAmount +
				", transactionCurrency='" + transactionCurrency + '\'' +
				", transactionDateTime=" + transactionDateTime +
				", debitDescription='" + debitDescription + '\'' +
				", creditDescription='" + creditDescription + '\'' +
				", transactionStatus='" + transactionStatus + '\'' +
				'}';
	}
}
