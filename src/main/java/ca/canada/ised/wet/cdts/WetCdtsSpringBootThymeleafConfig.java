/**
 *
 */
package ca.canada.ised.wet.cdts;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import ca.canada.ised.wet.cdts.components.wet.interceptor.WETTemplateInterceptor;

/**
 * WetCdtsSpringBootThymeleafConfig .
 *
 * @author Andrew Pitt
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
@ComponentScan(basePackages = "ca.canada.ised.wet.cdts.components")
public class WetCdtsSpringBootThymeleafConfig {

    /**
     * Locale resolver.
     *
     * @return <code>LocaleResolver</code>
     */
    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.CANADA);
        return localeResolver;
    }

    /**
     * Locale change interceptor.
     *
     * @return <code>LocaleChangeInterceptor</code>
     */
    @Bean(name = "localeChangeInterceptor")
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    /**
     * WET Template Interceptor.
     *
     * @return WETTemplateInterceptor
     */
    @Bean(name = "wetTemplateInterceptor")
    public WETTemplateInterceptor getTemplateHandler() {
        return new WETTemplateInterceptor();
    }

}
