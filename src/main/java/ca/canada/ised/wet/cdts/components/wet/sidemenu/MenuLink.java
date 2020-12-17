/*
 *
 */
package ca.canada.ised.wet.cdts.components.wet.sidemenu;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import ca.canada.ised.wet.cdts.components.wet.utils.Language;

/**
 * The Class MenuLink contains information required by the WET4 side menu.
 *
 * @author Frank Giusto
 */
public class MenuLink implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The displayed menu text. */
    private String text;

    /** The internal menu text en. */
    private String textEn;

    /** The internal menu text fr. */
    private String textFr;

    /** The url for the menu. */
    private String href;

    /** Does the link open in a new window? */
    private boolean newWindow;

    /**
     * Gets the menu text.
     *
     * @return the text
     */
    public String getText() {
        if (Language.isEnglish()) {
            return textEn;
        } else {
            return textFr;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Sets the menu text.
     *
     * @param text the new text
     */
    public void setText(String text) {
        this.text = text;
    }

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
     * Gets the text en.
     *
     * @return the text en
     */
    public String getTextEn() {
        return textEn;
    }

    /**
     * Sets the text en.
     *
     * @param textEn the new text en
     */
    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    /**
     * Gets the text fr.
     *
     * @return the text fr
     */
    public String getTextFr() {
        return textFr;
    }

    /**
     * Sets the text fr.
     *
     * @param textFr the new text fr
     */
    public void setTextFr(String textFr) {
        this.textFr = textFr;
    }

    /**
     * @return the newWindow
     */
    public boolean isNewWindow() {
        return newWindow;
    }

    /**
     * @param newWindow the newWindow to set
     */
    public void setNewWindow(boolean newWindow) {
        this.newWindow = newWindow;
    }

}
