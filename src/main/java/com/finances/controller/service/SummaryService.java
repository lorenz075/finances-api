package com.finances.controller.service;

import java.math.BigDecimal;

import com.finances.model.dto.ExpenseByCategoryDto;

public interface SummaryService {
	
	BigDecimal getTotalIncome(int ano, int mes);
	BigDecimal getTotalExpense(int ano, int mes);
	BigDecimal getBalance(BigDecimal incomeValue, BigDecimal expenseValue);
	ExpenseByCategoryDto getExpenseByCategoryDto(int ano, int mes);
	
}
