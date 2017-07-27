/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.config.beans;

import ca.canada.ised.wet.cdts.components.wet.utils.Language;

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
	 * @param english
	 *            the english to set
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
	 * @param french
	 *            the french to set
	 */
	public void setFrench(WETUrl french) {
		this.french = french;
	}

	/**
	 * Get language specific url.
	 * 
	 * @param isEnglish
	 *            <code>boolean</code>
	 * @return
	 */
	public String getUrl() {
		if (Language.isEnglish()) {
			return english.getUrl();
		}
		return french.getUrl();
	}

}
