package de.pbauerochse.worklogviewer.youtrack.connector;

import de.pbauerochse.worklogviewer.util.SettingsUtil;
import de.pbauerochse.worklogviewer.youtrack.domain.GroupByCategory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author patrick
 * @since 07.09.16
 */
public class RestYoutrackConnector {

    private RestTemplate restTemplate;

    public RestYoutrackConnector(ClientHttpRequestFactory httpRequestFactory) {
        restTemplate = new RestTemplate(httpRequestFactory);
    }

    public List<GroupByCategory> getPossibleGroupByCategories() {
        String url = buildYouTrackUrl("");
        Object response = restTemplate.getForObject(url, Object.class);

        return null;
    }

}
