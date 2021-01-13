package org.vaadin.paul.spring.ui.views;

import java.time.LocalDate;

import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.SanitarioRepository;
import org.vaadin.paul.spring.repositories.TrabajadorRepository;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.app.security.SecurityUtils;
@Route(value = "citas-pendientes", layout = MainView.class)
@PageTitle("Citas Pendientes")
@Secured({"ROLE_SANITARIO", "ROLE_ADMIN"})

public class CitasPendientes extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private final CitaRepository repo;
	private final SanitarioRepository reposanitario;
	private final TrabajadorRepository repotrabajador;
	final Grid<Cita> grid;

	public CitasPendientes(CitaRepository repo, SanitarioRepository repouser, TrabajadorRepository repotrabajador) {
		this.repo = repo;
		this.reposanitario = repouser;
		this.repotrabajador = repotrabajador;
		this.grid = new Grid<>();
		grid.addColumn(Cita::getNombreyApellidospaciente, "Nombres y Apellidos").setHeader("Nombre y apellidos");
		grid.addColumn(new ComponentRenderer<>(cita -> { 
			Button confirmbutton = new Button("Confirmar"); 
			confirmbutton.addClickListener(event -> { 
				cita.setConfirmada(true);
				repo.save(cita);
				listCustomers();
			}); 
			return confirmbutton;
		}));
		add(grid);
		listCustomers();
	}

	private void listCustomers() {
		LocalDate hoy = LocalDate.now();
		User u = (User) SecurityUtils.getAuthenticatedUser();
		grid.setItems(repo.findByFechaCitaAndSanitarioAndConfirmada(hoy, reposanitario.findByTrabajador(repotrabajador.findByUser(u)), false));
	}
}
