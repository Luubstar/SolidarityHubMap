package es.pingu.map.commons;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import es.pingu.map.views.MapView;

public class NavigationBar {
    public static SideNav createNavBar() {
        SideNav nav = new SideNav();

        SideNavItem dashboardLink = new SideNavItem("Dashboard",
                MapView.class, VaadinIcon.DASHBOARD.create());
        SideNavItem inboxLink = new SideNavItem("Inbox", MapView.class,
                VaadinIcon.ENVELOPE.create());
        SideNavItem calendarLink = new SideNavItem("Calendar",
                MapView.class, VaadinIcon.CALENDAR.create());
        SideNavItem settingsLink = new SideNavItem("Settings",
                MapView.class, VaadinIcon.COG.create());
        SideNavItem vaadinLink = new SideNavItem("Vaadin website",
                "https://vaadin.com", VaadinIcon.VAADIN_H.create());

        nav.addItem(dashboardLink, inboxLink, calendarLink, settingsLink,
                vaadinLink);
        return nav;
    }
}
