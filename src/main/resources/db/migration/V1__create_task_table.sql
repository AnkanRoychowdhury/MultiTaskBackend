CREATE TABLE scm_db.task
(
    id            BIGINT AUTO_INCREMENT             NOT NULL,
    title         VARCHAR(255)                      NOT NULL,
    description   VARCHAR(255)                      NOT NULL,
    task_status   ENUM('COMPLETED','INPROGRESS','ARCHIVED', 'DELETED', 'HOLD') DEFAULT 'INPROGRESS' NOT NULL,
    start_date    datetime                          NULL,
    end_date      datetime                          NULL,
    due_date      datetime                          NULL,
    created_at    datetime                          NOT NULL,
    updated_at    datetime                          NOT NULL,
    CONSTRAINT pk_task PRIMARY KEY (id)
);