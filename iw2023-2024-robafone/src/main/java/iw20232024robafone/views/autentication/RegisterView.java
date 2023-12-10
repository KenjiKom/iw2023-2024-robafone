package iw20232024robafone.views.autentication;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.*;

@Route("register")
@PageTitle("Register | Robafone")
public class RegisterView extends VerticalLayout {

    public RegisterView(){

        //Set the layout to be centered in the page.
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        //Main title of the page.
        H1 title = new H1("Register in Robafone");

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
                event -> Register(validEmailField.getValue(), passwordField.getValue(), repeatPasswordField.getValue()));
        primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(title, validEmailField, passwordField, repeatPasswordField, primaryButton);
    }

    private void Register(String email, String p1, String p2){
        if(email.isEmpty()){
            Notification.show("Email is empty");
        }else if(p1.isEmpty()){
            Notification.show("Password is empty");
        } else if (!p1.equals(p2)) {
            Notification.show("Passwords are not equal");
        } else {
            Notification.show("The user is registered, change later");
        }
    }
}
