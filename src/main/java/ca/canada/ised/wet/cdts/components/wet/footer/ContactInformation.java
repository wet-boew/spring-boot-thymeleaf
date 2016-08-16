package ca.canada.ised.wet.cdts.components.wet.footer;

import java.io.Serializable;

/**
 * The Class ContactInformation contains information required by the WET4 apis to override Contact Information.
 *
 * @author Frank Giusto
 */
public class ContactInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The href attribute corresponds to the WET4 naming requirement. Url to Contact Information.
     */
    private String href;

    /**
     * The text attribute corresponds to the WET4 naming requirement for Contact Information Label.
     */
    private String text;

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
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text the new text
     */
    public void setText(String text) {
        this.text = text;
    }

}
