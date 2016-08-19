package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

/**
 * The Enum BreadcrumbResource is used to get property values from the bread crumbs resource file.
 *
 * @author Frank Giusto
 */
public enum BreadcrumbResource {

    /** The breadcrumb message key prefix. */
    KEY_PREFIX("bc."),

    /** The breadcrumb key suffix for acronymns. */
    KEY_ACRONYM(".acronym"),

    /** The name of the resource bundle. */
    NAME("breadcrumbs"),

    /** The placeholder. */
    PLACEHOLDER("bc.placeholder.breadcrumb"),

    /** The root key. */
    ROOT_KEY("bc.root.key");

    /** The value. */
    private String value;

    /**
     * Instantiates a new breadcrumb resource.
     *
     * @param value the value
     */
    BreadcrumbResource(String value) {
        this.value = value;
    }

    /**
     * Value.
     *
     * @return the string
     */
    public String value() {
        return value;
    }
}
