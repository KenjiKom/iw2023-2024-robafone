package iw20232024robafone.views.autentication;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.entity.Sms;
import iw20232024robafone.backend.service.ClientService;
import iw20232024robafone.backend.service.ComplaintService;
import iw20232024robafone.backend.service.EmployeeService;
import iw20232024robafone.backend.service.SmsService;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.awt.*;
import java.text.Normalizer;
import java.util.List;

@RolesAllowed("CLIENT")
@Route("profile")
public class ClientProfileView extends VerticalLayout {
    private final SecurityService securityService;
    public ClientProfileView(SecurityService securityService, EmployeeService employeeService, ComplaintService complaintService, ClientService clientService, SmsService smsService) {
        this.securityService = securityService;

        //Set the layout to be centered in the page.
        setSizeFull();
        setAlignItems(Alignment.STRETCH);

        //Get the Autentication Information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        //Find the Client currently using the view
        Client currentClient = new Client();
        List<Client> clientList = clientService.findAll();
        for (int i = 0; i < clientList.size(); i++){
            if(clientList.get(i).getUsername().equals(currentPrincipalName)){
                currentClient = clientList.get(i);
            }
        }


        //Now the userlayout is created and populated
        FormLayout userLayout = new FormLayout();

        TextField username = new TextField("Username");
        username.setValue(currentClient.getUsername());

        TextField firstname = new TextField("First Name");
        firstname.setValue(currentClient.getFirstName());

        TextField lastName = new TextField("Last Name");
        lastName.setValue(currentClient.getLastName());

        TextField phoneNumber = new TextField("Phone Number");
        phoneNumber.setValue(currentClient.getPhoneNumber());

        //Pass the current client to a final variable
        Client finalCurrentClient = currentClient;
        Button changeInfoButton = new Button("Change User Information", e -> {
            //If the user has clicked the button, set the new information and save to the repository
            finalCurrentClient.setUsername(username.getValue());
            finalCurrentClient.setFirstName(firstname.getValue());
            finalCurrentClient.setLastName(lastName.getValue());
            finalCurrentClient.setPhoneNumber(phoneNumber.getValue());
            clientService.save(finalCurrentClient);

            //Finally, reload the page
            UI.getCurrent().getPage().reload();
        });
        changeInfoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        userLayout.add(username, firstname, lastName, phoneNumber);

        //Let´s create a new vertical layout for the page info
        VerticalLayout formLayout = new VerticalLayout();

        formLayout.add(userLayout, changeInfoButton);

        add(createHeaderContent(), new H2("Change Your Personal Information"), formLayout, new Button("Go Back To Main Menu", e->{
            UI.getCurrent().navigate("client");
        }));

    }

    /*
    * Function createHeaderComponent:
    *       Input: Nothing
    *       Output: A component in the form of a header. Can be used in any of the views.
    * */
    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();

        // Configure styling for the header
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(true);
        layout.setAlignItems(Alignment.CENTER);

        //New meu bar
        MenuBar menuBar = new MenuBar();

        Div icon = new Div(new Avatar());

        icon.getStyle().set("background-color", "transparent");

        MenuItem item = menuBar.addItem(icon);
        item.getStyle().set("background-color", "transparent");
        SubMenu subItems = item.getSubMenu();

        //Add the logout button.
        Button logout = new Button("Log out ", e -> securityService.logout());
        logout.addThemeVariants(ButtonVariant.LUMO_ERROR);

        subItems.addItem(logout);

        menuBar.getStyle().set("background-color", "transparent");
        layout.add(menuBar);
        layout.setAlignSelf(Alignment.END, icon);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Placeholder for the title of the current view.
        // The title will be set after navigation.
        layout.add(new H1(currentPrincipalName.toUpperCase() +", Welcome to Robafone"));

        return layout;
    }
}
