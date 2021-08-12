CREATE DATABASE IF NOT EXISTS mini_test DEFAULT CHARACTER SET utf8mb4;

USE mini_test;

-- region_info 
CREATE TABLE region_info
(
    region_id        BIGINT       NOT NULL,
    region_name      VARCHAR(100) NOT NULL,
    region_parent_id BIGINT,
    PRIMARY KEY (region_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='';

-- role_info 
CREATE TABLE role_info
(
    role_id   BIGINT      NOT NULL,
    role_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (role_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='';

-- text_info 
CREATE TABLE text_info
(
    text_id      BIGINT       NOT NULL,
    text_title   VARCHAR(100) NOT NULL,
    text_content TEXT         NOT NULL,
    PRIMARY KEY (text_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='';

-- user_info 
CREATE TABLE user_info
(
    user_id          BIGINT      NOT NULL,
    user_name        VARCHAR(20) NOT NULL,
    user_full_name   VARCHAR(20),
    user_email       VARCHAR(50),
    user_age         INT         NOT NULL DEFAULT 0,
    user_region_id   BIGINT,
    user_create_time DATETIME    NOT NULL,
    PRIMARY KEY (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='';

-- user_role 
CREATE TABLE user_role
(
    user_role_id      bigint not null,
    user_role_user_id BIGINT NOT NULL,
    user_role_role_id BIGINT NOT NULL,
    PRIMARY KEY (user_role_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='';


-- region_info 
ALTER TABLE region_info
    ADD CONSTRAINT FK_region_parent_id FOREIGN KEY (region_parent_id)
        REFERENCES region_info (region_id) ON DELETE SET NULL;

-- user_info 
ALTER TABLE user_info
    ADD CONSTRAINT FK_user_region_id FOREIGN KEY (user_region_id)
        REFERENCES region_info (region_id) ON DELETE SET NULL;

-- user_role 
ALTER TABLE user_role
    ADD CONSTRAINT FK_user_role_user_id FOREIGN KEY (user_role_user_id)
        REFERENCES user_info (user_id) ON DELETE CASCADE;

ALTER TABLE user_role
    ADD CONSTRAINT FK_user_role_role_id FOREIGN KEY (user_role_role_id)
        REFERENCES role_info (role_id) ON DELETE CASCADE;

alter table user_info
    add column user_gender tinyint not null default 0 after user_age;