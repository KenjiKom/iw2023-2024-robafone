package iw20232024robafone.views.back_office;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.Route;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RolesAllowed("EMPLOYEE")
@Route("internal")
public class BackOfficeMainView extends VerticalLayout {
    private final SecurityService securityService;
    public BackOfficeMainView(SecurityService securityService) {
        this.securityService = securityService;
        //Set the layout to be centered in the page.
        setSizeFull();
        setAlignItems(Alignment.STRETCH);


        TabSheet tabSheet = new TabSheet();

        //Components for 1st tab----------------------------------------------
        Text hire_title = new Text("Sales And Marketing Internal Tools");

        Button fiberButton = new Button("Manage Services and Rates", event -> UI.getCurrent().navigate("services"));
        fiberButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button customerButton = new Button("Manage Customers", event -> UI.getCurrent().navigate("customers"));
        customerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        VerticalLayout formLayout = new VerticalLayout(fiberButton, customerButton);

        formLayout.setWidthFull();
        formLayout.setAlignItems(Alignment.STRETCH);
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        tabSheet.add("Sales and Marketing",
                new Div(hire_title, formLayout));

        //End of 1st tab-------------------------------------------------------

        //Components for 2nd tab----------------------------------------------
        Text consult_title = new Text("Customer Services Internal Tools");

        Button callButton = new Button("Consult Open User Tickets", event -> UI.getCurrent().navigate("tickets"));
        callButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout tabTwoLayout = new VerticalLayout( callButton);

        tabTwoLayout.setWidthFull();
        tabTwoLayout.setAlignItems(Alignment.STRETCH);
        tabTwoLayout.setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        tabSheet.add("Customer Services",
                new Div(consult_title, tabTwoLayout));
        //End of components of 2nd tab---------------------------------------

        //Components for 3rd tab---------------------------------------------

        VerticalLayout gridLayout = new VerticalLayout();

        //Temporary grid until we have data
        Text tabThreeTitle = new Text("Billing Internal Tools");

        Button billingButton = new Button("Billing", event -> UI.getCurrent().navigate("invoice"));
        billingButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout tabThreeLayout = new VerticalLayout(billingButton);

        tabThreeLayout.setWidthFull();
        tabThreeLayout.setAlignItems(Alignment.STRETCH);
        tabThreeLayout.setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        tabSheet.add("Finance",
                new Div(tabThreeTitle, tabThreeLayout));
        //End of components of 3rd tab---------------------------------------

        //Some adjustments for the tabs
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);

        add(createHeaderContent(), tabSheet);

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
