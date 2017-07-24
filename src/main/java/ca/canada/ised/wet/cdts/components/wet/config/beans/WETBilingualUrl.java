/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.config.beans;

/**
 * Bilingual URL used for mapping cdn.properties.
 *
 * @author Andrew Pitt
 * @since 4.0.25
 */
public class WETBilingualUrl {

    /** English url. */
    private WETUrl english = new WETUrl();

    /** French url. */
    private WETUrl french = new WETUrl();

    /**
     * @return the english
     */
    public WETUrl getEnglish() {
        return english;
    }

    /**
     * @param english the english to set
     */
    public void setEnglish(WETUrl english) {
        this.english = english;
    }

    /**
     * @return the french
     */
    public WETUrl getFrench() {
        return french;
    }

    /**
     * @param french the french to set
     */
    public void setFrench(WETUrl french) {
        this.french = french;
    }

}
