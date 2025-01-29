package org.docks.loadingdocks.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.docks.loadingdocks.model.entity.DriverEntity;
import org.docks.loadingdocks.model.entity.Schedule;
import org.docks.loadingdocks.model.service.BackButtonService;
import org.docks.loadingdocks.model.service.DriverService;
import org.docks.loadingdocks.model.service.TranslationService;

import java.time.LocalDate;
import java.util.List;


@Route("profile")
public class ProfileView extends VerticalLayout {

    private final TranslationService translationService;

    private final DriverService driverService;

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField phoneNumberField;

    private PasswordField newPasswordField;
    private Button changePasswordButton;

    private Button saveButton;
    private Button scheduleButton;



    public ProfileView(TranslationService translationService, DriverService driverService) {
        this.translationService = translationService;
        this.driverService = driverService;

        firstNameField = new TextField(translationService.getTranslation("First Name"));
        lastNameField = new TextField(translationService.getTranslation("Last Name"));
        emailField = new TextField(translationService.getTranslation("Email"));
        phoneNumberField = new TextField(translationService.getTranslation("Phone Number"));

        newPasswordField = new PasswordField(translationService.getTranslation("New Password"));
        newPasswordField.setPlaceholder(translationService.getTranslation("Enter new password"));

        changePasswordButton = new Button(translationService.getTranslation("Change Password"));
        saveButton = new Button(translationService.getTranslation("Save All Changes"));
        scheduleButton = new Button(translationService.getTranslation("My Schedule"));

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);

        String email = (String) VaadinSession.getCurrent().getAttribute("email");
        if (email == null) {
            navigateToLogin();
        } else {
            loadDriverData(email);
            configureButtons();
            addComponents();
        }
    }

    private void navigateToLogin() {
        Notification.show(translationService.getTranslation("Please log in first!"), 3000, Notification.Position.TOP_CENTER);
        getUI().ifPresent(ui -> ui.navigate("login"));
    }

    private void loadDriverData(String email) {
        DriverEntity driver = driverService.getDriverByUsername(email);
        if (driver != null) {
            firstNameField.setValue(driver.getFirstName());
            lastNameField.setValue(driver.getLastName());
            emailField.setValue(driver.getEmail());
            emailField.setEnabled(false);
            phoneNumberField.setValue(driver.getPhoneNumber());
        }
    }

    private void configureButtons() {
        saveButton.addClickListener(event -> saveChanges());
        scheduleButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("schedule")));

        changePasswordButton.addClickListener(event -> changePassword());
    }

    private void addComponents() {
        Button backButton = BackButtonService.createBackButton("", translationService);
        add(
                backButton,
                firstNameField,
                lastNameField,
                emailField,
                phoneNumberField,
                newPasswordField,
                changePasswordButton,
                saveButton,
                scheduleButton

        );
    }

    private void saveChanges() {
        try {
            String email = emailField.getValue();
            DriverEntity driver = driverService.getDriverByUsername(email);
            if (driver != null) {
                driver.setFirstName(firstNameField.getValue());
                driver.setLastName(lastNameField.getValue());
                driver.setPhoneNumber(phoneNumberField.getValue());
                driverService.saveDriver(driver);
                updateSessionAttributes(driver);
                Notification.show(translationService.getTranslation("Profile updated successfully!"), 3000, Notification.Position.TOP_CENTER);
            }
        } catch (Exception e) {
            Notification.show(translationService.getTranslation("Error updating profile: ") + e.getMessage(), 5000, Notification.Position.TOP_CENTER);
        }
    }

    private void changePassword() {
        try {
            String newPassword = newPasswordField.getValue();
            if (newPassword == null || newPassword.trim().isEmpty() || newPassword.length() < 6) {
                Notification.show(translationService.getTranslation("Password must be at least 6 characters long!"), 5000, Notification.Position.TOP_CENTER);
                return;
            }

            String email = emailField.getValue();
            DriverEntity driver = driverService.getDriverByUsername(email);
            if (driver != null) {
                driver.setPassword(newPassword);
                driverService.saveDriver(driver);
                Notification.show(translationService.getTranslation("Password changed successfully!"), 3000, Notification.Position.TOP_CENTER);
                newPasswordField.clear();
            }
        } catch (Exception e) {
            Notification.show(translationService.getTranslation("Error changing password: ") + e.getMessage(), 5000, Notification.Position.TOP_CENTER);
        }
    }

    private void updateSessionAttributes(DriverEntity driver) {
        VaadinSession.getCurrent().setAttribute("firstName", driver.getFirstName());
        VaadinSession.getCurrent().setAttribute("lastName", driver.getLastName());
        VaadinSession.getCurrent().setAttribute("phoneNumber", driver.getPhoneNumber());
    }
}

