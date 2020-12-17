package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Work around to fake out Session scoped spring bean for a threading test.
 *
 * @author SellersJ
 */
public class ThreadLocalBreadCrumbSession extends BreadCrumbSession {

    /** */
    private static final long serialVersionUID = 1L;

    private ThreadLocal<Map<String, BreadCrumb>> crumb = new ThreadLocal<Map<String, BreadCrumb>>() {

        @Override
        protected Map<String, BreadCrumb> initialValue() {
            return Collections.synchronizedMap(new LinkedHashMap<String, BreadCrumb>());
        }
    };

    @Override
    public Map<String, BreadCrumb> getBreadCrumbMap() {
        return crumb.get();
    }

}
