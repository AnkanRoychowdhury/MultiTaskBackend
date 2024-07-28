ALTER TABLE scm_db.task
    ADD assignor_id BIGINT NULL;

ALTER TABLE scm_db.task
    ADD CONSTRAINT FK_TASK_ON_ASSIGNOR FOREIGN KEY (assignor_id) REFERENCES scm_db.user (id);