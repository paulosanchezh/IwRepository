package org.vaadin.paul.spring.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Cita {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name = "id")
	private int id;
	
	@JoinColumn (name = "idPaciente")
	@ManyToOne
	private User paciente;
	
	@JoinColumn (name = "idSanitario")
	@ManyToOne
	private Sanitario sanitario;
	
	@JoinColumn (name = "idInforme")
	@OneToOne
	private Informe informe;
	
	@Column (name = "fecha")
	private LocalDate fecha;
	
	
	private LocalTime hora;
	
	@Column (name = "importe")
	private float importe;
	
	@Column(name = "Confirmada")
	private boolean confirmada;
	
	public Cita() {}
	
	public Cita(int id, User paciente, Sanitario sanitario, Informe informe, LocalDate fecha, LocalTime hora,
			float importe) {
		this.id = id;
		this.paciente = paciente;
		this.sanitario = sanitario;
		this.informe = informe;
		this.fecha = fecha;
		this.hora = hora;
		this.importe = importe;
		this.confirmada = false;
	}

	public int getId() {
		return id;
	}
	
	public User getPaciente() {
		return paciente;
	}
	
	public void setPaciente(User paciente) {
		this.paciente = paciente;
	}
	
	public Sanitario getSanitario() {
		return sanitario;
	}
	
	public void setSanitario(Sanitario sanitario) {
		this.sanitario = sanitario;
	}
	
	public Informe getInforme() {
		return informe;
	}
	
	public void setInforme(Informe informe) {
		this.informe = informe;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDate fechaCita) {
		this.fecha = fechaCita;
	}
	
	public float getImporte() {
		return importe;
	}
	
	public void setImporte(float importe) {
		this.importe = importe;
	}
	
	public String getNombreyApellidospaciente() {
		return paciente.getNombreyApellidos();
	}
	
	public String getNombreyApellidosSanitario() {
		return sanitario.getTrabajador().getusuario().getNombreyApellidos();
	}
	
	public boolean getConfirmada() {
		return confirmada;
	}
	
	public void setConfirmada(boolean confirmada) {
		this.confirmada = confirmada;
	}
	
	public LocalTime getHora() { 
		return hora;
	}
	
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	
	public String getConfirmadaString() {
		if (confirmada==true)
			return "Sí";
		else 
			return "No";
	}
}
