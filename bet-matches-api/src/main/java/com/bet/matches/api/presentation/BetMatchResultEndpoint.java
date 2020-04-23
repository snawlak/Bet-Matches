package com.bet.matches.api.presentation;

import com.bet.matches.api.core.betresults.BetMatchResult;
import com.bet.matches.api.core.betresults.BetMatchResultId;
import com.bet.matches.api.core.betresults.BetMatchResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("bets")
public class BetMatchResultEndpoint {
    private final BetMatchResultService betMatchResultService;

    @GetMapping("/users/{idUser}/matches/{idMatch}")
    public BetMatchResult getBetMatchResult(@PathVariable final UUID idUser, @PathVariable final int idMatch) {
        final BetMatchResultId id = new BetMatchResultId(idUser, idMatch);
        return betMatchResultService.getBetMatchResult(id);
    }

    @GetMapping("/users/{idUser}/matches")
    public List<BetMatchResult> getAllBetMatchResultsOfSpecificUser(@PathVariable final UUID idUser) {
        return betMatchResultService.getAllBetMatchResultsOfSpecificUser(idUser);
    }

    @GetMapping("users/matches/{idMatch}")
    public List<BetMatchResult> getAllBetMatchResultsOfSpecificMatch(@PathVariable final int idMatch) {
        return betMatchResultService.getAllBetMatchResultsOfSpecificMatch(idMatch);
    }

    @PostMapping
    public BetMatchResult addBetMatchResult(@RequestBody final BetMatchResult betMatchResult) {
        return betMatchResultService.addBetMatchResult(betMatchResult);
    }

    @PutMapping("/users/{idUser}/matches/{idMatch}")
    public BetMatchResult updateBetMatchResult(@PathVariable final UUID idUser,
                                               @PathVariable final int idMatch,
                                               @RequestBody final BetMatchResult betMatchResult) {
        final BetMatchResultId id = new BetMatchResultId(idUser, idMatch);
        return betMatchResultService.updateBetMatchResult(id, betMatchResult);
    }

    @DeleteMapping("/users/{idUser}/matches/{idMatch}")
    public ResponseEntity<Integer> deleteBetMatchResult(@PathVariable final UUID idUser,
                                                        @PathVariable final int idMatch) {
        final BetMatchResultId id = new BetMatchResultId(idUser, idMatch);
        final boolean isDeleted = betMatchResultService.deleteBetMatchResult(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
