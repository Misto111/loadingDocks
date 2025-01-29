package org.docks.loadingdocks.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.docks.loadingdocks.enums.Role;
import org.docks.loadingdocks.model.entity.DriverEntity;
import org.docks.loadingdocks.model.service.BackButtonService;
import org.docks.loadingdocks.model.service.DriverService;
import org.docks.loadingdocks.model.service.TranslationService;

@Route("register")
public class RegisterView extends VerticalLayout {

    private final DriverService driverService;
    private final TranslationService translationService;

    private TextField firstNameField;
    private TextField lastNameField;
    private PasswordField passwordField;
    private PasswordField  confirmPasswordField;
    private TextField emailField;
    private TextField phoneNumberField;
    private Button submitButton;

    public RegisterView(DriverService driverService, TranslationService translationService) {
        this.driverService = driverService;
        this.translationService = translationService;

        firstNameField = new TextField(translationService.getTranslation("firstName"));
        lastNameField = new TextField(translationService.getTranslation("lastName"));
        passwordField = new PasswordField(translationService.getTranslation("password"));
        confirmPasswordField = new PasswordField(translationService.getTranslation("confirmPassword"));
        emailField = new TextField(translationService.getTranslation("email"));
        phoneNumberField = new TextField(translationService.getTranslation("phoneNumber"));
        submitButton = new Button(translationService.getTranslation("register"));

        Button backButton = BackButtonService.createBackButton("", translationService);

        submitButton.addClickListener(e -> registerDriver());
        add(backButton, firstNameField, lastNameField, passwordField, confirmPasswordField, emailField, phoneNumberField, submitButton);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void registerDriver() {
        String firstName = firstNameField.getValue();
        String lastName = lastNameField.getValue();
        String password = passwordField.getValue();
        String confirmPassword = confirmPasswordField.getValue();
        String email = emailField.getValue();
        String phoneNumber = phoneNumberField.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Notification.show("All fields are required!", 3000, Notification.Position.TOP_CENTER);
            return;
        }

        if (!password.equals(confirmPassword)) {
            Notification.show("Passwords do not match!", 3000, Notification.Position.TOP_CENTER);
            return;
        }

        if (password.length() < 6) {
            Notification.show("Password must be at least 6 characters long.", 3000, Notification.Position.TOP_CENTER);
            return;
        }

        if (driverService.existByEmail(email)) {
            Notification.show("Email is already registered.", 3000, Notification.Position.TOP_CENTER);
            return;
        }

        if (driverService.existByPhoneNumber(phoneNumber)) {
            Notification.show("Phone number is already registered.", 3000, Notification.Position.TOP_CENTER);
            return;
        }

        DriverEntity driver = new DriverEntity(firstName, lastName, password, phoneNumber, email, Role.USER);
        driverService.saveDriver(driver);

        Notification.show("Driver registered successfully!",3000, Notification.Position.TOP_CENTER);
        UI.getCurrent().navigate("login");
    }
}