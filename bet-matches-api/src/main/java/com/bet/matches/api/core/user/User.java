package com.bet.matches.api.core.user;

import com.bet.matches.api.core.UserRequestBodyDto;
import com.bet.matches.api.core.league.League;
import com.bet.matches.api.core.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PROTECTED;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class User implements Serializable {

    @Id
    private UUID id;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private boolean admin;

    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private List<League> leagues;

    public User(final UserRequestBodyDto userRequestBodyDto) {
        this.id = randomUUID();
        this.username = userRequestBodyDto.getUsername();
        this.email = userRequestBodyDto.getEmail();
        // TODO: Bcrypt security
        this.password = userRequestBodyDto.getPassword();
        this.points = 0;
        this.admin = false;
    }

    public void addLeague(final League league) {
        if (leagues == null) {
            leagues = new ArrayList<>();
        }
        leagues.add(league);
    }
}
