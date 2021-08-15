DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) NULL,
    password TEXT        NULL
);

DROP TABLE IF EXISTS otp;

CREATE TABLE IF NOT EXISTS otp
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) NULL,
    code     VARCHAR(45) NULL
);

INSERT INTO user (username, password)
VALUES ('user', 'password');

