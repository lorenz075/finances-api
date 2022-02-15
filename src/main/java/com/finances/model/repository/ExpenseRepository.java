package com.finances.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.finances.model.Expense;
import com.finances.model.ExpenseCategory;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer>{
	
	public Optional<Expense> findByDescricao(String descricao);

	public List<Expense> findByDescricaoContaining(String descricao);
	
	@Query("select e from Expense e where year(e.data) = ?1 and month(e.data) = ?2")
	public List<Expense> findByMes(int ano, int mes);
	
	@Query("select e from Expense e where e.category = ?1 and year(e.data) = ?2 and month(e.data) = ?3")
	public List<Expense> findByCategory(ExpenseCategory expense, int ano, int mes);
}
