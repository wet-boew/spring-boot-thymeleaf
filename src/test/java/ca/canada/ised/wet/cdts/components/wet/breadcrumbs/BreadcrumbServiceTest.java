package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.canada.ised.wet.cdts.components.wet.interceptor.WETTemplateInterceptor;

/**
 * @author fgiusto
 *
 */
public class BreadcrumbServiceTest {

    /** The breadcrumbs service. */
    @Autowired
    private WETTemplateInterceptor cdnTemplateInterceptor;

    @Autowired
    private BreadcrumbService breadcrumbsService;

    /** */
    @Test
    public void dummyTestPleaseReplaceMe() {

        // breadcrumbsService.buildBreadCrumbs("page1", "pageReq=");

        List<Object> bc = new ArrayList<>();
        BreadCrumb bcr = new BreadCrumb();
        bcr.setHref("http://google.ca");
        bcr.setTitleEN("fgg");
        bc.add(bcr);
        BreadCrumbTitle bcs = new BreadCrumbTitle();
        bcs.setTitleEN("fgg2");

        bc.add(bcs);

        System.out.println(bc.toString());

        for (Object o : bc) {
            System.out.println(o.toString());
        }

        // List<BreadCrumb> bcList = breadcrumbsService.getBreadCrumbList();

        // Assert.isTrue(!CollectionUtils.isEmpty(bcList));
    }

}
