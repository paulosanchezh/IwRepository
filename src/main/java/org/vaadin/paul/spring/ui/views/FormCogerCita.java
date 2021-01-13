package org.vaadin.paul.spring.ui.views;

import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Especialidad;
import org.vaadin.paul.spring.entities.Informe;
import org.vaadin.paul.spring.entities.Localidad;
import org.vaadin.paul.spring.entities.Provincia;
import org.vaadin.paul.spring.entities.Sanitario;
import org.vaadin.paul.spring.entities.Trabajador;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.CentroRepository;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.LocalidadRepository;
import org.vaadin.paul.spring.repositories.ProvinciaRepository;
import org.vaadin.paul.spring.repositories.SanitarioRepository;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.app.security.SecurityUtils;

@Route(value = "coger-cita", layout = MainView.class)
@PageTitle("Coger Cita")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class FormCogerCita extends VerticalLayout {
	private ComboBox<Especialidad> especialidad = new ComboBox<>();
	
	private ComboBox<Provincia> provincia = new ComboBox<>();
	
	private ComboBox<Localidad> localidad = new ComboBox<>();
	
	private ComboBox<Centro> centro = new ComboBox<>();
	
	private DatePicker fechaCita = new DatePicker("Fecha cita");

	private Button save = new Button("Save");
	
	private final ProvinciaRepository repoprovincias;
	private final LocalidadRepository repolocalidades;
	private final CentroRepository repocentros;
	private final CitaRepository repocitas;
	private final SanitarioRepository reposanitario;
	
	
	public FormCogerCita(ProvinciaRepository repoprovincias, LocalidadRepository repolocalidades, CentroRepository repocentros, CitaRepository repocitas, SanitarioRepository reposanitario) {
		
		this.repoprovincias = repoprovincias;
		this.repolocalidades = repolocalidades;
		this.repocentros = repocentros;
		this.repocitas = repocitas;
		this.reposanitario = reposanitario;
		
		
		provincia.setItemLabelGenerator(Provincia::getNombre);
		localidad.setItemLabelGenerator(Localidad::getNombre);
		centro.setItemLabelGenerator(Centro::getNombre);
		especialidad.setItemLabelGenerator(Especialidad::getNombre_);
		
		provincia.addValueChangeListener(event -> {
		    if (event.getValue() != null) {
		    	listlocalidades(event.getValue());
		    	localidad.setEnabled(true);
		    }
		});
		
		localidad.addValueChangeListener(event -> {
		    if (event.getValue() != null) {
		    	listcentros(event.getValue());
		    	centro.setEnabled(true);
		    }
		});
		
		centro.addValueChangeListener(event -> {
		    if (event.getValue() != null) {
		    	listespecialidades(event.getValue());
		    	especialidad.setEnabled(true);
		    }
		});
		
		provincia.setLabel("Provincia");
		localidad.setLabel("Localidad");
		centro.setLabel("Centro");
		especialidad.setLabel("Especialidad");
		centro.setEnabled(false);
		localidad.setEnabled(false);
		especialidad.setEnabled(false);
		
		save.addClickListener(event -> { 
			LocalDate fecha = fechaCita.getValue();
			User u = (User) SecurityUtils.getAuthenticatedUser();
			Sanitario s = medicocita(centro.getValue(), especialidad.getValue());
			Cita c = new Cita((Integer) null, u, s, null, fecha, 30);
			guardar_cita(c);
		}); 
		
		listprovincias();
		
		add(provincia);
		add(localidad);
		add(centro);
		add(especialidad);
		add(fechaCita);
		add(save);
		
		//Binder<Centro> binder = new Binder<>(Centro.class);
		//binder.bindInstanceFields(this);
		
	}
	
	void listprovincias() {
		provincia.setItems(repoprovincias.findAll());
	}
	
	void listlocalidades(Provincia provincia) {
		localidad.setItems(repolocalidades.findByProvincia(provincia));
	}
	
	void listcentros(Localidad localidad) {
		centro.setItems(repocentros.findByLocalidad(localidad));
	}
	
	void listespecialidades(Centro c){
		especialidad.setItems(c.getEspecialidad());
	}
	
	Sanitario medicocita(Centro c, Especialidad e) {
		
		List<Trabajador> trabajadores = c.getTrabajadores();
		List<Sanitario> sanitarios = new ArrayList<Sanitario>();
		Sanitario s = new Sanitario();
		
		for(Trabajador trabajador:trabajadores) {
			
			sanitarios.add(reposanitario.findByTrabajador(trabajador));
			
		}
		
		for(Sanitario sanitario:sanitarios) {
			if(sanitario.getEspecialidad() == e) {
				s = sanitario;
				break;
			}
		}
		
		return s;
	}
	
	void guardar_cita(Cita c) {
		repocitas.save(c);
	}
}
