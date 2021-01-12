package org.vaadin.paul.spring.ui.views;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.app.security.SecurityUtils;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.UserRepository;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;

@Route("user")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class UserView extends FormLayout {
	UserRepository repo;
	Grid<User> grid;
	H1 texto;
	Button button;
	User user;
	
	public UserView(UserRepository repo) {
		this.user = (User) SecurityUtils.getAuthenticatedUser();
		this.repo = repo;
		this.grid = new Grid<>(User.class);
		this.grid.setItems(repo.findByid(this.user.getId()));
		add(grid);
		this.texto = new H1();
		this.button = new Button();
		
		
		
		if(SecurityUtils.isUserLoggedIn()) {
			texto.setText("Estoy conectado");
		}
		else {
			texto.setText("No estoy conectado");
		}
		add(texto);
		
		
//		if (SecurityContextHolderAwareRequestWrapper.isUserInRole("ROLE_ADMIN")) {
//			button.setVisible(true);
//		}
//		else {
//			button.setVisible(false);
//		}
		
		
	}
	
	void listProvincia(int id) {
		grid.setItems(repo.findByid(id));
	}
	
}
