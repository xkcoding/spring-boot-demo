
DROP TABLE qrtz_locks;
DROP TABLE qrtz_scheduler_state;
DROP TABLE qrtz_fired_triggers;
DROP TABLE qrtz_paused_trigger_grps;
DROP TABLE qrtz_calendars;
DROP TABLE qrtz_blob_triggers;
DROP TABLE qrtz_cron_triggers;
DROP TABLE qrtz_simple_triggers;
DROP TABLE qrtz_simprop_triggers;
DROP TABLE qrtz_triggers;
DROP TABLE qrtz_job_details;

create table qrtz_job_details (
    sched_name varchar(120) not null,
	job_name varchar(80) not null,
	job_group varchar(80) not null,
	description varchar(120) ,
	job_class_name varchar(128) not null,
	is_durable varchar(5) not null,
    is_nonconcurrent varchar(5) not null,
    is_update_data varchar(5) not null,
	requests_recovery varchar(5) not null,
	job_data long varbinary,
primary key (sched_name,job_name,job_group)
);

create table qrtz_triggers(
    sched_name varchar(120) not null,
	trigger_name varchar(80) not null,
	trigger_group varchar(80) not null,
	job_name varchar(80) not null,
	job_group varchar(80) not null,
	description varchar(120) ,
	next_fire_time numeric(13),
	prev_fire_time numeric(13),
	priority integer,
	trigger_state varchar(16) not null,
	trigger_type varchar(8) not null,
	start_time numeric(13) not null,
	end_time numeric(13),
	calendar_name varchar(80),
	misfire_instr smallint,
	job_data long varbinary,
primary key (sched_name,trigger_name,trigger_group),
foreign key (sched_name,job_name,job_group) references qrtz_job_details(sched_name,job_name,job_group)
);

create table qrtz_simple_triggers(
    sched_name varchar(120) not null,
	trigger_name varchar(80) not null,
	trigger_group varchar(80) not null,
	repeat_count numeric(13) not null,
	repeat_interval numeric(13) not null,
	times_triggered numeric(13) not null,
primary key (sched_name,trigger_name,trigger_group),
foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

CREATE TABLE qrtz_simprop_triggers
  (          
    sched_name varchar(120) not null,
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INTEGER NULL,
    INT_PROP_2 INTEGER NULL,
    LONG_PROP_1 NUMERIC(13) NULL,
    LONG_PROP_2 NUMERIC(13) NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR(5) NULL,
    BOOL_PROP_2 VARCHAR(5) NULL,
PRIMARY KEY (sched_name,trigger_name,trigger_group),
FOREIGN KEY (sched_name,trigger_name,trigger_group) REFERENCES qrtz_triggers(sched_name,trigger_name,trigger_group)
);


create table qrtz_cron_triggers(
    sched_name varchar(120) not null,
	trigger_name varchar(80) not null,
	trigger_group varchar(80) not null,
	cron_expression varchar(120) not null,
	time_zone_id varchar(80),
primary key (sched_name,trigger_name,trigger_group),
foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_blob_triggers(
    sched_name varchar(120) not null,
	trigger_name varchar(80) not null,
	trigger_group varchar(80) not null,
	blob_data long varbinary ,
primary key (sched_name,trigger_name,trigger_group),
foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_calendars(
    sched_name varchar(120) not null,
	calendar_name varchar(80) not null,
	calendar long varbinary not null,
primary key (sched_name,calendar_name)
); 

create table qrtz_paused_trigger_grps
  (
    sched_name varchar(120) not null,
    trigger_group  varchar(80) not null, 
primary key (sched_name,trigger_group)
);

create table qrtz_fired_triggers(
    sched_name varchar(120) not null,
	entry_id varchar(95) not null,
	trigger_name varchar(80) not null,
	trigger_group varchar(80) not null,
	instance_name varchar(80) not null,
	fired_time numeric(13) not null,
	sched_time numeric(13) not null,
	priority integer not null,
	state varchar(16) not null,
	job_name varchar(80) null,
	job_group varchar(80) null,
	is_nonconcurrent varchar(5) null,
	requests_recovery varchar(5) null,
primary key (sched_name,entry_id)
);

create table qrtz_scheduler_state 
  (
    sched_name varchar(120) not null,
    instance_name varchar(80) not null,
    last_checkin_time numeric(13) not null,
    checkin_interval numeric(13) not null,
primary key (sched_name,instance_name)
);

create table qrtz_locks
  (
    sched_name varchar(120) not null,
    lock_name  varchar(40) not null, 
primary key (sched_name,lock_name)
);

commit work;
