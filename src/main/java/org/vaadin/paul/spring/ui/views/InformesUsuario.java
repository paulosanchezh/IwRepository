package org.vaadin.paul.spring.ui.views;

import org.apache.commons.lang3.StringUtils;
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
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "informes-usuarios", layout = MainView.class)
@PageTitle("Informes")
@Secured({"ROLE_USER"})

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
		
		ListDataProvider<Cita> dataProvider = new ListDataProvider<Cita>(listCitas());
		
		this.grid.setDataProvider(dataProvider);
		
		Grid.Column<Cita> SanitarioColumn = this.grid.addColumn(Cita::getNombreyApellidosSanitario, "Sanitario" + " ").setHeader("Sanitario");
		Grid.Column<Cita> FechaColumn = this.grid.addColumn(Cita::getFecha, "Fecha").setHeader("Fecha");
		Grid.Column<Cita> HoraColumn = this.grid.addColumn(Cita::getHora, "Hora").setHeader("Hora");
		Grid.Column<Cita> CentroColumn = this.grid.addColumn(Cita::getCentroString, "Centro").setHeader("Centro");
		
		HeaderRow filterRow = grid.appendHeaderRow();
		
		this.grid.setDataProvider(dataProvider);
		
		//Filtro Sanitario
				TextField SanitarioField = new TextField();
				SanitarioField.addValueChangeListener(event -> 
				dataProvider.addFilter(
				        cita -> StringUtils.containsIgnoreCase(cita.getNombreyApellidosSanitario(),SanitarioField.getValue())));

				SanitarioField.setValueChangeMode(ValueChangeMode.EAGER);

				filterRow.getCell(SanitarioColumn).setComponent(SanitarioField);
				SanitarioField.setSizeFull();
				SanitarioField.setPlaceholder("Filter");
				
				//filtro Fecha
				
				TextField FechaField = new TextField();
				FechaField.addValueChangeListener(event -> 
				dataProvider.addFilter(
				        cita -> StringUtils.containsIgnoreCase(cita.getFecha().toString(),FechaField.getValue())));

				 FechaField.setValueChangeMode(ValueChangeMode.EAGER);

				filterRow.getCell(FechaColumn).setComponent(FechaField);
				FechaField.setSizeFull();
				FechaField.setPlaceholder("Filter");
				
				//Filtro Hora
				TextField HoraField = new TextField();
				HoraField.addValueChangeListener(event -> 
				dataProvider.addFilter(
				        cita -> StringUtils.containsIgnoreCase(cita.getHora().toString(),HoraField.getValue())));

				 HoraField.setValueChangeMode(ValueChangeMode.EAGER);

				filterRow.getCell(HoraColumn).setComponent(HoraField);
				HoraField.setSizeFull();
				HoraField.setPlaceholder("Filter");
				
				//Filtro Centro
				TextField CentroField = new TextField();
				CentroField.addValueChangeListener(event -> 
				dataProvider.addFilter(
						cita -> StringUtils.containsIgnoreCase(cita.getCentroString(),CentroField.getValue())));

				CentroField.setValueChangeMode(ValueChangeMode.EAGER);

				filterRow.getCell(CentroColumn).setComponent(CentroField);
				CentroField.setSizeFull();
				CentroField.setPlaceholder("Filter");
		
		grid.addColumn(new ComponentRenderer<>(cita -> { 
			 Informe informe = cita.getInforme();
			 Accordion Datos_informe = new Accordion();
			 Button confirmbutton = new Button("Informe");
			 H1 h = new H1(cita.getNombreyApellidospaciente());
			 Dialog dialog = new Dialog();
			 dialog.add(h, Datos_informe);
			 dialog.add(new Button("Close", e -> dialog.close()) );
			 if(!informe.getFirma()) {
				 confirmbutton.addThemeVariants(ButtonVariant.LUMO_ERROR);
				 dialog.add(new Button("Firmar", e -> {
					 informe.setFirma(true);
					 repoinformes.save(informe);
					 Notification.show("El informe ha sido firmado");
					 dataProvider.refreshItem(cita);
					 dialog.close();
				 }) );
			 }
			 
			 Datos_informe.add("Porque", new Span(informe.getPorQue()));
			 Datos_informe.add("Diagnostico", new Span(informe.getDiagnostico()));
			 Datos_informe.add("Enfermedad Actual", new Span(informe.getEnfermedadActual()));
			 Datos_informe.add("Intervencion", new Span(informe.getIntervencion()));
			 Datos_informe.add("Medicamentos", new Span(informe.getMedicamentos()));
			 Datos_informe.add("Plan Clinico", new Span(informe.getPlanClinico()));
			 
			 dialog.setModal(true);
			 dialog.setDraggable(true); 
			 dialog.setResizable(false);
			 dialog.setWidth("1200px"); 
			 dialog.setHeight("1000px");
			 
			 confirmbutton.addClickListener(event -> { 
				 dialog.open();
		 	}); 
		 return confirmbutton;
		  }));
		
		grid.addColumn(new ComponentRenderer<>(cita -> {
			Button facturabutton = new Button("Factura"); 
			
			Dialog dialog = new Dialog(); 
			dialog.setModal(true);
			dialog.setDraggable(true); 
			dialog.setResizable(false);
			dialog.setWidth("1200px"); 
			dialog.setHeight("1000px");
			Label lugar = new Label();
			 Label paciente = new Label();
			 Label sanitario = new Label();
			 Label importe = new Label();
			 Label intervencion = new Label();
			 VerticalLayout vlugar = new VerticalLayout(lugar);
			 VerticalLayout vpaciente = new VerticalLayout(paciente);
			 VerticalLayout vsanitario = new VerticalLayout(sanitario);
			 VerticalLayout vimporte = new VerticalLayout(importe);
			 VerticalLayout vintervencion = new VerticalLayout(intervencion);
			 
			 lugar.setText(cita.getCentroString() + " , " + cita.getCentro().getUbicacion().getNombre() + "  tlf: " + cita.getCentro().getTelefono());
			 paciente.setText("Paciente: " + cita.getNombreyApellidospaciente());
			 sanitario.setText("Sanitario: " + cita.getNombreyApellidosSanitario());
			 importe.setText("Importe: " + cita.getImporte());
			 intervencion.setText("Intervencion: " + cita.getInforme().getIntervencion());
			 
			 dialog.add(vlugar, vpaciente, vsanitario, vimporte, vintervencion);
			 dialog.add(new Button("Close", e -> dialog.close()) );
			facturabutton.addClickListener(event -> { 
				 
				 
				 dialog.open();
		 	}); 
			return facturabutton;	
		}));
		add(grid);
	}
	
	private List<Cita> listCitas() {
		User u = (User) SecurityUtils.getAuthenticatedUser();
		List <Cita> citas = repo.findByPacienteAndInforme(u.getId());
		
		return citas;
	}
}
