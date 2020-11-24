/*==============================================================================================*/
/* Quartz database tables creation script for Sybase ASE 12.5 */
/* Written by Pertti Laiho (email: pertti.laiho@deio.net), 9th May 2003 */
/* */
/* Compatible with Quartz version 1.1.2 */
/* */
/* Sybase ASE works ok with the SybaseDelegate delegate class. That means in your Quartz properties */
/* file, you'll need to set: */
/* org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.SybaseDelegate */
/*==============================================================================================*/

use your_db_name_here
go

/*==============================================================================*/
/* Clear all tables: */
/*==============================================================================*/

IF OBJECT_ID('QRTZ_FIRED_TRIGGERS') IS NOT NULL 
delete from QRTZ_FIRED_TRIGGERS
go
IF OBJECT_ID('QRTZ_PAUSED_TRIGGER_GRPS') IS NOT NULL 
delete from QRTZ_PAUSED_TRIGGER_GRPS
go
IF OBJECT_ID('QRTZ_SCHEDULER_STATE') IS NOT NULL 
delete from QRTZ_SCHEDULER_STATE
go
IF OBJECT_ID('QRTZ_LOCKS') IS NOT NULL 
delete from QRTZ_LOCKS
go
IF OBJECT_ID('QRTZ_SIMPLE_TRIGGERS') IS NOT NULL 
delete from QRTZ_SIMPLE_TRIGGERS
go
IF OBJECT_ID('QRTZ_SIMPROP_TRIGGERS') IS NOT NULL 
delete from QRTZ_SIMPROP_TRIGGERS
go
IF OBJECT_ID('QRTZ_CRON_TRIGGERS') IS NOT NULL 
delete from QRTZ_CRON_TRIGGERS
go
IF OBJECT_ID('QRTZ_BLOB_TRIGGERS') IS NOT NULL 
delete from QRTZ_BLOB_TRIGGERS
go
IF OBJECT_ID('QRTZ_TRIGGERS') IS NOT NULL 
delete from QRTZ_TRIGGERS
go
IF OBJECT_ID('QRTZ_JOB_DETAILS') IS NOT NULL 
delete from QRTZ_JOB_DETAILS
go
IF OBJECT_ID('QRTZ_CALENDARS') IS NOT NULL 
delete from QRTZ_CALENDARS
go

/*==============================================================================*/
/* Drop constraints: */
/*==============================================================================*/

alter table QRTZ_TRIGGERS
drop constraint FK_triggers_job_details
go

alter table QRTZ_CRON_TRIGGERS
drop constraint FK_cron_triggers_triggers
go

alter table QRTZ_SIMPLE_TRIGGERS
drop constraint FK_simple_triggers_triggers
go

alter table QRTZ_SIMPROP_TRIGGERS
drop constraint FK_simprop_triggers_triggers
go

alter table QRTZ_BLOB_TRIGGERS
drop constraint FK_blob_triggers_triggers
go

/*==============================================================================*/
/* Drop tables: */
/*==============================================================================*/

drop table QRTZ_FIRED_TRIGGERS
go
drop table QRTZ_PAUSED_TRIGGER_GRPS
go
drop table QRTZ_SCHEDULER_STATE
go
drop table QRTZ_LOCKS
go
drop table QRTZ_SIMPLE_TRIGGERS
go
drop table QRTZ_SIMPROP_TRIGGERS
go
drop table QRTZ_CRON_TRIGGERS
go
drop table QRTZ_BLOB_TRIGGERS
go
drop table QRTZ_TRIGGERS
go
drop table QRTZ_JOB_DETAILS
go
drop table QRTZ_CALENDARS
go

/*==============================================================================*/
/* Create tables: */
/*==============================================================================*/

create table QRTZ_CALENDARS (
SCHED_NAME varchar(120) not null,
CALENDAR_NAME varchar(80) not null,
CALENDAR image not null
)
go

create table QRTZ_CRON_TRIGGERS (
SCHED_NAME varchar(120) not null,
TRIGGER_NAME varchar(80) not null,
TRIGGER_GROUP varchar(80) not null,
CRON_EXPRESSION varchar(120) not null,
TIME_ZONE_ID varchar(80) null,
)
go

create table QRTZ_PAUSED_TRIGGER_GRPS (
SCHED_NAME varchar(120) not null,
TRIGGER_GROUP  varchar(80) not null, 
)
go

create table QRTZ_FIRED_TRIGGERS(
SCHED_NAME varchar(120) not null,
ENTRY_ID varchar(95) not null,
TRIGGER_NAME varchar(80) not null,
TRIGGER_GROUP varchar(80) not null,
INSTANCE_NAME varchar(80) not null,
FIRED_TIME numeric(13,0) not null,
SCHED_TIME numeric(13,0) not null,
PRIORITY int not null,
STATE varchar(16) not null,
JOB_NAME varchar(80) null,
JOB_GROUP varchar(80) null,
IS_NONCONCURRENT bit not null,
REQUESTS_RECOVERY bit not null,
)
go

create table QRTZ_SCHEDULER_STATE (
SCHED_NAME varchar(120) not null,
INSTANCE_NAME varchar(80) not null,
LAST_CHECKIN_TIME numeric(13,0) not null,
CHECKIN_INTERVAL numeric(13,0) not null,
)
go

