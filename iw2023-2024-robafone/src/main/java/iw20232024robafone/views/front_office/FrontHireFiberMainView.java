package iw20232024robafone.views.front_office;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import iw20232024robafone.backend.entity.*;
import iw20232024robafone.backend.service.*;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RolesAllowed("CLIENT")
@Route("hire_fiber")
public class FrontHireFiberMainView extends VerticalLayout {
    private final SecurityService securityService;
    public FrontHireFiberMainView(SecurityService securityService, EmployeeService employeeService, ComplaintService complaintService, ClientService clientService, ServicioService servicioService, InvoiceService invoiceService, TarifaService tarifaService) {
        this.securityService = securityService;

        //Set the layout to be centered in the page.
        setSizeFull();
        //setAlignItems(Alignment.STRETCH);

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

        //Add the go back button
        Button goBack = new Button("Go Back to Main Menu", buttonClickEvent -> UI.getCurrent().navigate("client"));
        goBack.setWidth("120px");
        goBack.setSizeFull();
        goBack.addThemeVariants(ButtonVariant.LUMO_ERROR);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(goBack);
        buttonLayout.setAlignItems(Alignment.START);

        //Add the description of the service.
        Text description = new Text("Fiber-optic internet, commonly called fiber internet or simply “fiber,” is a broadband connection that can reach speeds of up to 10 Gigabits per second (Gbps) in some areas.");
        TextField gigas = new TextField("Choose GB's ($5 per GB)");
        gigas.setWidth("250px");


        //Now the user can select a desired rate.
        H3 texto = new H3("Select the desired rate");
        List<Tarifa> listTarifas = tarifaService.findAll();


        //Filter through the rates to only get the ones needed
        for (int i = 0; i < listTarifas.size(); i++){
            if(listTarifas.get(i).getTipo().equals("Phone")){
                listTarifas.remove(i);
            }
        }

        Grid<Tarifa> tarifaGrid = new Grid<>(Tarifa.class, false);

        tarifaGrid.addColumn(Tarifa::getGigas).setHeader("Gigas");
        tarifaGrid.addColumn(Tarifa::getPrecio).setHeader("Price");

        tarifaGrid.setHeight("50px");

        tarifaGrid.setItems(listTarifas);

        Client finalCurrentClient = currentClient;
        tarifaGrid.addSelectionListener(selection->{
            Button hireRateButton = new Button("Hire this Service", e->{
                Servicio newServicio = new Servicio();
                newServicio.setDescription(description.getText());
                newServicio.setPrice(selection.getFirstSelectedItem().get().getPrecio());
                newServicio.setGigas(selection.getFirstSelectedItem().get().getGigas());
                newServicio.setType("Fibra");
                newServicio.setValidated(false);
                newServicio.setClient(finalCurrentClient);
                newServicio.setDateService(LocalDateTime.now());
                servicioService.save(newServicio);
                Notification.show("Service Hired!");
                UI.getCurrent().navigate("client");
            });
            hireRateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            add(hireRateButton);
        });

        add(createHeaderContent(), new H2("Hire Fiber With Robafone"),description,texto, tarifaGrid ,buttonLayout);

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

        MenuBar menuBar = new MenuBar();

        Div icon = new Div(new Avatar());

        icon.getStyle().set("background-color", "transparent");

        MenuItem item = menuBar.addItem(icon);
        item.getStyle().set("background-color", "transparent");
        SubMenu subItems = item.getSubMenu();
        Button logout = new Button("Log out ", e -> securityService.logout());
        logout.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button profile = new Button("Profile ", e -> UI.getCurrent().navigate("profile"));
        logout.addThemeVariants(ButtonVariant.LUMO_ERROR);
        subItems.addItem(profile);

        subItems.addItem(logout);

        menuBar.getStyle().set("background-color", "transparent");
        layout.add(menuBar);
        layout.setAlignSelf(Alignment.END, icon);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Placeholder for the title of the current view.
        // The title will be set after navigation.
        layout.add(new H1(currentPrincipalName.toUpperCase() +", Welcome to Robafone Internal"));

        return layout;
    }

}
