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
	
	@Query(value = "Select * From Cita Where id_paciente = ?1 AND id_informe IS NOT NULL", nativeQuery = true)
	List<Cita> findByPacienteAndInforme(int paciente);
	
	@Query(value = "Select * From Cita Where id_sanitario = ?1 AND id_informe IS NOT NULL", nativeQuery = true)
	List<Cita> findBySanitarioAndInforme(int sanitario);
	
	@Query(value = "SELECT * , max(fecha) FROM cita WHERE id_sanitario = 1 GROUP BY id_paciente", nativeQuery = true)
	List<Cita> findBySanitarioAndUltimacita(int idsanitario);
}
