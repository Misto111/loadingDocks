package org.docks.loadingdocks.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.docks.loadingdocks.enums.BranchLocationEnum;
import org.docks.loadingdocks.enums.GoodsEnum;
import org.docks.loadingdocks.enums.VehicleType;
import org.docks.loadingdocks.model.entity.Schedule;
import org.docks.loadingdocks.model.service.BackButtonService;
import org.docks.loadingdocks.model.service.HolidayService;
import org.docks.loadingdocks.model.service.ScheduleService;
import org.docks.loadingdocks.model.service.TranslationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


@Route("create-schedule")
public class SchedulesView extends VerticalLayout {

    private final ScheduleService scheduleService;
    private final HolidayService holidayService;
    private ComboBox<VehicleType> vehicleTypeComboBox;
    private ComboBox<BranchLocationEnum> branchComboBox; // Заменяме String с BranchLocationEnum
    private ComboBox<GoodsEnum> goodsTypeComboBox;
    private NumberField linearMetersField;
    private DatePicker datePicker;
    private Button createScheduleButton;
    private Map<String, Double> goodsMeters = new HashMap<>();
    private Map<String, Checkbox> timeSlots = new LinkedHashMap<>();

    private final TranslationService translationService;

    public SchedulesView(ScheduleService scheduleService, HolidayService holidayService, TranslationService translationService) {
        this.scheduleService = scheduleService;
        this.holidayService = holidayService;
        this.translationService = translationService;

        Button backButton = BackButtonService.createBackButton("", translationService);// Empty for root navigation

        setWidthFull();
        setSpacing(true);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(createLogoLayout());
        add(backButton);
        add(createForm());
        add(createPortalGrid());

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

    private VerticalLayout createForm() {
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setWidth("50%");
        formLayout.setSpacing(true);
        formLayout.setPadding(true);
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // Групиране на "Вид превозно средство" и "Филиал"
        VerticalLayout vehicleAndBranchLayout = new VerticalLayout();
        vehicleAndBranchLayout.setSpacing(true);
        vehicleAndBranchLayout.setDefaultHorizontalComponentAlignment(Alignment.END);

        vehicleTypeComboBox = new ComboBox<>(translationService.getTranslation("Vehicle type"));
        vehicleTypeComboBox.setItems(VehicleType.values()); // Добавяме всички стойности на енума
        vehicleTypeComboBox.setItemLabelGenerator(vehicleType -> translationService.translateEnum(vehicleType)); // Превеждаме

        // Създаваме ComboBox за филиалите
        branchComboBox = new ComboBox<>(translationService.getTranslation("Branch"));
        branchComboBox.setItems(Arrays.asList(BranchLocationEnum.values()));
        branchComboBox.setItemLabelGenerator(branchLocation -> translationService.translateEnum(branchLocation)); // Превеждаме

        vehicleAndBranchLayout.add(vehicleTypeComboBox, branchComboBox);

        // Добавяме слушател за промени в branchComboBox. Това ще актуализира времевите слотове, ако има избрана дата.
        branchComboBox.addValueChangeListener(event -> {
            BranchLocationEnum selectedBranch = event.getValue();
            LocalDate selectedDate = datePicker.getValue();

            // Проверка дали има избрана дата и филиал и актуализиране на времевите слотове
            if (selectedBranch != null && selectedDate != null) {
                refreshPortalGrid(); // Актуализиране на времевите слотове
            }
        });

        // Групиране на "Вид стока", "Линейни метри" и "Добави стока"
        VerticalLayout goodsAndMetersLayout = new VerticalLayout();
        goodsAndMetersLayout.setSpacing(true);
        goodsAndMetersLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);



        //комбо за стоките
        goodsTypeComboBox = new ComboBox<>(translationService.getTranslation("Type of product"));
        goodsTypeComboBox.setItems(GoodsEnum.values());
        // Задаваме как да се показват имената на стоките
        goodsTypeComboBox.setItemLabelGenerator(goodsType -> translationService.translateEnum(goodsType));
        goodsTypeComboBox.getElement().getStyle().set("text-align", "center");

        linearMetersField = new NumberField(translationService.getTranslation("Linear meters"));
        linearMetersField.setMin(0);
        linearMetersField.setStep(0.1);

        Button addGoodsButton = new Button(translationService.getTranslation("Add the product"), event -> {
            GoodsEnum goodsType = goodsTypeComboBox.getValue(); // Сега е тип GoodsEnum
            Double meters = linearMetersField.getValue();

            if (goodsType == null || meters == null || meters <= 0) {
                Notification notification = Notification.show(translationService.getTranslation("Please enter valid product details and meters"));
                notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
                return;
            }

            goodsMeters.put(goodsType.name(), goodsMeters.getOrDefault(goodsType.name(), 0.0) + meters); // Използваме name() за съхранение на низ
            Notification notification = Notification.show(translationService.getTranslation("Added ")
                    + meters + translationService.getTranslation(" linear meters for ") + translationService.translateEnum(goodsType));
            notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
        });

        goodsAndMetersLayout.add(goodsTypeComboBox, linearMetersField, addGoodsButton);

        // Групиране на "Дата за разтоварване" и "Създай график"
        VerticalLayout centerLayout = new VerticalLayout();
        centerLayout.setSpacing(true);
        centerLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // Инициализация на datePicker
        datePicker = new DatePicker(translationService.getTranslation("Unloading date"));
        datePicker.setMin(LocalDate.now());

        // Добавяне на слушател за промени на datePicker
        datePicker.addValueChangeListener(event -> {
            LocalDate selectedDate = event.getValue();

            // Получаваме директно BranchLocationEnum
            BranchLocationEnum selectedBranch = branchComboBox.getValue();  // Вече е BranchLocationEnum


            // Проверка и обновяване на времевите слотове
            if (selectedDate != null && selectedBranch != null) {
                updateTimeSlotsForDate(selectedDate, selectedBranch);  // Използваме BranchLocationEnum директно
            }

            if (selectedDate != null && selectedBranch != null) {
                // Обновяваме визуализацията на слотовете
                refreshPortalGrid();

                // Проверка дали всички слотове са заети
                if (areAllSlotsOccupied()) {
                    Notification notification = Notification.show(translationService.getTranslation("No available portals on this date!"));
                    notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
                }
            }

            // Проверка за празник
            if (holidayService.isHoliday(selectedDate)) {
                Notification notification = Notification.show(translationService.getTranslation("The selected day is a holiday. Please choose another day!"));
                notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
                datePicker.clear();
            }
        });

        createScheduleButton = new Button(translationService.getTranslation("Create a schedule"), event -> createSchedule());
        createScheduleButton.setEnabled(false);

        centerLayout.add(datePicker, createScheduleButton);

        // Обща подредба на всички секции
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setWidthFull();
        mainLayout.setSpacing(true);
        mainLayout.add(goodsAndMetersLayout, centerLayout, vehicleAndBranchLayout);

        formLayout.add(mainLayout);
        return formLayout;
    }

