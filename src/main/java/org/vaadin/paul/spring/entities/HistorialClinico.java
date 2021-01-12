package org.vaadin.paul.spring.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class HistorialClinico {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	int id;
	
	@JoinColumn(name = "idPaciente")
	@OneToOne
	User paciente;
	
	@OneToMany(targetEntity=Cita.class)
	List<Cita> citas;
	
	@OneToMany(targetEntity=Informe.class)
	List<Informe> informes;

	@Column(name = "informacionImportante")
	String InformacionImportante;
	
	public HistorialClinico () {}
}
