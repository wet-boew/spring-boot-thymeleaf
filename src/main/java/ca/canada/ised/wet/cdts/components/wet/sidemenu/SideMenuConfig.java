package ca.canada.ised.wet.cdts.components.wet.sidemenu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class SideMenuConfig is populated with the contents of sectionMenu.yml which contains the WET4 side menu text and
 * url information.
 *
 * @author Frank Giusto
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(locations = {"classpath:sectionMenu.yml"})
public class SideMenuConfig {

    /** The section menu list. */
    private List<SectionMenu> sectionMenuList = new ArrayList<>();

    /**
     * Gets the section menu list.
     *
     * @return the section menu list
     */
    public List<SectionMenu> getSectionMenuList() {
        return sectionMenuList;
    }

    /**
     * Sets the section menu list.
     *
     * @param sectionMenuList the new section menu list
     */
    public void setSectionMenuList(List<SectionMenu> sectionMenuList) {
        this.sectionMenuList = sectionMenuList;
    }

}
