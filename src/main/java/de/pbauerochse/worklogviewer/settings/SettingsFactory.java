package de.pbauerochse.worklogviewer.settings;

import de.pbauerochse.worklogviewer.settings.properties.TypedProperties;

import java.io.File;

/**
 * @author Patrick Bauerochse
 */
class SettingsFactory {

    static Settings loadSettings(File configFileLocation) {
        TypedProperties properties = TypedProperties.fromFile(configFileLocation);
        return properties.applyTo(new Settings());
    }

    static void saveSettings(Settings settings, File configFileLocation) {
        // TODO implement me
        throw new IllegalStateException("Not implemented yet");
    }

    private SettingsFactory() {

    }

}
