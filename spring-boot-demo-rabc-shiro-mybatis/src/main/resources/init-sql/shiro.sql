-- 建表 --
-- 部门表 --
DROP TABLE IF EXISTS `mybatis_shiro_dept`;
CREATE TABLE `mybatis_shiro_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级部门id，顶层是0',
  `level` varchar(255) NOT NULL DEFAULT '0' COMMENT '部门层级，默认为0，层级直接用逗号（半角）隔开',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '部门在当前层级下的顺序，由小到大',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='部门表';

INSERT INTO `mybatis_shiro_dept` VALUES (1, '总公司', 0, '0', 0, '总公司', '系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_dept` VALUES (2, '开发部', 1, '0,1', 0, '开发部', '系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_dept` VALUES (3, '运营部', 1, '0,1', 1, '运营部', '系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_dept` VALUES (4, '战略部', 1, '0,1', 2, '战略部', '系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_dept` VALUES (5, '软件部', 2, '0,1,2', 0, '软件部', '系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_dept` VALUES (6, '硬件部', 2, '0,1,2', 1, '硬件部', '系统', '2017-12-01 00:00:00', '127.0.0.1');

-- 用户表 --
DROP TABLE IF EXISTS `mybatis_shiro_user`;
CREATE TABLE `mybatis_shiro_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(40) NOT NULL DEFAULT '' COMMENT '加密后的密码',
  `salt` varchar(32) NOT NULL DEFAULT '' COMMENT '密码加密的盐值',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `realname` varchar(20) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `telephone` varchar(13) NOT NULL DEFAULT '' COMMENT '手机号',
  `mail` varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱',
  `dept_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户所在部门的id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，-1：删除，0：冻结状态，1：正常',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `last_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次登录的时间',
  `last_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次登录的ip地址',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT ='用户表';

INSERT INTO `mybatis_shiro_user` VALUES (1, 'xkcoding', '29b97b26be547c8442689bdbe6f1efb9', '77798aa08e914989bb588188cb2c0ad4', '','沈扬凯','18601224166','237497819@qq.com',5,1,'本尊','2017-12-01 00:00:00', '127.0.0.1', '系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_user` VALUES (2, 'test', '29b97b26be547c8442689bdbe6f1efb9', '77798aa08e914989bb588188cb2c0ad4', '','测试用户','18666666666','qaz@qq.com',6,1,'测试用户', '2017-12-01 00:00:00', '127.0.0.1','系统', '2017-12-01 00:00:00', '127.0.0.1');

-- 权限表 --
DROP TABLE IF EXISTS `mybatis_shiro_acl`;
CREATE TABLE `mybatis_shiro_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '权限编号',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '权限名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '权限图标',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级权限id',
  `level` varchar(255) NOT NULL DEFAULT '' COMMENT '权限层级，默认为0，层级直接用逗号（半角）隔开',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT '权限的url, 可以填正则表达式',
  `type` int(11) NOT NULL DEFAULT '3' COMMENT '类型，1：菜单，2：按钮，3：其他',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：冻结，1：正常',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '权限在当前层级下的顺序，由小到大',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT ='权限表';

INSERT INTO `mybatis_shiro_acl` VALUES (1,'system_mgr','系统管理','',0,'0','/system_mgr/*',1,1,0,'系统管理', '系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_acl` VALUES (2,'user_mgr','用户管理','',1,'0,1','/system_mgr/user_mgr/*',1,1,0,'用户管理', '系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_acl` VALUES (3,'user_insert','新增用户','',2,'0,1,2','/user/add',2,1,0,'新增用户','系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_acl` VALUES (4,'user_delete','删除用户','',2,'0,1,2','/user/delete',2,1,1,'删除用户','系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_acl` VALUES (5,'user_update','修改用户','',2,'0,1,2','/user/update',2,1,2,'修改用户','系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_acl` VALUES (6,'user_select','查询用户','',2,'0,1,2','/user/select',2,1,3,'查询用户','系统', '2017-12-01 00:00:00', '127.0.0.1');

-- 角色表 --
DROP TABLE IF EXISTS `mybatis_shiro_role`;
CREATE TABLE `mybatis_shiro_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(20) NOT NULL COMMENT '角色名称',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '角色的类型，1：管理员角色，2：其他',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：冻结，1：可用',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT ='角色表';

INSERT INTO `mybatis_shiro_role` VALUES (1,'超级管理员', 1, 1,'超级管理员' ,'系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_role` VALUES (2, '临时', 2, 1,'临时','系统', '2017-12-01 00:00:00', '127.0.0.1');

-- 角色-用户表 --
DROP TABLE IF EXISTS `mybatis_shiro_role_user`;
CREATE TABLE `mybatis_shiro_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色-用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `mybatis_shiro_role_user` VALUES (1, 1, 1,'系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_role_user` VALUES (2, 2, 2,'系统', '2017-12-01 00:00:00', '127.0.0.1');

-- 角色-权限表 --
DROP TABLE IF EXISTS `mybatis_shiro_role_acl`;
CREATE TABLE `mybatis_shiro_role_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色-权限id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `acl_id` int(11) NOT NULL COMMENT '权限id',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `mybatis_shiro_role_acl` VALUES (1, 1, 1,'系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_role_acl` VALUES (2, 1, 2,'系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_role_acl` VALUES (3, 1, 3,'系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_role_acl` VALUES (4, 1, 4,'系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_role_acl` VALUES (5, 1, 5,'系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_role_acl` VALUES (6, 1, 6,'系统', '2017-12-01 00:00:00', '127.0.0.1');
INSERT INTO `mybatis_shiro_role_acl` VALUES (7, 2, 6,'系统', '2017-12-01 00:00:00', '127.0.0.1');

-- 日志记录表 --
DROP TABLE IF EXISTS `mybatis_shiro_log`;
CREATE TABLE `mybatis_shiro_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志记录id',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '权限更新的类型，1：部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系',
  `target_id` int(11) NOT NULL COMMENT '基于type后指定的对象id，比如用户、权限、角色等表的主键',
  `old_value` text COMMENT '旧值',
  `new_value` text COMMENT '新值',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip地址',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '当前是否复原过，0：没有复原过，1：复原过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
