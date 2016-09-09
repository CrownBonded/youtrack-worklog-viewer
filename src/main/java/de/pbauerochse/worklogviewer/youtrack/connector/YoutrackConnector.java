package de.pbauerochse.worklogviewer.youtrack.connector;

import de.pbauerochse.worklogviewer.youtrack.domain.GroupByCategory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author patrick
 * @since 07.09.16
 */
public class YoutrackConnector {

    private RestTemplate restTemplate;

    public YoutrackConnector(RestTemplate restTemplate) {
        Assert.notNull(restTemplate, "RestTemplate must not be null");
        this.restTemplate = restTemplate;
    }

    public List<GroupByCategory> getPossibleGroupByCategories() {

        return null;
    }

}
