/**
 *
 */
package ca.canada.ised.wet.cdts;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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

}
