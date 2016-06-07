package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.io.Serializable;

/**
 * The Class BreadCrumb contains information required for the WET4 breadcrumb.
 */
public class BreadCrumb implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The href attribute contains the url for the breadcrumb. */
	private String href;

	/**
	 * The title is the WET4 attribute holding the displayed name of the
	 * breadcrumb.
	 */
	private String title;

	/** The title en holds the internal english name of the breadcrumb. */
	private String titleEN;

	/** The title fr holds the internal french name of the breadcrumb. */
	private String titleFR;

	/** The acronym holds the acronym of the breadcrumb. TODO */
	private String acronym;

	/** The view name hold the name of the view that uses this breadcrumb. */
	private String viewName;

	/**
	 * Instantiates a new bread crumb.
	 */
	public BreadCrumb() {
	}

	/**
	 * Sets the href.
	 *
	 * @param href
	 *            the new href
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * Gets the href.
	 *
	 * @return the href
	 */
	public String getHref() {
		return this.href;
	}

	/**
	 * Sets the acronym.
	 *
	 * @param acronym
	 *            the new acronym
	 */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	/**
	 * Gets the acronym.
	 *
	 * @return the acronym
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * Gets the view name.
	 *
	 * @return the view name
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * Sets the view name.
	 *
	 * @param viewName
	 *            the new view name
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	/**
	 * Gets the title en.
	 *
	 * @return the title en
	 */
	public String getTitleEN() {
		return titleEN;
	}

	/**
	 * Sets the internal title en.
	 *
	 * @param titleEN
	 *            the new title en
	 */
	public void setTitleEN(String titleEN) {
		this.titleEN = titleEN;
	}

	/**
	 * Gets the title fr.
	 *
	 * @return the title fr
	 */
	public String getTitleFR() {
		return titleFR;
	}

	/**
	 * Sets the internal title fr.
	 *
	 * @param titleFR
	 *            the new title fr
	 */
	public void setTitleFR(String titleFR) {
		this.titleFR = titleFR;
	}

	/**
	 * Gets the displayed title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the displayed title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
