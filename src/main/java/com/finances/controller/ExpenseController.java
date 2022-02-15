package com.finances.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.finances.model.Expense;
import com.finances.model.dto.ExpenseDto;
import com.finances.model.form.ExpenseForm;
import com.finances.model.repository.ExpenseRepository;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
	
	@Autowired
	ExpenseRepository expenseRepository;

	@PostMapping
	public ResponseEntity<ExpenseDto> register(@RequestBody @Valid ExpenseForm expenseForm,
			UriComponentsBuilder uriBuilder) {
	
		Expense expense = expenseForm.parseAndValidate(expenseRepository);

		if (expense == null) {
			
			return ResponseEntity.badRequest().build();
		}
		
		expenseRepository.save(expense);
		ExpenseDto expenseDto = new ExpenseDto(expense);
		URI uri = uriBuilder.path("/income/{id}").buildAndExpand(expense.getId()).toUri();
		return ResponseEntity.created(uri).body(expenseDto);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ExpenseDto> update(@PathVariable(name = "id") int id, @RequestBody @Valid ExpenseForm expenseForm) {
		
		Optional<Expense> findById = expenseRepository.findById(id);
		if(findById.isPresent()) {
			Expense expenseValido = expenseForm.parseAndValidate(expenseRepository);
			if(expenseValido != null) {
				Expense expense = expenseForm.atualiza(findById.get());
				return ResponseEntity.ok(new ExpenseDto(expense));
			}
		} 
			return ResponseEntity.badRequest().build();
		
	}

	@GetMapping()
	public List<ExpenseDto> getAll(String descricao) {
		
		if(descricao == null) {
			List<ExpenseDto> expenseDtoList = ExpenseDto.parseToDtoList(expenseRepository.findAll());
			return expenseDtoList;
		}
		
		List<ExpenseDto> expenseDtoDescricao = ExpenseDto.parseToDtoList(expenseRepository.findByDescricaoContaining(descricao));
		return expenseDtoDescricao;
	}
	
	@GetMapping("/{id}")
	public ExpenseDto getById(@PathVariable(name = "id") int id) {
		Optional<Expense> expense = expenseRepository.findById(id);
		ExpenseDto expenseDto = ExpenseDto.parseToDto(expense.get());
		return expenseDto;
		
	}
	
	@GetMapping("/{ano}/{mes}")
	public List<ExpenseDto> getByMonth(@PathVariable(name="ano") int ano, @PathVariable(name="mes") int mes) {
		List<Expense> list = expenseRepository.findByMes(ano, mes);
		List<ExpenseDto> listDto = ExpenseDto.parseToDtoList(list);
		return listDto;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") int id) {
		Optional<Expense> findById = expenseRepository.findById(id);
		if(findById.isPresent()) {
			expenseRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
