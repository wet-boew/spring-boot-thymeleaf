/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
@EnableConfigurationProperties
@PropertySource("classpath:cdn/cdn.properties")
public class WETSettings {

    /** Url of the cdn. */
    @Value("${cdn.url}")
    private String url;

    /** Environment of the cdn. */
    @Value("${cdn.environment}")
    private String environment;

    /** Template to use. */
    @Value("${cdn.wettemplate-theme}")
    private String wettemplateTheme;

    /** Template version to use. */
    @Value("${cdn.wettemplate-version}")
    private String wettemplateVersion;

    /** The session enabled. */
    @Value("${cdn.session.timeout.enabled}")
    private Boolean sessionEnabled;

    /** The session inactivity. */
    @Value("${cdn.session.inactivity.value}")
    private Integer sessionInactivity;

    /** The session reaction time. */
    @Value("${cdn.session.reactiontime.value}")
    private Integer sessionReactionTime;

    /** The session alive. */
    @Value("${cdn.session.sessionalive.value}")
    private Integer sessionAlive;

    /** The logout url. */
    @Value("${cdn.session.logouturl}")
    private String logoutUrl;

    /** The refresh callback. */
    @Value("${cdn.session.refreshcallback}")
    private String refreshCallback;

    /** The refresh callback url. */
    @Value("${cdn.session.refreshcallbackurl}")
    private String refreshCallbackUrl;

    /** The refresh on click. */
    @Value("${cdn.session.refreshonclick}")
    private Boolean refreshOnClick;

    /** The refresh limit. */
    @Value("${cdn.session.refreshlimit.value}")
    private Integer refreshLimit;

    /** The request method. */
    @Value("${cdn.session.method}")
    private String requestMethod;

    /** The additional data. */
    @Value("${cdn.session.additionaldata}")
    private String additionalData;

    /** The show pre content. */
    @Value("${cdn.goc.webtemplate.showprecontent}")
    private Boolean showPreContent;

    /** The show post content. */
    @Value("${cdn.goc.webtemplate.showpostcontent}")
    private Boolean showPostContent;

    /** The show footer. */
    @Value("${cdn.goc.webtemplate.showfooter}")
    private Boolean showFooter;

    /** The show features. */
    @Value("${cdn.goc.webtemplate.showfeatures}")
    private Boolean showFeatures;

    /** The show feedback. */
    @Value("${cdn.goc.webtemplate.showfeedbacklink}")
    private Boolean showFeedback;

    /** The show share. */
    @Value("${cdn.goc.webtemplate.showsharepagelink}")
    private Boolean showShare;

    /** The show search. */
    @Value("${cdn.goc.webtemplate.showsearch}")
    private Boolean showSearch;

    /** The leaving secure site warning enabled. */
    @Value("${cdn.leavingsecuresitewarning.enabled}")
    private Boolean leavingSecureSiteWarningEnabled;

    /** The leaving secure site warning redirect url. */
    @Value("${cdn.leavingsecuresitewarning.redirecturl}")
    private String leavingSecureSiteWarningRedirectUrl;

    /** The leaving secure site warning excluded domains. */
    @Value("${cdn.leavingsecuresitewarning.excludeddomains}")
    private String leavingSecureSiteWarningExcludedDomains;

    /** The exit transaction link url. */
    @Value("${cdn.exit.transaction.link.url}")
    private String exitTransactionLinkUrl;

    /** The contact link english url. */
    @Value("${cdn.contact.link.english.url}")
    private String contactLinkEnglishUrl;

    /** The contact link french url. */
    @Value("${cdn.contact.link.french.url}")
    private String contactLinkFrenchUrl;

    /** The terms link english url. */
    @Value("${cdn.terms.link.english.url}")
    private String termsLinkEnglishUrl;

    /** The terms link french url. */
    @Value("${cdn.terms.link.french.url}")
    private String termsLinkFrenchUrl;

    /** The privacy link english url. */
    @Value("${cdn.privacy.link.english.url}")
    private String privacyLinkEnglishUrl;

