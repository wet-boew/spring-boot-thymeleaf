package ca.canada.ised.wet.cdts.components.wet.sidemenu;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

/**
 * The Class SideMenuConfig is populated with the contents of sectionMenu.yml which contains the WET4 side menu text and
 * url information.
 *
 * @author Frank Giusto
 */
@Component
public class SideMenuConfig {

    /** Logging instance. */
    private static final Logger LOG = LoggerFactory.getLogger(SideMenuConfig.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void readSectionMenuYaml() {
        String path = "classpath:sectionMenu.yml";
        Resource resource = resourceLoader.getResource(path);
        if (resource.exists()) {
            Yaml yaml = new Yaml();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sectionMenu.yml");
            SideMenuConfig load = yaml.loadAs(inputStream, SideMenuConfig.class);
            sectionMenuList.addAll(load.getSectionMenuList());
        } else {
            LOG.warn("Could not load the sidenav from file " + path);
        }
    }

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
