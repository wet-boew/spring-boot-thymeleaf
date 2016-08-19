package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.util.List;

/**
 * The Interface BreadcrumbService gives access to building and retrieving breadcrumbs.
 *
 * @author Frank Giusto
 */
public interface BreadcrumbService {

    /**
     * Gets the bread crumb list.
     *
     * @param currentView the current view (requested view)
     * @return the bread crumb list
     */
    List<Object> getBreadCrumbList();

    /**
     * Builds the bread crumbs.
     *
     * @param currentView the current view
     * @param requestURL the request url
     */
    void buildBreadCrumbs(String currentView, String requestURL);
}
