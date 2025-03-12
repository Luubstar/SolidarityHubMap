package es.pingu.map;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import software.xdev.vaadin.maps.leaflet.MapContainer;
import software.xdev.vaadin.maps.leaflet.basictypes.LLatLng;
import software.xdev.vaadin.maps.leaflet.layer.raster.LTileLayer;
import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.registry.LComponentManagementRegistry;
import software.xdev.vaadin.maps.leaflet.registry.LDefaultComponentManagementRegistry;

@Route(Demo.NAV)
@SuppressWarnings("checkstyle:MagicNumber")
public class Demo extends VerticalLayout {

    public static final String NAV = "leaflet-vaadin-geojson";

    public Demo() {
        // Let the view use 100% of the site
        this.setSizeFull();

        this.add(new Anchor("https://vaadin.com/docs/v14/flow/leaflet-maps/overview", "Documentation"));

        // Create the registry which is needed so that components can be reused and their methods invoked
        final LComponentManagementRegistry reg = new LDefaultComponentManagementRegistry(this);

        // Create and add the MapContainer (which contains the map) to the UI
        final MapContainer mapContainer = new MapContainer(reg);
        mapContainer.setSizeFull();
        this.add(mapContainer);

        final LMap map = mapContainer.getlMap();

        // Add a (default) TileLayer so that we can see something on the map
        map.addLayer(LTileLayer.createDefaultForOpenStreetMapTileServer(reg));

        // Set the view to a default location (Denver for example)
        map.setView(new LLatLng(reg, 39.75621, -104.99404), 17);

        // Add a method to get the user's current location and update the map
        getCurrentLocationAndUpdateMap(map, reg);
    }

    private void getCurrentLocationAndUpdateMap(LMap map, LComponentManagementRegistry reg) {
        // Using JavaScript to access the geolocation API of the browser
        getElement().executeJs("navigator.geolocation.getCurrentPosition(function(position) {"
                + "var latitude = position.coords.latitude;"
                + "var longitude = position.coords.longitude;"
                + "console.log('User position: ', latitude, longitude);"
                + "var latLng = new L.LatLng(latitude, longitude);"
                + "window.vaadin.updateLocation(latLng.lat, latLng.lng);"
                + "}, function(error) {"
                + "console.error('Error getting geolocation', error);"
                + "});");

        // Listen for JavaScript callback and update the map view
        getElement().getNode().runWhenAttached(ui ->
                ui.getPage().addJavaScript("window.vaadin = { updateLocation: function(lat, lng) {"
                        + "    var latLng = new L.LatLng(lat, lng);"
                        + "    var map = document.querySelector('vaadin-map').map; "
                        + "    map.setView(latLng, 17);"
                        + "    L.marker(latLng).addTo(map);"
                        + "}};"));
    }


}
