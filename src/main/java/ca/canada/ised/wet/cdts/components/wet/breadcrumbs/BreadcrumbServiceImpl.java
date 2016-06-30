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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import ca.canada.ised.wet.cdts.components.wet.config.WETModelKey;
import ca.canada.ised.wet.cdts.components.wet.config.WETResourceBundle;

/**
 * The Class BreadcrumbServiceImpl is responsible for building and retrieving application breadcrumbs.
 */
@Component
public class BreadcrumbServiceImpl implements BreadcrumbService, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** Logging instance. */
    private static final Logger LOG = LoggerFactory.getLogger(BreadcrumbServiceImpl.class);

    /**
     * The Constant JUST_HOME_AND_CURRENT_BREADCRUMB which breadcrumbs are to be returned from the breadcrumb map.
     */
    private static final int JUST_HOME_AND_CURRENT_BREADCRUMB = 2;

    /** The bread session. */
    @Autowired
    private BreadCrumbSession breadcrumbSession;

    /** Message source. */
    @Autowired
    private MessageSource applicationMessageSource;

    /** The breadcrumb message source. */
    private static WETResourceBundle breadcrumbMessageSource = getBreadCrumbBundle();

    @Override
    public List<BreadCrumb> getBreadCrumbList() {

        if (breadcrumbSession.getBreadCrumbMap().size() == 0) {
            LOG.error("No Breadcrumbs exist");
            return new ArrayList<>();
        }
        // return all but the last breadcrumb which is the current page.
        List<BreadCrumb> displayedBreadCrumbList = new ArrayList<>();

        Iterator<String> breadcrumbKeyIterator = breadcrumbSession.getBreadCrumbMap().keySet().iterator();

        int breadCrumbCount = 0;
        int secondLastBreadcrumb = getBreadcrumbMap().size() - 1;

        while (breadcrumbKeyIterator.hasNext()) {

            String breadcrumbKey = breadcrumbKeyIterator.next();
            displayedBreadCrumbList
                .add(setBreadCrumbLanguageTitle(breadcrumbKey, getBreadcrumbMap().get(breadcrumbKey)));
            breadCrumbCount++;
            if (getBreadcrumbMap().size() <= JUST_HOME_AND_CURRENT_BREADCRUMB) {
                break;
            }
            if (breadCrumbCount == secondLastBreadcrumb) {
                break;
            }
        }
        return displayedBreadCrumbList;
    }

    @Override
    public void buildBreadCrumbs(String currentView, String requestURL) {

        Set<String> keys = breadcrumbMessageSource.getKeys(breadcrumbMessageSource.getBasename(),
            LocaleContextHolder.getLocale());
        if (CollectionUtils.isEmpty(keys)) {
            LOG.warn("No breadcrumbs.properties found in folder defined by spring.thymeleaf.prefix");
            // Home still required.
            createBreadCrumbList();
            return;
        }
        String rootKey = breadcrumbMessageSource.getMessage("bc.root.key", null, LocaleContextHolder.getLocale());

        if (CollectionUtils.isEmpty(getBreadcrumbMap()) || rootKey.equals(breadcrumbKey(currentView))) {
            createBreadCrumbList();
        }
        if (keys.contains(breadcrumbKey(currentView))) {
            if (getBreadcrumbMap().containsKey(currentView)) {
                // going back to page previously visited
                setPreviouslyVisitedBreadCrumb(currentView);
            } else {
                // create new breadcrumb for requested view
                BreadCrumb newBreadCrumb = createBreadCrumb(currentView, requestURL,
                    getBreadcrumbAcronym(keys, currentView), breadcrumbMessageSource);

                this.addBreadCrumb(newBreadCrumb, currentView);
            }
        }
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

        breadcrumbSession = new BreadCrumbSession();
        BreadCrumb breadCrumb = new BreadCrumb();

        breadCrumb.setTitleEN(applicationMessageSource.getMessage("home", null, Locale.CANADA));
        breadCrumb.setTitleFR(applicationMessageSource.getMessage("home", null, Locale.CANADA_FRENCH));
        breadCrumb.setAcronym("CDN");
        breadCrumb.setViewName(WETModelKey.CANADA_HOME.name());

        getBreadcrumbMap().put(WETModelKey.CANADA_HOME.name(), breadCrumb);
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
        homeLink.append("http://www.canada.ca/").append(language).append("/index.html");

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
            }
        } else {
            localeBreadCrumb.setTitle(breadCrumb.getTitleEN());
            if (breadcrumbKey.equals(WETModelKey.CANADA_HOME.name())) {
                localeBreadCrumb.setHref(setCanadaHomeUrl(Locale.CANADA.getLanguage()));
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

        breadcrumbSession = new BreadCrumbSession();
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
