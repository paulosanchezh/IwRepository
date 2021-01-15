package org.vaadin.paul.spring.ui.views;

import org.vaadin.paul.spring.MainView;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.app.security.SecurityUtils;
import org.vaadin.paul.spring.entities.Provincia;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.ProvinciaRepository;
import org.vaadin.paul.spring.repositories.UserRepository;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;

@Route(value="user")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class UserView extends FormLayout {
	UserRepository repo;
	Grid<User> grid;
	H1 texto;
	Button button;
	User user;
	ComboBox<Provincia> a = new ComboBox<>();
	ProvinciaRepository r;
	
	public UserView(UserRepository repo, ProvinciaRepository r) {
//Para traer el usuario que está usando la sesión
		this.user = (User) SecurityUtils.getAuthenticatedUser();
		
		
		this.repo = repo;
		this.grid = new Grid<>(User.class);
		this.grid.setItems(repo.findByid(this.user.getId()));
		add(grid);
		this.texto = new H1();
		this.button = new Button();
		
		
//para Comprobar que el usuario está conectado	
		if(SecurityUtils.isUserLoggedIn()) {
			texto.setText("Estoy conectado");
		}
		else {
			texto.setText("No estoy conectado");
		}
		
//Para mostrar algo que sólo sea visible a cierto roles
		if (this.user != null && this.user.getAuthorities().stream()
			      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			texto.setText("Visible solo si soy admin");
			add(texto);
		}
	}
	
	void listProvincia(int id) {
		grid.setItems(repo.findByid(id));
	}
	
}
