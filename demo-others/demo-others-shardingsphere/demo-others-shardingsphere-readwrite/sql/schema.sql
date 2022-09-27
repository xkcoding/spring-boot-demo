USE `spring-boot-demo`;
CREATE TABLE `t_user`
(
  id       BIGINT AUTO_INCREMENT,
  username VARCHAR(30),
  PRIMARY KEY (id)
);

INSERT t_user(username)
VALUES ('xkcoding');

INSERT t_user(username)
VALUES ('spring-boot-demo-test');
