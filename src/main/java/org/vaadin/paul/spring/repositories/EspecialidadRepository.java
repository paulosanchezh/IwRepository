package org.vaadin.paul.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.paul.spring.entities.Especialidad;

import java.util.List;

import org.springframework.beans.factory.BeanCreationException;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer>{
	List<Especialidad> findAll();
}
