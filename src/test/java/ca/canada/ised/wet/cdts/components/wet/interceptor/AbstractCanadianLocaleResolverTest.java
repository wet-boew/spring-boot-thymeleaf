package ca.canada.ised.wet.cdts.components.wet.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.LocaleResolver;

public abstract class AbstractCanadianLocaleResolverTest {

    protected LocaleResolver localeResolver;

    protected MockHttpServletRequest request;

    protected MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        localeResolver = getLocaleResolver();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    abstract LocaleResolver getLocaleResolver();

    @Test
    void shouldReturnDefaultLocaleWhenNoneSet() {
        Locale locale = localeResolver.resolveLocale(request);
        assertEquals(Locale.CANADA, locale);
    }

    @Test
    void shouldChangeLocale() {
        Locale german = Locale.GERMAN;
        localeResolver.setLocale(request, response, german);

        Locale locale = localeResolver.resolveLocale(request);
        assertEquals(Locale.CANADA, locale);
    }
}
