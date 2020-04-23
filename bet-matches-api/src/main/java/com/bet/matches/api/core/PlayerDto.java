package com.bet.matches.api.core;

import com.bet.matches.api.core.player.PlayerPosition;
import com.bet.matches.api.core.team.Team;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlayerDto {

    @ApiModelProperty(example = "123")
    private int id;

    @JsonProperty("first_name")
    @ApiModelProperty(example = "Michal")
    private String firstName;

    @JsonProperty("last_name")
    @ApiModelProperty(example = "Pazdan")
    private String lastName;

    @ApiModelProperty(example = "Polish")
    private String nationality;

    @ApiModelProperty(example = "30")
    private int age;

    @ApiModelProperty(example = "123")
    private Team team;

    @ApiModelProperty(example = "DEFENDER")
    private PlayerPosition position;

    @ApiModelProperty(example = "2")
    private int goals;

    @JsonProperty("id_rapid_api")
    @ApiModelProperty(example = "123")
    private int idRapidApi;
}
