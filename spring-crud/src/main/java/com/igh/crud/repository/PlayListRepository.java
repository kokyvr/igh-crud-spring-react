package com.igh.crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igh.crud.model.PlayList;
import com.igh.crud.model.Usuario;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Integer>{

	List<PlayList> findByUsuario(Usuario usuario);
	
	Page<PlayList> findByUsuarioAndEstadoPlayList(Pageable pageable,Usuario usuario,String estadoPlayList);
	
	Optional<PlayList> findByIdAndEstadoPlayList(Integer id,String estadoPlayList);
	
}
