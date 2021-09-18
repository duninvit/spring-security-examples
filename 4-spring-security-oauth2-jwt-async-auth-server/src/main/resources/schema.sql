DROP TABLE IF EXISTS `public`.`user`;

CREATE TABLE IF NOT EXISTS `public`.`user`
(
    id       INT         NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(150) NOT NULL,
    authority VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);
