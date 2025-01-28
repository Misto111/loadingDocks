package org.docks.loadingdocks.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.docks.loadingdocks.model.service.TranslationService;

@Route("")
@PageTitle("LoadingDocks - Home")
public class HomeView extends VerticalLayout {

    private final TranslationService translationService;
    private final com.vaadin.flow.component.html.H1 welcomeMessage;

    public HomeView(TranslationService translationService) {
        this.translationService = translationService;

        // Центрираме останалата част от съдържанието
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);



        // Създаване на H1 компонент за съобщението
        welcomeMessage = new com.vaadin.flow.component.html.H1();
        updateWelcomeMessage();


        // Регистрация като слушател за промяна на езика
        translationService.addLanguageChangeListener(locale -> {
            UI.getCurrent().access(this::updateWelcomeMessage);
        });
        //add(createHomeLayout());

        if (isLoggedIn()) {
            // Ако потребителят е логнат

            add(createLogoLayout());

            NavbarComponent navbar = new NavbarComponent( translationService);
            add(navbar);

            add(createHomeLayout());


        } else {
            // Ако потребителят не е логнат

            add(welcomeMessage, createLogoLayout());

            NavbarComponent navbar = new NavbarComponent(translationService);
            add(navbar);

            add(createHomeLayout());
        }
    }

    private void updateWelcomeMessage() {
        String translatedText = translationService.getTranslation("Welcome to Loading Docks!");
        welcomeMessage.setText(translatedText);
    }

    private HorizontalLayout createLogoLayout() {
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setWidthFull();
        logoLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        logoLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        logoLayout.setSpacing(true);


        com.vaadin.flow.component.html.Image logo1 = new com.vaadin.flow.component.html.Image("/images/aiko.png", "Logo 1");
        logo1.setWidth("80px");
        logo1.setHeight("50px");

        com.vaadin.flow.component.html.Image logo2 = new com.vaadin.flow.component.html.Image("/images/momaxx.png", "Logo 2");
        logo2.setWidth("80px");
        logo2.setHeight("50px");

        logoLayout.add(logo1, logo2);

        return logoLayout;
    }

    private HorizontalLayout createHomeLayout() {
        HorizontalLayout homeLayout = new HorizontalLayout();
        homeLayout.setWidthFull();
        homeLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        homeLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        homeLayout.setSpacing(true);

        com.vaadin.flow.component.html.Image truck1 = new com.vaadin.flow.component.html.Image("/images/truck1.jpg", "Truck 1");
        truck1.setWidth("100%");
        truck1.setHeight("100%");

        truck1.getStyle().set("object-fit", "cover");

        homeLayout.add(truck1);


        return homeLayout;
    }

    private boolean isLoggedIn() {
        // Проверяваме дали има записан email в сесията
        return VaadinSession.getCurrent().getAttribute("email") != null;
    }

//    private String getTranslation(String key) {
//        Locale currentLocale = VaadinSession.getCurrent().getAttribute(Locale.class);
//        if (currentLocale == null) {
//            currentLocale = Locale.ENGLISH; // по подразбиране
//        }
//        return i18NProvider.getTranslation(key, currentLocale);
//    }

}