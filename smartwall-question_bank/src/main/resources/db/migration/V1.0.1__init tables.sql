create table EXAM_USER
(
  GUID          VARCHAR2(32) not null,
  MOBILE        VARCHAR2(11),
  NAME          VARCHAR2(50),
  PASSWORD      VARCHAR2(100),
  EXAM_KIND     VARCHAR2(32),
  EXAM_CATEGORY VARCHAR2(32),
  REG_TIME      DATE,
  MODIFY_TIME   DATE,
  AVATOR        VARCHAR2(255)
);

comment on table EXAM_USER is 'user';
comment on column EXAM_USER.EXAM_KIND is 'exam_medical_kind';
comment on column EXAM_USER.EXAM_CATEGORY  is 'exam_medical_category';
alter table EXAM_USER  add constraint PK_EXAM_USER primary key (GUID)  using index;
create index IX_EXAM_USER_01 on EXAM_USER (MOBILE);
create index IX_EXAM_USER_02 on EXAM_USER (PASSWORD);

-- Create table
create table EXAM_MEDICAL_KIND
(
  GUID        VARCHAR2(32) not null,
  NAME        VARCHAR2(50) not null,
  MODIFY_TIME DATE,
  MODIFY_FLAG NUMBER(1)
 );
 
comment on table EXAM_MEDICAL_KIND is '医学分类';
alter table EXAM_MEDICAL_KIND
  add constraint PK_EXAM_MEDICAL_KIND primary key (GUID) using index;

create table EXAM_MEDICAL_CATEGORY
(
  GUID        VARCHAR2(32) not null,
  KIND_GUID   VARCHAR2(32) not null,
  NAME        VARCHAR2(50) not null,
  MODIFY_TIME DATE,
  MODIFY_FLAG NUMBER(1)
);

comment on table EXAM_MEDICAL_CATEGORY is 'medical category';
comment on column EXAM_MEDICAL_CATEGORY.KIND_GUID is 'exam_medical_kind';
alter table EXAM_MEDICAL_CATEGORY add constraint PK_EXAM_MEDICAL_CATEGORY primary key (GUID)  using index;
create index IX_EXAM_MEDICAL_CATEGORY_01 on EXAM_MEDICAL_CATEGORY (KIND_GUID);

create table EXAM_MEDICAL_SUBJECT
(
  GUID          VARCHAR2(32) not null,
  CATEGORY_GUID VARCHAR2(32) not null,
  NAME          VARCHAR2(50) not null,
  MODIFY_TIME   DATE,
  MODIFY_FLAG   NUMBER(1)
);

comment on table EXAM_MEDICAL_SUBJECT is 'medical subject';
comment on column EXAM_MEDICAL_SUBJECT.CATEGORY_GUID  is 'exam_medical_category';
alter table EXAM_MEDICAL_SUBJECT add constraint PK_EXAM_MEDICAL_SUBJECT primary key (GUID) using index;
create index IX_EXAM_MEDICAL_SUBJECT_01 on EXAM_MEDICAL_SUBJECT (CATEGORY_GUID);


create table EXAM_MEDICAL_CHAPTER
(
  GUID         VARCHAR2(32) not null,
  SUBJECT_GUID VARCHAR2(32) not null,
  NAME         VARCHAR2(50) not null,
  MODIFY_TIME  DATE,
  MODIFY_FLAG  NUMBER(1)
);

comment on table EXAM_MEDICAL_CHAPTER is 'medical chapter';
comment on column EXAM_MEDICAL_CHAPTER.SUBJECT_GUID is 'exam_category_chapter';

alter table EXAM_MEDICAL_CHAPTER add constraint PK_EXAM_MEDICAL_CHAPTER primary key (GUID) using index;
create index IX_EXAM_MEDICAL_CHAPTER on EXAM_MEDICAL_CHAPTER (SUBJECT_GUID);

create table EXAM_USER_CATEGORY
(
  USER_GUID     VARCHAR2(32),
  CATEGORY_GUID VARCHAR2(32),
  MODIFY_TIME   DATE,
  MODIFY_FLAG   NUMBER(1)
);
comment on table EXAM_USER_CATEGORY is 'user category';
create index IX_EXAM_USER_CATEGORY_01 on EXAM_USER_CATEGORY (USER_GUID);
create index IX_EXAM_USER_CATEGORY_02 on EXAM_USER_CATEGORY (CATEGORY_GUID);

create table EXAM_USER_DO_SUMMARY
(
  GUID          VARCHAR2(32) not null,
  QUESTION_GUID VARCHAR2(32) not null,
  CHAPTER_GUID  VARCHAR2(32) not null,
  QUESTION_NO   NUMBER(10),
  DO_NUM        NUMBER(10),
  RIGHT_NUM     NUMBER(10),
  ERROR_NUM     NUMBER(10)
);

comment on table EXAM_USER_DO_SUMMARY is 'exam user do summary';
comment on column EXAM_USER_DO_SUMMARY.QUESTION_GUID is 'from mongodb';
comment on column EXAM_USER_DO_SUMMARY.CHAPTER_GUID is 'exam_medical_chapter';

create index IX_EXAM_USER_DO_SUMMARY_01 on EXAM_USER_DO_SUMMARY (QUESTION_GUID);
create index IX_EXAM_USER_DO_SUMMARY_02 on EXAM_USER_DO_SUMMARY (CHAPTER_GUID);
create index IX_EXAM_USER_DO_SUMMARY_03 on EXAM_USER_DO_SUMMARY (QUESTION_NO);


create table EXAM_USER_QUESTION_DO
(
  GUID          VARCHAR2(32) not null,
  USER_GUID     VARCHAR2(32) not null,
  CHAPTER_GUID  VARCHAR2(32) not null,
  QUESTION_NO   NUMBER(10),
  QUESTION_GUID VARCHAR2(32),
  STATUS        NUMBER(1) default 0,
  DATA_TIME     DATE
);
comment on table EXAM_USER_QUESTION_DO is 'exam user question do';
comment on column EXAM_USER_QUESTION_DO.STATUS is '0:undo 1:right 2:error';
create index IX_EXAM_USER_QUESTION_DO_01 on EXAM_USER_QUESTION_DO (USER_GUID);
create index IX_EXAM_USER_QUESTION_DO_02 on EXAM_USER_QUESTION_DO (CHAPTER_GUID);
create index IX_EXAM_USER_QUESTION_DO_03 on EXAM_USER_QUESTION_DO (QUESTION_GUID);
