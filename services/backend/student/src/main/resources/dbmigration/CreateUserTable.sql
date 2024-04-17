CREATE TABLE user
(
    id  int  NOT NULL    AUTO_INCREMENT,
    email       varchar(50) NOT NULL,
    username    varchar(20) NOT NULL,
    password    varchar(40) NOT NULL,
    role        varchar(6)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username),
    UNIQUE (email)
);
