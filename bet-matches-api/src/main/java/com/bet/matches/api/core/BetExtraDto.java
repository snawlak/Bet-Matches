package com.bet.matches.api.core;

import com.bet.matches.api.core.player.Player;
import com.bet.matches.api.core.team.Team;
import com.bet.matches.api.core.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class BetExtraDto {

    @ApiModelProperty(example = "123")
    private User user;

    @ApiModelProperty(example = "123")
    private Player player;

    @ApiModelProperty(example = "123")
    private Team team;
}
