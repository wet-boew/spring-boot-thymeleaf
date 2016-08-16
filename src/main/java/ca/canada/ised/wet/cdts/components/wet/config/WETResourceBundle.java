package ca.canada.ised.wet.cdts.components.wet.config;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * The Class WETResourceBundle is used to retrieve all WET resource bundle properties.
 *
 * @author Frank Giusto
 */
public class WETResourceBundle extends ResourceBundleMessageSource {

    /** The base names for the CDN resource bundles. */
    private List<String> baseNames;

    /**
     * Gets the keys for the requested resource bundle.
     *
     * @param baseName the resource bundle base name
     * @param locale the locale
     * @return the keys of the resource bundle properties
     */
    public Set<String> getKeys(String baseName, Locale locale) {
        ResourceBundle bundle = getResourceBundle(baseName, locale);
        if (null == bundle) {
            return new HashSet<>();
        }
        return bundle.keySet();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setBasenames(String... basenames) {
        baseNames = CollectionUtils.arrayToList(basenames);
        super.setBasenames(basenames);
    }

    /**
     * Gets the basename of the resource bundle.
     *
     * @return the basename
     */
    public String getBasename() {
        Assert.isTrue(!CollectionUtils.isEmpty(baseNames), "Basename must not be empty");
        return baseNames.get(0);
    }
}
