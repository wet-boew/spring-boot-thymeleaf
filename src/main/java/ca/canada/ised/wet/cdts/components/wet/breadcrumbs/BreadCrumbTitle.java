package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.io.Serializable;

/**
 * The Class BreadCrumb contains information required for the WET4 breadcrumb.
 *
 * @author Frank Giusto
 */
public class BreadCrumbTitle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The title is the WET4 attribute holding the displayed name of the breadcrumb.
     */
    private String title;

    /** The title en holds the internal english name of the breadcrumb. */
    private String titleEN;

    /** The title fr holds the internal french name of the breadcrumb. */
    private String titleFR;

    /**
     * Instantiates a new bread crumb.
     */
    public BreadCrumbTitle() {
    }

    /**
     * Gets the title en.
     *
     * @return the title en
     */
    public String getTitleEN() {
        return titleEN;
    }

    /**
     * Sets the internal title en.
     *
     * @param titleEN the new title en
     */
    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    /**
     * Gets the title fr.
     *
     * @return the title fr
     */
    public String getTitleFR() {
        return titleFR;
    }

    /**
     * Sets the internal title fr.
     *
     * @param titleFR the new title fr
     */
    public void setTitleFR(String titleFR) {
        this.titleFR = titleFR;
    }

    /**
     * Gets the displayed title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the displayed title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("title:").append(getTitle());
        return sb.toString();
    }
}
