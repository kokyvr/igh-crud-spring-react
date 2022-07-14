package com.igh.crud.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "play_list")
@SequenceGenerator(name = "sq_playlist",sequenceName = "sq_playlist",allocationSize = 1)
public class PlayList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sq_playlist")
	private Integer id;
	
	@NotEmpty
	@Column(name = "nombre_play_list")
	private String nombrePlayList;
	
	@JoinTable(name = "play_list_canciones",joinColumns = {@JoinColumn(name="playlist_id")},inverseJoinColumns = {@JoinColumn(name="cancion_id")})
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Cancion> canciones;
	
	@JsonIgnore
	@JoinColumn(name = "fk_usuario")
	@ManyToOne(cascade ={CascadeType.REMOVE})
	private Usuario usuario;
	
	@Column(name = "fecha_creada")
	private Date fechaCreada;
	
	@Column(name = "estado_play_list")
	private String estadoPlayList;
	
}
