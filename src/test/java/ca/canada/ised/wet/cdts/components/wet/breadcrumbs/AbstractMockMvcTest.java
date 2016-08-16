/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.breadcrumbs;

import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Abstract test case used for doing mock mvc test cases.
 *
 * @author Jim Sellers
 * @since 1.0.0
 */
public abstract class AbstractMockMvcTest extends AbstractBaseTest {

    /** MockMVC. */
    protected MockMvc mockMvc;

    /**
     * Setup.
     */
    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(getWebAppContext()).dispatchOptions(true).build();
    }

}
