package iw20232024robafone.views.front_office;

import com.ironsoftware.ironpdf.PdfDocument;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.entity.Complaint;
import iw20232024robafone.backend.entity.Invoice;
import iw20232024robafone.backend.service.ClientService;
import iw20232024robafone.backend.service.ComplaintService;
import iw20232024robafone.backend.service.InvoiceService;
import iw20232024robafone.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.openqa.selenium.Pdf;
import org.pdfbox.exceptions.COSVisitorException;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.util.PDFHighlighter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.w3c.dom.Document;

import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RolesAllowed("CLIENT")
@Route("client")
public class FrontOfficeMainView extends VerticalLayout {

    private final SecurityService securityService;
    public FrontOfficeMainView(SecurityService securityService, InvoiceService invoiceService, ComplaintService complaintService, ClientService clientService){
        this.securityService = securityService;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Long numberOfComplaints = complaintService.count();

        //Find the Client currently using the view
        Client currentClient = new Client();
        List<Client> clientList = clientService.findAll();
        for (int i = 0; i < clientList.size(); i++){
            if(clientList.get(i).getUsername().equals(currentPrincipalName)){
                currentClient = clientList.get(i);
            }
        }
        //Set the layout to be centered in the page.
        setSizeFull();
        setAlignItems(Alignment.STRETCH);

        H1 title = new H1("Robafone for Clients");

        TabSheet tabSheet = new TabSheet();

        //Components for 1st tab----------------------------------------------
        Text hire_title = new Text("Services for hire in Robafone");

        Button fiberButton = new Button("Hire Optic Fiber Service", event -> UI.getCurrent().navigate("hire_fiber"));
        fiberButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button phoneButton = new Button("Hire Phone Services", event -> UI.getCurrent().navigate("hire_phone"));
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

        Button monthlyButton = new Button("Active Services", event -> UI.getCurrent().navigate("client_services"));
        monthlyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button callButton = new Button("Call Registry", event -> UI.getCurrent().navigate("calls"));
        callButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button smsButton = new Button("SMS Registry", event -> UI.getCurrent().navigate("sms"));
        smsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        //Button fivegButton = new Button("5G Daily Volume", event -> UI.getCurrent().navigate("5g"));
        //fivegButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        VerticalLayout tabTwoLayout = new VerticalLayout(monthlyButton, callButton, smsButton);

        tabTwoLayout.setWidthFull();
        tabTwoLayout.setAlignItems(Alignment.STRETCH);
        tabTwoLayout.setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        tabSheet.add("Consult Services",
                new Div(consult_title, tabTwoLayout));
        //End of components of 2nd tab---------------------------------------

        Text complaintTitle = new Text("Complaints");

        //Show all existing Complaints for this client

        List<Complaint> clientComplaints = complaintService.findComplaintByUser(currentPrincipalName);
        Grid<Complaint> gridComplaint = new Grid<>(Complaint.class, true);


        //Make the complaint Form
        TextField complaintSubject = new TextField();
        complaintSubject.setLabel("Please State a Subject");

        TextField complaintDescription = new TextField();
        complaintDescription.setLabel("Please Explain Your Issue");

        Client finalCurrentClient = currentClient;
        Button sendComplaint = new Button("Send Complaint", buttonClickEvent -> {
            sendComplaint(complaintSubject.getValue(), complaintDescription.getValue(), complaintService, clientService, finalCurrentClient);
            gridComplaint.setItems(complaintService.findComplaintByUser(finalCurrentClient.getUsername()));
        });

        sendComplaint.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        VerticalLayout complaintLayout = new VerticalLayout(complaintSubject, complaintDescription, sendComplaint);
        complaintLayout.setHeightFull();
        complaintLayout.setAlignItems(Alignment.CENTER);
        complaintLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);


        tabSheet.add("Complaints", new Div(complaintTitle,gridComplaint, complaintLayout));
        //Components for 3rd tab---------------------------------------------

        VerticalLayout gridLayout = new VerticalLayout();

        //Temporary grid until we have data
        Grid<Invoice> grid = new Grid<>(Invoice.class, false);
        List<Invoice> invoices = invoiceService.findAll();
        grid.addColumn(Invoice::getId).setHeader("ID");
        grid.addColumn(Invoice::getClient).setHeader("Owner");
        grid.addColumn(Invoice::getEmployee).setHeader("Employee In Charge");
        grid.addColumn(Invoice::getCost).setHeader("Cost");
        grid.addColumn(Invoice::getInvoiceDate).setHeader("Date of Creation");
        grid.addSelectionListener(selection -> {
            Optional<Invoice> optionalInvoice = selection.getFirstSelectedItem();
            if(optionalInvoice.isPresent()){
                String html = new String(optionalInvoice.get().getClient().toString() + " has a payment of: " + optionalInvoice.get().getCost().toString() +"$"+" with date of "+optionalInvoice.get().getInvoiceDate().toString());
                download(html);

            }
        });
        grid.setItems(invoiceService.findServiciotByUser(currentClient.getUsername()));

        tabSheet.add("Download Bills",
                new Div(new Text("Download your past bills"), grid));
        //End of components of 3rd tab---------------------------------------


        MultiSelectComboBox<String> multiSelectComboBox = new MultiSelectComboBox<>();
        multiSelectComboBox.setItems("Activate Roaming","Activate Share Data","Activate Block Special Numbers");
        Button sendConfig = new Button("Send Configuration", buttonClickEvent -> {

        });
        sendConfig.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        tabSheet.add("Activate Extra Options",
                new VerticalLayout(new Text("Click to Activate These Options in Your Account"), multiSelectComboBox, sendConfig));

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

    private void sendComplaint(String subject, String description, ComplaintService complaintService, ClientService clientService, Client currentClient){
        Complaint newComplaint = new Complaint();
        newComplaint.setDateComplaint(LocalDateTime.now());
        newComplaint.setReason(subject);
        newComplaint.setMessage(description);
        newComplaint.setClient(clientService.findClientByUsername(currentClient.getUsername()));
        complaintService.save(newComplaint);
        Notification.show("Complaint Sent");
    }

    public void download(String html) {

        StreamResource res = new StreamResource("invoice", () -> {
                byte[] b = null;
                b=html.getBytes();
                return new ByteArrayInputStream(b);
        });

        res.setContentType("application/txt");
        res.setCacheTime(0);

        Anchor a = new Anchor(res, "");
        a.getElement().setAttribute("download", true);
        a.add(new Button(new Icon(VaadinIcon.DOWNLOAD_ALT)));
        add(a);
    }


}
