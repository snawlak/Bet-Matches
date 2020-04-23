package com.bet.matches.api.presentation;

import com.bet.matches.api.core.TeamDto;
import com.bet.matches.api.core.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("teams")
public class TeamEndpoint {

    private final TeamService teamService;

    @GetMapping
    public List<TeamDto> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable final int id) {
        return teamService.getTeam(id);
    }

    @PostMapping
    public TeamDto addTeam(@RequestBody final TeamDto teamDto) {
        return teamService.addTeam(teamDto);
    }

    @PutMapping("/{id}")
    public TeamDto updateTeam(@PathVariable final int id, @RequestBody final TeamDto teamDto) {
        return teamService.updateTeam(id, teamDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteTeam(@PathVariable final int id) {
        final boolean isDeleted = teamService.deleteTeam(id);
        if (isDeleted) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
