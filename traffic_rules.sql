CREATE TABLE ANSWER_CATEGORY (
  ID INTEGER PRIMARY KEY     NOT NULL,
  DESCRIPTION        TEXT    NOT NULL
);

CREATE TABLE ANSWER (
  ID INTEGER PRIMARY KEY     NOT NULL,
  DESCRIPTION        TEXT    NOT NULL,
  ANSWER_CATEGORY           INTEGER    NOT NULL,
  FOREIGN KEY(ANSWER_CATEGORY) REFERENCES ANSWER_CATEGORY(ID)
);

CREATE TABLE QUESTION (
  ID INTEGER PRIMARY KEY     NOT NULL,
  DESCRIPTION        TEXT    NOT NULL,
  ANSWER_CATEGORY           INTEGER    NOT NULL,
  FOREIGN KEY(ANSWER_CATEGORY) REFERENCES ANSWER_CATEGORY(ID),
  CORRECT_ANSWER_ID          INTEGER NOT NULL,
  FOREIGN KEY(CORRECT_ANSWER_ID) REFERENCES ANSWER(ID)
);

