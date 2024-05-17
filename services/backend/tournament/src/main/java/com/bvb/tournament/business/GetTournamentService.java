package com.bvb.tournament.business;

import com.bvb.tournament.persistence.Tournament;

import java.util.Optional;

public interface GetTournamentService {
    Optional<Tournament> getTournament(long tournamentId);
}
