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
import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.entity.Llamada;
import iw20232024robafone.backend.entity.Sms;
import iw20232024robafone.backend.service.*;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.aspectj.weaver.ast.Call;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RolesAllowed("CLIENT")
@Route("monthly")
public class FrontMonthlyMainView extends VerticalLayout {
    private final SecurityService securityService;
    public FrontMonthlyMainView(SecurityService securityService, ClientService clientService, SmsService smsService, LlamadaService llamadaService) {
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

        //Call monthly volume

        Grid<Llamada> gridCalls = new Grid<>(Llamada.class, false);

        gridCalls.addColumn(Llamada::getCallDate).setHeader("Date of Call").setSortable(true);
        gridCalls.addColumn(Llamada::getSender).setHeader("Called to").setSortable(true);
        gridCalls.addColumn(Llamada::getDuration).setHeader("Duration of call").setSortable(true);

        List<Llamada> listAllCalls = llamadaService.findCallByUser(currentClient.getUsername());

        List<Llamada> filteredCallList = new ArrayList<>();

        for (int i = 0; i < listAllCalls.size(); i++){
            if(listAllCalls.get(i).getCallDate().isBefore(LocalDateTime.now()) && listAllCalls.get(i).getCallDate().isAfter(LocalDateTime.now().minusDays(31))){
                filteredCallList.add(listAllCalls.get(i));
            }
        }

        gridCalls.setItems(filteredCallList);


        //Call monthly volume

        Grid<Sms> gridSms = new Grid<>(Sms.class, false);

        gridSms.addColumn(Sms::getSmsDate).setHeader("Date of SMS").setSortable(true);
        gridSms.addColumn(Sms::getSender).setHeader("Sms to").setSortable(true);
        gridSms.addColumn(Sms::getMessage).setHeader("Message of Sms").setSortable(true);

        List<Sms> listAllSms = smsService.findSmsByUser(currentClient.getUsername());

        List<Sms> filteredSmsList = new ArrayList<>();

        for (int i = 0; i < listAllSms.size(); i++){
            //Cambio aqui
            if(listAllSms.get(i).getSmsDate().isBefore(LocalDateTime.now()) && listAllSms.get(i).getSmsDate().isAfter(LocalDateTime.now().minusDays(31))){
                filteredSmsList.add(listAllSms.get(i));
            }
        }

        gridSms.setItems(filteredSmsList);

        Button goBack = new Button("Go Back to Main Menu", buttonClickEvent -> UI.getCurrent().navigate("client"));
        goBack.setWidth("120px");
        goBack.setSizeFull();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(goBack);
        buttonLayout.setAlignItems(Alignment.START);

        add(createHeaderContent(), new H2("Monthly Registry of Calls"),gridCalls, new H2("Monthly Registry of SMS"),gridSms, buttonLayout);

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
