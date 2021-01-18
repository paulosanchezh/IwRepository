package org.vaadin.paul.spring.ui.views;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.Especialidad;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.UserRepository;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "cruduser", layout = MainView.class)
@PageTitle("Gestión de Usuarios")
@Secured({"ROLE_ADMIN"})

public class crudUser extends VerticalLayout{
	
	private UserRepository repoUser;
	
	private Grid<User> gUser;
	
	public crudUser(UserRepository repoUser) {
		gUser = new Grid<>();
		gUser.addColumn(User::getNombre, "Nombre" + " ").setHeader("Nombre");
		gUser.addColumn(User::getApellidos, "Apellidos" + " ").setHeader("Apellidos");
		gUser.addColumn(User::getDireccion, "Dirección" + " ").setHeader("Dirección");
		gUser.addColumn(User::getDni, "DNI" + " ").setHeader("DNI");
		gUser.addColumn(User::getTlf, "Teléfono" + " ").setHeader("Teléfono");
		gUser.addColumn(User::getUsername, "Username" + " ").setHeader("Username");
		gUser.addColumn(User::getPassword, "Contraseña" + " ").setHeader("Contraseña");
		
		gUser.addColumn(new ComponentRenderer<>(especialidad -> {
			Button deleteButton = new Button("Eliminar"); 
			deleteButton.addClickListener(delete -> { 
				
				gUser.setItems(repoUser.findAll());
		 	}); 
			return deleteButton;	
		}));
		Button addUser = new Button("Añadir Usuario");
		addUser.addClickListener(delete -> { 
			
	 	}); 
		add(addUser);
		add(gUser);
	}
}
