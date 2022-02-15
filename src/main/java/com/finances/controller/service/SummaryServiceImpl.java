package com.finances.controller.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finances.model.Expense;
import com.finances.model.Income;
import com.finances.model.dto.ExpenseByCategoryDto;
import com.finances.model.repository.ExpenseRepository;
import com.finances.model.repository.IncomeRespository;

@Service
public class SummaryServiceImpl implements SummaryService {

	@Autowired
	IncomeRespository incomeRespository;

	@Autowired
	ExpenseRepository expenseRepository;

	@Override
	public BigDecimal getTotalIncome(int ano, int mes) {

		List<Income> incomeList = incomeRespository.findByMes(ano, mes);
		List<BigDecimal> bdList = new ArrayList<>();

		incomeList.forEach(income -> {
			bdList.add(income.getValor());
		});

		BigDecimal total = bdList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

		return total;
	}

	@Override
	public BigDecimal getTotalExpense(int ano, int mes) {
		List<Expense> expenseList = expenseRepository.findByMes(ano, mes);
		List<BigDecimal> bdList = new ArrayList<>();

		expenseList.forEach(expense -> {
			bdList.add(expense.getValor());
		});

		BigDecimal total = bdList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

		return total;
	}

	@Override
	public BigDecimal getBalance(BigDecimal incomeValue, BigDecimal expenseValue) {
		BigDecimal balance = incomeValue.subtract(expenseValue);
		return balance;
	}

	@Override
	public ExpenseByCategoryDto getExpenseByCategoryDto(int ano, int mes) {
		ExpenseByCategoryDto byCategoryDto = new ExpenseByCategoryDto(expenseRepository, ano, mes);
		return byCategoryDto;
	}
	
}
