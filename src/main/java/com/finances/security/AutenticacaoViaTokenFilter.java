package com.finances.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.finances.model.Usuario;
import com.finances.model.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	private TokenService service;
	private UsuarioRepository repository;
	
	public AutenticacaoViaTokenFilter(TokenService service, UsuarioRepository usuarioRepository) {
		this.service = service;
		this.repository = usuarioRepository;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		boolean valido = service.isTokenValido(token);
		if(valido) {
			autenticarCliente(token);
		}
		filterChain.doFilter(request, response);
	}

	private void autenticarCliente(String token) {
		Long idUsuario = service.getIdUsuario(token);
		Optional<Usuario> optional = repository.findById(idUsuario);
		if(optional.isPresent()) {
			Usuario usuario = optional.get();
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token ==  null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}
	
	

}
