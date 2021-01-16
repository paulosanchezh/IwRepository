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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
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
	TextField porque = new TextField();
    TextField enfermedad = new TextField();
    TextField intervencion = new TextField();
    TextField diagnostico = new TextField();
    TextField medicamento = new TextField();
    TextField planclinico = new TextField();
    Button firmar = new Button("Firmar");
	 Button closebutton = new Button("Close");
	
	

	public CitasDelDia(CitaRepository repo, SanitarioRepository repouser,InformeRepository repoinformes, TrabajadorRepository repotrabajador) {
		this.repo = repo;
		this.reposanitario = repouser;
		this.repoinformes = repoinformes;
		this.grid = new Grid<>(); 
		this.repotrabajador = repotrabajador;
		
		grid.setSelectionMode(SelectionMode.SINGLE);
		  
		 grid.addColumn(Cita::getNombreyApellidospaciente, "Nombre y apellidos").setHeader("Nombre y apellidos");
		 grid.addColumn(Cita::getHora, "Hora").setHeader("Hora");
		 grid.addColumn(new ComponentRenderer<>(cita -> { 
			 
			 Button confirmbutton = new Button("Informe"); 
			 Dialog dialog = new Dialog();
			 dialog.setModal(true);
			 dialog.setDraggable(true); 
			 dialog.setResizable(false);
			 dialog.setWidth("1200px"); 
			 dialog.setHeight("1000px");
		
			 confirmbutton.addClickListener(event -> { 
				 Binder<Informe> binder = new Binder<>(Informe.class);
				 
				 closebutton.addClickListener( e2 -> {
					 dialog.close();
				 });
					
					
				    porque.setPlaceholder("por que");
				    porque.setLabel("Porque");
				    
				    enfermedad.setPlaceholder("Enfermedad del paciente");
				    enfermedad.setLabel("Enfermedad");
				    
				    intervencion.setPlaceholder("Intervencion necesaria");
				    intervencion.setLabel("Intervencion");
				    
				    diagnostico.setPlaceholder("Diagnóstico del paciente");
				    diagnostico.setLabel("diagnostico");
				    
				    medicamento.setPlaceholder("Medicamento(s) recetado al paciente");
				    medicamento.setLabel("Medicamento");
				    
				    planclinico.setPlaceholder("Plan clinico");
				    planclinico.setLabel("Plan clinico");
				    

					VerticalLayout lporque = new VerticalLayout(porque);

					VerticalLayout lenfermedad = new VerticalLayout(enfermedad);

					VerticalLayout lintervencion = new VerticalLayout(intervencion);

					VerticalLayout ldiagnostico = new VerticalLayout(diagnostico);

					VerticalLayout lmedicamento = new VerticalLayout(medicamento);

					VerticalLayout lplanclinico = new VerticalLayout(planclinico);
					
				    
				    binder.forField(porque)
		        	.asRequired("Por que no puede estar vacío")
		        	.bind(Informe::getPorQue, Informe::setPorQue);
				    
				    binder.forField(enfermedad)
		        	.asRequired("Enfermedad no puede estar vacío")
		        	.bind(Informe::getEnfermedadActual, Informe::setEnfermedadActual);
				    
				    binder.forField(intervencion)
		        	.asRequired("Intervencion no puede estar vacío")
		        	.bind(Informe::getIntervencion, Informe::setIntervencion);
				    
				    binder.forField(diagnostico)
		        	.asRequired("Diagnóstico no puede estar vacío")
		        	.bind(Informe::getDiagnostico, Informe::setDiagnostico);
				    
				    binder.forField(medicamento)
		        	.asRequired("Apellidos no puede estar vacío")
		        	.bind(Informe::getMedicamentos, Informe::setMedicamentos);
				    
				    binder.forField(planclinico)
		        	.asRequired("Apellidos no puede estar vacío")
		        	.bind(Informe::getPlanClinico, Informe::setPlanClinico);
				    
				    Informe informe = cita.getInforme();
			    	binder.setBean(informe);
				    
				    dialog.add(lporque, lenfermedad, lintervencion, ldiagnostico, lmedicamento, lplanclinico, firmar, closebutton);
				    firmar.addClickListener(evento -> {
				    	if (binder.validate().isOk()) {
				    		informe.setFirma(true);
				    		informe.setIdCita_(cita);
				    		repoinformes.save(informe);
				    	}
				    	cita.setInforme(informe);
				    	repo.save(cita);
				    	Notification.show("El informe ha sido modificado");
				    	dialog.close();
				    	
				    }); 
				    
				 
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
		grid.setItems(repo.findByFechaAndSanitarioAndConfirmada(hoy, reposanitario.findByTrabajador(repotrabajador.findByUser(u)), true));
	}
	
	
}
