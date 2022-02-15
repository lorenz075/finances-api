package com.finances.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.finances.model.Expense;
import com.finances.model.ExpenseCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ExpenseDto {
	
	private String descricao;
	private BigDecimal valor;
	private LocalDateTime data;
	private ExpenseCategory category;

	public ExpenseDto(Expense expense) {
		this.descricao = expense.getDescricao();
		this.valor = expense.getValor();
		this.data = expense.getData();
		this.category = expense.getCategory();
	}

	public static List<ExpenseDto> parseToDtoList(List<Expense> expenseList) {

		return expenseList.stream()
				.map(expense -> new ExpenseDto(expense))
				.collect(Collectors.toList());

	}
	
	public static ExpenseDto parseToDto(Expense expense) {
		
		return new ExpenseDto(expense);
		
	}
}
