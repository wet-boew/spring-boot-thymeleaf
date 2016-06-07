package ca.canada.ised.wet.cdts.components.wet.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ca.canada.ised.wet.cdts.components.wet.breadcrumbs.BreadcrumbService;
import ca.canada.ised.wet.cdts.components.wet.config.WETModelKey;
import ca.canada.ised.wet.cdts.components.wet.config.WETResourceBundle;
import ca.canada.ised.wet.cdts.components.wet.config.WETSettings;
import ca.canada.ised.wet.cdts.components.wet.exit.ExitScript;
import ca.canada.ised.wet.cdts.components.wet.exit.ExitTransaction;
import ca.canada.ised.wet.cdts.components.wet.footer.ContactInformation;
import ca.canada.ised.wet.cdts.components.wet.sidemenu.MenuLink;
import ca.canada.ised.wet.cdts.components.wet.sidemenu.SectionMenu;
import ca.canada.ised.wet.cdts.components.wet.sidemenu.SideMenuConfig;
import ca.canada.ised.wet.cdts.components.wet.utils.Language;

/**
 * The Class WETTemplateInterceptor populates the Spring Thymeleaf WET Template with properties from the cdn.properties
 * file or any page level overrides.
 */
public class WETTemplateInterceptor extends HandlerInterceptorAdapter {

    /** Logging instance. */
    private static final Logger LOG = LoggerFactory.getLogger(WETTemplateInterceptor.class);

    /** The default cdn settings. */
    @Autowired
    private WETSettings defaultCdnSettings;

    /** The side menu config. */
    @Autowired
    private SideMenuConfig sideMenuConfig;

    /** The thymeleaf folder. */
    @Value("${spring.thymeleaf.prefix}")
    private String thymeleafFolder;

    /** The locale resolver. */
    @Autowired
    private LocaleResolver localeResolver;

    /** Message source. */
    @Autowired
    private MessageSource applicationMessageSource;

    /** The breadcrumbs bean. */
    @Autowired
    private BreadcrumbService breadcrumbsBean;

    /** The template resource. */
    private final Map<String, WETResourceBundle> templateResource = new HashMap<>();

    /**
     * The Constant THYMELEAF_PREFIX_SEARCH_STRING indicates the string to search for in the property defined by key
     * spring.thymeleaf.prefix.
     */
    private static final String THYMELEAF_PREFIX_SEARCH_STRING = ":/";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {

        if (null != modelAndView && StringUtils.isNoneBlank(modelAndView.getViewName())) {

            LOG.debug("---Post Method Execution---postHandle() " + request.getServletPath() + ", "
                + modelAndView.getViewName());

            WETSettings cdnSettings = new WETSettings();

            // Check if page level properties exist and override with those.
            if (!templateResource.containsKey(modelAndView.getViewName())) {

                templateResource.put(modelAndView.getViewName(), getBundle(modelAndView.getViewName()));
            }
            WETResourceBundle pageMessageSource = templateResource.get(modelAndView.getViewName());

            // Set default
            BeanUtils.copyProperties(defaultCdnSettings, cdnSettings);

            // Set page
            setPageTemplateProperties(cdnSettings, pageMessageSource);

            // Set any session timeout properties
            setSessionTimeoutProperties(cdnSettings, modelAndView);

            // Set the Locale Toggle
            setLanguageRequestUrl(modelAndView, request);

            // Set Side Menu
            setSideMenu(modelAndView);

            // Breadcrumbs
            setBreadCrumbs(cdnSettings, modelAndView, request);

            // Leaving Secure Site
            getLeavingSecureSiteWarning(cdnSettings, modelAndView);

            // Set Exit Transaction
            setExitTransaction(cdnSettings, modelAndView, request);

            // Set Footer Links
            setFooterLinks(cdnSettings, modelAndView, request);

            // Put CDN object in model view.
            modelAndView.addObject(WETModelKey.CDN.wetAttributeName(), cdnSettings);
        }
    }

