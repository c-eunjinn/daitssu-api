DELETE FROM notice.notice;
DELETE FROM notice.notice_fs;

INSERT INTO notice.notice(id, title, department_id, content, category, created_at, updated_at) VALUES
    (1,'공지사항1',1,'1번 공지 내용입니다!!','학사','1000-01-01 00:00:00','1000-01-01 00:00:00'),
    (2,'공지사항2',2,'2번 공지 내용입니다!!','구독','1000-01-01 00:00:00','1000-01-01 00:00:00'),
    (3,'공지사항3',3,'3번 공지 내용입니다!!','장학','1000-01-01 00:00:00','1000-01-01 00:00:00'),
    (4,'공지사항4',4,'4번 공지 내용입니다!!','국제교류','1000-01-01 00:00:00','1000-01-01 00:00:00');
