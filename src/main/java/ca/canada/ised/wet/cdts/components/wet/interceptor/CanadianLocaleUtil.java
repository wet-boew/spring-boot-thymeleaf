package ca.canada.ised.wet.cdts.components.wet.interceptor;

import static org.apache.commons.lang3.StringUtils.substring;

import java.util.Locale;

/**
 * Locale-related utility methods in the context of Spring Boot.
 */
public final class CanadianLocaleUtil {

    private CanadianLocaleUtil() {
    }

    /**
     * Converts the supplied locale to one of the two official Canadian locales. Any locale with a language starting
     * with an "F" (case insensitive) is assumed to be {@link CanadianLocaleUtil#FRENCH_CANADIAN} while anything else
     * (including <code>null</code>) is {@link CanadianLocaleUtil#ENGLISH_CANADIAN}.
     *
     * @param locale locale to convert
     * @return Canadian locale
     */
    public static Locale toCanadianLocale(Locale locale) {
        if (null == locale) {
            return Locale.CANADA;
        }

        // Since this class focuses on the two official languages only and never
        // returns null, we take liberties and assume anything starting with
        // an F is French, and anything else is English.
        return org.apache.commons.lang3.StringUtils.equalsIgnoreCase("f", substring(locale.getLanguage(), 0, 1))
            ? Locale.CANADA_FRENCH : Locale.CANADA;
    }

    /**
     * Locale toggle
     *
     * @param locale that we are currently using
     * @return
     */
    public static Locale toOtherCanadianLocale(Locale locale) {
        return Locale.CANADA_FRENCH.equals(locale) ? Locale.CANADA : Locale.CANADA_FRENCH;
    }

}
