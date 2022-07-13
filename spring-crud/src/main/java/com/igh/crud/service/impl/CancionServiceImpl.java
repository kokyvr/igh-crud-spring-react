package com.igh.crud.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.igh.crud.model.Cancion;
import com.igh.crud.model.Usuario;
import com.igh.crud.repository.CancionRepository;
import com.igh.crud.service.CancionService;
import com.igh.crud.service.PageableMapper;
import com.igh.crud.service.UsuarioService;

@Service
public class CancionServiceImpl implements CancionService, PageableMapper<Cancion> {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CancionRepository cancionDao;

	@Override
	public int insertar(Cancion cancion) {
		int rpta = 0;
		try {
			Usuario usuario = usuarioService.getUserSession();
			cancion.setUsuario(usuario);
			cancionDao.save(cancion);
			rpta = 1;
		} catch (Exception e) {
			// TODO: handle exception
			rpta = 0;
		}

		return rpta;
	}

	@Override
	public Cancion findByNombreCancion(String cancion) {
		// TODO Auto-generated method stub
		return cancionDao.findByNombreCancion(cancion).orElse(null);
	}

	@Override
	public Map<String, Object> getCanciones(int page, int size) {
		PageRequest pageable = PageRequest.of(page, size);

		Page<Cancion> cancionesPage = cancionDao.findAll(pageable);

		Map<String, Object> mapCanciones = mapperPageable(cancionesPage);

		return mapCanciones;
	}

	@Override
	public Map<String, Object> mapperPageable(Page<Cancion> mapper) {
		Map<String, Object> mapCanciones = new HashMap<>();
		mapCanciones.put("canciones", mapper.getContent());
		mapCanciones.put("totalElements", mapper.getTotalElements());
		mapCanciones.put("totalPages", mapper.getTotalPages());
		mapCanciones.put("currentPage", mapper.getNumber());
		return mapCanciones;
	}

	@Override
	public int deleteById(Integer id) {
		int rpta = 0;
		try {
			cancionDao.deleteById(id);
			rpta = 1;
		} catch (Exception e) {
			rpta = 0;
		}
		return rpta;
	}

}
