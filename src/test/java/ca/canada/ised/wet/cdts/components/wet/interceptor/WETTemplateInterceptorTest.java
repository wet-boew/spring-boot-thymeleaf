package ca.canada.ised.wet.cdts.components.wet.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;

import ca.canada.ised.wet.cdts.components.wet.breadcrumbs.AbstractMockMvcTest;
import ca.canada.ised.wet.cdts.components.wet.config.WETModelKey;
import ca.canada.ised.wet.cdts.components.wet.config.WETSettings;

@DirtiesContext
public class WETTemplateInterceptorTest extends AbstractMockMvcTest {

    @Autowired
    private WETTemplateInterceptor interceptor;

    @Autowired
    private WETSettings wetSettings;

    private MockHttpServletRequest request = new MockHttpServletRequest();

    private MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    public void preHandle() throws Exception {
        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    public void postHandleNoModelAndView() throws Exception {
        // smoke test
        interceptor.postHandle(request, response, null, null);
    }

    @Test
    public void postHandleModelAndViewFilledOut() throws Exception {
        ModelAndView mav = new ModelAndView("unit-test");
        interceptor.postHandle(request, response, null, mav);
        // TODO check that the model objects where put in here correctly
    }

    @Test
    public void postHandleModelAndViewAndBreadcrumbsSettingsFilledOut() throws Exception {
        wetSettings.getSession().getTimeout().setEnabled(true);
        wetSettings.getSession().setRefreshonclick(Boolean.TRUE);
        wetSettings.getSession().setMethod("GET");
        wetSettings.getSession().setAdditionaldata("unit-test");

        ModelAndView mav = new ModelAndView("unit-test");
        interceptor.postHandle(request, response, null, mav);
        // TODO check that the model objects where put in here correctly

        assertNotNull(mav.getModel().get(WETModelKey.SESSION_TIMEOUT.wetAttributeName()),
            "session timeout shouldn't be null");
    }

    @Test
    public void postHandleModelAndViewAndBreadcrumbsSettingsThingsNotFilledOut() throws Exception {
        wetSettings.getSession().getTimeout().setEnabled(true);
        wetSettings.getSession().setRefreshonclick(Boolean.FALSE);
        wetSettings.getSession().setMethod(null);
        wetSettings.getSession().setAdditionaldata(null);

        wetSettings.getSession().setRefreshcallbackurl(null);
        wetSettings.getSession().setRefreshonclick(null);
        wetSettings.getSession().getRefreshlimit().setValue(null);

        // footer stuff
        wetSettings.getContact().getEnglish().setUrl("https://example.com/content-eng");
        wetSettings.getPrivacy().getEnglish().setUrl("https://example.com/privacy-eng");
        wetSettings.getTerms().getEnglish().setUrl("https://example.com/terms-eng");

        ModelAndView mav = new ModelAndView("unit-test");
        interceptor.postHandle(request, response, null, mav);
        // TODO check that the model objects where put in here correctly

        assertNotNull(mav.getModel().get(WETModelKey.SESSION_TIMEOUT.wetAttributeName()),
            "session timeout shouldn't be null");

        // footer
        assertNotNull(mav.getModel().get(WETModelKey.CONTACT_LINK.wetAttributeName()), "contact url shouldn't be null");
        assertNotNull(mav.getModel().get(WETModelKey.PRIVACY_LINK.wetAttributeName()), "privacy shouldn't be null");
        assertNotNull(mav.getModel().get(WETModelKey.TERMS_LINK.wetAttributeName()), "terms shouldn't be null");
    }

    @Test
    public void postHandle_NoModelAndView() throws Exception {
        ModelAndView mav = null;
        interceptor.postHandle(request, response, null, mav);
    }

    @Test
    public void postHandle_ModelAndViewHasNoName() throws Exception {
        ModelAndView mav = new ModelAndView();
        interceptor.postHandle(request, response, null, mav);
    }

    @Test
    public void postHandle_NoBreadcrumbs() throws Exception {
        String fieldName = "includeBreadCrumbs";
        ReflectionTestUtils.setField(interceptor, fieldName, Boolean.FALSE);

        ModelAndView mav = new ModelAndView("unit-test");
        interceptor.postHandle(request, response, null, mav);

        // restore it back to true before the asserts
        ReflectionTestUtils.setField(interceptor, fieldName, Boolean.TRUE);

        // check things
        assertNull(mav.getModel().get(WETModelKey.BREADCRUMBS.wetAttributeName()), "breadcrumbs should be null");
    }

    @Test
    public void postHandle_NoExitTransaction() throws Exception {
        String fieldName = "showExitTransaction";
        ReflectionTestUtils.setField(interceptor, fieldName, Boolean.FALSE);

        ModelAndView mav = new ModelAndView("unit-test");
        interceptor.postHandle(request, response, null, mav);

        // restore it back to true before the asserts
        ReflectionTestUtils.setField(interceptor, fieldName, Boolean.TRUE);

        // check things
        assertEquals("", mav.getModel().get(WETModelKey.EXIT_TRANSACTION.wetAttributeName()),
            "exit transition should be empty");
    }

}
