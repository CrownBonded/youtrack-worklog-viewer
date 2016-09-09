package de.pbauerochse.worklogviewer.settings.properties;

import de.pbauerochse.worklogviewer.settings.properties.converters.Converters;
import de.pbauerochse.worklogviewer.settings.properties.converters.MissingConverterException;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Allows annotating class fields with {@link Property} and {@link NestedProperties}
 * to automatically apply the values read from {@link Properties}
 *
 * @author Patrick Bauerochse
 */
public class TypedProperties extends Properties {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypedProperties.class);

    public static Converters CONVERTERS = new Converters();

    /**
     * Create TypedProperties from the given file. Returns an empty
     * instance if the file can not be found or is not a valid,
     * standard Java properties file.
     *
     * @param file The file containing the properties
     * @return the instance of the TypedProperties
     */
    public static TypedProperties fromFile(File file) {
        Properties properties = new Properties();

        try (InputStream in = new FileInputStream(file)) {
            properties.load(in);
        } catch (IOException e) {
            LOGGER.warn("Could not load properties from file {}. Returning default instance without any properties", file.getAbsolutePath(), e);
        }

        return fromProperties(properties);
    }

    /**
     * Create TypedProperties from the given Properties
     *
     * @param properties The properties to use
     * @return the instance of the TypedProperties
     */
    public static TypedProperties fromProperties(Properties properties) {
        Assert.notNull(properties, "Properties must not be null");
        return new TypedProperties(properties);
    }

    public static Properties toProperties(Object object) {
        return null;
    }

    private TypedProperties(Properties properties) {
        super(properties);
    }

    /**
     * Applies the values read from the {@link Properties} object to
     * the given type by reading the fields annotated with {@link Property} or
     * {@link NestedProperties} and automatically transforming the values
     *
     * @param type The type to apply the values to
     * @return the type with the applied values
     */
    public <T> T applyTo(T type) {
        Assert.notNull(type, "Type to apply values to must not be null");

        Class<?> clazz = type.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, field.getName());

            Property propertyAnnotation = field.getAnnotation(Property.class);
            if (propertyAnnotation != null) {
                tryApplyProperty(propertyAnnotation, propertyDescriptor, type);
            }

            NestedProperties nestedProperties = field.getAnnotation(NestedProperties.class);
            if (nestedProperties != null) {
                tryApplyNestedProperties(nestedProperties, propertyDescriptor, type);
            }
        }

        return type;
    }

    public <T> TypedProperties applyFrom(T type) {
        return this;
    }

    private void tryApplyProperty(Property propertyAnnotation, PropertyDescriptor fieldDescriptor, Object type) {
        Class<?> propertyType = fieldDescriptor.getPropertyType();

        if (propertyType.isPrimitive()) {
            tryApplyPrimitiveValue(propertyAnnotation, fieldDescriptor, type);
        } else {
            tryApplyNonPrimitiveValue(propertyAnnotation, fieldDescriptor, type);
        }
    }

    private void tryApplyPrimitiveValue(Property propertyAnnotation, PropertyDescriptor fieldDescriptor, Object type) {
        if (isExplicitlySetToNull(propertyAnnotation)) {
            throw new IllegalArgumentException("Property " + propertyAnnotation.value() + " is set to a null value but is a primitive type which can not be null");
        }

        if (containsPropertyKey(propertyAnnotation)) {
            Class<?> primitiveWrapperClass = ClassUtils.primitiveToWrapper(fieldDescriptor.getPropertyType());
            setValue(propertyAnnotation, fieldDescriptor, type, primitiveWrapperClass);
        }
    }

    private void tryApplyNonPrimitiveValue(Property propertyAnnotation, PropertyDescriptor fieldDescriptor, Object type) {
        if (isExplicitlySetToNull(propertyAnnotation)) {
            // value is explicitly set to null so we set the property to null
            setValue(fieldDescriptor, null, type);
        } else if (!mightBeNullValue(propertyAnnotation)) {
            setValue(propertyAnnotation, fieldDescriptor, type, fieldDescriptor.getPropertyType());
        }
    }

    private void setValue(Property propertyAnnotation, PropertyDescriptor fieldDescriptor, Object type, Class<?> actualTargetClass) {
        String valueString = getProperty(propertyAnnotation.value());
        Object value = valueString;

        if (!String.class.equals(actualTargetClass)) {
            try {
                value = CONVERTERS.convert(valueString, actualTargetClass);
            } catch (MissingConverterException e) {
                LOGGER.warn("Could not set field " + fieldDescriptor.getDisplayName() + ". No converter to " + actualTargetClass.getName() + " found", e);
            }
        }

        setValue(fieldDescriptor, value, type);
    }

    private boolean isExplicitlySetToNull(Property propertyAnnotation) {
        return containsPropertyKey(propertyAnnotation) && mightBeNullValue(propertyAnnotation);
    }

    private boolean containsPropertyKey(Property propertyAnnotation) {
        return stringPropertyNames().contains(propertyAnnotation.value());
    }

    private boolean mightBeNullValue(Property propertyAnnotation) {
        String value = getProperty(propertyAnnotation.value());
        return StringUtils.isEmpty(value);
    }

    private void setValue(PropertyDescriptor fieldDescriptor, Object value, Object target) {
        Method setter = fieldDescriptor.getWriteMethod();
        try {
            setter.invoke(target, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Could not invoke setter " + setter.toString() + " with value " + value + " of type " + value.getClass().getName(), e);
        }
    }

    private void tryApplyNestedProperties(NestedProperties nestedProperties, PropertyDescriptor field, Object type) {
        try {
            Class<?> nestedObjectType = field.getPropertyType();
            Object nestedProperty = field.getReadMethod().invoke(type, null);

            if (nestedProperty == null) {
                nestedProperty = nestedObjectType.newInstance();
                field.getWriteMethod().invoke(type, nestedProperty);
            }

            applyTo(nestedProperty);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Could not invoke getter " + field.getReadMethod().toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


}
