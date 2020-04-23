package com.bet.matches.api.core;

import com.bet.matches.api.core.match.MatchStatus;
import com.bet.matches.api.core.stadium.Stadium;
import com.bet.matches.api.core.team.Team;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class MatchDto {

    @ApiModelProperty(example = "123")
    private int id;

    @JsonProperty("id_rapid_api")
    @ApiModelProperty(example = "123")
    private int idRapidApi;

    @JsonProperty("team_first")
    @ApiModelProperty(example = "123")
    private Team teamFirst;

    @JsonProperty("team_second")
    @ApiModelProperty(example = "123")
    private Team teamSecond;

    @JsonProperty("match_date")
    @ApiModelProperty(example = "2020-01-27T10:07:06.407Z")
    private OffsetDateTime matchDate;

    @ApiModelProperty(example = "NOT_STARTED")
    private MatchStatus status;

    @JsonProperty("team_first_score")
    @ApiModelProperty(example = "2")
    private int teamFirstScore;

    @JsonProperty("team_second_score")
    @ApiModelProperty(example = "1")
    private int teamSecondScore;

    @JsonProperty("match_day")
    @ApiModelProperty(example = "1")
    private int matchDay;

    @ApiModelProperty(example = "123")
    private Stadium stadium;
}
