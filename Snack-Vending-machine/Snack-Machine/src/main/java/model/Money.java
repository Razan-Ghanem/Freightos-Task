package main.java.model;


import main.java.enums.Currency;
import main.java.enums.MoneySourceType;

public class Money {
	MoneySourceType type;
	double amount;
	Currency currency = Currency.USD;
	Object extension;

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public MoneySourceType getType() {
		return type;
	}

	public void setType(MoneySourceType type) {
		this.type = type;
	}

	public Object getExtension() {
		return extension;
	}

	public void setExtension(Object extension) {
		this.extension = extension;
	}
}
