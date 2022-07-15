package com.igh.crud.controller;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igh.crud.dto.JwtResponse;
import com.igh.crud.service.UsuarioService;

@RestController
@RequestMapping(path = "auth")
public class AuthController {

	@Autowired
	private UsuarioService usuarioService;
	
	
	@PermitAll
	@PostMapping
	public ResponseEntity<?> processLogin(@RequestBody MultiValueMap<String,String> paramMap){
		JwtResponse jwt;
		
		try {
			jwt = usuarioService.processLogin(paramMap);
		} catch (BadCredentialsException  e) {
			return new ResponseEntity<>("Credenciales Incorrectas.",HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(jwt);
	}
}
