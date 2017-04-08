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
  IMAGE_URL TEXT,
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
  Aggregated statistics, for analytics purposes. This data can be also computed dynamically from other tables,
  but it is easier if we store it and update it so that we don't have to compute it every time
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

  QUESTIONS_TOTAL INTEGER,
  QUESTIONS_ANSWERED INTEGER,
  QUESTIONS_CORRECT INTEGER,

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
  FOREIGN KEY (TEST_ID) REFERENCES TEST(ID),
  FOREIGN KEY (STUDENT_ID) REFERENCES USER(ID),
  FOREIGN KEY(QUESTION_ID) REFERENCES QUESTION(ID),
  FOREIGN KEY(ANSWER_ID) REFERENCES ANSWER(ID)
);


INSERT INTO ROLE (ID, DESCRIPTION) VALUES (1, "Admin");
INSERT INTO ROLE (ID, DESCRIPTION) VALUES (2, "Teacher");
INSERT INTO ROLE (ID, DESCRIPTION) VALUES (3, "Student");

INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, IMAGE_URL, ROLE_ID, PASSWORD) VALUES (1, "Andrey", "Petrov", "http://lh3.googleusercontent.com/-bjgJUDNlFhM/AAAAAAAAAAI/AAAAAAAAAAA/AKB_U8vGzayOkjkrL9TY_lHaJMz0HRBvjQ/s64-c-mo/photo.jpg", 1, "parola");
INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, IMAGE_URL, ROLE_ID, PASSWORD) VALUES (2, "Aleksandar", "Petrov", "http://orig04.deviantart.net/b250/f/2009/330/e/8/smiley_face_avatar_by_pixeltwist.gif", 1, "parola");
INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, IMAGE_URL, ROLE_ID, PASSWORD) VALUES (3, "Учителко", "Учителков", NULL, 2, "parola");
INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, IMAGE_URL, ROLE_ID, PASSWORD) VALUES (4, "Йордан", "Илиев", NULL, 3, "parola");
INSERT INTO USER (ID, FIRST_NAME, LAST_NAME, IMAGE_URL, ROLE_ID, PASSWORD) VALUES (5, "Теодор", "Стаматов", NULL, 3, NULL );


INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (1, "Обща");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (2, "Веригата на велосипеда 1");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (3, "Веригата на велосипеда 2");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (4, "Буташ велосипед");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (5, "Няма тротоар или банкет. Велосипед");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (6, "Няма тротоар или банкет. Велосипед");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (7, "Няма тротоар или банкет. Велосипед");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (8, "Няма тротоар или банкет. Велосипед");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (9, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (10, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (11, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (12, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (13, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (14, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (15, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (16, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (17, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (18, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (19, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (20, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (21, "ДА или НЕ");
INSERT INTO ANSWER_POOL (ID, DESCRIPTION) VALUES (22, "ДА или НЕ");



INSERT INTO QUESTION_CATEGORY (ID, DESCRIPTION) VALUES (1, "Кръстовище");
INSERT INTO QUESTION_CATEGORY (ID, DESCRIPTION) VALUES (2, "Велосипед");
INSERT INTO QUESTION_CATEGORY (ID, DESCRIPTION) VALUES (3, "Пътни знаци");
INSERT INTO QUESTION_CATEGORY (ID, DESCRIPTION) VALUES (4, "Светофар");
INSERT INTO QUESTION_CATEGORY (ID, DESCRIPTION) VALUES (5, "Карта");


INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (1, "Какво означава този знак?", "sign_A25.png", 3, 1, 1);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (2, "Какво означава този знак?", "sign_A26.png", 3, 1, 3);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (3, "Какво означава този знак?", "sign_A27.png", 3, 1, 5);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (4, "Какво означава този знак?", "sign_A28.png", 3, 1, 4);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (5, "Какво означава този знак?", "sign_A29.png", 3, 1, 2);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (6, "Какво означава този знак?", "sign_B1.png", 3, 1, 6);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (7, "Какво означава този знак?", "sign_B2.png", 3, 1, 9);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (8, "Какво означава този знак?", "sign_B3.png", 3, 1, 7);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (9, "Какво означава този знак?", "sign_B4.png", 3, 1, 8);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (10, "Този сигнал на регулировчика означава?", "cop_S1.png", 1, 2, 10);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (11, "Забранява ли регулировчика на пешеходците и водачите да минават през кръстовището?", "cop_S1.png", 1, 3, 15);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (12, "Дясната ръка на регулировчика, протегната хоризонтално напред, разрешава ли на пешеходците да преминават зад гърба му?", "cop_S2.png", 1, 3, 15);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (13, "Този сигнал на регулировчика означава:", "cop_S3.png", 1, 2, 11);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (14, "Този сигнал на регулировчика отговаря на следния сигнал на светофара:", "cop_S1.png", 1, 4, 17);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (15, "Този сигнал на регулировчика отговаря на следния сигнал на светофара:", "cop_S3.png", 1, 4, 19);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (16, "Лява ръка, изпъната хоризонтално встрани се подава при?", NULL, 2, 5, 21);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (17, "Дясна ръка, изпъната хоризонтално встрани се подава при?", NULL, 2, 5, 20);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (18, "Как ще направиш ляв завой на оживено кръстовище?", NULL, 2, 6, 23);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (19, "Може ли да влияе на равновесието и на безопасното управление на велосипеда една ненапомпена гума?", NULL, 2, 3, 15);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (20, "Може ли да влияе на равновесието и на безопасното управление на велосипеда нерегулирани добре седалка и кормило?", NULL, 2, 3, 15);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (21, "Може ли да влияе на равновесието и на безопасното управление на велосипеда един голям товар на багажника?", NULL, 2, 3, 15);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (22, "Когато буташ велосипед, тогава, според ЗДвП, ти си?", NULL, 2, 7, 26);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (23, "Когато няма тротоар или банкет, или те не могат да бъдат използвани, тогава къде ще буташ велосипеда си?", NULL, 2, 8, 28);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (24, "Веригата на велосипеда:", NULL, 2, 9, 30);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (25, "Веригата на велосипеда:", NULL, 2, 10, 33);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (26, "Има ли части на велосипеда, които се смазват с масло?", NULL, 2, 3, 15);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (27, "Има ли части на велосипеда, които се смазват с грес?", NULL, 2, 3, 15);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (28, "Равномерното действие на крачната спирачка зависи ли от правилното опъване на веригата?", NULL, 2, 3, 15);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (29, "Равномерното действие на ръчните спирачки зависи ли от центровката на колелата?", NULL, 2, 3, 15);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (30, "Какво означава този знак?", "sign_V1.png", 3, 11, 35);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (31, "Какво означава този знак?", "sign_V2.png", 3, 11, 36);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (32, "Има ли право велосипедистът да управлява, без да държи кормилото с ръце, както и да освобождава педалите?", NULL, 2, 3, 16);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (33, "Има ли право велосипедистът да се учи да кара по платното за движение?", NULL, 2, 3, 16);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (34, "Има ли право велосипедистът да се движи на повече от 1м. от края на платното?", NULL, 2, 3, 16);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (35, "Има ли право велосипедистът да се движи близо до ППС или да се държи за него?", NULL, 2, 3, 16);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (36, "Има ли право велосипедистът да превозва дете до 7-годишна възраст без допълнителна седалка?", NULL, 2, 3, 16);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (37, "Има ли право велосипедистът да превозва товар, който излиза на повече от половин метър от габаритите на велосипеда?", NULL, 2, 3, 16);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (38, "Има ли право велосипедистът да се движи в група за тренировки или поход без придружително МПС?", NULL, 2, 3, 16);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (39, "Как се нарича това кръстовище?", "crossroad_01.png", 2, 12, 38);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (40, "Как се нарича това кръстовище?", "crossroad_02.png", 2, 12, 39);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (41, "Как се нарича това кръстовище?", "crossroad_03.png", 2, 12, 40);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (42, "Как се нарича това кръстовище?", "crossroad_04.png", 2, 12, 42);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (43, "Как се нарича това кръстовище?", "crossroad_05.png", 2, 12, 41);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (44, "Как се регулира движението на това кръстовище?", "cop_S4.png", 2, 13, 43);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (45, "Как се регулира движението на това кръстовище?", "crossroad_06.png", 2, 13, 44);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (46, "Как се регулира движението на това кръстовище?", "crossroad_07.png", 2, 13, 45);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (47, "Последователната смяма на светлините на сфетовара е следната: Червена светлина, червена и жълта (едновременно светещи), зелена светлина, жълта светлина?", NULL, 4, 3, 15);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (48, "Сигналът на допълнителната секция означава, че атвомобилите могат да продължат движението си:", "trafficlight_01.png", 4, 14, 46);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (49, "Най-отгоре на светофара светлината е?", "trafficlight_02.png", 4, 15, 50);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (50, "По средата на светофара светлината е?", "trafficlight_02.png", 4, 15, 49);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (51, "Най-отдолу на светофара светлината е?", "trafficlight_02.png", 4, 15, 51);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (52, "Светлините на пешеходния светофар са подредени:", NULL, 4, 16, 52);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (53, "Червената светлина на пешеходния светофар е:", NULL, 4, 17, 54);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (54, "Зелената светлина на пешеходния светофар е:", NULL, 4, 17, 55);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (55, "Колко секции има пешеходния светофар?", NULL, 4, 18, 57);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (56, "Как подава сигнали регулировчикът?", NULL, 4, 19, 60);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (57, "Регулировчикът може да използва допълнително:", NULL, 4, 20, 62);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (58, "Какво означава този знак:", "zebra.png", 3, 21, 65);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (59, "Какво означава този знак:", "podlez.png", 3, 21, 66);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (60, "Какво означава този знак:", "busstop.png", 3, 21, 68);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (61, "Коя е тази сграда?", "assets/img/tiles/buildings/mistral.png", 5, 22, 69);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (62, "Коя е тази сграда?", NULL, 5, 22, 70);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (63, "Коя е тази сграда?", NULL, 5, 22, 71);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (64, "Коя е тази сграда?", NULL, 5, 22, 72);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (65, "Коя е тази сграда?", NULL, 5, 22, 73);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (66, "Коя е тази сграда?", NULL, 5, 22, 74);
INSERT INTO QUESTION (ID, DESCRIPTION, IMAGE_URL, QUESTION_CATEGORY_ID, ANSWER_POOL_ID, CORRECT_ANSWER_ID) VALUES (67, "Коя е тази сграда?", NULL, 5, 22, 75);



INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (1, "Кръстовище на равнозначни пътища", 1);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (2, "Кръстовище с кръгово движение", 1);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (3, "Кръстовище с път без предимство", 1);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (4, "Кръстовище с път без предимство отляво", 1);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (5, "Кръстовище с път без предимство отдясно", 1);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (6, "Пропусни движещите се по пътя с предимство", 1);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (7, "Път с предимство", 1);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (8, "Край на пътя с предимство", 1);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (9, "Спри! Пропусни движещите се по пътя с предимство", 1);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (10, "Внимание! Спри!", 2);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (11, "Преминаването е разрешено", 2);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (12, "Преминаването отдясно е забранено", 2);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (13, "Преминаването отдясно е разрешено", 2);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (14, "Преминаването е забранено", 2);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (15, "Да", 3);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (16, "Не", 3);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (17, "Червен", 4);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (18, "Жълт", 4);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (19, "Зелен", 4);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (20, "Завиване надясно", 5);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (21, "Завиване наляво", 5);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (22, "Спиране", 5);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (23, "Ще сляза от велосипеда, ще се кача на тротоара и ще пресека по пешеходните пътеки, като го бутам", 6);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (24, "Ще го направя качен на велосипед", 6);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (25, "Ще сляза от велосипеда и ще го бутам по платното", 6);


INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (26, "Пешеходец", 7);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (27, "Велосипедист", 7);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (28, "По платното за движение, най-близо до дясната му граница", 8);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (29, "По платното за движение, най-близо до лявата му граница", 8);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (30, "Трябва да се обтяга периодично", 9);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (31, "Трябва да се отпуспа периодично", 9);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (32, "Нито се обтяга, нито се отпуска", 9);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (33, "Се смазва периодично", 10);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (34, "Не се смазва", 10);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (35, "Забранено е влизането на ППС", 11);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (36, "Забранено е влизането на ППС в двете посоки", 11);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (37, "Позволено е влизането на ППС", 11);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (38, "Кръстообразно", 12);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (39, "Звездообразно", 12);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (40, "Т-образно", 12);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (41, "Кръгово", 12);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (42, "У-образно", 12);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (43, "От регулировчик", 13);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (44, "От пътни знаци", 13);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (45, "От светофари", 13);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (46, "Само в показаната със стрелка посока", 14);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (47, "Спират", 14);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (48, "Изчакват зелената светлина на основния светофар", 14);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (49, "Жълта", 15);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (50, "Червена", 15);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (51, "Зелена", 15);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (52, "Вертикално", 16);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (53, "Хоризонтално", 16);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (54, "Отгоре", 17);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (55, "Отдолу", 17);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (56, "Една", 18);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (57, "Две", 18);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (58, "Три", 18);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (59, "Четири", 18);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (60, "С положението на тялото си и ръцете си", 19);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (61, "С краката и главата си", 19);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (62, "Регулировъчна палка и полицейска свирка", 20);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (63, "Знаме", 20);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (64, "Колело", 20);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (65, "Пешеходна пътека", 21);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (66, "Подлез", 21);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (67, "Забранено за пешеходци", 21);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (68, "Автобусна спирка", 21);

INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (69, "Заведение 'Мистрал'", 22);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (70, "РУМ 'Тракия'", 22);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (71, "Кино Фейсис", 22);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (72, "Пето РПУ", 22);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (73, "Заведение Енканто", 22);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (74, "Монумент Хан Крум", 22);
INSERT INTO ANSWER (ID, DESCRIPTION, ANSWER_POOL_ID) VALUES (75, "Автосвят", 22);

INSERT INTO TEST (ID, DESCRIPTION, TEACHER_ID) VALUES  (1, "Тест за движение по пътищата", 3);

INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (1, "Първа секция", 1);
INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (2, "Втора секция", 1);
INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (3, "Трета секция", 1);
INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (4, "Четвърта секция", 1);
INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (5, "Пета секция", 1);
INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (6, "Шеста секция", 1);
INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (7, "Седма секция", 1);
INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (8, "Осма секция", 1);
INSERT INTO TEST_SECTION (ID, DESCRIPTION, TEST_ID) VALUES (9, "Девета секция", 1);


INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 1);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 2);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 3);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 4);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 5);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 6);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 7);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 8);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 9);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 10);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 11);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 12);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (1, 13);

INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (2, 14);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (2, 15);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (2, 16);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (2, 17);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (2, 18);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (2, 19);


INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 20);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 21);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 22);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 23);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 24);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 25);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 26);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 27);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 28);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 29);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (3, 30);

INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (4, 31);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (4, 32);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (4, 33);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (4, 34);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (4, 35);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (4, 36);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (4, 37);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (4, 38);

INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (5, 39);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (5, 40);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (5, 41);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (5, 42);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (5, 43);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (5, 44);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (5, 45);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (5, 46);


INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 47);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 48);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 49);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 50);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 51);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 52);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 53);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 54);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 55);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 56);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 57);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 58);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (6, 59);

INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (7, 60);

INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (8, 61);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (8, 62);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (8, 63);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (8, 64);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (8, 65);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (8, 66);
INSERT INTO TEST_SECTION_QUESTION_XREF (TEST_SECTION_ID, QUESTION_ID) VALUES (8, 67);




INSERT INTO TEST_RESULT_ANSWERS_FACTS (TEST_ID, STUDENT_ID, QUESTION_ID, ANSWER_ID, IS_CORRECT, DATETIME) VALUES (1, 4, 1, 2, 1, DateTime('now'));
INSERT INTO TEST_RESULT_ANSWERS_FACTS (TEST_ID, STUDENT_ID, QUESTION_ID, ANSWER_ID, IS_CORRECT, DATETIME) VALUES (1, 4, 2, 5, 1, DateTime('now'));
INSERT INTO TEST_RESULT_ANSWERS_FACTS (TEST_ID, STUDENT_ID, QUESTION_ID, ANSWER_ID, IS_CORRECT, DATETIME) VALUES (1, 4, 3, 5, 0, DateTime('now'));
INSERT INTO TEST_RESULT_ANSWERS_FACTS (TEST_ID, STUDENT_ID, QUESTION_ID, ANSWER_ID, IS_CORRECT, DATETIME) VALUES (1, 4, 4, 5, 0, DateTime('now'));
INSERT INTO TEST_RESULT_ANSWERS_FACTS (TEST_ID, STUDENT_ID, QUESTION_ID, ANSWER_ID, IS_CORRECT, DATETIME) VALUES (1, 4, 5, 5, 0, DateTime('now'));
INSERT INTO TEST_RESULT_ANSWERS_FACTS (TEST_ID, STUDENT_ID, QUESTION_ID, ANSWER_ID, IS_CORRECT, DATETIME) VALUES (1, 4, 6, 5, 0, DateTime('now'));
INSERT INTO TEST_RESULT_ANSWERS_FACTS (TEST_ID, STUDENT_ID, QUESTION_ID, ANSWER_ID, IS_CORRECT, DATETIME) VALUES (1, 4, 7, 5, 0, DateTime('now','start of month','+1 month','-1 day'));