    private VerticalLayout createPortalGrid() {
        VerticalLayout portalLayout = new VerticalLayout();
        portalLayout.setWidth("80%");
        portalLayout.setSpacing(true);
        portalLayout.getStyle().set("margin", "0 auto");
        portalLayout.getStyle().set("display", "grid");
        portalLayout.getStyle().set("grid-template-columns", "repeat(10, 1fr)");
        portalLayout.getStyle().set("gap", "20px");

        for (int hour = 8; hour < 18; hour++) {
            for (int j = 0; j < 4; j++) {
                String timeSlot = String.format("%02d:%02d", hour, j * 15);

                Checkbox portalCheckbox = new Checkbox(timeSlot);
                portalCheckbox.setWidth("80px");
                portalCheckbox.setHeight("30px");

                portalCheckbox.getElement().getStyle().set("background-color", "#d4edda"); // Green for available

                final String selectedTimeSlot = timeSlot;
                portalCheckbox.addValueChangeListener(event -> {
                    if (portalCheckbox.getValue()) {
                        markTimeSlots(selectedTimeSlot);
                        createScheduleButton.setEnabled(true);
                    } else {
                        unmarkTimeSlots();
                        createScheduleButton.setEnabled(false);
                    }
                });

                timeSlots.put(timeSlot, portalCheckbox);
                portalLayout.add(portalCheckbox);
            }
        }

        return portalLayout;
    }

//    private Grid<Schedule> createScheduleTable() {
//        Grid<Schedule> scheduleGrid = new Grid<>(Schedule.class);
//        scheduleGrid.setWidth("80%");
//        scheduleGrid.setColumns("vehicleType", "branch", "date", "startTime", "endTime", "reservedBy");
//        scheduleGrid.setItems(scheduleService.getAllSchedules());
//        return scheduleGrid;
//    }
private void createSchedule() {
    // Проверяваме дали всички полета са попълнени
    if (vehicleTypeComboBox.isEmpty() || branchComboBox.isEmpty() || datePicker.isEmpty() || goodsMeters.isEmpty()) {
        Notification notification = Notification.show(translationService.getTranslation("Please fill in all fields!"));
        notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
        return;
    }

    // Извличане на текущия потребител
    String currentUser = (String) VaadinSession.getCurrent().getAttribute("email");
    if (currentUser == null) {
        Notification notification = Notification.show(translationService.getTranslation("Please log in to create a schedule!"));
        notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра
        return;
    }

    // Вземаме избраната стойност за филиал от ComboBox
    BranchLocationEnum selectedBranch = branchComboBox.getValue();  // Вече взимаме стойността като BranchLocationEnum

    // Изчисляваме общото време за разтоварване
    int totalUnloadingTime = scheduleService.calculateUnloadingTime(goodsMeters);

    // Получаваме избрания начален час
    LocalTime startTime = getSelectedStartTime();
    LocalTime endTime = startTime.plusMinutes(totalUnloadingTime);

    // Създаваме нов график
    Schedule schedule = new Schedule();

    // Задаваме стойностите за полетата на графика
    schedule.setVehicleType(vehicleTypeComboBox.getValue()); // Превозно средство
    schedule.setBranch(selectedBranch);  // Филиал (от тип BranchLocationEnum)
    schedule.setGoods(goodsMeters); // Стоки
    schedule.setDate(datePicker.getValue()); // Дата
    schedule.setStartTime(startTime); // Начален час
    schedule.setEndTime(endTime); // Краен час
    schedule.setReservedBy(currentUser); // Кой е създал графика (потребителят)

    // Добавяме графика в базата
    scheduleService.addSchedule(schedule);

    // Показваме известие за успех
    Notification notification = Notification.show(translationService.getTranslation("The schedule has been created successfully!"));
    notification.setPosition(Notification.Position.TOP_CENTER); // Позиция: горе в центъра

    // Обновяваме времевите интервали в грид-а
    refreshPortalGrid();
}

