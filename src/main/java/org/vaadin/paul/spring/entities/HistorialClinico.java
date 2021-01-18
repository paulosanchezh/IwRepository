package org.vaadin.paul.spring.entities;

import java.util.List;

import javax.persistence.CascadeType;
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
	private int id;
	
	@JoinColumn(name = "idPaciente")
	@OneToOne(orphanRemoval = true)
	private User paciente;
	
	@OneToMany(targetEntity=Cita.class)
	private List<Cita> citas;
	
	@OneToMany(targetEntity=Informe.class)
	private List<Informe> informes;

	@Column(name = "informacionImportante")
	private String InformacionImportante;
	
	public HistorialClinico () {}
}
