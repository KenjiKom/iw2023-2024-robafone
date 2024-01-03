package iw20232024robafone.views.front_office;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed("CLIENT")
@Route("hire")
public class HireServicesMainView extends VerticalLayout {
    public HireServicesMainView(){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Services for Hire in Robafone");

        Button fiberButton = new Button("Hire Optic Fiber Service", event -> UI.getCurrent().navigate("fiber"));
        fiberButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button phoneButton = new Button("Hire Phone Services", event -> UI.getCurrent().navigate("phone"));
        phoneButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout formLayout = new FormLayout(fiberButton, phoneButton);

        add(title, formLayout);
    }
}
