package com.bvb.match.business;

import com.bvb.match.persistence.Match;
import com.bvb.match.persistence.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteMatchImpl implements DeleteMatchService {
    private final MatchRepository matchRepository;

    @Override
    public void deleteMatch(long matchId) {
        Match match = matchRepository.findById(matchId).stream().findFirst().orElse(null);

        if (match == null) {
            throw new RuntimeException("Match doesn't exist");
        }

        matchRepository.deleteById(matchId);
    }
}
