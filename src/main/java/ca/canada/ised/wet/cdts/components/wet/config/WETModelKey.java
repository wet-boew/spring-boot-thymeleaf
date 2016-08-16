package ca.canada.ised.wet.cdts.components.wet.config;

/**
 * The Enum WETModelKey contains attribute names stored and the spring model and referenced on the WET templates.
 *
 * @author Frank Giusto
 */
public enum WETModelKey {

    /** The cdn configuration file key. */
    // @formatter:off
	CDN("cdn"),

	/** The privacy link. */
	PRIVACY_LINK("privacyLink"),

	/** The terms link. */
	TERMS_LINK("termsLink"),

	/** The contact link. */
	CONTACT_LINK("contactLink"),

	/** The exit transaction. */
	EXIT_TRANSACTION("exitTransactionLink"),

	/** The breadcrumbs. */
	BREADCRUMBS("breadcrumbs"),

	/** The sections. */
	SECTIONS("sections"),

	/** The language url. */
	LANGUAGE_URL("langUrl"),

	/** The session timeout. */
	SESSION_TIMEOUT("wetSessionTimeout"),

	/** The leaving secure site. */
	LEAVING_SECURE_SITE("leavingSecureSite"),

	/** The canada home key is for www.canada.ca . */
	CANADA_HOME("canadaHome"),

	/** The department home key. */
	DEPARTMENT_HOME("departmentHome"),

	/** The page section menu key hold the section menu for a specific page. */
	PAGE_SECTION_MENU("pageSectionMenu");


	/** The wet attribute name. */
	// @formatter:on
    private String wetAttributeName;

    /**
     * Instantiates a new WET model key.
     *
     * @param wetAttributeName the wet attribute name
     */
    WETModelKey(String wetAttributeName) {
        this.wetAttributeName = wetAttributeName;
    }

    /**
     * Wet attribute name.
     *
     * @return the string
     */
    public String wetAttributeName() {
        return wetAttributeName;
    }

}
