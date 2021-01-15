package org.vaadin.paul.spring.ui.views;

import java.util.List;

import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.Comunidad;
import org.vaadin.paul.spring.entities.Especialidad;
import org.vaadin.paul.spring.entities.Localidad;
import org.vaadin.paul.spring.entities.Provincia;
import org.vaadin.paul.spring.entities.Rol;
import org.vaadin.paul.spring.entities.Sanitario;
import org.vaadin.paul.spring.entities.Trabajador;
import org.vaadin.paul.spring.repositories.CentroRepository;
import org.vaadin.paul.spring.repositories.ComunidadRepository;
import org.vaadin.paul.spring.repositories.LocalidadRepository;
import org.vaadin.paul.spring.repositories.ProvinciaRepository;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;

@Route("cita")
public class solicitarCita extends VerticalLayout{

	private ComboBox<Especialidad> cbEspecialidad = new ComboBox<>();
	private ComboBox<Comunidad> cbComunidad = new ComboBox<>();	
	private ComboBox<Provincia> cbProvincia = new ComboBox<>();	
	private ComboBox<Localidad> cbLocalidad = new ComboBox<>();
	private ComboBox<Centro> cbCentro = new ComboBox<>();
	private ComboBox<Sanitario> cbSanitario = new ComboBox<>();
	
	private ComunidadRepository repoComunidad;
	private ProvinciaRepository repoProvincia;
	private LocalidadRepository repoLocalidad;
	private CentroRepository repoCentro;
	
	private DatePicker fecha = new DatePicker("Fecha cita");
	private TimePicker hora = new TimePicker("Hora cita");
	
	public solicitarCita(ComunidadRepository repoComunidad, ProvinciaRepository repoProvincia,
			LocalidadRepository repoLocalidad, CentroRepository repoCentro) {
		
        cbComunidad.setItems(repoComunidad.findAll());
        cbComunidad.setItemLabelGenerator(Comunidad::getNombre);
        add(cbComunidad);
        
        cbComunidad.addValueChangeListener(event -> {
		    if (event.getValue() != null) {
		    	cbProvincia.setItems(repoProvincia.findBycomunidad(event.getValue()));
		    	cbProvincia.setItemLabelGenerator(Provincia::getNombre);
		    	cbLocalidad.setEnabled(true);
		    }
		});
        add(cbProvincia);
        
        cbProvincia.addValueChangeListener(event -> {
        	 if (event.getValue() != null) {
 		    	cbLocalidad.setItems(repoLocalidad.findByprovincia(event.getValue()));
 		    	cbLocalidad.setItemLabelGenerator(Localidad::getNombre);
 		    	cbLocalidad.setEnabled(true);
 		    }
        });
        add(cbLocalidad);
        
        cbLocalidad.addValueChangeListener(event -> {
       	 if (event.getValue() != null) {
		    	cbCentro.setItems(repoCentro.findByLocalidad(event.getValue()));
		    	cbCentro.setItemLabelGenerator(Centro::getNombre);
		    	cbCentro.setEnabled(true);
		    }
       });
       add(cbCentro);
       cbCentro.addValueChangeListener(event -> {
         	 if (event.getValue() != null) {
  		    	cbEspecialidad.setItems(event.getValue().getEspecialidad());
  		    	cbEspecialidad.setItemLabelGenerator(Especialidad::getNombre);
  		    	cbEspecialidad.setEnabled(true);
  		    	cbEspecialidad.addValueChangeListener(event1 ->{
  		    		List<Trabajador> lTrabajadores = event.getValue().getTrabajadores();
  		    		for(Trabajador trabajador : lTrabajadores) {
  		    			
  		    		}
  		    	
  		    		cbSanitario.setItems();
  		    	});
  		    }
         });
       add(cbEspecialidad);
       
	}
}
