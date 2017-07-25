package ca.canada.ised.wet.cdts.components.wet.interceptor;

import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ca.canada.ised.wet.cdts.components.wet.breadcrumbs.BreadCrumbs;
import ca.canada.ised.wet.cdts.components.wet.breadcrumbs.BreadcrumbService;
import ca.canada.ised.wet.cdts.components.wet.config.WETModelKey;
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
 *
 * @author Frank Giusto
 */
@Component
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

    /**
     * The show exit transaction link. This can be turned off in your application.properties
     */
    @Value("${show.wet.exit.transaction:true}")
    private Boolean showExitTransaction;

    /** The locale resolver. */
    @Autowired
    private LocaleResolver localeResolver;

    /** Message source. */
    @Autowired
    @Qualifier("messageSource")
    private MessageSource applicationMessageSource;

    /** The breadcrumbs service. */
    @Autowired
    private BreadcrumbService breadcrumbsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {

        if (null != modelAndView && StringUtils.isNotBlank(modelAndView.getViewName())) {

            LOG.debug("---Post Method Execution---postHandle() " + request.getServletPath() + ", "
                + modelAndView.getViewName());

            // Set any session timeout properties
            setSessionTimeoutProperties(defaultCdnSettings, modelAndView);

            // Set the Locale Toggle
            setLanguageRequestUrl(modelAndView, request);

            // Set Side Menu
            setSideMenu(modelAndView);

            // Breadcrumbs
            setBreadCrumbs(modelAndView, request);

            // Leaving Secure Site
            getLeavingSecureSiteWarning(defaultCdnSettings, modelAndView);

            // Set Exit Transaction
            setExitTransaction(defaultCdnSettings, modelAndView, request);

            // Set Footer Links
            setFooterLinks(defaultCdnSettings, modelAndView, request);

            // Put CDN object in model view.
            modelAndView.addObject(WETModelKey.CDN.wetAttributeName(), defaultCdnSettings);
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
            if (StringUtils.isNotBlank(cdnSettings.getPrivacy().getEnglish().getUrl())) {
                modelAndView.addObject(WETModelKey.PRIVACY_LINK.wetAttributeName(),
                    cdnSettings.getPrivacy().getEnglish().getUrl());
            }
            if (StringUtils.isNotBlank(cdnSettings.getTerms().getEnglish().getUrl())) {
                modelAndView.addObject(WETModelKey.TERMS_LINK.wetAttributeName(),
                    cdnSettings.getTerms().getEnglish().getUrl());
            }

        } else {
            if (StringUtils.isNotBlank(cdnSettings.getPrivacy().getFrench().getUrl())) {
                modelAndView.addObject(WETModelKey.TERMS_LINK.wetAttributeName(),
                    cdnSettings.getPrivacy().getFrench().getUrl());
            }
            if (StringUtils.isNotBlank(cdnSettings.getTerms().getFrench().getUrl())) {
                modelAndView.addObject(WETModelKey.PRIVACY_LINK.wetAttributeName(),
                    cdnSettings.getTerms().getFrench().getUrl());
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
            contactInformation.setHref(cdnSettings.getContact().getEnglish().getUrl());
            contactInformation
                .setText(applicationMessageSource.getMessage("cdn.contact.link.text", null, Locale.CANADA));
        } else {
            contactInformation.setHref(cdnSettings.getContact().getFrench().getUrl());
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
        if (!showExitTransaction) {
            modelAndView.addObject(WETModelKey.EXIT_TRANSACTION.wetAttributeName(), "");
            return;
        }
        List<ExitTransaction> exitTransactionList = new ArrayList<>();
        ExitTransaction exitTransaction = new ExitTransaction();
        exitTransaction.setHref(request.getContextPath() + cdnSettings.getExit().getUrl());
        exitTransaction.setTitle(applicationMessageSource.getMessage("cdn.exit.transaction.link.label", null,
            LocaleContextHolder.getLocale()));

        exitTransactionList.add(exitTransaction);

        modelAndView.addObject(WETModelKey.EXIT_TRANSACTION.wetAttributeName(), exitTransactionList);
    }

    /**
     * Sets the bread crumbs for the requested view.
     *
     * @param modelAndView the model and view
     * @param request the request
     */
    private void setBreadCrumbs(ModelAndView modelAndView, HttpServletRequest request) {

        // Get view breadCrumbs
        StringBuilder viewParameters = new StringBuilder();
        viewParameters.append(request.getRequestURI()).append(getRequestParameters(request).toString());
        breadcrumbsService.buildBreadCrumbs(modelAndView.getViewName(), viewParameters.toString());
        List<Object> viewBreadCrumbs = breadcrumbsService.getBreadCrumbList();
        String breadCrumbsKey = WETModelKey.BREADCRUMBS.wetAttributeName();

        ModelMap modelMap = modelAndView.getModelMap();
        Object breadCrumbList = modelMap.get(breadCrumbsKey);
        // if list is provided by the BreadCrumbs key in the ModelMap then add
        // to the BreadCrumbs list
        if (breadCrumbList != null && breadCrumbList instanceof BreadCrumbs) {
            BreadCrumbs breadCrumbs = (BreadCrumbs) breadCrumbList;
            // list should not be null but check for null anyway
            if (breadCrumbs.getList() != null && !breadCrumbs.getList().isEmpty()) {
                viewBreadCrumbs.addAll(breadCrumbs.getList());
            }
        }
        modelAndView.addObject(breadCrumbsKey, viewBreadCrumbs);
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
        if (cdnSettings.getSession().getTimeout().getEnabled()) {
            // Build data-wb-sessto
            StringBuilder dataWbSessionTo = new StringBuilder();
            dataWbSessionTo.append("<span class=\"wb-sessto\" data-wb-sessto='{");
            dataWbSessionTo.append("\"inactivity\": ").append(cdnSettings.getSession().getInactivity().getValue());
            dataWbSessionTo.append(",").append("\"reactionTime\": ")
                .append(cdnSettings.getSession().getReactiontime().getValue());
            dataWbSessionTo.append(",").append("\"sessionalive\":")
                .append(cdnSettings.getSession().getSessionalive().getValue());
            if (StringUtils.isNotBlank(cdnSettings.getSession().getRefreshcallbackurl())) {
                dataWbSessionTo.append(",").append("\"refreshCallbackUrl\": ").append("\"")
                    .append(cdnSettings.getSession().getRefreshcallbackurl()).append("\"");
            }
            if (null != cdnSettings.getSession().getRefreshonclick()) {
                dataWbSessionTo.append(",").append("\"refreshOnClick\": ")
                    .append(cdnSettings.getSession().getRefreshonclick());
            }
            if (null != cdnSettings.getSession().getRefreshlimit().getValue()) {
                dataWbSessionTo.append(",").append("\"refreshLimit\": ")
                    .append(cdnSettings.getSession().getRefreshlimit().getValue());
            }
            if (StringUtils.isNotBlank(cdnSettings.getSession().getMethod())) {
                dataWbSessionTo.append(",").append("\"method\":").append("\"")
                    .append(cdnSettings.getSession().getMethod()).append("\"");
            }
            if (StringUtils.isNotBlank(cdnSettings.getSession().getAdditionaldata())) {
                dataWbSessionTo.append(",").append("\"additionalData\":").append("\"")
                    .append(cdnSettings.getSession().getAdditionaldata()).append("\"");
            }
            dataWbSessionTo.append(",").append("\"logouturl\":").append("\"")
                .append(cdnSettings.getSession().getLogouturl()).append("\"");
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
        exitScript.setExitScriptEnabled(cdnSettings.getLeavingsecuresitewarning().getEnabled());
        exitScript.setExitUrl(cdnSettings.getLeavingsecuresitewarning().getRedirecturl());
        exitScript.setExitMessage((applicationMessageSource.getMessage("cdn.leavingsecuresitewarning.message", null,
            LocaleContextHolder.getLocale())));

        exitScript.setExitExcludedDomains(cdnSettings.getLeavingsecuresitewarning().getExcludeddomains());

        modelAndView.addObject(WETModelKey.LEAVING_SECURE_SITE.wetAttributeName(), exitScript);
    }

}