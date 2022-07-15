package com.igh.crud.config;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.igh.crud.model.Estado;
import com.igh.crud.model.Role;
import com.igh.crud.model.Usuario;
import com.igh.crud.repository.RoleRepository;
import com.igh.crud.repository.UsuarioRepository;

@Component
public class Loader {
	
	@Autowired
	private RoleRepository roleService;
	
	@Autowired
	private UsuarioRepository usuarioDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final String ROLE_USER="ROLE_USER";
	
	private static final String ROLE_ADMINISTRADOR="ROLE_ADMINISTRADOR";
	
	private static final String ROLE_MODERADOR = "ROLE_MODERADOR";

	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		System.out.println("Ready");
		Role roleUser = createRoleIfNotFund(ROLE_USER);
		Role roleAdministrador = createRoleIfNotFund(ROLE_ADMINISTRADOR);
		Role roleModerador = createRoleIfNotFund(ROLE_MODERADOR);
		
		Set<Role> roles = new HashSet<>();
		
		roles.add(roleModerador);
		roles.add(roleUser);
		roles.add(roleAdministrador);
		createUserIfNotFound("venturakoky12@gmail.com",roles);
		
	}
	@Transactional
	private final Usuario createUserIfNotFound(final String email, Set<Role> roles) {
		Usuario user = usuarioDao.findByEmail(email).orElse(null);
		if (user == null) {
			user = new Usuario();
			user.setUsuario("kokyventura");
			user.setEmail(email);
			user.setNombre("Administrador");
			user.setApellido("Apellido Administrador");
			user.setPassword(passwordEncoder.encode("admin@"));
			user.setRoles(roles);
			user.setEstado_cuenta(Estado.ACTIVO.toString());
			
			
			user = usuarioDao.save(user);
		}
		return user;
	}
	
	@Transactional
	private Role createRoleIfNotFund(String roleName) {
		Role role = roleService.findByRoleName(roleName).orElse(null);
		
		if(role ==null) {
			
			role =roleService.save(new Role(roleName));
			
		}
		
		
		return role;
	}
}
