package com.cbd.transaction.builder.service.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CustomerAccountDTO {

	private long id;
	private String customerId;
	private String customerIban;
	private String customerAccountId;
	private String customerBankId;
	private String customerBankName;
	private BigDecimal balance;
	private String currency;

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