    /**
     * Sets the footer links if the user wishes to override the WET4 defaults.
     *
     * @param cdnSettings the cdn settings
     * @param modelAndView the model and view
     * @param request the request
     */
    private void setFooterLinks(WETSettings cdnSettings, ModelAndView modelAndView, HttpServletRequest request) {

        setContactInformation(cdnSettings, modelAndView, request);

        if (LocaleContextHolder.getLocale().getLanguage().equals(Locale.CANADA.getLanguage())) {
            if (StringUtils.isNotBlank(cdnSettings.getPrivacyLinkEnglishUrl())) {
                modelAndView.addObject(WETModelKey.PRIVACY_LINK.wetAttributeName(),
                    cdnSettings.getPrivacyLinkEnglishUrl());
            }
            if (StringUtils.isNotBlank(cdnSettings.getTermsLinkEnglishUrl())) {
                modelAndView.addObject(WETModelKey.TERMS_LINK.wetAttributeName(), cdnSettings.getTermsLinkEnglishUrl());
            }
        } else {
            if (StringUtils.isNotBlank(cdnSettings.getPrivacyLinkFrenchUrl())) {
                modelAndView.addObject(WETModelKey.TERMS_LINK.wetAttributeName(),
                    cdnSettings.getPrivacyLinkFrenchUrl());
            }
            if (StringUtils.isNotBlank(cdnSettings.getTermsLinkFrenchUrl())) {
                modelAndView.addObject(WETModelKey.PRIVACY_LINK.wetAttributeName(),
                    cdnSettings.getTermsLinkFrenchUrl());
            }
        }
    }

    /**
     * Sets the contact information if the user wishes to override the WET4 defaults.
     *
     * @param cdnSettings the cdn settings
     * @param modelAndView the model and view
     * @param request the request
     */
    private void setContactInformation(WETSettings cdnSettings, ModelAndView modelAndView, HttpServletRequest request) {
        List<ContactInformation> contactInformationList = new ArrayList<>();
        ContactInformation contactInformation = new ContactInformation();
        if (LocaleContextHolder.getLocale().getLanguage().equals(Locale.CANADA.getLanguage())) {
            contactInformation.setHref(cdnSettings.getContactLinkEnglishUrl());
            contactInformation
                .setText(applicationMessageSource.getMessage("cdn.contact.link.text", null, Locale.CANADA));
        } else {
            contactInformation.setHref(cdnSettings.getContactLinkFrenchUrl());
            contactInformation
                .setText(applicationMessageSource.getMessage("cdn.contact.link.text", null, Locale.CANADA_FRENCH));
        }
        contactInformationList.add(contactInformation);
        if (StringUtils.isNotBlank(contactInformation.getHref())) {
            modelAndView.addObject(WETModelKey.CONTACT_LINK.wetAttributeName(), contactInformationList);
        }
    }

    /**
     * Sets the exit transaction.
     *
     * @param cdnSettings the cdn settings
     * @param modelAndView the model and view
     * @param request the request
     */
    private void setExitTransaction(WETSettings cdnSettings, ModelAndView modelAndView, HttpServletRequest request) {

        // TODO It would be nice to do this only for pages using Transaction
        // layouts.
        List<ExitTransaction> exitTransactionList = new ArrayList<>();
        ExitTransaction exitTransaction = new ExitTransaction();
        exitTransaction.setHref(request.getContextPath() + cdnSettings.getExitTransactionLinkUrl());
        exitTransaction.setTitle(applicationMessageSource.getMessage("cdn.exit.transaction.link.label", null,
            LocaleContextHolder.getLocale()));

        exitTransactionList.add(exitTransaction);

        modelAndView.addObject(WETModelKey.EXIT_TRANSACTION.wetAttributeName(), exitTransactionList);
    }

