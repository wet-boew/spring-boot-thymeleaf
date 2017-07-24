/**
 *
 */
package ca.canada.ised.wet.cdts.components.wet.config.beans;

/**
 * @author pitta
 *
 */
public class WETSession {

    private WETBooleanValue timeout = new WETBooleanValue();

    private WETIntegerValue inactivity = new WETIntegerValue();

    private WETIntegerValue reactiontime = new WETIntegerValue();

    private WETIntegerValue sessionalive = new WETIntegerValue();

    private String logouturl;

    private String refreshcallbackurl;

    private Boolean refreshonclick;

    private WETIntegerValue refreshlimit = new WETIntegerValue();

    private String method;

    private String additionaldata;

    /**
     * @return the timeout
     */
    public WETBooleanValue getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(WETBooleanValue timeout) {
        this.timeout = timeout;
    }

    /**
     * @return the inactivity
     */
    public WETIntegerValue getInactivity() {
        return inactivity;
    }

    /**
     * @param inactivity the inactivity to set
     */
    public void setInactivity(WETIntegerValue inactivity) {
        this.inactivity = inactivity;
    }

    /**
     * @return the reactiontime
     */
    public WETIntegerValue getReactiontime() {
        return reactiontime;
    }

    /**
     * @param reactiontime the reactiontime to set
     */
    public void setReactiontime(WETIntegerValue reactiontime) {
        this.reactiontime = reactiontime;
    }

    /**
     * @return the sessionalive
     */
    public WETIntegerValue getSessionalive() {
        return sessionalive;
    }

    /**
     * @param sessionalive the sessionalive to set
     */
    public void setSessionalive(WETIntegerValue sessionalive) {
        this.sessionalive = sessionalive;
    }

    /**
     * @return the logouturl
     */
    public String getLogouturl() {
        return logouturl;
    }

    /**
     * @param logouturl the logouturl to set
     */
    public void setLogouturl(String logouturl) {
        this.logouturl = logouturl;
    }

    /**
     * @return the refreshcallbackurl
     */
    public String getRefreshcallbackurl() {
        return refreshcallbackurl;
    }

    /**
     * @param refreshcallbackurl the refreshcallbackurl to set
     */
    public void setRefreshcallbackurl(String refreshcallbackurl) {
        this.refreshcallbackurl = refreshcallbackurl;
    }

    /**
     * @return the refreshonclick
     */
    public Boolean getRefreshonclick() {
        return refreshonclick;
    }

    /**
     * @param refreshonclick the refreshonclick to set
     */
    public void setRefreshonclick(Boolean refreshonclick) {
        this.refreshonclick = refreshonclick;
    }

    /**
     * @return the refreshlimit
     */
    public WETIntegerValue getRefreshlimit() {
        return refreshlimit;
    }

    /**
     * @param refreshlimit the refreshlimit to set
     */
    public void setRefreshlimit(WETIntegerValue refreshlimit) {
        this.refreshlimit = refreshlimit;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the additionaldata
     */
    public String getAdditionaldata() {
        return additionaldata;
    }

    /**
     * @param additionaldata the additionaldata to set
     */
    public void setAdditionaldata(String additionaldata) {
        this.additionaldata = additionaldata;
    }

}
