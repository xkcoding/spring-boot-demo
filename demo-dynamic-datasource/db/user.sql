CREATE TABLE IF NOT EXISTS `test_user`
(
    `id`   bigint(13)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(255) NOT NULL COMMENT '姓名',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户表';

--  默认数据库插入如下 SQL
INSERT INTO `test_user`(`id`, `name`)
values (1, '默认数据库用户1');
INSERT INTO `test_user`(`id`, `name`)
values (2, '默认数据库用户2');

-- 测试库1插入如下SQL
INSERT INTO `test_user`(`id`, `name`)
values (1, '测试库1用户1');
INSERT INTO `test_user`(`id`, `name`)
values (2, '测试库1用户2');

-- 测试库2插入如下SQL
INSERT INTO `test_user`(`id`, `name`)
values (1, '测试库2用户1');
INSERT INTO `test_user`(`id`, `name`)
values (2, '测试库2用户2');