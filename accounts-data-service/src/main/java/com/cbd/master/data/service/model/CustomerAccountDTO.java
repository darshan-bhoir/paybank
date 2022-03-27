package com.cbd.master.data.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "customer_accounts")
public class CustomerAccountDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "customer_id")
	@NotNull
	private String customerId;

	@Column(name = "customer_iban")
	@NotNull
	private String customerIban;

	@Column(name = "customer_account_id")
	@NotNull
	private String customerAccountId;
	
	@Column(name = "customer_bank_id")
	@NotNull
	private String customerBankId;
	
	@Column(name = "customer_bank_name")
	@NotNull
	private String customerBankName;

	@Column(name = "balance")
	@NotNull
	private BigDecimal balance;

	@Column(name = "currency")
	@NotNull
	private String currency;

	public CustomerAccountDTO() {
	}

	public CustomerAccountDTO(String customerId, String customerIban, String customerAccountId, String customerBankId, String customerBankName, BigDecimal balance, String currency) {
		this.customerId = customerId;
		this.customerIban = customerIban;
		this.customerAccountId = customerAccountId;
		this.customerBankId = customerBankId;
		this.customerBankName = customerBankName;
		this.balance = balance;
		this.currency = currency;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerIban() {
		return customerIban;
	}

	public void setCustomerIban(String customerIban) {
		this.customerIban = customerIban;
	}

	public String getCustomerAccountId() {
		return customerAccountId;
	}

	public void setCustomerAccountId(String customerAccountId) {
		this.customerAccountId = customerAccountId;
	}

	public String getCustomerBankId() {
		return customerBankId;
	}

	public void setCustomerBankId(String customerBankId) {
		this.customerBankId = customerBankId;
	}

	public String getCustomerBankName() {
		return customerBankName;
	}

	public void setCustomerBankName(String customerBankName) {
		this.customerBankName = customerBankName;
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
		return "CustomerAccountDTO{" +
				"id=" + id +
				", customerId='" + customerId + '\'' +
				", customerIban='" + customerIban + '\'' +
				", customerAccountId='" + customerAccountId + '\'' +
				", customerBankId='" + customerBankId + '\'' +
				", customerBankName='" + customerBankName + '\'' +
				", balance=" + balance +
				", currency='" + currency + '\'' +
				'}';
	}
}