/**SELECTS**/

/* All tests */
SELECT t.*,
  ts.id TEST_SECTION_ID,
  ts.description TEST_SECTION_DESCRIPTION,
  q.id QUESTION_ID,
  q.description QUESTION_DESCRIPTION,
  q.correct_answer_id QUESTION_CORRECT_ANSWER_ID,
  q.IMAGE_URL QUESTION_IMAGE_URL,
  a.id ANSWER_ID,
  a.description ANSWER_DESCRIPTION,
  qc.description QUESTION_CATEGORY_DESCRIPTION
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
ORDER BY t.ID, TEST_SECTION_ID, q.id, a.id;


/* Test with answers for one user */
SELECT t.*,
  ts.id TEST_SECTION_ID,
  ts.description TEST_SECTION_DESCRIPTION,
  q.id QUESTION_ID,
  q.description QUESTION_DESCRIPTION,
  q.correct_answer_id QUESTION_CORRECT_ANSWER_ID,
  q.IMAGE_URL QUESTION_IMAGE_URL,
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




SELECT t.*,
  ts.id TEST_SECTION_ID,
  ts.description TEST_SECTION_DESCRIPTION,
  q.id QUESTION_ID,
  q.description QUESTION_DESCRIPTION,
  q.correct_answer_id QUESTION_CORRECT_ANSWER_ID,
  q.IMAGE_URL QUESTION_IMAGE_URL,
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
    ON t.ID = traf.TEST_ID AND q.id = traf.QUESTION_ID AND traf.STUDENT_ID = 1
