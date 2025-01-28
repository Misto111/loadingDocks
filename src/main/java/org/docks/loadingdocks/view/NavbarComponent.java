package org.docks.loadingdocks.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;
import org.docks.loadingdocks.model.service.TranslationService;

import java.util.Locale;

public class NavbarComponent extends HorizontalLayout {

    private final TranslationService translationService;

    private Button profileButton;
    private Button createScheduleButton;
    private Button adminPanelButton;
    private Button branchesButton;
    private Button logoutButton;
    private Button loginButton;
    private Button registerButton;
    private Button helpButton;

    private ComboBox<String> languageSelector;

    public NavbarComponent(TranslationService translationService) {
        this.translationService = translationService;

        // Настройка на компоненти
        initComponents();

        // Добавяне на бутони според състоянието на сесията
        setupButtons();
    }

    private void initComponents() {
        setWidthFull();
        setSpacing(true);
        setPadding(true);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.BETWEEN);

        // Създаване и настройка на езиковия селектор
        languageSelector = new ComboBox<>(translationService.getTranslation("Language"));
        languageSelector.setItems("English", "Български");

        String currentLanguage = VaadinSession.getCurrent().getAttribute(Locale.class) != null
                ? VaadinSession.getCurrent().getAttribute(Locale.class).getLanguage()
                : Locale.ENGLISH.getLanguage();
        languageSelector.setValue(currentLanguage.equals("bg") ? "Български" : "English");

        // Позициониране на селектора за език горе вдясно
        languageSelector.setWidth("150px");
        languageSelector.getStyle()
                .set("position", "absolute")
                .set("top", "10px")
                .set("right", "10px");
        languageSelector.addValueChangeListener(event -> changeLanguage(event.getValue()));

        // Инициализация на бутоните
        profileButton = new Button();
        createScheduleButton = new Button();
        adminPanelButton = new Button();
        branchesButton = new Button();
        logoutButton = new Button();
        loginButton = new Button();
        registerButton = new Button();
        helpButton = new Button();

        // Обновяване на текста на бутоните
        updateButtonTexts();

        // Добавяне на езиковия селектор директно към layout-а
        add(languageSelector);
    }

    private void setupButtons() {
        if (isLoggedIn()) {
            profileButton.addClickListener(e -> navigateTo("profile"));
            createScheduleButton.addClickListener(e -> navigateTo("create-schedule"));
            branchesButton.addClickListener(e -> navigateToBranches());
            helpButton.addClickListener(e -> navigateTo("help"));
            logoutButton.addClickListener(e -> logout());

            String role = (String) VaadinSession.getCurrent().getAttribute("role");
            if ("Admin".equals(role)) {
                adminPanelButton.addClickListener(e -> navigateToAdmin());
                adminPanelButton.getStyle().set("color", "green");
                add(adminPanelButton);
            }

            logoutButton.getStyle().set("color", "red");
            add(profileButton, createScheduleButton, branchesButton, helpButton, logoutButton, languageSelector);
        } else {
            loginButton.addClickListener(e -> navigateTo("login"));
            registerButton.addClickListener(e -> navigateTo("register"));
            add(loginButton, registerButton, languageSelector);
        }
    }

    private void updateButtonTexts() {
        profileButton.setText(translationService.getTranslation("profile"));
        createScheduleButton.setText(translationService.getTranslation("create_schedule"));
        adminPanelButton.setText(translationService.getTranslation("admin_panel"));
        branchesButton.setText(translationService.getTranslation("branches"));
        logoutButton.setText(translationService.getTranslation("logout"));
        loginButton.setText(translationService.getTranslation("login"));
        registerButton.setText(translationService.getTranslation("register"));
        helpButton.setText(translationService.getTranslation("help"));
    }

    private void changeLanguage(String language) {
        translationService.changeLanguage(language);
        updateButtonTexts(); // Обновява текста на бутоните
    }

    private boolean isLoggedIn() {
        return VaadinSession.getCurrent().getAttribute("email") != null;
    }

    private void logout() {
        VaadinSession.getCurrent().setAttribute("email", null);
        VaadinSession.getCurrent().setAttribute("role", null);
        getUI().ifPresent(ui -> ui.getPage().reload());
    }

    private void navigateTo(String route) {
        getUI().ifPresent(ui -> ui.navigate(route));
    }

    private void navigateToAdmin() {
        getUI().ifPresent(ui -> ui.navigate("admin-panel"));
    }

    private void navigateToBranches() {
        getUI().ifPresent(ui -> ui.navigate("branches"));
    }
}