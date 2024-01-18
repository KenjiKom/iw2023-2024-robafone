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
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.entity.Servicio;
import iw20232024robafone.backend.entity.Tarifa;
import iw20232024robafone.backend.service.*;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RolesAllowed("CLIENT")
@Route("hire_phone")
public class FrontHirePhoneMainView extends VerticalLayout {
    private final SecurityService securityService;
    public FrontHirePhoneMainView(SecurityService securityService, EmployeeService employeeService, ClientService clientService, ServicioService servicioService, TarifaService tarifaService) {
        this.securityService = securityService;

        //Set the layout to be centered in the page.
        setSizeFull();
        setAlignItems(Alignment.STRETCH);

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

        Button goBack = new Button("Go Back to Main Menu", buttonClickEvent -> UI.getCurrent().navigate("client"));
        goBack.setWidth("120px");
        goBack.setSizeFull();
        goBack.addThemeVariants(ButtonVariant.LUMO_ERROR);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(goBack);
        buttonLayout.setAlignItems(Alignment.START);

        Text description = new Text("The basic standard phone offering consists of phone service/dial tone, basic feature and functionality, standard programming of phone, ability to make and receive calls, personalized phone number and voice mailbox.");

        TextField gigas = new TextField("How many MB? ($3 per MB)");
        gigas.setWidth("250px");

        H3 texto = new H3("Select the desired rate");
        List<Tarifa> listTarifas = tarifaService.findAll();

        for (int i = 0; i < listTarifas.size(); i++){
            if(listTarifas.get(i).getTipo().equals("Fiber")){
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
                newServicio.setType("Phone");
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

        /*

        Client finalCurrentClient = currentClient;
        Button hireService = new Button("Hire this service", buttonClickEvent -> {
            if(gigas.equals("0") || gigas.equals("00") || gigas.equals("000") || gigas.equals("0000") || gigas.equals("000000") || gigas.equals("0000000")){
                Notification.show("Dont try to scam us");
            }else {
                String precio = gigas.getValue();
                Integer precioInt = Integer.parseInt(precio) * 3;
                String precioFinal = String.valueOf(precioInt);
                Servicio newServicio = new Servicio();
                newServicio.setDescription(description.getText());
                newServicio.setPrice(precioFinal);
                newServicio.setType("Fijo");
                newServicio.setValidated(false);

                List<Client> cliente = new ArrayList<Client>();
                cliente.add(finalCurrentClient);
                newServicio.setClient(finalCurrentClient);
                servicioService.save(newServicio);
                Notification.show("Service Hired!");
                UI.getCurrent().navigate("client");
            }
        });
        hireService.addThemeVariants(ButtonVariant.LUMO_PRIMARY);*/


        add(createHeaderContent(), new H2("Hire Phone With Robafone"),description, texto, tarifaGrid,buttonLayout);

    }
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
