package com.bet.matches.api.core.betextra;

import com.bet.matches.api.core.BetExtraDto;
import com.bet.matches.api.core.user.User;
import com.bet.matches.api.core.player.Player;
import com.bet.matches.api.core.team.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "bet_extra")
@NoArgsConstructor
@AllArgsConstructor
public class BetExtra {

    @Id
    @JsonIgnore
    @ApiModelProperty(example = "123")
    @Column(name = "id_user", updatable = false, nullable = false)
    private UUID id;

    @MapsId
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @ApiModelProperty(example = "123")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_player")
    @ApiModelProperty(example = "123")
    private Player player;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_team")
    @ApiModelProperty(example = "123")
    private Team team;

    BetExtra(final BetExtraDto betExtraDto) {
        this.id = betExtraDto.getUser().getId();
        this.user = betExtraDto.getUser();
        this.player = betExtraDto.getPlayer();
        this.team = betExtraDto.getTeam();
    }

}
