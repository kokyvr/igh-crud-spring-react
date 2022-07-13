package com.igh.crud.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igh.crud.model.Usuario;
import com.igh.crud.service.UsuarioService;

@RestController
@RequestMapping(path = "usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@PostMapping
	public ResponseEntity<Integer> insertar(@Valid @RequestBody Usuario usuario) {
		int rpta = 0;
		try {
			rpta = service.insertar(usuario);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<Integer>(rpta,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Integer>(rpta,HttpStatus.OK);


	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/get")
	public ResponseEntity<Map<String, Object>> getUsuariosPageable(@RequestParam int page, @RequestParam int size) {
		Map<String, Object> map;

		try {
			map = service.getUsuarios(page, size);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Integer> deleteById(@PathVariable Integer id) {
		int rpta = 0;
		try {
			rpta = service.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PutMapping
	public ResponseEntity<Integer> update(@Valid @RequestBody Usuario usuario) {
		int rpta = 0;
		rpta = service.update(usuario);
		if (rpta == 1)
			return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
		else
			return new ResponseEntity<Integer>(rpta, HttpStatus.BAD_REQUEST);
	}
	
	
}
