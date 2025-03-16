package es.pingu.map.commons;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import es.pingu.map.views.MainView;
import es.pingu.map.views.MapView;

public class NavigationBar {
    public static SideNav createNavBar() {
        SideNav nav = new SideNav();

        SideNavItem homeLink = new SideNavItem("Inicio",
                MainView.class, VaadinIcon.HOME.create());
        SideNavItem mapLink = new SideNavItem("Mapa",
                MapView.class, VaadinIcon.MAP_MARKER.create());
        SideNavItem taskLink = new SideNavItem("Tareas", MapView.class,
                VaadinIcon.BELL.create());
        SideNavItem dashboardLink = new SideNavItem("Dashboard",
                MapView.class, VaadinIcon.DASHBOARD.create());
        SideNavItem resourcesLink = new SideNavItem("Recursos",
                MapView.class, VaadinIcon.TOOLBOX.create());
        SideNavItem contactLink = new SideNavItem("Contacto",
                "https://vaadin.com", VaadinIcon.PHONE.create());

        nav.addItem(homeLink, mapLink, taskLink, dashboardLink, resourcesLink, contactLink);
        return nav;
    }
}
