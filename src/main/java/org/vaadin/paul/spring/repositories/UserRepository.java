package org.vaadin.paul.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findAll();
	User findByusername(String username);
	User findByid(int i);
	User findBydni(String dni);
	
	@Query(value = "SELECT * FROM user WHERE username = ?1 AND NOT id = ?2 ", nativeQuery = true)
	User findByUsernameEdit(String username, int id);
	
	@Query(value = "SELECT * FROM user WHERE dni = ?1 AND NOT id = ?2 ", nativeQuery = true)
	User findByDniEdit(String dni, int id);
	
	@Query(value = "Select * From User Where rol = 2", nativeQuery = true)
	List<User> findByRoles(int rol);
}
