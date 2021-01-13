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
	int idSanitario;
	@JoinColumn (name = "idTrabajador")
	@OneToOne
	Trabajador trabajador;
	@JoinColumn (name = "idEspecialidad")
	@ManyToOne
	Especialidad especialidad;
	@JoinColumn (name = "idTipo")
	@OneToOne
	TipoSanitario tipo;
	
	public Sanitario () {}
	
	public Sanitario(Trabajador trabajador, Especialidad especialidad, TipoSanitario tipo){
		
		this.trabajador = trabajador;
		this.especialidad = especialidad;
		this.tipo = tipo;
	}
	
	public Trabajador getTrabajador(){
		return trabajador;
	}
	
	public Especialidad getEspecialidad(){
		return especialidad;
	}
};