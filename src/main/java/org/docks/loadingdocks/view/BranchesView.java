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
@JavaScript("map.js")
@CssImport("map-styles.css")
public class BranchesView extends VerticalLayout {

    private final TranslationService translationService;


    public BranchesView(TranslationService translationService) {
        this.translationService = translationService;
        List<Branch> branches = createStaticBranches();

        Div mapContainer = new Div();
        mapContainer.setId("map");
        mapContainer.getStyle().set("width", "100%").set("height", "400px");

        Div branchCardsContainer = new Div();
        branchCardsContainer.setWidthFull();
        branchCardsContainer.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap")
                .set("justify-content", "space-around")
                .set("gap", "20px")
                .set("max-height", "400px")
                .set("overflow-y", "auto")
                .set("padding", "10px")
                .set("box-sizing", "border-box");

        branches.forEach(branch -> branchCardsContainer.add(createBranchCard(branch)));

        add(mapContainer, branchCardsContainer);

        getElement().executeJs("setTimeout(function() { window.branchLocations = " + branchesToJson(branches) + "; initMap(); }, 1000);");

        Button backButton = BackButtonService.createBackButton("", translationService);
        backButton.getStyle().set("position", "absolute").set("top", "20px").set("left", "20px");
        add(backButton);

        setSizeFull();
    }

    private List<Branch> createStaticBranches() {
        List<Branch> branches = new ArrayList<>();
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

    private Div createBranchCard(Branch branch) {
        Div branchCard = new Div();
        branchCard.getStyle()
                .set("border", "1px solid #ccc")
                .set("padding", "10px")
                .set("margin", "10px")
                .set("border-radius", "5px")
                .set("width", "calc(45%)")
                .set("box-sizing", "border-box")
                .set("display", "flex")
                .set("align-items", "center")
                .set("gap", "10px");

        com.vaadin.flow.component.html.Image image = new com.vaadin.flow.component.html.Image(branch.getImageUrl(), "Branch Image");
        image.getStyle()
                .set("width", "210px")
                .set("height", "120px")
                .set("object-fit", "cover")
                .set("border-radius", "5px");
        branchCard.add(image);

        Div textContainer = new Div();
        textContainer.getStyle().set("flex", "1");

        Div nameDiv = new Div();
        nameDiv.setText(branch.getName());
        nameDiv.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "1em");
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

        branchCard.add(textContainer);

        return branchCard;
    }

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