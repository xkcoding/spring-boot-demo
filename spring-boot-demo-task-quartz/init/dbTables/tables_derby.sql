-- 
-- Apache Derby scripts by Steve Stewart, updated by Ronald Pomeroy
-- Based on Srinivas Venkatarangaiah's file for Cloudscape
-- 
-- Known to work with Apache Derby 10.0.2.1, or 10.6.2.1
--
-- Updated by Zemian Deng <saltnlight5@gmail.com> on 08/21/2011
--   * Fixed nullable fields on qrtz_simprop_triggers table. 
--   * Added Derby QuickStart comments and drop tables statements.
--
-- DerbyDB + Quartz Quick Guide:
-- * Derby comes with Oracle JDK! For Java6, it default install into C:/Program Files/Sun/JavaDB on Windows.
-- 1. Create a derby.properties file under JavaDB directory, and have the following:
--    derby.connection.requireAuthentication = true
--    derby.authentication.provider = BUILTIN
--    derby.user.quartz2=quartz2123
-- 2. Start the DB server by running bin/startNetworkServer script.
-- 3. On a new terminal, run bin/ij tool to bring up an SQL prompt, then run:
--    connect 'jdbc:derby://localhost:1527/quartz2;user=quartz2;password=quartz2123;create=true';
--    run 'quartz/docs/dbTables/tables_derby.sql';
-- Now in quartz.properties, you may use these properties:
--    org.quartz.dataSource.quartzDataSource.driver = org.apache.derby.jdbc.ClientDriver
--    org.quartz.dataSource.quartzDataSource.URL = jdbc:derby://localhost:1527/quartz2
--    org.quartz.dataSource.quartzDataSource.user = quartz2
--    org.quartz.dataSource.quartzDataSource.password = quartz2123
--

-- Auto drop and reset tables 
-- Derby doesn't support if exists condition on table drop, so user must manually do this step if needed to.
-- drop table qrtz_fired_triggers;
-- drop table qrtz_paused_trigger_grps;
-- drop table qrtz_scheduler_state;
-- drop table qrtz_locks;
-- drop table qrtz_simple_triggers;
-- drop table qrtz_simprop_triggers;
-- drop table qrtz_cron_triggers;
-- drop table qrtz_blob_triggers;
-- drop table qrtz_triggers;
-- drop table qrtz_job_details;
-- drop table qrtz_calendars;

create table qrtz_job_details (
sched_name varchar(120) not null,
job_name varchar(200) not null,
job_group varchar(200) not null,
description varchar(250) ,
job_class_name varchar(250) not null,
is_durable varchar(5) not null,
is_nonconcurrent varchar(5) not null,
is_update_data varchar(5) not null,
requests_recovery varchar(5) not null,
job_data blob,
primary key (sched_name,job_name,job_group)
);

create table qrtz_triggers(
sched_name varchar(120) not null,
trigger_name varchar(200) not null,
trigger_group varchar(200) not null,
job_name varchar(200) not null,
job_group varchar(200) not null,
description varchar(250),
next_fire_time bigint,
prev_fire_time bigint,
priority integer,
trigger_state varchar(16) not null,
trigger_type varchar(8) not null,
start_time bigint not null,
end_time bigint,
calendar_name varchar(200),
misfire_instr smallint,
job_data blob,
primary key (sched_name,trigger_name,trigger_group),
foreign key (sched_name,job_name,job_group) references qrtz_job_details(sched_name,job_name,job_group)
);

create table qrtz_simple_triggers(
sched_name varchar(120) not null,
trigger_name varchar(200) not null,
trigger_group varchar(200) not null,
repeat_count bigint not null,
repeat_interval bigint not null,
times_triggered bigint not null,
primary key (sched_name,trigger_name,trigger_group),
foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_cron_triggers(
sched_name varchar(120) not null,
trigger_name varchar(200) not null,
trigger_group varchar(200) not null,
cron_expression varchar(120) not null,
time_zone_id varchar(80),
primary key (sched_name,trigger_name,trigger_group),
foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_simprop_triggers
  (          
    sched_name varchar(120) not null,
    trigger_name varchar(200) not null,
    trigger_group varchar(200) not null,
    str_prop_1 varchar(512),
    str_prop_2 varchar(512),
    str_prop_3 varchar(512),
    int_prop_1 int,
    int_prop_2 int,
    long_prop_1 bigint,
    long_prop_2 bigint,
    dec_prop_1 numeric(13,4),
    dec_prop_2 numeric(13,4),
    bool_prop_1 varchar(5),
    bool_prop_2 varchar(5),
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group) 
    references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_blob_triggers(
sched_name varchar(120) not null,
trigger_name varchar(200) not null,
trigger_group varchar(200) not null,
blob_data blob,
primary key (sched_name,trigger_name,trigger_group),
foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_calendars(
sched_name varchar(120) not null,
calendar_name varchar(200) not null,
calendar blob not null,
primary key (sched_name,calendar_name)
);

create table qrtz_paused_trigger_grps
  (
    sched_name varchar(120) not null,
    trigger_group varchar(200) not null,
primary key (sched_name,trigger_group)
);

create table qrtz_fired_triggers(
sched_name varchar(120) not null,
entry_id varchar(95) not null,
trigger_name varchar(200) not null,
trigger_group varchar(200) not null,
instance_name varchar(200) not null,
fired_time bigint not null,
sched_time bigint not null,
priority integer not null,
state varchar(16) not null,
job_name varchar(200),
job_group varchar(200),
is_nonconcurrent varchar(5),
requests_recovery varchar(5),
primary key (sched_name,entry_id)
);

create table qrtz_scheduler_state
  (
    sched_name varchar(120) not null,
    instance_name varchar(200) not null,
    last_checkin_time bigint not null,
    checkin_interval bigint not null,
primary key (sched_name,instance_name)
);

create table qrtz_locks
  (
    sched_name varchar(120) not null,
    lock_name varchar(40) not null,
primary key (sched_name,lock_name)
);

