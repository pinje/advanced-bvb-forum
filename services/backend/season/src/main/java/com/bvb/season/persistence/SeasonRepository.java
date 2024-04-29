package com.bvb.season.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
    boolean existsByStartYear(String startYear);
    boolean existsByEndYear(String endYear);
}
