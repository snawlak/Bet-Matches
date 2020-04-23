package com.bet.matches.api.presentation;

import com.bet.matches.api.core.LeagueDto;
import com.bet.matches.api.core.league.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("leagues")
public class LeagueEndpoint {

    private final LeagueService leagueService;

    @GetMapping
    public List<LeagueDto> getAllLeagues() {
        return leagueService.getAllLeagues();
    }

    @GetMapping("/{id}")
    public LeagueDto getLeague(@PathVariable final int id) {
        return leagueService.getLeague(id);
    }

    @GetMapping("/users/{idUser}")
    public List<LeagueDto> getAllLeaguesOfUser(@PathVariable final UUID idUser) {
        return leagueService.getAllLeaguesOfUser(idUser);
    }

    @PostMapping
    public LeagueDto addLeague(@RequestBody final LeagueDto leagueDto) {
        return leagueService.addLeague(leagueDto);
    }

    @PostMapping("/{idLeague}/users/{idUser}")
    public LeagueDto addUserToLeague(@PathVariable final int idLeague, @PathVariable final UUID idUser) {
        return leagueService.addUserToLeague(idLeague, idUser);
    }

    @PutMapping("/{id}")
    public LeagueDto updateLeague(@PathVariable final int id, @RequestBody final LeagueDto leagueDto) {
        return leagueService.updateLeague(id, leagueDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteLeague(@PathVariable final int id) {
        final boolean isDeleted = leagueService.deleteLeague(id);
        if (isDeleted) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
