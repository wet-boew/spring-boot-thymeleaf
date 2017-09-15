/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.config.beans;

/**
 * WET version info.
 *
 * @author Andrew Pitt
 * @since 4.0.25.4
 */
public class WETVersion {

    /** Thymeleaf templates version. */
    private String template;

    /** JavaScript version. */
    private String javascript;

    /** Private constructor. */
    public WETVersion() {
        super();
    }

    /**
     * @return the template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @return the javascript
     */
    public String getJavascript() {
        return javascript;
    }

    /**
     * @param javascript the javascript to set
     */
    public void setJavascript(String javascript) {
        this.javascript = javascript;
    }

}
