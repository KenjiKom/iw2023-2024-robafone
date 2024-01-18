package iw20232024robafone.views.autentication;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import iw20232024robafone.backend.service.EmployeeService;

@AnonymousAllowed
@Route("employee_verification")
public class EmployeeVerificationView extends VerticalLayout {
    public EmployeeVerificationView (EmployeeService employeeService){
        //Set the layout to be centered in the page.
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        //Main title of the page.
        H1 title = new H1("Please insert verification PIN:");

        //Create the password form.
        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("PIN");
        passwordField.setRevealButtonVisible(false);

        //Repeat the password.
        PasswordField repeatPasswordField = new PasswordField();
        repeatPasswordField.setLabel("Repeat PIN");
        repeatPasswordField.setRevealButtonVisible(false);

        Button primaryButton = new Button("Register",
                //When the button is clicked, we check if the fields are OK. If then, the user is registered.
                event -> Register(passwordField.getValue(), repeatPasswordField.getValue(), employeeService));
        primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(title, passwordField, repeatPasswordField, primaryButton);
    }

    private void Register(String p1, String p2, EmployeeService employeeService){
        if(p1.isEmpty()){
            Notification.show("PIN is empty");
        } else if (!p1.equals(p2)) {
            Notification.show("PINs are not equal");
        } else if (p1.equals("aroldanleregalaronednl")){
            add(new EmployeeRegisterView(employeeService));
        } else {
            Notification.show("PIN not correct");
        }
    }
}
