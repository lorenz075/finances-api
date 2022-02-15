package com.finances.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.finances.model.Income;

@Repository
public interface IncomeRespository extends JpaRepository<Income, Integer>{
	
	public Optional<Income> findByDescricao(String descricao);
	
	public List<Income>  findByDescricaoContaining(String descricao);
	
	@Query("select i from Income i where year(i.data) = ?1 and month(i.data) = ?2")
	public List<Income> findByMes(int ano, int mes);
}
