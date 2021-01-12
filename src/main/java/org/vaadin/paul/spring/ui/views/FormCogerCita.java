package org.vaadin.paul.spring.ui.views;

import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.Localidad;
import org.vaadin.paul.spring.entities.Provincia;
import org.vaadin.paul.spring.repositories.CentroRepository;
import org.vaadin.paul.spring.repositories.LocalidadRepository;
import org.vaadin.paul.spring.repositories.ProvinciaRepository;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.paul.spring.MainView;

@Route(value = "coger-cita", layout = MainView.class)
@PageTitle("Coger Cita")
public class FormCogerCita extends VerticalLayout {
	private ComboBox<String> especialidad = new ComboBox<>("Especialidad", "Traumatólogo", "Medico de cabecera");
	
	private ComboBox<Provincia> provincia = new ComboBox<>();
	
	private ComboBox<Localidad> localidad = new ComboBox<>();
	
	private ComboBox<Centro> centro = new ComboBox<>();
	
	private DatePicker fechaCita = new DatePicker("Fecha cita");

	private Button save = new Button("Save");
	
	private final ProvinciaRepository repoprovincias;
	private final LocalidadRepository repolocalidades;
	private final CentroRepository repocentros;
	
	
	public FormCogerCita(ProvinciaRepository repoprovincias, LocalidadRepository repolocalidades, CentroRepository repocentros) {
		
		this.repoprovincias = repoprovincias;
		this.repolocalidades = repolocalidades;
		this.repocentros = repocentros;
		
		
		provincia.setItemLabelGenerator(Provincia::getNombre);
		localidad.setItemLabelGenerator(Localidad::getNombre);
		centro.setItemLabelGenerator(Centro::getNombre);
		
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
		
		centro.setEnabled(false);
		localidad.setEnabled(false);
		
		listprovincias();
		
		
		add(provincia);
		add(localidad);
		add(centro);
		//add(especialidad);
		add(fechaCita);
		add(save);
		
		Binder<Centro> binder = new Binder<>(Centro.class);
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
}
