package org.vaadin.paul.spring.ui.views;

import java.time.LocalDate;
import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.app.security.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Informe;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.SanitarioRepository;
import org.vaadin.paul.spring.repositories.TrabajadorRepository;
import org.vaadin.paul.spring.repositories.UserRepository;

import com.vaadin.flow.component.Text;
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

@Route(value = "ver-pacientes", layout = MainView.class)
@PageTitle("Pacientes")

public class VerPacientes extends VerticalLayout {
	
	private Grid<Cita> grid;
	private final UserRepository repousuario;
	private final CitaRepository repo;
	private final SanitarioRepository reposanitario;
	private final TrabajadorRepository repotrabajador;
	private List <Informe> informes;
	
	public VerPacientes(UserRepository repousuario, CitaRepository repo,SanitarioRepository reposanitario,TrabajadorRepository repotrabajador) {
		this.repo = repo;
		this.repotrabajador = repotrabajador;
		this.repousuario = repousuario;
		this.reposanitario = reposanitario;
		this.grid = new Grid<>();
		
		this.grid.addColumn(Cita::getNombreyApellidospaciente, "Nombres y Apellidos").setHeader("Nombre y Apellidos");
		this.grid.addColumn(Cita::getFechaCita, "Fecha").setHeader("Fecha");
		this.grid.addColumn(Cita::getConfirmada, "Confirmada").setHeader("Confirmada");
		
		listpacientes();
		add(grid);
	}
	
	private void listpacientes() {
		User u = (User) SecurityUtils.getAuthenticatedUser();
		grid.setItems(repo.findBySanitario(reposanitario.findByTrabajador(repotrabajador.findByUser(u))));
	}
	
}
