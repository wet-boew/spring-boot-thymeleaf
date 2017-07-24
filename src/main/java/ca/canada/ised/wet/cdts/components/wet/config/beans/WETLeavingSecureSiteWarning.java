/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.config.beans;

/**
 * Used to map "leavingsecuresitewarning" values in cdn.properties.
 *
 * @author Andrew Pitt
 * @since 4.0.25
 */
public class WETLeavingSecureSiteWarning {

    private Boolean enabled;

    private String redirecturl;

    private String excludeddomains;

    private Boolean displaymodalwindow;

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the redirecturl
     */
    public String getRedirecturl() {
        return redirecturl;
    }

    /**
     * @param redirecturl the redirecturl to set
     */
    public void setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
    }

    /**
     * @return the excludeddomains
     */
    public String getExcludeddomains() {
        return excludeddomains;
    }

    /**
     * @param excludeddomains the excludeddomains to set
     */
    public void setExcludeddomains(String excludeddomains) {
        this.excludeddomains = excludeddomains;
    }

    /**
     * @return the displaymodalwindow
     */
    public Boolean getDisplaymodalwindow() {
        return displaymodalwindow;
    }

    /**
     * @param displaymodalwindow the displaymodalwindow to set
     */
    public void setDisplaymodalwindow(Boolean displaymodalwindow) {
        this.displaymodalwindow = displaymodalwindow;
    }

}
