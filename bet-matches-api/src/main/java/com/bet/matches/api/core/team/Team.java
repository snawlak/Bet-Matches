package com.bet.matches.api.core.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "teams")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("id_rapid_api")
    @Column(nullable = false, unique = true, name = "id_rapid_api")
    private int idRapidApi;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int place;

    @Column(nullable = false)
    private String logo;
}
