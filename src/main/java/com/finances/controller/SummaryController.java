package com.finances.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finances.controller.service.SummaryService;
import com.finances.model.dto.ExpenseByCategoryDto;
import com.finances.model.dto.SummaryDto;
import com.finances.model.repository.ExpenseRepository;
import com.finances.model.repository.IncomeRespository;

@RestController
@RequestMapping("/summary")
public class SummaryController {

	@Autowired
	SummaryService service;
	
	@Autowired
	IncomeRespository incomeRespository;
	
	@Autowired
	ExpenseRepository expenseRepository;
	
	@GetMapping("/{ano}/{mes}")
	public SummaryDto getSummary(@PathVariable(name="ano") int ano, @PathVariable(name="mes") int mes) {
		BigDecimal totalIncome = service.getTotalIncome(ano, mes);
		BigDecimal totalExpense = service.getTotalExpense(ano, mes);
		BigDecimal balance = service.getBalance(totalIncome, totalExpense);
		ExpenseByCategoryDto byCategoryDto = service.getExpenseByCategoryDto(ano, mes);
		SummaryDto dto = new SummaryDto(totalIncome, totalExpense, balance, byCategoryDto);
		
		return dto;
	}
	
	
}
