package ca.canada.ised.wet.cdts.components.wet.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * The <code>Language</code> class represents all languages currently supported
 * by Strategis. It uses the ISO code language. Supported languages are:
 *
 * <ul>
 * <li>English</li>
 * <li>French</li>
 * <li>Spanish</li>
 * </ul>
 *
 * Support for Spanish uses only the <code>spa</code> value. The
 * <code>esl</code> is not supported by this class
 *
 * @author Hugh Willson (octalpus), willson.hugh@ic.gc.ca
 * @since 2007-04-16
 * @see Locale
 */
public final class Language {

	/** English Constant. */
	public static final String ENGLISH = "eng";

	/** French constant. */
	public static final String FRENCH = "fre";

	/** French constant. */
	public static final String FRANCAIS = "fra";

	/** Array of supported languages. */
	private static String[] supportedLanguages = { Language.ENGLISH, Language.FRENCH, Language.FRANCAIS };

	/** Map supported languages to locales. */
	private static final Map<String, Locale> LANG_TO_LOCALE_MAP = new HashMap<>();
	static {
		LANG_TO_LOCALE_MAP.put(ENGLISH, new Locale("en", "CA"));
		LANG_TO_LOCALE_MAP.put(FRENCH, new Locale("fr", "CA"));
		LANG_TO_LOCALE_MAP.put(FRANCAIS, new Locale("fr", "CA"));
	}

	/** Map supported locales to languages. */
	private static final Map<Locale, String> LOCALE_TO_LANG_MAP = new HashMap<>();
	static {
		// note: this is not just a simple inversion of the above Map
		LOCALE_TO_LANG_MAP.put(new Locale("en"), ENGLISH);
		LOCALE_TO_LANG_MAP.put(new Locale("fr"), FRENCH);
		LOCALE_TO_LANG_MAP.put(new Locale("en", "CA"), ENGLISH);
		LOCALE_TO_LANG_MAP.put(new Locale("en", "US"), ENGLISH);
		LOCALE_TO_LANG_MAP.put(new Locale("en", "GB"), ENGLISH);
		LOCALE_TO_LANG_MAP.put(new Locale("fr", "CA"), FRENCH);
	}

	/**
	 * Private constructor to identify this as a utility class.
	 */
	private Language() {
	}

	/**
	 * Returns three character ISO code for the specified locale.
	 * 
	 * @param locale
	 *            <code>Locale</code> for which we want the language.
	 * @return the 3-character ISO code of the language for the specified
	 *         locale.
	 */
	public static String getLanguage(final Locale locale) {
		if (locale == null) {
			throw new IllegalArgumentException("Invalid locale.");
		}
		return LOCALE_TO_LANG_MAP.get(locale);
	}

	/**
	 * Returns a <code>Locale</code> object for the specified language ISO code.
	 * 
	 * @param lang
	 *            the 3-character ISO code of the language to check.
	 * @return locale <code>Locale</code> based on <code>Language</code>.
	 */
	public static Locale getLocale(final String lang) {
		return LANG_TO_LOCALE_MAP.get(lang);

	}

	/**
	 * Return array of Strategis supported languages.
	 * 
	 * @return supportedLanguages Array of Strategis supported languages.
	 */
	public static String[] getSupportedLanguages() {
		return supportedLanguages;
	}

	/**
	 * Return array of Strategis supported locales.
	 * 
	 * @return supportedLocales Strategis supported locales.
	 */
	public static Map<Locale, String> getSupportedLocales() {
		return LOCALE_TO_LANG_MAP;
	}

	/**
	 * <code>isValidLang</code> checks whether the passed paramter represents a
	 * valid Strategis supported <code>Language</code>.
	 * 
	 * @param lang
	 *            <code>Language</code> to check.
	 * @return boolean <code>true</code> if language is supported,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isValidLanguage(final String lang) {
		boolean isValidLanguage = false;
		if (lang != null) {
			for (String supportedLanguage : supportedLanguages) {
				if (lang.equals(supportedLanguage)) {
					isValidLanguage = true;
				}
			}
		}
		return isValidLanguage;

	}

	/**
	 * <code>isValidLocale</code> checks whether the passed paramter represents
	 * a valid Strategis supported <code>Locale</code>.
	 * 
	 * @param locale
	 *            the <code>Locale</code> to check.
	 * @return boolean <code>true</code> if locale is supported,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isValidLocale(final Locale locale) {
		boolean isValidLocale = false;
		if (locale != null) {
			isValidLocale = LOCALE_TO_LANG_MAP.containsKey(locale);
		}
		return isValidLocale;
	}

	/**
	 * Is the current locale English?
	 * 
	 * @return <code>boolean</code>
	 */
	public static boolean isEnglish() {
		return LocaleContextHolder.getLocale().getLanguage().equals(Locale.CANADA.getLanguage());
	}

}
