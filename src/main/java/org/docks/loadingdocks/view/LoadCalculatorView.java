package org.docks.loadingdocks.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("load-calculator")
@PageTitle("TruckGates - Load Calculator")
public class LoadCalculatorView extends VerticalLayout {

    private final TextField linearMetersField = new TextField("Enter Linear Meters");
    private final Button calculateButton = new Button("Calculate Unload Time");
    private final Button backButton = new Button("Back");

    public LoadCalculatorView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);

        calculateButton.addClickListener(e -> calculateUnloadTime());
        // Използване на JavaScript за връщане назад
        backButton.addClickListener(e -> backButton.getUI().ifPresent(ui ->
                ui.getPage().executeJs("window.history.back()")));

        add(linearMetersField, calculateButton, backButton);
    }

    private void calculateUnloadTime() {
        try {
            String input = linearMetersField.getValue();
            if (input.isEmpty()) {
                Notification.show("Please enter a value!", 3000, Notification.Position.MIDDLE);
                return;
            }

            double linearMeters = Double.parseDouble(input);
            double unloadTime = calculateTimeFromLinearMeters(linearMeters);

            String formattedTime = formatTime(unloadTime);

            Notification.show("Estimated Unload Time: " + formattedTime, 5000, Notification.Position.MIDDLE);
        } catch (NumberFormatException e) {
            Notification.show("Invalid input! Please enter a numeric value.", 3000, Notification.Position.MIDDLE);
        }
    }

    private double calculateTimeFromLinearMeters(double linearMeters) {
        // Примерно правило: всеки линеен метър отнема 5 минути
        double minutesPerLinearMeter = 5.0;
        return linearMeters * minutesPerLinearMeter;
    }

    private String formatTime(double totalMinutes) {
        int hours = (int) totalMinutes / 60;
        int minutes = (int) totalMinutes % 60;

        if (hours > 0 && minutes > 0) {
            return hours + " hours and " + minutes + " minutes";
        } else if (hours > 0) {
            return hours + " hours";
        } else {
            return minutes + " minutes";
        }
    }
}