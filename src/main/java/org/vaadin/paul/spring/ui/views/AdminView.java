package org.vaadin.paul.spring.ui.views;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route("admin")
@Secured("ROLE_ADMIN")
public class AdminView extends VerticalLayout {
    @Autowired
    public AdminView() {
        Label label = new Label("Looks like you are admin!");
        add(label);
    }

}
