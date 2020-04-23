package com.bet.matches.api.core.match;

import com.bet.matches.api.core.MatchDto;
import com.bet.matches.api.core.stadium.Stadium;
import com.bet.matches.api.core.stadium.StadiumRepository;
import com.bet.matches.api.core.team.Team;
import com.bet.matches.api.core.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MatchValidator {

    private final TeamRepository teamRepository;
    private final StadiumRepository stadiumRepository;

    public boolean isValid(final MatchDto matchDto) {
        final int idTeamFirst = matchDto.getTeamFirst().getId();
        final int idTeamSecond = matchDto.getTeamSecond().getId();
        final int idStadium = matchDto.getStadium().getId();
        final Optional<Team> teamFirst = teamRepository.getTeam(idTeamFirst);
        final Optional<Team> teamSecond = teamRepository.getTeam(idTeamSecond);
        final Optional<Stadium> stadium = stadiumRepository.getStadium(idStadium);

        return teamFirst.isPresent() && teamSecond.isPresent() && stadium.isPresent();
    }

}
