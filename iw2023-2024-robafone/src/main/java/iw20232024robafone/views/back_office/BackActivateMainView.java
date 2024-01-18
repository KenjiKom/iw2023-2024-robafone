package iw20232024robafone.views.back_office;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import iw20232024robafone.backend.entity.Employee;
import iw20232024robafone.backend.entity.Servicio;
import iw20232024robafone.backend.service.EmployeeService;
import iw20232024robafone.backend.service.ServicioService;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RolesAllowed("EMPLOYEE")
@Route("pending")
public class BackActivateMainView extends VerticalLayout {
    private final SecurityService securityService;
    public BackActivateMainView(SecurityService securityService, EmployeeService employeeService, ServicioService servicioService) {
        this.securityService = securityService;

        //Set the layout to be centered in the page.
        setSizeFull();
        setAlignItems(Alignment.STRETCH);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        //Find the Client currently using the view
        Employee currentEmployee = new Employee();
        List<Employee> clientList = employeeService.findAll();
        for (int i = 0; i < clientList.size(); i++){
            if(clientList.get(i).getUsername().equals(currentPrincipalName)){
                currentEmployee = clientList.get(i);
            }
        }

        Grid<Servicio> gridClient = new Grid<>(Servicio.class, false);

        Grid.Column<Servicio> clientColumn = gridClient
                .addColumn(Servicio::getClient).setHeader("Client");

        Grid.Column<Servicio> descriptionColumn = gridClient
                .addColumn(Servicio::getDescription).setHeader("Description");

        Grid.Column<Servicio> typeColumn = gridClient
                .addColumn(Servicio::getType).setHeader("Type");

        Grid.Column<Servicio> priceColumn = gridClient
                .addColumn(Servicio::getPrice).setHeader("Price");


        Editor<Servicio> editor = gridClient.getEditor();


        gridClient.setItems(servicioService.findServicioByValidation());

        gridClient.addSelectionListener(selectionEvent -> {
           Button acceptButton = new Button("Accept", buttonClickEvent -> {
               selectionEvent.getFirstSelectedItem().get().setValidated(true);
               servicioService.save(selectionEvent.getFirstSelectedItem().get());
               Notification.show("Service accepted");
               UI.getCurrent().navigate("internal");
               });
           /*Button deleteButton = new Button("Denied", buttonClickEvent -> {
               servicioService.delete(selectionEvent.getFirstSelectedItem().get());
               Notification.show("Service denied");
               UI.getCurrent().navigate("internal");
                });*/
            add(acceptButton);
        });

        Button goBack = new Button("Go Back to Main Menu", buttonClickEvent -> UI.getCurrent().navigate("internal"));
        goBack.setWidth("120px");
        goBack.setSizeFull();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(goBack);
        buttonLayout.setAlignItems(Alignment.START);

        add(createHeaderContent(), new H2("Manage Services"), gridClient, buttonLayout);

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
