package ca.canada.ised.wet.cdts.components.wet.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 */
public class LanguageTest {

    @Test
    public void getLanguageNull() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Language.getLanguage(null);
        });

        assertEquals("Invalid locale.", thrown.getMessage());
    }

    @Test
    public void getSupportedLanguages() {
        String[] supportedLanguages = Language.getSupportedLanguages();
        assertEquals(3, supportedLanguages.length);
    }

    @Test
    public void getSupportedLocales() {
        Map<Locale, String> supportedLocales = Language.getSupportedLocales();
        assertEquals(6, supportedLocales.size());
    }

    @ParameterizedTest
    @CsvSource({ //
        "null,false", // null
        ",false", // empty
        "Quenya,false", // Elvish
        "fre,true" // French
    })
    void toUpperCase_ShouldGenerateTheExpectedUppercaseValue(String input, boolean expected) {
        assertEquals(expected, Language.isValidLanguage(input));
    }

    @Test
    public void isValidLocaleNull() {
        assertFalse(Language.isValidLocale(null));
    }

    @Test
    public void isValidLocaleCanadianFrench() {
        assertTrue(Language.isValidLocale(Locale.CANADA_FRENCH));
    }
}
