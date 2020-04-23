package com.bet.matches.api.presentation;

import com.bet.matches.api.core.StadiumDto;
import com.bet.matches.api.core.stadium.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("stadiums")
public class StadiumEndpoint {

    private final StadiumService stadiumService;

    @GetMapping
    public List<StadiumDto> getStadiums() {
        return stadiumService.getAllStadiums();
    }

    @GetMapping("/{id}")
    public StadiumDto getStadium(@PathVariable final int id) {
        return stadiumService.getStadium(id);
    }

    @PostMapping
    public StadiumDto addStadium(@RequestBody final StadiumDto stadiumDto) {
        return stadiumService.addStadium(stadiumDto);
    }

    @PutMapping("/{id}")
    public StadiumDto updateStadium(@PathVariable final int id, @RequestBody final StadiumDto stadiumDto) {
        return stadiumService.updateStadium(id, stadiumDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteStadium(@PathVariable final int id) {
        final boolean isDeleted = stadiumService.deleteStadium(id);
        if (isDeleted) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
