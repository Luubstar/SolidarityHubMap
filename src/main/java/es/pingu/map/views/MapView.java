package es.pingu.map.views;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.pingu.map.commons.NavigationBar;
import software.xdev.vaadin.maps.leaflet.MapContainer;
import software.xdev.vaadin.maps.leaflet.basictypes.LLatLng;
import software.xdev.vaadin.maps.leaflet.layer.raster.LTileLayer;
import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.registry.LComponentManagementRegistry;
import software.xdev.vaadin.maps.leaflet.registry.LDefaultComponentManagementRegistry;

@Route("map")
@PageTitle("Visor del mapa")
public class MapView extends HorizontalLayout {

    public MapView() {
        this.setSizeFull();

        this.add(NavigationBar.createNavBar());

        final LComponentManagementRegistry reg = new LDefaultComponentManagementRegistry(this);
        final MapContainer mapContainer = new MapContainer(reg);
            mapContainer.setSizeFull();
            this.add(mapContainer);

        final LMap map = mapContainer.getlMap();
        map.addLayer(LTileLayer.createDefaultForOpenStreetMapTileServer(reg));

        map.setView(new LLatLng(reg, 39.75621, -104.99404), 17);
        map.on(
                "click",
                "e => alert('Failed to locate: ' "
                        + ")");
    }
}
