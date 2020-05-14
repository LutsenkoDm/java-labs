--DROP TABLE IF EXISTS clients;
CREATE TABLE clients
(
    id            NUMBER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstName     VARCHAR2(20),
    lastName      VARCHAR2(20),
    patherName    VARCHAR2(20),
    passportSeria VARCHAR2(20),
    passportNum   VARCHAR2(20)
);

--DROP TABLE IF EXISTS bookTypes;
CREATE TABLE bookTypes
(
    id                  NUMBER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR2(50),
    cnt                 NUMBER,
    fine                NUMBER(18, 2),
    dayCount            NUMBER
);

--DROP TABLE IF EXISTS books;
CREATE TABLE books
(
    id     NUMBER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR2(50),
    cnt    NUMBER,
    typeId NUMBER,

    FOREIGN KEY (typeId) REFERENCES bookTypes (id)
);

--DROP TABLE IF EXISTS journalRecord;
CREATE TABLE journalRecord
(
    id       NUMBER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bookID   NUMBER NOT NULL,
    clientId NUMBER NOT NULL,
    dateBeg  TIMESTAMP(3),
    dateEnd  TIMESTAMP(3),
    dateRet  TIMESTAMP(3),

    FOREIGN KEY (bookID) REFERENCES books (id),
    FOREIGN KEY (clientId) REFERENCES clients (id)
);
