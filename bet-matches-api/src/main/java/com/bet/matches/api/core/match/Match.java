package com.bet.matches.api.core.match;

import com.bet.matches.api.core.stadium.Stadium;
import com.bet.matches.api.core.team.Team;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@Table(name = "matches")
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_rapid_api", nullable = false)
    private int idRapidApi;

    @JoinColumn(name = "id_team_first", nullable = false)
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Team teamFirst;

    @JoinColumn(name = "id_team_second", nullable = false)
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Team teamSecond;

    @Column(name = "match_date", nullable = false)
    private OffsetDateTime matchDate;

    @Column(name = "match_day", nullable = false)
    private int matchDay;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status;

    @Column(name = "team_first_score", nullable = false)
    @JsonProperty("team_first_score")
    private int teamFirstScore;

    @Column(name = "team_second_score", nullable = false)
    @JsonProperty("team_second_score")
    private int teamSecondScore;

    @JoinColumn(name = "id_stadium", nullable = false)
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Stadium stadium;
}
