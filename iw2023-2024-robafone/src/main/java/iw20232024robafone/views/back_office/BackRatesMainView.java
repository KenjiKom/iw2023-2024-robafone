package iw20232024robafone.views.back_office;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import iw20232024robafone.backend.entity.Complaint;
import iw20232024robafone.backend.entity.Employee;
import iw20232024robafone.backend.entity.Tarifa;
import iw20232024robafone.backend.service.ComplaintService;
import iw20232024robafone.backend.service.EmployeeService;
import iw20232024robafone.backend.service.TarifaService;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@RolesAllowed("EMPLOYEE")
@Route("rates")
public class BackRatesMainView extends VerticalLayout {
    private final SecurityService securityService;
    public BackRatesMainView(SecurityService securityService, EmployeeService employeeService, TarifaService tarifaService) {
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

        Grid<Tarifa> tarifaGrid = new Grid<>(Tarifa.class, false);

        tarifaGrid.addColumn(Tarifa::getTipo).setHeader("Type of Rate").setSortable(true);
        tarifaGrid.addColumn(Tarifa::getGigas).setHeader("GB ").setSortable(true);
        tarifaGrid.addColumn(Tarifa::getPrecio).setHeader("Price").setSortable(true);

        tarifaGrid.setItems(tarifaService.findAll());

        tarifaGrid.addSelectionListener(selectionEvent -> {
            TextField newGigas = new TextField("Introduce GB");
            newGigas.setValue(selectionEvent.getFirstSelectedItem().get().getGigas());

            TextField newPrice = new TextField("Introduce Price");
            newPrice.setValue(selectionEvent.getFirstSelectedItem().get().getPrecio());

            Button sendButton = new Button("Save Changes", e->{

                if(newPrice.isEmpty() || newGigas.isEmpty()){
                    Notification.show("Empty Value");
                }else{
                    selectionEvent.getFirstSelectedItem().get().setGigas(newGigas.getValue());
                    selectionEvent.getFirstSelectedItem().get().setPrecio(newPrice.getValue());
                    tarifaService.save(selectionEvent.getFirstSelectedItem().get());
                    Notification.show("Changes Saved");
                    UI.getCurrent().getPage().reload();
                }
            });
            add(newGigas, newPrice, sendButton);
        });


        Button goBack = new Button("Go Back to Main Menu", buttonClickEvent -> UI.getCurrent().navigate("internal"));
        goBack.setWidth("120px");
        goBack.setSizeFull();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(goBack);
        buttonLayout.setAlignItems(Alignment.START);

        add(createHeaderContent(), new H2("Manage Rates"),new H4("Select Rates to Modify Them"),tarifaGrid, buttonLayout);

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
