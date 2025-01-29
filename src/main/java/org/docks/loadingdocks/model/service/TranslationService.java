package org.docks.loadingdocks.model.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

@Service
public class TranslationService {

    private final I18NProvider i18NProvider;
    private final List<Consumer<Locale>> languageChangeListeners = new ArrayList<>();

    public TranslationService(I18NProvider i18NProvider) {
        this.i18NProvider = i18NProvider;
    }

    public String getTranslation(String key) {
        Locale currentLocale = VaadinSession.getCurrent().getAttribute(Locale.class);
        if (currentLocale == null) {
            currentLocale = Locale.ENGLISH;
        }
        return i18NProvider.getTranslation(key, currentLocale);
    }

    public void changeLanguage(String language) {
        Locale selectedLocale = "Български".equals(language) ? new Locale("bg") : Locale.ENGLISH;

        VaadinSession.getCurrent().setAttribute(Locale.class, selectedLocale);

        UI.getCurrent().setLocale(selectedLocale);

        notifyLanguageChange(selectedLocale);
    }

    public void addLanguageChangeListener(Consumer<Locale> listener) {
        languageChangeListeners.add(listener);
    }

    private void notifyLanguageChange(Locale locale) {
        languageChangeListeners.forEach(listener -> listener.accept(locale));
    }

    public String translateEnum(Enum<?> enumValue) {
        String key = enumValue.getClass().getSimpleName() + "." + enumValue.name();
        return getTranslation(key);
    }
}