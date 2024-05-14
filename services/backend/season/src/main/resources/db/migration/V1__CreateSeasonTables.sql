CREATE TABLE season
(
    id              SERIAL  PRIMARY KEY,
    start_year       varchar(4) NOT NULL,
    end_year         varchar(4) NOT NULL
);

INSERT INTO season (start_year, end_year) VALUES ('2023', '2024');