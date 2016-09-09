package de.pbauerochse.worklogviewer.settings.properties.converters;

/**
 * @author Patrick Bauerochse
 */
public class MissingConverterException extends Exception {

    MissingConverterException(String message) {
        super(message);
    }
}
