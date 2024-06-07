CREATE TABLE user_account
(
    id              SERIAL  PRIMARY KEY,
    email           varchar(50) NOT NULL,
    username        varchar(20) NOT NULL,
    password        varchar(100) NOT NULL,
    enabled         BOOLEAN NOT NULL DEFAULT FALSE,
    account_locked  BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE role
(
    id          SERIAL  PRIMARY KEY,
    role_name   varchar(20) NOT NULL
);

CREATE TABLE user_account_roles
(
    roles_id    int NOT NULL,
    users_id    int NOT NULL,
    PRIMARY KEY (roles_id, users_id),
    CONSTRAINT fk_roles FOREIGN KEY (roles_id) REFERENCES role (id) ON DELETE CASCADE,
    CONSTRAINT fk_users FOREIGN KEY (users_id) REFERENCES user_account (id) ON DELETE CASCADE
);