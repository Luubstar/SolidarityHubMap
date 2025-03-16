package es.pingu.map;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import es.pingu.map.mapController.components.Marker;
import es.pingu.map.mapController.components.UserLocation;
import jdk.dynalink.beans.StaticClass;
import lombok.Getter;
import software.xdev.vaadin.maps.leaflet.MapContainer;
import software.xdev.vaadin.maps.leaflet.basictypes.LLatLng;
import software.xdev.vaadin.maps.leaflet.layer.raster.LTileLayer;
import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.map.LMapLocateOptions;
import software.xdev.vaadin.maps.leaflet.registry.LDefaultComponentManagementRegistry;

@Route(MapaFin.NAV)
public class MapaFin extends VerticalLayout{

    public static final String NAV = "mapa";
    private static final String ID = "mapa";

    private final LDefaultComponentManagementRegistry reg;
    private final LMap map;
    private final HorizontalLayout hlButtons = new HorizontalLayout();

    @Getter
    private static LLatLng coordinates;

    public MapaFin() {
        this.setId(ID);

        this.setSizeFull();

        this.reg = new LDefaultComponentManagementRegistry(this);
        final MapContainer mapContainer = new MapContainer(this.reg);
        mapContainer.setSizeFull();
        this.add(mapContainer);
        this.setSizeFull();

        this.map = mapContainer.getlMap();
        this.map.addLayer(LTileLayer.createDefaultForOpenStreetMapTileServer(this.reg));
        this.map.locate(new LMapLocateOptions().withSetView(true));
        addEventDemo();



        //Marker.createMarker(this.reg,this.map,








        Button locationButton = UserLocation.getLocationButton(this.map);
        Button markerButton = Marker.getMarkerButton(this.reg, "Marker", this.map);

        this.hlButtons.add(locationButton);
        this.hlButtons.add(markerButton);




        this.add(this.hlButtons);




    }

    public void setCoordinates(double lat, double lng) {
        coordinates = new LLatLng(this.reg, lat, lng);

    }

    public void setCoordinates(LLatLng latLng) {
        coordinates = latLng;
    }


    public void addEventDemo()
    {
        // See also https://vaadin.com/docs/latest/create-ui/element-api/client-server-rpc
        this.map.on("click", "e => document.getElementById('" + ID + "').$server.mapClicked(e.latlng)");
    }

    // This server side method will be called when the map is clicked
    @ClientCallable
    public void mapClicked(final JsonValue input)
    {
        if(!(input instanceof final JsonObject obj))
        {
            return;
        }

        System.out.println("Map clicked at: " + obj.getNumber("lat") + ", " + obj.getNumber("lng"));
        setCoordinates(obj.getNumber("lat"), obj.getNumber("lng"));
    }



}
