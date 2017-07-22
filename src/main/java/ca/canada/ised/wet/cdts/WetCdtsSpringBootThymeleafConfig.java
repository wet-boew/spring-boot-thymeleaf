/**
 *
 */
package ca.canada.ised.wet.cdts;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

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

	// /**
	// * Locale change interceptor.
	// *
	// * @return <code>LocaleChangeInterceptor</code>
	// */
	// @Bean(name = "localeChangeInterceptor")
	// public LocaleChangeInterceptor localeChangeInterceptor() {
	// LocaleChangeInterceptor localeChangeInterceptor = new
	// LocaleChangeInterceptor();
	// localeChangeInterceptor.setParamName("lang");
	// return localeChangeInterceptor;
	// }

	/**
	 * WET Template Interceptor.
	 *
	 * @return WETTemplateInterceptor
	 */
	@Bean(name = "wetTemplateInterceptor")
	public WETTemplateInterceptor getTemplateHandler() {
		return new WETTemplateInterceptor();
	}

	@Bean(name = "applicationMessageSource")
	public MessageSource wetMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

		messageSource.setBasenames("cdn/cdn", "cdn/messages");
		return messageSource;
	}
}
