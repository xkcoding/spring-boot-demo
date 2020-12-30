CREATE TABLE IF NOT EXISTS `datasource_config`
(
    `id`       bigint(13)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `host`     varchar(255) NOT NULL COMMENT '数据库地址',
    `port`     int(6)       NOT NULL COMMENT '数据库端口',
    `username` varchar(100) NOT NULL COMMENT '数据库用户名',
    `password` varchar(100) NOT NULL COMMENT '数据库密码',
    `database` varchar(100) DEFAULT 0 COMMENT '数据库名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='数据源配置表';

INSERT INTO `datasource_config`(`id`, `host`, `port`, `username`, `password`, `database`)
VALUES (1, '127.0.01', 3306, 'root', 'root', 'test');
INSERT INTO `datasource_config`(`id`, `host`, `port`, `username`, `password`, `database`)
VALUES (2, '192.168.239.4', 3306, 'dmcp', 'Dmcp321!', 'test');