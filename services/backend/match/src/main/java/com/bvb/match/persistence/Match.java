package com.bvb.match.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "match")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "match_date")
    private Date matchDate;

    @Column(name = "season_id")
    private Long seasonId;

    @Column(name = "tournament_id")
    private Long tournamentId;

    @Column(name = "home_team_id")
    private Long homeTeamId;

    @Column(name = "away_team_id")
    private Long awayTeamId;

    @Column(name = "home_team_score")
    private String homeTeamScore;

    @Column(name = "away_team_score")
    private String awayTeamScore;
}
