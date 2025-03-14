package es.pingu.map.mapController;

import es.pingu.map.mapController.elements.ElementTypes;
import es.pingu.map.mapController.elements.Refugio;
import es.pingu.map.mapController.elements.ZonaAfectada;

public class MapElementFactory {
    public static MapElement crearElemento(ElementTypes type, double lat, double lon, String description) {
        return switch (type) {
            case AFECTADA -> ZonaAfectada.createNewElement(lat, lon, description);
            case REFUGIO -> Refugio.createNewElement(lat, lon, description);
        };
    }
}
