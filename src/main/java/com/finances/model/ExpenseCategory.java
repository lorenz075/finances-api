package com.finances.model;

public enum ExpenseCategory {
	ALIMENTACAO("ALIMENTACAO"), SAUDE("SAUDE"), MORADIA("MORADIA"), TRANSPORTE("TRANSPORTE"), EDUCACAO("EDUCACAO"),
	LAZER("LAZER"), IMPREVISTOS("IMPREVISTOS"), OUTRAS("OUTRAS");

	private String value;

	private ExpenseCategory(String value) {
		this.value = value;
	}

	public String getCategory() {
		return value;
	}

	
}
