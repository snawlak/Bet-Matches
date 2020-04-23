package com.bet.matches.api.core.betresults;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "bet_results")
@NoArgsConstructor
@AllArgsConstructor
public class BetMatchResult {

    @EmbeddedId
    private BetMatchResultId betMatchResultId;

    @Column(name = "team_first_score")
    @JsonProperty("team_first_score")
    @ApiModelProperty(example = "2")
    private int teamFirstScore;

    @Column(name = "team_second_score")
    @JsonProperty("team_second_score")
    @ApiModelProperty(example = "2")
    private int teamSecondScore;
}
