package com.igh.crud.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class JwtResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	

	private  String jwttoken;
	
	private  Collection<? extends GrantedAuthority> role;
	
	private Date tokenexpired;
	
	private  String username;


}
