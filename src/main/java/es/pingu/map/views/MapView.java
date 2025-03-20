package es.pingu.map.views;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import es.pingu.map.commons.NavigationBar;
import es.pingu.map.controllers.MapControllTypes;
import es.pingu.map.controllers.MapController;
import software.xdev.vaadin.maps.leaflet.MapContainer;
import software.xdev.vaadin.maps.leaflet.basictypes.LLatLng;
import software.xdev.vaadin.maps.leaflet.layer.raster.LTileLayer;
import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.map.LMapLocateOptions;
import software.xdev.vaadin.maps.leaflet.registry.LComponentManagementRegistry;
import software.xdev.vaadin.maps.leaflet.registry.LDefaultComponentManagementRegistry;

@Route("map")
@PageTitle("Visor del mapa")
public class MapView extends HorizontalLayout {

    MapController controller;
    private final String ID = "mapa";
    private final LMap map;
    private final Object lock = new Object();
    private UI ui;



    public MapView() {
        this.setId(ID);
        this.setSizeFull();
        this.add(NavigationBar.createNavBar());

        VerticalLayout MapVerticalLayout = new VerticalLayout();
        HorizontalLayout ButtonLayout = new HorizontalLayout();

        this.add(MapVerticalLayout);

        final LComponentManagementRegistry reg = new LDefaultComponentManagementRegistry(this);
        final MapContainer mapContainer = new MapContainer(reg);
        mapContainer.setSizeFull();
        this.map = mapContainer.getlMap();
        this.map.addLayer(LTileLayer.createDefaultForOpenStreetMapTileServer(reg));
        this.map.locate(new LMapLocateOptions().withSetView(true));



        MapVerticalLayout.add(mapContainer);
        MapVerticalLayout.add(ButtonLayout);

        Button tarea = new Button("Tarea");
        Button zona = new Button("Zona");
        ButtonLayout.add(tarea, zona);

        controller = new MapController(reg, map);

        tarea.addClickListener(e -> click(MapControllTypes.TAREA));
        zona.addClickListener(e -> click(MapControllTypes.ZONA));


    }

    public void click(MapControllTypes Action) {
        this.map.once("click", "e => document.getElementById('" + ID + "').$server.mapClicked(e.latlng)");

        ui = UI.getCurrent();

        new Thread(() -> {

            synchronized (lock) {
                try {
                    System.out.println("Esperando clic en el mapa...");
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("Erros esperando clic en el mapa"+e);
                    return;
                }
            }
            if (ui != null) {
                ui.access(() -> {
                    switch (Action) {
                        case TAREA:
                            controller.createTask();
                            break;
                        case ZONA:
                            controller.createZone();
                            break;
                    }
                });
            }
        }).start();
    }

    @ClientCallable
    public void mapClicked(final JsonValue input) {
        if (!(input instanceof final JsonObject obj)) {
            return;
        }

        controller.setCoords(obj.getNumber("lat"), obj.getNumber("lng"));

        synchronized (lock) {
            lock.notify();
        }

        ui.push();
    }

}