    private boolean isUpdating = false;

    private void markTimeSlots(String startSlot) {
        if (isUpdating) {
            return; // Предотвратяваме повторно извикване
        }

        isUpdating = true; // Започваме операцията

        try {
            unmarkTimeSlots(); // Изчистваме преди маркиране

            LocalTime startTime = LocalTime.parse(startSlot);
            int totalUnloadingTime = scheduleService.calculateUnloadingTime(goodsMeters);
            LocalTime endTime = startTime.plusMinutes(totalUnloadingTime);

            timeSlots.forEach((timeSlot, checkbox) -> {
                LocalTime currentTime = LocalTime.parse(timeSlot);
                if (!currentTime.isBefore(startTime) && currentTime.isBefore(endTime)) {
                    checkbox.setValue(true);
                    checkbox.getElement().getStyle().set("background-color", "#f8d7da");
                }
            });
        } finally {
            isUpdating = false; // Завършваме операцията
        }
    }

    private void unmarkTimeSlots() {
        // Изчистваме всички чекбокси до първоначално състояние
        timeSlots.forEach((timeSlot, checkbox) -> {
            checkbox.setValue(false); // Деактивираме маркировката
            checkbox.getElement().getStyle().set("background-color", "#d4edda"); // Зелен фон за свободно
        });
    }

    private LocalTime getSelectedStartTime() {
        for (Map.Entry<String, Checkbox> entry : timeSlots.entrySet()) {
            if (entry.getValue().getValue()) {
                return LocalTime.parse(entry.getKey());
            }
        }
        return null;
    }

    private void updateTimeSlotsForDate(LocalDate selectedDate, BranchLocationEnum selectedBranch) {
        // Извличаме графици за избрания ден и филиал
        List<Schedule> schedules = scheduleService.getSchedulesByBranchAndDate(selectedBranch, selectedDate);

        // Нулираме стила на всички слотове
        timeSlots.forEach((timeSlot, checkbox) -> {
            checkbox.setEnabled(true); // Активираме всички
            checkbox.getElement().getStyle().set("background-color", "#d4edda"); // Зелен фон за свободен слот
        });

        // Маркираме заетите слотове
        for (Schedule schedule : schedules) {
            LocalTime startTime = schedule.getStartTime();
            LocalTime endTime = schedule.getEndTime();

            for (LocalTime time = startTime; !time.isAfter(endTime); time = time.plusMinutes(15)) {
                String timeSlot = time.toString();
                Checkbox checkbox = timeSlots.get(timeSlot);

                if (checkbox != null) {
                    checkbox.getElement().getStyle().set("background-color", "#f8d7da"); // Червен фон за зает слот
                    checkbox.setEnabled(false); // Забраняваме избора
                }
            }
        }
    }
    private void refreshPortalGrid() {
        LocalDate selectedDate = datePicker.getValue();
        BranchLocationEnum selectedBranch = branchComboBox.getValue();  // Вече получаваме директно BranchLocationEnum
        if (selectedDate != null && selectedBranch != null) {
            updateTimeSlotsForDate(selectedDate, selectedBranch);  // Използваме BranchLocationEnum директно
        }
    }

   //метод за промяна на имената от енъма.Друг вариант е с toString() в енъма - Ваадин ще го взема автоматично
    private String formatGoodsName(String enumName) {
        // Разделяме текста на базата на "_" и преобразуваме всяка дума
        String[] words = enumName.split("_");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            formattedName.append(word.charAt(0))
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }

        return formattedName.toString().trim();
    }

    private boolean areAllSlotsOccupied() {
        // Преглеждаме всички слотове от 8:00 до 18:00
        for (int hour = 8; hour < 18; hour++) {
            for (int j = 0; j < 4; j++) {
                String timeSlot = String.format("%02d:%02d", hour, j * 15);
                Checkbox checkbox = timeSlots.get(timeSlot);

                // Ако поне един чекбокс не е избран, връщаме false
                if (checkbox != null && !checkbox.getValue()) {
                    return false;
                }
            }
        }
        // Ако всички са заети
        return true;
    }

}
