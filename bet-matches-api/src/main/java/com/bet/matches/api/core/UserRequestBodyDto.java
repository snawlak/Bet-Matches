package com.bet.matches.api.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserRequestBodyDto {

    @Email
    @ApiModelProperty(example = "superBob@gmail.com")
    private String email;

    @ApiModelProperty(example = "superBob")
    private String username;

    @ApiModelProperty(example = "abcd123")
    private String password;
}