package iw20232024robafone.views.front_office;

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
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import iw20232024robafone.backend.entity.Llamada;
import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.service.LlamadaService;
import iw20232024robafone.backend.service.ClientService;
import iw20232024robafone.backend.service.ComplaintService;
import iw20232024robafone.backend.service.EmployeeService;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RolesAllowed("CLIENT")
@Route("calls")
public class FrontCallMainView extends VerticalLayout {
    private final SecurityService securityService;
    public FrontCallMainView(SecurityService securityService, EmployeeService employeeService, ComplaintService complaintService, ClientService clientService, LlamadaService llamadaService) {
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

        Grid<Llamada> gridCalls = new Grid<>(Llamada.class, false);

        gridCalls.addColumn(Llamada::getCallDate).setHeader("Date of Call").setSortable(true);
        gridCalls.addColumn(Llamada::getSender).setHeader("Called To").setSortable(true);
        gridCalls.addColumn(Llamada::getDuration).setHeader("Duration of The Call").setSortable(true);

        gridCalls.setItems(llamadaService.findCallByUser(currentClient.getUsername()));

        Button goBack = new Button("Go Back to Main Menu", buttonClickEvent -> UI.getCurrent().navigate("client"));
        goBack.setWidth("120px");
        goBack.setSizeFull();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(goBack);
        buttonLayout.setAlignItems(Alignment.START);

        add(createHeaderContent(), new H2("Registry of Calls"), gridCalls, buttonLayout);

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
