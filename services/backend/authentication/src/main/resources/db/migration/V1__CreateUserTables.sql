CREATE TABLE user_account
(
    id              SERIAL  PRIMARY KEY,
    email           varchar(50) NOT NULL,
    username        varchar(20) NOT NULL,
    password        varchar(100) NOT NULL,
    enabled         BOOLEAN NOT NULL DEFAULT FALSE,
    account_locked  BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE user_account_roles
(
    roles_id    int NOT NULL,
    users_id    int NOT NULL
);

CREATE TABLE role
(
    id          SERIAL  PRIMARY KEY,
    role_name   varchar(20) NOT NULL
);