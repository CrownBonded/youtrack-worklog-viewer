package de.pbauerochse.worklogviewer.settings.properties.converters;

import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Patrick Bauerochse
 */
@SuppressWarnings("WeakerAccess")
public class Converters {

    private final Set<TypeConverter> converters = new LinkedHashSet<>();

    public Converters() {
        addConverter(new ValueOfConverter());
    }

    public void addConverter(TypeConverter converter) {
        Assert.notNull(converter, "Converter must not be null");
        converters.add(converter);
    }

    public Object convert(String valueString, Class<?> targetClass) throws MissingConverterException {
        boolean noSuitableConverterFound = true;

        for (TypeConverter converter : converters) {
            if (converter.canConvertTo(targetClass)) {
                noSuitableConverterFound = false;

                Object value = converter.convert(valueString, targetClass);
                if (value != null) {
                    return value;
                }
            }
        }

        if (noSuitableConverterFound) {
            throw new MissingConverterException("No converter found for type " + targetClass.getName());
        }

        return null;
    }

    /**
     * <p>Converts to any type, that provides a static valueOf(String) method</p>
     * <p>Should cover most primitive type wrappers, as well as {@link java.util.Date} and Enumerations</p>
     */
    private class ValueOfConverter implements TypeConverter {

        @Override
        public boolean canConvertTo(Class<?> targetClass) {
            return getValueOfMethod(targetClass) != null;
        }

        @Override
        public Object convert(String value, Class<?> targetClass) {
            try {
                Method valueOfMethod = getValueOfMethod(targetClass);
                if (valueOfMethod == null) {
                    throw new IllegalArgumentException("Class " + targetClass.getName() + " does not provide a valueOf method");
                }

                return valueOfMethod.invoke(null, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Can not convert value " + value + " to " + targetClass.getName(), e);
            }
        }

        @SuppressWarnings("unchecked")
        private Method getValueOfMethod(Class targetClass) {
            try {
                return targetClass.getDeclaredMethod("valueOf", String.class);
            } catch (NoSuchMethodException e) {
                return null;
            }
        }
    }


}
