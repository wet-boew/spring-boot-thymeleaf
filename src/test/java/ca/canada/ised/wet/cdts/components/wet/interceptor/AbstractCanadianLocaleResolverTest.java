package ca.canada.ised.wet.cdts.components.wet.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

public abstract class AbstractCanadianLocaleResolverTest {

    private LocaleResolver localeResolver;

    private MockHttpServletRequest request;

    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        localeResolver = getLocaleResolver();
        request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        response = new MockHttpServletResponse();

        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, localeResolver);
    }

    abstract LocaleResolver getLocaleResolver();

    @Test
    void noSessionLocaleResolver() {
        request.removeAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE);

        assertThrows(IllegalStateException.class, () -> {
            // Code that should throw the exception
            localeResolver.resolveLocale(request);
        });

    }

    @Test
    void shouldReturnDefaultLocaleWhenNoneSet() {
        Locale locale = localeResolver.resolveLocale(request);
        assertEquals(Locale.CANADA, locale);
    }

    @Test
    void shouldReturnLocaleFromSessionWhenSet() {
        Locale french = Locale.FRENCH;
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, french);

        Locale locale = localeResolver.resolveLocale(request);
        assertEquals(Locale.CANADA_FRENCH, locale);
    }

    @Test
    void shouldChangeLocaleInSession() {
        Locale german = Locale.GERMAN;
        localeResolver.setLocale(request, response, german);

        Locale locale = localeResolver.resolveLocale(request);
        assertEquals(Locale.CANADA, locale);
    }
}
