package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

/**
 * The Class BreadCrumb contains information required for the WET4 breadcrumb.
 *
 * @author Frank Giusto
 */
public class BreadCrumbLink extends BreadCrumbTitle {

    /**
     *
     */
    private static final long serialVersionUID = 1560150270997596985L;

    /** The href attribute contains the url for the breadcrumb. */
    private String href;

    /** The view name hold the name of the view that uses this breadcrumb. */
    private String viewName;

    /**
     * Instantiates a new bread crumb.
     */
    public BreadCrumbLink() {
    }

    /**
     * Sets the href.
     *
     * @param href the new href
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * Gets the href.
     *
     * @return the href
     */
    public String getHref() {
        return this.href;
    }

    /**
     * Gets the view name.
     *
     * @return the view name
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the view name.
     *
     * @param viewName the new view name
     */
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("viewname:").append(this.viewName).append(", ").append("href:").append(this.href).append(", title:")
            .append(getTitle());
        return sb.toString();
    }
}
