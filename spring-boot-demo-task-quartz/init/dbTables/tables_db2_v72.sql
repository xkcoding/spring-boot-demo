--
-- Thanks to Horia Muntean for submitting this, Mikkel Heisterberg for updating it 
--
-- .. known to work with DB2 7.2 and the JDBC driver "COM.ibm.db2.jdbc.net.DB2Driver"
-- .. likely to work with others...
--
-- In your Quartz properties file, you'll need to set 
-- org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.DB2v7Delegate
--
-- or
--
-- org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
--
-- If you're using DB2 6.x you'll want to set this property to
-- org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.DB2v6Delegate
--
-- Note that the blob column size (e.g. blob(2000)) dictates the amount of data that can be stored in 
-- that blob - i.e. limits the amount of data you can put into your JobDataMap 
--

DROP TABLE QRTZ_FIRED_TRIGGERS;
DROP TABLE QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE QRTZ_SCHEDULER_STATE;
DROP TABLE QRTZ_LOCKS;
DROP TABLE QRTZ_SIMPLE_TRIGGERS;
DROP TABLE QRTZ_SIMPROP_TRIGGERS;
DROP TABLE QRTZ_CRON_TRIGGERS;
DROP TABLE QRTZ_TRIGGERS;
DROP TABLE QRTZ_JOB_DETAILS;
DROP TABLE QRTZ_CALENDARS;
DROP TABLE QRTZ_BLOB_TRIGGERS;

create table qrtz_job_details (
  sched_name varchar(120) not null,
  job_name varchar(80) not null,
  job_group varchar(80) not null,
  description varchar(120),
  job_class_name varchar(128) not null,
  is_durable varchar(1) not null,
  is_nonconcurrent varchar(1) not null,
  is_update_data varchar(1) not null,
  requests_recovery varchar(1) not null,
  job_data blob(2000),
    primary key (sched_name,job_name,job_group)
);

create table qrtz_triggers(
  sched_name varchar(120) not null,
  trigger_name varchar(80) not null,
  trigger_group varchar(80) not null,
  job_name varchar(80) not null,
  job_group varchar(80) not null,
  description varchar(120),
  next_fire_time bigint,
  prev_fire_time bigint,
  priority integer,
  trigger_state varchar(16) not null,
  trigger_type varchar(8) not null,
  start_time bigint not null,
  end_time bigint,
  calendar_name varchar(80),
  misfire_instr smallint,
  job_data blob(2000),
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,job_name,job_group) references qrtz_job_details(sched_name,job_name,job_group)
);

create table qrtz_simple_triggers(
  sched_name varchar(120) not null,
  trigger_name varchar(80) not null,
  trigger_group varchar(80) not null,
  repeat_count bigint not null,
  repeat_interval bigint not null,
  times_triggered bigint not null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
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
    LONG_PROP_1 BIGINT NULL,
    LONG_PROP_2 BIGINT NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR(1) NULL,
    BOOL_PROP_2 VARCHAR(1) NULL,
    PRIMARY KEY (sched_name,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (sched_name,TRIGGER_NAME,TRIGGER_GROUP) 
    REFERENCES QRTZ_TRIGGERS(sched_name,TRIGGER_NAME,TRIGGER_GROUP)
);

create table qrtz_blob_triggers(
  sched_name varchar(120) not null,
  trigger_name varchar(80) not null,
  trigger_group varchar(80) not null,
  blob_data blob(2000),
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_calendars(
  sched_name varchar(120) not null,
  calendar_name varchar(80) not null,
  calendar blob(2000) not null,
    primary key (sched_name,calendar_name)
);

create table qrtz_fired_triggers(
  sched_name varchar(120) not null,
  entry_id varchar(95) not null,
  trigger_name varchar(80) not null,
  trigger_group varchar(80) not null,
  instance_name varchar(80) not null,
  fired_time bigint not null,
  sched_time bigint not null,
  priority integer not null,
  state varchar(16) not null,
  job_name varchar(80),
  job_group varchar(80),
  is_nonconcurrent varchar(1),
  requests_recovery varchar(1),
    primary key (sched_name,entry_id)
);


create table qrtz_paused_trigger_grps(
  sched_name varchar(120) not null,
  trigger_group  varchar(80) not null, 
    primary key (sched_name,trigger_group)
);

create table qrtz_scheduler_state (
  sched_name varchar(120) not null,
  instance_name varchar(80) not null,
  last_checkin_time bigint not null,
  checkin_interval bigint not null,
    primary key (sched_name,instance_name)
);

create table qrtz_locks
  (
    sched_name varchar(120) not null,
    lock_name  varchar(40) not null, 
      primary key (sched_name,lock_name)
);
