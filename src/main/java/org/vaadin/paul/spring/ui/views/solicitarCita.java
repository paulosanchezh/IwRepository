package org.vaadin.paul.spring.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.paul.spring.app.security.SecurityUtils;
import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Comunidad;
import org.vaadin.paul.spring.entities.Especialidad;
import org.vaadin.paul.spring.entities.Localidad;
import org.vaadin.paul.spring.entities.Provincia;
import org.vaadin.paul.spring.entities.Rol;
import org.vaadin.paul.spring.entities.Sanitario;
import org.vaadin.paul.spring.entities.Trabajador;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.CentroRepository;
import org.vaadin.paul.spring.repositories.CitaRepository;
import org.vaadin.paul.spring.repositories.ComunidadRepository;
import org.vaadin.paul.spring.repositories.LocalidadRepository;
import org.vaadin.paul.spring.repositories.ProvinciaRepository;
import org.vaadin.paul.spring.repositories.SanitarioRepository;
import org.vaadin.paul.spring.repositories.TrabajadorRepository;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route("cita")
public class solicitarCita extends VerticalLayout{

	private ComboBox<Especialidad> cbEspecialidad = new ComboBox<>();
	private ComboBox<Comunidad> cbComunidad = new ComboBox<>();	
	private ComboBox<Provincia> cbProvincia = new ComboBox<>();	
	private ComboBox<Localidad> cbLocalidad = new ComboBox<>();
	private ComboBox<Centro> cbCentro = new ComboBox<>();
	private ComboBox<User> cbUsuario = new ComboBox<>();
	
	private ComunidadRepository repoComunidad;
	private ProvinciaRepository repoProvincia;
	private LocalidadRepository repoLocalidad;
	private CentroRepository repoCentro;
	private SanitarioRepository repoSanitario;
	private TrabajadorRepository repoTrabajador;
	private CitaRepository repoCita;
	
	private DatePicker fecha = new DatePicker("Fecha cita");
	private TimePicker hora = new TimePicker("Hora cita");
	
	private Button saveButton = new Button("Save");;
	
	public solicitarCita(ComunidadRepository repoComunidad, ProvinciaRepository repoProvincia,
			LocalidadRepository repoLocalidad, CentroRepository repoCentro, SanitarioRepository repoSanitario,
			TrabajadorRepository repoTrabajador, CitaRepository repoCita) {
		
		Cita cita = new Cita();
		Binder<Cita> binder = new Binder<>(Cita.class);
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
  		    		List<User> lUser = new ArrayList<User>();
  		    		Sanitario sanitario;
  		    		for(Trabajador trabajador : lTrabajadores) {
  		    			sanitario = repoSanitario.findByTrabajador(trabajador);
  		    			if (sanitario.getEspecialidad().getNombre().equals(event1.getValue().getNombre())) {
  		    				lUser.add(trabajador.getusuario());
  		    			}	
  		    		}
  		    		cbUsuario.setItems(lUser);
  		    		cbUsuario.setItemLabelGenerator(User::getNombre);
  		    		cbUsuario.setEnabled(true);
  		    	});
  		    }
         });
       
       cbUsuario.addValueChangeListener(event -> {
    	   Trabajador trabajador = repoTrabajador.findByUser(event.getValue());
    	   cita.setSanitario(repoSanitario.findByTrabajador(trabajador));
       });
       
       add(cbEspecialidad);
       add(cbUsuario);
       
       
       binder.forField(fecha)
   			.asRequired("Debes de selecionar una fecha")
   			.bind(Cita::getFecha, Cita::setFecha);
       add(fecha);
       
       binder.forField(hora)
			.asRequired("Debes de selecionar una hora")
			.bind(Cita::getHora, Cita::setHora);
       add(hora);
       
       cita.setPaciente((User) SecurityUtils.getAuthenticatedUser());
       cita.setConfirmada(false);
       
       binder.setBean(cita);
       
       saveButton.addClickListener(event -> { 
       	if (binder.validate().isOk()) {
       		Notification.show("La cita ha sido creada");
       		repoCita.save(cita);
       		//UI.getCurrent().navigate("coger-cita");
       	}
		});
       add(saveButton);
       
       
	}
}
