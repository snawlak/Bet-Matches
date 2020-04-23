package com.bet.matches.api.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StadiumDto {

    @ApiModelProperty(example = "123")
    private int id;

    @ApiModelProperty(example = "PGE Narodowy")
    private String name;

    @ApiModelProperty(example = "Poland")
    private String country;

    @ApiModelProperty(example = "Warsaw")
    private String city;

    @ApiModelProperty(example = "55000")
    private int capacity;
}