    /** The privacy link french url. */
    @Value("${cdn.privacy.link.french.url}")
    private String privacyLinkFrenchUrl;

    /**
     *
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
     * @return the wettemplateTheme
     */
    public String getWettemplateTheme() {
        return wettemplateTheme;
    }

    /**
     * @param wettemplateTheme the wettemplateTheme to set
     */
    public void setWettemplateTheme(String wettemplateTheme) {
        this.wettemplateTheme = wettemplateTheme;
    }

    /**
     * @return the wettemplateVersion
     */
    public String getWettemplateVersion() {
        return wettemplateVersion;
    }

    /**
     * @param wettemplateVersion the wettemplateVersion to set
     */
    public void setWettemplateVersion(String wettemplateVersion) {
        this.wettemplateVersion = wettemplateVersion;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    /**
     * Gets the show pre content.
     *
     * @return the show pre content
     */
    public Boolean getShowPreContent() {
        return showPreContent;
    }

    /**
     * Sets the show pre content.
     *
     * @param showPreContent the new show pre content
     */
    public void setShowPreContent(Boolean showPreContent) {
        this.showPreContent = showPreContent;
    }

    /**
     * Gets the show post content.
     *
     * @return the show post content
     */
    public Boolean getShowPostContent() {
        return showPostContent;
    }

    /**
     * Sets the show post content.
     *
     * @param showPostContent the new show post content
     */
    public void setShowPostContent(Boolean showPostContent) {
        this.showPostContent = showPostContent;
    }

    /**
     * Gets the show footer.
     *
     * @return the show footer
     */
    public Boolean getShowFooter() {
        return showFooter;
    }

    /**
     * Sets the show footer.
     *
     * @param showFooter the new show footer
     */
    public void setShowFooter(Boolean showFooter) {
        this.showFooter = showFooter;
    }

    /**
     * Gets the show features.
     *
     * @return the show features
     */
    public Boolean getShowFeatures() {
        return showFeatures;
    }

    /**
     * Sets the show features.
     *
     * @param showFeatures the new show features
     */
    public void setShowFeatures(Boolean showFeatures) {
        this.showFeatures = showFeatures;
    }

    /**
     * Gets the show feedback.
     *
     * @return the show feedback
     */
    public Boolean getShowFeedback() {
        return showFeedback;
    }

    /**
     * Sets the show feedback.
     *
     * @param showFeedback the new show feedback
     */
    public void setShowFeedback(Boolean showFeedback) {
        this.showFeedback = showFeedback;
    }

    /**
     * Gets the show share.
     *
     * @return the show share
     */
    public Boolean getShowShare() {
        return showShare;
    }

    /**
     * Sets the show share.
     *
     * @param showShare the new show share
     */
    public void setShowShare(Boolean showShare) {
        this.showShare = showShare;
    }

    /**
     * Gets the session enabled.
     *
     * @return the session enabled
     */
    public Boolean getSessionEnabled() {
        return sessionEnabled;
    }

    /**
     * Sets the session enabled.
     *
     * @param sessionEnabled the new session enabled
     */
    public void setSessionEnabled(Boolean sessionEnabled) {
        this.sessionEnabled = sessionEnabled;
    }

    /**
     * Gets the session inactivity.
     *
     * @return the session inactivity
     */
    public Integer getSessionInactivity() {
        return sessionInactivity;
    }

    /**
     * Sets the session inactivity.
     *
     * @param sessionInactivity the new session inactivity
     */
    public void setSessionInactivity(Integer sessionInactivity) {
        this.sessionInactivity = sessionInactivity;
    }

    /**
     * Gets the session reaction time.
     *
     * @return the session reaction time
     */
    public Integer getSessionReactionTime() {
        return sessionReactionTime;
    }

    /**
     * Sets the session reaction time.
     *
     * @param sessionReactionTime the new session reaction time
     */
    public void setSessionReactionTime(Integer sessionReactionTime) {
        this.sessionReactionTime = sessionReactionTime;
    }

    /**
     * Gets the session alive.
     *
     * @return the session alive
     */
    public Integer getSessionAlive() {
        return sessionAlive;
    }

    /**
     * Sets the session alive.
     *
     * @param sessionAlive the new session alive
     */
    public void setSessionAlive(Integer sessionAlive) {
        this.sessionAlive = sessionAlive;
    }

    /**
     * Gets the refresh callback url.
     *
     * @return the refresh callback url
     */
    public String getRefreshCallbackUrl() {
        return refreshCallbackUrl;
    }

    /**
     * Sets the refresh callback url.
     *
     * @param refreshCallbackUrl the new refresh callback url
     */
    public void setRefreshCallbackUrl(String refreshCallbackUrl) {
        this.refreshCallbackUrl = refreshCallbackUrl;
    }

    /**
     * Gets the refresh on click.
     *
     * @return the refresh on click
     */
    public Boolean getRefreshOnClick() {
        return refreshOnClick;
    }

    /**
     * Sets the refresh on click.
     *
     * @param refreshOnClick the new refresh on click
     */
    public void setRefreshOnClick(Boolean refreshOnClick) {
        this.refreshOnClick = refreshOnClick;
    }

    /**
     * Gets the refresh limit.
     *
     * @return the refresh limit
     */
    public Integer getRefreshLimit() {
        return refreshLimit;
    }

    /**
     * Sets the refresh limit.
     *
     * @param refreshLimit the new refresh limit
     */
    public void setRefreshLimit(Integer refreshLimit) {
        this.refreshLimit = refreshLimit;
    }

    /**
     * Gets the request method.
     *
     * @return the request method
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * Sets the request method.
     *
     * @param requestMethod the new request method
     */
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * Gets the additional data.
     *
     * @return the additional data
     */
    public String getAdditionalData() {
        return additionalData;
    }

    /**
     * Sets the additional data.
     *
     * @param additionalData the new additional data
     */
    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    /**
     * Gets the refresh callback.
     *
     * @return the refresh callback
     */
    public String getRefreshCallback() {
        return refreshCallback;
    }

    /**
     * Sets the refresh callback.
     *
     * @param refreshCallback the new refresh callback
     */
    public void setRefreshCallback(String refreshCallback) {
        this.refreshCallback = refreshCallback;
    }

    /**
     * Gets the logout url.
     *
     * @return the logout url
     */
    public String getLogoutUrl() {
        return logoutUrl;
    }

    /**
     * Sets the logout url.
     *
     * @param logoutUrl the new logout url
     */
    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    /**
     * Gets the show search.
     *
     * @return the show search
     */
    public Boolean getShowSearch() {
        return showSearch;
    }

    public void setShowSearch(Boolean showSearch) {
        this.showSearch = showSearch;
    }

    /**
     * Gets the leaving secure site warning enabled.
     *
     * @return the leaving secure site warning enabled
     */
    public Boolean getLeavingSecureSiteWarningEnabled() {
        return leavingSecureSiteWarningEnabled;
    }

    /**
     * Sets the leaving secure site warning enabled.
     *
     * @param leavingSecureSiteWarningEnabled the new leaving secure site warning enabled
     */
    public void setLeavingSecureSiteWarningEnabled(Boolean leavingSecureSiteWarningEnabled) {
        this.leavingSecureSiteWarningEnabled = leavingSecureSiteWarningEnabled;
    }

    /**
     * Gets the leaving secure site warning redirect url.
     *
     * @return the leaving secure site warning redirect url
     */
    public String getLeavingSecureSiteWarningRedirectUrl() {
        return leavingSecureSiteWarningRedirectUrl;
    }

    /**
     * Sets the leaving secure site warning redirect url.
     *
     * @param leavingSecureSiteWarningRedirectUrl the new leaving secure site warning redirect url
     */
    public void setLeavingSecureSiteWarningRedirectUrl(String leavingSecureSiteWarningRedirectUrl) {
        this.leavingSecureSiteWarningRedirectUrl = leavingSecureSiteWarningRedirectUrl;
    }

    /**
     * Gets the leaving secure site warning excluded domains.
     *
     * @return the leaving secure site warning excluded domains
     */
    public String getLeavingSecureSiteWarningExcludedDomains() {
        return leavingSecureSiteWarningExcludedDomains;
    }

    /**
     * Sets the leaving secure site warning excluded domains.
     *
     * @param leavingSecureSiteWarningExcludedDomains the new leaving secure site warning excluded domains
     */
    public void setLeavingSecureSiteWarningExcludedDomains(String leavingSecureSiteWarningExcludedDomains) {
        this.leavingSecureSiteWarningExcludedDomains = leavingSecureSiteWarningExcludedDomains;
    }

    /**
     * Gets the exit transaction link url.
     *
     * @return the exit transaction link url
     */
    public String getExitTransactionLinkUrl() {
        return exitTransactionLinkUrl;
    }

    /**
     * Sets the exit transaction link url.
     *
     * @param exitTransactionLinkUrl the new exit transaction link url
     */
    public void setExitTransactionLinkUrl(String exitTransactionLinkUrl) {
        this.exitTransactionLinkUrl = exitTransactionLinkUrl;
    }

    /**
     * Gets the contact link english url.
     *
     * @return the contact link english url
     */
    public String getContactLinkEnglishUrl() {
        return contactLinkEnglishUrl;
    }

    /**
     * Sets the contact link english url.
     *
     * @param contactLinkEnglishUrl the new contact link english url
     */
    public void setContactLinkEnglishUrl(String contactLinkEnglishUrl) {
        this.contactLinkEnglishUrl = contactLinkEnglishUrl;
    }

    /**
     * Gets the contact link french url.
     *
     * @return the contact link french url
     */
    public String getContactLinkFrenchUrl() {
        return contactLinkFrenchUrl;
    }

    /**
     * Sets the contact link french url.
     *
     * @param contactLinkFrenchUrl the new contact link french url
     */
    public void setContactLinkFrenchUrl(String contactLinkFrenchUrl) {
        this.contactLinkFrenchUrl = contactLinkFrenchUrl;
    }

    /**
     * Gets the terms link english url.
     *
     * @return the terms link english url
     */
    public String getTermsLinkEnglishUrl() {
        return termsLinkEnglishUrl;
    }

    /**
     * Sets the terms link english url.
     *
     * @param termsLinkEnglishUrl the new terms link english url
     */
    public void setTermsLinkEnglishUrl(String termsLinkEnglishUrl) {
        this.termsLinkEnglishUrl = termsLinkEnglishUrl;
    }

    /**
     * Gets the terms link french url.
     *
     * @return the terms link french url
     */
    public String getTermsLinkFrenchUrl() {
        return termsLinkFrenchUrl;
    }

    /**
     * Sets the terms link french url.
     *
     * @param termsLinkFrenchUrl the new terms link french url
     */
    public void setTermsLinkFrenchUrl(String termsLinkFrenchUrl) {
        this.termsLinkFrenchUrl = termsLinkFrenchUrl;
    }

    /**
     * Gets the privacy link english url.
     *
     * @return the privacy link english url
     */
    public String getPrivacyLinkEnglishUrl() {
        return privacyLinkEnglishUrl;
    }

    /**
     * Sets the privacy link english url.
     *
     * @param privacyLinkEnglishUrl the new privacy link english url
     */
    public void setPrivacyLinkEnglishUrl(String privacyLinkEnglishUrl) {
        this.privacyLinkEnglishUrl = privacyLinkEnglishUrl;
    }

    /**
     * Gets the privacy link french url.
     *
     * @return the privacy link french url
     */
    public String getPrivacyLinkFrenchUrl() {
        return privacyLinkFrenchUrl;
    }

    /**
     * Sets the privacy link french url.
     *
     * @param privacyLinkFrenchUrl the new privacy link french url
     */
    public void setPrivacyLinkFrenchUrl(String privacyLinkFrenchUrl) {
        this.privacyLinkFrenchUrl = privacyLinkFrenchUrl;
    }
}
