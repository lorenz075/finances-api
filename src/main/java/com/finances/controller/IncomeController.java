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

import com.finances.model.Income;
import com.finances.model.dto.IncomeDto;
import com.finances.model.form.IncomeForm;
import com.finances.model.repository.IncomeRespository;


@RestController
@RequestMapping("/income")
public class IncomeController {

	@Autowired
	IncomeRespository incomeRepository;

	@PostMapping
	public ResponseEntity<IncomeDto> register(@RequestBody @Valid IncomeForm incomeForm,
			UriComponentsBuilder uriBuilder) {
		Income income = incomeForm.parseAndValidate(incomeRepository);

		if (income == null) {
			
			return ResponseEntity.badRequest().build();
		}
		
		
		incomeRepository.save(income);
		IncomeDto incomeDto = new IncomeDto(income);
		URI uri = uriBuilder.path("/income/{id}").buildAndExpand(income.getId()).toUri();
		return ResponseEntity.created(uri).body(incomeDto);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<IncomeDto> update(@PathVariable(name = "id") int id, @RequestBody @Valid IncomeForm incomeForm) {
		
		Optional<Income> findById = incomeRepository.findById(id);
		if(findById.isPresent()) {
			Income incomeValido = incomeForm.parseAndValidate(incomeRepository);
			if(incomeValido != null) {
				Income income = incomeForm.atualiza(findById.get());
				return ResponseEntity.ok(new IncomeDto(income));
			}
		} 
			return ResponseEntity.badRequest().build();
		
	}

	@GetMapping()
	public List<IncomeDto> getAll(String descricao) {
		
		if(descricao == null) {
			List<IncomeDto> incomeDtoList = IncomeDto.parse(incomeRepository.findAll());
			return incomeDtoList;
		}
		
		List<IncomeDto> incomeDtoDescricao = IncomeDto.parse(incomeRepository.findByDescricaoContaining(descricao));
		return incomeDtoDescricao;
	}
	
	@GetMapping("/{id}")
	public IncomeDto getById(@PathVariable(name = "id") int id) {
		Optional<Income> income = incomeRepository.findById(id);
		IncomeDto incomeDto = IncomeDto.parse(income.get());
		return incomeDto;
		
	}
	
	@GetMapping("/{ano}/{mes}")
	public List<IncomeDto> getByMonth(@PathVariable(name="ano") int ano, @PathVariable(name="mes") int mes) {
		List<Income> list = incomeRepository.findByMes(ano, mes);
		List<IncomeDto> listDto = IncomeDto.parse(list);
		return listDto;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") int id) {
		Optional<Income> findById = incomeRepository.findById(id);
		if(findById.isPresent()) {
			incomeRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
