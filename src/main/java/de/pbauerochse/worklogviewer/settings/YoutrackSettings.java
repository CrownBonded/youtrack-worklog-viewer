package de.pbauerochse.worklogviewer.settings;

import de.pbauerochse.worklogviewer.settings.properties.Property;
import de.pbauerochse.worklogviewer.youtrack.connector.YouTrackAuthenticationMethod;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Patrick Bauerochse
 */
public class YoutrackSettings {

    @Property("auth_method")
    private YouTrackAuthenticationMethod youTrackAuthenticationMethod = YouTrackAuthenticationMethod.HTTP_API;

    @Property("oauth_hub_url")
    private String youtrackOAuthHubUrl;

    @Property("oauth_service_id")
    private String youtrackOAuthServiceId;

    @Property("oauth_service_secret")
    private String youtrackOAuthServiceSecret;

    @Property("youtrackurl")
    private String youtrackUrl;

    @Property("username")
    private String youtrackUsername;

    @Property("password")
    private String youtrackPassword;

    public YouTrackAuthenticationMethod getYouTrackAuthenticationMethod() {
        return youTrackAuthenticationMethod;
    }

    public void setYouTrackAuthenticationMethod(YouTrackAuthenticationMethod youTrackAuthenticationMethod) {
        this.youTrackAuthenticationMethod = youTrackAuthenticationMethod;
    }

    public String getYoutrackOAuthHubUrl() {
        return youtrackOAuthHubUrl;
    }

    public void setYoutrackOAuthHubUrl(String youtrackOAuthHubUrl) {
        this.youtrackOAuthHubUrl = youtrackOAuthHubUrl;
    }

    public String getYoutrackOAuthServiceId() {
        return youtrackOAuthServiceId;
    }

    public void setYoutrackOAuthServiceId(String youtrackOAuthServiceId) {
        this.youtrackOAuthServiceId = youtrackOAuthServiceId;
    }

    public String getYoutrackOAuthServiceSecret() {
        return youtrackOAuthServiceSecret;
    }

    public void setYoutrackOAuthServiceSecret(String youtrackOAuthServiceSecret) {
        this.youtrackOAuthServiceSecret = youtrackOAuthServiceSecret;
    }

    public String getYoutrackUrl() {
        return youtrackUrl;
    }

    public void setYoutrackUrl(String youtrackUrl) {
        this.youtrackUrl = youtrackUrl;
    }

    public String getYoutrackUsername() {
        return youtrackUsername;
    }

    public void setYoutrackUsername(String youtrackUsername) {
        this.youtrackUsername = youtrackUsername;
    }

    public String getYoutrackPassword() {
        return youtrackPassword;
    }

    public void setYoutrackPassword(String youtrackPassword) {
        this.youtrackPassword = youtrackPassword;
    }

    public boolean hasMissingConnectionParameters() {
        return StringUtils.isEmpty(youtrackUrl) ||
                StringUtils.isEmpty(youtrackUsername) ||
                StringUtils.isEmpty(youtrackPassword) ||
                (
                        youTrackAuthenticationMethod == YouTrackAuthenticationMethod.OAUTH2 &&
                                (StringUtils.isEmpty(youtrackOAuthHubUrl) || StringUtils.isEmpty(youtrackOAuthServiceId) || StringUtils.isEmpty(youtrackOAuthServiceSecret))
                );
    }

    public int getConnectionParametersHashCode() {
        int result = getHashOrZero(youtrackUrl);
        result = 31 * result + getHashOrZero(youtrackUsername);
        result = 31 * result + getHashOrZero(youtrackPassword);
        result = 31 * result + getHashOrZero(youTrackAuthenticationMethod);
        result = 31 * result + getHashOrZero(youtrackOAuthServiceId);
        result = 31 * result + getHashOrZero(youtrackOAuthServiceSecret);
        result = 31 * result + getHashOrZero(youtrackOAuthHubUrl);
        return result;
    }

    private int getHashOrZero(Object o) {
        if (o == null) return 0;
        return o.hashCode();
    }
}
