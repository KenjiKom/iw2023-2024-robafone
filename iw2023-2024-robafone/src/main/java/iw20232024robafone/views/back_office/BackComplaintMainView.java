package iw20232024robafone.views.back_office;

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
import iw20232024robafone.backend.entity.Complaint;
import iw20232024robafone.backend.entity.Employee;
import iw20232024robafone.backend.entity.Servicio;
import iw20232024robafone.backend.service.ComplaintService;
import iw20232024robafone.backend.service.EmployeeService;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@RolesAllowed("EMPLOYEE")
@Route("tickets")
public class BackComplaintMainView extends VerticalLayout {
    private final SecurityService securityService;
    public BackComplaintMainView(SecurityService securityService, EmployeeService employeeService, ComplaintService complaintService) {
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

        Grid<Complaint> grinComplaint = new Grid<>(Complaint.class, false);
        List<Complaint> listComplaints = complaintService.findAll();

        grinComplaint.addColumn(Complaint::getClient).setHeader("Client").setSortable(true);
        grinComplaint.addColumn(Complaint::getReason).setHeader("Reason").setSortable(true);
        grinComplaint.addColumn(Complaint::getMessage).setHeader("Description").setSortable(true);
        grinComplaint.addColumn(Complaint::getResolverComment).setHeader("Resolver Comment").setSortable(true);
        grinComplaint.addColumn(Complaint::getResolved).setHeader("Is Resolved").setSortable(true);
        grinComplaint.addColumn(Complaint::getDateComplaint).setHeader("Date Of Complaint").setSortable(true);

        grinComplaint.addSelectionListener(selection -> {
            Optional<Complaint> optionalPerson = selection.getFirstSelectedItem();
            if (optionalPerson.isPresent()) {

                TextField commentText = new TextField("Comment the Complaint");

                Button sendButton = new Button("Send Comment", e->{
                    if(!commentText.isEmpty()){
                        optionalPerson.get().setResolverComment(commentText.getValue());
                        complaintService.save(optionalPerson.get());
                        Notification.show("Comment Sent to User");
                        UI.getCurrent().getPage().reload();
                    }else{
                        Notification.show("Empty Comment");
                    }
                });
                Button markButton = new Button("Mark as Resolved", e-> {
                    optionalPerson.get().setReasolved(true);
                    complaintService.save(optionalPerson.get());
                    Notification.show("Complaint Marked as Resolved");
                    UI.getCurrent().getPage().reload();
                });

                add(commentText, sendButton, markButton);
                grinComplaint.setItems(complaintService.findAll());
            }
        });


        grinComplaint.setItems(listComplaints);

        GridListDataView<Complaint> dataView = grinComplaint.setItems(listComplaints);

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

        add(createHeaderContent(), new H2("Manage Open Consults"), new H3("Select to Mark Complaint as Resolved"),searchField, grinComplaint, buttonLayout);

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
