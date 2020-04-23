package com.bet.matches.api.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamDto {

    @ApiModelProperty(example = "123")
    private int id;

    @JsonProperty("id_rapid_api")
    @ApiModelProperty(example = "123")
    private int idRapidApi;

    @ApiModelProperty(example = "Tottenham Hotspur")
    private String name;

    @ApiModelProperty(example = "1")
    private int place;

    @ApiModelProperty(example = "https://logo.pl/123")
    private String logo;
}