create table QRTZ_LOCKS (
SCHED_NAME varchar(120) not null,
LOCK_NAME  varchar(40) not null, 
)
go


create table QRTZ_JOB_DETAILS (
SCHED_NAME varchar(120) not null,
JOB_NAME varchar(80) not null,
JOB_GROUP varchar(80) not null,
DESCRIPTION varchar(120) null,
JOB_CLASS_NAME varchar(128) not null,
IS_DURABLE bit not null,
IS_NONCONCURRENT bit not null,
IS_UPDATE_DATA bit not null,
REQUESTS_RECOVERY bit not null,
JOB_DATA image null
)
go

create table QRTZ_SIMPLE_TRIGGERS (
SCHED_NAME varchar(120) not null,
TRIGGER_NAME varchar(80) not null,
TRIGGER_GROUP varchar(80) not null,
REPEAT_COUNT numeric(13,0) not null,
REPEAT_INTERVAL numeric(13,0) not null,
TIMES_TRIGGERED numeric(13,0) not null
)
go

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
  (          
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INT NULL,
    INT_PROP_2 INT NULL,
    LONG_PROP_1 NUMERIC(13,0) NULL,
    LONG_PROP_2 NUMERIC(13,0) NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 bit NOT NULL,
    BOOL_PROP_2 bit NOT NULL
)
go

create table QRTZ_BLOB_TRIGGERS (
SCHED_NAME varchar(120) not null,
TRIGGER_NAME varchar(80) not null,
TRIGGER_GROUP varchar(80) not null,
BLOB_DATA image null
)
go

create table QRTZ_TRIGGERS (
SCHED_NAME varchar(120) not null,
TRIGGER_NAME varchar(80) not null,
TRIGGER_GROUP varchar(80) not null,
JOB_NAME varchar(80) not null,
JOB_GROUP varchar(80) not null,
DESCRIPTION varchar(120) null,
NEXT_FIRE_TIME numeric(13,0) null,
PREV_FIRE_TIME numeric(13,0) null,
PRIORITY int null,
TRIGGER_STATE varchar(16) not null,
TRIGGER_TYPE varchar(8) not null,
START_TIME numeric(13,0) not null,
END_TIME numeric(13,0) null,
CALENDAR_NAME varchar(80) null,
MISFIRE_INSTR smallint null,
JOB_DATA image null
)
go

/*==============================================================================*/
/* Create primary key constraints: */
/*==============================================================================*/

alter table QRTZ_CALENDARS
add constraint PK_qrtz_calendars primary key clustered (SCHED_NAME,CALENDAR_NAME)
go

alter table QRTZ_CRON_TRIGGERS
add constraint PK_qrtz_cron_triggers primary key clustered (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP)
go

alter table QRTZ_FIRED_TRIGGERS
add constraint PK_qrtz_fired_triggers primary key clustered (SCHED_NAME,ENTRY_ID)
go

alter table QRTZ_PAUSED_TRIGGER_GRPS
add constraint PK_qrtz_paused_trigger_grps primary key clustered (SCHED_NAME,TRIGGER_GROUP)
go

alter table QRTZ_SCHEDULER_STATE
add constraint PK_qrtz_scheduler_state primary key clustered (SCHED_NAME,INSTANCE_NAME)
go

alter table QRTZ_LOCKS
add constraint PK_qrtz_locks primary key clustered (SCHED_NAME,LOCK_NAME)
go

alter table QRTZ_JOB_DETAILS
add constraint PK_qrtz_job_details primary key clustered (SCHED_NAME,JOB_NAME, JOB_GROUP)
go

alter table QRTZ_SIMPLE_TRIGGERS
add constraint PK_qrtz_simple_triggers primary key clustered (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP)
go

alter table QRTZ_SIMPROP_TRIGGERS
add constraint PK_qrtz_simprop_triggers primary key clustered (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP)
go

alter table QRTZ_TRIGGERS
add constraint PK_qrtz_triggers primary key clustered (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP)
go

alter table QRTZ_BLOB_TRIGGERS
add constraint PK_qrtz_blob_triggers primary key clustered (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP)
go


/*==============================================================================*/
/* Create foreign key constraints: */
/*==============================================================================*/

alter table QRTZ_CRON_TRIGGERS
add constraint FK_cron_triggers_triggers foreign key (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
references QRTZ_TRIGGERS (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
go

alter table QRTZ_SIMPLE_TRIGGERS
add constraint FK_simple_triggers_triggers foreign key (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
references QRTZ_TRIGGERS (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
go

alter table QRTZ_SIMPROP_TRIGGERS
add constraint FK_simprop_triggers_triggers foreign key (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
references QRTZ_TRIGGERS (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
go

alter table QRTZ_TRIGGERS
add constraint FK_triggers_job_details foreign key (SCHED_NAME,JOB_NAME,JOB_GROUP)
references QRTZ_JOB_DETAILS (SCHED_NAME,JOB_NAME,JOB_GROUP)
go

alter table QRTZ_BLOB_TRIGGERS
add constraint FK_blob_triggers_triggers foreign key (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
references QRTZ_TRIGGERS (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
go

/*==============================================================================*/
/* End of script. */
/*==============================================================================*/
