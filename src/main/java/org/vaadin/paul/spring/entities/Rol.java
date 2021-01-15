package org.vaadin.paul.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rol {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name = "id")
	int id;
	@Column (name = "nombre")
	String nombre;
	
	public Rol () {}
	
	public Rol(int Id, String nombre) {
		this.id = Id;
		this.nombre = nombre;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre_) {
		this.nombre = nombre_;
	}
}
