package com.bvb.tournament.business;

import com.bvb.tournament.domain.CreateTournamentRequest;

public interface CreateTournamentService {
    void createTournament(CreateTournamentRequest request);
}
