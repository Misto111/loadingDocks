package org.docks.loadingdocks.view;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.docks.loadingdocks.model.service.BackButtonService;
import org.docks.loadingdocks.model.service.TranslationService;


@Route("help")
public class HelpView extends VerticalLayout {

    private final TranslationService translationService;

    public HelpView(TranslationService translationService) {
        this.translationService = translationService;

        Button backButton = BackButtonService.createBackButton("", translationService);

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        H1 mainTitle = new H1("Important information that must be read!");
        mainTitle.getStyle()
                .set("color", "black")
                .set("font-size", "36px")
                .set("font-weight", "bold")
                .set("margin-bottom", "20px");

        Div textContainer = new Div();
        textContainer.setText("Instructions that every user must follow.");
        textContainer.getStyle()
                .set("color", "blue")
                .set("font-size", "20px")
                .set("text-align", "center")
                .set("max-width", "600px")
                .set("margin-top", "0px");

        add(backButton, mainTitle, textContainer);
    }
}
