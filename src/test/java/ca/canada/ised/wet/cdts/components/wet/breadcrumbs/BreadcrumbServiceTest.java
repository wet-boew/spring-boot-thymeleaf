package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;

/**
 * @author fgiusto
 *
 */
public class BreadcrumbServiceTest extends AbstractMockMvcTest {

    /** Logging instance. */
    private static final Logger LOG = LoggerFactory.getLogger(BreadcrumbServiceTest.class);

    @Autowired
    private BreadcrumbService breadcrumbsService;

    /** */
    @Test
    public void breadcrumbEnglish() {

        LocaleContextHolder.setLocale(Locale.CANADA);

        breadcrumbsService.buildBreadCrumbs("greeting", "pageReq1");
        List<Object> bcList = breadcrumbsService.getBreadCrumbList();

        Assert.isTrue(bcList.size() == 2);

        BreadCrumbLink breadcrumb = (BreadCrumbLink) bcList.get(0);
        Assert.isTrue("Home".equals(breadcrumb.getTitle()));
        Assert.isTrue("http://www.canada.ca/en/index.html".equals(breadcrumb.getHref()));

    }

    @Test
    public void breadcrumbFrench() {

        LocaleContextHolder.setLocale(Locale.CANADA_FRENCH);

        breadcrumbsService.buildBreadCrumbs("home", "pageReq1");
        List<Object> bcList = breadcrumbsService.getBreadCrumbList();

        BreadCrumbLink breadcrumb = (BreadCrumbLink) bcList.get(0);
        Assert.isTrue("Accueil".equals(breadcrumb.getTitle()));
        Assert.isTrue("http://www.canada.ca/fr/index.html".equals(breadcrumb.getHref()));

        Assert.isTrue(bcList.size() == 2);
    }

    @Test
    public void breadcrumb3() {

        LocaleContextHolder.setLocale(Locale.CANADA_FRENCH);

        breadcrumbsService.buildBreadCrumbs("greeting", "pageReq0");
        breadcrumbsService.buildBreadCrumbs("page1", "pageReq1");
        breadcrumbsService.buildBreadCrumbs("page2", "pageReq2");
        List<Object> bcList = breadcrumbsService.getBreadCrumbList();

        Assert.isTrue(bcList.size() == 3);
    }

    @Test
    public void breadcrumbNoHome() {

        breadcrumbsService.buildBreadCrumbs("greeting", "pageReq1");
        // breadcrumbsService.buildBreadCrumbs("page2", "pageReq2");
        List<Object> bcList = breadcrumbsService.getBreadCrumbList();

        LOG.info(" ************** Breadcrumbs ***************");
        for (Object bc : bcList) {
            if (bc instanceof BreadCrumbLink) {
                BreadCrumbLink breadcrumb = (BreadCrumbLink) bc;
                LOG.info(breadcrumb.toString());
            } else if (bc instanceof BreadCrumbTitle) {
                BreadCrumbTitle breadcrumb = (BreadCrumbTitle) bc;
                LOG.info(breadcrumb.toString());
            } else if (bc instanceof BreadCrumb) {
                BreadCrumb breadcrumb = (BreadCrumb) bc;
                LOG.info(breadcrumb.toString());
            }
        }
        LOG.info(" *****************************");
        // Assert.isTrue(bcList.size() == 2);
    }
    // TODO test invalid breadcrumbs.properties....
    // TODO test when there are no breadcrumbs.properties which is valid.

}
