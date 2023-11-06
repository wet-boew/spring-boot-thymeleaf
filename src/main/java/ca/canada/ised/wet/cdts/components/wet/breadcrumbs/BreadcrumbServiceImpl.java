package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import ca.canada.ised.wet.cdts.components.wet.config.WETModelKey;
import ca.canada.ised.wet.cdts.components.wet.config.WETResourceBundle;

/**
 * The Class BreadcrumbServiceImpl is responsible for building and retrieving application breadcrumbs.
 *
 * @author Frank Giusto
 */
@Component
public class BreadcrumbServiceImpl implements BreadcrumbService, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** The position Constant for the APPLICATION_HOME_CRUMB. */
    private static final int APPLICATION_HOME_CRUMB = 2;

    /** The Constant LAST_BREAD_CRUMB. */
    private static final int LAST_BREAD_CRUMB = 2;

    /** The Constant HOME_BREAD_CRUMB. */
    private static final int HOME_BREAD_CRUMB = 0;

    /** The Constant DEPARTMENT_HOME_BREAD_CRUMB. */
    private static final int DEPARTMENT_HOME_BREAD_CRUMB = 1;

    /** Logging instance. */
    private static final Logger LOG = LoggerFactory.getLogger(BreadcrumbServiceImpl.class);

    /**
     * The Constant HOME_AND_DEPARTMENT which breadcrumbs are to be returned from the breadcrumb map.
     */
    private static final int HOME_AND_DEPARTMENT = 2;

    /**
     * The show landing page breadcrumb property of true states that the application will always show the landing page
     * breadcrumb. This property can be overridden in the appliation.properties. Default is false.
     */
    @Value("${show.landing.page.breadcrumb:true}")
    private Boolean showLandingPageBreadcrumb;

    @Autowired
    private ServletContext servletContext;

    /** The bread session. */
    @Autowired
    private BreadCrumbSession breadcrumbSession;

    /** Message source. */
    @Autowired
    @Qualifier("messageSource")
    private MessageSource applicationMessageSource;

    /** The breadcrumb message source. */
    private static WETResourceBundle breadcrumbMessageSource = getBreadCrumbBundle();

    /** {@inheritDoc} */
    @Override
    public void buildBreadCrumbs(String currentView, String requestURL) {

        Set<String> keys = breadcrumbMessageSource.getKeys(breadcrumbMessageSource.getBasename(),
            LocaleContextHolder.getLocale());
        if (CollectionUtils.isEmpty(keys)) {
            // User may have chosen not to include breadcrumbs in their
            // application.
            LOG.info("No breadcrumbs.properties found in folder defined by spring.thymeleaf.prefix");
            // Home / Department Home still required.
            createBreadCrumbList();
            return;
        }
        // Initialize-Validate Bread Crumbs
        String rootKey = validateBreadCrumbProperties();

        if (CollectionUtils.isEmpty(getBreadcrumbMap()) || rootKey.equals(breadcrumbKey(currentView))) {
            createBreadCrumbList();
            if (rootKey.equals(breadcrumbKey(currentView))) {
                // create bread crumb for application home
                this.addBreadCrumb(createBreadCrumb(currentView, requestURL, getBreadcrumbAcronym(keys, currentView),
                    breadcrumbMessageSource), currentView);
            }
        }
        // Populate New Bread Crumb
        if (keys.contains(breadcrumbKey(currentView))) {
            if (getBreadcrumbMap().containsKey(currentView)) {
                if (!rootKey.equals(breadcrumbKey(currentView))) {
                    // going back to page previously visited
                    setPreviouslyVisitedBreadCrumb(currentView);
                }
            } else {
                // create new breadcrumb for requested view
                BreadCrumb newBreadCrumb = createBreadCrumb(currentView, requestURL,
                    getBreadcrumbAcronym(keys, currentView), breadcrumbMessageSource);
                this.addBreadCrumb(newBreadCrumb, currentView);
            }
        } else {
            if (!getBreadcrumbMap().containsKey(BreadcrumbResource.PLACEHOLDER.value())) {
                this.addBreadCrumb(new BreadCrumb(BreadcrumbResource.PLACEHOLDER.value()),
                    BreadcrumbResource.PLACEHOLDER.value());
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<Object> getBreadCrumbList() {

        if (breadcrumbSession.getBreadCrumbMap().size() == 0) {
            LOG.error("No Breadcrumbs exist. We at least need a Home link.");
            return new ArrayList<>();
        }
        // return all but the last breadcrumb which is the current page.
        List<BreadCrumb> breadCrumbList = new ArrayList<>();

        checkApplicationHome();

        Iterator<String> breadcrumbKeyIterator = getBreadcrumbMap().keySet().iterator();

        while (breadcrumbKeyIterator.hasNext()) {
            String breadcrumbKey = breadcrumbKeyIterator.next();
            breadCrumbList.add(setBreadCrumbLanguageTitle(breadcrumbKey, getBreadcrumbMap().get(breadcrumbKey)));
        }
        return adjustForBreadcrumbLength(breadCrumbList);
    }

    /**
     * Validate bread crumb properties. root.key and associated key for root.key value must exist.
     *
     * @return the string
     */
    private String validateBreadCrumbProperties() {
        // Initialize-Validate Bread Crumbs
        String rootKey = breadcrumbMessageSource.getMessage(BreadcrumbResource.ROOT_KEY.value(), null, Locale.CANADA);

        String rootKey2 = breadcrumbMessageSource.getMessage(BreadcrumbResource.ROOT_KEY.value(), null,
            Locale.CANADA_FRENCH);

        Assert.notNull(rootKey, "rookKey cannot be null");
        Assert.isTrue(rootKey.equals(rootKey2), "The rootKey ust be equal to the rootKey2");

        Assert.notNull(breadcrumbMessageSource.getMessage(rootKey, null, Locale.CANADA),
            String.format("The message for key %s and locale %s cannot be null", rootKey, Locale.CANADA));
        Assert.notNull(breadcrumbMessageSource.getMessage(rootKey2, null, Locale.CANADA_FRENCH),
            String.format("The message for key %s and locale %s cannot be null", rootKey, Locale.CANADA_FRENCH));

        return rootKey;
    }

    /**
     * Check application home. A bread crumb should exist for Application Home
     */
    private void checkApplicationHome() {

        if (!breadcrumbsConfigured()) {
            // Bread crumbs are not configured for this application.
            return;
        }
        boolean rootKeyFound = false;
        // Make sure home page bread crumb exists. If not, user entered
        // application on a page within the application.
        // Add a link back to the application home.
        String rootKey = breadcrumbMessageSource.getMessage(BreadcrumbResource.ROOT_KEY.value(), null,
            LocaleContextHolder.getLocale());
        for (BreadCrumb bc : getBreadcrumbMap().values()) {
            if (breadcrumbKey(bc.getViewName()).equals(rootKey)) {
                rootKeyFound = true;
                break;
            }
        }
        if (!rootKeyFound) {
            insertHomePage(rootKey);
        }
    }

    /**
     * Insert home page. Add an Application Home breadcrumb if one doesn't exist.
     *
     * @param rootKey the root key
     */
    private void insertHomePage(String rootKey) {

        Map<String, BreadCrumb> ajustedBreadCrumbList = new LinkedHashMap<>();

        if (getBreadcrumbMap().size() == HOME_AND_DEPARTMENT) {
            getBreadcrumbMap().put(extractView(rootKey),
                createBreadCrumb(extractView(rootKey), servletContext.getContextPath(), "", breadcrumbMessageSource));
            return;
        }

        Collection<BreadCrumb> crumbs = getBreadcrumbMap().values();
        Iterator<BreadCrumb> iterator = crumbs.iterator();

        int idx = 0;
        while (iterator.hasNext()) {
            BreadCrumb breadCrumb = iterator.next();
            if (idx == HOME_AND_DEPARTMENT) {
                ajustedBreadCrumbList.put(extractView(rootKey), createBreadCrumb(extractView(rootKey),
                    servletContext.getContextPath(), "", breadcrumbMessageSource));
            }
            ajustedBreadCrumbList.put(breadCrumb.getViewName(), breadCrumb);
            idx++;
        }

        getBreadcrumbMap().clear();
        getBreadcrumbMap().putAll(ajustedBreadCrumbList);
    }

    /**
     * Extract view. Get the spring view name based on the breadcrumb key.
     *
     * @param key the key
     * @return the string
     */
    private String extractView(String key) {

        return key.substring(key.indexOf(".") + 1);
    }

    /**
     * Adjust for bread crumb length. Do not show all breadcrumbs. Show and elliptical to indicate this.
     *
     * @param fullBreadCrumbList the full bread crumb list
     * @return the shortened bread crumb list
     */
    private List<Object> adjustForBreadcrumbLength(List<BreadCrumb> fullBreadCrumbList) {
        List<Object> breadCrumbList = new ArrayList<>();
        String rootKey = null;
        if (breadcrumbsConfigured()) {
            rootKey = breadcrumbMessageSource.getMessage(BreadcrumbResource.ROOT_KEY.value(), null,
                LocaleContextHolder.getLocale());
        }

        if (fullBreadCrumbList.size() > 3) {
            BreadCrumbLink breadcrumb = fullBreadCrumbList.get(fullBreadCrumbList.size() - LAST_BREAD_CRUMB);
            if (!breadcrumbsConfigured() || breadcrumb.getViewName().equals(extractView(rootKey))) {
                buildEclipticalCrumb(breadCrumbList, fullBreadCrumbList);
                breadCrumbList.add(fullBreadCrumbList.get(APPLICATION_HOME_CRUMB));
            } else {
                if (showLandingPageBreadcrumb) {
                    buildEclipticalCrumb(breadCrumbList, fullBreadCrumbList);
                    breadCrumbList.add(fullBreadCrumbList.get(APPLICATION_HOME_CRUMB));
                } else {
                    buildEclipticalCrumb(breadCrumbList, fullBreadCrumbList);
                    breadCrumbList.add(fullBreadCrumbList.get(fullBreadCrumbList.size() - LAST_BREAD_CRUMB));
                }
            }
        } else {
            breadCrumbList.add(checkAcronym(fullBreadCrumbList.get(HOME_BREAD_CRUMB)));
            breadCrumbList.add(checkAcronym(fullBreadCrumbList.get(DEPARTMENT_HOME_BREAD_CRUMB)));
        }
        return breadCrumbList;
    }

    /**
     * Builds the ecliptical crumb. The bread crumb ... indicates that some breadcrumbs are not shown.
     *
     * @param breadCrumbList the bread crumb list
     * @param displayedBreadCrumbList the displayed bread crumb list
     */
    private void buildEclipticalCrumb(List<Object> breadCrumbList, List<BreadCrumb> displayedBreadCrumbList) {

        breadCrumbList.add(checkAcronym(displayedBreadCrumbList.get(HOME_BREAD_CRUMB)));

        BreadCrumbTitle breadCrumbShort = new BreadCrumbTitle();
        breadCrumbShort.setTitleFR("...");
        breadCrumbShort.setTitleEN("...");
        breadCrumbShort.setTitle("...");
        breadCrumbList.add(breadCrumbShort);
    }

    /**
     * Check acronym. Add an Acronymn to the breadcrumb if one was configured.
     *
     * @param breadCrumb the bread crumb
     * @return the object
     */
    private Object checkAcronym(BreadCrumb breadCrumb) {

        if (StringUtils.isEmpty(breadCrumb.getAcronym())) {
            BreadCrumbLink breadCrumbLink = new BreadCrumbLink();
            BeanUtils.copyProperties(breadCrumb, breadCrumbLink);
            return breadCrumbLink;
        }

        return breadCrumb;
    }

    /**
     * Breadcrumbs configured. Check to see if bread crumbs have been configured in application.
     *
     * @return true, if successful
     */
    private boolean breadcrumbsConfigured() {
        if (CollectionUtils.isEmpty(
            breadcrumbMessageSource.getKeys(breadcrumbMessageSource.getBasename(), LocaleContextHolder.getLocale()))) {
            return false;
        }
        return true;
    }

    /**
     * Gets the breadcrumb acronym.
     *
     * @param keys the keys
     * @param currentView the current view
     * @return the breadcrumb acronym
     */
    private String getBreadcrumbAcronym(Set<String> keys, String currentView) {

        String acronym = "";
        if (keys.contains(acronymKey(currentView))) {
            acronym = breadcrumbMessageSource.getMessage(acronymKey(currentView), null, Locale.CANADA);
        }
        return acronym;
    }

    /**
     * Creates the bread crumb.
     *
     * @param currentView the current view
     * @param href the href
     * @param acronym the acronym
     * @param messageSource the message source
     * @return the bread crumb
     */
    private BreadCrumb createBreadCrumb(String currentView, String href, String acronym, MessageSource messageSource) {

        BreadCrumb breadCrumb = new BreadCrumb();
        breadCrumb.setTitleEN(getBreadcrumbLabel(currentView, messageSource, Locale.CANADA));
        breadCrumb.setTitleFR(getBreadcrumbLabel(currentView, messageSource, Locale.CANADA_FRENCH));
        breadCrumb.setHref(href);
        breadCrumb.setAcronym(acronym);
        breadCrumb.setViewName(currentView);

        return breadCrumb;
    }

    /**
     * Gets the breadcrumb label.
     *
     * @param currentView the current view
     * @param messageSource the message source
     * @param locale the locale
     * @return the breadcrumb label
     */
    private String getBreadcrumbLabel(String currentView, MessageSource messageSource, Locale locale) {
        StringBuilder breadcrumbKey = new StringBuilder();
        breadcrumbKey.append(BreadcrumbResource.KEY_PREFIX.value()).append(currentView);
        return messageSource.getMessage(breadcrumbKey.toString(), null, locale);
    }

    /**
     * Creates a new bread crumb list containing the first link to Home "www.canada.ca" in the current locale.
     *
     * @return the list
     */
    private Map<String, BreadCrumb> createBreadCrumbList() {

        getBreadcrumbMap().clear();

        BreadCrumb breadCrumb = new BreadCrumb();
        breadCrumb.setTitleEN(applicationMessageSource.getMessage("home", null, Locale.CANADA));
        breadCrumb.setTitleFR(applicationMessageSource.getMessage("home", null, Locale.CANADA_FRENCH));
        breadCrumb.setViewName(WETModelKey.CANADA_HOME.name());
        getBreadcrumbMap().put(WETModelKey.CANADA_HOME.name(), breadCrumb);

        breadCrumb = new BreadCrumb();
        breadCrumb.setTitleEN(applicationMessageSource.getMessage("department.home", null, Locale.CANADA));
        breadCrumb.setTitleFR(applicationMessageSource.getMessage("department.home", null, Locale.CANADA_FRENCH));
        breadCrumb.setViewName(WETModelKey.DEPARTMENT_HOME.name());
        getBreadcrumbMap().put(WETModelKey.DEPARTMENT_HOME.name(), breadCrumb);

        return getBreadcrumbMap();
    }

    /**
     * Sets the canada home url which the current locale.
     *
     * @param language the language
     * @return the string
     */
    private String setCanadaHomeUrl(String language) {

        StringBuilder homeLink = new StringBuilder();
        if ("en".equals(language)) {
            homeLink.append(applicationMessageSource.getMessage("home.url", null, Locale.CANADA));
        } else {
            homeLink.append(applicationMessageSource.getMessage("home.url", null, Locale.CANADA_FRENCH));
        }

        return homeLink.toString();
    }

    /**
     * Adds the bread crumb to the user session map.
     *
     * @param breadCrumb the bread crumb
     * @param currentView the current view
     */
    private void addBreadCrumb(BreadCrumb breadCrumb, String currentView) {

        getBreadcrumbMap().put(currentView, breadCrumb);
    }

    /**
     * Sets the bread crumb language title to the current locale.
     *
     * @param breadcrumbKey the breadcrumb key
     * @param breadCrumb the bread crumb
     * @return the bread crumb
     */
    private BreadCrumb setBreadCrumbLanguageTitle(String breadcrumbKey, BreadCrumb breadCrumb) {

        BreadCrumb localeBreadCrumb = new BreadCrumb();
        BeanUtils.copyProperties(breadCrumb, localeBreadCrumb);

        if (LocaleContextHolder.getLocale().getLanguage().equals(Locale.CANADA_FRENCH.getLanguage())) {
            localeBreadCrumb.setTitle(breadCrumb.getTitleFR());
            if (breadcrumbKey.equals(WETModelKey.CANADA_HOME.name())) {
                localeBreadCrumb.setHref(setCanadaHomeUrl(Locale.CANADA_FRENCH.getLanguage()));
            } else if (breadcrumbKey.equals(WETModelKey.DEPARTMENT_HOME.name())) {
                localeBreadCrumb
                    .setHref(applicationMessageSource.getMessage("department.home.url", null, Locale.CANADA_FRENCH));
            }
        } else {
            localeBreadCrumb.setTitle(breadCrumb.getTitleEN());
            if (breadcrumbKey.equals(WETModelKey.CANADA_HOME.name())) {
                localeBreadCrumb.setHref(setCanadaHomeUrl(Locale.CANADA.getLanguage()));
            } else if (breadcrumbKey.equals(WETModelKey.DEPARTMENT_HOME.name())) {
                localeBreadCrumb
                    .setHref(applicationMessageSource.getMessage("department.home.url", null, Locale.CANADA));
            }
        }
        return localeBreadCrumb;
    }

    /**
     * Sets the previously visited bread crumb.
     *
     * @param currentView the new previously visited bread crumb
     */
    private void setPreviouslyVisitedBreadCrumb(String currentView) {

        Map<String, BreadCrumb> ajustedBreadCrumbList = new LinkedHashMap<>();
        Collection<BreadCrumb> crumbs = getBreadcrumbMap().values();
        Iterator<BreadCrumb> iterator = crumbs.iterator();

        while (iterator.hasNext()) {
            BreadCrumb breadCrumb = iterator.next();
            if (breadCrumb.getViewName().equals(currentView)) {
                ajustedBreadCrumbList.put(breadCrumb.getViewName(), breadCrumb);
                break;
            }
            ajustedBreadCrumbList.put(breadCrumb.getViewName(), breadCrumb);
        }

        getBreadcrumbMap().clear();
        getBreadcrumbMap().putAll(ajustedBreadCrumbList);
    }

    /**
     * Gets the bread crumb bundle.
     *
     * @return the bread crumb bundle
     */
    private static WETResourceBundle getBreadCrumbBundle() {

        WETResourceBundle breadcrumbMessageSource = new WETResourceBundle();
        breadcrumbMessageSource.setBasename(BreadcrumbResource.NAME.value());

        return breadcrumbMessageSource;
    }

    /**
     * Gets the breadcrumb map.
     *
     * @return the breadcrumb map
     */
    private Map<String, BreadCrumb> getBreadcrumbMap() {
        return breadcrumbSession.getBreadCrumbMap();
    }

    /**
     * Acronym key. Build the acronym key based on the current view.
     *
     * @param currentView the current view
     * @return the string
     */
    private String acronymKey(String currentView) {
        StringBuilder acronymKey = new StringBuilder();
        acronymKey.append(BreadcrumbResource.KEY_PREFIX.value()).append(currentView)
            .append(BreadcrumbResource.KEY_ACRONYM.value());
        return acronymKey.toString();
    }

    /**
     * Breadcrumb key. Build the breadcrumb key based on the current view.
     *
     * @param currentView the current view
     * @return the string
     */
    private String breadcrumbKey(String currentView) {
        StringBuilder breadcrumbKey = new StringBuilder();
        breadcrumbKey.append(BreadcrumbResource.KEY_PREFIX.value()).append(currentView);
        return breadcrumbKey.toString();
    }
}
