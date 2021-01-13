package org.vaadin.paul.spring.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Centro {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	int id;
	@Column(name = "nombre")
	String nombre;
	@Column(name = "telefono")
	String telefono;
	
	@OneToMany(targetEntity=Especialidad.class)
	List<Especialidad> especialidades;
	
//	@JoinColumn(name = "idEspecialidad")
//	@OneToOne
//	Especialidad especialidad;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(targetEntity=User.class)
	List<User> usuarios;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(targetEntity=Trabajador.class)
	List<Trabajador> trabajadores;
	
	@JoinColumn(name = "idLocalidad")
	@OneToOne
	Localidad localidad;
	
	public Centro () {}
	
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public List<Especialidad> getEspecialidad() {
		return especialidades;
	}
	
	public void setEspecialidad(List<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}
	
	public List<User> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}
	
	public List<Trabajador> getTrabajadores() {
		return trabajadores;
	}
	
	public void setTrabajadores(List<Trabajador> trabajadores) {
		this.trabajadores = trabajadores;
	}
	
	public Localidad getUbicacion() {
		return localidad;
	}
	
	public void setUbicacion(Localidad ubicacion) {
		this.localidad = ubicacion;
	}
	
}
