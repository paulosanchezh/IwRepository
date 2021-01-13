package org.vaadin.paul.spring.ui.views;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.MainView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Informe;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.InformeRepository;
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

@Route(value = "informes-usuarios", layout = MainView.class)
@PageTitle("Informes")
@Secured({"ROLE_USER", "ROLE_ADMIN"})

public class InformesUsuario extends VerticalLayout{
	
	private Grid<Informe> grid;
	private final InformeRepository repoinformes;
	private final UserRepository repousuario;
	private final CitaRepository repo;
	private List <Informe> informes;
	
	public InformesUsuario(InformeRepository repoinformes, UserRepository repousuario, CitaRepository repo) {
		
		this.repoinformes = repoinformes;
		this.grid = new Grid<>();
		this.repousuario = repousuario;
		this.repo = repo;
		informes = new ArrayList<Informe>();
		
		this.grid.addColumn(Informe::getNombreyApellidosSanitario).setHeader("Nombre del Sanitario");
		grid.addColumn(new ComponentRenderer<>(informe -> { 
			 Grid<Informe> grid2 = new Grid<>();
			 grid2.addColumn(Informe::getNombreyApellidosSanitario).setHeader("Nombre del Sanitario");
			 grid2.addColumn(Informe::getPorQue).setHeader("Motivo");
			 grid2.addColumn(Informe::getEnfermedadActual).setHeader("Enfermedad actual");
			 Dialog dialog = new Dialog();
			 dialog.add( grid2, new Button("Close", e -> dialog.close()) );
			 dialog.setModal(true);
			 dialog.setDraggable(true); 
			 dialog.setResizable(false);
			 dialog.setWidth("1200px"); 
			 dialog.setHeight("1000px");
			 Button confirmbutton = new Button("Informe"); 
			 confirmbutton.addClickListener(event -> { 
				 grid2.setItems(informe);
				 dialog.open();
		 	}); 
		 return confirmbutton;
		  }));
		
		grid.addColumn(new ComponentRenderer<>(informe -> {
			Button facturabutton = new Button("Factura"); 
			Grid<Cita> grid2 = new Grid<>();
			grid2.addColumn(Cita::getNombreyApellidospaciente).setHeader("Nombre y Apellidos");
			grid2.addColumn(Cita::getImporte).setHeader("Precio");
			grid2.addColumn(Cita::getNombreyApellidosSanitario).setHeader("Sanitario");
			Dialog dialog = new Dialog();
			dialog.add( grid2, new Button("Close", e -> dialog.close()) ); 
			dialog.setModal(true);
			dialog.setDraggable(true); 
			dialog.setResizable(false);
			dialog.setWidth("1200px"); 
			dialog.setHeight("1000px");
			facturabutton.addClickListener(event -> { 
				 grid2.setItems(informe.getIdCita_());
				 dialog.open();
		 	}); 
			return facturabutton;	
		}));
		listInformes();
		add(grid);
	}
	
	private void listInformes() {
		List <Cita> citas = repo.findByPaciente(repousuario.findByid(4));
		for(Cita cita:citas) {	
			informes.add(repoinformes.findByid(cita.getInforme().getid()));
		}
		
		grid.setItems(informes);
		
	}

}
