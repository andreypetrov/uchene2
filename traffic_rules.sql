DROP TABLE IF EXISTS ROLE;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS ANSWER_POOL;
DROP TABLE IF EXISTS ANSWER;
DROP TABLE IF EXISTS QUESTION_CATEGORY;
DROP TABLE IF EXISTS QUESTION;
DROP TABLE IF EXISTS TEST;
DROP TABLE IF EXISTS TEST_SECTION;
DROP TABLE IF EXISTS TEST_SECTION_QUESTION_XREF;
DROP TABLE IF EXISTS TEST_RESULT;
DROP TABLE IF EXISTS TEST_RESULT_ANSWERS_FACTS;

CREATE TABLE ROLE (
  ID INTEGER PRIMARY KEY NOT NULL,
  DESCRIPTION TEXT NOT NULL
);

CREATE TABLE USER (
  ID INTEGER PRIMARY KEY NOT NULL,
  FIRST_NAME TEXT NOT NULL,
  LAST_NAME TEXT NOT NULL,
  IMAGE_URL TEXT,
  ROLE_ID INTEGER NOT NULL,
  PASSWORD TEXT,
  FOREIGN KEY(ROLE_ID) REFERENCES ROLE(ID)
);

/*
 * Category of the question. Usually but not necessarily there will be 1 to 1 relationship between this category and a question
 */
CREATE TABLE ANSWER_POOL (
  ID INTEGER PRIMARY KEY     NOT NULL,
  DESCRIPTION        TEXT    NOT NULL
);

CREATE TABLE ANSWER (
  ID INTEGER PRIMARY KEY     NOT NULL,
  DESCRIPTION        TEXT    NOT NULL,
  ANSWER_POOL_ID           INTEGER    NOT NULL,
  FOREIGN KEY(ANSWER_POOL_ID) REFERENCES ANSWER_POOL(ID)
);

CREATE TABLE QUESTION_CATEGORY (
  ID INTEGER PRIMARY KEY     NOT NULL,
  DESCRIPTION        TEXT    NOT NULL
);

CREATE TABLE QUESTION (
  ID INTEGER PRIMARY KEY        NOT NULL,
  DESCRIPTION        TEXT       NOT NULL,
  QUESTION_CATEGORY_ID        INTEGER    NOT NULL,
  ANSWER_POOL_ID     INTEGER    NOT NULL,
  CORRECT_ANSWER_ID  INTEGER    NOT NULL,
  FOREIGN KEY(QUESTION_CATEGORY_ID) REFERENCES QUESTION_CATEGORY(ID),
  FOREIGN KEY(ANSWER_POOL_ID) REFERENCES ANSWER_POOL(ID),
  FOREIGN KEY(CORRECT_ANSWER_ID) REFERENCES ANSWER(ID)
);



/**
 * Every Test has Text sections which contain a list of questions
 */
CREATE TABLE TEST (
  ID INTEGER PRIMARY KEY NOT NULL,
  DESCRIPTION        TEXT    NOT NULL,
  TEACHER_ID INTEGER NOT NULL,
  FOREIGN KEY(TEACHER_ID) REFERENCES USER(ID)
);

CREATE TABLE TEST_SECTION (
  ID INTEGER PRIMARY KEY NOT NULL,
  DESCRIPTION        TEXT    NOT NULL,
  TEST_ID            INTEGER NOT NULL,
  FOREIGN KEY(TEST_ID) REFERENCES TEST(ID)
);

/**
 * We can have the same question in several sections, therefore we need a many to many xref table between test sections and questions
 */
CREATE TABLE TEST_SECTION_QUESTION_XREF (
  TEST_SECTION_ID INTEGER NOT NULL,
  QUESTION_ID INTEGER NOT NULL,
  FOREIGN KEY (TEST_SECTION_ID) REFERENCES TEST_SECTION(ID),
  FOREIGN KEY (QUESTION_ID) REFERENCES  QUESTION(ID)
);

/**
  Test results are user specific
  One test_result per user per test.
 */
CREATE TABLE TEST_RESULT (
  ID INTEGER PRIMARY KEY NOT NULL,
  STUDENT_ID INTEGER NOT NULL,
  TEST_ID INTEGER NOT NULL,
  DATETIME_START INTEGER NOT NULL,
  DATETIME_END INTEGER NOT NULL,
  IS_COMPLETED INTEGER NOT NULL,
  FOREIGN KEY(STUDENT_ID) REFERENCES USER(ID),
  FOREIGN KEY(TEST_ID) REFERENCES TEST(ID)
);

/* Table in which we keep the answer of every question for every test */
CREATE TABLE TEST_RESULT_ANSWERS_FACTS (
  TEST_ID INTEGER NOT NULL ,
  STUDENT_ID INTEGER NOT NULL,
  QUESTION_ID INTEGER NOT NULL,
  ANSWER_ID INTEGER NOT NULL,
  IS_CORRECT INTEGER NOT NULL,
  DATETIME TEXT NOT NULL,
  PRIMARY KEY (TEST_ID, STUDENT_ID, QUESTION_ID),
  FOREIGN KEY (TEST_ID) REFERENCES TEST_RESULT(ID),
  FOREIGN KEY (STUDENT_ID) REFERENCES TEST_RESULT(ID),
  FOREIGN KEY(QUESTION_ID) REFERENCES QUESTION(ID),
  FOREIGN KEY(ANSWER_ID) REFERENCES ANSWER(ID)
);


INSERT INTO ROLE (ID, DESCRIPTION) VALUES (1, "Admin");
INSERT INTO ROLE (ID, DESCRIPTION) VALUES (2, "Teacher");
INSERT INTO ROLE (ID, DESCRIPTION) VALUES (3, "Student");

INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, IMAGE_URL, ROLE_ID, PASSWORD) VALUES (1, "Andrey", "Petrov", "http://lh3.googleusercontent.com/-bjgJUDNlFhM/AAAAAAAAAAI/AAAAAAAAAAA/AKB_U8vGzayOkjkrL9TY_lHaJMz0HRBvjQ/s64-c-mo/photo.jpg", 1, "parola");
INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, IMAGE_URL, ROLE_ID, PASSWORD) VALUES (2, "Aleksandar", "Petrov", NULL, 1, "parola");
INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, IMAGE_URL, ROLE_ID, PASSWORD) VALUES (3, "Учителко", "Учителков", NULL, 2, "parola");
INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, IMAGE_URL, ROLE_ID, PASSWORD) VALUES (4, "Йордан", "Илиев", NULL, 3, "parola");
INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, IMAGE_URL, ROLE_ID, PASSWORD) VALUES (5, "Теодор", "Стаматов", NULL, 3, NULL );


INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (1, "Обща");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (2, "Веригата на велосипеда 1");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (3, "Веригата на велосипеда 2");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (4, "Буташ велосипед");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (5, "Няма тротоар или банкет. Велосипед");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (10, "ДА или НЕ");



INSERT INTO QUESTION_CATEGORY (ID, DESCRIPTION) VALUES (1, "Кръстовище");
INSERT INTO QUESTION_CATEGORY (ID, DESCRIPTION) VALUES (2, "Велосипед");

INSERT INTO QUESTION (ID, DESCRIPTION, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (1, "Веригата на велосипеда:", 1, 2, 1);
INSERT INTO QUESTION (ID, DESCRIPTION, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (2, "Веригата на велосипеда:", 1, 3, 4);
INSERT INTO QUESTION (ID, DESCRIPTION, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (3, "Когато буташ велосипед, тогава според ЗДвП, ти си:", 1, 4, 6);
INSERT INTO QUESTION (ID, DESCRIPTION, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (4, "Когато няма тротоар или банкет, или те не могат да бъдат използвани, тогава къде ще буташ велосипеда си?", 1, 5, 7);
INSERT INTO QUESTION (ID, DESCRIPTION, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (5, "Може ли една ненапомпена гума да влияе на равновесието и на безопасното управление на велосипеда?", 2, 10, 11);
INSERT INTO QUESTION (ID, DESCRIPTION, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (6, "Може ли нерегулирани добре седалка и кормило да влияят на равновесието и на безопасното управление на велосипеда?", 2, 10, 11);
INSERT INTO QUESTION (ID, DESCRIPTION, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (7, "Може ли голям товар на багажника да влияе на равновесието и на безопасното управление на велосипеда?", 2, 10, 11);


INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (1, "трябва да се обтяга периодично", 2);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (2, "трябва да се отпуска периодично", 2);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (3, "нито се обтяга, нито се отпуска", 2);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (4, "се смазва периодично", 3);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (5, "не се смазва", 3);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (6, "велосипедист", 4);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (7, "пешеходец", 4);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (8, "По платното за движение, най-близо до дясната му граница", 5);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (9, "По платното за движение най-близо до лявата му граница", 5);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (11, "Да", 10);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (12, "Не", 10);

INSERT INTO TEST (ID, DESCRIPTION, TEACHER_ID) VALUES  (1, "Тест за движение по пътищата", 3);

INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (1, "Първа секция", 1);
INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (2, "Втора секция", 1);
INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (3, "Трета секция", 1);

INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 1);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 2);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (2, 3);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (2, 4);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 5);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 6);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 7);


INSERT INTO TEST_RESULT_ANSWERS_FACTS (TEST_ID, STUDENT_ID, QUESTION_ID, ANSWER_ID, IS_CORRECT, DATETIME) VALUES (1, 4, 1, 2, 0, DateTime('now'));
INSERT INTO TEST_RESULT_ANSWERS_FACTS (TEST_ID, STUDENT_ID, QUESTION_ID, ANSWER_ID, IS_CORRECT, DATETIME) VALUES (1, 4, 2, 4, 1,DateTime('now'));

/**SELECTS**/
SELECT t.*,
  ts.id TEST_SECTION_ID,
  ts.description TEST_SECTION_DESCRIPTION,
  q.id QUESTION_ID,
  q.description QUESTION_DESCRIPTION,
  a.id ANSWER_ID,
  a.description ANSWER_DESCRIPTION,
  qc.description QUESTION_CATEGORY_DESCRIPTION,
  traf.ANSWER_ID GIVEN_ANSWER,
  traf.IS_CORRECT
FROM TEST t
  LEFT JOIN TEST_SECTION ts
    ON t.ID = ts.TEST_ID
  LEFT JOIN TEST_SECTION_QUESTION_XREF tsqx
    ON ts.ID = tsqx.TEST_SECTION_ID
  LEFT JOIN QUESTION q
    ON tsqx.QUESTION_ID = q.ID
  LEFT JOIN ANSWER_POOL ap
    ON ap.ID = q.ANSWER_POOL_ID
  LEFT JOIN ANSWER a
    ON ap.ID = a.ANSWER_POOL_ID
  LEFT JOIN QUESTION_CATEGORY qc
    ON q.QUESTION_CATEGORY_ID = qc.ID
  LEFT JOIN TEST_RESULT_ANSWERS_FACTS traf
    ON t.ID = traf.TEST_ID AND q.id = traf.QUESTION_ID AND traf.STUDENT_ID = 4
  WHERE t.ID = 2
ORDER BY t.ID, TEST_SECTION_ID, q.id, a.id;

