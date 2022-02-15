package com.finances.model.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginForm {

	private String senha;
	private String email;

	public UsernamePasswordAuthenticationToken converter() {
		
		return new UsernamePasswordAuthenticationToken(email, senha);
	}

}
