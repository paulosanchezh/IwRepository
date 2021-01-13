package org.vaadin.paul.spring.ui.views;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.repositories.CentroRepository;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Labels;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.PlotOptionsColumn;
import com.vaadin.flow.component.charts.model.SeriesTooltip;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.paul.spring.MainView;
import org.vaadin.paul.spring.entities.Centro;
import org.vaadin.paul.spring.entities.User;

@Route(value = "PruebaEstadistica", layout=MainView.class)
@Secured({"ROLE_ADMIN", "ROLE_GESTOR"})

public class PruebaEstadistica extends VerticalLayout{
	
	CentroRepository centro_repo;
	Chart chart;
	ListSeries series;
	Configuration conf;
	
	public PruebaEstadistica(CentroRepository centro_repo) {
		List<Centro> lCentro = centro_repo.findAll();
		
		chart = new Chart();
		conf = chart.getConfiguration();
		conf.setTitle("Numero de usuarios en cada centro");
		XAxis x = new XAxis();
		
		YAxis y = new YAxis();
        Labels yLabels = new Labels();
        yLabels.setFormat("{value}");
        y.setLabels(yLabels);
        y.setTitle("Personas");
        conf.addyAxis(y);
        
        series = new ListSeries();
        
		for(Centro centro : lCentro) {
			List<User> user = centro.getUsuarios();
			x.addCategory(centro.getNombre());
			series.addData(user.size());
		}
		conf.addxAxis(x);
		
		PlotOptionsColumn rainfallOptions = new PlotOptionsColumn();
        SeriesTooltip rainfallTooltip = new SeriesTooltip();
        rainfallTooltip.setPointFormat(
                "<span style=\"font-weight: bold; color: {series.color}\">" +
                    "{series.name}</span>: <b>{point.y:.1f} mm</b> ");
        rainfallOptions.setTooltip(rainfallTooltip);
        series.setPlotOptions(rainfallOptions);
        series.setName("Centros");
        SeriesTooltip errorTooltip = new SeriesTooltip();
        
        conf.addSeries(series);
        
		
		conf.setSubTitle("");
		add(chart);
	}
}
