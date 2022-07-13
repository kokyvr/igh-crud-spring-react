package com.igh.crud.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.igh.crud.dto.JwtResponse;
import com.igh.crud.model.Role;
import com.igh.crud.model.Usuario;
import com.igh.crud.repository.UsuarioRepository;
import com.igh.crud.security.JwtUtil;
import com.igh.crud.service.MailService;
import com.igh.crud.service.PageableMapper;
import com.igh.crud.service.RoleService;
import com.igh.crud.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService,
	UserDetailsService,
	PageableMapper<Usuario> {

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Lazy
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioRepository usuarioDao;

	@Autowired
	private RoleService serviceRole;

	private static final String ROLE_USER = "ROLE_USER";
	
	private static final String ESTADO_USUARIO_ACTIVO = "ACTIVO";
	
	private static final String ESTADO_USUARIO_DESACTIVADO = "DESACTIVADO";

	private static final String ROLE_ADMINISTRADOR = "ROLE_ADMINISTRADOR";

	private static final String TITULO_ACTIVAR_CUENTA="";

	private String CORREO_ELECTRONICO;
	
	@Autowired
	private MailService mailService;
	
	private String HTML_CONFIRMATION_EMAIL;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsuario(username).orElse(null);
		if(usuario == null) {
			 throw new UsernameNotFoundException(String.format("El usuario %s no existe",username));
		}
		
		if(usuario == null || usuario.getEstado_cuenta().equals(ESTADO_USUARIO_DESACTIVADO)) {
			 throw new RuntimeException(String.format("El usuario no ha sido Verificado.",username));
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		usuario.getRoles().forEach(role->{
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		});
		
		UserDetails userDetail =new User(usuario.getUsuario(),usuario.getPassword(),authorities);
		
		return userDetail;
	}
	

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int insertar(Usuario usuario) {
		int rpta = 0;
		try {
			Usuario usuarioDB = usuarioDao.findByUsuario(usuario.getUsuario()).orElse(null);
			Role role = serviceRole.findByRoleName(ROLE_USER);
			if (usuarioDB != null || role == null) {
				rpta = 0;
				return rpta;
			} else {
			usuario.setRoles(Set.of(role));
			usuario.setEstado_cuenta(ESTADO_USUARIO_DESACTIVADO);
			String passwordEncode = passwordEncoder.encode(usuario.getPassword());
			usuario.setPassword(passwordEncode);
			usuarioDao.save(usuario);
			
			/*Mail mail = new Mail();
			mail.setHtml(HTML_CONFIRMATION_EMAIL);
			mail.setTo(usuario.getEmail());
			mail.setFrom(CORREO_ELECTRONICO);
			mail.setSubject(TITULO_ACTIVAR_CUENTA);
			Map<String,Object> model = new HashMap<>();
			model.put("user",usuario.getUsuario());
			mail.setModel(model);
			mailService.SendEmail(mail);*/
			rpta = 1;
			}

		} catch (Exception e) {
			rpta=0;
		}
		return rpta;

	}

	@Override
	public Map<String, Object> getUsuarios(int page, int size) {
		
		
		PageRequest pageable = PageRequest.of(page, size);
		Page<Usuario> usuariosPage = usuarioDao.findAll(pageable);

		Map<String, Object> mapUser = mapperPageable(usuariosPage);
		

		return mapUser;
	}

	@Override
	public int deleteById(int id) {
		int rpta = 0;
		try {
			usuarioDao.deleteById(id);
			rpta = 1;
		} catch (Exception e) {
			rpta = 0;
		}
		return rpta;
	}

	@Override
	public boolean findById(int id) {

		return usuarioDao.existsById(id);
	}

	@Override
	public int update(Usuario usuario) {
		int rpta = 0;

		if (!usuarioDao.existsById(usuario.getId())) {
			rpta = 0;
			return rpta;
		}

		try {

			usuarioDao.save(usuario);
			rpta = 1;
		} catch (Exception e) {
			// TODO: handle exception
			rpta = 0;
		}

		return rpta;
	}


	@Override
	public JwtResponse processLogin(MultiValueMap<String, String> paramMap) {
		JwtResponse jwt = new JwtResponse();
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(paramMap.getFirst("username"), paramMap.getFirst("password")));
		SecurityContextHolder.getContext().setAuthentication(authentication);;
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String token = jwtTokenUtil.generateToken(userDetails);
		Date tokenexpiredDate = jwtTokenUtil.getExpirationDateFromToken(token);
		
	
		
		
		jwt.setJwttoken(token);
		jwt.setTokenexpired(tokenexpiredDate);
		jwt.setRole(userDetails.getAuthorities());
		jwt.setUsername(userDetails.getUsername());
		
		return jwt;
	}


	@Override
	public Map<String, Object> mapperPageable(Page<Usuario> mapper) {
		List<Usuario> usuarios =  mapper.getContent();
		Map<String, Object> mapUser = new HashMap<>();
		usuarios.stream().forEach(u->u.setPassword("**************"));
		
		mapUser.put("usuarios", usuarios);
		mapUser.put("totalElements", mapper.getTotalElements());
		mapUser.put("totalPages", mapper.getTotalPages());
		mapUser.put("currentPage", mapper.getNumber());
		return mapUser;
	}


	@Override
	public Usuario getUserSession() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getName().equals("anonymousUser")) {
			return	 null;
			}
		Optional<Usuario>optional=	usuarioDao.findByUsuario(auth.getName());
		
		return optional.orElse(null);
		
	}



}
