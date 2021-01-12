package org.vaadin.paul.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.paul.spring.entities.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia, Integer>{
	List<Provincia> findAll();
	
	Provincia findById (int id_);
}
