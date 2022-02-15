package com.finances.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public class SummaryDto {
	
	
	
	private BigDecimal totalIncome;
	private BigDecimal totalExpense;
	private BigDecimal balance;
	ExpenseByCategoryDto expenseByCategory;
	
	
	public SummaryDto(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal balance, ExpenseByCategoryDto byCategoryDto) {
		super();
		this.totalIncome = totalIncome;
		this.totalExpense = totalExpense;
		this.balance = balance;
		this.expenseByCategory = byCategoryDto;
	}
	
	
	
	
}
