package org.docks.loadingdocks.config;

import com.vaadin.flow.i18n.I18NProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CustomI18NProvider implements I18NProvider {

    private static final Map<String, Map<Locale, String>> translations = new HashMap<>();

    static {
        addTranslation("Welcome to Loading Docks!", Locale.ENGLISH, "Welcome to Loading Docks!");
        addTranslation("Welcome to Loading Docks!", new Locale("bg"), "Добре дошли в Товарни докове!");

        addTranslation("profile", Locale.ENGLISH, "Profile");
        addTranslation("profile", new Locale("bg"), "Профил");

        addTranslation("create_schedule", Locale.ENGLISH, "Create Schedule");
        addTranslation("create_schedule", new Locale("bg"), "Създай график");

        addTranslation("admin_panel", Locale.ENGLISH, "Admin Panel");
        addTranslation("admin_panel", new Locale("bg"), "Админ панел");

        addTranslation("branches", Locale.ENGLISH, "Branches");
        addTranslation("branches", new Locale("bg"), "Филиали");

        addTranslation("logout", Locale.ENGLISH, "Logout");
        addTranslation("logout", new Locale("bg"), "Изход");

        addTranslation("login", Locale.ENGLISH, "Login");
        addTranslation("login", new Locale("bg"), "Вход");

        addTranslation("register", Locale.ENGLISH, "Register");
        addTranslation("register", new Locale("bg"), "Регистрация");

        addTranslation("help", Locale.ENGLISH, "Help");
        addTranslation("help", new Locale("bg"), "Помощ");

        addTranslation("change_language", Locale.ENGLISH, "Change Language");
        addTranslation("change_language", new Locale("bg"), "Смени език");

        addTranslation("back", Locale.ENGLISH, "Back");
        addTranslation("back", new Locale("bg"), "Назад");

        addTranslation("email", Locale.ENGLISH, "Email");
        addTranslation("email", new Locale("bg"), "Имейл");

        addTranslation("password", Locale.ENGLISH, "Password");
        addTranslation("password", new Locale("bg"), "Парола");

        addTranslation("firstName", Locale.ENGLISH, "First Name");
        addTranslation("firstName", new Locale("bg"), "Име");

        addTranslation("lastName", Locale.ENGLISH, "Last Name");
        addTranslation("lastName", new Locale("bg"), "Фамилия");

        addTranslation("confirmPassword", Locale.ENGLISH, "Confirm Password");
        addTranslation("confirmPassword", new Locale("bg"), "Потвърдете паролата");

        addTranslation("phoneNumber", Locale.ENGLISH, "Phone Number");
        addTranslation("phoneNumber", new Locale("bg"), "Телефон");

        addTranslation("allSchedules", Locale.ENGLISH, "All Schedules");
        addTranslation("allSchedules", new Locale("bg"), "Всички графици");

        addTranslation("setRole", Locale.ENGLISH, "Set role");
        addTranslation("setRole", new Locale("bg"), "Задаване на роля");

        addTranslation("setHoliday", Locale.ENGLISH, "Set holiday");
        addTranslation("setHoliday", new Locale("bg"), "Задаване на почивен ден");

        addTranslation("delete", Locale.ENGLISH, "Delete");
        addTranslation("delete", new Locale("bg"), "Изтрий");

        addTranslation("vehicleType", Locale.ENGLISH, "Vehicle Type");
        addTranslation("vehicleType", new Locale("bg"), "Тип превозно средство");

        addTranslation("branch", Locale.ENGLISH, "Branch");
        addTranslation("branch", new Locale("bg"), "Клон");

        addTranslation("date", Locale.ENGLISH, "Date");
        addTranslation("date", new Locale("bg"), "Дата");

        addTranslation("startTime", Locale.ENGLISH, "Start Time");
        addTranslation("startTime", new Locale("bg"), "Начално време");

        addTranslation("endTime", Locale.ENGLISH, "End Time");
        addTranslation("endTime", new Locale("bg"), "Крайно време");

        addTranslation("reservedBy", Locale.ENGLISH, "Reserved By");
        addTranslation("reservedBy", new Locale("bg"), "Резервирано от");

        addTranslation("actions", Locale.ENGLISH, "Actions");
        addTranslation("actions", new Locale("bg"), "Действия");

        addTranslation("saveChanges", Locale.ENGLISH, "Save Changes");
        addTranslation("saveChanges", new Locale("bg"), "Запази промените");

        addTranslation("Error loading users: ", Locale.ENGLISH, "Error loading users:");
        addTranslation("Error loading users: ", new Locale("bg"), "Грешка при зареждане на потребители: ");

        addTranslation("Roles updated successfully.", Locale.ENGLISH, "Roles updated successfully.");
        addTranslation("Roles updated successfully.", new Locale("bg"), "Ролите са актуализирани успешно.");

        addTranslation("Error saving roles: ", Locale.ENGLISH, "Error saving roles: ");
        addTranslation("Error saving roles: ", new Locale("bg"), "Грешка при запазване на роли: ");

        addTranslation("fullName", Locale.ENGLISH, "Full Name");
        addTranslation("fullName", new Locale("bg"), "Име и Фамилия");

        addTranslation("role", Locale.ENGLISH, "Role");
        addTranslation("role", new Locale("bg"), "Роля");

        addTranslation("admin", Locale.ENGLISH, "Admin");
        addTranslation("admin", new Locale("bg"), "Администратор");

        addTranslation("user", Locale.ENGLISH, "User");
        addTranslation("user", new Locale("bg"), "Потребител");

        addTranslation("id", Locale.ENGLISH, "ID");
        addTranslation("id", new Locale("bg"), "Ид");

        /// addTranslation("Add a day off", Locale.ENGLISH, "Add a day off");
        addTranslation("Add a day off", new Locale("bg"), "Добави почивен ден");

        //addTranslation("Delete selected day", Locale.ENGLISH, "Delete selected day");
        addTranslation("Delete selected day", new Locale("bg"), "Изтрий избрания ден");

        addTranslation("Choose a day off", Locale.ENGLISH, "Choose a day off");
        addTranslation("Choose a day off", new Locale("bg"), "Изберете почивен ден");

        // addTranslation("Add a day off", Locale.ENGLISH, "Add a day off");
        addTranslation("Delete selected day", new Locale("bg"), "Изтриване на избрания ден");

        addTranslation("Please select a date", new Locale("bg"), "Моля, изберете дата");

        addTranslation("The holiday has been added successfully", new Locale("bg"), "Почивният ден е добавен успешно");

        addTranslation("The holiday has been deleted successfully", new Locale("bg"), "Почивният ден е изтрит успешно");

        addTranslation("Please select a holiday to delete", new Locale("bg"), "Моля, изберете почивен ден за изтриване");

        addTranslation("Error: ", new Locale("bg"), "Грешка: ");

        addTranslation("Error loading holidays: ", new Locale("bg"), "Грешка при зареждане на почивните дни: ");


        addTranslation("VehicleType.VAN", Locale.ENGLISH, "Van");
        addTranslation("VehicleType.VAN", new Locale("bg"), "Микробус");

        addTranslation("VehicleType.TRUCK", Locale.ENGLISH, "Truck");
        addTranslation("VehicleType.TRUCK", new Locale("bg"), "Камион");

        addTranslation("VehicleType.CARGO_TRUCK", Locale.ENGLISH, "Cargo truck");
        addTranslation("VehicleType.CARGO_TRUCK", new Locale("bg"), "Товарен камион");

        addTranslation("VehicleType.TRUCK_WITH_TRAILER", Locale.ENGLISH, "Truck with trailer");
        addTranslation("VehicleType.TRUCK_WITH_TRAILER", new Locale("bg"), "Камион с ремарке");

        addTranslation("BranchLocationEnum.SOFIA", Locale.ENGLISH, "Sofia 1");
        addTranslation("BranchLocationEnum.SOFIA", new Locale("bg"), "София 1");

        addTranslation("BranchLocationEnum.VARNA", Locale.ENGLISH, "Varna");
        addTranslation("BranchLocationEnum.VARNA", new Locale("bg"), "Варна");

        addTranslation("BranchLocationEnum.BURGAS", Locale.ENGLISH, "Burgas");
        addTranslation("BranchLocationEnum.BURGAS", new Locale("bg"), "Бургас");

        addTranslation("BranchLocationEnum.PLOVDIV", Locale.ENGLISH, "Plovdiv");
        addTranslation("BranchLocationEnum.PLOVDIV", new Locale("bg"), "Пловдив");

        addTranslation("GoodsEnum.CABINET_FURNITURE", Locale.ENGLISH, "Cabinet furniture");
        addTranslation("GoodsEnum.CABINET_FURNITURE", new Locale("bg"), "Корпусни мебели");

        addTranslation("GoodsEnum.UPHOLSTERED_FURNITURE", Locale.ENGLISH, "Upholstered furniture");
        addTranslation("GoodsEnum.UPHOLSTERED_FURNITURE", new Locale("bg"), " Мека мебел");

        addTranslation("GoodsEnum.GARDEN_FURNITURE", Locale.ENGLISH, "Garden furniture");
        addTranslation("GoodsEnum.GARDEN_FURNITURE", new Locale("bg"), " Градинска мебел");

        addTranslation("GoodsEnum.MATTRESSES", Locale.ENGLISH, "Mattresses");
        addTranslation("GoodsEnum.MATTRESSES", new Locale("bg"), " Матраци");

        addTranslation("Language", Locale.ENGLISH, "Language");
        addTranslation("Language", new Locale("bg"), "Език");

        addTranslation("Type of product", new Locale("bg"), "Вид продукт");

        addTranslation("Linear meters", new Locale("bg"), "Линейни метри");

        addTranslation("Add the product", new Locale("bg"), "Добавете продукта");

        addTranslation("Please enter valid product details and meters", new Locale("bg"), "Моля, въведете валиден продукт и линейни метри");

        addTranslation("Added ", new Locale("bg"), "Добавени: ");

        addTranslation(" linear meters for ", new Locale("bg"), " линейни метри за ");

        addTranslation("Unloading date", new Locale("bg"), "Дата на разтоварване");

        addTranslation("The selected day is a holiday. Please choose another day!",
                new Locale("bg"), "Избраният ден е почивен. Моля, изберете друг ден!");

        addTranslation("Create a schedule", new Locale("bg"), "Създайте график");

        addTranslation("Please fill in all fields!", new Locale("bg"), "Моля, попълнете всички полета!");

        addTranslation("Please log in to create a schedule!", new Locale("bg"), "Моля, влезте, за да създадете график!");

        addTranslation("The schedule has been created successfully!", new Locale("bg"), "Графикът е създаден успешно!");

        addTranslation("Vehicle type", new Locale("bg"), "Тип превозно средство");

        addTranslation("Branch", new Locale("bg"), "Филиал");

        addTranslation("First Name", new Locale("bg"), "Име");

        addTranslation("Last Name", new Locale("bg"), "Фамилия");

        addTranslation("Email", new Locale("bg"), "Имейл");

        addTranslation("Phone Number", new Locale("bg"), "Телефон");

        addTranslation("Save All Changes", new Locale("bg"), "Запази промените");

        addTranslation("My Schedule", new Locale("bg"), "Моят график");

        addTranslation("Please log in first!", new Locale("bg"), "Моля първо влезте в системата!");

        addTranslation("Profile updated successfully!", new Locale("bg"), "Профилът е актуализиран успешно!");

        addTranslation("Error updating profile: ", new Locale("bg"), "Грешка при актуализиране на профила: ");

        addTranslation("Date", new Locale("bg"), "Дата");

        addTranslation("Start Time", new Locale("bg"), "Начален час");

        addTranslation("End Time", new Locale("bg"), "Краен час");

        addTranslation("Reserved By", new Locale("bg"), "Запазено от");

        addTranslation("Delete", new Locale("bg"), "Изтрий");

        addTranslation("Actions", new Locale("bg"), "Действия");

        addTranslation("Schedule deleted successfully!", new Locale("bg"), "Графикът е изтрит успешно!");

        addTranslation("Error deleting schedule: ", new Locale("bg"), "Грешка при изтриването на графика: ");

        addTranslation("You have no upcoming deliveries", new Locale("bg"), "Нямате предстоящи доставки");

        addTranslation("Error loading schedule: ", new Locale("bg"), "Грешка при зареждане на графика: ");

        addTranslation("Password must be at least 6 characters long!", new Locale("bg"), "Паролата трябва да е с дължина поне 6 знака!");

        addTranslation("Password changed successfully!", new Locale("bg"), "Паролата е променена успешно!");

        addTranslation("Error changing password: ", new Locale("bg"), "Грешка при промяна на паролата: ");

        addTranslation("New Password", new Locale("bg"), "Нова парола");

        addTranslation("Enter new password", new Locale("bg"), "Въведете нова парола");

        addTranslation("Change Password", new Locale("bg"), "Промяна на паролата");

    }

    private static void addTranslation(String key, Locale locale, String translation) {
        translations.computeIfAbsent(key, k -> new HashMap<>()).put(locale, translation);
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return List.of(Locale.ENGLISH, new Locale("bg"));
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {

        Map<Locale, String> localeMap = translations.get(key);
        if (localeMap != null) {
            String translation = localeMap.getOrDefault(locale, key);

            return translation;
        }

        return key;
    }
}