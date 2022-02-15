package com.finances.model.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.finances.model.Expense;
import com.finances.model.ExpenseCategory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
class ExpenseRepositoryTest {

	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Test
	public void saveExpenseTest() {
		Expense expense = Expense.builder()
				.descricao("Aluguel")
				.valor(new BigDecimal("500.00"))
				.data(LocalDateTime.now())
				.category(ExpenseCategory.MORADIA)
				.id(1)
				.build();
		
		expenseRepository.save(expense);
		
		Assertions.assertThat(expense.getId()).isGreaterThan(0);		
	}
	
	@Test
	public void checkForDescriptionTest() {
		Expense expense = Expense.builder()
				.descricao("Aluguel")
				.valor(new BigDecimal("500.00"))
				.data(LocalDateTime.now())
				.category(ExpenseCategory.MORADIA)
				.id(1)
				.build();
		
		expenseRepository.save(expense);
		
		Optional<Expense> trueArg = expenseRepository.findByDescricao(expense.getDescricao());
		Optional<Expense> falseArg = expenseRepository.findByDescricao("INVALIDO");
		
		Assertions.assertThat(trueArg.get()).isNotNull();
		Assertions.assertThat(falseArg).isEmpty();
	}
	
	@Test
	public void testFindExpenseByMes() {
		Expense expense = Expense.builder()
				.descricao("Aluguel")
				.valor(new BigDecimal("500.00"))
				.data(LocalDateTime.now())
				.category(ExpenseCategory.MORADIA)
				.id(1)
				.build();
		
		List<Expense> findByMes = expenseRepository.findByMes(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue());
		Assertions.assertThat(findByMes.size()).isGreaterThan(0);
	}

}
