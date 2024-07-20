CREATE TABLE scm_db.user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    username   VARCHAR(255)          NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    bio        VARCHAR(255)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE scm_db.user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE scm_db.user
    ADD CONSTRAINT uc_user_username UNIQUE (username);