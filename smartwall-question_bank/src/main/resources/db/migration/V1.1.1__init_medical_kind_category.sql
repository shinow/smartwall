alter table EXAM_MEDICAL_KIND add show_index number(10);
insert into exam_medical_kind (guid, name, modify_time, modify_flag,show_index) values ('61AEAB78A7CD3671E050840A063959A8', '医生资格', sysdate, 1,1);
insert into exam_medical_kind (guid, name, modify_time, modify_flag,show_index) values ('61AEAB78A7CE3671E050840A063959A8', '卫生资格', sysdate, 1,2);

alter table exam_medical_category add show_index number(10);
insert into exam_medical_category (guid, kind_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7CF3671E050840A063959A8', '61AEAB78A7CD3671E050840A063959A8', '执业医师', sysdate, 1, 1);
insert into exam_medical_category (guid, kind_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7D03671E050840A063959A8', '61AEAB78A7CD3671E050840A063959A8', '执业助理医师', sysdate, 1, 2);