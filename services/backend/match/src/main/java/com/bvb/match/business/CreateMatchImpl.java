package com.bvb.match.business;

import com.bvb.match.business.exception.MatchAlreadyExistsException;
import com.bvb.match.domain.CreateMatchRequest;
import com.bvb.match.persistence.Match;
import com.bvb.match.persistence.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
public class CreateMatchImpl implements CreateMatchService {
    private final MatchRepository matchRepository;

    @Transactional
    @Override
    public void createMatch(CreateMatchRequest request) {
        Date matchDate = new Date(request.getMatchDate());
        if(matchRepository.existsByHomeTeamIdAndAwayTeamIdAndMatchDate(
                request.getHomeTeamId(),
                request.getAwayTeamId(),
                matchDate
        )) {
            throw new MatchAlreadyExistsException();
        }

        var match = Match.builder()
                        .matchDate(matchDate)
                        .seasonId(request.getSeasonId())
                        .tournamentId(request.getTournamentId())
                        .homeTeamId(request.getHomeTeamId())
                        .awayTeamId(request.getAwayTeamId())
                        .homeTeamScore(request.getHomeTeamScore())
                        .awayTeamScore(request.getAwayTeamScore())
                        .build();

        matchRepository.save(match);
    }
}
