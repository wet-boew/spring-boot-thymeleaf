package ca.canada.ised.wet.cdts.components.wet.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

public class CanadianSessionLocaleResolverTest extends AbstractCanadianLocaleResolverTest {

    @Override
    protected LocaleResolver getLocaleResolver() {
        return new CanadianSessionLocaleResolver();
    }

    @Test
    void shouldReturnLocaleFromSessionWhenSet() {
        Locale french = Locale.FRENCH;
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, french);

        Locale locale = localeResolver.resolveLocale(request);
        assertEquals(Locale.CANADA_FRENCH, locale);
    }
}
