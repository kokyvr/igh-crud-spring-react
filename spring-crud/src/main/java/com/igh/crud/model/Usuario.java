package com.igh.crud.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@SequenceGenerator(name = "sq_usuario", sequenceName = "sq_usuario",
allocationSize = 1)
@Getter
@Setter
@Entity
@Table(name="USUARIO")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_usuario")
	private Integer id;
	
	@Column(unique = true)
	@NotEmpty
	private String usuario;
	@NotEmpty
	private String password;
	@NotEmpty
	private String nombre;
	@NotEmpty
	private String apellido;
	@Column(unique = true)
	@Email
	private String email;
	
	@Column(name = "estado_cuenta")
	private String estado_cuenta;
	
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	@ManyToMany(fetch= FetchType.EAGER)
	private Set<Role> roles;

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", usuario=" + usuario + ", password=" + password + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", email=" + email + ", estado_cuenta=" + estado_cuenta + "]";
	}
	

	
}
