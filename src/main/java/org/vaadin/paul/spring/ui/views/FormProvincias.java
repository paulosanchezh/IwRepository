package org.vaadin.paul.spring.ui.views;

import org.vaadin.paul.spring.entities.Provincia;

import org.vaadin.paul.spring.repositories.ProvinciaRepository;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import org.vaadin.paul.spring.MainView;

@Route(value="provincia", layout=MainView.class)
public class FormProvincias extends FormLayout {
	ProvinciaRepository repo;
	Grid<Provincia> grid;
	
	public FormProvincias(ProvinciaRepository repo) {
		this.repo = repo;
		this.grid = new Grid<>(Provincia.class);
		this.grid.setItems(repo.findById(2));
		add(grid);
	}
	
	void listProvincia(int filterText) {
		grid.setItems(repo.findById(filterText));
	}
	
}
