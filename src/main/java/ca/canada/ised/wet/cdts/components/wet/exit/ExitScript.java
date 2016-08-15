package ca.canada.ised.wet.cdts.components.wet.exit;

import java.io.Serializable;

/**
 * The Class ExitScript contains information required by the WET4 api that monitors when a user requests to leave the
 * secure application.
 *
 * @author Frank Giusto
 */
public class ExitScript implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The exit script enabled property. */
    private Boolean exitScriptEnabled;

    /** The exit message text. */
    private String exitMessage;

    /** The exit url. */
    private String exitUrl;

    /** The exit excluded domains. */
    private String exitExcludedDomains;

    /**
     * Gets the exit message.
     *
     * @return the exit message
     */
    public String getExitMessage() {
        return exitMessage;
    }

    /**
     * Sets the exit message.
     *
     * @param exitMessage the new exit message
     */
    public void setExitMessage(String exitMessage) {
        this.exitMessage = exitMessage;
    }

    /**
     * Gets the exit script enabled property.
     *
     * @return the exit script enabled property
     */
    public Boolean getExitScriptEnabled() {
        return exitScriptEnabled;
    }

    /**
     * Sets the exit script enabled property.
     *
     * @param exitScriptEnabled the new exit script enabled
     */
    public void setExitScriptEnabled(Boolean exitScriptEnabled) {
        this.exitScriptEnabled = exitScriptEnabled;
    }

    /**
     * Gets the exit url.
     *
     * @return the exit url
     */
    public String getExitUrl() {
        return exitUrl;
    }

    /**
     * Sets the exit url.
     *
     * @param exitUrl the new exit url
     */
    public void setExitUrl(String exitUrl) {
        this.exitUrl = exitUrl;
    }

    /**
     * Gets the exit excluded domains.
     *
     * @return the exit excluded domains
     */
    public String getExitExcludedDomains() {
        return exitExcludedDomains;
    }

    /**
     * Sets the exit excluded domains.
     *
     * @param exitExcludedDomains the new exit excluded domains
     */
    public void setExitExcludedDomains(String exitExcludedDomains) {
        this.exitExcludedDomains = exitExcludedDomains;
    }

}
