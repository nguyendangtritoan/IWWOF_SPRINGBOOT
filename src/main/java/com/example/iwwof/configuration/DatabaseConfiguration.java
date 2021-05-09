package com.example.iwwof.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "db")
@Configuration
@RefreshScope
public class DatabaseConfiguration {
    // This is for storing application properties in a Map
    public Map<String, String> dbProps;

    public Map<String, String> getDbProps() {
        return dbProps;
    }

    public void setDbProps(Map<String, String> dbProps) {
        this.dbProps = dbProps;
    }

    // Get property value using key
    public String getDbPropData(String key) {
        return dbProps.get(key);
    }



}
