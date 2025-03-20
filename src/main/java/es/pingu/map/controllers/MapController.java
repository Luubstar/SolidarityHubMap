package es.pingu.map.controllers;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import es.pingu.map.views.MapView;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import software.xdev.vaadin.maps.leaflet.basictypes.LLatLng;
import software.xdev.vaadin.maps.leaflet.layer.raster.LTileLayer;
import software.xdev.vaadin.maps.leaflet.layer.ui.LMarker;
import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.map.LMapLocateOptions;
import software.xdev.vaadin.maps.leaflet.registry.LComponentManagementRegistry;

import java.util.ArrayList;

public class MapController {
    private LComponentManagementRegistry reg;
    private LMap map;
    private LLatLng coords;
    private ArrayList<LLatLng> points;



    public MapController(@NotNull LComponentManagementRegistry reg, @NonNull LMap map) {
        this.reg = reg;
        this.map = map;
    }


    // TODO: Texto para el el marcador de tarea
    public void createTask(){
        // TODO: ¿Modificar el mapa? realmente no tengo ni idea
        // TODO: ¿Cambiar cursor?
        System.out.println("Task created at: " + coords);
        new LMarker(reg,coords).addTo(map);
        UI.getCurrent();

    }

    public void createZone(){
        // TODO: ¿Modificar el mapa? realmente no tengo ni idea
        // TODO: ¿Cambiar cursor?
    }

    public void setCoords(double lat, double lng) {
        this.coords = new LLatLng(reg, lat, lng);
    }


}