package com.igh.crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igh.crud.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer >{

	Optional<Role> findByRoleName(String roleName);
}