WHERE t.ID = 1
ORDER BY t.ID, TEST_SECTION_ID, q.id, a.id;



/* -- all questions --*/
SELECT COUNT(DISTINCT QUESTION_ID) AS QUESTIONS_COUNT FROM TEST t
  LEFT JOIN TEST_SECTION ts
    ON t.ID = ts.TEST_ID
  LEFT JOIN TEST_SECTION_QUESTION_XREF tsqx
    ON ts.ID = tsqx.TEST_SECTION_ID
  LEFT JOIN QUESTION q
    ON tsqx.QUESTION_ID = q.ID
WHERE t.ID = 1;


/* correct and answered */
SELECT SUM(IS_CORRECT) as QUESTIONS_ANSWERED_CORRECTLY_COUNT,
  COUNT(*) as QUESTIONS_ANSWERED_COUNT,
  COALESCE(MIN(DATETIME), 'N/A') as FIRST_ANSWERED_QUESTION_DATETIME,
  COALESCE(MAX(DATETIME), 'N/A') as LAST_ANSWERED_QUESTION_DATETIME
FROM TEST_RESULT_ANSWERS_FACTS
WHERE TEST_ID = 1 AND STUDENT_ID = 4;

/*DELETE FROM TEST_RESULT_ANSWERS_FACTS;*/