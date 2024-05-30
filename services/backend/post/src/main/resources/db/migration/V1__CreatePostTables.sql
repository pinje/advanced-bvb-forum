CREATE TABLE post
(
    id                  SERIAL  PRIMARY KEY,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id             INTEGER NOT NULL,
    match_id            INTEGER NOT NULL,
    vote                INTEGER NOT NULL,
    review_text         varchar(2000) NOT NULL
);