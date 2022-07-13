package com.igh.crud.service;

import java.util.Map;

import org.springframework.util.MultiValueMap;

import com.igh.crud.dto.JwtResponse;
import com.igh.crud.model.Usuario;

public interface UsuarioService {

	public int insertar(Usuario usuario);

	public Map<String, Object> getUsuarios(int page, int size);

	public int deleteById(int id);

	public boolean findById(int id);

	public int update(Usuario usuario);
	
	public JwtResponse processLogin(MultiValueMap<String,String> paramMap);
	
	public Usuario getUserSession();

}
