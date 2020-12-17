/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.util.Locale;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import ca.canada.ised.wet.cdts.WetCdtsSpringBootThymeleafConfig;

/**
 * WetCdtsSpringBootThymeleafTestConfig. Enables auto configuration so that tests will run.
 *
 * @author Frank Giusto, Andrew Pitt
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
@EnableAutoConfiguration
public class WetCdtsSpringBootThymeleafTestConfig extends WetCdtsSpringBootThymeleafConfig {

    /**
     * Test message source. The WET message source is set as the parent.
     * 
     * @return <code>MessageSource</code>
     */
    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("cdn/cdn", "cdn/cdn_override", "cdn/common_messages");
        return messageSource;
    }

    /**
     * Test locale resolver.
     * 
     * @return <code>LocaleResolver</code>
     */
    @Bean
    public LocaleResolver localeResolver() {
        FixedLocaleResolver localeResolver = new FixedLocaleResolver(Locale.CANADA);
        return localeResolver;
    }
}
