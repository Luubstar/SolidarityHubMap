package es.pingu.map.mapController.elements;

import es.pingu.map.mapController.MapElement;

public class ZonaAfectada extends MapElement {
    public ZonaAfectada(double lat, double lon, String description) {
        super(lat, lon, description);
    }

    public static MapElement createNewElement(double lat, double lon, String description) {
        return new Refugio(lat, lon, description);
    }
}
