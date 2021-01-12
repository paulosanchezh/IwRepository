package org.vaadin.paul.spring.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.Localidad;

public interface CentroRepository extends JpaRepository<Centro, Integer>{
	
	List<Centro> findByLocalidad(Localidad Localidad);

}
