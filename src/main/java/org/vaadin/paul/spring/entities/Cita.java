package org.vaadin.paul.spring.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Cita {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name = "id")
	int id;
	@JoinColumn (name = "idPaciente")
	@ManyToOne
	User paciente;
	@JoinColumn (name = "idTrabajador")
	@ManyToOne
	Sanitario sanitario;
	@JoinColumn (name = "idInforme")
	@OneToOne
	Informe informe;
	@Column (name = "fecha")
	LocalDate fechaCita;
	// hora incluida en fecha?
	@Column (name = "importe")
	float importe;
	@Column(name = "Confirmada")
	boolean confirmada;
	
	public Cita() {}
	
	public Cita(int id, User paciente, Sanitario sanitario, Informe informe, LocalDate fechaCita,
			float importe) {
		this.id = id;
		this.paciente = paciente;
		this.sanitario = sanitario;
		this.informe = informe;
		this.fechaCita = fechaCita;
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
	
	public LocalDate getFechaCita() {
		return fechaCita;
	}
	
	public void setFechaCita(LocalDate fechaCita) {
		this.fechaCita = fechaCita;
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
}
