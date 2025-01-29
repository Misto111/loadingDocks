package org.docks.loadingdocks.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.docks.loadingdocks.model.service.BackButtonService;
import org.docks.loadingdocks.model.service.DriverService;
import org.docks.loadingdocks.model.service.TranslationService;

@Route("login")
public class LoginView extends VerticalLayout {

    private TextField emailField = new TextField("Email");
    private PasswordField passwordField = new PasswordField("Password");
    private Button loginButton = new Button("Login");
    private final TranslationService translationService;
    private DriverService driverService;

    public LoginView(TranslationService translationService, DriverService driverService) {
        this.translationService = translationService;
        this.driverService = driverService;

        emailField = new TextField(translationService.getTranslation("email"));
        passwordField = new PasswordField(translationService.getTranslation("password"));
        loginButton = new Button(translationService.getTranslation("login"));

        Button backButton = BackButtonService.createBackButton("", translationService);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginButton.addClickListener(e -> login());

        add(backButton, emailField, passwordField, loginButton);
    }

    private void login() {
        String email = emailField.getValue();
        String password = passwordField.getValue();

        if (driverService.authenticateDriver(email, password)) {
            Notification.show("Login successful", 3000, Notification.Position.TOP_CENTER);
            getUI().ifPresent(ui -> ui.navigate(""));
        } else {
            Notification notification = Notification.show("Invalid email or password", 3000, Notification.Position.TOP_CENTER);
        }
    }
}