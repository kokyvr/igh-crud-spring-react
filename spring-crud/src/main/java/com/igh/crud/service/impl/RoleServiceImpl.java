package com.igh.crud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igh.crud.model.Role;
import com.igh.crud.repository.RoleRepository;
import com.igh.crud.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleDao;
	
	@Override
	public Role findByRoleName(String roleName) {
		return roleDao.findByRoleName(roleName).orElse(null);
	}

}
