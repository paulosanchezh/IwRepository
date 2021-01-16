package org.vaadin.paul.spring.ui.views;

import java.util.List;

import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Trabajador;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.CentroRepository;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.UserRepository;

import com.vaadin.flow.component.grid.Grid;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
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
	private final CentroRepository repocentro;
	User user = (User) SecurityUtils.getAuthenticatedUser();
	

	public VerCitas(UserRepository repousuario, CitaRepository repo, CentroRepository repocentro) {
		this.repo = repo;
		this.repousuario = repousuario;
		this.grid = new Grid<>();
		this.repocentro = repocentro;
		Button crearcitabutton = new Button("Coger cita");
		H1 h = new H1(this.user.getNombreyApellidos());
		this.grid.addColumn(Cita::getNombreyApellidosSanitario, "Sanitario"
				+ " ").setHeader("Sanitario");
		this.grid.addColumn(Cita::getFecha, "Fecha").setHeader("Fecha");
		this.grid.addColumn(Cita::getHora, "Hora").setHeader("Hora");
		this.grid.addColumn(Cita::getCentroString, "Centro").setHeader("Centro");
		this.grid.addColumn(Cita::getConfirmadaString, "Confirmada").setHeader("Confirmada");
		Binder<Cita> binder = new Binder<>(Cita.class);
		
		grid.addColumn(new ComponentRenderer<>(cita -> { 
			
			
			Button modificarbutton = new Button("Modificar");
			DatePicker fecha = new DatePicker("Fecha cita");
			TimePicker hora = new TimePicker("Hora cita");
			Trabajador trabajador = listTrabajador(cita);
			
			binder.setBean(cita);
			
			 
			if(cita.getConfirmada())
				modificarbutton.setEnabled(false);
			
		if(fecha.getValue() != null)
		    if (LocalDate.now().compareTo(fecha.getValue()) == 0) {
		    	if (trabajador.getHoraInicio().compareTo(LocalTime.now()) > 0 ) {
		        	hora.setMinTime(trabajador.getHoraInicio());
		        }
		        else {
		        	int min,hour;
		        	if(LocalTime.now().getMinute() >= 30) {
		        		min=0;
		        		hour=+2;
		        	}
		        	else {
		        		min=30;
		        		hour=1;
		        	}
		        	hora.setMinTime(LocalTime.of(LocalTime.now().getHour()+hour,min,0));
		        }
		    }
		      
		     hora.setStep(Duration.ofMinutes(30));
		     hora.setMinTime(trabajador.getHoraInicio());
		     hora.setMaxTime(trabajador.getHoraFinal());
		     fecha.setMin(LocalDate.now());
			
			modificarbutton.addClickListener(event ->{
				
			  binder.forField(fecha)
		   			.asRequired("Debes de selecionar una fecha")
		   			.bind(Cita::getFecha, Cita::setFecha);
		       add(fecha);
		       
		       binder.forField(hora)
					.asRequired("Debes de selecionar una hora")
					.bind(Cita::getHora, Cita::setHora);
		       add(hora);
		       
		       Dialog dialog = new Dialog();
				 dialog.setModal(true);
				 dialog.setDraggable(true); 
				 dialog.setResizable(false);
				 dialog.setWidth("1200px"); 
				 dialog.setHeight("1000px");
				 
				 dialog.open();
				 Button closebutton = new Button("close", e -> dialog.close());
				 Button confirmarbutton = new Button("Confirmar");
				 dialog.add(fecha, hora, confirmarbutton, closebutton);
				 
				 
				 confirmarbutton.addClickListener(e1 ->{
					 if (binder.validate().isOk()) {
						 Notification.show("La cita ha sido modificada");
				    		repo.save(cita);
				    	}
					 listcitas();
					 dialog.close();
				 });
				
			});
			
			return modificarbutton;
		}));
		
		grid.addColumn(new ComponentRenderer<>(cita -> { 
			Button cancelarbutton = new Button("Cancelar Cita");
			if(cita.getConfirmada())
				cancelarbutton.setEnabled(false);
			
			cancelarbutton.addClickListener(event ->{
				
			});
			
			cancelarbutton.addThemeVariants(ButtonVariant.LUMO_ERROR);
			
			return cancelarbutton;
		}));
		
		add(h);
		crearcitabutton.addClickListener(event -> { 

			crearcitabutton.getUI().ifPresent(ui -> ui.navigate(solicitarCita.class)); 

	 	}); 
		
		listcitas();
		add(grid);
		add(crearcitabutton);
		
	}

	private void listcitas() {
		grid.setItems(repo.findByPaciente(repousuario.findByid(this.user.getId())));
	}
	
	private Trabajador listTrabajador(Cita cita) {
		return cita.getSanitario().getTrabajador();
	}
	
}
