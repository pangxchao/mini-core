CREATE DATABASE IF NOT EXISTS mini_test DEFAULT CHARACTER SET utf8mb4;
USE mini_test;

CREATE TABLE common_region
(
    region_id        int(11)                                                       NOT NULL COMMENT '地区码/地区ID',
    region_name      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地区名称',
    region_id_uri    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地区ID列表',
    region_name_uri  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地区名称列表',
    region_region_id int(11) DEFAULT NULL COMMENT '上级地区ID',
    PRIMARY KEY (region_id),
    KEY IX_region_id_uri (region_id_uri),
    KEY IX_region_region_id (region_region_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='地区信息表';

CREATE TABLE user_info
(
    user_id          bigint(20)                                                    NOT NULL COMMENT '用户ID',
    user_name        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
    user_password    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT 'MD5(密码)',
    user_phone       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '用户手机号',
    user_phone_auth  tinyint(4)                                                    NOT NULL COMMENT '0-未认证，1-已谁',
    user_full_name   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci           DEFAULT NULL COMMENT '用户姓名',
    user_email       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '用户邮箱地址',
    user_email_auth  tinyint(4)                                                    NOT NULL DEFAULT '0' COMMENT '0-未认证，1-已认证',
    user_head_url    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '用户头像地址',
    user_region_id   int(11)                                                                DEFAULT NULL COMMENT '用户所属地区ID',
    user_create_time datetime                                                      NOT NULL COMMENT '用户注册时间',
    PRIMARY KEY (user_id),
    KEY FK_user_region_id (user_region_id),
    CONSTRAINT FK_user_region_id FOREIGN KEY (user_region_id) REFERENCES common_region (region_id) ON DELETE SET NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户信息表';

