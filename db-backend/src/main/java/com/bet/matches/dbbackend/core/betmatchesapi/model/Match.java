package com.bet.matches.dbbackend.core.betmatchesapi.model;

import com.bet.matches.dbbackend.core.OffsetDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    private int id;

    @JsonProperty("id_rapid_api")
    private int idRapidApi;

    @JsonProperty("team_first")
    private Team teamFirst;

    @JsonProperty("team_second")
    private Team teamSecond;

    @JsonProperty("match_date")
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    private OffsetDateTime matchDate;

    @JsonProperty("match_day")
    private int matchDay;

    private String status;

    @JsonProperty("team_first_score")
    private int teamFirstScore;

    @JsonProperty("team_second_score")
    private int teamSecondScore;

    private Stadium stadium;


}
