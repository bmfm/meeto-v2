-- MySQL Script generated by MySQL Workbench
-- Sun Oct 26 21:13:25 2014
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema meeto
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `meeto`;

-- -----------------------------------------------------
-- Schema meeto
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `meeto`
  DEFAULT CHARACTER SET utf8;
USE `meeto`;

-- -----------------------------------------------------
-- Table `meeto`.`meeting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meeto`.`meeting`;

CREATE TABLE IF NOT EXISTS `meeto`.`meeting` (
  `idmeeting` INT(11)      NOT NULL AUTO_INCREMENT,
  `title`     VARCHAR(45)  NOT NULL,
  `objective` VARCHAR(255) NOT NULL,
  `date`      DATETIME     NOT NULL,
  `location`  VARCHAR(45)  NOT NULL,
  PRIMARY KEY (`idmeeting`))
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `meeto`.`member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meeto`.`member`;

CREATE TABLE IF NOT EXISTS `meeto`.`member` (
  `idmember` INT(11)      NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(16)  NOT NULL,
  `email`    VARCHAR(127) NULL DEFAULT NULL,
  `password` VARCHAR(32)  NOT NULL,
  `online`   TINYINT(1)   NOT NULL DEFAULT 0,
  PRIMARY KEY (`idmember`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
  ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `meeto`.`action`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meeto`.`action`;

CREATE TABLE IF NOT EXISTS `meeto`.`action` (
  `idaction`    INT(11)      NOT NULL AUTO_INCREMENT,
  `idmeeting`   INT(11)      NOT NULL,
  `idmember`    INT(11)      NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `completed`   TINYINT(1)   NULL DEFAULT '0',
  PRIMARY KEY (`idaction`),
  INDEX `fk_action_member1_idx` (`idmember` ASC),
  INDEX `fk_action_meeting1_idx` (`idmeeting` ASC),
  CONSTRAINT `fk_action_meeting1`
  FOREIGN KEY (`idmeeting`)
  REFERENCES `meeto`.`meeting` (`idmeeting`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_action_member1`
  FOREIGN KEY (`idmember`)
  REFERENCES `meeto`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `meeto`.`agenda`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meeto`.`agenda`;

CREATE TABLE IF NOT EXISTS `meeto`.`agenda` (
  `idagenda`  INT(11) NOT NULL AUTO_INCREMENT,
  `idmeeting` INT(11) NOT NULL,
  PRIMARY KEY (`idagenda`),
  INDEX `fk_agenda_meeting1_idx` (`idmeeting` ASC),
  CONSTRAINT `fk_agenda_meeting1`
  FOREIGN KEY (`idmeeting`)
  REFERENCES `meeto`.`meeting` (`idmeeting`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `meeto`.`item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meeto`.`item`;

CREATE TABLE IF NOT EXISTS `meeto`.`item` (
  `iditem`      INT(11)      NOT NULL AUTO_INCREMENT,
  `idagenda`    INT(11)      NOT NULL,
  `name`        VARCHAR(127) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `keydecision` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`iditem`),
  INDEX `fk_item_agenda1_idx` (`idagenda` ASC),
  CONSTRAINT `fk_item_agenda1`
  FOREIGN KEY (`idagenda`)
  REFERENCES `meeto`.`agenda` (`idagenda`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `meeto`.`log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meeto`.`log`;

CREATE TABLE IF NOT EXISTS `meeto`.`log` (
  `idlog`    INT(11)  NOT NULL AUTO_INCREMENT,
  `iditem`   INT(11)  NOT NULL,
  `idmember` INT(11)  NOT NULL,
  `line`     LONGTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`idlog`),
  INDEX `fk_log_item1_idx` (`iditem` ASC),
  INDEX `fk_log_member1_idx` (`idmember` ASC),
  CONSTRAINT `fk_log_item1`
  FOREIGN KEY (`iditem`)
  REFERENCES `meeto`.`item` (`iditem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_log_member1`
  FOREIGN KEY (`idmember`)
  REFERENCES `meeto`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `meeto`.`meeting_member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meeto`.`meeting_member`;

CREATE TABLE IF NOT EXISTS `meeto`.`meeting_member` (
  `idmeeting` INT(11)    NOT NULL,
  `idmember`  INT(11)    NOT NULL,
  `accepted`  VARCHAR(4) NULL DEFAULT NULL,
  PRIMARY KEY (`idmeeting`, `idmember`),
  INDEX `fk_meeting_members_meeting_idx` (`idmeeting` ASC),
  INDEX `fk_meeting_members_member1_idx` (`idmember` ASC),
  CONSTRAINT `fk_meeting_members_meeting`
  FOREIGN KEY (`idmeeting`)
  REFERENCES `meeto`.`meeting` (`idmeeting`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_meeting_members_member1`
  FOREIGN KEY (`idmember`)
  REFERENCES `meeto`.`member` (`idmember`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `meeto`.`system_message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meeto`.`system_message`;

CREATE TABLE IF NOT EXISTS `meeto`.`system_message` (
  `idsystem_message` DATETIME    NOT NULL,
  `idmember`         INT         NULL DEFAULT NULL,
  `username`         VARCHAR(45) NULL DEFAULT NULL,
  `type`             VARCHAR(45) NULL DEFAULT NULL,
  `data`             LONGTEXT    NULL DEFAULT NULL,
  `date`             VARCHAR(45) NULL DEFAULT NULL,
  `time`             VARCHAR(45) NULL DEFAULT NULL,
  `desiredoutcome`   VARCHAR(45) NULL DEFAULT NULL,
  `list`             VARCHAR(45) NULL DEFAULT NULL,
  `location`         VARCHAR(45) NULL DEFAULT NULL,
  `timestamp`        VARCHAR(45) NULL DEFAULT NULL,
  `delivered`        INT         NOT NULL DEFAULT 0,
  PRIMARY KEY (`idsystem_message`))
  ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `meeto`.`meeting`
-- -----------------------------------------------------
START TRANSACTION;
USE `meeto`;
INSERT INTO `meeto`.`meeting` (`idmeeting`, `title`, `objective`, `date`, `location`)
VALUES (1, 'Estado da cozinha', 'Arranjar empresa responsável pela manutenção da mesma', '2015-05-15 9:00', 'Coimbra');
INSERT INTO `meeto`.`meeting` (`idmeeting`, `title`, `objective`, `date`, `location`) VALUES
  (2, 'Time tracking', 'Discutir a arquitectura da nova ferramenta de report de esforço.', '2014-04-18 10:15',
   'Taveiro');
INSERT INTO `meeto`.`meeting` (`idmeeting`, `title`, `objective`, `date`, `location`)
VALUES (3, 'Jantar de Natal', 'Brainstorming sobre ementa', '2014-12-10 15:45', 'Lisboa');
INSERT INTO `meeto`.`meeting` (`idmeeting`, `title`, `objective`, `date`, `location`) VALUES
  (4, 'Selecção Portuguesa', 'Discussão sobre a fraca performance da equipa Portuguesa no Mundial 2014',
   '2014-07-14 22:30', 'The Cloud');

COMMIT;


-- -----------------------------------------------------
-- Data for table `meeto`.`member`
-- -----------------------------------------------------
START TRANSACTION;
USE `meeto`;
INSERT INTO `meeto`.`member` (`idmember`, `username`, `email`, `password`, `online`)
VALUES (1, 'bruno', 'bruno', 'bruno', 0);
INSERT INTO `meeto`.`member` (`idmember`, `username`, `email`, `password`, `online`)
VALUES (2, 'luis', 'luis', 'luis', 0);
INSERT INTO `meeto`.`member` (`idmember`, `username`, `email`, `password`, `online`)
VALUES (3, 'catarina', 'catarina', 'catarina', 0);
INSERT INTO `meeto`.`member` (`idmember`, `username`, `email`, `password`, `online`)
VALUES (4, 'jose', 'jose', 'jose', 0);
INSERT INTO `meeto`.`member` (`idmember`, `username`, `email`, `password`, `online`)
VALUES (5, 'tome', 'tome', 'tome', 0);
INSERT INTO `meeto`.`member` (`idmember`, `username`, `email`, `password`, `online`)
VALUES (6, 'irene', 'irene', 'irene', 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `meeto`.`action`
-- -----------------------------------------------------
START TRANSACTION;
USE `meeto`;
INSERT INTO `meeto`.`action` (`idaction`, `idmeeting`, `idmember`, `description`, `completed`)
VALUES (1, 1, 1, 'Contactar empresa LIMPOTRONIC', 0);
INSERT INTO `meeto`.`action` (`idaction`, `idmeeting`, `idmember`, `description`, `completed`)
VALUES (2, 3, 2, 'Comprar diversas garrafas de vinho do Porto para oferecer às famílias', 0);
INSERT INTO `meeto`.`action` (`idaction`, `idmeeting`, `idmember`, `description`, `completed`)
VALUES (3, 2, 5, 'Avaliar preço de uma floating license do Enterprise Architect para a empresa.', 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `meeto`.`agenda`
-- -----------------------------------------------------
START TRANSACTION;
USE `meeto`;
INSERT INTO `meeto`.`agenda` (`idagenda`, `idmeeting`) VALUES (1, 1);
INSERT INTO `meeto`.`agenda` (`idagenda`, `idmeeting`) VALUES (2, 2);
INSERT INTO `meeto`.`agenda` (`idagenda`, `idmeeting`) VALUES (3, 3);
INSERT INTO `meeto`.`agenda` (`idagenda`, `idmeeting`) VALUES (4, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `meeto`.`item`
-- -----------------------------------------------------
START TRANSACTION;
USE `meeto`;
INSERT INTO `meeto`.`item` (`iditem`, `idagenda`, `name`, `description`, `keydecision`)
VALUES (1, 1, 'LIMPOTRONIC', 'Discussão sobre a empresa LIMPOTRONIC', 'Melhor relação qualidade/preço');
INSERT INTO `meeto`.`item` (`iditem`, `idagenda`, `name`, `description`, `keydecision`)
VALUES (2, 1, 'TUDOLIMPA', 'Discussão sobre a empresa TUDOLIMPA', 'Demasiado cara.');
INSERT INTO `meeto`.`item` (`iditem`, `idagenda`, `name`, `description`, `keydecision`)
VALUES (3, 1, 'CLEAN-O-MATIC', 'Discussão sobre a empresa CLEAN-O-MATIC', 'Pouca flexibilidade de horários.');
INSERT INTO `meeto`.`item` (`iditem`, `idagenda`, `name`, `description`, `keydecision`)
VALUES (4, 4, 'Paulo Bento', 'Fica ou vai?', 'Já vai tarde.');
INSERT INTO `meeto`.`item` (`iditem`, `idagenda`, `name`, `description`, `keydecision`)
VALUES (5, 4, 'CR7', 'Performance e ordenado.', 'A Irina, sim senhor!');

COMMIT;


-- -----------------------------------------------------
-- Data for table `meeto`.`log`
-- -----------------------------------------------------
START TRANSACTION;
USE `meeto`;
INSERT INTO `meeto`.`log` (`idlog`, `iditem`, `idmember`, `line`)
VALUES (1, 1, 1, 'Conheço a empresa, recomendo vivamente');
INSERT INTO `meeto`.`log` (`idlog`, `iditem`, `idmember`, `line`)
VALUES (2, 1, 2, 'Não basta conhecer. Como é em termos de preços?');
INSERT INTO `meeto`.`log` (`idlog`, `iditem`, `idmember`, `line`)
VALUES (3, 1, 1, 'Bastante acessível. Acabei de vos mandar um ficheiro com a tabela de preços para o vosso mail');
INSERT INTO `meeto`.`log` (`idlog`, `iditem`, `idmember`, `line`)
VALUES (4, 1, 2, 'Acabei de ver. Por mim podemos seguir');
INSERT INTO `meeto`.`log` (`idlog`, `iditem`, `idmember`, `line`) VALUES (5, 1, 3, 'Também já vi. Aprovado.');
INSERT INTO `meeto`.`log` (`idlog`, `iditem`, `idmember`, `line`)
VALUES (6, 3, 2, 'Vi esta empresa a semana passada. Que me dizem?');
INSERT INTO `meeto`.`log` (`idlog`, `iditem`, `idmember`, `line`)
VALUES (7, 3, 1, 'Não parece má, o problema é que não estão disponíveis no horário que precisamos.');
INSERT INTO `meeto`.`log` (`idlog`, `iditem`, `idmember`, `line`) VALUES (8, 3, 3, 'Concordo, não irá servir.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `meeto`.`meeting_member`
-- -----------------------------------------------------
START TRANSACTION;
USE `meeto`;
INSERT INTO `meeto`.`meeting_member` (`idmeeting`, `idmember`, `accepted`) VALUES (1, 1, '1');
INSERT INTO `meeto`.`meeting_member` (`idmeeting`, `idmember`, `accepted`) VALUES (1, 2, '1');
INSERT INTO `meeto`.`meeting_member` (`idmeeting`, `idmember`, `accepted`) VALUES (1, 3, '1');
INSERT INTO `meeto`.`meeting_member` (`idmeeting`, `idmember`, `accepted`) VALUES (2, 4, NULL);
INSERT INTO `meeto`.`meeting_member` (`idmeeting`, `idmember`, `accepted`) VALUES (2, 5, NULL);
INSERT INTO `meeto`.`meeting_member` (`idmeeting`, `idmember`, `accepted`) VALUES (2, 6, '0');
INSERT INTO `meeto`.`meeting_member` (`idmeeting`, `idmember`, `accepted`) VALUES (3, 1, '1');

COMMIT;

