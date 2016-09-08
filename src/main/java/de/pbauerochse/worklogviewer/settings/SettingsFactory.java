package de.pbauerochse.worklogviewer.settings;

import de.pbauerochse.worklogviewer.util.ExceptionUtil;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

/**
 * @author Patrick Bauerochse
 */
class SettingsFactory {

    static Settings loadSettings(File configFileLocation) {
        Configuration configuration = loadConfiguration(configFileLocation);
        Settings settings = new Settings();
        return applyToSettings(settings, configuration);
    }

    private static Settings applyToSettings(Settings settings, Configuration configuration) {

        return settings;
    }

    private static Configuration loadConfiguration(File configFileLocation) {
        try {
            return new Configurations().properties(configFileLocation);
        } catch (ConfigurationException e) {
            throw ExceptionUtil.getIllegalStateException("exceptions.settings.read", e, configFileLocation.getAbsolutePath());
        }
    }


    private SettingsFactory() {

    }

}
