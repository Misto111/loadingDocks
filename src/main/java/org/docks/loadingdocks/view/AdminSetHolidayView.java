package org.docks.loadingdocks.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.docks.loadingdocks.model.entity.Holiday;
import org.docks.loadingdocks.model.service.BackButtonService;
import org.docks.loadingdocks.model.service.HolidayService;
import org.docks.loadingdocks.model.service.TranslationService;

import java.time.LocalDate;
import java.util.List;

@Route("admin-set-holiday")
public class AdminSetHolidayView extends VerticalLayout {

    private final HolidayService holidayService;
    private final DatePicker holidayPicker;
    private final Grid<Holiday> holidayGrid;

    private final TranslationService translationService;

    public AdminSetHolidayView(HolidayService holidayService, TranslationService translationService) {
        this.holidayService = holidayService;
        this.translationService = translationService;

        Button backButton = BackButtonService.createBackButton("", translationService); // Empty for root navigation

        // Центрираме останалата част от съдържанието
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        setSizeFull();
        setSpacing(true);

        holidayPicker = new DatePicker(translationService.getTranslation("Choose a day off"));
        holidayPicker.setMin(LocalDate.now());

        Button addHolidayButton = new Button(translationService.getTranslation("Add a day off"), event -> addHoliday());
        Button deleteHolidayButton = new Button(translationService.getTranslation("Delete selected day"), event -> deleteHoliday());

        holidayGrid = new Grid<>(Holiday.class);
        configureHolidayGrid();

        add(backButton, holidayPicker, addHolidayButton, deleteHolidayButton, holidayGrid);

        loadHolidays();

        // Маркиране на почивните дни в календара
        holidayPicker.addValueChangeListener(event -> highlightHolidays());
    }

    private void configureHolidayGrid() {
        // Премахваме автоматично създадените колони
        holidayGrid.removeAllColumns();
        //по този начин задаваме таблицата, за да не се бърка базата данни от превода
        holidayGrid.addColumn(Holiday::getDate)
                .setHeader(translationService.getTranslation("date"))
                .setKey("date");

        //Преди превода бяха тези 4 реда отдолу, включително закоментираните
//        holidayGrid.setColumns("date");
//        holidayGrid.setSizeFull();
        holidayGrid.addSelectionListener(selectionEvent -> {
            selectionEvent.getFirstSelectedItem().ifPresent(holiday -> holidayPicker.setValue(holiday.getDate()));
        });
    }

    private void addHoliday() {
        LocalDate selectedDate = holidayPicker.getValue();

        if (selectedDate == null) {
            Notification notification = Notification.show(translationService.getTranslation("Please select a date"));
            notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
            return;
        }

        try {
            Holiday holiday = new Holiday();
            holiday.setDate(selectedDate);

            holidayService.addHoliday(holiday);
            Notification notification = Notification.show(translationService.getTranslation("The holiday has been added successfully"));
            notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
            loadHolidays();
            holidayPicker.clear();

        } catch (Exception e) {
            Notification notification = Notification.show(translationService.getTranslation("Error: ") + e.getMessage());
            notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
        }
    }

    private void deleteHoliday() {
        Holiday selectedHoliday = holidayGrid.asSingleSelect().getValue();

        if (selectedHoliday == null) {
            Notification notification = Notification.show(translationService.getTranslation("Please select a holiday to delete"));
            notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
            return;
        }

        try {
            holidayService.deleteHoliday(selectedHoliday.getId());
            Notification notification = Notification.show(translationService.getTranslation("The holiday has been deleted successfully"));
            notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
            loadHolidays();
        } catch (Exception e) {
            Notification.show(translationService.getTranslation(translationService.getTranslation("Error: ")) + e.getMessage());
        }
    }

    private void loadHolidays() {
        try {
            List<Holiday> holidays = holidayService.getAllHolidays();
            holidayGrid.setItems(holidays);
        } catch (Exception e) {
            Notification notification = Notification.show(translationService.getTranslation("Error loading holidays: ") + e.getMessage());
            notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
        }
    }

    private void highlightHolidays() {
        List<Holiday> holidays = holidayService.getAllHolidays();

        // Изчистваме всички предишни стилизации
        holidayPicker.getElement().executeJs(
                "setTimeout(() => {"+
                        "  this.shadowRoot.querySelectorAll('[part=\"cell\"]').forEach(function(cell) {"+
                        "    cell.style.backgroundColor = '';"+
                        "    cell.style.color = '';"+
                        "  });"+
                        "}, 200);"
        );

        // Добавяне на стилизация за почивните дни
        for (Holiday holiday : holidays) {
            LocalDate holidayDate = holiday.getDate();

            holidayPicker.getElement().executeJs(
                    "setTimeout(() => {"+
                            "  this.shadowRoot.querySelector('[part=\"cell\"][date=\"" + holidayDate + "\"]').style.backgroundColor = 'red';"+
                            "}, 200);"
            );
        }
    }
}