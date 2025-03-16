package es.pingu.map.mapController.components;

import com.vaadin.flow.component.button.Button;
import software.xdev.vaadin.maps.leaflet.basictypes.LLatLng;
import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.map.LMapLocateOptions;

public class UserLocation {



    public static Button getLocationButton(LMap map)
    {
        /*map.on(
                "locationerror",
                "e => alert('Failed to locate: ' "
                        + "+ '\\nCode: ' + e.code "
                        + "+ '\\nMessage: ' + e.message"
                        + ")");
        map.on(
                "locationfound",
                "e => alert('Location successful: '"
                        + "+ '\\nLocation: ' + e.latlng "
                        + "+ '\\nBounds: ' + e.bounds?.getNorthWest() + ' ' + e.bounds?.getSouthEast() "
                        + "+ '\\nAccuracy(m): ' + e.accuracy "
                        + "+ '\\nAltitude(m): ' + e.altitude "
                        + "+ '\\nAltitudeAccuracy(m): ' + e.altitudeAccuracy "
                        + "+ '\\nHeading: ' + e.heading"
                        + "+ '\\nSpeed: ' + e.speed "
                        + ")");

         */
        return new Button("Locate", ev -> map.locate(new LMapLocateOptions().withSetView(true)));
    }





}
