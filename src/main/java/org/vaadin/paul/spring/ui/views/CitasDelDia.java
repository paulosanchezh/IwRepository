package org.vaadin.paul.spring.ui.views;

import java.awt.Window;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.app.security.SecurityUtils;
import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Informe;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.InformeRepository;
import org.vaadin.paul.spring.repositories.SanitarioRepository;
import org.vaadin.paul.spring.repositories.TrabajadorRepository;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;

@Route(value = "citashoy", layout=MainView.class)
@PageTitle("Citas de hoy")
@Secured({"ROLE_SANITARIO", "ROLE_ADMIN"})

public class CitasDelDia<V> extends VerticalLayout {
	private Grid<Cita> grid;
	private final CitaRepository repo;
	private final InformeRepository repoinformes;
	private final SanitarioRepository reposanitario;
	private final TrabajadorRepository repotrabajador;

	public CitasDelDia(CitaRepository repo, SanitarioRepository repouser,InformeRepository repoinformes, TrabajadorRepository repotrabajador) {
		this.repo = repo;
		this.reposanitario = repouser;
		this.repoinformes = repoinformes;
		this.grid = new Grid<>(); 
		this.repotrabajador = repotrabajador;
		
		grid.setSelectionMode(SelectionMode.SINGLE);
		  
		 grid.addColumn(Cita::getNombreyApellidospaciente, "Nombre y apellidos").setHeader("Nombre y apellidos");
		 grid.addColumn(new ComponentRenderer<>(cita -> { 
			 Grid<Informe> grid2 = new Grid<>();
			 grid2.addColumn(Informe::getNombreyApellidospaciente).setHeader("Nombre y Apellidos");
			 grid2.addColumn(Informe::getPorQue).setHeader("Motivo");
			 grid2.addColumn(Informe::getEnfermedadActual).setHeader("Enfermedad actual");
			 Button confirmbutton = new Button("Detalles"); 
			 Dialog dialog = new Dialog();
			 dialog.add( grid2, new Button("Close", e -> dialog.close()) ); 
			 dialog.setModal(true);
			 dialog.setDraggable(true); 
			 dialog.setResizable(false);
			 dialog.setWidth("1200px"); 
			 dialog.setHeight("1000px");
			 confirmbutton.addClickListener(event -> { 
				grid2.setItems(cita.getInforme());
				dialog.open();
		 	}); 
		 return confirmbutton;
		  }));
		  
		 add(grid);
		 listCustomers();
		
	}
	
	private void listCustomers() {
		LocalDate hoy = LocalDate.now();
		User u = (User) SecurityUtils.getAuthenticatedUser();
		grid.setItems(repo.findByFechaCitaAndSanitarioAndConfirmada(hoy, reposanitario.findByTrabajador(repotrabajador.findByUser(u)), true));
	}
	
}
