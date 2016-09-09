package de.pbauerochse.worklogviewer.settings;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

/**
 * @author Patrick Bauerochse
 */
public class SettingsFactoryTest {

    @Test
    public void shouldReturnDefaultPropertiesWhenConfigFileNotPresent() throws IOException {
        File tempFile = new File(UUID.randomUUID().toString());
        Settings settings = SettingsFactory.loadSettings(tempFile);
        assertNotNull(settings);
    }

    @Test
    public void shouldApplyProperties() {

    }

}