    /**
     * Sets the bread crumbs for the requested view.
     *
     * @param cdnSettings the cdn settings
     * @param modelAndView the model and view
     * @param request the request
     */
    private void setBreadCrumbs(WETSettings cdnSettings, ModelAndView modelAndView, HttpServletRequest request) {

        StringBuilder viewParameters = new StringBuilder();
        viewParameters.append(request.getRequestURI()).append(getRequestParameters(request).toString());

        breadcrumbsBean.buildBreadCrumbs(modelAndView.getViewName(), viewParameters.toString());
        modelAndView.addObject(WETModelKey.BREADCRUMBS.wetAttributeName(), breadcrumbsBean.getBreadCrumbList());
    }

    /**
     * Sets the side menu.
     *
     * @param modelAndView the new side menu
     */
    private void setSideMenu(ModelAndView modelAndView) {
        // Side Menu
        if (modelAndView.getModelMap().containsAttribute(WETModelKey.PAGE_SECTION_MENU.wetAttributeName())) {
            modelAndView.addObject(WETModelKey.SECTIONS.wetAttributeName(),
                modelAndView.getModelMap().get(WETModelKey.PAGE_SECTION_MENU.wetAttributeName()));
        } else {
            modelAndView.addObject(WETModelKey.SECTIONS.wetAttributeName(),
                getSectionMenuText(sideMenuConfig.getSectionMenuList()));
        }
    }

    /**
     * Gets the section menu text.
     *
     * @param sectionMenuList the section menu list
     * @return the section menu text
     */
    private List<SectionMenu> getSectionMenuText(List<SectionMenu> sectionMenuList) {

        for (SectionMenu sectionMenu : sectionMenuList) {
            if (LocaleContextHolder.getLocale().getLanguage().equals(Locale.CANADA.getLanguage())) {
                sectionMenu.setSectionName(sectionMenu.getSectionNameEn());
            } else {
                sectionMenu.setSectionName(sectionMenu.getSectionNameFr());
            }
            for (MenuLink menuItem : sectionMenu.getMenuLinks()) {
                if (LocaleContextHolder.getLocale().getLanguage().equals(Locale.CANADA.getLanguage())) {
                    menuItem.setText(menuItem.getTextEn());
                } else {
                    menuItem.setText(menuItem.getTextFr());
                }
            }
        }
        return sectionMenuList;
    }

    /**
     * Sets the language request url.
     *
     * @param modelAndView the model and view
     * @param request the request
     */
    private void setLanguageRequestUrl(ModelAndView modelAndView, HttpServletRequest request) {

        StringBuilder requestParameters = getRequestParameters(request);

        setLanguageToggle(request, requestParameters);

        modelAndView.addObject(WETModelKey.LANGUAGE_URL.wetAttributeName(), requestParameters.toString());
    }

    /**
     * Gets the request parameters.
     *
     * @param request the request
     * @return the request parameters
     */
    private StringBuilder getRequestParameters(HttpServletRequest request) {
        StringBuilder requestParameters = new StringBuilder();

        Map<String, String[]> requestParams = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = requestParams.entrySet();

        int element = 0;
        for (Entry<String, String[]> entry : entrySet) {
            String key = entry.getKey(); // parameter name
            String[] value = entry.getValue(); // parameter value
            for (String val : value) {
                if (!"lang".equals(key) && !WETModelKey.LANGUAGE_URL.wetAttributeName().equals(key)) {
                    getParameterIdentifier(element == 0, requestParameters);
                    requestParameters.append(key).append("=").append(val);
                    element++;
                }
            }
        }
        return requestParameters;
    }

    /**
     * Gets the parameter identifier.
     *
     * @param firstParameter the first parameter
     * @param requestParameters the request parameters
     * @return the parameter identifier
     */
    private void getParameterIdentifier(boolean firstParameter, StringBuilder requestParameters) {
        if (firstParameter) {
            requestParameters.append("?");
        } else {
            requestParameters.append("&");
        }
    }

