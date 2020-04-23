package com.bet.matches.api.core.player;

import com.bet.matches.api.core.team.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "players")
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_rapid_api", nullable = false)
    private int idRapidApi;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @JoinColumn(name = "id_team", nullable = false)
    @ManyToOne(cascade = CascadeType.MERGE)
    private Team team;

    @Column(nullable = false)
    private int goals;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PlayerPosition position;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private int age;
}

