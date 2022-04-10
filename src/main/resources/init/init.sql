CREATE DATABASE demo1;
USE demo1;

-- CREATE TABLE
CREATE TABLE Book
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    author      VARCHAR(64)  NOT NULL,
    name        VARCHAR(64)  NOT NULL,
    price       DECIMAL      NOT NULL,
    createTime  DATETIME     NULL,
    description VARCHAR(128) NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
-- INSERT DATA
INSERT INTO Book (id, author, name, price, createTime, description)
VALUES (1, '金庸', '笑傲江湖', 13, '2020-12-19 15:26:51', '武侠小说');
INSERT INTO Book (id, author, name, price, createTime, description)
VALUES (2, '罗贯中', '三国演义', 14, '2020-12-19 15:28:36', '历史小说');

CREATE DATABASE demo2;
USE demo2;
-- CREATE TABLE
CREATE TABLE User
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(32) NULL,
    birthday DATE NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
-- INSERT DATA
INSERT INTO User (id, name, birthday) VALUES (1, '金庸', '1924-03-10');
INSERT INTO User (id, name, birthday) VALUES (2, '罗贯中', '1330-01-10');