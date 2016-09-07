package de.pbauerochse.worklogviewer.youtrack.connector;

import de.pbauerochse.worklogviewer.util.SettingsUtil;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;

/**
 * @author patrick
 * @since 07.09.16
 */
public class BasicAuthRequestFactory extends HttpComponentsClientHttpRequestFactory {

    private SettingsUtil.Settings settings;

    public BasicAuthRequestFactory(SettingsUtil.Settings settings) {
        super();
        this.settings = settings;
    }

    @Override
    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
        HttpHost host = new HttpHost(settings.getYoutrackUrl());

        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();

        // Generate BASIC scheme object and add it to the local auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(host, basicAuth);

        // authentication credentials
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(settings.getYoutrackUsername(), settings.getYoutrackPassword());
        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(host), credentials);

        // Add AuthCache to the execution context
        HttpClientContext localcontext = HttpClientContext.create();
        localcontext.setAuthCache(authCache);
        localcontext.setCredentialsProvider(credsProvider);

        return localcontext;
    }
}
