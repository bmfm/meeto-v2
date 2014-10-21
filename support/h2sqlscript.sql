DROP TABLE IF EXISTS `system_message`;

CREATE TABLE IF NOT EXISTS `system_message` (
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