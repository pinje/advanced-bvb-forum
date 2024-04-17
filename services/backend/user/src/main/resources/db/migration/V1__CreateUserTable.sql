CREATE TABLE user_account
(
    id          SERIAL  PRIMARY KEY,
    email       varchar(50) NOT NULL,
    username    varchar(20) NOT NULL,
    password    varchar(40) NOT NULL,
    role        varchar(6)  NOT NULL,
    UNIQUE (username),
    UNIQUE (email)
);
