package org.vaadin.paul.spring.ui.views;

import java.time.LocalDate;

import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.SanitarioRepository;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.paul.spring.MainView;
@Route(value = "citas-pendientes", layout = MainView.class)
@PageTitle("Citas Pendientes")

public class CitasPendientes extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private final CitaRepository repo;
	private final SanitarioRepository reposanitario;
	final Grid<Cita> grid;

	public CitasPendientes(CitaRepository repo, SanitarioRepository repouser) {
		this.repo = repo;
		this.reposanitario = repouser;
		this.grid = new Grid<>();
		grid.addColumn(Cita::getNombreyApellidospaciente).setHeader("Nombre y apellidos");
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
		LocalDate x = LocalDate.of(2020, 1, 26);
		grid.setItems(repo.findByFechaCitaAndSanitarioAndConfirmada(x, reposanitario.findById(13), false));
	}
}
