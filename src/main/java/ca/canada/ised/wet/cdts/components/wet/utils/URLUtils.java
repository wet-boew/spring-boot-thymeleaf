package ca.canada.ised.wet.cdts.components.wet.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.canada.ised.wet.cdts.components.wet.config.WETModelKey;

/**
 * URL-related utility methods.
 *
 * @author Pascal Essiembre
 * @since 4.0.26.3
 */
public final class URLUtils {

    private static final Logger LOG = LoggerFactory.getLogger(URLUtils.class);

    /** Constant for language parameter (e.g. URL query string). */
    public static final String PARAM_LANG = "lang";

    private URLUtils() {
    }

    /**
     * Builds a "query string" based on given URL parameters, clearing up any "lang" parameter. If you do not care about
     * the presence of a language parameter, you may want to use {@link HttpServletRequest#getQueryString()} instead.
     *
     * @param requestParams URL request parameters
     * @return properly encoded query string
     */
    public static String toLanguageNeutralQueryString(Map<String, String[]> requestParams) {
        return toLanguageToggleQueryString(requestParams, (String) null);
    }

    /**
     * <p>
     * Builds a language toggle "query string" based on given URL parameters. The query string is returned is an
     * equivalent ready-to-use string with the "lang" parameter value toggled between its original value (if any) to the
     * target locale.
     * </p>
     * <p>
     * If no target locale is supplied, the query string is simply returned without a language parameter.
     * </p>
     * 
     * @param requestParams URL request parameters
     * @param targetLocal locale
     * @return properly encoded query string
     */
    public static String toLanguageToggleQueryString(Map<String, String[]> requestParams, Locale targetLocale) {
        return toLanguageToggleQueryString(requestParams, Language.getLanguage(targetLocale));
    }

    /**
     * <p>
     * Builds a language toggle "query string" based on given URL parameters. The query string is returned is an
     * equivalent ready-to-use string with the "lang" parameter value toggled between its original value (if any) to the
     * target language.
     * </p>
     * <p>
     * If no target language is supplied, the query string is simply returned without a language parameter.
     * </p>
     * 
     * @param requestParams URL request parameters
     * @param targetLanguage 3-letter ISO language code
     * @return properly encoded query string
     */
    public static String toLanguageToggleQueryString(Map<String, String[]> requestParams, String targetLanguage) {

        StringBuilder qs = new StringBuilder();
        char separator = '?';
        for (Entry<String, String[]> entry : requestParams.entrySet()) {
            String name = entry.getKey();
            for (String value : entry.getValue()) {
                if (!WETModelKey.LANGUAGE_URL.wetAttributeName().equals(name) && !PARAM_LANG.equals(name)) {
                    qs.append(separator);
                    separator = '&';
                    qs.append(urlEncode(name));
                    qs.append('=');
                    qs.append(urlEncode(value));
                }
            }
        }
        if (StringUtils.isNotBlank(targetLanguage)) {
            qs.append(separator);
            qs.append(PARAM_LANG);
            qs.append('=');
            qs.append(targetLanguage);
        }
        return qs.toString();
    }

    /**
     * Encodes a string into <code>application/x-www-form-urlencoded</code> format using the <code>UTF-8</code> encoding
     * scheme. Use this method to encode HTTP request parameter names and values.
     * 
     * @param text text to encode
     * @return encoded text
     */
    public static String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            // should never happen (standard charset).
            LOG.error("Could not encode URL value: {}", text, e);
            return text;
        }
    }

    /**
     * Decodes an <code>application/x-www-form-urlencoded</code> string using the <code>UTF-8</code> encoding scheme.
     * Use this method to decode raw HTTP request parameter names and values.
     * 
     * @param text text to decode
     * @return decoded text
     */
    public static String urlDecode(String text) {
        try {
            return URLDecoder.decode(text, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            // should never happen (standard charset).
            LOG.error("Could not decode URL value: {}", text, e);
            return text;
        }
    }
}
