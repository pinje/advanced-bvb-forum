package com.bvb.match.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface MatchRepository extends JpaRepository<Match, Long> {
    boolean existsByHomeTeamIdAndAwayTeamIdAndMatchDate(Long homeTeamId, Long awayTeamId, Date matchDate);
}
