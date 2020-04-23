package com.bet.matches.api.core.common.calculation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "bet.matches.points")
public class Points {

    private int extra;
    private int betCorrectly;
    private int betWinningTeamOrDraw;

}
