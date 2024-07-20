CREATE TABLE scm_db.user_assigned_tasks
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    assigned_tasks_id BIGINT NOT NULL,
    assignees_id      BIGINT NOT NULL,
    CONSTRAINT pk_user_assigned_tasks PRIMARY KEY (id)
);

ALTER TABLE scm_db.user_assigned_tasks
    ADD CONSTRAINT fk_useasstas_on_task FOREIGN KEY (assigned_tasks_id) REFERENCES scm_db.task (id);

ALTER TABLE scm_db.user_assigned_tasks
    ADD CONSTRAINT fk_useasstas_on_user FOREIGN KEY (assignees_id) REFERENCES scm_db.user (id);