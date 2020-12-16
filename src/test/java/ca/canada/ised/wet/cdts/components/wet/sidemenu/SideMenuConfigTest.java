package ca.canada.ised.wet.cdts.components.wet.sidemenu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.canada.ised.wet.cdts.components.wet.breadcrumbs.AbstractBaseTest;

public class SideMenuConfigTest extends AbstractBaseTest {

    @Autowired
    private SideMenuConfig sideMenuConfig;

    /** Smoke test using a testing file. */
    @Test
    public void testGetSectionMenuList() {
        List<SectionMenu> menuList = sideMenuConfig.getSectionMenuList();
        Assert.assertNotNull("menu list shouldb't be null", menuList);

        Assert.assertEquals("not the right menu size", 2, menuList.size());
        for (SectionMenu sectionMenu : menuList) {
            assertContains(sectionMenu.getSectionNameEn(), "unit test", "Not the expected value in SectionNameEn");
            assertContains(sectionMenu.getSectionNameFr(), "unit test", "Not the expected value in SectionNameFr");
            assertContains(sectionMenu.getSectionNameFr(), "FR", "Not the expected FR value in SectionNameFr");

            assertFalse("menu links should not be empty", sectionMenu.getMenuLinks().isEmpty());

            for (MenuLink menuLink : sectionMenu.getMenuLinks()) {
                assertTrue("test links should start with https", menuLink.getHref().startsWith("https://"));
            }
        }
    }

    private void assertContains(String string, String searchText, String message) {
        assertTrue(message, string.contains(searchText));
    }
}
