package ca.canada.ised.wet.cdts.components.wet.interceptor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import ca.canada.ised.wet.cdts.components.wet.breadcrumbs.AbstractMockMvcTest;

public class WETTemplateInterceptorTest extends AbstractMockMvcTest {

    @Autowired
    private WETTemplateInterceptor interceptor;

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

}
