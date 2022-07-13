package com.igh.crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igh.crud.model.Cancion;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Integer>{

	Optional<Cancion> findByNombreCancion(String nombreCancion);
}
