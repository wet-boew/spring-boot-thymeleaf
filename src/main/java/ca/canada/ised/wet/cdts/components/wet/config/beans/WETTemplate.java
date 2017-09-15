/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.config.beans;

/**
 * WET properties.
 *
 * @author Andrew Pitt
 * @since 4.0.25
 */
public class WETTemplate {

    /** Wet version. */
    private WETVersion version;

    private String theme;

    private String subtheme;

    private Boolean loadjqueryfromgoogle;

    private Boolean usehttps;

    /**
     * @return the version
     */
    public WETVersion getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(WETVersion version) {
        this.version = version;
    }

    /**
     * @return the theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * @return the subtheme
     */
    public String getSubtheme() {
        return subtheme;
    }

    /**
     * @param subtheme the subtheme to set
     */
    public void setSubtheme(String subtheme) {
        this.subtheme = subtheme;
    }

    /**
     * @return the loadjqueryfromgoogle
     */
    public Boolean getLoadjqueryfromgoogle() {
        return loadjqueryfromgoogle;
    }

    /**
     * @param loadjqueryfromgoogle the loadjqueryfromgoogle to set
     */
    public void setLoadjqueryfromgoogle(Boolean loadjqueryfromgoogle) {
        this.loadjqueryfromgoogle = loadjqueryfromgoogle;
    }

    /**
     * @return the usehttps
     */
    public Boolean getUsehttps() {
        return usehttps;
    }

    /**
     * @param usehttps the usehttps to set
     */
    public void setUsehttps(Boolean usehttps) {
        this.usehttps = usehttps;
    }

}
