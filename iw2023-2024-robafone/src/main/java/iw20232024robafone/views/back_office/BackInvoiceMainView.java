package iw20232024robafone.views.back_office;

import com.vaadin.flow.component.Component;
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
import iw20232024robafone.backend.entity.Employee;
import iw20232024robafone.backend.entity.Invoice;
import iw20232024robafone.backend.entity.Servicio;
import iw20232024robafone.backend.service.*;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RolesAllowed("EMPLOYEE")
@Route("invoice")
public class BackInvoiceMainView extends VerticalLayout {
    private final SecurityService securityService;
    public BackInvoiceMainView(SecurityService securityService, EmployeeService employeeService, ComplaintService complaintService, ClientService clientService, ServicioService servicioService, InvoiceService invoiceService) {
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

        gridClient.addColumn(Client::getFirstName).setHeader("First Name");
        gridClient.addColumn(Client::getLastName).setHeader("Last Name");
        gridClient.addColumn(Client::getEmail).setHeader("Email");
        gridClient.addColumn(Client::getPhoneNumber).setHeader("Phone");
        gridClient.addColumn(Client::getDataUsage).setHeader("Data Usage");
        gridClient.addColumn(Client::getMonthlyVolume).setHeader("Monthly Volume");

        VerticalLayout crudLayout = new VerticalLayout();

        AtomicReference<Boolean> clicked = new AtomicReference<>(false);
        Employee finalCurrentEmployee = currentEmployee;
        gridClient.addSelectionListener(selection -> {
            Optional<Client> optionalPerson = selection.getFirstSelectedItem();
            if (optionalPerson.isPresent()) {
                H2 title = new H2("List of Bills of Selected Client");

                Grid<Servicio> gridInvoice = new Grid<>(Servicio.class, false);


                selection.getFirstSelectedItem().get().getUsername();

                gridInvoice.addColumn(Servicio::getClient).setHeader("Client");
                gridInvoice.addColumn(Servicio::getType).setHeader("Service");
                gridInvoice.addColumn(Servicio::getDescription).setHeader("Description");
                gridInvoice.addColumn(Servicio::getPrice).setHeader("Price");

                gridInvoice.setItems(servicioService.findServiciotByUser(selection.getFirstSelectedItem().get().getUsername()));
                gridInvoice.addSelectionListener(selectionServicio -> {
                    Optional<Servicio> optionalServicio = selectionServicio.getFirstSelectedItem();
                    if (optionalServicio.isPresent()) {

                        Invoice invoice = new Invoice();
                        invoice.setClient(selection.getFirstSelectedItem().get());
                        invoice.setService(selectionServicio.getFirstSelectedItem().get());
                        invoice.setEmployee(finalCurrentEmployee);
                        invoice.setInvoiceDate(LocalDateTime.now());
                        invoice.setCost(selectionServicio.getFirstSelectedItem().get().getPrice());
                        invoiceService.save(invoice);

                        Notification.show("Invoice Sent");
                    }
                });
                crudLayout.add(title, gridInvoice);
            }
        });


        gridClient.setItems(clientService.findAll());

        Button goBack = new Button("Go Back to Main Menu", buttonClickEvent -> UI.getCurrent().navigate("internal"));
        goBack.setWidth("120px");
        goBack.setSizeFull();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(goBack);
        buttonLayout.setAlignItems(Alignment.START);

        add(createHeaderContent(), new H2("Send Bills"), new H3("Select A Client to Send A Bill"), gridClient, crudLayout ,buttonLayout);

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
