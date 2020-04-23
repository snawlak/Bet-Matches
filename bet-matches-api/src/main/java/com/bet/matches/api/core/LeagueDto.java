package com.bet.matches.api.core;

import com.bet.matches.api.core.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LeagueDto {
    @ApiModelProperty(example = "123")
    private int id;

    @ApiModelProperty(example = "Super Liga")
    private String name;

    @ApiModelProperty(example = "Best League in the World")
    private String description;

    @ApiModelProperty(example = "123")
    private List<User> users;
}
