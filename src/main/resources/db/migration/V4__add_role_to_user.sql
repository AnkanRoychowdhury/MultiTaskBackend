ALTER TABLE scm_db.user
    ADD `role` ENUM('USER','ADMIN','GUEST') NOT NULL;