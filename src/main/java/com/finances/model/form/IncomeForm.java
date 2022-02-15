package com.finances.model.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.finances.model.Income;
import com.finances.model.repository.IncomeRespository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class IncomeForm {
	
	@NotNull @NotEmpty
	private String descricao;
	@NotNull @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 10, fraction = 2)
	private BigDecimal valor;
	
	
	
	
	public Income parseAndValidate(IncomeRespository incomeRespository) {
		
		Optional<Income> optional =  incomeRespository.findByDescricao(descricao);
		LocalDateTime now = LocalDateTime.now();
		
		if(optional.isPresent()) {
			Income income = optional.get();
			Integer year = income.getData().getYear();
			Integer month = income.getData().getMonthValue();
			if(year.equals(now.getYear()) && month.equals(now.getMonthValue())) {
				return null;
			}
		}
		
		return new Income(descricao, valor);
	}
	
	public Income atualiza(Income income) {
		income.setDescricao(this.descricao);
		income.setValor(this.valor);
		return income;
	}

}
