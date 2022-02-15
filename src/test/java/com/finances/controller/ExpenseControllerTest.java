package com.finances.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void shouldReturn400IfJsonPostSsInvalid() throws Exception {
		URI uri = new URI("/expense");
		String json = "{\"descricao\":\"\", \"valor\": \"500.45\", \"category\": \"MORADIA\"}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(400));
			
	}

	@Test
	void shouldReturn400IfPutGoesWrong() throws Exception {
		URI uri = new URI("/expense/1");
		String json = "{\"descricao\":\"\", \"valor\": \"500.45\", \"category\": \"MORADIA\"}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.put(uri)
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(400));
	}
	
	@Test
	void shouldReturnAllExpenses() throws Exception {
		URI uri = new URI("/expense");
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(uri)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(200));
	}
	

}
