package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class BreadCrumbList contains a list of breadcrumbs add to existing
 * breadcrumb list display.
 *
 * @author Vernon Thompson
 */
public class BreadCrumbs {

	List<BreadCrumb> list = new ArrayList<>();

	public List<BreadCrumb> getList() {
		return list;
	}

	public void setList(List<BreadCrumb> list) {
		this.list = list;
	}

	public void addBreadCrumb(BreadCrumb breadCrumb) {
		list.add(breadCrumb);
	};

	/**
	 * Instantiates a new bread crumb.
	 */
	public BreadCrumbs() {
	}

}
