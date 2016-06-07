package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * The Class BreadCrumbSession hold the breadcrumbs for a user session.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BreadCrumbSession implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The bread crumb map contains the breadcrumbs. */
	private Map<String, BreadCrumb> breadCrumbMap;

	/**
	 * Instantiates a new bread crumb session map.
	 */
	public BreadCrumbSession() {
		breadCrumbMap = new LinkedHashMap<>();
	}

	/**
	 * Gets the bread crumb map.
	 *
	 * @return the bread crumb map
	 */
	public Map<String, BreadCrumb> getBreadCrumbMap() {
		return breadCrumbMap;
	}

	/**
	 * Sets the bread crumb map.
	 *
	 * @param breadCrumbMap
	 *            the bread crumb map
	 */
	public void setBreadCrumbMap(Map<String, BreadCrumb> breadCrumbMap) {
		this.breadCrumbMap = breadCrumbMap;
	}

}
