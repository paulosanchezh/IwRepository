package org.vaadin.paul.spring.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;



@Entity
public class Trabajador{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name = "id")
	private int id;
	@JoinColumn (name = "idUser")
	@OneToOne
	private User user;
	@Column (name = "horario", length = 256)
    private String horario;
	@Column (name = "salarioBruto")
    private float salario;
	@Column (name = "fechaContrato")
    private LocalDate fechaContrato;
	
	@JoinColumn (name = "idCentro")
	@OneToOne
	private Centro centro;
	
	private LocalTime horaInicio;
	
	private LocalTime horaFinal;
	
	public Trabajador () {}

	public Trabajador(User usuario, String horario, float salario, LocalDate fechaContrato){
        
		this.user = usuario;
        this.horario = horario;
        this.salario = salario;
        this.fechaContrato = fechaContrato;
     
    }
	
    public String getHorario() {
		return this.horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

    public float getSalario() {
		return this.salario;
	}

	public void setSalario(float salario) {
		this.salario = salario;
	}
    
	public LocalDate getFechaContrato() {
		return this.fechaContrato;
	}

    public void setFechaContrato(LocalDate fechaContrato) {
		this.fechaContrato = fechaContrato;
    }
    
    public User getusuario() {
    	return user;
    }
    
    public LocalTime getHoraInicio() {
		return horaInicio;
	}
    
    public void setHoraInicio(LocalTime horaInicio) {
    	this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFinal() {
    	return horaFinal;
    }
    
	public void setHoraFinal(LocalTime horaFinal) {
		this.horaFinal = horaFinal;
	}
	
	public Centro getCentro() {
		return centro;
	}
	
    
};