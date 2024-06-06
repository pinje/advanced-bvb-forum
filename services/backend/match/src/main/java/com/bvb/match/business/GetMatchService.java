package com.bvb.match.business;

import com.bvb.match.persistence.Match;

import java.util.Optional;

public interface GetMatchService {
    Optional<Match> getMatch(long matchId);
}
