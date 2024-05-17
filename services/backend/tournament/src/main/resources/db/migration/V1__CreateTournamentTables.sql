CREATE TABLE tournament
(
    id                  SERIAL  PRIMARY KEY,
    tournament_name     varchar(30) NOT NULL,
    logo_id             varchar(50) NOT NULL,
    category            varchar(10) NOT NULL
);