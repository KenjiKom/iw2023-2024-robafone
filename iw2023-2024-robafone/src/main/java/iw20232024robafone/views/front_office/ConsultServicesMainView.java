package iw20232024robafone.views.front_office;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("consult")
public class ConsultServicesMainView extends VerticalLayout {
    public ConsultServicesMainView (){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Services for Clients");

        Button hireButton = new Button("Monthly Volume", event -> UI.getCurrent().navigate("montly"));
        hireButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button consultButton = new Button("Call Registry", event -> UI.getCurrent().navigate("calls"));
        consultButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button downloadButton = new Button("SMS Registry", event -> UI.getCurrent().navigate("sms"));
        downloadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button shareButton = new Button("5G Daily Volume", event -> UI.getCurrent().navigate("5g"));
        shareButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout formLayout = new FormLayout(hireButton, consultButton, downloadButton, shareButton);

        add(title, formLayout);

    }
}
