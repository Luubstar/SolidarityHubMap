package org.pinguweb.frontend.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.pinguweb.frontend.utils.AuthUtils;

@Route("test")
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        AuthUtils.checkAuthentication();

        this.setSizeFull();

        VerticalLayout dashboardLayout = new VerticalLayout();
        dashboardLayout.setSizeFull();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        this.add(NavigationBar.createNavBar());

        this.add(buttonLayout);

        FlexLayout cardL = new FlexLayout();
        cardL.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        cardL.setJustifyContentMode(FlexLayout.JustifyContentMode.CENTER);
        cardL.setWidthFull();

        String[] cards = {"volunteer-skills", "users-affected", "Configuración", "PendingTaskView", "needs", "Proyectos"};
        for (int i = 0; i < cards.length; i++) {
            Div card = createCard(cards[i], "Description " + i);
            int finalI = i;
            card.addClickListener(e -> {
                switch (finalI) {
                    case 0:
                        getUI().ifPresent(ui -> ui.navigate("dashboard/volunteer-skills"));
                        break;
                    case 1:
                        getUI().ifPresent(ui -> ui.navigate("dashboard/users-affected"));
                        break;
                    case 2:
                        getUI().ifPresent(ui -> ui.navigate("dashboard/settings"));
                        break;
                    case 3:
                        getUI().ifPresent(ui -> ui.navigate("dashboard/pending-task"));
                        break;
                    case 4:
                        getUI().ifPresent(ui -> ui.navigate("dashboard/needs"));
                        break;
                    case 5:
                        getUI().ifPresent(ui -> ui.navigate("dashboard/projects"));
                        break;
                    default:
                        break;
                }
            });
            cardL.add(card);
        }
        dashboardLayout.add(cardL);
        this.add(dashboardLayout);
    }

    private Div createCard(String title, String description) {
        Div card = new Div();
        card.setText(title + "\n" + description);
        card.getStyle().set("border", "1px solid #ccc");
        card.getStyle().set("padding", "20px");
        card.getStyle().set("margin", "10px");
        card.getStyle().set("border-radius", "10px");
        card.getStyle().set("cursor", "pointer");
        card.getStyle().set("text-align", "center");
        card.getStyle().set("width", "150px");
        card.getStyle().set("background", "#f9f9f9");
        card.getStyle().set("flex", "1 1 30%");
        return card;
    }
}
