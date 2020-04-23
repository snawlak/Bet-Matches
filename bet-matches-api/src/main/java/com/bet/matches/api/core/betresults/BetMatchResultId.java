package com.bet.matches.api.core.betresults;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BetMatchResultId implements Serializable {

    @Column(name = "id_user")
    @ApiModelProperty(example = "123")
    private UUID user;

    @Column(name = "id_match")
    @ApiModelProperty(example = "123")
    private int match;
}

