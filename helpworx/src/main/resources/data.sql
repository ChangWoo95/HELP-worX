

INSERT INTO ROLE (ROLE_ID,ROLE_NM) values (0,'ROLE_USER');
INSERT INTO ROLE (ROLE_ID,ROLE_NM) values (1,'ROLE_ADMIN');
INSERT INTO ROLE (ROLE_ID,ROLE_NM) values (2,'ROLE_OPERATION');


INSERT INTO CTMMNY(CTMMNY_CD, CTMMNY_NM, CTMMNY_DSCR, CONT_BEGIN_DATE, CONT_END_DATE, IS_USED) values ('0', '코오롱베니트', '!!!!!!', '2021-03-28 17:47:22', '2021-03-28 17:47:22', true);
INSERT INTO CTMMNY(CTMMNY_CD, CTMMNY_NM, CTMMNY_DSCR, CONT_BEGIN_DATE, CONT_END_DATE, IS_USED) values ('1', '코오롱글로벌', '@@@@@@', '2021-03-28 17:47:22', '2021-03-28 17:47:22', true);
INSERT INTO CTMMNY(CTMMNY_CD, CTMMNY_NM, CTMMNY_DSCR, CONT_BEGIN_DATE, CONT_END_DATE, IS_USED) values ('2', '코오롱인더스트리', '#####', '2021-03-28 17:47:22', '2021-03-28 17:47:22', true);
INSERT INTO CTMMNY(CTMMNY_CD, CTMMNY_NM, CTMMNY_DSCR, CONT_BEGIN_DATE, CONT_END_DATE, IS_USED) values ('3', '코오롱글로텍', '$$$$$$', '2021-03-28 17:47:22', '2021-03-28 17:47:22', true);
INSERT INTO CTMMNY(CTMMNY_CD, CTMMNY_NM, CTMMNY_DSCR, CONT_BEGIN_DATE, CONT_END_DATE, IS_USED) values ('4', '코오롱제약', '%%%%%%', '2021-03-28 17:47:22', '2021-03-28 17:47:22', true);
INSERT INTO CTMMNY(CTMMNY_CD, CTMMNY_NM, CTMMNY_DSCR, CONT_BEGIN_DATE, CONT_END_DATE, IS_USED) values ('a101', 'test', '123ㅁㅈㄷㅁㄴㅇ', '2021-05-17 17:10:22', '2021-05-17 17:47:22', true);

INSERT INTO USERS (USER_ID, USER_EMAIL, USER_NM, USER_PWD, USER_TELENO, CTMMNY_CD, IS_USED  ) VALUES (11110,'user@mail.com', '정유저' , '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','01012341234', '0', true);
INSERT INTO USERS (USER_ID, USER_EMAIL, USER_NM, USER_PWD, USER_TELENO, CTMMNY_CD, IS_USED  ) VALUES (11111,'oper@mail.com', '김운영' , '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','01012344321', '0', true);
INSERT INTO USERS (USER_ID, USER_EMAIL, USER_NM, USER_PWD, USER_TELENO, CTMMNY_CD, IS_USED  ) VALUES (11112,'admin@mail.com', '남관리' , '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','01012344321', '0', true);

INSERT INTO USER_ROLE_MAP (USER_ID,ROLE_ID) values ( 11110, 0);
INSERT INTO USER_ROLE_MAP (USER_ID,ROLE_ID) values ( 11110, 1);
INSERT INTO USER_ROLE_MAP (USER_ID,ROLE_ID) values ( 11110, 2);
INSERT INTO USER_ROLE_MAP (USER_ID,ROLE_ID) values ( 11111, 2);
INSERT INTO USER_ROLE_MAP (USER_ID,ROLE_ID) values ( 11112, 1);

INSERT INTO SYS (SYS_ID, SYS_NM) values (0, 'PeopleWorX' );
INSERT INTO SYS (SYS_ID, SYS_NM) values (1, 'SalesWorX');
INSERT INTO SYS (SYS_ID, SYS_NM) values (2, 'r-PLANNA' );
INSERT INTO SYS (SYS_ID, SYS_NM) values (3, 'r-CoCoAna' );

INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (0, '인사', 0);
INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (1, '평가', 0);
INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (2, '급여', 0);
INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (3, '근태', 0);
INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (4, '수주/반품', 1);
INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (5, '영업회의', 1);
INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (6, '손익분석', 1);
INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (7, 'A', 2);
INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (8, 'B', 2);
INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (9, '가', 3);
INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (10, '나', 3);
INSERT INTO JOB (JOB_ID, JOB_GB, SYS_ID) values (11, '다', 3);


INSERT INTO CTMMNY_SYS (CTMMNY_SYS_ID, CTMMNY_CD,SYS_ID) VALUES (11110,'0',0);
INSERT INTO CTMMNY_SYS (CTMMNY_SYS_ID, CTMMNY_CD,SYS_ID) VALUES (11112,'0',1);
INSERT INTO CTMMNY_SYS (CTMMNY_SYS_ID, CTMMNY_CD,SYS_ID) VALUES (11113,'1',1);
INSERT INTO CTMMNY_SYS (CTMMNY_SYS_ID, CTMMNY_CD,SYS_ID) VALUES (11114,'1',3);
INSERT INTO CTMMNY_SYS (CTMMNY_SYS_ID, CTMMNY_CD,SYS_ID) VALUES (11115,'a101',0);
--

