package com.bvb.match.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    boolean existsByHomeTeamIdAndAwayTeamIdAndMatchDate(Long homeTeamId, Long awayTeamId, Date matchDate);
    List<Match> findAllMatchesBySeasonId(Long seasonId);
    List<Match> findAllMatchesBySeasonIdAndTournamentId(Long seasonId, Long tournamentId);
}
