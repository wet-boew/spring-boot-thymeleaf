package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

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

    @Autowired
    @Qualifier("messageSource")
    private MessageSource applicationMessageSource;

    @Autowired
    private ServletContext servletContext;

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
        assertEquals(3, bcList.size());
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

    /**
     * Used to find issue https://github.com/wet-boew/spring-boot-thymeleaf/issues/24
     *
     * Was tested with up to 100000 threads, but I've dropped it down to make the test suite faster
     *
     * @throws InterruptedException
     */
    @Test
    public void getBreadCrumbListWithConcurrency() throws InterruptedException {

        // don't use the spring beans since we are messing around with threads
        final BreadcrumbServiceImpl breadCrumb = new BreadcrumbServiceImpl();
        ReflectionTestUtils.setField(breadCrumb, "showLandingPageBreadcrumb", Boolean.TRUE);
        ReflectionTestUtils.setField(breadCrumb, "breadcrumbSession", new ThreadLocalBreadCrumbSession());
        ReflectionTestUtils.setField(breadCrumb, "applicationMessageSource", applicationMessageSource);
        ReflectionTestUtils.setField(breadCrumb, "servletContext", servletContext);

        LocaleContextHolder.setLocale(Locale.CANADA);
        int numberOfThreads = 1000;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads / 10);
        final CountDownLatch latch = new CountDownLatch(numberOfThreads);
        final Random rand = new Random();
        final List<Object> exceptions = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(10 + rand.nextInt(50));

                        int loopCount = 2 + rand.nextInt(10);
                        breadCrumb.buildBreadCrumbs("greeting", "pageReq0");
                        for (int j = 0; j < loopCount; j++) {
                            breadCrumb.buildBreadCrumbs("page" + j, "pageReq" + j);
                        }

                        List<Object> bcList = breadCrumb.getBreadCrumbList();
                        assertFalse(bcList.isEmpty());

                    } catch (Exception | AssertionError e) {
                        // have to add it back in this way because the exceptions won't fail the junit test since they
                        // are in a different thread
                        e.printStackTrace();
                        exceptions.add(e);
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        latch.await();

        assertTrue("should not be any exceptions from the threads but was " + exceptions, exceptions.isEmpty());
    }
}
