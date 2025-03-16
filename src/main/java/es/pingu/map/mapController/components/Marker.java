package es.pingu.map.mapController.components;

import com.vaadin.flow.component.button.Button;
import es.pingu.map.MapaFin;
import software.xdev.vaadin.maps.leaflet.basictypes.LLatLng;
import software.xdev.vaadin.maps.leaflet.layer.raster.LTileLayer;
import software.xdev.vaadin.maps.leaflet.layer.ui.LMarker;
import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.registry.LDefaultComponentManagementRegistry;

public class Marker {
    public static void createMarker(LDefaultComponentManagementRegistry reg, LLatLng coordinates, String text , LMap map) {
        new LMarker(reg,coordinates).bindPopup(text).addTo(map);
    }

    public static void createMarker(LDefaultComponentManagementRegistry reg, String text, LMap map) {
        LLatLng coordinates = MapaFin.getCoordinates();
        System.out.println(coordinates);
        createMarker(reg, coordinates, text, map);
    }

    public static Button getMarkerButton(LDefaultComponentManagementRegistry reg, String text, LMap map) {
        return new Button("Add Marker", ev -> createMarker(reg, text , map));
    }
}
