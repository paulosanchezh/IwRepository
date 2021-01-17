package org.vaadin.paul.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.paul.spring.entities.Sanitario;
import org.vaadin.paul.spring.entities.Trabajador;
import org.springframework.beans.factory.BeanCreationException;

@Repository
public interface SanitarioRepository extends JpaRepository<Sanitario, Integer>{
	
	Sanitario findById(int id);
	Sanitario findByTrabajador(Trabajador trabajador);

}

