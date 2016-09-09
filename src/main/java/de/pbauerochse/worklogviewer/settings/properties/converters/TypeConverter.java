package de.pbauerochse.worklogviewer.settings.properties.converters;

/**
 * @author Patrick Bauerochse
 */
public interface TypeConverter {

    boolean canConvertTo(Class<?> targetClass);

    Object convert(String value, Class<?> targetClass);

}
