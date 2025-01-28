package org.docks.loadingdocks.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.docks.loadingdocks.enums.BranchLocationEnum;
import org.docks.loadingdocks.model.service.BackButtonService;
import org.docks.loadingdocks.model.service.TranslationService;

import java.util.ArrayList;
import java.util.List;

@Route("branches")
@PageTitle("Branches")
@JavaScript("map.js")  // Зареждаме map.js за Google Maps
@CssImport("map-styles.css")  // Зареждаме стила
public class BranchesView extends VerticalLayout {

    private final TranslationService translationService;


    public BranchesView(TranslationService translationService) {
        this.translationService = translationService;
        // Създаваме предварително дефинирани филиали
        List<Branch> branches = createStaticBranches();

        // Контейнер за картата
        Div mapContainer = new Div();
        mapContainer.setId("map");
        mapContainer.getStyle().set("width", "100%").set("height", "400px");

// Контейнер за визитките на филиалите
        Div branchCardsContainer = new Div();
        branchCardsContainer.setWidthFull();
        branchCardsContainer.getStyle()
                .set("display", "flex") // Използваме flexbox
                .set("flex-wrap", "wrap") // Позволява пренасяне на нов ред
                .set("justify-content", "space-around") // Разпределя равномерно картите
                .set("gap", "20px") // Разстояние между картите
                .set("max-height", "400px") // Ограничаваме височината на контейнера
                .set("overflow-y", "auto") // Активира вертикално превъртане
                .set("padding", "10px") // Добавяме вътрешно разстояние
                .set("box-sizing", "border-box");

        // Добавяме визитките за всеки филиал
        branches.forEach(branch -> branchCardsContainer.add(createBranchCard(branch)));

        // Добавяме картата и визитките в основния layout
        add(mapContainer, branchCardsContainer);

        // Инициализираме картата с JSON данни
        getElement().executeJs("setTimeout(function() { window.branchLocations = " + branchesToJson(branches) + "; initMap(); }, 1000);");

        // Добавяме бутон за връщане
        Button backButton = BackButtonService.createBackButton("", translationService);
        backButton.getStyle().set("position", "absolute").set("top", "20px").set("left", "20px");
        add(backButton);

        setSizeFull();
    }

    /**
     * Създава статичен списък от филиали.
     */
    private List<Branch> createStaticBranches() {
        List<Branch> branches = new ArrayList<>();
        // Преобразуваме данни от енъма в обекти Branch
        for (BranchLocationEnum location : BranchLocationEnum.values()) {
            branches.add(new Branch(
                    location.getName(),
                    location.getLat(),
                    location.getLng(),
                    location.getAddress(),
                    location.getWorkingHours(),
                    location.getImageUrl(),
                    location.getInfoUrl(),
                    location.getPhone()

            ));
        }
        return branches;
    }

    /**
     * Създава визитка за даден филиал.
     */
    private Div createBranchCard(Branch branch) {
        Div branchCard = new Div();
        branchCard.getStyle()
                .set("border", "1px solid #ccc")
                .set("padding", "10px")
                .set("margin", "10px")
                .set("border-radius", "5px")
                .set("width", "calc(45%)") // Размер на картата
                .set("box-sizing", "border-box")
                .set("display", "flex") // Flexbox за хоризонтално подравняване
                .set("align-items", "center") // Вертикално подравняване
                .set("gap", "10px"); // Разстояние между изображението и текста

        // Създаваме изображението
        com.vaadin.flow.component.html.Image image = new com.vaadin.flow.component.html.Image(branch.getImageUrl(), "Branch Image");
        image.getStyle()
                .set("width", "210px") // Фиксирана ширина на изображението
                .set("height", "120px") // Фиксирана височина на изображението
                .set("object-fit", "cover") // Подрязване на изображението, ако е необходимо
                .set("border-radius", "5px");
        branchCard.add(image);

        // Създаваме контейнер за текста
        Div textContainer = new Div();
        textContainer.getStyle().set("flex", "1"); // Текстът заема оставащото пространство

        Div nameDiv = new Div();
        nameDiv.setText(branch.getName());
        nameDiv.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1em"); // Голям шрифт за името
        textContainer.add(nameDiv);

        Div addressDiv = new Div();
        addressDiv.setText(branch.getAddress());
        textContainer.add(addressDiv);

        Div workingHoursDiv = new Div();
        workingHoursDiv.setText(branch.getWorkingHours());
        textContainer.add(workingHoursDiv);

        Div phonesDiv = new Div();
        phonesDiv.setText(branch.getPhone());
        textContainer.add(phonesDiv);

        Div urlDiv = new Div();
        com.vaadin.flow.component.html.Anchor link = new com.vaadin.flow.component.html.Anchor(branch.getInfoUrl(), "See more");
        urlDiv.add(link);
        textContainer.add(urlDiv);

        // Добавяме текстовия контейнер в картата
        branchCard.add(textContainer);

        return branchCard;
    }

    /**
     * Преобразува данните на филиалите в JSON формат за картата.
     */
    private String branchesToJson(List<Branch> branches) {
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (Branch branch : branches) {
            jsonBuilder.append(String.format(
                    "{name: \"%s\", lat: %f, lng: %f, address: \"%s\", workingHours: \"%s\", imageUrl: \"%s\"},",
                    branch.getName(),
                    branch.getLat(),
                    branch.getLng(),
                    branch.getAddress(),
                    branch.getWorkingHours(),
                    branch.getImageUrl(),
                    branch.getPhone(),
                    branch.getInfoUrl()
            ));
        }
        if (!branches.isEmpty()) {
            jsonBuilder.setLength(jsonBuilder.length() - 1); // Премахваме последната запетая
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    /**
     * Клас, който представлява филиал.
     */
    public static class Branch {
        private String name;
        private double lat;
        private double lng;
        private String address;
        private String workingHours;
        private String imageUrl;
        private String infoUrl;
        private String phone;


       public Branch(String name, double lat, double lng, String address, String workingHours, String imageUrl, String infoUrl, String phone) {
           this.name = name;
           this.lat = lat;
           this.lng = lng;
           this.address = address;
           this.workingHours = workingHours;
           this.imageUrl = imageUrl;
           this.infoUrl = infoUrl;
           this.phone = phone;

       }

        public String getName() {
            return name;
        }

        public Branch setName(String name) {
            this.name = name;
            return this;
        }

        public double getLat() {
            return lat;
        }

        public Branch setLat(double lat) {
            this.lat = lat;
            return this;
        }

        public double getLng() {
            return lng;
        }

        public Branch setLng(double lng) {
            this.lng = lng;
            return this;
        }

        public String getAddress() {
            return address;
        }

        public Branch setAddress(String address) {
            this.address = address;
            return this;
        }

        public String getWorkingHours() {
            return workingHours;
        }

        public Branch setWorkingHours(String workingHours) {
            this.workingHours = workingHours;
            return this;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public Branch setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public String getInfoUrl() {
            return infoUrl;
        }

        public Branch setInfoUrl(String infoUrl) {
            this.infoUrl = infoUrl;
            return this;
        }

        public String getPhone() {
            return phone;
        }

        public Branch setPhone(String phone) {
            this.phone = phone;
            return this;
        }
    }
}