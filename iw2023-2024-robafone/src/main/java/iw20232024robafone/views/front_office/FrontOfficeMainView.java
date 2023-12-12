package iw20232024robafone.views.front_office;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("client")
public class FrontOfficeMainView extends VerticalLayout {

    public FrontOfficeMainView(){
        //Set the layout to be centered in the page.
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Robafone for Clients");

        Button hireButton = new Button("Hire services", event -> UI.getCurrent().navigate("hire"));
        hireButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button consultButton = new Button("Consult hired services", event -> UI.getCurrent().navigate("consult"));
        consultButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button downloadButton = new Button("Download past bills", event -> UI.getCurrent().navigate("bills"));
        downloadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button shareButton = new Button("Share data", event -> UI.getCurrent().navigate("share"));
        shareButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout formLayout = new FormLayout(hireButton, consultButton, downloadButton, shareButton);

        add(title, formLayout);
    }
}
