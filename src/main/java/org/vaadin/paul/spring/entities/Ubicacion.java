package org.vaadin.paul.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Ubicacion {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	int id;
	@JoinColumn(name = "idCalle")
	@OneToOne
	Calle calle;
	@JoinColumn(name = "idLocalidad")
	@OneToOne
	Localidad localidad;
	@OneToOne
	@JoinColumn(name = "idProvincia")
	Provincia provincia;
	@OneToOne
	@JoinColumn(name = "idComunidad")
	Comunidad comunidad;
	
	public Ubicacion () {}
	
	public int getId() {
		return id;
	}
	
	public Calle getCalle() {
		return calle;
	}
	
	public void setCalle(Calle calle) {
		this.calle = calle;
	}
	
	public Localidad getLocalidad() {
		return localidad;
	}
	
	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
	
	public Provincia getProvincia() {
		return provincia;
	}
	
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	
	public Comunidad getComunidad() {
		return comunidad;
	}
	
	public void setComunidad(Comunidad comunidad) {
		this.comunidad = comunidad;
	}
	
}
