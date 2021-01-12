package org.vaadin.paul.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TipoSanitario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name = "id")
	int id;
	@Column (name = "nombre", length = 128)
	String nombre;
	
	public TipoSanitario () {}
	
	public int getId() {
		return id;
	}
	
	public String getTipo() {
		return nombre;
	}
	
	public void setTipo(String nombre) {
		this.nombre = nombre;
	}
}
