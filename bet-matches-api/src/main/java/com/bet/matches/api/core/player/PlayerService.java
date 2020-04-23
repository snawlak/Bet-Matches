package com.bet.matches.api.core.player;

import com.bet.matches.api.core.PlayerDto;
import com.bet.matches.api.core.team.Team;
import com.bet.matches.api.core.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    public List<PlayerDto> getAllPlayers() {
        return playerRepository.getAllPlayers().stream()
                .map(player -> modelMapper.map(player, PlayerDto.class))
                .collect(Collectors.toList());
    }

    public PlayerDto getPlayer(final int id) {
        return playerRepository.getPlayer(id)
                .map(player -> modelMapper.map(player, PlayerDto.class))
                .orElseThrow(IllegalStateException::new);
    }

    public List<PlayerDto> getPlayerByRapidApiId(final int rapidApiId) {
        return playerRepository.getPlayersByRapidApiId(rapidApiId).stream()
                .map(player -> modelMapper.map(player, PlayerDto.class))
                .collect(Collectors.toList());
    }

    public PlayerDto addPlayer(final PlayerDto playerDto) {
        Player player = modelMapper.map(playerDto, Player.class);
        player = playerRepository.addOrUpdatePlayer(player);
        return modelMapper.map(player, PlayerDto.class);
    }

    @Transactional
    public PlayerDto updatePlayer(final int id, final PlayerDto newPlayer) {
        final Player player = playerRepository.getPlayer(id)
                .orElseThrow(IllegalStateException::new);

        player.setFirstName(newPlayer.getFirstName());
        player.setLastName(newPlayer.getLastName());
        player.setGoals(newPlayer.getGoals());
        player.setTeam(newPlayer.getTeam());
        player.setAge(newPlayer.getAge());
        player.setNationality(newPlayer.getNationality());
        player.setPosition(newPlayer.getPosition());

        return modelMapper.map(player, PlayerDto.class);
    }

    public boolean deletePlayer(final int id) {
        if (playerRepository.getPlayer(id).isPresent()) {
            playerRepository.deletePlayer(id);
            return true;
        }
        return false;
    }
}
