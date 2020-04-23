package com.bet.matches.api.core.betextra;

import com.bet.matches.api.core.BetExtraDto;
import com.bet.matches.api.core.player.Player;
import com.bet.matches.api.core.player.PlayerRepository;
import com.bet.matches.api.core.team.Team;
import com.bet.matches.api.core.team.TeamRepository;
import com.bet.matches.api.core.user.User;
import com.bet.matches.api.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BetExtraService {

    private final BetExtraRepository betExtraRepository;
    private final ModelMapper modelMapper;

    public BetExtraDto getBetExtra(final UUID id) {
        return betExtraRepository.getBetExtra(id)
                .map(betExtra -> modelMapper.map(betExtra, BetExtraDto.class))
                .orElseThrow(RuntimeException::new);
    }

    public BetExtraDto addBetExtra(final BetExtraDto betExtraDto) {
        BetExtra betExtra = new BetExtra(betExtraDto);
        betExtra = betExtraRepository.addOrUpdateBetExtra(betExtra);
        return modelMapper.map(betExtra, BetExtraDto.class);
    }

    @Transactional
    public BetExtraDto updateBetExtra(final UUID id, final BetExtraDto newBetExtra) {
        final BetExtra betExtra = betExtraRepository.getBetExtra(id)
                .orElseThrow(IllegalStateException::new);

        betExtra.setPlayer(newBetExtra.getPlayer());
        betExtra.setTeam(newBetExtra.getTeam());

        return modelMapper.map(betExtra, BetExtraDto.class);
    }

    public boolean deleteBetExtra(final UUID id) {
        final Optional<BetExtra> betExtra = betExtraRepository.getBetExtra(id);
        if (betExtra.isPresent()) {
            final BetExtra playerTeam = betExtra.get();
            playerTeam.setPlayer(null);
            playerTeam.setTeam(null);
            betExtraRepository.addOrUpdateBetExtra(playerTeam);
            return true;
        }
        return false;
    }
}
