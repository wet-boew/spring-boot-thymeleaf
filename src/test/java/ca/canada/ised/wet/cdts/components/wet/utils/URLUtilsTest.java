package ca.canada.ised.wet.cdts.components.wet.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Pascal Essiembre
 * @since 4.0.26.3
 */
public class URLUtilsTest {

    private final Map<String, String[]> params = new LinkedHashMap<>();

    private String qs = null;

    @Before
    public void setup() {
        params.clear();
        params.put("plainKey", new String[]{"plain value"});
        params.put("funkÿ+ &key", new String[]{"acmé, & co. = great"});
        params.put("lang", new String[]{"eng"});
    }

    @Test
    public void testToLanguageNeutralQueryString() {
        // One lang set
        qs = URLUtils.toLanguageNeutralQueryString(params);
        params.remove("lang");
        assertEquals(qs, params);

        // Two langs set (bogus, but should work anyway)
        params.put("lang", new String[]{"fre", "eng"});
        qs = URLUtils.toLanguageNeutralQueryString(params);
        params.remove("lang");
        assertEquals(qs, params);

        // No lang set
        params.remove("lang");
        qs = URLUtils.toLanguageNeutralQueryString(params);
        assertEquals(qs, params);
    }

    @Test
    public void testToLanguageToggleQueryString() {
        // English to French (String)
        qs = URLUtils.toLanguageToggleQueryString(params, "fre");
        params.put("lang", new String[]{"fre"});
        assertEquals(qs, params);

        // French to English (String)
        qs = URLUtils.toLanguageToggleQueryString(params, "eng");
        params.put("lang", new String[]{"eng"});
        assertEquals(qs, params);

        // English to French (Locale)
        qs = URLUtils.toLanguageToggleQueryString(params, Locale.CANADA_FRENCH);
        params.put("lang", new String[]{"fre"});
        assertEquals(qs, params);

        // French to English (Locale)
        qs = URLUtils.toLanguageToggleQueryString(params, Locale.ENGLISH);
        params.put("lang", new String[]{"eng"});
        assertEquals(qs, params);

        // English, lang not set
        params.remove("lang");
        qs = URLUtils.toLanguageToggleQueryString(params, "fre");
        params.put("lang", new String[]{"fre"});
        assertEquals(qs, params);

        // French, lang not set
        params.remove("lang");
        qs = URLUtils.toLanguageToggleQueryString(params, Locale.ENGLISH);
        params.put("lang", new String[]{"eng"});
        assertEquals(qs, params);
    }

    private void assertEquals(String qs, Map<String, String[]> expectedParams) {
        Assert.assertTrue("Query string is expected to start with '?'.", qs.startsWith("?"));

        Map<String, String[]> actualParams = new LinkedHashMap<>();
        String[] pairs = StringUtils.removeStart(qs, "?").split("\\&");
        for (String pair : pairs) {
            actualParams.put(URLUtils.urlDecode(StringUtils.substringBefore(pair, "=")),
                new String[]{URLUtils.urlDecode(StringUtils.substringAfter(pair, "="))});
        }

        List<Entry<String, String[]>> actualEntries = new ArrayList<>(actualParams.entrySet());
        List<Entry<String, String[]>> expectedEntries = new ArrayList<>(expectedParams.entrySet());

        Assert.assertEquals("Wrong entry count.", expectedEntries.size(), actualEntries.size());

        for (int i = 0; i < expectedEntries.size(); i++) {
            Entry<String, String[]> expectedEntry = expectedEntries.get(i);
            Entry<String, String[]> actualEntry = actualEntries.get(i);
            Assert.assertEquals(expectedEntry.getKey(), actualEntry.getKey());
            Assert.assertArrayEquals(expectedEntry.getValue(), actualEntry.getValue());
        }
    }
}
