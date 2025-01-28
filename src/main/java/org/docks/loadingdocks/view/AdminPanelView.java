package org.docks.loadingdocks.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.docks.loadingdocks.model.service.BackButtonService;
import org.docks.loadingdocks.model.service.TranslationService;

@Route("admin-panel")
public class AdminPanelView extends VerticalLayout {

    private final TranslationService translationService;
    private Button allSchedulesButton;
    private Button setRoleButton;
    private Button setHolidayButton;

    public AdminPanelView(TranslationService translationService) {
        this.translationService = translationService;


        Button backButton = BackButtonService.createBackButton("", translationService); // Empty for root navigation

        // Инициализация на компонентите с превод
        allSchedulesButton = new Button(translationService.getTranslation("allSchedules"));
        setRoleButton = new Button(translationService.getTranslation("setRole"));
        setHolidayButton = new Button(translationService.getTranslation("setHoliday"));

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        allSchedulesButton.addClickListener(e -> navigateToАllSchedules());
        setRoleButton.addClickListener(е-> navigateToSetRole());
        setHolidayButton.addClickListener(e -> navigateToSetHoliday());


//        // Бутон за управление на почивни дни
//        Button setHolidayButton = new Button("Set Holiday", event ->
//                getUI().ifPresent(ui -> ui.navigate("admin-set-holiday"))
//
//        );


        add(backButton, allSchedulesButton, setRoleButton, setHolidayButton);
    }

    private void navigateToАllSchedules() {
        getUI().ifPresent(ui -> ui.navigate("all-schedules"));
    }

    private void navigateToSetRole() {
        getUI().ifPresent(ui -> ui.navigate("admin-set-role"));
    }

    private void navigateToSetHoliday() {
        getUI().ifPresent(ui -> ui.navigate("admin-set-holiday"));
    }

}