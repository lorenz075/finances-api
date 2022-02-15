package com.finances.model.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.finances.model.Expense;
import com.finances.model.ExpenseCategory;
import com.finances.model.repository.ExpenseRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ExpenseForm {
	
	@NotNull @NotEmpty
	private String descricao;
	@NotNull @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 10, fraction = 2)
	private BigDecimal valor;
	private ExpenseCategory category;
	
	
	
	
	public Expense parseAndValidate(ExpenseRepository expenseRespository) {
		
		Optional<Expense> optional =  expenseRespository.findByDescricao(descricao);
		LocalDateTime now = LocalDateTime.now();
		
		if(optional.isPresent()) {
			Expense expense = optional.get();
			Integer year = expense.getData().getYear();
			Integer month = expense.getData().getMonthValue();
			if(year.equals(now.getYear()) && month.equals(now.getMonthValue())) {
				return null;
			}
		}
		
		if(category == null) {
			
			return new Expense(descricao, valor, ExpenseCategory.OUTRAS);
		}
		
		return new Expense(descricao, valor, getCategory());
	}
	
	public Expense atualiza(Expense expense) {
		expense.setDescricao(this.descricao);
		expense.setValor(this.valor);
		return expense;
	}

}

