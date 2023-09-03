DELETE FROM course.user_course_relation;
DELETE FROM course.course;
DELETE FROM main.users;
DELETE FROM main.department;
DELETE FROM main.article;
DELETE FROM main.comment;

INSERT INTO main.department(id, name) VALUES
    (1, 'computer'),
    (2, 'media'),
    (3, 'electronic');

INSERT INTO main.users(id, student_id, name, nickname, department_id, term) VALUES
    (1, 1001, 'Alpha', null, 1, 1),
    (2, 2002, 'Bravo', 'B', 2, 2),
    (3, 3003, 'Charlie', null, 2, 2),
    (4, 4004, 'Delta', 'D', 3, 3);

INSERT INTO course.course (id, name, term, created_at, updated_at) VALUES
    (1, 'eat paper', 15, '2023-07-27 10:00:00.000', '2023-07-27 10:00:00.000'),
    (2, 'do it', 15, '2023-07-28 10:00:00.000', '2023-07-28 10:00:00.000'),
    (3, 'im hungry', 14, '2023-07-29 10:00:00.000', '2023-07-29 10:00:00.000'),
    (4, 'choco', 16, '2023-07-30 10:00:00.000', '2023-07-30 10:00:00.000');

INSERT INTO course.user_course_relation(id, user_id, course_id, register_status) VALUES
    (1, 1, 1, 'ACTIVE'),
    (2, 2, 2, 'DROP'),
    (3, 3, 3, 'ACTIVE'),
    (4, 1, 4, 'ACTIVE'),
    (5, 2, 3, 'ACTIVE');

INSERT INTO main.article(id, user_id, topic, title, content) VALUES
    (1111, 1, 'CHAT', 'hello', '안녕하세요 저는'),
    (2222, 2, 'QUESTION', 'ask', '궁금한게 있어요'),
    (3333, 3, 'INFORMATION', 'information', '내일 학식은');

INSERT INTO main.comment(id, article_id, user_id, content) VALUES
   (1111, 1111, 2, '안녕하세요~~~'),
   (2222, 1111, 3, 'hi!'),
   (3333, 2222, 1, '이건 이렇고'),
   (4444, 3333, 3, '와 맛있겠다');