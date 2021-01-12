package org.vaadin.paul.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.paul.spring.entities.Sanitario;
import org.springframework.beans.factory.BeanCreationException;

public interface SanitarioRepository extends JpaRepository<Sanitario, Integer>{
	
	Sanitario findById(int id);

}

