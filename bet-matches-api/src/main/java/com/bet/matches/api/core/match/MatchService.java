package com.bet.matches.api.core.match;

import com.bet.matches.api.core.MatchDto;
import com.bet.matches.api.core.common.event.OnEndOfMatchEvent;
import com.bet.matches.api.core.common.event.OnEndOfTournamentEvent;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchValidator matchValidator;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${bet.matches.tournament.number-of-matches}")
    private Integer numberOfMatchDays;

    public List<MatchDto> getAllMatches() {
        return matchRepository.getAllMatches().stream()
                .map(match -> modelMapper.map(match, MatchDto.class))
                .collect(Collectors.toList());
    }

    public List<MatchDto> getAllMatchesFromDate(final OffsetDateTime matchDate) {
        final OffsetDateTime startDate = matchDate.withHour(0).withMinute(0);
        final OffsetDateTime endDate = startDate.plusDays(1);
        return matchRepository.getAllMatchesFromDate(startDate, endDate).stream()
                .map(match -> modelMapper.map(match, MatchDto.class))
                .collect(Collectors.toList());
    }

    public List<MatchDto> getAllMatchesFromMatchDay(final int matchDay) {
        return matchRepository.getAllMatchesFromMatchDay(matchDay).stream()
                .map(match -> modelMapper.map(match, MatchDto.class))
                .collect(Collectors.toList());
    }

    public MatchDto getMatch(final int id) {
        return matchRepository.getMatch(id)
                .map(match -> modelMapper.map(match, MatchDto.class))
                .orElseThrow(IllegalStateException::new);
    }

    public MatchDto addMatch(final MatchDto matchDto) {
        if (matchValidator.isValid(matchDto)) {
            Match match = modelMapper.map(matchDto, Match.class);
            match = matchRepository.addOrUpdateMatch(match);
            return modelMapper.map(match, MatchDto.class);
        }
        throw new IllegalStateException("Validation of Match went wrong");
    }

    @Transactional
    public MatchDto updateMatch(final int id, final MatchDto newMatchDto) {
        if (matchValidator.isValid(newMatchDto)) {
            final Match match = matchRepository.getMatch(id)
                    .orElseThrow(RuntimeException::new);
            final MatchStatus previousStatus = match.getStatus();
            final MatchStatus newStatus = newMatchDto.getStatus();

            match.setTeamFirst(newMatchDto.getTeamFirst());
            match.setTeamSecond(newMatchDto.getTeamSecond());
            match.setTeamFirstScore(newMatchDto.getTeamFirstScore());
            match.setTeamSecondScore(newMatchDto.getTeamSecondScore());
            match.setStadium(newMatchDto.getStadium());
            match.setMatchDate(newMatchDto.getMatchDate());
            match.setStatus(newStatus);
            match.setMatchDay(newMatchDto.getMatchDay());
            match.setIdRapidApi(newMatchDto.getIdRapidApi());

            if (isMatchFinished(previousStatus, newStatus)) {
                applicationEventPublisher.publishEvent(new OnEndOfMatchEvent(this, match));
            }

            if (isLastMatchDay(match)) {
                applicationEventPublisher.publishEvent(new OnEndOfTournamentEvent(this));
            }

            return modelMapper.map(match, MatchDto.class);
        }

        throw new IllegalStateException("Validation of Match went wrong");
    }

    private boolean isLastMatchDay(final Match match) {
        if (match.getMatchDay() == numberOfMatchDays) {
            final Optional<Match> lastMatch = matchRepository.getAllMatches().stream()
                    .filter(theMatch -> theMatch.getMatchDay() == numberOfMatchDays)
                    .filter(theMatch -> MatchStatus.FINISHED != theMatch.getStatus())
                    .findAny();
            return lastMatch.isEmpty();
        }
        return false;
    }

    public boolean deleteMatch(final int id) {
        if (matchRepository.getMatch(id).isPresent()) {
            matchRepository.deleteMatch(id);
            return true;
        }
        return false;
    }

    private static boolean isMatchFinished(final MatchStatus previousStatus, final MatchStatus newStatus) {
        return (MatchStatus.FINISHED == newStatus) && (previousStatus != newStatus);
    }
}
