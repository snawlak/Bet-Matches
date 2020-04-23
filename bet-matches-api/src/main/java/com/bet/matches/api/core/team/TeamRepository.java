package com.bet.matches.api.core.team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {

    List<Team> getAllTeams();

    Optional<Team> getTeam(final int id);

    Team getTeamOnPlace(final int place);

    Team addOrUpdateTeam(final Team team);

    void deleteTeam(final int id);

}
