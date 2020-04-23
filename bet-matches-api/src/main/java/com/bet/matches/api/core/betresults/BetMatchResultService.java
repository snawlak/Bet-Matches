package com.bet.matches.api.core.betresults;

import com.bet.matches.api.core.match.Match;
import com.bet.matches.api.core.match.MatchRepository;
import com.bet.matches.api.core.user.User;
import com.bet.matches.api.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BetMatchResultService {

    private final BetMatchResultRepository betMatchResultRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    public BetMatchResult getBetMatchResult(final BetMatchResultId id) {
        return betMatchResultRepository.getBetMatchResult(id).orElseThrow(RuntimeException::new);
    }

    public List<BetMatchResult> getAllBetMatchResultsOfSpecificUser(final UUID userId) {
        final Optional<User> user = this.userRepository.getUser(userId);
        if (user.isPresent()) {
            return betMatchResultRepository.getAllBetMatchResultsOfSpecificUser(userId);
        }
        throw new RuntimeException("User with id: " + userId + "does not exists");
    }

    public List<BetMatchResult> getAllBetMatchResultsOfSpecificMatch(final int matchId) {
        final Optional<Match> match = matchRepository.getMatch(matchId);
        if (match.isPresent()) {
            return betMatchResultRepository.getAllBetMatchResultsOfSpecificMatch(matchId);
        }
        throw new RuntimeException("Match with id: " + matchId + "does not exists");
    }

    public BetMatchResult addBetMatchResult(final BetMatchResult betMatchResult) {
        final BetMatchResultId betMatchResultId = betMatchResult.getBetMatchResultId();
        final UUID userId = betMatchResultId.getUser();
        final int matchId = betMatchResultId.getMatch();

        final Optional<User> user = this.userRepository.getUser(userId);
        final Optional<Match> match = matchRepository.getMatch(matchId);

        if (user.isPresent() && match.isPresent()) {
            return betMatchResultRepository.addOrUpdateBetMatchResult(betMatchResult);
        }
        throw new RuntimeException("Match or User does not exists");
    }

    public BetMatchResult updateBetMatchResult(final BetMatchResultId id, final BetMatchResult newBetMatchResult) {
        return betMatchResultRepository.getBetMatchResult(id)
                .map(betMatchResult -> {
                    betMatchResult.setTeamFirstScore(newBetMatchResult.getTeamFirstScore());
                    betMatchResult.setTeamSecondScore(newBetMatchResult.getTeamSecondScore());
                    return betMatchResultRepository.addOrUpdateBetMatchResult(betMatchResult);
                })
                .orElseGet(() -> {
                    newBetMatchResult.setBetMatchResultId(id);
                    return betMatchResultRepository.addOrUpdateBetMatchResult(newBetMatchResult);
                });
    }

    public boolean deleteBetMatchResult(final BetMatchResultId id) {
        if (betMatchResultRepository.getBetMatchResult(id).isPresent()) {
            betMatchResultRepository.deleteBetMatchResult(id);
            return true;
        }
        return false;
    }

}
