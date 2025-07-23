package ca.canada.ised.wet.cdts.components.wet.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

public class CanadianCookieLocaleResolverTest extends AbstractCanadianLocaleResolverTest {

    @Override
    protected LocaleResolver getLocaleResolver() {
        return new CanadianCookieLocaleResolver();
    }

    @Test
    void shouldReturnLocaleFromCookieWhenSet() {
        Locale french = Locale.FRENCH;
        request.setAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME, french);

        Locale locale = localeResolver.resolveLocale(request);
        assertEquals(Locale.CANADA_FRENCH, locale);
    }

}
