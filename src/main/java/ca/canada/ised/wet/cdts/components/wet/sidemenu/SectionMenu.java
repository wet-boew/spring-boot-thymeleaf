package ca.canada.ised.wet.cdts.components.wet.sidemenu;

import java.io.Serializable;
import java.util.List;

import ca.canada.ised.wet.cdts.components.wet.utils.Language;

/**
 * The Class SectionMenu contains information required to build the WET4 side
 * menu.
 * 
 * @author Frank Giusto
 */
public class SectionMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The displayed section name. */
	private String sectionName;

	/** The internal section name en. */
	private String sectionNameEn;

	/** The section name fr. */
	private String sectionNameFr;

	/** The menu links. */
	private List<MenuLink> menuLinks;

	/**
	 * Gets the section name.
	 *
	 * @return the section name
	 */
	public String getSectionName() {
		if (Language.isEnglish()) {
			return sectionNameEn;
		} else {
			return sectionNameFr;
		}
	}

	/**
	 * Sets the section name.
	 *
	 * @param sectionName
	 *            the new section name
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	/**
	 * Gets the menu links.
	 *
	 * @return the menu links
	 */
	public List<MenuLink> getMenuLinks() {
		return menuLinks;
	}

	/**
	 * Sets the menu links.
	 *
	 * @param menuLinks
	 *            the new menu links
	 */
	public void setMenuLinks(List<MenuLink> menuLinks) {
		this.menuLinks = menuLinks;
	}

	public String sectionNameForLocale() {
		if (Language.isEnglish()) {
			return sectionNameEn;
		} else {
			return sectionNameFr;
		}
	}

	/**
	 * Gets the section name en.
	 *
	 * @return the section name en
	 */
	public String getSectionNameEn() {
		return sectionNameEn;
	}

	/**
	 * Sets the section name en.
	 *
	 * @param sectionNameEn
	 *            the new section name en
	 */
	public void setSectionNameEn(String sectionNameEn) {
		this.sectionNameEn = sectionNameEn;
	}

	/**
	 * Gets the section name fr.
	 *
	 * @return the section name fr
	 */
	public String getSectionNameFr() {
		return sectionNameFr;
	}

	/**
	 * Sets the section name fr.
	 *
	 * @param sectionNameFr
	 *            the new section name fr
	 */
	public void setSectionNameFr(String sectionNameFr) {
		this.sectionNameFr = sectionNameFr;
	}
}
