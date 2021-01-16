package org.vaadin.paul.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Sanitario{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name = "id")
	private int idSanitario;
	@JoinColumn (name = "idTrabajador")
	@OneToOne
	private Trabajador trabajador;
	@JoinColumn (name = "idEspecialidad")
	@ManyToOne
	private Especialidad especialidad;
	@JoinColumn (name = "idTipo")
	@OneToOne
	private TipoSanitario tipo;
	
	public Sanitario () {}
	
	public Sanitario(Trabajador trabajador, Especialidad especialidad, TipoSanitario tipo){
		
		this.trabajador = trabajador;
		this.especialidad = especialidad;
		this.tipo = tipo;
	}
	
	public Trabajador getTrabajador(){
		return trabajador;
	}
	
	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}
	
	public Especialidad getEspecialidad(){
		return especialidad;
	}
	
	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}
};