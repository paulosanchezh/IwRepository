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
	private int id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "telefono")
	private String telefono;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(targetEntity=Especialidad.class)
	private List<Especialidad> especialidades;
	
//	@JoinColumn(name = "idEspecialidad")
//	@OneToOne
//	Especialidad especialidad;
	
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(targetEntity=User.class)
	private List<User> usuarios;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(targetEntity=Trabajador.class)
	private List<Trabajador> trabajadores;
	
	@JoinColumn(name = "idLocalidad")
	@OneToOne
	private Localidad localidad;
	
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
