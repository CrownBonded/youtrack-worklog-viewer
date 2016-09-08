package de.pbauerochse.worklogviewer.settings;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Patrick Bauerochse
 */
public class SettingsFactoryTest {

    @Test
    public void shouldReturnDefaultPropertiesWhenConfigFileNotPresent() throws IOException {
        File tempFile = File.createTempFile("ytwlv_", "tempfile");
        Settings settings = SettingsFactory.loadSettings(tempFile);

        assertNotNull(settings);
    }

}