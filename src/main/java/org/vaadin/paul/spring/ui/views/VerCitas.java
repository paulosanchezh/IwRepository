package org.vaadin.paul.spring.ui.views;

import java.util.List;

import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.UserRepository;

import com.vaadin.flow.component.grid.Grid;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.app.security.SecurityUtils;

@Route(value = "ver-citas", layout = MainView.class)
@PageTitle("Citas")
@Secured({"ROLE_USER", "ROLE_ADMIN"})

public class VerCitas extends VerticalLayout {

	private Grid<Cita> grid;
	private final UserRepository repousuario;
	private final CitaRepository repo;

	public VerCitas(UserRepository repousuario, CitaRepository repo) {
		
		this.repo = repo;
		this.repousuario = repousuario;
		this.grid = new Grid<>();
		Button crearcitabutton = new Button("Coger cita");
		
		this.grid.addColumn(Cita::getNombreyApellidosSanitario, "Nombres y Apellidos").setHeader("Nombre y Apellidos");
		this.grid.addColumn(Cita::getFechaCita, "Fecha").setHeader("Fecha");
		this.grid.addColumn(Cita::getConfirmada, "Confirmada").setHeader("Confirmada");
		
		crearcitabutton.addClickListener(event -> { 
			crearcitabutton.getUI().ifPresent(ui -> ui.navigate(FormCogerCita.class));
	 	}); 
		
		listpacientes();
		add(grid);
		add(crearcitabutton);
		
	}
	
	private void listpacientes() {
		User u = (User) SecurityUtils.getAuthenticatedUser();
		grid.setItems(repo.findByPaciente(u));
	}
}
