alter table exam_medical_subject add show_index number(10);
insert into exam_medical_subject (guid, category_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7D13671E050840A063959A8', '61AEAB78A7CF3671E050840A063959A8','生理学', sysdate, 1,1);
insert into exam_medical_subject (guid, category_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7D23671E050840A063959A8', '61AEAB78A7CF3671E050840A063959A8','生物化学', sysdate, 1,2);
insert into exam_medical_subject (guid, category_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7D33671E050840A063959A8', '61AEAB78A7CF3671E050840A063959A8','病理学', sysdate, 1,3);
insert into exam_medical_subject (guid, category_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7D43671E050840A063959A8', '61AEAB78A7CF3671E050840A063959A8','药理学', sysdate, 1,4);
insert into exam_medical_subject (guid, category_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7D53671E050840A063959A8', '61AEAB78A7CF3671E050840A063959A8','医学微生物学', sysdate, 1,5);
insert into exam_medical_subject (guid, category_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7D63671E050840A063959A8', '61AEAB78A7CF3671E050840A063959A8','医学免疫学', sysdate, 1,6);
insert into exam_medical_subject (guid, category_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7D73671E050840A063959A8', '61AEAB78A7CF3671E050840A063959A8','卫生法规', sysdate, 1,7);
insert into exam_medical_subject (guid, category_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7D83671E050840A063959A8', '61AEAB78A7CF3671E050840A063959A8','预防学', sysdate, 1,8);
insert into exam_medical_subject (guid, category_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7D93671E050840A063959A8', '61AEAB78A7CF3671E050840A063959A8','医学心理学', sysdate, 1,9);
insert into exam_medical_subject (guid, category_guid, name, modify_time, modify_flag, show_index)
values ('61AEAB78A7DA3671E050840A063959A8', '61AEAB78A7CF3671E050840A063959A8','医学伦理学', sysdate, 1,10);

alter table exam_medical_chapter add show_index number(10);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D13671E050840A063959A8', '绪论', sysdate, 1, 1);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D13671E050840A063959A8', '细胞的基本功能', sysdate, 1, 2);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D13671E050840A063959A8', '血液', sysdate, 1, 3);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D13671E050840A063959A8', '血液循环', sysdate, 1, 4);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D13671E050840A063959A8', '呼吸', sysdate, 1, 5);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D13671E050840A063959A8', '消化和吸收', sysdate, 1, 6);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D13671E050840A063959A8', '能量代谢和体温', sysdate, 1, 7);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D13671E050840A063959A8', '肾脏的排泄功能', sysdate, 1, 8);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D13671E050840A063959A8', '神经系统的功能', sysdate, 1, 9);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D13671E050840A063959A8', '内分泌', sysdate, 1, 10);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D13671E050840A063959A8', '生殖', sysdate, 1, 11);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D23671E050840A063959A8', '蛋白质的结构与功能', sysdate, 1, 1);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D23671E050840A063959A8', '核酸的结构与功能', sysdate, 1, 2);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D23671E050840A063959A8', '酶', sysdate, 1, 3);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D23671E050840A063959A8', '聚糖的结构与功能', sysdate, 1, 4);
insert into exam_medical_chapter (guid, subject_guid, name, modify_time, modify_flag, show_index)
values (sys_guid(), '61AEAB78A7D23671E050840A063959A8', '维生素与无机盐', sysdate, 1, 5);