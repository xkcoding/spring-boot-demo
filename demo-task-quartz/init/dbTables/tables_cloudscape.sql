# 
# Thanks to Srinivas Venkatarangaiah for submitting this file's contents
#
# In your Quartz properties file, you'll need to set 
# org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.CloudscapeDelegate
#
# Known to work with Cloudscape 3.6.4 (should work with others)
#


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
	job_data long varbinary,
primary key (sched_name,job_name,job_group)
);

create table qrtz_triggers(
    sched_name varchar(120) not null,
	trigger_name varchar(200) not null,
	trigger_group varchar(200) not null,
	job_name varchar(200) not null,
	job_group varchar(200) not null,
	description varchar(250) ,
	next_fire_time longint,
	prev_fire_time longint,
	priority integer,
	trigger_state varchar(16) not null,
	trigger_type varchar(8) not null,
	start_time longint not null,
	end_time longint,
	calendar_name varchar(200),
	misfire_instr smallint,
	job_data long varbinary,
primary key (sched_name,trigger_name,trigger_group),
foreign key (sched_name,job_name,job_group) references qrtz_job_details(sched_name,job_name,job_group)
);

create table qrtz_simple_triggers(
    sched_name varchar(120) not null,
	trigger_name varchar(200) not null,
	trigger_group varchar(200) not null,
	repeat_count longint not null,
	repeat_interval longint not null,
	times_triggered longint not null,
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

CREATE TABLE qrtz_simprop_triggers
  (          
    sched_name varchar(120) not null,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INT NULL,
    INT_PROP_2 INT NULL,
    LONG_PROP_1 longint NULL,
    LONG_PROP_2 longint NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 varchar(5) NULL,
    BOOL_PROP_2 varchar(5) NULL,
    PRIMARY KEY (sched_name,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (sched_name,TRIGGER_NAME,TRIGGER_GROUP) 
    REFERENCES QRTZ_TRIGGERS(sched_name,TRIGGER_NAME,TRIGGER_GROUP)
);

create table qrtz_blob_triggers(
    sched_name varchar(120) not null,
	trigger_name varchar(200) not null,
	trigger_group varchar(200) not null,
	blob_data long varbinary ,
primary key (sched_name,trigger_name,trigger_group),
foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_calendars(
    sched_name varchar(120) not null,
	calendar_name varchar(200) not null,
	calendar long varbinary not null,
primary key (sched_name,calendar_name)
); 

create table qrtz_paused_trigger_grps
  (
    sched_name varchar(120) not null,
    trigger_group  varchar(200) not null, 
primary key (sched_name,trigger_group)
);

create table qrtz_fired_triggers(
    sched_name varchar(120) not null,
	entry_id varchar(95) not null,
	trigger_name varchar(200) not null,
	trigger_group varchar(200) not null,
	instance_name varchar(200) not null,
	fired_time longint not null,
	sched_time longint not null,
	priority integer not null,
	state varchar(16) not null,
	job_name varchar(200) null,
	job_group varchar(200) null,
	is_nonconcurrent varchar(5) null,
	requests_recovery varchar(5) null,
primary key (sched_name,entry_id)
);

create table qrtz_scheduler_state 
  (
    sched_name varchar(120) not null,
    instance_name varchar(200) not null,
    last_checkin_time longint not null,
    checkin_interval longint not null,
primary key (sched_name,instance_name)
);

create table qrtz_locks
  (
    sched_name varchar(120) not null,
    lock_name  varchar(40) not null, 
primary key (sched_name,lock_name)
);
