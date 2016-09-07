package de.pbauerochse.worklogviewer.youtrack.connector;

import de.pbauerochse.worklogviewer.util.SettingsUtil;
import de.pbauerochse.worklogviewer.youtrack.domain.GroupByCategory;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * @author patrick
 * @since 07.09.16
 */
public class RestYoutrackConnectorTest {

    private RestYoutrackConnector connector;

    @Before
    public void before() {
        SettingsUtil.Settings settings = new SettingsUtil.Settings();

        BasicAuthRequestFactory requestFactory = new BasicAuthRequestFactory(settings);
        connector = new RestYoutrackConnector(requestFactory);
    }

    @Test
    public void testGetPossibleGroupByCategories() {
        List<GroupByCategory> categories = connector.getPossibleGroupByCategories();
        assertThat(categories, notNullValue());
        assertThat(categories, not(empty()));
    }

}