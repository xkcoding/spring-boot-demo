-- 测试数据
INSERT INTO sys_client_details (id, access_token_validity_seconds, authorizations, auto_approve_scopes, client_id, client_secret, grant_types, redirect_url, refresh_token_validity_seconds, resource_ids, scopes) VALUES (1, 6000, null, null, 'oauth2', '$2a$10$O8uM8kd5SbsuoITG3tBifOcarqqI8GP19vzbqDzVHP5ZV9yOfvpYS', 'authorization_code,password', 'http://example.com', 6000, 'oauth2', 'READ,WRITE');
INSERT INTO sys_client_details (id, access_token_validity_seconds, authorizations, auto_approve_scopes, client_id, client_secret, grant_types, redirect_url, refresh_token_validity_seconds, resource_ids, scopes) VALUES (2, 6000, null, null, 'test', '$2a$10$O8uM8kd5SbsuoITG3tBifOcarqqI8GP19vzbqDzVHP5ZV9yOfvpYS', 'authorization_code,password', 'http://example.com', 6000, 'test', 'READ');
INSERT INTO sys_client_details (id, access_token_validity_seconds, authorizations, auto_approve_scopes, client_id, client_secret, grant_types, redirect_url, refresh_token_validity_seconds, resource_ids, scopes) VALUES (3, 6000, null, null, 'test', '$2a$10$O8uM8kd5SbsuoITG3tBifOcarqqI8GP19vzbqDzVHP5ZV9yOfvpYS', 'authorization_code,password', 'http://example.com', 6000, 'error', 'READ');
INSERT INTO sys_role (id, name, description) VALUES (1, 'ROLE_ADMIN', '管理员');
INSERT INTO sys_role (id, name, description) VALUES (2, 'ROLE_TEST', '测试');
INSERT INTO sys_user (id, username, password) VALUES (1, 'admin', '$2a$10$xLH.pDNz3d2frOBQ6Gc.wuHY4ghwlSyFDgy0Ta.psXmm1YJjNaV1G');
INSERT INTO sys_user (id, username, password) VALUES (2, 'test', '$2a$10$xLH.pDNz3d2frOBQ6Gc.wuHY4ghwlSyFDgy0Ta.psXmm1YJjNaV1G');
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO sys_user_role (user_id, role_id) VALUES (2, 2);
