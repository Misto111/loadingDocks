package org.docks.loadingdocks.model.service;

import com.vaadin.flow.component.button.Button;

public class BackButtonService {

    public static Button createBackButton(String navigationTarget, TranslationService translationService) {
        String backButtonText = translationService.getTranslation("back");

        Button backButton = new Button(backButtonText);
        backButton.addClickListener(e -> backButton.getUI().ifPresent(ui ->
                ui.getPage().executeJs("window.history.back()")));
        backButton.getStyle().set("margin-right", "auto"); // Избутва бутона "Back" вляво

        return backButton;
    }
}
