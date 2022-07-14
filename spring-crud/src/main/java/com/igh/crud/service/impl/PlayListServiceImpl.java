package com.igh.crud.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.igh.crud.model.Estado;
import com.igh.crud.model.PlayList;
import com.igh.crud.model.Usuario;
import com.igh.crud.repository.PlayListRepository;
import com.igh.crud.service.PageableMapper;
import com.igh.crud.service.PlayListService;
import com.igh.crud.service.UsuarioService;

@Service
public class PlayListServiceImpl implements PlayListService,PageableMapper<PlayList>{

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PlayListRepository playListDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int insertar(PlayList playList) {
		int rpta = 0;
		try {
			Usuario usuario = usuarioService.getUserSession();
			if(usuario == null) {
				return rpta;
			}
			playList.setUsuario(usuario);
			playList.setFechaCreada(new Date());
			playList.setEstadoPlayList(Estado.ACTIVO.toString());
			playListDao.save(playList);
			rpta = 1;
			
		} catch (Exception e) {
			rpta = 0;
			System.out.println(e.getMessage());
		}
		return rpta;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int update(PlayList playList, Integer id) {
		int rpta = 0;
		try {
			PlayList playBD = playListDao.findById(id).orElse(null);
			System.out.println(playBD);
			if(playBD ==null) {
				return rpta;
			}
			if(playBD.getEstadoPlayList().equals(Estado.BORRADO.toString())) {
				return rpta;
			}
			
			Usuario usuarioBD = usuarioService.getUserSession();
			
			if(!usuarioBD.getId().equals(playBD.getUsuario().getId())) {
				return rpta;
			}
			playList.setFechaCreada(new Date());
			playList.setId(playBD.getId());
			playList.setUsuario(playBD.getUsuario());
			playList.setEstadoPlayList(playBD.getEstadoPlayList());
			
			playListDao.save(playList);
			rpta = 1;
			
			
			
		} catch (Exception e) {
			rpta = 0;
			System.out.println(e.getCause());
		}
		return rpta;
	}
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int deleteById(Integer id) {
		int rpta = 0;
		try {
			PlayList playListBD = playListDao.findById(id).orElse(null);
			if(playListBD == null) {
				return rpta;
			}
			Usuario usuarioBD = usuarioService.getUserSession();
			
			if(!usuarioBD.getId().equals(playListBD.getUsuario().getId())) {
				return rpta;
			}
			
			playListBD.setEstadoPlayList(Estado.BORRADO.toString());
			playListDao.save(playListBD);
			rpta = 1;
			
		} catch (Exception e) {
			rpta = 0;
			System.out.println(e.getMessage());
		}
		return rpta;
	}

	@Override
	public Map<String, Object> mapperPageable(Page<PlayList> mapper) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("playList", mapper.getContent());
		map.put("totalElements", mapper.getTotalElements());
		map.put("totalPages", mapper.getTotalPages());
		map.put("currentPage", mapper.getNumber());
		
		return map;
	}

	@Override
	public Map<String, Object> getPlayList(int page, int size) {
		Map<String, Object> map = null;
		try {
			PageRequest pageable = PageRequest.of(page, size);
			Usuario usuario = usuarioService.getUserSession();
			
			Page<PlayList> pageablePlayList = playListDao.findByUsuarioAndEstadoPlayList(pageable, usuario, Estado.ACTIVO.toString());
			map = mapperPageable(pageablePlayList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return map;
	}

	@Override
	public PlayList findById(Integer id) {
		// TODO Auto-generated method stub
		PlayList playList = null;
		try {
			playList = playListDao.findByIdAndEstadoPlayList(id,Estado.ACTIVO.toString()).orElse(null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return playList;
	}

}
