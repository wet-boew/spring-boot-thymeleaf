/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.util.Locale;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import ca.canada.ised.wet.cdts.WetCdtsSpringBootThymeleafConfig;

/**
 * WetCdtsSpringBootThymeleafTestConfig. Enables auto configuration so that tests will run.
 *
 * @author Frank Giusto
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
@EnableAutoConfiguration
public class WetCdtsSpringBootThymeleafTestConfig extends WetCdtsSpringBootThymeleafConfig {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.CANADA);
        return localeResolver;
    }
}
