package org.ikora.inspector.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatabaseConfiguration {
    @JsonProperty(value = "driver", defaultValue = "sqlite")
    private String driver = "sqlite";
    @JsonProperty("location")
    private String location;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
