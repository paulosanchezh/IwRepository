package org.vaadin.paul.spring.ui.views;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.app.security.SecurityUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Informe;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.SanitarioRepository;
import org.vaadin.paul.spring.repositories.TrabajadorRepository;
import org.vaadin.paul.spring.repositories.UserRepository;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "ver-pacientes", layout = MainView.class)
@PageTitle("Pacientes")
@Secured({"ROLE_SANITARIO", "ROLE_ADMIN"})

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
		
		ListDataProvider<Cita> dataProvider = new ListDataProvider<Cita>(listpacientes());
		
		this.grid.setDataProvider(dataProvider);
		
		HeaderRow filterRow = grid.appendHeaderRow();
		
		Grid.Column<Cita> PacienteColumn = this.grid.addColumn(Cita::getNombreyApellidospaciente, "Nombres y Apellidos").setHeader("Nombre y Apellidos");
		Grid.Column<Cita> FechaColumn = this.grid.addColumn(Cita::getFecha, "Fecha").setHeader("Fecha");
		
		//Filtro Sanitario
		TextField PacienteField = new TextField();
		PacienteField.addValueChangeListener(event -> 
		dataProvider.addFilter(
		        cita -> StringUtils.containsIgnoreCase(cita.getNombreyApellidosSanitario(),PacienteField.getValue())));

		PacienteField.setValueChangeMode(ValueChangeMode.EAGER);

		filterRow.getCell(PacienteColumn).setComponent(PacienteField);
		PacienteField.setSizeFull();
		PacienteField.setPlaceholder("Filter");
		
		//filtro Fecha
		
		TextField FechaField = new TextField();
		FechaField.addValueChangeListener(event -> 
		dataProvider.addFilter(
		        cita -> StringUtils.containsIgnoreCase(cita.getFecha().toString(),FechaField.getValue())));

		
		 FechaField.setValueChangeMode(ValueChangeMode.EAGER);

			filterRow.getCell(FechaColumn).setComponent(FechaField);
			FechaField.setSizeFull();
			FechaField.setPlaceholder("Filter");
			
		
			grid.addColumn(new ComponentRenderer<>(cita -> { 
				Button historialbutton = new Button("Ver Todas Las Citas");
				Grid<Cita> grid2 = new Grid<>();
				
				 
				historialbutton.addClickListener(event ->{
					ListDataProvider<Cita> dataProviderinformes = new ListDataProvider<Cita>(listcitas(cita.getPaciente()));
					Dialog dialog = new Dialog();
					grid2.setDataProvider(dataProviderinformes);
					
					HeaderRow filterRow2 = grid2.appendHeaderRow();
					
					Grid.Column<Cita> Paciente2Column = grid2.addColumn(Cita::getNombreyApellidospaciente, "Nombres y Apellidos").setHeader("Nombre y Apellidos");
					Grid.Column<Cita> Fecha2Column = grid2.addColumn(Cita::getFecha, "Fecha").setHeader("Fecha");
					
					
					//Filtro Sanitario
					TextField Paciente2Field = new TextField();
					Paciente2Field.addValueChangeListener(event1 -> 
					dataProviderinformes.addFilter(
					        cita2 -> StringUtils.containsIgnoreCase(cita2.getNombreyApellidosSanitario(),Paciente2Field.getValue())));

					Paciente2Field.setValueChangeMode(ValueChangeMode.EAGER);

					filterRow2.getCell(Paciente2Column).setComponent(Paciente2Field);
					Paciente2Field.setSizeFull();
					Paciente2Field.setPlaceholder("Filter");
					
					//filtro Fecha
					
					TextField Fecha2Field = new TextField();
					Fecha2Field.addValueChangeListener(event1 -> 
					dataProviderinformes.addFilter(
					        cita2 -> StringUtils.containsIgnoreCase(cita2.getFecha().toString(),Fecha2Field.getValue())));
					
					Fecha2Field.setValueChangeMode(ValueChangeMode.EAGER);

					filterRow2.getCell(Fecha2Column).setComponent(Fecha2Field);
					Fecha2Field.setSizeFull();
					Fecha2Field.setPlaceholder("Filter");
					
					grid2.addColumn(new ComponentRenderer<>(cita3 -> { 
						 Informe informe = cita3.getInforme();
						 Button confirmbutton = new Button(" Ver Informe");
						 confirmbutton.addClickListener(event2 -> { 
							 Accordion Datos_informe = new Accordion();
							 
							 H1 h = new H1(cita3.getNombreyApellidospaciente());
							 Dialog dialog2 = new Dialog();
							 dialog2.add(h, Datos_informe);
							 dialog2.add(new Button("Close", e -> dialog2.close()) );
							 
							 Datos_informe.add("Porque", new Span(informe.getPorQue()));
							 Datos_informe.add("Diagnostico", new Span(informe.getDiagnostico()));
							 Datos_informe.add("Enfermedad Actual", new Span(informe.getEnfermedadActual()));
							 Datos_informe.add("Intervencion", new Span(informe.getIntervencion()));
							 Datos_informe.add("Medicamentos", new Span(informe.getMedicamentos()));
							 Datos_informe.add("Plan Clinico", new Span(informe.getPlanClinico()));
							 
							 dialog2.setModal(true);
							 dialog2.setDraggable(true); 
							 dialog2.setResizable(false);
							 dialog2.setWidth("1200px"); 
							 dialog2.setHeight("1000px");
							 dialog2.open();
					 	}); 
						 return confirmbutton;
					 }));
					
					dialog.setModal(true);
					dialog.setDraggable(true); 
					dialog.setResizable(false);
					dialog.setWidth("1200px"); 
					dialog.setHeight("1000px");
					
					dialog.add(grid2);
					dialog.add(new Button("Close", e -> dialog.close()) );
					dialog.open();
				});
				return historialbutton;
			}));
		
		add(grid);
	}
	
	private Collection<Cita> listpacientes() {
		User u = (User) SecurityUtils.getAuthenticatedUser();
		List<Cita> citas = repo.findBySanitarioAndUltimacita(reposanitario.findByTrabajador(repotrabajador.findByUser(u)).getid());
		
		return citas;
	}
	
	private Collection<Cita> listcitas(User paciente){
		User sanitario = (User) SecurityUtils.getAuthenticatedUser();
		List<Cita> citas = repo.findBySanitarioAndPaciente(reposanitario.findByTrabajador(repotrabajador.findByUser(sanitario)), paciente);
		
		return citas;
	}
	
}
