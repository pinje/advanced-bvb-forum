package com.bvb.match.business;

import com.bvb.match.domain.GetAllMatchesResponse;
import com.bvb.match.persistence.Match;
import com.bvb.match.persistence.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllMatchesImpl implements GetAllMatchesService {
    private final MatchRepository matchRepository;

    @Override
    public GetAllMatchesResponse getAllMatchesBySeason(long seasonId) {
        List<Match> matches = matchRepository.findAllMatchesBySeasonId(seasonId);
        return new GetAllMatchesResponse(matches);
    }

    @Override
    public GetAllMatchesResponse getAllMatchesBySeasonAndTournament(long seasonId, long tournamentId) {
        List<Match> matches = matchRepository.findAllMatchesBySeasonIdAndTournamentId(seasonId, tournamentId);
        return new GetAllMatchesResponse(matches);
    }
}
