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
import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.entity.Employee;
import iw20232024robafone.backend.entity.Servicio;
import iw20232024robafone.backend.service.ClientService;
import iw20232024robafone.backend.service.EmployeeService;
import iw20232024robafone.backend.service.ServicioService;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RolesAllowed("EMPLOYEE")
@Route("customers")
public class BackCustomersMainView extends VerticalLayout {
    private final SecurityService securityService;
    public BackCustomersMainView(SecurityService securityService, EmployeeService employeeService, ServicioService servicioService, ClientService clientService) {
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

        Grid<Client> gridClient = new Grid<>(Client.class, false);

        Grid.Column<Client> usernameColumn = gridClient
                .addColumn(Client::getUsername).setHeader("Username").setSortable(true);

        Grid.Column<Client> firstColumn = gridClient
                .addColumn(Client::getFirstName).setHeader("First Name").setSortable(true);

        Grid.Column<Client> lastColumn = gridClient
                .addColumn(Client::getLastName).setHeader("Last Name").setSortable(true);

        Grid.Column<Client> emailColumn = gridClient
                .addColumn(Client::getEmail).setHeader("Email").setSortable(true);

        Grid.Column<Client> phoneColumn = gridClient
                .addColumn(Client::getPhoneNumber).setHeader("Phone Number").setSortable(true);

        Grid.Column<Client> roamingColumn = gridClient
                .addColumn(Client::getRoaming).setHeader("Roaming").setSortable(true);

        Grid.Column<Client> datosCompColumn = gridClient
                .addColumn(Client::getDatosCompartidos).setHeader("Share Data").setSortable(true);

        Grid.Column<Client> blockNumberColumn = gridClient
                .addColumn(Client::getNumerosBloqueados).setHeader("Block Numbers").setSortable(true);



        Editor<Client> editor = gridClient.getEditor();

        Grid.Column<Client> editColumn = gridClient.addComponentColumn(person -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                gridClient.getEditor().editItem(person);
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);

        Binder<Client> binder = new Binder<>(Client.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField usernameField = new TextField();
        usernameField.setWidthFull();
        binder.forField(usernameField)
                .asRequired("Username must not be empty")
                .bind(Client::getUsername, Client::setUsername);
        usernameColumn.setEditorComponent(usernameField);

        TextField firstField = new TextField();
        firstField.setWidthFull();
        binder.forField(firstField)
                .asRequired("First name must not be empty")
                .bind(Client::getFirstName, Client::setFirstName);
        firstColumn.setEditorComponent(firstField);

        TextField lastField = new TextField();
        lastField.setWidthFull();
        binder.forField(lastField)
                .asRequired("Last name must not be empty")
                .bind(Client::getLastName, Client::setLastName);
        lastColumn.setEditorComponent(lastField);

        TextField emailField = new TextField();
        emailField.setWidthFull();
        binder.forField(emailField)
                .asRequired("Email must not be empty")
                .bind(Client::getEmail, Client::setEmail);
        emailColumn.setEditorComponent(emailField);

        TextField phoneField = new TextField();
        phoneField.setWidthFull();
        binder.forField(phoneField)
                .asRequired("Phone must not be empty")
                .bind(Client::getPhoneNumber, Client::setPhoneNumber);
        phoneColumn.setEditorComponent(phoneField);

        gridClient.addSelectionListener(selectionEvent -> {
            clientService.save(selectionEvent.getFirstSelectedItem().get());
        });

        Button saveButton = new Button("Save", e -> {
            editor.save();
        });


        Button cancelButton = new Button(VaadinIcon.CLOSE.create(),
                e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);

        List<Client> listClient = clientService.findAll();
        gridClient.setItems(listClient);



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
        Button profile = new Button("Profile ", e -> UI.getCurrent().navigate("employee_profile"));
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
