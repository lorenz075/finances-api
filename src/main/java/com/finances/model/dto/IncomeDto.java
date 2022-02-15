package com.finances.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.finances.model.Income;

import lombok.Getter;

@Getter
public class IncomeDto {

	private String descricao;
	private BigDecimal valor;
	private LocalDateTime data;

	public IncomeDto(Income income) {
		this.descricao = income.getDescricao();
		this.valor = income.getValor();
		this.data = income.getData();
	}

	public static List<IncomeDto> parse(List<Income> incomeList) {

		return incomeList.stream()
				.map(income -> new IncomeDto(income))
				.collect(Collectors.toList());

	}
	
	public static  IncomeDto parse(Income income) {
		
		return new IncomeDto(income);
		
	}
}
