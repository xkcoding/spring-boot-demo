create table sys_client_details
(
    id                             bigint auto_increment primary key,
    access_token_validity_seconds  int          null,
    authorizations                 varchar(255) null,
    auto_approve_scopes            varchar(255) null,
    client_id                      varchar(255) null,
    client_secret                  varchar(255) null,
    grant_types                    varchar(255) null,
    redirect_url                   varchar(255) null,
    refresh_token_validity_seconds int          null,
    resource_ids                   varchar(255) null,
    scopes                         varchar(255) null
);

create table sys_role
(
    id          bigint auto_increment primary key,
    name        varchar(55) not null,
    description varchar(55) null
);

create table sys_user
(
    id       bigint auto_increment primary key,
    username varchar(55)  not null,
    password varchar(128) not null
);

create table sys_user_role
(
    id      bigint auto_increment  primary key,
    user_id bigint not null,
    role_id bigint not null,
    constraint sys_user_role_sys_role_id_fk
        foreign key (role_id) references sys_role (id),
    constraint sys_user_role_sys_user_id_fk
        foreign key (user_id) references sys_user (id)
);

