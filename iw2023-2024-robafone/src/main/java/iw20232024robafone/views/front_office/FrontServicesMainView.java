package iw20232024robafone.views.front_office;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.entity.Servicio;
import iw20232024robafone.backend.service.*;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@RolesAllowed("CLIENT")
@Route("client_services")
public class FrontServicesMainView extends VerticalLayout {
    private final SecurityService securityService;
    public FrontServicesMainView(SecurityService securityService, EmployeeService employeeService, ComplaintService complaintService, ClientService clientService, LlamadaService llamadaService, ServicioService servicioService) {
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

        Grid<Servicio> gridCalls = new Grid<>(Servicio.class, false);

        gridCalls.addColumn(Servicio::getClient).setHeader("Client Name");
        gridCalls.addColumn(Servicio::getType).setHeader("Type of Service");
        gridCalls.addColumn(Servicio::getDescription).setHeader("Description");
        gridCalls.addColumn(Servicio::getGigas).setHeader("Gigas");
        gridCalls.addColumn(Servicio::getPrice).setHeader("Price");
        gridCalls.addColumn(Servicio::getValidated).setHeader("Is Validated By Employee?");

        HorizontalLayout buttonLayout = new HorizontalLayout();

        gridCalls.addSelectionListener(selection -> {
            Optional<Servicio> optionalPerson = selection.getFirstSelectedItem();
            if (optionalPerson.isPresent()) {

                Button deleteButton = new Button("Unsubscribe to Service", e ->{
                    servicioService.delete(optionalPerson.get());
                    gridCalls.setItems(servicioService.findAll());
                    Notification.show("Unsubscribed Succesfully");
                    UI.getCurrent().getPage().reload();
                });

                deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

                add(deleteButton);
            }
        });

        //gridCalls.setItems(servicioService.findAll());
        List<Servicio> listaFiltrada = servicioService.findServiciotByUser(currentClient.getUsername());
        for(int i = 0; i < listaFiltrada.size(); i++){
            if(!listaFiltrada.get(i).getValidated()) listaFiltrada.remove(i);
        }
        gridCalls.setItems( servicioService.findServiciotByUser(currentClient.getUsername()));


        GridListDataView<Servicio> dataView = gridCalls.setItems(servicioService.findServiciotByUser(currentClient.getUsername()));

        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Search Username");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());


        dataView.addFilter(service -> {
            String searchTerm = searchField.getValue().trim();

            if (searchTerm.isEmpty())
                return true;

            boolean matchesFirstName = matchesTerm(service.getDescription(),
                    searchTerm);

            boolean matchesgigas = matchesTerm(service.getGigas(),
                    searchTerm);

            boolean matchesPrice = matchesTerm(service.getPrice(),
                    searchTerm);

            return matchesFirstName || matchesPrice || matchesgigas;
        });


        Button goBack = new Button("Go Back to Main Menu", buttonClickEvent -> UI.getCurrent().navigate("client"));
        goBack.setWidth("120px");
        goBack.setSizeFull();

        buttonLayout.add(goBack);
        buttonLayout.setAlignItems(Alignment.START);

        add(createHeaderContent(), new H2("Active Services of User"), new H3("Select Services to Unsubscribe"),searchField, gridCalls, buttonLayout);

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

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
