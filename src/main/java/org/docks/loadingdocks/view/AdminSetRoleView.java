package org.docks.loadingdocks.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.docks.loadingdocks.enums.Role;
import org.docks.loadingdocks.model.entity.DriverEntity;
import org.docks.loadingdocks.model.service.BackButtonService;
import org.docks.loadingdocks.model.service.DriverService;
import org.docks.loadingdocks.model.service.TranslationService;

import java.util.List;

@Route("admin-set-role")
public class AdminSetRoleView extends VerticalLayout {

    private final DriverService driverService;
    private Grid<DriverEntity> userGrid = new Grid<>(DriverEntity.class);
    private Button saveRolesButton;

    private final TranslationService translationService;

    public AdminSetRoleView(DriverService driverService, TranslationService translationService) {
        this.driverService = driverService;
        this.translationService = translationService;

        // Задаваме размер на целия layout
        setSizeFull();

        configureUserGrid();

        Button backButton = BackButtonService.createBackButton("", translationService); // Empty for root navigation

        saveRolesButton = new Button(translationService.getTranslation("saveChanges"));
        saveRolesButton.addClickListener(e -> saveRoleChanges());
        saveRolesButton.getStyle().set("margin-left", "auto"); // Избутва бутона "Back" вдясно

        // Добавяме таблицата и бутона
        add(backButton, saveRolesButton, userGrid);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Зареждаме потребителите
        loadUsers();
    }

    private void configureUserGrid() {
        // Премахваме автоматично създадените колони
        userGrid.removeAllColumns();

        // Добавяме колони ръчно с преводи за заглавията
        userGrid.addColumn(DriverEntity::getId)
                .setHeader(translationService.getTranslation("id"))
                .setKey("id");

        userGrid.addColumn(DriverEntity::getEmail)
                .setHeader(translationService.getTranslation("email"))
                .setKey("email");

        userGrid.addColumn(DriverEntity::getFirstName)
                .setHeader(translationService.getTranslation("firstName"))
                .setKey("firstName");

        userGrid.addColumn(DriverEntity::getLastName)
                .setHeader(translationService.getTranslation("lastName"))
                .setKey("lastName");

        userGrid.addColumn(DriverEntity::getPassword)
                .setHeader(translationService.getTranslation("password"))
                .setKey("password");

        userGrid.addColumn(DriverEntity::getPhoneNumber)
                .setHeader(translationService.getTranslation("phoneNumber"))
                .setKey("phoneNumber");

        userGrid.addColumn(DriverEntity::getRole)
                .setHeader(translationService.getTranslation("role"))
                .setKey("role");

        userGrid.addColumn(driver -> driver.getFirstName() + " " + driver.getLastName())
                .setHeader(translationService.getTranslation("fullName"))
                .setKey("fullName");

        // Добавяме чекбоксове за ролите
        for (Role role : Role.values()) {
            userGrid.addComponentColumn(user -> {
                Checkbox checkbox = new Checkbox();
                checkbox.setValue(user.getRole() == role);
                checkbox.addValueChangeListener(event -> {
                    if (event.getValue()) {
                        user.setRole(role);
                    }
                });
                return checkbox;
            }).setHeader(translationService.getTranslation(role.name().toLowerCase()));
        }

        // Задаваме размер на таблицата
        userGrid.setSizeFull();
        userGrid.setHeight("600px");
    }

    private void loadUsers() {
        try {
            List<DriverEntity> users = driverService.getAllDrivers();
            userGrid.setItems(users);
        } catch (Exception ex) {
            Notification notification = Notification.show(translationService.getTranslation("Error loading users: ") + ex.getMessage());
            notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
        }
    }

    private void saveRoleChanges() {
        List<DriverEntity> updatedUsers = userGrid.getListDataView().getItems().toList();
        try {
            for (DriverEntity user : updatedUsers) {
                driverService.saveDriver(user);
            }
            Notification notification = Notification.show(translationService.getTranslation("Roles updated successfully."));
            notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра

        } catch (Exception ex) {
            Notification notification = Notification.show(translationService.getTranslation("Error saving roles: ") + ex.getMessage());
            notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
        }
    }
}
