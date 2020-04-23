package com.bet.matches.api.core.user;

import com.bet.matches.api.core.UserDto;
import com.bet.matches.api.core.UserRequestBodyDto;
import com.bet.matches.api.core.betextra.BetExtra;
import com.bet.matches.api.core.league.League;
import com.bet.matches.api.core.league.LeagueRepository;
import com.bet.matches.api.core.betextra.BetExtraRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LeagueRepository leagueRepository;
    private final BetExtraRepository betExtraRepository;
    private final ModelMapper modelMapper;

    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map((User user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto getUser(final UUID id) {
        return userRepository.getUser(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(RuntimeException::new);
    }

    public List<UserDto> getAllUsersInLeague(final int idLeague) {
        final Optional<League> league = this.leagueRepository.getLeague(idLeague);

        if (league.isPresent()) {
            return league.get()
                    .getUsers()
                    .stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList());
        }

        throw new IllegalStateException("Cannot find league with given id: " + idLeague);
    }


    public UserDto addUser(final UserRequestBodyDto userRequestBodyDto) {
        final User user = new User(userRequestBodyDto);
        final User newUser = userRepository.addOrUpdateUser(user);
        final UserDto userDto = modelMapper.map(newUser, UserDto.class);

        final BetExtra betExtra = new BetExtra();
        betExtra.setUser(newUser);
        betExtraRepository.addOrUpdateBetExtra(betExtra);

        return userDto;
    }

    @Transactional
    public UserDto updateUser(final UUID id, final UserDto newUser) {
        final User user = userRepository.getUser(id)
                .orElseThrow(IllegalStateException::new);

        user.setPoints(newUser.getPoints());
        user.setEmail(newUser.getEmail());
        user.setUsername(newUser.getUsername());

        return modelMapper.map(user, UserDto.class);
    }

    public boolean deleteUser(final UUID id) {
        if (userRepository.getUser(id).isPresent()) {
            userRepository.deleteUser(id);
            betExtraRepository.deleteBetExtra(id);
            return true;
        }
        return false;
    }
}
