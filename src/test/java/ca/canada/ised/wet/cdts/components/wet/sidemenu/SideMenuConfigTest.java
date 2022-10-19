package ca.canada.ised.wet.cdts.components.wet.sidemenu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.canada.ised.wet.cdts.components.wet.breadcrumbs.AbstractBaseTest;

public class SideMenuConfigTest extends AbstractBaseTest {

    @Autowired
    private SideMenuConfig sideMenuConfig;

    /** Smoke test using a testing file. */
    @Test
    public void testGetSectionMenuList() {
        List<SectionMenu> menuList = sideMenuConfig.getSectionMenuList();
        assertNotNull(menuList, "menu list shouldb't be null");

        assertEquals(2, menuList.size(), "not the right menu size");
        for (SectionMenu sectionMenu : menuList) {
            assertContains(sectionMenu.getSectionNameEn(), "unit test", "Not the expected value in SectionNameEn");
            assertContains(sectionMenu.getSectionNameFr(), "unit test", "Not the expected value in SectionNameFr");
            assertContains(sectionMenu.getSectionNameFr(), "FR", "Not the expected FR value in SectionNameFr");

            assertFalse(sectionMenu.getMenuLinks().isEmpty(), "menu links should not be empty");

            for (MenuLink menuLink : sectionMenu.getMenuLinks()) {
                assertTrue(menuLink.getHref().startsWith("https://"), "test links should start with https");
            }
        }
    }

    private void assertContains(String string, String searchText, String message) {
        assertTrue(string.contains(searchText), message);
    }
}
