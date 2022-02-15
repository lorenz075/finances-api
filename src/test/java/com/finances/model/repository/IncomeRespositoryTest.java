package com.finances.model.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.finances.model.Income;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IncomeRespositoryTest {

	@Autowired
	private IncomeRespository incomeRespository;
	
	@Test
	public void checkForDescriptionTest() {
		Income income = Income.builder()
				.descricao("Salario")
				.valor(new BigDecimal("500.00"))
				.data(LocalDateTime.now())
				.id(1)
				.build();
		
		incomeRespository.save(income);
		
		Optional<Income> trueArg = incomeRespository.findByDescricao(income.getDescricao());
		Optional<Income> falseArg = incomeRespository.findByDescricao("INVALIDO");
		
		Assertions.assertThat(trueArg.get()).isNotNull();
		Assertions.assertThat(falseArg).isEmpty();
	}

}
