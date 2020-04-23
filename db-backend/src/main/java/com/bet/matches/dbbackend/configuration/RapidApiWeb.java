package com.bet.matches.dbbackend.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "rapid.api")
public class RapidApiWeb {

    private final List<Header> headers;
    private final Endpoint endpoint = new Endpoint();
    private final Id id = new Id();

    private String baseUrl;

    @Data
    public static class Header {
        private final String name;
        private final String value;
    }

    @Data
    public static class Endpoint {
        private String teams;
        private String matchesFromLeague;
        private String match;
        private String playersFromTeam;
        private String teamsPosition;
        private String events;
    }

    @Data
    public static class Id {
        private int leagueSpain;
        private String leagueSpainName;
        private String season;
    }
}
