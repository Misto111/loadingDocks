package org.docks.loadingdocks.config;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class I18nConfig {

    @Bean
    public I18NProvider i18nProvider() {
        return new CustomI18NProvider(); // Регистрираме CustomI18NProvider
    }
}
