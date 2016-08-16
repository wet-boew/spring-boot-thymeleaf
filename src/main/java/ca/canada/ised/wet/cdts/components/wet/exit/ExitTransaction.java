package ca.canada.ised.wet.cdts.components.wet.exit;

import java.io.Serializable;

/**
 * The Class ExitTransaction contains information required by the WET4 template to build the Exit Transaction link.
 *
 * @author Frank Giusto
 */
public class ExitTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The href. */
    private String href;

    /** The title. */
    private String title;

    /**
     * Gets the href.
     *
     * @return the href
     */
    public String getHref() {
        return href;
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
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
