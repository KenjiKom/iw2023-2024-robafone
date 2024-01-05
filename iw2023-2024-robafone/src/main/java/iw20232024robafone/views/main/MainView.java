package iw20232024robafone.views.main;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import iw20232024robafone.backend.service.ClientService;
import iw20232024robafone.backend.service.ComplaintService;
import iw20232024robafone.backend.service.EmployeeService;
import iw20232024robafone.backend.service.InvoiceService;
import iw20232024robafone.security.SecurityService;
import iw20232024robafone.views.back_office.BackOfficeMainView;
import iw20232024robafone.views.front_office.FrontOfficeMainView;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route("")
public class MainView extends VerticalLayout {
    public MainView(SecurityService securityService, EmployeeService employeeService, ClientService clientService, InvoiceService invoiceService, ComplaintService complaintService) {

        if (securityService.getAuthenticatedUser().getAuthorities().toString().equals("[ROLE_EMPLOYEE]")){
            add(new BackOfficeMainView(securityService));
        }
        if (securityService.getAuthenticatedUser().getAuthorities().toString().equals("[ROLE_CLIENT]")) {
            add(new FrontOfficeMainView(securityService, invoiceService, complaintService, clientService));
        }
    }
}