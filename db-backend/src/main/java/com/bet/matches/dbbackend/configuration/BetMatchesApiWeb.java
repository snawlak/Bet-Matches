package com.bet.matches.dbbackend.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "bet.matches.api")
public class BetMatchesApiWeb {

    private final Endpoint endpoint = new Endpoint();
    private String baseUrl;

    @Data
    public static class Endpoint {
        private String teams;
        private String stadiums;
        private String players;
        private String matches;
    }

}

