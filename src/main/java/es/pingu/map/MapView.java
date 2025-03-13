package es.pingu.map;

import java.util.List;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;



@PageTitle("Leaflet + Vaadin demos")
@Route("")
public class MapView extends VerticalLayout {

    private final Grid<Example> grExamples = new Grid<>();

    public MapView() {
        this.grExamples.addColumn(new ComponentRenderer<>(example -> {
            final Anchor anchor = new Anchor(example.route(), example.name());
            final Span spDesc = new Span(example.desc());
            spDesc.getStyle().set("font-size", "90%");
            spDesc.getStyle().set("white-space", "pre");

            final VerticalLayout vl = new VerticalLayout(anchor, spDesc);
            vl.setSpacing(false);
            return vl;
        })).setHeader("Available demos");

        this.grExamples.setSizeFull();
        this.grExamples.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER);
        this.add(this.grExamples);
        this.setHeightFull();
    }

    @Override
    protected void onAttach(final AttachEvent attachEvent) {
        this.grExamples.setItems(List.of(
                new Example("leaflet", "Leaflet", "Leaflet is the leading open-source JavaScript library for mobile-friendly interactive maps."),
                new Example("leaflet-vaadin", "Leaflet + Vaadin", "A simple Vaadin application that integrates Leaflet with Vaadin."),
                new Example("leaflet-vaadin-geojson", "Leaflet + Vaadin + GeoJSON", "A simple Vaadin application that integrates Leaflet with Vaadin and GeoJSON.")));
    }

    record Example(String route, String name, String desc) {
    }
}
