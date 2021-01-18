package org.vaadin.paul.spring.ui.views;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.Especialidad;
import org.vaadin.paul.spring.entities.Rol;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.UserRepository;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "cruduser", layout = MainView.class)
@PageTitle("Gestión de Usuarios")
@Secured({"ROLE_ADMIN"})

public class crudUser extends VerticalLayout{
	
	private UserRepository repoUser;
	
	private Grid<User> gUser;
	
	public crudUser(UserRepository repoUser) {
		gUser = new Grid<>();
		gUser.addColumn(User::getNombre, "Nombre" + " ").setHeader("Nombre");
		gUser.addColumn(User::getApellidos, "Apellidos" + " ").setHeader("Apellidos");
		gUser.addColumn(User::getDireccion, "Dirección" + " ").setHeader("Dirección");
		gUser.addColumn(User::getDni, "DNI" + " ").setHeader("DNI");
		gUser.addColumn(User::getTlf, "Teléfono" + " ").setHeader("Teléfono");
		gUser.addColumn(User::getUsername, "Username" + " ").setHeader("Username");
		gUser.addColumn(User::getPassword, "Contraseña" + " ").setHeader("Contraseña");
		gUser.setItems(repoUser.findAll());
		add(gUser);
		
//		gUser.addColumn(new ComponentRenderer<>(userDelete -> {
//			Button deleteButton = new Button("Eliminar"); 
//			deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
//			deleteButton.addClickListener(delete -> { 
//				repoUser.delete(userDelete);
//				gUser.setItems(repoUser.findAll());
//		 	}); 
//			return deleteButton;	
//		}));
		gUser.addColumn(new ComponentRenderer<>(usuario -> {
			Button editButton = new Button("Editar"); 
			editButton.addClickListener(delete -> { 
				TextField apellidos = new TextField("Apellidos");
			    TextField nombre = new TextField("Nombre");
			    TextField dni = new TextField("DNI");
			    TextField direccion = new TextField("Dirección");
			    TextField tlf = new TextField("Teléfono"); 
			    TextField username = new TextField("Nombre de usuario");
			    
			    Binder<User> binder = new Binder<>(User.class);
		        
		        binder.forField(apellidos)
		        	.asRequired("Apellidos no puede estar vacío")
		        	.bind(User::getApellidos, User::setApellidos);
		        
		        binder.forField(nombre)
		        	.asRequired("El nombre no puede estar vacío")
		        	.bind(User::getNombre, User::setNombre);
		        
		        dni.setLabel("DNI (sin letra)");
		        binder.forField(dni)
		    		.asRequired("DNI no puede estar vacío")
		    		.withValidator(dn -> dn.length() == 8, "El formato del Dni no es correcto, El dni consta de 8 dígitos")
		    		.withValidator(dn -> StringUtils.isNumeric(dn) == true, "El DNI sólo debe contener caracteres numéricos")
		    		.withValidator(dn -> repoUser.findByDniEdit(dn, usuario.getId()) == null, "Este DNI ya existe, compruebe que ha sido introducido correctamente")
		    		.bind(User::getDni, User::setDni);
		        
		        binder.forField(direccion)
					.asRequired("La direccion no puede estar vacía")
					.bind(User::getDireccion, User::setDireccion);
		        
		        binder.forField(tlf)
		    		.asRequired("El teléfono no puede estar vacío")
		    		.withValidator(telefono -> telefono.length() == 9, "El formato del Teléfono no es correcto, El Teléfono consta de 9 dígitos")
		    		.withValidator(telefono -> StringUtils.isNumeric(telefono) == true, "El Teléfono sólo debe contener carácteres numéricos")
		    		.bind(User::getTlf, User::setTlf);
		        
		        if(usuario.getRol().getId() == 1) {
		        	binder.forField(username)
		    			.asRequired("El nombre de usuario no puede estar vacío")
		    			.withValidator(usern -> usern.equals(usuario.getDni()), "Es un paciente, el paciente debe tener username igual a DNI")
		    			.bind(User::getUsername, User::setUsername);
		        }
		        else {
		        	binder.forField(username)
	    			.asRequired("El nombre de usuario no puede estar vacío")
	    			.withValidator(us -> repoUser.findByUsernameEdit(us, usuario.getId()) == null, "Ya existe otro usuario con mismo username")
	    			.bind(User::getUsername, User::setUsername);
		        }
		        
		        Label validationStatus = new Label();
		        binder.setStatusLabel(validationStatus);
		        
		        binder.setBean(usuario);
		        usuario.setBaja(false);
		        usuario.setUsername(usuario.getDni());
				
				Dialog dialog = new Dialog();
				
				Button close = new Button("editar");
				close.addClickListener(event3 -> { 
			        	if (binder.validate().isOk()) {
			        		repoUser.save(usuario);
			        		gUser.setItems(repoUser.findAll());
			        		dialog.close();
			        	}
				});
				 
				dialog.open();
				dialog.add(nombre,apellidos,direccion,tlf,dni,username,close);
				dialog.setModal(true);
				dialog.setDraggable(true); 
				dialog.setResizable(false);
				dialog.setWidth("550px"); 
				dialog.setHeight("350px");	
		 	}); 
			return editButton;	
		}));
		Button addUser = new Button("Añadir Usuario");
		addUser.addClickListener(create -> { 
			UI.getCurrent().navigate("adduser");
	 	}); 
		add(addUser);
		
	}
}
