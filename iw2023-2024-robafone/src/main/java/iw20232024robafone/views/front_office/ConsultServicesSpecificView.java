package iw20232024robafone.views.front_office;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("consult_service")
public class ConsultServicesSpecificView extends VerticalLayout {
    public ConsultServicesSpecificView(){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        //Change later with the backend description
        H5 title = new H5("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse" +
                " cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa" +
                " qui officia deserunt mollit anim id est laborum.");

        //Button to end existing service. Red. This button should only appear if the user has this service active.
        Button deleteButton =  new Button("End existing contract");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        FormLayout formLayout = new FormLayout(title);

        add(formLayout);

    }
}
