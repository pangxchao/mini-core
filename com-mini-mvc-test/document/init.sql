CREATE TABLE mini_test.common_init
(
    init_id      INT          NOT NULL COMMENT '参数键',
    init_value   VARCHAR(255) NOT NULL COMMENT '参数值',
    init_remarks VARCHAR(255) NOT NULL COMMENT '参数说明',
    PRIMARY KEY (init_id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT '参数-配置信息';

CREATE TABLE mini_test.common_region
(
    region_id        INT          NOT NULL COMMENT '地区码/地区ID',
    region_name      VARCHAR(255) NOT NULL COMMENT '地区名称',
    region_id_uri    VARCHAR(255) NOT NULL COMMENT '地区ID列表',
    region_name_uri  VARCHAR(255) NOT NULL COMMENT '地区名称列表',
    region_region_id INT DEFAULT NULL COMMENT '上级地区ID',
    PRIMARY KEY (region_id),
    KEY IX_region_id_uri (region_id_uri),
    KEY IX_region_region_id (region_region_id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT '地区信息表';

CREATE TABLE mini_test.user_info
(
    user_id          BIGINT       NOT NULL COMMENT '用户ID',
    user_name        VARCHAR(100) NOT NULL COMMENT '用户名',
    user_password    VARCHAR(32)  NOT NULL COMMENT 'MD5(密码)',
    user_phone       VARCHAR(20)  NOT NULL COMMENT '用户手机号',
    user_phone_auth  TINYINT      NOT NULL COMMENT '0-未认证，1-已谁',
    user_full_name   VARCHAR(20)           DEFAULT NULL COMMENT '用户姓名',
    user_email       VARCHAR(100)          DEFAULT NULL COMMENT '用户邮箱地址',
    user_email_auth  TINYINT      NOT NULL DEFAULT 0 COMMENT '0-未认证，1-已认证',
    user_head_url    VARCHAR(100)          DEFAULT NULL COMMENT '用户头像地址',
    user_region_id   INT                   DEFAULT NULL COMMENT '用户所属地区ID',
    user_create_time DATETIME     NOT NULL COMMENT '用户注册时间',
    PRIMARY KEY (user_id),
    KEY FK_user_region_id (user_region_id),
    CONSTRAINT FK_user_region_id FOREIGN KEY (user_region_id) REFERENCES common_region (region_id) ON DELETE SET NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户信息表';