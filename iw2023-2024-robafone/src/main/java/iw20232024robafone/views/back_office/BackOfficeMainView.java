package iw20232024robafone.views.back_office;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("internal")
public class BackOfficeMainView extends VerticalLayout {

    public BackOfficeMainView() {
        //Set the layout to be centered in the page.
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Robafone for Employees");

        H5 disclaimer = new H5("Ahora mismo se vern todas las opciones por que falta el backend que compruebe de que tipo es el empleado");

        Button contractsButton = new Button("Manage Contracts", event -> UI.getCurrent().navigate("contracts"));
        contractsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button ticketsButton = new Button("Consult open tickets", event -> UI.getCurrent().navigate("tickets"));
        ticketsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button billButton = new Button("Billing", event -> UI.getCurrent().navigate("billing"));
        billButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button charasteristicsButton = new Button("Manage characteristics", event -> UI.getCurrent().navigate("characteristics"));
        charasteristicsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button ratesButton = new Button("Manage currect rates", event -> UI.getCurrent().navigate("rates"));
        ratesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout formLayout = new FormLayout(contractsButton, ticketsButton, billButton, charasteristicsButton, ratesButton);

        add(title,disclaimer, formLayout);

    }
}
