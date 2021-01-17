package org.vaadin.paul.spring.ui.views;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Especialidad;
import org.vaadin.paul.spring.entities.Trabajador;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.CentroRepository;
import org.vaadin.paul.spring.repositories.TrabajadorRepository;
import org.vaadin.paul.spring.repositories.UserRepository;

@Route("h")
public class crudCentro extends VerticalLayout {

	
	private CentroRepository repoCentro;
	private TrabajadorRepository repoTrabajador;
	private UserRepository repoUser;
	private Grid<Centro> gCentro;
	
	public crudCentro(CentroRepository repoCentro, TrabajadorRepository repoTrabajador, UserRepository repoUser) {
		gCentro = new Grid<>();
		gCentro.addColumn(Centro::getNombre, "Nombre" + " ").setHeader("Nombre");
		gCentro.addColumn(Centro::getTelefono, "Telefono" + " ").setHeader("Telefono");
		
		gCentro.addColumn(new ComponentRenderer<>(centro -> {
			Button especialidadButton = new Button("Especialidades"); 
			
			especialidadButton.addClickListener(event -> { 
				Grid<Especialidad> gEspecialidad = new Grid<>();
				
				Dialog dialog = new Dialog();
				dialog.add( gEspecialidad, new Button("Close", e -> dialog.close()) ); 
				dialog.setModal(true);
				dialog.setDraggable(true); 
				dialog.setResizable(false);
				dialog.setWidth("1200px"); 
				dialog.setHeight("1000px");
				
				gEspecialidad.setItems(centro.getEspecialidad());
				
				gEspecialidad.addColumn(Especialidad::getNombre).setHeader("Nombre Especialidad");
				
				gEspecialidad.addColumn(new ComponentRenderer<>(especialidad -> {
					Button deleteButton = new Button("Eliminar"); 
					deleteButton.addClickListener(delete -> { 
						List<Especialidad> lEspecialidad = centro.getEspecialidad();
						lEspecialidad.remove(especialidad);
						centro.setEspecialidad(lEspecialidad);
						repoCentro.save(centro);
						gEspecialidad.setItems(centro.getEspecialidad());
				 	}); 
					return deleteButton;	
				}));
				dialog.open();
		}); 
			return especialidadButton;	
		}));
		
		gCentro.addColumn(new ComponentRenderer<>(centro1 -> {
			Button trabajadorButton = new Button("Trabajadores"); 
			
			
			trabajadorButton.addClickListener(event -> {
				Grid<User> gUser = new Grid<>();
				Dialog dialog = new Dialog();
				dialog.add( gUser, new Button("Close", e -> dialog.close()) ); 
				dialog.setModal(true);
				dialog.setDraggable(true); 
				dialog.setResizable(false);
				dialog.setWidth("1200px"); 
				dialog.setHeight("1000px");
				List<Trabajador> lTrabajador = centro1.getTrabajadores();
				List<User> lUser = new ArrayList<User>();
				
				for(Trabajador trabajador : lTrabajador) {
					lUser.add(trabajador.getUser());
				}
				gUser.setItems(lUser);
				
				gUser.addColumn(User::getNombre).setHeader("Nombre Trabajador");
				gUser.addColumn(new ComponentRenderer<>(user -> {
					
					Button deleteButtonTrabajador = new Button("Eliminar"); 
					deleteButtonTrabajador.addClickListener(delete -> {
						Iterator<Trabajador> itr = lTrabajador.iterator();
						
						while (itr.hasNext()) {
							Trabajador t = itr.next();
							if(t.getUser().equals(user)) {
								itr.remove();
							}
						}
					
						centro1.setTrabajadores(lTrabajador);						
						repoCentro.save(centro1);
						lUser.remove(user);
						gUser.setItems(lUser);
				 	}); 
					return deleteButtonTrabajador;
				}));
				
				dialog.open();
				
			}); 
			return trabajadorButton;
			
		}));
		gCentro.addColumn(new ComponentRenderer<>(centro -> {
			Button addTrabajadorButton = new Button("Añadir Trabajador"); 
			
			addTrabajadorButton.addClickListener(event -> { 
				Grid<User> gUser = new Grid<>();
				
				Dialog dialog = new Dialog();
				dialog.add( gUser, new Button("Close", e -> dialog.close()) ); 
				dialog.setModal(true);
				dialog.setDraggable(true); 
				dialog.setResizable(false);
				dialog.setWidth("1200px"); 
				dialog.setHeight("1000px");
				
				gUser.setItems(repoUser.findByRoles(2));
				
				gUser.addColumn(User::getNombre).setHeader("Nombre del médico");
				
				gUser.addColumn(new ComponentRenderer<>(user -> {
					Button addButton = new Button("Añadir"); 
					addButton.addClickListener(añadir -> { 
						List<Trabajador> lTrabajador = centro.getTrabajadores();
						lTrabajador.add(repoTrabajador.findByuser(user));
						centro.setTrabajadores(lTrabajador);
						repoCentro.save(centro);
				 	}); 
					return addButton;	
				}));
				dialog.open();
		}); 
			return addTrabajadorButton;	
		}));
		gCentro.setItems(repoCentro.findAll());
		add(gCentro);
	}
}
