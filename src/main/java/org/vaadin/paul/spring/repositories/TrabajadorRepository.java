package org.vaadin.paul.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.paul.spring.entities.Trabajador;
import org.vaadin.paul.spring.entities.User;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer>{
	
	Trabajador findByUser(User usuario);

}
