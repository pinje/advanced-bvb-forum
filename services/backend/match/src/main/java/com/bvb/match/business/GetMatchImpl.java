package com.bvb.match.business;

import com.bvb.match.persistence.Match;
import com.bvb.match.persistence.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetMatchImpl implements GetMatchService {
    private final MatchRepository matchRepository;

    @Override
    public Optional<Match> getMatch(long matchId) {
        if (!matchRepository.existsById(matchId)) {
            throw new RuntimeException("Match doesn't exist");
        }

        return matchRepository.findById(matchId);
    }
}
