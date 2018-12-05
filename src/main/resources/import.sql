INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (1, 'hee', 'jeong', '희정', 'gmlwjd@gmail.com');
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (2, 'hee2', 'jeong', '희정2', 'gmlwjd2@gmail.com');

INSERT INTO QUESTION (id, writer_id, title, contents, create_date, count_of_answer) VALUES  (1, 1, '테스트 데이터 제목', '테스트 데이터 내용 - hee', CURRENT_TIMESTAMP, 0);
INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATE_DATE, COUNT_OF_ANSWER) VALUES  (2, 2, '테스트 데이터 제목2', '테스트 데이터 내용 - hee2', CURRENT_TIMESTAMP, 0);