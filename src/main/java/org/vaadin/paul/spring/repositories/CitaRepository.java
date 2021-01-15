package org.vaadin.paul.spring.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Sanitario;
import org.vaadin.paul.spring.entities.User;

public interface CitaRepository extends JpaRepository<Cita, Integer>{
	List<Cita> findAll();
	Cita findByid(Integer id);
	//Optional<Cita> findById(Integer Id);
	
	List<Cita>findByImporteAndConfirmada(float importe, boolean confirmada);
	List<Cita> findByPaciente(User paciente);
	List<Cita>findByFechaAndSanitarioAndConfirmada(LocalDate fechaCita, Sanitario sanitario, boolean confirmada);
	List<Cita> findBySanitario(Sanitario findById);
	List<Cita> findBySanitarioAndPaciente(Sanitario sanitario, User paciente);
}
