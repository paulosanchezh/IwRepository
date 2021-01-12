package org.vaadin.paul.spring.ui.views;

import java.awt.Window;
import java.time.LocalDate;
import java.util.ArrayList;
import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Informe;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.InformeRepository;
import org.vaadin.paul.spring.repositories.SanitarioRepository;

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

public class CitasDelDia<V> extends VerticalLayout {
	private Grid<Cita> grid;
	private final CitaRepository repo;
	private final InformeRepository repoinformes;
	private final SanitarioRepository reposanitario;

	public CitasDelDia(CitaRepository repo, SanitarioRepository repouser,InformeRepository repoinformes) {
		this.repo = repo;
		this.reposanitario = repouser;
		this.repoinformes = repoinformes;
		this.grid = new Grid<>(); 
		
		grid.setSelectionMode(SelectionMode.SINGLE);
		  
		 grid.addColumn(Cita::getNombreyApellidospaciente).setHeader("Nombre y apellidos");
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
		LocalDate x = LocalDate.of(2020, 1, 26);
		grid.setItems(repo.findByFechaCitaAndSanitarioAndConfirmada(x, reposanitario.findById(13), true));
	}
	
}
