package ca.canada.ised.wet.cdts.components.wet.interceptor;

import org.springframework.web.servlet.LocaleResolver;

public class CanadianSessionLocaleResolverTest extends AbstractCanadianLocaleResolverTest {

    @Override
    protected LocaleResolver getLocaleResolver() {
        return new CanadianSessionLocaleResolver();
    }

}
