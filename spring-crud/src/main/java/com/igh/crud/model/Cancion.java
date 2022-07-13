package com.igh.crud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "cancion")
@SequenceGenerator(name = "sq_cancion",sequenceName = "sq_role",allocationSize = 1)
public class Cancion implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sq_cancion")
	private Integer id;

	@NotEmpty
	@Column(name = "nombre_cancion")
	private String nombreCancion;
	
	@NotEmpty
	@Column(name = "cantante")
	private String cantante;
	
	@ManyToOne
	private Usuario usuario;
}
