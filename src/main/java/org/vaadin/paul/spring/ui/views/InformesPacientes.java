package org.vaadin.paul.spring.ui.views;

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
import org.vaadin.paul.spring.repositories.SanitarioRepository;
import org.vaadin.paul.spring.repositories.TrabajadorRepository;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "informes-pacientes", layout = MainView.class)
@PageTitle("Informes Pacientes")

public class InformesPacientes extends FormLayout{
	private Grid<Informe> grid;
	private final InformeRepository repoinformes;
	private final SanitarioRepository reposanitario;
	private final TrabajadorRepository repotrabajador;
	private final CitaRepository repo;
	private List <Informe> informes;
	
	public InformesPacientes(InformeRepository repoinformes, SanitarioRepository reposanitario, CitaRepository repo, TrabajadorRepository repotrabajador) {
		
		this.repoinformes = repoinformes;
		this.grid = new Grid<>();
		this.reposanitario = reposanitario;
		this.repo = repo;
		this.repotrabajador = repotrabajador;
		informes = new ArrayList<Informe>();
		
		this.grid.addColumn(Informe::getNombreyApellidospaciente, "Nombre y Apellidos").setHeader("Nombre y Apellidos");
		grid.addColumn(new ComponentRenderer<>(informe -> { 
			 Button confirmbutton = new Button("Informe");
			 Grid<Informe> grid2 = new Grid<>();
			 grid2.addColumn(Informe::getNombreyApellidospaciente).setHeader("Nombre y Apellidos");
			 grid2.addColumn(Informe::getPorQue).setHeader("Motivo");
			 grid2.addColumn(Informe::getEnfermedadActual).setHeader("Enfermedad actual");
			 Dialog dialog = new Dialog();
			 dialog.add( grid2, new Button("Close", e -> dialog.close()) );
			 if(!informe.getFirma()) {
				 confirmbutton.addThemeVariants(ButtonVariant.LUMO_ERROR);
				 dialog.add(new Button("Firmar", e -> {
					 Dialog firma = new Dialog();
					 firma.add(new Text("Informe firmado con exito"));
					 informe.setFirma(true);
					 repoinformes.save(informe);
					 firma.open();
				 }) );
			 }
			 dialog.setModal(true);
			 dialog.setDraggable(true); 
			 dialog.setResizable(false);
			 dialog.setWidth("1200px"); 
			 dialog.setHeight("1000px"); 
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
		User u = (User) SecurityUtils.getAuthenticatedUser();
		List <Cita> citas = repo.findBySanitario(reposanitario.findByTrabajador(repotrabajador.findByUser(u)));
		for(Cita cita:citas) {	
			informes.add(repoinformes.findByid(cita.getInforme().getid()));
		}
		grid.setItems(informes);
	}
}
