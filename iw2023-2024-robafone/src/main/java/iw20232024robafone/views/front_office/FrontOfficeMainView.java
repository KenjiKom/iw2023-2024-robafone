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

@RolesAllowed("CLIENT")
@Route("client")
public class FrontOfficeMainView extends VerticalLayout {

    private final SecurityService securityService;
    public FrontOfficeMainView(SecurityService securityService){
        this.securityService = securityService;
        //Set the layout to be centered in the page.
        setSizeFull();
        setAlignItems(Alignment.STRETCH);

        H1 title = new H1("Robafone for Clients");

        TabSheet tabSheet = new TabSheet();

        //Components for 1st tab----------------------------------------------
        Text hire_title = new Text("Services for hire in Robafone");

        Button fiberButton = new Button("Hire Optic Fiber Service", event -> UI.getCurrent().navigate("fiber"));
        fiberButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button phoneButton = new Button("Hire Phone Services", event -> UI.getCurrent().navigate("phone"));
        phoneButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout formLayout = new VerticalLayout(fiberButton, phoneButton);

        formLayout.setWidthFull();
        formLayout.setAlignItems(Alignment.STRETCH);
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        tabSheet.add("Hire Services",
                new Div(hire_title, formLayout));

        //End of 1st tab-------------------------------------------------------

        //Components for 2nd tab----------------------------------------------
        Text consult_title = new Text("Consult your Services in Robafone");

        Button monthlyButton = new Button("Monthly Volume", event -> UI.getCurrent().navigate("montly"));
        monthlyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button callButton = new Button("Call Registry", event -> UI.getCurrent().navigate("calls"));
        callButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button smsButton = new Button("SMS Registry", event -> UI.getCurrent().navigate("sms"));
        smsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button fivegButton = new Button("5G Daily Volume", event -> UI.getCurrent().navigate("5g"));
        fivegButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout tabTwoLayout = new VerticalLayout(monthlyButton, callButton, smsButton, fivegButton);

        tabTwoLayout.setWidthFull();
        tabTwoLayout.setAlignItems(Alignment.STRETCH);
        tabTwoLayout.setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        tabSheet.add("Consult Services",
                new Div(consult_title, tabTwoLayout));
        //End of components of 2nd tab---------------------------------------

        //Components for 3rd tab---------------------------------------------

        VerticalLayout gridLayout = new VerticalLayout();

        //Temporary grid until we have data
        Grid grid = new Grid();
        gridLayout.add(grid);
        grid.setSizeFull();

        tabSheet.add("Download Bills",
                new Div(new Text("Download your past bills"), grid));
        //End of components of 3rd tab---------------------------------------

        //Components for 4th tab---------------------------------------------
        tabSheet.add("Share",
                new Div(new Text("Share Robafone with Friends")));
        //End of components of 4th tab---------------------------------------

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
        layout.add(new H1(currentPrincipalName.toUpperCase() +", Welcome to Robafone"));

        return layout;
    }
}
