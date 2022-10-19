package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * Base transactional test for this project. We have this here to minimize the number of times the app context is
 * created and torn down.
 */
@WebAppConfiguration
@SpringBootTest(classes = {WetCdtsSpringBootThymeleafTestConfig.class})
@ActiveProfiles(profiles = {"test"})
public abstract class AbstractBaseTest {

    /** The WebApplicationContext. */
    @Autowired
    private WebApplicationContext webAppContext;

    /**
     * Execute before each test.
     */
    @BeforeEach
    public void baseBefore() {
        toEnglish();
    }

    /**
     * Get webAppContext.
     *
     * @return <code>WebApplicationContext</code>
     */
    public WebApplicationContext getWebAppContext() {
        return webAppContext;
    }

    /**
     * Switch to English locale.
     */
    public void toEnglish() {
        LocaleContextHolder.setLocale(Locale.CANADA);
    }

    /**
     * Switch to French locale.
     */
    public void toFrench() {
        LocaleContextHolder.setLocale(Locale.CANADA_FRENCH);
    }

}
