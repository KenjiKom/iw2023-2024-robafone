package iw20232024robafone.views.back_office;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.entity.Complaint;
import iw20232024robafone.backend.entity.Employee;
import iw20232024robafone.backend.entity.Servicio;
import iw20232024robafone.backend.service.EmployeeService;
import iw20232024robafone.backend.service.ServicioService;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Route("services")
public class BackServicesMainView extends VerticalLayout {
    private final SecurityService securityService;
    public BackServicesMainView(SecurityService securityService, EmployeeService employeeService, ServicioService servicioService) {
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

        Grid.Column<Servicio> stateColumn = gridClient
                .addColumn(Servicio::getValidated).setHeader("Is Validated");

        Grid.Column<Servicio> dateColumn = gridClient
                .addColumn(Servicio::getDateService).setHeader("Date");


        Editor<Servicio> editor = gridClient.getEditor();

        Grid.Column<Servicio> editColumn = gridClient.addComponentColumn(person -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                gridClient.getEditor().editItem(person);
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);

        Binder<Servicio> binder = new Binder<>(Servicio.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField descriptionField = new TextField();
        descriptionField.setWidthFull();
        binder.forField(descriptionField)
                .asRequired("First name must not be empty")
                .bind(Servicio::getDescription, Servicio::setDescription);
        descriptionColumn.setEditorComponent(descriptionField);

        TextField typeField = new TextField();
        descriptionField.setWidthFull();
        binder.forField(typeField)
                .asRequired("First name must not be empty")
                .bind(Servicio::getType, Servicio::setType);
        typeColumn.setEditorComponent(typeField);

        TextField priceField = new TextField();
        descriptionField.setWidthFull();
        binder.forField(priceField)
                .asRequired("First name must not be empty")
                .bind(Servicio::getPrice, Servicio::setPrice);
        priceColumn.setEditorComponent(priceField);
        gridClient.addSelectionListener(selectionEvent -> {
            servicioService.save(selectionEvent.getFirstSelectedItem().get());
        });

        Button saveButton = new Button("Save", e -> editor.save());
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(),
                e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);

        List<Servicio> listServicio = servicioService.findAll();



        GridListDataView<Servicio> dataView = gridClient.setItems(listServicio);

        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());


        dataView.addFilter(service -> {
            String searchTerm = searchField.getValue().trim();

            if (searchTerm.isEmpty())
                return true;

            boolean matchesFirstName = matchesTerm(service.getClient().getFirstName(),
                    searchTerm);

            boolean matchesFastName = matchesTerm(service.getClient().getLastName(),
                    searchTerm);

            return matchesFirstName || matchesFastName ;
        });


        Button goBack = new Button("Go Back to Main Menu", buttonClickEvent -> UI.getCurrent().navigate("internal"));
        goBack.setWidth("120px");
        goBack.setSizeFull();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(goBack);
        buttonLayout.setAlignItems(Alignment.START);



        add(createHeaderContent(), new H2("Manage Services"),searchField, gridClient, buttonLayout);

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

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
