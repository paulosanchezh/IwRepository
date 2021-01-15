package org.vaadin.paul.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.paul.spring.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer>{
	List<Rol> findAll();
}
