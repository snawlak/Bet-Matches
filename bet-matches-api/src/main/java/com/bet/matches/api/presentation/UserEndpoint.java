package com.bet.matches.api.presentation;

import com.bet.matches.api.core.UserDto;
import com.bet.matches.api.core.UserRequestBodyDto;
import com.bet.matches.api.core.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserEndpoint {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable final UUID id) {
        return userService.getUser(id);
    }

    @GetMapping("/leagues/{idLeague}")
    public List<UserDto> getAllUsersOfLeagues(@PathVariable final int idLeague) {
        return userService.getAllUsersInLeague(idLeague);
    }

    @PostMapping
    public UserDto addUser(@RequestBody final UserRequestBodyDto user) {
        return userService.addUser(user);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable final UUID id, @RequestBody final UserDto user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteUser(@PathVariable final UUID id) {
        final boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