    /**
     * Sets the language toggle.
     *
     * @param request the request
     * @param langUrl the lang url
     */
    private void setLanguageToggle(HttpServletRequest request, StringBuilder langUrl) {

        String requestedLanguage = null;
        String requestLang = request.getParameter("lang");
        Locale requestLocale = localeResolver.resolveLocale(request);

        String otherLocaleString = null;
        if (null != requestLang) {
            requestedLanguage = requestLang;
        } else {
            requestedLanguage = Language.getLanguage(requestLocale);
        }
        if (requestedLanguage.equals(Language.ENGLISH)) {
            otherLocaleString = Language.FRENCH;
        } else {
            otherLocaleString = Language.ENGLISH;
        }
        if (!langUrl.toString().contains("?")) {
            langUrl.append("?lang=").append(otherLocaleString);
        } else {
            langUrl.append("&lang=").append(otherLocaleString);
        }
    }

    /**
     * Sets the session timeout properties in the layout if provided.
     *
     * @param cdnSettings the cdn settings
     * @param modelAndView the model and view
     */
    private void setSessionTimeoutProperties(WETSettings cdnSettings, ModelAndView modelAndView) {
        if (cdnSettings.getSessionEnabled()) {
            // Build data-wb-sessto
            StringBuilder dataWbSessionTo = new StringBuilder();
            dataWbSessionTo.append("<span class=\"wb-sessto\" data-wb-sessto='{");
            dataWbSessionTo.append("\"inactivity\": ").append(cdnSettings.getSessionInactivity());
            dataWbSessionTo.append(",").append("\"reactionTime\": ").append(cdnSettings.getSessionReactionTime());
            dataWbSessionTo.append(",").append("\"sessionalive\":").append(cdnSettings.getSessionAlive());
            if (StringUtils.isNotBlank(cdnSettings.getRefreshCallback())) {
                dataWbSessionTo.append(",").append("\"refreshCallback\": ").append("\"")
                    .append(cdnSettings.getRefreshCallback()).append("\"");
            }
            if (StringUtils.isNotBlank(cdnSettings.getRefreshCallbackUrl())) {
                dataWbSessionTo.append(",").append("\"refreshCallbackUrl\": ").append("\"")
                    .append(cdnSettings.getRefreshCallbackUrl()).append("\"");
            }
            if (null != cdnSettings.getRefreshOnClick()) {
                dataWbSessionTo.append(",").append("\"refreshOnClick\": ").append(cdnSettings.getRefreshOnClick());
            }
            if (null != cdnSettings.getRefreshLimit()) {
                dataWbSessionTo.append(",").append("\"refreshLimit\": ").append(cdnSettings.getRefreshLimit());
            }
            if (StringUtils.isNotBlank(cdnSettings.getRequestMethod())) {
                dataWbSessionTo.append(",").append("\"method\":").append("\"").append(cdnSettings.getRequestMethod())
                    .append("\"");
            }
            if (StringUtils.isNotBlank(cdnSettings.getAdditionalData())) {
                dataWbSessionTo.append(",").append("\"additionalData\":").append("\"")
                    .append(cdnSettings.getAdditionalData()).append("\"");
            }
            dataWbSessionTo.append(",").append("\"logouturl\":").append("\"").append(cdnSettings.getLogoutUrl())
                .append("\"");
            dataWbSessionTo.append("}'></span>");
            modelAndView.addObject(WETModelKey.SESSION_TIMEOUT.wetAttributeName(), dataWbSessionTo.toString());
        }
    }

