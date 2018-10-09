package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import ca.canada.ised.wet.cdts.components.wet.config.WETResourceBundle;

/**
 * @author fgiusto
 *
 */
public class BreadcrumbServiceTest extends AbstractMockMvcTest {

    /** Logging instance. */
    private static final Logger LOG = LoggerFactory.getLogger(BreadcrumbServiceTest.class);

    @Autowired
    private BreadcrumbService breadcrumbsService;

    /** The breadcrumb message source. */
    private static WETResourceBundle breadcrumbMessageSource = getBreadCrumbBundle();

    /**
     * Gets the bread crumb bundle.
     *
     * @return the bread crumb bundle
     */
    private static WETResourceBundle getBreadCrumbBundle() {

        WETResourceBundle breadcrumbMessageSource = new WETResourceBundle();
        breadcrumbMessageSource.setBasename(BreadcrumbResource.NAME.value());

        return breadcrumbMessageSource;
    }

    /** */
    @Test
    public void breadcrumbEnglish() {

        LocaleContextHolder.setLocale(Locale.CANADA);

        breadcrumbsService.buildBreadCrumbs("greeting", "pageReq1");
        List<Object> bcList = breadcrumbsService.getBreadCrumbList();

        assertEquals(2, bcList.size());

        BreadCrumbLink breadcrumb = (BreadCrumbLink) bcList.get(0);
        assertEquals("Home", breadcrumb.getTitle());
        assertEquals("http://www.canada.ca/en/index.html", breadcrumb.getHref());

    }

    @Test
    public void breadcrumbFrench() {

        LocaleContextHolder.setLocale(Locale.CANADA_FRENCH);

        breadcrumbsService.buildBreadCrumbs("home", "pageReq1");
        List<Object> bcList = breadcrumbsService.getBreadCrumbList();

        BreadCrumbLink breadcrumb = (BreadCrumbLink) bcList.get(0);
        assertEquals("Accueil", breadcrumb.getTitle());
        assertEquals("http://www.canada.ca/fr/index.html", breadcrumb.getHref());

        assertEquals(3, bcList.size());
    }

    @Test
    public void breadcrumb3() {

        LocaleContextHolder.setLocale(Locale.CANADA_FRENCH);

        breadcrumbsService.buildBreadCrumbs("greeting", "pageReq0");
        breadcrumbsService.buildBreadCrumbs("page1", "pageReq1");
        breadcrumbsService.buildBreadCrumbs("page2", "pageReq2");
        List<Object> bcList = breadcrumbsService.getBreadCrumbList();

        assertEquals(3, bcList.size());
    }

    @Test
    public void breadcrumbNoHome() {

        breadcrumbsService.buildBreadCrumbs("greeting", "pageReq1");
        breadcrumbsService.buildBreadCrumbs("page2", "pageReq2");
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

    @Test
    public void validateBreadCrumbProperties() {
        // Initialize-Validate Bread Crumbs
        String rootKey = breadcrumbMessageSource.getMessage(BreadcrumbResource.ROOT_KEY.value(), null, Locale.CANADA);

        String rootKey2 = breadcrumbMessageSource.getMessage(BreadcrumbResource.ROOT_KEY.value(), null,
            Locale.CANADA_FRENCH);

        assertNotNull(rootKey);
        assertEquals(rootKey, rootKey2);

        assertNotNull(breadcrumbMessageSource.getMessage(rootKey, null, Locale.CANADA));
        assertNotNull(breadcrumbMessageSource.getMessage(rootKey2, null, Locale.CANADA_FRENCH));

    }
}
