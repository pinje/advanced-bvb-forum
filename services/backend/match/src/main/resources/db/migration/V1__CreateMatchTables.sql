CREATE TABLE match
(
    id                  SERIAL  PRIMARY KEY,
    match_date                DATE NOT NULL,
    season_id           INTEGER NOT NULL,
    tournament_id       INTEGER NOT NULL,
    home_team_id        INTEGER NOT NULL,
    away_team_id        INTEGER NOT NULL,
    home_team_score     varchar(3) NOT NULL,
    away_team_score     varchar(3) NOT NULL
);