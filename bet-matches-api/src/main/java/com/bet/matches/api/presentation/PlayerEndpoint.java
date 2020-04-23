package com.bet.matches.api.presentation;

import com.bet.matches.api.core.PlayerDto;
import com.bet.matches.api.core.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("players")
public class PlayerEndpoint {

    private final PlayerService playerService;

    @GetMapping()
    public List<PlayerDto> getAllPlayers(@RequestParam(required = false) final Integer rapidApiId) {
        if (rapidApiId != null) {
            return playerService.getPlayerByRapidApiId(rapidApiId);
        }
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public PlayerDto getPlayer(@PathVariable final int id) {
        return playerService.getPlayer(id);
    }

    @PostMapping
    public PlayerDto addPlayer(@RequestBody final PlayerDto playerDto) {
        return playerService.addPlayer(playerDto);
    }

    @PutMapping("/{id}")
    public PlayerDto updatePlayer(@PathVariable final int id, @RequestBody final PlayerDto playerDto) {
        return playerService.updatePlayer(id, playerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deletePlayer(@PathVariable final int id) {
        final boolean isDeleted = playerService.deletePlayer(id);
        if (isDeleted) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
