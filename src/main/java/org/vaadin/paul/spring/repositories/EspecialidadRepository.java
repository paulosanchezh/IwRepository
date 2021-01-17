package org.vaadin.paul.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vaadin.paul.spring.entities.Especialidad;

import java.util.List;

import org.springframework.beans.factory.BeanCreationException;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer>{
	List<Especialidad> findAll();
	
	@Query(value = "SELECT e.* FROM especialidad e WHERE NOT EXISTS ( SELECT c.especialidades_id FROM centro_especialidades c WHERE e.id = c.especialidades_id )" , nativeQuery = true)
	List<Especialidad> findByEspecialidadSinCentro();
	
}
