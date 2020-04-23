package com.bet.matches.dbbackend.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {

    private final RapidApiWeb rapidApiWeb;
    private final BetMatchesApiWeb betMatchesApiWeb;

    private static Consumer<HttpHeaders> toConsumerHeaders(final List<RapidApiWeb.Header> headers) {
        return httpHeaders -> headers
                .forEach(header -> httpHeaders
                        .add(header.getName(), header.getValue()));
    }

    @Bean
    public WebClient rapidApiWebClient() {
        return WebClient
                .builder()
                .baseUrl(rapidApiWeb.getBaseUrl())
                .defaultHeaders(toConsumerHeaders(rapidApiWeb.getHeaders()))
                .build();
    }

    @Bean
    public WebClient betMatchesWebClient() {
        return WebClient
                .builder()
                .baseUrl(betMatchesApiWeb.getBaseUrl())
                .build();
    }
}
