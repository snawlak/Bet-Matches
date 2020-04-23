package com.bet.matches.api.core.league;

import com.bet.matches.api.core.LeagueDto;
import com.bet.matches.api.core.user.User;
import com.bet.matches.api.core.user.UserRepository;
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
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<LeagueDto> getAllLeagues() {
        return leagueRepository.getAllLeagues()
                .stream()
                .map(league -> modelMapper.map(league, LeagueDto.class))
                .collect(Collectors.toList());
    }

    public LeagueDto getLeague(final int id) {
        return leagueRepository.getLeague(id)
                .map(league -> modelMapper.map(league, LeagueDto.class))
                .orElseThrow(IllegalStateException::new);
    }

    public List<LeagueDto> getAllLeaguesOfUser(final UUID idUser) {
        final Optional<User> user = userRepository.getUser(idUser);
        if (user.isPresent()) {
            return user.get()
                    .getLeagues()
                    .stream()
                    .map(league -> modelMapper.map(league, LeagueDto.class))
                    .collect(Collectors.toList());
        }
        throw new IllegalStateException();
    }

    public LeagueDto addLeague(final LeagueDto leagueDto) {
        League league = modelMapper.map(leagueDto, League.class);
        league = leagueRepository.addOrUpdateLeague(league);
        return modelMapper.map(league, LeagueDto.class);
    }

    public LeagueDto addUserToLeague(final int idLeague, final UUID idUser) {
        final Optional<User> user = userRepository.getUser(idUser);
        if (user.isPresent()) {
            final User theUser = user.get();
            League league = leagueRepository.getLeague(idLeague)
                    .orElseThrow(RuntimeException::new);
            league.addUser(theUser);
            theUser.addLeague(league);
            league = leagueRepository.addOrUpdateLeague(league);
            return modelMapper.map(league, LeagueDto.class);
        }
        throw new IllegalStateException("User with id: {idUser} does not exists");
    }

    @Transactional
    public LeagueDto updateLeague(final int id, final LeagueDto newLeague) {
        final League league = leagueRepository.getLeague(id)
                .orElseThrow(IllegalStateException::new);

        league.setName(newLeague.getName());
        league.setDescription(newLeague.getDescription());

        return modelMapper.map(league, LeagueDto.class);
    }

    public boolean deleteLeague(final int id) {
        if (leagueRepository.getLeague(id).isPresent()) {
            leagueRepository.deleteLeague(id);
            return true;
        }
        return false;
    }
}
