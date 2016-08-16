package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;

/**
 * @author fgiusto
 *
 */
public class BreadcrumbServiceTest extends AbstractMockMvcTest {

    @Autowired
    private BreadcrumbService breadcrumbsService;

    /** */
    @Test
    public void breadcrumbEnglish() {

        LocaleContextHolder.setLocale(Locale.CANADA);

        breadcrumbsService.buildBreadCrumbs("home", "pageReq1=");
        List<Object> bcList = breadcrumbsService.getBreadCrumbList();

        Assert.isTrue(bcList.size() == 1);

        BreadCrumbLink breadcrumb = (BreadCrumbLink) bcList.get(0);
        Assert.isTrue("Home".equals(breadcrumb.getTitle()));
        Assert.isTrue("http://www.canada.ca/en/index.html".equals(breadcrumb.getHref()));

    }

    @Test
    public void breadcrumbFrench() {

        LocaleContextHolder.setLocale(Locale.CANADA_FRENCH);

        breadcrumbsService.buildBreadCrumbs("home", "pageReq1=");
        List<Object> bcList = breadcrumbsService.getBreadCrumbList();

        BreadCrumbLink breadcrumb = (BreadCrumbLink) bcList.get(0);
        Assert.isTrue("Accueil".equals(breadcrumb.getTitle()));
        Assert.isTrue("http://www.canada.ca/fr/index.html".equals(breadcrumb.getHref()));

        Assert.isTrue(bcList.size() == 1);
    }

}
