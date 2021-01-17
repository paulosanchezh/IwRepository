package org.vaadin.paul.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vaadin.paul.spring.entities.Trabajador;
import org.vaadin.paul.spring.entities.User;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer>{
	
	Trabajador findByUser(User usuario);
	Trabajador findByuser(User usuario);
	
	@Query(value = "SELECT * FROM trabajador WHERE id_centro IS NULL", nativeQuery = true)
	List<Trabajador> findByTrabajadoresNulos();
}
