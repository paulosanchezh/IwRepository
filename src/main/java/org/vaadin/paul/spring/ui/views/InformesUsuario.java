package org.vaadin.paul.spring.ui.views;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.app.security.SecurityUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Informe;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.InformeRepository;
import org.vaadin.paul.spring.repositories.UserRepository;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
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
	
	private Grid<Cita> grid;
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
		
		this.grid.addColumn(Cita::getNombreyApellidosSanitario, "Nombre del Sanitario").setHeader("Nombre del Sanitario");
		grid.addColumn(new ComponentRenderer<>(cita -> { 
			 Dialog dialog = new Dialog();
			 Accordion sample = new Accordion();
		     sample.setHeight("800px");
			 dialog.setModal(true);
			 dialog.setDraggable(true); 
			 dialog.setResizable(false);
			 dialog.setWidth("1200px"); 
			 dialog.setHeight("1000px");
			 Button confirmbutton = new Button("Informe"); 
			 
			 confirmbutton.addClickListener(event -> { 
				 dialog.open();
		 	}); 
			 
			dialog.add(new Button("Close", e -> dialog.close()) );
		 return confirmbutton;
		  }));
		
		grid.addColumn(new ComponentRenderer<>(informe -> {
			Button facturabutton = new Button("Factura"); 
			
			Dialog dialog = new Dialog();
			dialog.add(new Button("Close", e -> dialog.close()) ); 
			dialog.setModal(true);
			dialog.setDraggable(true); 
			dialog.setResizable(false);
			dialog.setWidth("1200px"); 
			dialog.setHeight("1000px");
			facturabutton.addClickListener(event -> { 
				 
				 dialog.open();
		 	}); 
			return facturabutton;	
		}));
		listInformes();
		add(grid);
	}
	
	private void listInformes() {
		User u = (User) SecurityUtils.getAuthenticatedUser();
		List <Cita> citas = repo.findByPacienteAndInforme(u.getId());
		
		grid.setItems(citas);
	}
}
