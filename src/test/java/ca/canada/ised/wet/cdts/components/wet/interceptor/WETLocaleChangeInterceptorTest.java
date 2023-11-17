package ca.canada.ised.wet.cdts.components.wet.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class WETLocaleChangeInterceptorTest {

    private WETLocaleChangeInterceptor interceptor = new WETLocaleChangeInterceptor();

    private MockHttpServletRequest request = new MockHttpServletRequest();

    private MockHttpServletResponse response = new MockHttpServletResponse();

    @BeforeEach
    void setupLocale() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, localeResolver);
    }

    @Test
    public void preHandleNoLocalePram() throws Exception {
        boolean result = interceptor.preHandle(request, response, null);
        assertTrue(result, "result if we should proceed");
        assertEquals(200, response.getStatus());
    }

    @Test
    public void preHandleWithLocalePramFra() throws Exception {
        request.setParameter(interceptor.getParamName(), "fra");
        boolean result = interceptor.preHandle(request, response, null);
        assertTrue(result, "result if we should proceed");
        assertEquals(200, response.getStatus());

        Locale actualLocale = RequestContextUtils.getLocale(request);
        assertEquals(Locale.FRENCH, actualLocale, "not the expected locale");
    }

    @Test
    public void preHandleWithLocalePramFre() throws Exception {
        request.setParameter(interceptor.getParamName(), "fre");
        boolean result = interceptor.preHandle(request, response, null);
        assertTrue(result, "result if we should proceed");
        assertEquals(200, response.getStatus());

        Locale actualLocale = RequestContextUtils.getLocale(request);
        assertEquals(Locale.FRENCH, actualLocale, "not the expected locale");
    }

    @Test
    public void preHandleWithLocalePramFrenchAndSwitchToEnglish() throws Exception {
        // set it to French before we start
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        localeResolver.setLocale(request, response, Locale.FRENCH);

        // now do the test to make sure we can switch from French to English
        request.setParameter(interceptor.getParamName(), "eng");
        boolean result = interceptor.preHandle(request, response, null);
        assertTrue(result, "result if we should proceed");
        assertEquals(200, response.getStatus());

        Locale actualLocale = RequestContextUtils.getLocale(request);
        assertEquals(Locale.ENGLISH, actualLocale, "not the expected locale");
    }

    @Test
    public void preHandleNoLocaleResolver() throws Exception {
        request.removeAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE);
        request.setParameter(interceptor.getParamName(), "fra");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            interceptor.preHandle(request, response, null);
        });

        String expectedMessage = "No LocaleResolver found: not in a DispatcherServlet request?";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void preHandleBadLocaleIgnore() throws Exception {
        // set it to French before we start
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        localeResolver.setLocale(request, response, Locale.FRENCH);

        // now do the test to make sure we can switch from French to English
        request.setParameter(interceptor.getParamName(), "abc");
        boolean result = interceptor.preHandle(request, response, null);
        assertTrue(result, "result if we should proceed");
        assertEquals(200, response.getStatus());

        Locale actualLocale = RequestContextUtils.getLocale(request);
        assertEquals(Locale.FRENCH, actualLocale, "not the expected locale");
    }

    @Test
    @Disabled("this test needs work")
    public void preHandleBadLocaleThrowException() throws Exception {
        request.setParameter(interceptor.getParamName(), "abc");
        interceptor.setIgnoreInvalidLocale(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            interceptor.preHandle(request, response, null);
        });

        assertNotNull(exception.getMessage(), "exception message has to have text");
        assertTrue(exception.getMessage().contains("Ignoring invalid locale value"));
    }

}
