package com.bvb.match.business;

import com.bvb.match.domain.GetAllMatchesResponse;

public interface GetAllMatchesService {
    GetAllMatchesResponse getAllMatchesBySeason(long seasonId);
    GetAllMatchesResponse getAllMatchesBySeasonAndTournament(long seasonId, long tournamentId);
}
