package es.pingu.map.controllers;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.Setter;
import software.xdev.vaadin.maps.leaflet.basictypes.LLatLng;
import software.xdev.vaadin.maps.leaflet.layer.raster.LTileLayer;
import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.registry.LComponentManagementRegistry;

public class MapController {
    private LComponentManagementRegistry reg;
    private LMap map;

    private boolean creatingZone;
    private boolean creatingTask;

    public MapController(@NotNull LComponentManagementRegistry reg, @NonNull LMap map, double lat, double lon) {
        this.reg = reg;
        this.map = map;

        this.map.addLayer(LTileLayer.createDefaultForOpenStreetMapTileServer(this.reg));
        this.map.setView(new LLatLng(reg, lat, lon), 13);
        this.SetMapEvent();
    }

    private void SetMapEvent(){
        this.map.on(
                "click",
                "e => alert('Click en Lat -> ' + e.latlng.lat + ', Lon -> ' + e.latlng.lng)");
    }

    public void createTask(){
        creatingZone = false;
        creatingTask = true;
        // TODO: ¿Modificar el mapa? realmente no tengo ni idea
    }

    public void createZone(){
        creatingTask = false;
        creatingZone = true;


        // TODO: ¿Modificar el mapa? realmente no tengo ni idea
    }
}
