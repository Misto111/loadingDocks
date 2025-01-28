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

        Button backButton = BackButtonService.createBackButton("", translationService); // Empty for root navigation

        // Главен layout
        setSizeFull();
        setAlignItems(Alignment.CENTER); // Центрираме елементите хоризонтално

        // Главно заглавие (най-отгоре)
        H1 mainTitle = new H1("Important information that must be read!");
        mainTitle.getStyle()
                .set("color", "black") // Син цвят
                .set("font-size", "36px") // Размер на шрифта
                .set("font-weight", "bold") // Удебелен текст
                .set("margin-bottom", "20px"); // Разстояние под заглавието

        // Текст под заглавието
        Div textContainer = new Div();
        textContainer.setText("Instructions that every user must follow.");
        textContainer.getStyle()
                .set("color", "blue") // Син цвят
                .set("font-size", "20px") // Размер на шрифта
                .set("text-align", "center") // Центриране на текста хоризонтално
                .set("max-width", "600px") // Ограничение на ширината за добър изглед
                .set("margin-top", "0px"); // Минимално разстояние над текста

        // Добавяме елементите към главния layout
        add(backButton, mainTitle, textContainer);
    }
}
