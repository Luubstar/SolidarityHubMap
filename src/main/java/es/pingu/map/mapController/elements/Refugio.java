package es.pingu.map.mapController.elements;

import es.pingu.map.mapController.MapElement;

public class Refugio extends MapElement {
    public Refugio(double lat, double lon, String description) {
        super(lat, lon, description);
    }

    public static MapElement createNewElement(double lat, double lon, String description) {
        return new Refugio(lat, lon, description);
    }

}
