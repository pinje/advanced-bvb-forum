package com.bvb.tournament.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    boolean existsByTournamentName(String tournamentName);
}
