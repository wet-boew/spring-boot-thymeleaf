/**
 *
 */
package ca.canada.ised.wet.cdts;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import ca.canada.ised.wet.cdts.components.wet.config.WETResourceBundle;

/**
 * WetCdtsSpringBootThymeleafConfig.
 * 
 * Leave creation of LocalChangeInterceptor up to the apps.
 * WETTemplateInterceptor will be component scanned automatically, no need for
 * bean creation here.
 *
 * @author Andrew Pitt
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
@ComponentScan(basePackages = "ca.canada.ised.wet.cdts.components")
public class WetCdtsSpringBootThymeleafConfig {

	/**
	 * WET CDTS message source.
	 * 
	 * @return <code>MessageSource</code>
	 */
	@Bean(name = "wetCDTSMessgeSource")
	public MessageSource wetMessageSource() {
		ResourceBundleMessageSource messageSource = new WETResourceBundle();

		messageSource.setBasenames("cdn/cdn", "cdn/cdn_override", "cdn/common_messages");
		return messageSource;
	}
}
