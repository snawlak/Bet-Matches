package com.bet.matches.api.core.team;

import com.bet.matches.api.core.MatchDto;
import com.bet.matches.api.core.TeamDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    public List<TeamDto> getAllTeams() {
        return teamRepository.getAllTeams().stream()
                .map(team -> modelMapper.map(team, TeamDto.class))
                .collect(Collectors.toList());
    }

    public TeamDto getTeam(final int id) {
        return teamRepository.getTeam(id)
                .map(team -> modelMapper.map(team, TeamDto.class))
                .orElseThrow(IllegalStateException::new);
    }

    public TeamDto addTeam(final TeamDto teamDto) {
        Team team = modelMapper.map(teamDto, Team.class);
        team = teamRepository.addOrUpdateTeam(team);
        return modelMapper.map(team, TeamDto.class);
    }

    @Transactional
    public TeamDto updateTeam(final int id, final TeamDto newTeam) {
        final Team team = teamRepository.getTeam(id)
                .orElseThrow(IllegalStateException::new);
        team.setName(newTeam.getName());
        team.setPlace(newTeam.getPlace());
        team.setIdRapidApi(newTeam.getIdRapidApi());
        team.setLogo(newTeam.getLogo());
        return modelMapper.map(team, TeamDto.class);
    }

    public boolean deleteTeam(final int id) {
        if (teamRepository.getTeam(id).isPresent()) {
            teamRepository.deleteTeam(id);
            return true;
        }
        return false;
    }
}
