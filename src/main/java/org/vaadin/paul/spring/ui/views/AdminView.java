package org.vaadin.paul.spring.ui.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.entities.Cita;
import org.vaadin.paul.spring.entities.Especialidad;
import org.vaadin.paul.spring.entities.Localidad;
import org.vaadin.paul.spring.repositories.CentroRepository;

@Route("admin")
@Secured("ROLE_ADMIN")
public class AdminView extends VerticalLayout {
	private ComboBox<Especialidad> e = new ComboBox<>();
	private CentroRepository cr;
    @Autowired
    public AdminView(CentroRepository cr) {
    	
    }

}
