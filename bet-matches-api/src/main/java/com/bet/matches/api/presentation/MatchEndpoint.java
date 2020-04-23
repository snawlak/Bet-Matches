package com.bet.matches.api.presentation;

import com.bet.matches.api.core.MatchDto;
import com.bet.matches.api.core.match.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("matches")
public class MatchEndpoint {

    private final MatchService matchService;

    @GetMapping
    public List<MatchDto> getMatches(/*@JsonDeserialize(using = OffsetDateTimeDeserializer.class)*/
            @RequestParam(required = false) final
            String matchDate,
            @RequestParam(required = false) final
            Integer matchDay) {
        if (matchDate != null) {
            return matchService.getAllMatchesFromDate(OffsetDateTime.parse(matchDate));
        }
        if (matchDay != null) {
            return matchService.getAllMatchesFromMatchDay(matchDay);
        }
        return matchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public MatchDto getMatch(@PathVariable final int id) {
        return matchService.getMatch(id);
    }

    @PostMapping
    public MatchDto addMatch(@RequestBody final MatchDto matchDto) {
        return matchService.addMatch(matchDto);
    }

    @PutMapping("/{id}")
    public MatchDto updateMatch(@PathVariable final int id, @RequestBody final MatchDto matchDto) {
        return matchService.updateMatch(id, matchDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteMatch(@PathVariable final int id) {
        final boolean isDeleted = matchService.deleteMatch(id);
        if (isDeleted) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
