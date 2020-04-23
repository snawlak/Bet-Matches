package com.bet.matches.api.presentation;

import com.bet.matches.api.core.BetExtraDto;
import com.bet.matches.api.core.betextra.BetExtraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("bet-extra")
public class BetExtraEndpoint {

    private final BetExtraService betExtraService;

    @GetMapping("/{id}")
    public BetExtraDto getBetExtra(@PathVariable final UUID id) {
        return betExtraService.getBetExtra(id);
    }

    @PostMapping
    public BetExtraDto addBetExtra(@RequestBody final BetExtraDto betExtraDto) {
        return betExtraService.addBetExtra(betExtraDto);
    }

    @PutMapping("/{id}")
    public BetExtraDto updateBetExtra(@PathVariable final UUID id, @RequestBody final BetExtraDto playerTeam) {
        return betExtraService.updateBetExtra(id, playerTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteBetExtra(@PathVariable("id") final UUID id) {
        final boolean isDeleted = betExtraService.deleteBetExtra(id);
        if (isDeleted) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
