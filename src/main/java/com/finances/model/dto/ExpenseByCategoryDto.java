package com.finances.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.finances.model.Expense;
import com.finances.model.ExpenseCategory;
import com.finances.model.repository.ExpenseRepository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @NoArgsConstructor @Getter
public class ExpenseByCategoryDto {

	private BigDecimal alimentacao;
	private BigDecimal educacao;
	private BigDecimal imprevistos;
	private BigDecimal lazer;
	private BigDecimal moradia;
	private BigDecimal saude;
	private BigDecimal transporte;
	private BigDecimal outras;

	
	
	public ExpenseByCategoryDto(ExpenseRepository expenseRepository, int ano, int mes) {
		this.alimentacao = categoryList(expenseRepository, ano, mes).get(0);
		this.educacao = categoryList(expenseRepository, ano, mes).get(1);
		this.imprevistos = categoryList(expenseRepository, ano, mes).get(2);
		this.lazer = categoryList(expenseRepository, ano, mes).get(3);
		this.moradia = categoryList(expenseRepository, ano, mes).get(4);
		this.saude = categoryList(expenseRepository, ano, mes).get(5);
		this.transporte = categoryList(expenseRepository, ano, mes).get(6);
		this.outras = categoryList(expenseRepository, ano, mes).get(7);
	}
	
	
	


	public List<BigDecimal> categoryList(ExpenseRepository expenseRepository, int ano, int mes) {
		List<ExpenseCategory> categoryListEnum = new ArrayList<>(Arrays.asList(ExpenseCategory.ALIMENTACAO,
				ExpenseCategory.EDUCACAO, ExpenseCategory.IMPREVISTOS, ExpenseCategory.LAZER, ExpenseCategory.MORADIA,
				 ExpenseCategory.SAUDE, ExpenseCategory.TRANSPORTE, ExpenseCategory.OUTRAS));
		List<List<Expense>> listOfListExpense = new ArrayList<>();
		List<BigDecimal> categoryValues =  new ArrayList<>();
		
		for (int i = 0; i < categoryListEnum.size(); i++) {
			listOfListExpense.add(expenseRepository.findByCategory(categoryListEnum.get(i), ano, mes));
		}
		
		for (int i = 0; i < listOfListExpense.size(); i++) {
			List<Expense> expenseList = new ArrayList<>(); 
			expenseList.addAll(listOfListExpense.get(i));
			List<BigDecimal> bdList = new ArrayList<>();
			
			expenseList.forEach(expense -> { bdList.add(expense.getValor()); });
			BigDecimal total = bdList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
			categoryValues.add(total);
		}
		
		return categoryValues;
	}
}
