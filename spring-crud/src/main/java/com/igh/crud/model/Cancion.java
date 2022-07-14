package com.igh.crud.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cancion")
@SequenceGenerator(name = "sq_cancion",sequenceName = "sq_cancion",allocationSize = 1)
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
	
	@Column(name = "nombre_archivo")
	private String nombreArchivo;
	
	@JsonIgnore
	@ManyToOne
	private Usuario usuario;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "canciones")
	private Set<PlayList> playList;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cancion other = (Cancion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
}
