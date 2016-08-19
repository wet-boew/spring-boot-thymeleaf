package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

/**
 * The Class BreadCrumb contains information required for the WET4 breadcrumb.
 *
 * @author Frank Giusto
 */
public class BreadCrumb extends BreadCrumbLink {

    /**
     *
     */
    private static final long serialVersionUID = -5934391697098217768L;

    /** The acronym holds the acronym of the breadcrumb. */
    private String acronym;

    /**
     * Instantiates a new bread crumb.
     */
    public BreadCrumb() {
    }

    /**
     * Instantiates a new bread crumb with a View Name.
     *
     * @param viewName the view name
     */
    public BreadCrumb(String viewName) {
        this.setViewName(viewName);
    }

    /**
     * Sets the acronym.
     *
     * @param acronym the new acronym
     */
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    /**
     * Gets the acronym.
     *
     * @return the acronym
     */
    public String getAcronym() {
        return acronym;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("viewname:").append(getViewName()).append(", ").append("href:").append(getHref()).append(", title:")
            .append(getTitle());
        return sb.toString();
    }
}
