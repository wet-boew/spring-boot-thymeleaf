package ca.canada.ised.wet.cdts.components.wet.interceptor;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import ca.canada.ised.wet.cdts.components.wet.utils.Language;

/**
 * The Class WETLocaleChangeInterceptor handles locale changes .
 */
public class WETLocaleChangeInterceptor extends LocaleChangeInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws ServletException {

        String lang = request.getParameter(getParamName());
        if (lang != null) {
            Locale newLocale = Language.getLocale(lang);
            if (newLocale != null) {
                changeLocale(request, response, newLocale.getLanguage());
            }
        }
        // Proceed in any case.
        return true;
    }

    /**
     * Change locale.
     *
     * @param request the request
     * @param response the response
     * @param newLocale the new locale
     */
    private void changeLocale(HttpServletRequest request, HttpServletResponse response, String newLocale) {
        if (checkHttpMethod(request.getMethod())) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }
            try {
                localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLocale));
            } catch (IllegalArgumentException ex) {
                if (isIgnoreInvalidLocale()) {
                    logger.debug("Ignoring invalid locale value [" + newLocale + "]: " + ex.getMessage());
                } else {
                    throw ex;
                }
            }
        }
    }

    /**
     * Check http method.
     *
     * @param currentMethod the current method
     * @return true, if successful
     */
    private boolean checkHttpMethod(String currentMethod) {
        String[] configuredMethods = getHttpMethods();
        if (ObjectUtils.isEmpty(configuredMethods)) {
            return true;
        }
        for (String configuredMethod : configuredMethods) {
            if (configuredMethod.equalsIgnoreCase(currentMethod)) {
                return true;
            }
        }
        return false;
    }
}
