package org.pinguweb.frontend.view.Dashboard;

import com.storedobject.chart.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.pinguweb.DTO.AffectedDTO;
import org.pinguweb.DTO.VolunteerDTO;
import org.pinguweb.frontend.services.backend.BackendObject;
import org.pinguweb.frontend.services.backend.BackendService;
import org.pinguweb.frontend.view.NavigationBar;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;

import java.util.List;

@Route("dashboard/users-affected")
public class Users extends HorizontalLayout {

    BackendObject<List<VolunteerDTO>> volunteer = BackendService.getListFromBackend(BackendService.BACKEND + "/api/volunteer",
            new ParameterizedTypeReference<List<VolunteerDTO>>() {
            });
    BackendObject<List<AffectedDTO>> affected = BackendService.getListFromBackend(BackendService.BACKEND + "/api/affected",
            new ParameterizedTypeReference<List<AffectedDTO>>() {
            });


    public Users() {
        this.setSizeFull();
        VerticalLayout navBarLayout = new VerticalLayout();
        navBarLayout.setWidth("250px");
        navBarLayout.setHeightFull();
        navBarLayout.add(NavigationBar.createNavBar());

        VerticalLayout chartsContainer = new VerticalLayout();
        chartsContainer.setSizeFull();


        HorizontalLayout chartLayout = new HorizontalLayout();
        chartLayout.setWidthFull();
        chartLayout.setAlignItems(Alignment.CENTER);
        chartLayout.setJustifyContentMode(JustifyContentMode.CENTER);


        chartLayout.add(createPieChart( 10,5), createBarChart(10,5), createLineChart(10,5));
        chartsContainer.add(chartLayout);
        this.add(navBarLayout, chartsContainer);
    }
  
    public SOChart createBarChart(int VolunteerData, int AffectedData) {
        SOChart barChart = new SOChart();
        barChart.setSize("400px", "400px");

        RectangularCoordinate rc = new RectangularCoordinate(new XAxis(DataType.CATEGORY), new YAxis(DataType.NUMBER));

        String[] labels = {"Volunteer", "Affected"};
        int[] data = {VolunteerData, AffectedData};

        for (int i = 0; i < labels.length; i++) {
            BarChart bar = new BarChart(new CategoryData(labels[i]), new Data(data[i]));
            bar.setName(labels[i]);
            bar.setColors(new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)));
            bar.plotOn(rc);
            barChart.add(bar);
        }

        return barChart;
    }
    public SOChart createPieChart(int  VolunteerData, int AffectedData) {
        SOChart pieChart = new SOChart();
        pieChart.setSize("400px", "400px");

        Data data = new Data(VolunteerData, AffectedData);
        CategoryData labels = new CategoryData("Volunteer", "Affected");
        PieChart pie = new PieChart(labels, data);

        pie.setName("Volunteer vs Affected");

        Legend legend = new Legend();
        pieChart.add(pie, legend);

        return pieChart;
    }

    public SOChart createLineChart(int VolunteerData, int AffectedData) {
        SOChart lineChart = new SOChart();
        lineChart.setSize("400px", "400px");

        Data data = new Data(VolunteerData, AffectedData);
        CategoryData labels = new CategoryData("Volunteer", "Affected");

        LineChart line = new LineChart(labels,data);

        RectangularCoordinate rc = new RectangularCoordinate(new XAxis(DataType.CATEGORY), new YAxis(DataType.NUMBER));
        line.plotOn(rc);
        lineChart.add(line);

        return lineChart;
    }
    public int getVolunteers() {
        int numberOfVolunteers = 0;
        if (volunteer.getStatusCode() == HttpStatus.OK) {
            List<VolunteerDTO> volunteerList = volunteer.getData();
            if (volunteerList != null) {
                numberOfVolunteers = volunteerList.size();
            } else {
                System.out.println("Volunteer list is null");
            }
        } else {
            System.out.println("Failed to fetch volunteers, status code: " + volunteer.getStatusCode());
        }
        return numberOfVolunteers;
    }

    public int getAffected() {
        int numberOfAffected = 0;
        if (affected.getStatusCode() == HttpStatus.OK) {
            List<AffectedDTO> affectedList = affected.getData();
            if (affectedList != null) {
                numberOfAffected = affectedList.size();
            } else {
                System.out.println("Affected list is null");
            }
        } else {
            System.out.println("Failed to fetch affected, status code: " + affected.getStatusCode());
        }
        return numberOfAffected;

    }
    private void updateChartsBasedOnPriority(String priority, HorizontalLayout chartLayout) {
        chartLayout.removeAll();

        switch (priority) {
            case "Low":
                chartLayout.add(createPieChart(1, 2), createBarChart(5, 2), createLineChart(5, 2));
                break;
            case "Medium":
                chartLayout.add(createPieChart(2, 1), createBarChart(2, 1), createLineChart(2, 1));
                break;
            case "High":
                chartLayout.add(createPieChart(1,1), createBarChart(1,1), createLineChart(1,1));
                break;
            default:
                chartLayout.add(createPieChart(1,1), createBarChart(1,1), createLineChart(1,1));
                break;
        }
    }

}



