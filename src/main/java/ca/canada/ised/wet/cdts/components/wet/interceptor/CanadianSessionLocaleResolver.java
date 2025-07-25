package ca.canada.ised.wet.cdts.components.wet.interceptor;

import java.util.Locale;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Enforces the use of one of Canada's official language {@link Locale}, with Canada as the locale's country. Having the
 * country set explicitly can be important for things such as value formatting (e.g., currency). The locale defaults to
 * English when it cannot be resolved.
 */
public class CanadianSessionLocaleResolver extends SessionLocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return CanadianLocaleUtil.toCanadianLocale(super.resolveLocale(request));
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        super.setLocale(request, response, CanadianLocaleUtil.toCanadianLocale(locale));
    }

}
