/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import ca.canada.ised.wet.cdts.components.wet.config.beans.WETBilingualUrl;
import ca.canada.ised.wet.cdts.components.wet.config.beans.WETGoCConfig;
import ca.canada.ised.wet.cdts.components.wet.config.beans.WETLeavingSecureSiteWarning;
import ca.canada.ised.wet.cdts.components.wet.config.beans.WETSession;
import ca.canada.ised.wet.cdts.components.wet.config.beans.WETTemplate;
import ca.canada.ised.wet.cdts.components.wet.config.beans.WETUrl;

/**
 * WETSettings.
 *
 * Default settings for Canada.ca WET template.
 *
 * To override any of these settings, you have two choices. 1) create a folder under src/main/resources in your
 * application and copy the cdn.properties file from this project there. These will now be your application defaults. 2)
 * create a properties file in your template/view directory. For example, if you have a templates/booking folder
 * containing bookingForm.html which is your thymeleaf page, create a properties file with the same name. ie.
 * bookingForm.properties. Simply add the properties from the original cnd.properties file you wish to override.
 *
 * For example, to enable the search bar:
 *
 * cdn.goc.webtemplate.showsearch=true
 *
 * @author Andrew pitt, Frank G.
 * @since 4.1.0
 */
@Configuration
@PropertySources({@PropertySource("classpath:cdn/cdn.properties"),
    @PropertySource("classpath:cdn/cdn_override.properties")})
@ConfigurationProperties(ignoreUnknownFields = true, prefix = "cdn")
public class WETSettings {

    /** Environment of the cdn. */
    private String environment;

    /** Url of the cdn. */
    private String url;

    private WETTemplate wettemplate = new WETTemplate();

    private WETSession session = new WETSession();

    private WETGoCConfig goc = new WETGoCConfig();

    private WETLeavingSecureSiteWarning leavingsecuresitewarning = new WETLeavingSecureSiteWarning();

    private WETBilingualUrl contact = new WETBilingualUrl();

    private WETBilingualUrl terms = new WETBilingualUrl();

    private WETBilingualUrl privacy = new WETBilingualUrl();

    private WETUrl exit = new WETUrl();

    /**
     * @return the environment
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * @param environment the environment to set
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the wettemplate
     */
    public WETTemplate getWettemplate() {
        return wettemplate;
    }

    /**
     * @param wettemplate the wettemplate to set
     */
    public void setWettemplate(WETTemplate wettemplate) {
        this.wettemplate = wettemplate;
    }

    /**
     * @return the session
     */
    public WETSession getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(WETSession session) {
        this.session = session;
    }

    /**
     * @return the goc
     */
    public WETGoCConfig getGoc() {
        return goc;
    }

    /**
     * @param goc the goc to set
     */
    public void setGoc(WETGoCConfig goc) {
        this.goc = goc;
    }

    /**
     * @return the leavingsecuresitewarning
     */
    public WETLeavingSecureSiteWarning getLeavingsecuresitewarning() {
        return leavingsecuresitewarning;
    }

    /**
     * @param leavingsecuresitewarning the leavingsecuresitewarning to set
     */
    public void setLeavingsecuresitewarning(WETLeavingSecureSiteWarning leavingsecuresitewarning) {
        this.leavingsecuresitewarning = leavingsecuresitewarning;
    }

    /**
     * @return the contact
     */
    public WETBilingualUrl getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(WETBilingualUrl contact) {
        this.contact = contact;
    }

    /**
     * @return the terms
     */
    public WETBilingualUrl getTerms() {
        return terms;
    }

    /**
     * @param terms the terms to set
     */
    public void setTerms(WETBilingualUrl terms) {
        this.terms = terms;
    }

    /**
     * @return the privacy
     */
    public WETBilingualUrl getPrivacy() {
        return privacy;
    }

    /**
     * @param privacy the privacy to set
     */
    public void setPrivacy(WETBilingualUrl privacy) {
        this.privacy = privacy;
    }

    /**
     * @return the exit
     */
    public WETUrl getExit() {
        return exit;
    }

    /**
     * @param exit the exit to set
     */
    public void setExit(WETUrl exit) {
        this.exit = exit;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
