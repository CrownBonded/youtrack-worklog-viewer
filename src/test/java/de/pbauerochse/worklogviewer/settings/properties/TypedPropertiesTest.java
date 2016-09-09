package de.pbauerochse.worklogviewer.settings.properties;

import de.pbauerochse.worklogviewer.domain.ReportTimerange;
import de.pbauerochse.worklogviewer.settings.WindowSettings;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Patrick Bauerochse
 */
public class TypedPropertiesTest {

    @Test
    public void shouldApplyPropertiesToClass() {
        // given
        Properties properties = new Properties();
        properties.setProperty("test.notnullable.integer", "1896");
        properties.setProperty("test.nullable.enum", ReportTimerange.LAST_WEEK.name());
        properties.setProperty("test.nullable.double", "");   // has a default value
        properties.setProperty("window.x", "1896");

        TypedProperties typedProperties = TypedProperties.fromProperties(properties);

        // when
        TypedPropertiesTestClass propertiesApplied = typedProperties.applyTo(new TypedPropertiesTestClass());

        // then
        assertThat(propertiesApplied, notNullValue());
        assertThat("notNullableInt does not match", propertiesApplied.getNotNullableInt(), is(1896));
        assertThat(propertiesApplied.getTimerange(), is(ReportTimerange.LAST_WEEK));
        assertThat(propertiesApplied.getNullableString(), nullValue());
        assertThat(propertiesApplied.getNullableDouble(), nullValue());
        assertThat(propertiesApplied.getNestedProperty(), notNullValue());
        assertThat(propertiesApplied.getNestedProperty().getPosX(), is(1896));
    }

    @Test
    public void shouldWriteClassToProperties() {
        WindowSettings nestedProperty = new WindowSettings();
        nestedProperty.setPosX(1004);
        nestedProperty.setPosY(1896);
        nestedProperty.setWidth(1024);
        nestedProperty.setHeight(768);

        TypedPropertiesTestClass testClass = new TypedPropertiesTestClass();
        testClass.setNestedProperty(nestedProperty);
        testClass.setNullableString("Padde war hier");
        testClass.setNullableInteger(9876);
        testClass.setNullableBoolean(true);
        testClass.setNullableDouble(1896d);
        testClass.setTimerange(ReportTimerange.LAST_MONTH);
        testClass.setNotNullableBoolean(true);
        testClass.setNotNullableInt(9999);

        // when
        Properties properties = TypedProperties.toProperties(testClass);

        // then
        assertThat(properties, notNullValue());
        assertThat(properties.getProperty("window.x"), is(String.valueOf(nestedProperty.getPosX())));
        assertThat(properties.getProperty("window.y"), is(String.valueOf(nestedProperty.getPosY())));
        assertThat(properties.getProperty("window.width"), is(String.valueOf(nestedProperty.getWidth())));
        assertThat(properties.getProperty("window.height"), is(String.valueOf(nestedProperty.getHeight())));

        assertThat(properties.getProperty("test.nullable.string"), is(String.valueOf(testClass.getNullableString())));
        assertThat(properties.getProperty("test.nullable.integer"), is(String.valueOf(testClass.getNullableInteger())));
        assertThat(properties.getProperty("test.nullable.boolean"), is(String.valueOf(testClass.getNullableBoolean())));
        assertThat(properties.getProperty("test.nullable.double"), is(String.valueOf(testClass.getNullableDouble())));
        assertThat(properties.getProperty("test.nullable.enum"), is(String.valueOf(testClass.getTimerange())));
        assertThat(properties.getProperty("test.notnullable.boolean"), is(String.valueOf(testClass.isNotNullableBoolean())));
        assertThat(properties.getProperty("test.notnullable.integer"), is(String.valueOf(testClass.getNotNullableInt())));
    }

    @Test
    public void testExplicitNullSetting() {
        fail("Not implemented yet");
    }

    @Test
    public void testLeaveDefaultValueIfNoPropertyAssigned() {
        fail("Not implemented yet");
    }


}