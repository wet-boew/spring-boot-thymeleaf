package ca.canada.ised.wet.cdts.components.wet.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;

public class CanadianLocaleUtilTest {

    @Test
    void toCanadianLocale() {
        // weird case
        assertEquals(Locale.CANADA, CanadianLocaleUtil.toCanadianLocale(null));

        // normal ones
        assertEquals(Locale.CANADA, CanadianLocaleUtil.toCanadianLocale(Locale.CANADA));
        assertEquals(Locale.CANADA_FRENCH, CanadianLocaleUtil.toCanadianLocale(Locale.CANADA_FRENCH));

        // people from the EU
        assertEquals(Locale.CANADA_FRENCH, CanadianLocaleUtil.toCanadianLocale(Locale.FRANCE));
        assertEquals(Locale.CANADA, CanadianLocaleUtil.toCanadianLocale(Locale.UK));

        // asia
        assertEquals(Locale.CANADA, CanadianLocaleUtil.toCanadianLocale(Locale.CHINA));
    }

    @Test
    void toOtherCanadianLocale() {
        // weird case
        assertEquals(Locale.CANADA_FRENCH, CanadianLocaleUtil.toOtherCanadianLocale(null));

        // normal ones
        assertEquals(Locale.CANADA, CanadianLocaleUtil.toOtherCanadianLocale(Locale.CANADA_FRENCH));
        assertEquals(Locale.CANADA_FRENCH, CanadianLocaleUtil.toOtherCanadianLocale(Locale.CANADA));
    }

}
