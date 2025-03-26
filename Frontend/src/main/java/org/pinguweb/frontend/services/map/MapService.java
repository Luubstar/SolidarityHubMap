package org.pinguweb.frontend.services.map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.pinguweb.frontend.objects.Zone;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.Tuple;
import software.xdev.vaadin.maps.leaflet.basictypes.LIcon;
import software.xdev.vaadin.maps.leaflet.basictypes.LIconOptions;
import org.springframework.web.client.RestTemplate;
import software.xdev.vaadin.maps.leaflet.basictypes.LLatLng;
import software.xdev.vaadin.maps.leaflet.basictypes.LPoint;
import software.xdev.vaadin.maps.leaflet.layer.ui.LMarker;
import software.xdev.vaadin.maps.leaflet.layer.ui.LMarkerOptions;
import software.xdev.vaadin.maps.leaflet.layer.vector.LPolygon;
import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.registry.LComponentManagementRegistry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapService {
    @Setter
    private LComponentManagementRegistry reg;

    @Setter
    private LMap map;

    @Setter
    @Getter
    private boolean zone = false;

    @Setter
    private String ID;

    public MapService() {
        load();
    }

    public void load() {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject("http://localhost:8081/api/task", String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Task> tasks = objectMapper.readValue(jsonResponse, new TypeReference<>(){});

            System.out.println(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        jsonResponse = restTemplate.getForObject("http://localhost:8081/api/zone", String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Zone> zones = objectMapper.readValue(jsonResponse, new TypeReference<>(){});

            System.out.println(zones);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    public void createTask(double lat, double lng) {
        LLatLng coords = new LLatLng(this.reg, lat, lng);

        new LMarker(reg, coords).addTo(map);
        UI.getCurrent();
    }

    public void createZone(List<Tuple<Double, Double>> markers) {

        List<LLatLng> points = new ArrayList<>();

        for (Tuple<Double, Double> marker : markers) {
            points.add(new LLatLng(this.reg, marker._1(), marker._2()));
        }

        new LPolygon(reg, points).addTo(map);
        points.clear();
    }

    public LMarker createZoneMarker(double lat, double lng) {
        LIcon icon = new LIcon(this.reg, new LIconOptions()
                .withIconUrl("https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png")
                .withShadowUrl("https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png")
                .withIconSize(new LPoint(this.reg, 25, 41))
                .withIconAnchor(new LPoint(this.reg, 12, 41))
                .withPopupAnchor(new LPoint(this.reg, 1, -34))
                .withShadowSize(new LPoint(this.reg, 41, 41))
        );

        LMarkerOptions options = new LMarkerOptions().withDraggable(true).withIcon(icon);

        LMarker marker = new LMarker(this.reg, new LLatLng(this.reg, lat, lng), options);

        marker.on("dragstart", "e => document.getElementById('" + ID + "').$server.zoneMarkerStart(e.target.getLatLng())");
        marker.on("dragend", "e => document.getElementById('" + ID + "').$server.zoneMarkerEnd(e.target.getLatLng())");

        marker.addTo(this.map);
        return marker;
    }


    public void createDialogZona() {
        final Icon icoClose = VaadinIcon.CLOSE.create();
        final Dialog dialog = new Dialog(icoClose);
        dialog.setDraggable(true);
        dialog.setResizable(true);
        dialog.setWidth("70vw");
        dialog.setHeight("70vh");

        H3 title = new H3("Crear zona");

        ComboBox<String> severityComboBox = new ComboBox<>("Gravedad");
        severityComboBox.setItems("Baja", "Media", "Alta");

        TextArea descriptionTextArea = new TextArea();
        descriptionTextArea.setPlaceholder("descripcion");
        descriptionTextArea.setWidthFull();
        descriptionTextArea.setHeight("50vh");

        Button cancelButton = new Button("Cancelar", event -> dialog.close());
        Button acceptButton = new Button("Aceptar", event -> {dialog.close();});

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, acceptButton);

        VerticalLayout dialogLayout = new VerticalLayout(title, severityComboBox, descriptionTextArea, buttonLayout);
        dialog.add(dialogLayout);

        dialog.open();

        icoClose.addClickListener(iev -> dialog.close());
    }

    public void crearDialogoTarea() {
        final Icon icoClose = VaadinIcon.CLOSE.create();
        final Dialog dialog = new Dialog(icoClose);
        dialog.setDraggable(true);
        dialog.setResizable(true);
        dialog.setWidth("70vw");
        dialog.setHeight("70vh");

        H3 title = new H3("Crear tarea");

        ComboBox<String> severityComboBox = new ComboBox<>("Tipo");
        severityComboBox.setItems("Mantenimiento", "Reparación", "Limpieza");

        TextArea descriptionTextArea = new TextArea();
        descriptionTextArea.setPlaceholder("descripcion");
        descriptionTextArea.setWidthFull();
        descriptionTextArea.setHeight("50vh");

        Button cancelButton = new Button("Cancelar", event -> dialog.close());
        Button acceptButton = new Button("Aceptar", event -> {dialog.close();});

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, acceptButton);

        VerticalLayout dialogLayout = new VerticalLayout(title, severityComboBox, descriptionTextArea, buttonLayout);
        dialog.add(dialogLayout);

        dialog.open();

        icoClose.addClickListener(iev -> dialog.close());
    }
}
