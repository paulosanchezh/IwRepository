package org.vaadin.paul.spring.ui.views;

import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.UserRepository;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;

@Route(value="usuarios", layout=MainView.class)
public class FormUsuario extends FormLayout {
	UserRepository repo;
	Grid<User> grid;
	
	public FormUsuario(UserRepository repo) {
		this.repo = repo;
		this.grid = new Grid<>(User.class);
		this.grid.setItems(repo.findAll());
		add(grid);
	}
}
