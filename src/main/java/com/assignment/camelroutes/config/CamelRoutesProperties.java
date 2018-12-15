package com.assignment.camelroutes.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "camelroutes", ignoreUnknownFields = false)
public class CamelRoutesProperties {

    private final Activemq activemq = new Activemq();

    @Getter
    @Setter
    public static class Activemq {

        @NotNull
        public String personQueue;

    }
}
