package org.vaadin.paul.spring.ui.views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.app.security.SecurityUtils;
import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Rol;
import org.vaadin.paul.spring.entities.Sanitario;
import org.vaadin.paul.spring.entities.User;
import org.vaadin.paul.spring.repositories.RolRepository;
import org.vaadin.paul.spring.repositories.UserRepository;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "createuser", layout=MainView.class)
@PageTitle("crear usuario")
@Secured({"ROLE_SANITARIO", "ROLE_ADMIN", "ROLE_USER"})

public class createUser extends FormLayout {
	RolRepository repoRol;
	UserRepository repoUser;
	
	Button saveButton = new Button("Save");;
	TextField apellidos = new TextField("Apellidos");
    TextField nombre = new TextField("Nombre");
    TextField dni = new TextField("DNI");
    TextField direccion = new TextField("Dirección");
    TextField tlf = new TextField("Teléfono"); 
    TextField username = new TextField("Nombre de usuario");
    
    PasswordField passwordField = new PasswordField("Password");
    PasswordField confirmPasswordField = new PasswordField("Confirm Password");
    User usuario = new User();
    
    
	
	public createUser(UserRepository repoUser, RolRepository repoRol) {
        
        Binder<User> binder = new Binder<>(User.class);
        
        ComboBox<Rol> cRol = new ComboBox<>();
        cRol.setItems(repoRol.findAll());
        cRol.setItemLabelGenerator(Rol::getNombre);
        
        
        binder.forField(apellidos)
        	.asRequired("Apellidos no puede estar vacío")
        	.bind(User::getApellidos, User::setApellidos);
        
        binder.forField(nombre)
        	.asRequired("El nombre no puede estar vacío")
        	.bind(User::getNombre, User::setNombre);
        
        binder.forField(dni)
    		.asRequired("dni no puede estar vacío")
    		.bind(User::getDni, User::setDni);
        
        binder.forField(direccion)
			.asRequired("La direccion no puede estar vacío")
			.bind(User::getDireccion, User::setDireccion);
        
        binder.forField(tlf)
    		.asRequired("El teléfono nombre no puede estar vacío")
    		.bind(User::getTlf, User::setTlf);
        
        binder.forField(username)
    		.asRequired("El nombre de usuario no puede estar vacío")
    		.bind(User::getUsername, User::setUsername);
        
        binder.forField(passwordField)
			.asRequired("La contraseña no puede estar vacía")
			.bind(User::getPassword, User::setPassword );
        
//        binder.forField(confirmPasswordField)
//			.asRequired("Debes confirmar la contraseña")
//			.bind(User::getPassword, (person, password) -> {} );
        
        binder.forField(cRol)
        	.asRequired("Debes escoger un Rol")
        	.bind(User::getRol, User::setRol);
      

//        binder.withValidator(Validator.from(user -> {
//            if (passwordField.isEmpty() || confirmPasswordField.isEmpty()) {
//                return true;
//            } else {
//                return Objects.equals(passwordField.getValue(),
//                        confirmPasswordField.getValue());
//            }
//        }, "Entered password and confirmation password must match"));
        
//        Label validationStatus = new Label();
//        binder.setStatusLabel(validationStatus);
        
        binder.setBean(usuario);
        usuario.setBaja(false);
        
        saveButton.addClickListener(event -> { 
        	if (binder.validate().isOk()) {
        		Notification.show("El usuario ha sido creado");
        		repoUser.save(usuario);
        		//UI.getCurrent().navigate("coger-cita");
        	}
		}); 
        
        add(nombre);
        add(apellidos);
        add(dni);
        add(direccion);
        add(tlf);
        add(username);
        add(passwordField);
        add(cRol);
        add(saveButton);
        	
	}
}