    /**
     * Gets the leaving secure site warning.
     *
     * @param cdnSettings the cdn settings
     * @param modelAndView the model and view
     * @return the leaving secure site warning
     */
    private void getLeavingSecureSiteWarning(WETSettings cdnSettings, ModelAndView modelAndView) {

        ExitScript exitScript = new ExitScript();
        exitScript.setExitScriptEnabled(cdnSettings.getLeavingSecureSiteWarningEnabled());
        exitScript.setExitUrl(cdnSettings.getLeavingSecureSiteWarningRedirectUrl());
        exitScript.setExitMessage((applicationMessageSource.getMessage("cdn.leavingsecuresitewarning.message", null,
            LocaleContextHolder.getLocale())));

        exitScript.setExitExcludedDomains(cdnSettings.getLeavingSecureSiteWarningExcludedDomains());

        modelAndView.addObject(WETModelKey.LEAVING_SECURE_SITE.wetAttributeName(), exitScript);
    }

    /**
     * Sets the page template properties.
     *
     * @param cdnSettings the cdn settings
     * @param messageSource the message source
     */
    private void setPageTemplateProperties(WETSettings cdnSettings, WETResourceBundle messageSource) {

        Set<String> keys = messageSource.getKeys(messageSource.getBasename(), Locale.CANADA);
        if (CollectionUtils.isEmpty(keys)) {
            return; // No page template overrides
        }

        if (keys.contains("cdn.url")) {
            cdnSettings.setUrl(messageSource.getMessage("cdn.url", null, Locale.CANADA));
        }
        if (keys.contains("cdn.environment")) {
            cdnSettings.setEnvironment(messageSource.getMessage("cdn.environment", null, Locale.CANADA));
        }
        if (keys.contains("cdn.goc.webtemplate.showpostcontent")) {
            cdnSettings.setShowPostContent(
                Boolean.valueOf(messageSource.getMessage("cdn.goc.webtemplate.showpostcontent", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.goc.webtemplate.showfeatures")) {
            cdnSettings.setShowFeatures(
                Boolean.valueOf(messageSource.getMessage("cdn.goc.webtemplate.showfeatures", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.goc.webtemplate.showfooter")) {
            cdnSettings.setShowFooter(
                Boolean.valueOf(messageSource.getMessage("cdn.goc.webtemplate.showfooter", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.goc.webtemplate.showfeedbacklink")) {
            cdnSettings.setShowFeedback(
                Boolean.valueOf(messageSource.getMessage("cdn.goc.webtemplate.showfeedbacklink", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.goc.webtemplate.showsharepagelink")) {
            cdnSettings.setShowShare(Boolean
                .valueOf(messageSource.getMessage("cdn.goc.webtemplate.showsharepagelink", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.session.timeout.enabled")) {
            cdnSettings.setSessionEnabled(
                Boolean.valueOf(messageSource.getMessage("cdn.session.timeout.enabled", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.session.inactivity.value")) {
            cdnSettings.setSessionInactivity(
                Integer.valueOf(messageSource.getMessage("cdn.session.inactivity.value", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.session.reactiontime.value")) {
            cdnSettings.setSessionReactionTime(
                Integer.valueOf(messageSource.getMessage("cdn.session.reactiontime.value", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.session.sessionalive.value")) {
            cdnSettings.setSessionAlive(
                Integer.valueOf(messageSource.getMessage("cdn.session.sessionalive.value", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.session.logouturl")) {
            cdnSettings.setLogoutUrl(messageSource.getMessage("cdn.session.logouturl", null, Locale.CANADA));
        }
        if (keys.contains("cdn.session.refreshcallback")) {
            cdnSettings
                .setRefreshCallback(messageSource.getMessage("cdn.session.refreshcallback", null, Locale.CANADA));
        }
        if (keys.contains("cdn.session.refreshcallbackurl")) {
            cdnSettings
                .setRefreshCallbackUrl(messageSource.getMessage("cdn.session.refreshcallbackurl", null, Locale.CANADA));
        }
        if (keys.contains("cdn.session.refreshonclick")) {
            cdnSettings.setRefreshOnClick(
                Boolean.valueOf(messageSource.getMessage("cdn.session.refreshonclick", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.session.refreshlimit.value")) {
            cdnSettings.setRefreshLimit(
                Integer.valueOf(messageSource.getMessage("cdn.session.refreshlimit.value", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.session.method")) {
            cdnSettings.setRequestMethod(messageSource.getMessage("cdn.session.method", null, Locale.CANADA));
        }
        if (keys.contains("cdn.session.additionaldata")) {
            cdnSettings.setAdditionalData(messageSource.getMessage("cdn.session.additionaldata", null, Locale.CANADA));
        }
        if (keys.contains("cdn.goc.webtemplate.showsearch")) {
            cdnSettings.setShowSearch(
                Boolean.valueOf(messageSource.getMessage("cdn.goc.webtemplate.showsearch", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.leavingsecuresitewarning.enabled")) {
            cdnSettings.setLeavingSecureSiteWarningEnabled(
                Boolean.valueOf(messageSource.getMessage("cdn.leavingsecuresitewarning.enabled", null, Locale.CANADA)));
        }
        if (keys.contains("cdn.leavingsecuresitewarning.redirecturl")) {
            cdnSettings.setLeavingSecureSiteWarningRedirectUrl(
                messageSource.getMessage("cdn.leavingsecuresitewarning.redirecturl", null, Locale.CANADA));
        }
        if (keys.contains("cdn.leavingsecuresitewarning.excludeddomains")) {
            cdnSettings.setLeavingSecureSiteWarningExcludedDomains(
                messageSource.getMessage("cdn.leavingsecuresitewarning.excludeddomains", null, Locale.CANADA));
        }
        if (keys.contains("cdn.contact.link.english.url")) {
            cdnSettings.setContactLinkEnglishUrl(
                messageSource.getMessage("cdn.contact.link.english.url", null, Locale.CANADA));
        }
        if (keys.contains("cdn.contact.link.french.url")) {
            cdnSettings
                .setContactLinkEnglishUrl(messageSource.getMessage("cdn.contact.link.french.url", null, Locale.CANADA));
        }
        if (keys.contains("cdn.terms.link.english.url")) {
            cdnSettings
                .setTermsLinkEnglishUrl(messageSource.getMessage("cdn.terms.link.english.url", null, Locale.CANADA));
        }
        if (keys.contains("cdn.terms.link.french.url")) {
            cdnSettings
                .setTermsLinkFrenchUrl(messageSource.getMessage("cdn.terms.link.french.url", null, Locale.CANADA));
        }
        if (keys.contains("cdn.privacy.link.english.url")) {
            cdnSettings.setPrivacyLinkEnglishUrl(
                messageSource.getMessage("cdn.privacy.link.english.url", null, Locale.CANADA));
        }
        if (keys.contains("cdn.privacy.link.french.url")) {
            cdnSettings
                .setPrivacyLinkFrenchUrl(messageSource.getMessage("cdn.privacy.link.french.url", null, Locale.CANADA));
        }
    }

    /**
     * Gets the WET resource bundle.
     *
     * @param view the thymeleaf view name
     * @return the bundle
     */
    private WETResourceBundle getBundle(String view) {

        if (StringUtils.isBlank(thymeleafFolder)) {
            throw new java.lang.IllegalArgumentException("Cannot find spring.thymeleaf.prefix application property.");
        }
        int thymleafFolderPosition = thymeleafFolder.indexOf(THYMELEAF_PREFIX_SEARCH_STRING);
        if (thymleafFolderPosition == -1) {
            throw new java.lang.IllegalArgumentException("Cannot find valid spring.thymeleaf.prefix key/value.");
        }

        StringBuilder baseName = new StringBuilder();
        WETResourceBundle pageMessageSource = new WETResourceBundle();
        baseName.append(thymeleafFolder.substring(thymleafFolderPosition + THYMELEAF_PREFIX_SEARCH_STRING.length()))
            .append(view);
        pageMessageSource.setBasenames(baseName.toString());

        return pageMessageSource;
    }

}