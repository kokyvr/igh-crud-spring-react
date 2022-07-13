package com.igh.crud.service;

import java.util.Map;

import com.igh.crud.model.Cancion;

public interface CancionService {

	public int insertar(Cancion cancion);
	
	public Cancion findByNombreCancion(String cancion);
	
	public int deleteById(Integer id);
	
	public Map<String, Object> getCanciones(int page, int size);
	
}
