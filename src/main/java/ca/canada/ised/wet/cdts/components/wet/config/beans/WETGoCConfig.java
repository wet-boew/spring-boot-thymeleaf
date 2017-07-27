/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.config.beans;

/**
 * Used to map "goc" entries in cdn.properties.
 *
 * @author Andrew Pitt
 * @since 4.0.25
 */
public class WETGoCConfig {

    /** WETWebTemplate. */
    private WETWebTemplate webtemplate = new WETWebTemplate();

    /**
     * @return the webtemplate
     */
    public WETWebTemplate getWebtemplate() {
        return webtemplate;
    }

    /**
     * @param webtemplate the webtemplate to set
     */
    public void setWebtemplate(WETWebTemplate webtemplate) {
        this.webtemplate = webtemplate;
    }

}