@Route("schedule")
class ScheduleView extends VerticalLayout {

    private final TranslationService translationService;

    private final DriverService driverService;
    private final Grid<Schedule> grid = new Grid<>(Schedule.class);

    public ScheduleView(TranslationService translationService, DriverService driverService) {
        this.translationService = translationService;
        this.driverService = driverService;

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);



        String email = (String) VaadinSession.getCurrent().getAttribute("email");
        if (email == null) {
            navigateToLogin();
        } else {
            configureGrid();
            loadDriverSchedule(email);
        }
    }

    private void navigateToLogin() {
        Notification.show(translationService.getTranslation("Please log in first!"), 3000, Notification.Position.TOP_CENTER);
        getUI().ifPresent(ui -> ui.navigate("login"));
    }

    private void configureGrid() {
        grid.removeAllColumns();
        grid.addColumn(Schedule::getDate).setHeader(translationService.getTranslation("Date")).setAutoWidth(true).setFlexGrow(1);
        grid.addColumn(Schedule::getStartTime).setHeader(translationService.getTranslation("Start Time")).setAutoWidth(true).setFlexGrow(1);
        grid.addColumn(Schedule::getEndTime).setHeader(translationService.getTranslation("End Time")).setAutoWidth(true).setFlexGrow(1);
        grid.addColumn(Schedule::getReservedBy).setHeader(translationService.getTranslation("Reserved By")).setAutoWidth(true).setFlexGrow(1);
        grid.addColumn(slot -> slot.getBranch() != null ? translationService.translateEnum(slot.getBranch()) : "N/A").setHeader(translationService.getTranslation("Branch")).setAutoWidth(true).setFlexGrow(1);

        grid.addComponentColumn(slot -> {
            Button deleteButton = new Button(translationService.getTranslation("Delete"), event -> deleteSlot(slot));
            deleteButton.getStyle().set("color", "red");
            return deleteButton;
        }).setHeader(translationService.getTranslation("Actions")).setAutoWidth(true).setFlexGrow(1);

        grid.setWidthFull();
        grid.setHeightFull();
        grid.getStyle().set("flex-grow", "1");

        Button backButton = BackButtonService.createBackButton("", translationService);

        setSizeFull();
        add(backButton, grid);
    }

    private void deleteSlot(Schedule slot) {
        try {
            driverService.deleteDeliverySlot(slot);
            Notification.show(translationService.getTranslation("Schedule deleted successfully!"), 3000, Notification.Position.TOP_CENTER);
            refreshGrid();
        } catch (Exception e) {
            Notification.show(translationService.getTranslation("Error deleting schedule: ") + e.getMessage(), 5000, Notification.Position.TOP_CENTER);
        }
    }

    private void refreshGrid() {
        String email = (String) VaadinSession.getCurrent().getAttribute("email");
        if (email != null) {
            List<Schedule> slots = driverService.getDriverSchedule(email);
            grid.setItems(slots);
        }
    }

    private void loadDriverSchedule(String email) {
        try {
            List<Schedule> slots = driverService.getDriverSchedule(email);
            LocalDate today = LocalDate.now();
            List<Schedule> filteredSlots = slots.stream()
                    .filter(slot -> !slot.getDate().isBefore(today))
                    .toList();

            if (filteredSlots.isEmpty()) {
                Notification.show(translationService.getTranslation("You have no upcoming deliveries"), 3000, Notification.Position.TOP_CENTER);
            }

            grid.setItems(filteredSlots);
        } catch (Exception e) {
            Notification.show(translationService.getTranslation("Error loading schedule: ") + e.getMessage(), 5000, Notification.Position.TOP_CENTER);
        }
    }


}