INSERT INTO RCPT_TREAT(RCPT_TREAT_ID) VALUES (111110);
INSERT INTO RCPT_TREAT(RCPT_TREAT_ID) VALUES (111111);
INSERT INTO RCPT_TREAT(RCPT_TREAT_ID) VALUES (111112);
INSERT INTO RCPT_TREAT(RCPT_TREAT_ID) VALUES (111113);
INSERT INTO RCPT_TREAT(RCPT_TREAT_ID) VALUES (111114);
INSERT INTO RCPT_TREAT(RCPT_TREAT_ID) VALUES (111115);
INSERT INTO RCPT_TREAT(RCPT_TREAT_ID) VALUES (111116);
INSERT INTO RCPT_TREAT(RCPT_TREAT_ID) VALUES (111117);
INSERT INTO RCPT_TREAT(RCPT_TREAT_ID) VALUES (111118);

INSERT INTO SR (SR_ID, STATUS, SBJCT, DSCR, REQR, REQ_TIM, REQ_GB, SYS_NM, JOB_GB, CTMMNY, RCPT_TREAT_ID) values (111111, '진행중','이거왜 안될까요?', '내용2',11110,'2021-05-27 16:45:20', '오류', 'r-CoCoAna', '가','0',111111);
INSERT INTO SR (SR_ID, STATUS, SBJCT, DSCR, REQR, REQ_TIM, REQ_GB, SYS_NM, JOB_GB, CTMMNY, RCPT_TREAT_ID) values (111112, '해결','수정부탁드립니다.', '내용3',11111,'2021-04-29 15:35:20', '문의', 'SalesWorX', '수주/반품','0',111112);
INSERT INTO SR (SR_ID, STATUS, SBJCT, DSCR, REQR, REQ_TIM, REQ_GB, SYS_NM, JOB_GB, CTMMNY, RCPT_TREAT_ID) values (111113, '접수대기','상황에 대한 이해가 어렵습니다.', '내용4',11111,'2021-05-30 14:59:20', '데이터수정', 'r-PLANNA', 'A','0',111113);
INSERT INTO SR (SR_ID, STATUS, SBJCT, DSCR, REQR, REQ_TIM, REQ_GB, SYS_NM, JOB_GB, CTMMNY, RCPT_TREAT_ID) values (111114, '접수대기','해결부탁드립니다', '내용5',11111,'2021-04-29 18:45:20', '오류', 'r-PLANNA', 'B','1',111114);
INSERT INTO SR (SR_ID, STATUS, SBJCT, DSCR, REQR, REQ_TIM, REQ_GB, SYS_NM, JOB_GB, CTMMNY, RCPT_TREAT_ID) values (111115, '접수대기','해결부탁드립니다', '내용5',11111,'2021-04-29 18:45:20', '오류', 'r-PLANNA', 'A','1',111115);
INSERT INTO SR (SR_ID, STATUS, SBJCT, DSCR, REQR, REQ_TIM, REQ_GB, SYS_NM, JOB_GB, CTMMNY, RCPT_TREAT_ID) values (111116, '접수대기','상황에 대한 이해가 어렵습니다', '내용5',11110,'2021-04-29 18:45:20', '오류', 'SalesWorX', '수주/반품','2',111116);
INSERT INTO SR (SR_ID, STATUS, SBJCT, DSCR, REQR, REQ_TIM, REQ_GB, SYS_NM, JOB_GB, CTMMNY, RCPT_TREAT_ID) values (111117, '접수대기','상황에 대한 이해가 어렵습니다', '내용5',11111,'2021-04-29 18:45:20', '오류', 'r-CoCoAna', '가','3',111117);
INSERT INTO SR (SR_ID, STATUS, SBJCT, DSCR, REQR, REQ_TIM, REQ_GB, SYS_NM, JOB_GB, CTMMNY ,RCPT_TREAT_ID) values (111118, '접수대기','이거왜 안될까요', '내용5',11110,'2021-04-29 18:45:20', '오류', 'PeopleWorX', '평가','4',111118);
INSERT INTO SR (SR_ID, STATUS, SBJCT, DSCR, REQR, REQ_TIM, REQ_GB, SYS_NM, JOB_GB, CTMMNY, RCPT_TREAT_ID) values (111119, '접수대기','이게 왜 안나올까요', '내용1',11110,'2021-05-24 20:21:00', '데이터수정', 'PeopleWorX', '인사','0',111110);


INSERT INTO CHTT_ROOM(CHTT_ROOM_ID,SYS_NM,STATUS,USER_ID) VALUES(1000,'PeopleWorX','진행중',11110);
INSERT INTO CHTT(CHTT_ID,CHTT_CREAT_DATE,CONVRS,CONVRS_TYPE,CHTT_ROOM_ID,USER_ID) VALUES(11110,'2021-05-30 14:59:20','어서오세요 HelpWorX에 대한 상담을 시작합니다','JOIN',1000,11112);
INSERT INTO CHTT(CHTT_ID,CHTT_CREAT_DATE,CONVRS,CONVRS_TYPE,CHTT_ROOM_ID,USER_ID) VALUES(11111,'2021-05-30 14:59:20','안녕하세요 저 좀 도와주세요','CHAT',1000,11110);
