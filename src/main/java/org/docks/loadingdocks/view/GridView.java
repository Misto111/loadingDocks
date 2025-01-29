package org.docks.loadingdocks.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.docks.loadingdocks.model.entity.Schedule;
import org.docks.loadingdocks.model.service.BackButtonService;
import org.docks.loadingdocks.model.service.ScheduleService;
import org.docks.loadingdocks.model.service.TranslationService;

import java.util.List;

@Route("all-schedules")
@PageTitle("Table")
public class GridView extends VerticalLayout {

    private final Grid<Schedule> scheduleGrid = new Grid<>();
    private final TranslationService translationService;
    private Button deleteButton;

    private final ScheduleService scheduleService;
    private final DatePicker datePicker = new DatePicker("Choose a date");
    private final ComboBox<String> branchComboBox = new ComboBox<>("Choose a branch");

    public GridView(TranslationService translationService, ScheduleService scheduleService) {
        this.translationService = translationService;
        this.scheduleService = scheduleService;

        Button backButton = BackButtonService.createBackButton("", translationService);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        setUpGrid();
        add(backButton, scheduleGrid);
        loadSchedules();
    }

    private void setUpGrid() {
        scheduleGrid.addColumn(schedule -> translationService.translateEnum(schedule.getVehicleType()))
                .setHeader(translationService.getTranslation("vehicleType"))
                .setKey("vehicleType")
                .setWidth("150px");

        scheduleGrid.addColumn(schedule -> translationService.translateEnum(schedule.getBranch()))
                .setHeader(translationService.getTranslation("branch"))
                .setKey("branch")
                .setWidth("150px");

        scheduleGrid.addColumn(Schedule::getDate)
                .setHeader(translationService.getTranslation("date"))
                .setKey("date")
                .setWidth("100px");

        scheduleGrid.addColumn(Schedule::getStartTime)
                .setHeader(translationService.getTranslation("startTime"))
                .setKey("startTime")
                .setWidth("100px");

        scheduleGrid.addColumn(Schedule::getEndTime)
                .setHeader(translationService.getTranslation("endTime"))
                .setKey("endTime")
                .setWidth("100px");

        scheduleGrid.addColumn(Schedule::getReservedBy)
                .setHeader(translationService.getTranslation("reservedBy"))
                .setKey("reservedBy")
                .setWidth("150px");

        scheduleGrid.addComponentColumn(schedule -> {
            deleteButton = new Button(translationService.getTranslation("delete"));
            deleteButton.getStyle().set("color", "red");
            deleteButton.addClickListener(click -> {
                scheduleService.deleteSchedule(schedule.getId());
                refreshSchedules();
            });
            return deleteButton;
        }).setHeader(translationService.getTranslation("actions"));

        scheduleGrid.getColumns().forEach(column -> column.setAutoWidth(true));
    }

    private void loadSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        scheduleGrid.setItems(schedules);
    }

    private void refreshSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        scheduleGrid.setItems(schedules);
    }
}
