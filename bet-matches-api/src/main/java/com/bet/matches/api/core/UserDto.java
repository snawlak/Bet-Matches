package com.bet.matches.api.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.UUID;

@Data
public class UserDto {

    @ApiModelProperty(example = "123")
    private UUID id;

    @ApiModelProperty(example = "superBob")
    private String username;

    @ApiModelProperty(example = "superBob@gmail.com")
    private String email;

    @ApiModelProperty(example = "12")
    private int points;
}
