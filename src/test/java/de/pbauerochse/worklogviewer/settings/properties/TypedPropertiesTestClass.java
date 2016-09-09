package de.pbauerochse.worklogviewer.settings.properties;

import de.pbauerochse.worklogviewer.domain.ReportTimerange;
import de.pbauerochse.worklogviewer.settings.WindowSettings;

/**
 * @author Patrick Bauerochse
 */
public class TypedPropertiesTestClass {

    @Property("test.nullable.string")
    private String nullableString;

    @Property("test.nullable.integer")
    private Integer nullableInteger;

    @Property("test.nullable.boolean")
    private Boolean nullableBoolean;

    @Property("test.nullable.double")
    private Double nullableDouble = 15d;

    @Property("test.nullable.enum")
    private ReportTimerange timerange;

    @Property("test.notnullable.boolean")
    private boolean notNullableBoolean = false;

    @Property("test.notnullable.integer")
    private int notNullableInt = -1896;

    @NestedProperties
    private WindowSettings nestedProperty;

    public String getNullableString() {
        return nullableString;
    }

    public void setNullableString(String nullableString) {
        this.nullableString = nullableString;
    }

    public Integer getNullableInteger() {
        return nullableInteger;
    }

    public void setNullableInteger(Integer nullableInteger) {
        this.nullableInteger = nullableInteger;
    }

    public Boolean getNullableBoolean() {
        return nullableBoolean;
    }

    public void setNullableBoolean(Boolean nullableBoolean) {
        this.nullableBoolean = nullableBoolean;
    }

    public Double getNullableDouble() {
        return nullableDouble;
    }

    public void setNullableDouble(Double nullableDouble) {
        this.nullableDouble = nullableDouble;
    }

    public ReportTimerange getTimerange() {
        return timerange;
    }

    public void setTimerange(ReportTimerange timerange) {
        this.timerange = timerange;
    }

    public boolean isNotNullableBoolean() {
        return notNullableBoolean;
    }

    public void setNotNullableBoolean(boolean notNullableBoolean) {
        this.notNullableBoolean = notNullableBoolean;
    }

    public int getNotNullableInt() {
        return notNullableInt;
    }

    public void setNotNullableInt(int notNullableInt) {
        this.notNullableInt = notNullableInt;
    }

    public WindowSettings getNestedProperty() {
        return nestedProperty;
    }

    public void setNestedProperty(WindowSettings nestedProperty) {
        this.nestedProperty = nestedProperty;
    }
}
