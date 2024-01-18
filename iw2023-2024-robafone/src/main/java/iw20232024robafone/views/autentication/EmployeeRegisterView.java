package iw20232024robafone.views.autentication;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import iw20232024robafone.backend.entity.Client;
import iw20232024robafone.backend.entity.Employee;
import iw20232024robafone.backend.service.ClientService;
import iw20232024robafone.backend.service.EmployeeService;
import iw20232024robafone.security.SecurityService;

import java.util.List;

@PageTitle("Register for Employees| Robafone")
public class EmployeeRegisterView extends VerticalLayout {

    public EmployeeRegisterView(EmployeeService employeeService){

        //Set the layout to be centered in the page.
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        //Main title of the page.
        H1 title = new H1("Register in Robafone");

        TextField username = new TextField();
        username.setLabel("Username");
        username.setClearButtonVisible(true);

        TextField firstName = new TextField();
        firstName.setLabel("First Name");
        firstName.setClearButtonVisible(true);

        TextField lastName = new TextField();
        lastName.setLabel("LastName");
        lastName.setClearButtonVisible(true);

        TextField phone = new TextField();
        phone.setLabel("Phone Number");
        phone.setClearButtonVisible(true);

        //Create the email form.
        EmailField validEmailField = new EmailField();
        validEmailField.setLabel("Email address");
        validEmailField.getElement().setAttribute("name", "email");
        validEmailField.setErrorMessage("Enter a valid email address");
        validEmailField.setClearButtonVisible(true);

        //Create the password form.
        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setRevealButtonVisible(false);

        //Repeat the password.
        PasswordField repeatPasswordField = new PasswordField();
        repeatPasswordField.setLabel("Repeat Password");
        repeatPasswordField.setRevealButtonVisible(false);
        //Check if the passwords are both the same.

        //Finally the button to be registered. The event is missing, will redirect to a register (Backend).
        Button primaryButton = new Button("Register",
                //When the button is clicked, we check if the fields are OK. If then, the user is registered.
                event -> Register(validEmailField.getValue(),
                        passwordField.getValue(),
                        repeatPasswordField.getValue(),
                        username.getValue(),
                        firstName.getValue(),
                        lastName.getValue(),
                        phone.getValue(),
                        employeeService));
        primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(title, validEmailField,username,firstName,lastName,phone, passwordField, repeatPasswordField, primaryButton);
    }

    private void Register(String email, String p1, String p2, String username, String firstName, String lastname, String phone, EmployeeService employeeService) {
        if (email.isEmpty()) {
            Notification.show("Email is empty");
        } else if (p1.isEmpty()) {
            Notification.show("Password is empty");
        } else if (!p1.equals(p2)) {
            Notification.show("Passwords are not equal");
        } else {
            List<Employee> listaEmpleados = employeeService.findAll();

            //List<Client> listaClientestTest = clientRepository.findAll();

            Boolean existeEmpleado = false;

            for (int i = 0; i < listaEmpleados.size() && existeEmpleado == false; i++) {
                if (listaEmpleados.get(i).getEmail() == email) {
                    existeEmpleado = true;
                }
            }

            if (existeEmpleado != true) {
                Employee newEmployee = new Employee();
                newEmployee.setUsername(username);
                newEmployee.setFirstName(firstName);
                newEmployee.setLastName(lastname);
                newEmployee.setPhoneNumber(phone);
                newEmployee.setEmail(email);
                newEmployee.setPassword(SecurityService.passwordEncoder().encode(p1));
                newEmployee.setRole("EMPLOYEE");
                employeeService.save(newEmployee);
                Notification.show("The user is registered");
                UI.getCurrent().navigate("internal");
            } else {
                Notification.show("The user already exists");

            }
        }
    }
}
