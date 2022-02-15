package com.finances.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String descricao;
	private BigDecimal valor;
	private LocalDateTime data = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	private ExpenseCategory category;
	
	public Expense(String descricao, BigDecimal valor, ExpenseCategory category) {
		this.descricao = descricao;
		this.valor = valor;
		this.category = category;
	}

	
	
	
}
