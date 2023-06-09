SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema 2023_sagrada
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema 2023_sagrada
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `2023_sagrada` DEFAULT CHARACTER SET utf8 ;
USE `2023_sagrada` ;

START TRANSACTION;

-- -----------------------------------------------------
-- Table `2023_sagrada`.`game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`game` (
  `idgame` INT NOT NULL AUTO_INCREMENT,
  `turn_idplayer` INT NULL,
  `current_roundID` INT NULL,
  `creationdate` DATETIME NOT NULL,
  PRIMARY KEY (`idgame`),
  INDEX `fk_spel_speler1_idx` (`turn_idplayer` ASC),
  INDEX `fk_game_round1_idx` (`current_roundID` ASC),
  CONSTRAINT `fk_spel_speler1`
    FOREIGN KEY (`turn_idplayer`)
    REFERENCES `2023_sagrada`.`player` (`idplayer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_game_round1`
    FOREIGN KEY (`current_roundID`)
    REFERENCES `2023_sagrada`.`round` (`roundID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`playstatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`playstatus` (
  `playstatus` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`playstatus`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`account` (
  `username` VARCHAR(25) NOT NULL,
  `password` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`color`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`color` (
  `color` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`color`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`patterncard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`patterncard` (
  `idpatterncard` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `difficulty` INT NOT NULL,
  `standard` TINYINT(1) NOT NULL,
  PRIMARY KEY (`idpatterncard`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`player` (
  `idplayer` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(25) NOT NULL,
  `idgame` INT NOT NULL,
  `playstatus` VARCHAR(12) NOT NULL,
  `seqnr` INT NULL,
  `private_objectivecard_color` VARCHAR(6) NOT NULL,
  `idpatterncard` INT NULL,
  `score` INT NULL,
  PRIMARY KEY (`idplayer`),
  INDEX `fk_speler_spel1_idx` (`idgame` ASC),
  INDEX `fk_speler_speelstatus1_idx` (`playstatus` ASC),
  INDEX `fk_speler_account1_idx` (`username` ASC),
  INDEX `fk_spelereur1_idx` (`private_objectivecard_color` ASC),
  INDEX `fk_speler_patroonkaart1_idx` (`idpatterncard` ASC),
  UNIQUE INDEX `username_idgame_UNIQUE` (`username` ASC, `idgame` ASC),
  CONSTRAINT `fk_speler_spel1`
    FOREIGN KEY (`idgame`)
    REFERENCES `2023_sagrada`.`game` (`idgame`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_speler_speelstatus1`
    FOREIGN KEY (`playstatus`)
    REFERENCES `2023_sagrada`.`playstatus` (`playstatus`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_speler_account1`
    FOREIGN KEY (`username`)
    REFERENCES `2023_sagrada`.`account` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_spelereur1`
    FOREIGN KEY (`private_objectivecard_color`)
    REFERENCES `2023_sagrada`.`color` (`color`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_speler_patroonkaart1`
    FOREIGN KEY (`idpatterncard`)
    REFERENCES `2023_sagrada`.`patterncard` (`idpatterncard`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`die`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`die` (
  `number` INT NOT NULL,
  `color` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`number`, `color`),
  INDEX `fk_dobbelsteeneur_idx` (`color` ASC),
  CONSTRAINT `fk_dobbelsteeneur`
    FOREIGN KEY (`color`)
    REFERENCES `2023_sagrada`.`color` (`color`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`round`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`round` (
  `roundID` INT NOT NULL,
  `roundnr` INT NOT NULL,
  `clockwise` TINYINT NOT NULL,
  PRIMARY KEY (`roundID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`gamedie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`gamedie` (
  `idgame` INT NOT NULL,
  `dienumber` INT NOT NULL,
  `diecolor` VARCHAR(6) NOT NULL,
  `eyes` INT NULL,
  `roundtrack` INT NULL,
  `roundID` INT NULL,
  PRIMARY KEY (`idgame`, `dienumber`, `diecolor`),
  INDEX `fk_speldobbelsteen_dobbelsteen1_idx` (`dienumber` ASC, `diecolor` ASC),
  INDEX `fk_speldobbelsteen_rondespoor1_idx` (`roundtrack` ASC),
  INDEX `fk_speldobbelsteen_ronde1_idx` (`roundID` ASC),
  CONSTRAINT `fk_speldobbelsteen_spel1`
    FOREIGN KEY (`idgame`)
    REFERENCES `2023_sagrada`.`game` (`idgame`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_speldobbelsteen_dobbelsteen1`
    FOREIGN KEY (`dienumber` , `diecolor`)
    REFERENCES `2023_sagrada`.`die` (`number` , `color`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_speldobbelsteen_rondespoor1`
    FOREIGN KEY (`roundtrack`)
    REFERENCES `2023_sagrada`.`round` (`roundID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_speldobbelsteen_ronde1`
    FOREIGN KEY (`roundID`)
    REFERENCES `2023_sagrada`.`round` (`roundID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`position`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`position` (
  `x` INT NOT NULL,
  `y` INT NOT NULL,
  PRIMARY KEY (`x`, `y`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`playerframefield`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`playerframefield` (
  `idplayer` INT NOT NULL,
  `position_x` INT NOT NULL,
  `position_y` INT NOT NULL,
  `idgame` INT NULL,
  `dienumber` INT NULL,
  `diecolor` VARCHAR(6) NULL,
  PRIMARY KEY (`idplayer`, `position_x`, `position_y`),
  INDEX `fk_spelersbordveld_speldobbelsteen1_idx` (`idgame` ASC, `dienumber` ASC, `diecolor` ASC),
  INDEX `fk_spelersbordveld_positie1_idx` (`position_x` ASC, `position_y` ASC),
  CONSTRAINT `fk_spelersbord_speler1`
    FOREIGN KEY (`idplayer`)
    REFERENCES `2023_sagrada`.`player` (`idplayer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_spelersbordveld_speldobbelsteen1`
    FOREIGN KEY (`idgame` , `dienumber` , `diecolor`)
    REFERENCES `2023_sagrada`.`gamedie` (`idgame` , `dienumber` , `diecolor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_spelersbordveld_positie1`
    FOREIGN KEY (`position_x` , `position_y`)
    REFERENCES `2023_sagrada`.`position` (`x` , `y`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`toolcard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`toolcard` (
  `idtoolcard` INT NOT NULL,
  `seqnr` TINYINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`idtoolcard`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`public_objectivecard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`public_objectivecard` (
  `idpublic_objectivecard` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` TEXT NOT NULL,
  `points` INT NOT NULL,
  PRIMARY KEY (`idpublic_objectivecard`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`chatline`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`chatline` (
  `idplayer` INT NOT NULL,
  `time` TIMESTAMP NOT NULL,
  `message` TEXT NOT NULL,
  PRIMARY KEY (`idplayer`, `time`),
  INDEX `fk_chatregel_speler1_idx` (`idplayer` ASC),
  CONSTRAINT `fk_chatregel_speler1`
    FOREIGN KEY (`idplayer`)
    REFERENCES `2023_sagrada`.`player` (`idplayer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`gametoolcard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`gametoolcard` (
  `gametoolcard` INT NOT NULL AUTO_INCREMENT,
  `idtoolcard` INT NOT NULL,
  `idgame` INT NOT NULL,
  INDEX `fk_gereedschapskaart_has_spel_spel1_idx` (`idgame` ASC),
  INDEX `fk_gereedschapskaart_has_spel_gereedschapskaart1_idx` (`idtoolcard` ASC),
  PRIMARY KEY (`gametoolcard`),
  UNIQUE INDEX `idgereedschapskaart_UNIQUE` (`idtoolcard` ASC, `idgame` ASC),
  CONSTRAINT `fk_gereedschapskaart_has_spel_gereedschapskaart1`
    FOREIGN KEY (`idtoolcard`)
    REFERENCES `2023_sagrada`.`toolcard` (`idtoolcard`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gereedschapskaart_has_spel_spel1`
    FOREIGN KEY (`idgame`)
    REFERENCES `2023_sagrada`.`game` (`idgame`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`gamefavortoken`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`gamefavortoken` (
  `idfavortoken` INT NOT NULL,
  `idgame` INT NOT NULL,
  `idplayer` INT NULL,
  `gametoolcard` INT NULL,
  `roundID` INT NULL,
  PRIMARY KEY (`idfavortoken`, `idgame`),
  INDEX `fk_spelbetaalsteen_spel1_idx` (`idgame` ASC),
  INDEX `fk_spelbetaalsteen_speler1_idx` (`idplayer` ASC),
  INDEX `fk_spelbetaalsteen_spelgereedschapskaart1_idx` (`gametoolcard` ASC),
  INDEX `fk_gamefavortoken_round1_idx` (`roundID` ASC),
  CONSTRAINT `fk_spelbetaalsteen_spel1`
    FOREIGN KEY (`idgame`)
    REFERENCES `2023_sagrada`.`game` (`idgame`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_spelbetaalsteen_speler1`
    FOREIGN KEY (`idplayer`)
    REFERENCES `2023_sagrada`.`player` (`idplayer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_spelbetaalsteen_spelgereedschapskaart1`
    FOREIGN KEY (`gametoolcard`)
    REFERENCES `2023_sagrada`.`gametoolcard` (`gametoolcard`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gamefavortoken_round1`
    FOREIGN KEY (`roundID`)
    REFERENCES `2023_sagrada`.`round` (`roundID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`gameobjectivecard_public`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`gameobjectivecard_public` (
  `idgame` INT NOT NULL,
  `idpublic_objectivecard` INT NOT NULL,
  PRIMARY KEY (`idgame`, `idpublic_objectivecard`),
  INDEX `fk_spel_has_doelkaart_gedeeld_doelkaart_gedeeld1_idx` (`idpublic_objectivecard` ASC),
  INDEX `fk_spel_has_doelkaart_gedeeld_spel1_idx` (`idgame` ASC),
  CONSTRAINT `fk_spel_has_doelkaart_gedeeld_spel1`
    FOREIGN KEY (`idgame`)
    REFERENCES `2023_sagrada`.`game` (`idgame`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_spel_has_doelkaart_gedeeld_doelkaart_gedeeld1`
    FOREIGN KEY (`idpublic_objectivecard`)
    REFERENCES `2023_sagrada`.`public_objectivecard` (`idpublic_objectivecard`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`patterncardfield`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`patterncardfield` (
  `idpatterncard` INT NOT NULL,
  `position_x` INT NOT NULL,
  `position_y` INT NOT NULL,
  `color` VARCHAR(6) NULL,
  `value` INT NULL,
  PRIMARY KEY (`idpatterncard`, `position_x`, `position_y`),
  INDEX `fk_patroonkaartveld_positie1_idx` (`position_x` ASC, `position_y` ASC),
  INDEX `fk_patroonkaartveldeur1_idx` (`color` ASC),
  CONSTRAINT `fk_patroonkaartveld_patroonkaart1`
    FOREIGN KEY (`idpatterncard`)
    REFERENCES `2023_sagrada`.`patterncard` (`idpatterncard`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patroonkaartveld_positie1`
    FOREIGN KEY (`position_x` , `position_y`)
    REFERENCES `2023_sagrada`.`position` (`x` , `y`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patroonkaartveldeur1`
    FOREIGN KEY (`color`)
    REFERENCES `2023_sagrada`.`color` (`color`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `2023_sagrada`.`patterncardoption`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `2023_sagrada`.`patterncardoption` (
  `idpatterncard` INT NOT NULL,
  `idplayer` INT NOT NULL,
  PRIMARY KEY (`idpatterncard`, `idplayer`),
  INDEX `fk_patroonkaart_has_speler_speler1_idx` (`idplayer` ASC),
  INDEX `fk_patroonkaart_has_speler_patroonkaart1_idx` (`idpatterncard` ASC),
  CONSTRAINT `fk_patroonkaart_has_speler_patroonkaart1`
    FOREIGN KEY (`idpatterncard`)
    REFERENCES `2023_sagrada`.`patterncard` (`idpatterncard`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_patroonkaart_has_speler_speler1`
    FOREIGN KEY (`idplayer`)
    REFERENCES `2023_sagrada`.`player` (`idplayer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

COMMIT;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- INSERTS data for 2023_sagrada DB

START TRANSACTION;

-- Truncate tables and drop triggers if schema already existed
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE account;
TRUNCATE TABLE chatline;
TRUNCATE TABLE color;
TRUNCATE TABLE die;
TRUNCATE TABLE game;
TRUNCATE TABLE gamedie;
TRUNCATE TABLE gamefavortoken;
TRUNCATE TABLE gameobjectivecard_public;
TRUNCATE TABLE gametoolcard;
TRUNCATE TABLE patterncard;
TRUNCATE TABLE patterncardfield;
TRUNCATE TABLE patterncardoption;
TRUNCATE TABLE player;
TRUNCATE TABLE playerframefield;
TRUNCATE TABLE playstatus;
TRUNCATE TABLE position;
TRUNCATE TABLE public_objectivecard;
TRUNCATE TABLE round;
TRUNCATE TABLE toolcard;
SET FOREIGN_KEY_CHECKS=1;

DROP TRIGGER IF EXISTS tr_account_username_insert;
DROP TRIGGER IF EXISTS tr_account_username_update;
DROP TRIGGER IF EXISTS tr_color_insert;
DROP TRIGGER IF EXISTS tr_color_update;
DROP TRIGGER IF EXISTS tr_die_insert;
DROP TRIGGER IF EXISTS tr_die_update;
DROP TRIGGER IF EXISTS tr_eyes_gamedie_insert;
DROP TRIGGER IF EXISTS tr_eyes_gamedie_update;
DROP TRIGGER IF EXISTS tr_gametoolcard_update;
DROP TRIGGER IF EXISTS tr_patterncard_insert;
DROP TRIGGER IF EXISTS tr_patterncard_update;
DROP TRIGGER IF EXISTS tr_value_patterncardfield_insert;
DROP TRIGGER IF EXISTS tr_value_patterncardfield_update;
DROP TRIGGER IF EXISTS tr_patterncardoption_insert;
DROP TRIGGER IF EXISTS tr_patterncardoption_update;
DROP TRIGGER IF EXISTS tr_player_insert;
DROP TRIGGER IF EXISTS tr_player_update;
DROP TRIGGER IF EXISTS tr_playstatus_insert;
DROP TRIGGER IF EXISTS tr_playstatus_update;
DROP TRIGGER IF EXISTS tr_position_insert;
DROP TRIGGER IF EXISTS tr_position_update;
DROP TRIGGER IF EXISTS tr_public_objectivecard_insert;
DROP TRIGGER IF EXISTS tr_public_objectivecard_update;
DROP TRIGGER IF EXISTS tr_round_insert;
DROP TRIGGER IF EXISTS tr_round_update;
DROP TRIGGER IF EXISTS tr_gameobjectivecard_public_update;
DROP TRIGGER IF EXISTS tr_toolcard_insert;
DROP TRIGGER IF EXISTS tr_toolcard_update;

-- End Truncation

-- lookuptable playstatus
INSERT INTO `2023_sagrada`.`playstatus` (`playstatus`) VALUES 
('challenger'), ('challengee'), ('accepted'), ('refused'), ('finished');

-- lookuptable position
INSERT INTO `2023_sagrada`.`position` (`x`, `y`) VALUES 
('1', '1'), ('1', '2'), ('1', '3'), ('1', '4'), 
('2', '1'), ('2', '2'), ('2', '3'), ('2', '4'), 
('3', '1'), ('3', '2'), ('3', '3'), ('3', '4'), 
('4', '1'), ('4', '2'), ('4', '3'), ('4', '4'), 
('5', '1'), ('5', '2'), ('5', '3'), ('5', '4');

-- lookuptable color
INSERT INTO `2023_sagrada`.`color` (`color`) VALUES 
('blue'), ('purple'), ('green'), ('red'), ('yellow');

-- lookuptable ronde
INSERT INTO `2023_sagrada`.`round` (`roundID`,`roundnr`,`clockwise`) VALUES 
('1','1','1'), ('2','1','0'), ('3','2','1'), ('4','2','0'), ('5','3','1'), ('6','3','0'), ('7','4','1'), ('8','4','0'), ('9','5','1'), ('10','5','0'), 
('11','6','1'), ('12','6','0'), ('13','7','1'), ('14','7','0'), ('15','8','1'), ('16','8','0'), ('17','9','1'), ('18','9','0'), ('19','10','1'), ('20','10','0');

-- datatable die
INSERT INTO `2023_sagrada`.`die` (`number`, `color`) VALUES 
('1', 'blue'), ('2', 'blue'), ('3', 'blue'), ('4', 'blue'), ('5', 'blue'), ('6', 'blue'), ('7', 'blue'), ('8', 'blue'), ('9', 'blue'), ('10', 'blue'), ('11', 'blue'), ('12', 'blue'), ('13', 'blue'), ('14', 'blue'), ('15', 'blue'), ('16', 'blue'), ('17', 'blue'), ('18', 'blue'), 
('1', 'yellow'), ('2', 'yellow'), ('3', 'yellow'), ('4', 'yellow'), ('5', 'yellow'), ('6', 'yellow'), ('7', 'yellow'), ('8', 'yellow'), ('9', 'yellow'), ('10', 'yellow'), ('11', 'yellow'), ('12', 'yellow'), ('13', 'yellow'), ('14', 'yellow'), ('15', 'yellow'), ('16', 'yellow'), ('17', 'yellow'), ('18', 'yellow'), 
('1', 'green'), ('2', 'green'), ('3', 'green'), ('4', 'green'), ('5', 'green'), ('6', 'green'), ('7', 'green'), ('8', 'green'), ('9', 'green'), ('10', 'green'), ('11', 'green'), ('12', 'green'), ('13', 'green'), ('14', 'green'), ('15', 'green'), ('16', 'green'), ('17', 'green'), ('18', 'green'), 
('1', 'purple'), ('2', 'purple'), ('3', 'purple'), ('4', 'purple'), ('5', 'purple'), ('6', 'purple'), ('7', 'purple'), ('8', 'purple'), ('9', 'purple'), ('10', 'purple'), ('11', 'purple'), ('12', 'purple'), ('13', 'purple'), ('14', 'purple'), ('15', 'purple'), ('16', 'purple'), ('17', 'purple'), ('18', 'purple'), 
('1', 'red'), ('2', 'red'), ('3', 'red'), ('4', 'red'), ('5', 'red'), ('6', 'red'), ('7', 'red'), ('8', 'red'), ('9', 'red'), ('10', 'red'), ('11', 'red'), ('12', 'red'), ('13', 'red'), ('14', 'red'), ('15', 'red'), ('16', 'red'), ('17', 'red'), ('18', 'red');

-- datatable public_objectivecard
INSERT INTO `2023_sagrada`.`public_objectivecard` (`idpublic_objectivecard`, `name`, `description`, `points`) VALUES 
('1', 'Shade Variety', 'Sets of one of each shade anywhere', '5'), 
('2', 'Medium Shades', 'Sets of 3 & 4 values anywhere', '2'), 
('3', 'Column Shade Variety', 'Columns with no repeated shades.', '4'), 
('4', 'Column Color Variety', 'Columns with no repeated colors', '5'), 
('5', 'Dark Shades', 'Sets of 5 & 6 values anywhere', '2'), 
('6', 'Color Variety', 'Sets of one of each color anywhere', '4'), 
('7', 'Row Color Variety', 'Rows with no repeated colors', '6'), 
('8', 'Diagonals', 'Number of pieces of the same color', '1'), 
('9', 'Light Shades', 'Sets of 1 & 2 values anywhere', '2'), 
('10', 'Row Shade Variety', 'Rows with no repeated shades.', '5');

-- datatable toolcard
INSERT INTO `2023_sagrada`.`toolcard` (`idtoolcard`, `name`, `seqnr`, `description`) VALUES 
('1', 'Grozing Pliers', '1', 'After drafting, increase or decrease the value of the drafted die by 1. 1 may not change to 6, or 6 to 1.'), 
('2', 'Eglomise Brush', '2', 'Move any one die in your window ignoring the color restrictions. You must obey all other placement restrictions.'), 
('3', 'Copper Foil Burnisher', '3', 'Move any one die in your window ignoring shade restriction. You must obey all other placement restrictions.'), 
('4', 'Lathekin', '4', 'Move exactly two dice, obeying all placement restrictions.'), 
('5', 'Lens Cutter', '5', 'After drafting, swap the drafted die with a die from the Round Track.'), 
('6', 'Flux Brush', '6', 'After drafting, re-roll the drafted die. If it cannot be placed, return it to the Drafting Pool'), 
('7', 'Glazing Hammer', '7', 'Re-roll all dice in the Draft Pool. This may only be used on your second turn before drafting.'), 
('8', 'Running Pliers', '8', 'After your first turn, immediately draft a die. Skip your next turn this round.'), 
('9', 'Cork-backed Straightedge', '9', 'After drafting, place the die in a spot that is not adjacent to another die. You must obey all other placement restrictions.'), 
('10', 'Grinding Stone', '10', 'After drafting, flip the die to its opposite side. 6 flips to 1, 5 to 2, 4 to 3, etc.'), 
('11', 'Flux Remover', '11', 'After drafting, return the die to the Dice Bag and pull 1 die from the bag. Choose a value and place the new die, obeying all placement restrictions, or return it to the Draft Pool.'), 
('12', 'Tap Wheel', '12', 'Move up to two dice of the same color that match the color of a die on the Round Track. You must obey all placement restrictions.');

-- data standard patterncard
INSERT INTO `2023_sagrada`.`patterncard` (`idpatterncard`, `name`, `difficulty`, `standard`) VALUES 
('1', 'Fractal Drops', '3', '1'), ('2', 'Luz Celestial', '3', '1'), ('3', 'Bellesguard', '3', '1'), ('4', 'Sun Catcher', '3', '1'), ('5', 'Chromatic Splendor', '4', '1'), ('6', 'Via Lux', '4', '1'), 
('7', 'Kaleidoscopic Dream', '4', '1'), ('8', 'Aurora Sagradis', '4', '1'), ('9', 'Gravitas', '5', '1'), ('10', 'Ripples of Light', '5', '1'), ('11', 'Comitas', '5', '1'), ('12', 'Industria', '5', '1'), 
('13', 'Fulgor del Cielo', '5', '1'), ('14', 'Firelight', '5', '1'), ('15', 'Firmitas', '5', '1'), ('16', 'Aurorae Magnificus', '5', '1'), ('17', 'Batllo', '5', '1'), ('18', 'Lux Astram', '5', '1'), 
('19', 'Shadow Thief', '5', '1'), ('20', 'Virtus', '5', '1'), ('21', 'Water of Life', '6', '1'), ('22', 'Sun\'s Glory', '6', '1'), ('23', 'Lux Mundi', '6', '1'), ('24', 'Symphony of Light', '6', '1');

-- data standard patterncardfields
INSERT INTO `2023_sagrada`.`patterncardfield` (`idpatterncard`, `position_x`, `position_y`, `color`, `value`) VALUES 
('1', '1', '1', NULL, NULL), ('1', '1', '2', 'red', NULL), ('1', '1', '3', NULL, NULL), ('1', '1', '4', 'blue', NULL), 
('1', '2', '1', NULL, '4'), ('1', '2', '2', NULL, NULL), ('1', '2', '3', NULL, NULL), ('1', '2', '4', 'yellow', NULL), 
('1', '3', '1', NULL, NULL), ('1', '3', '2', NULL, '2'), ('1', '3', '3', 'red', NULL), ('1', '3', '4', NULL, NULL), 
('1', '4', '1', 'yellow', NULL), ('1', '4', '2', NULL, NULL), ('1', '4', '3', 'purple', NULL), ('1', '4', '4', NULL, NULL), 
('1', '5', '1', NULL, '6'), ('1', '5', '2', NULL, NULL), ('1', '5', '3', NULL, '1'), ('1', '5', '4', NULL, NULL), 
('2', '1', '1', NULL, NULL), ('2', '1', '2', 'purple', NULL), ('2', '1', '3', NULL, '6'), ('2', '1', '4', NULL, NULL), 
('2', '2', '1', NULL, NULL), ('2', '2', '2', NULL, '4'), ('2', '2', '3', NULL, NULL), ('2', '2', '4', 'yellow', NULL), 
('2', '3', '1', 'red', NULL), ('2', '3', '2', NULL, NULL), ('2', '3', '3', NULL, NULL), ('2', '3', '4', NULL, '2'), 
('2', '4', '1', NULL, '5'), ('2', '4', '2', 'green', NULL), ('2', '4', '3', 'blue', NULL), ('2', '4', '4', NULL, NULL), 
('2', '5', '1', NULL, NULL), ('2', '5', '2', NULL, '3'), ('2', '5', '3', NULL, NULL), ('2', '5', '4', NULL, NULL), 
('3', '1', '1', 'blue', NULL), ('3', '1', '2', NULL, NULL), ('3', '1', '3', NULL, NULL), ('3', '1', '4', NULL, NULL), 
('3', '2', '1', NULL, '6'), ('3', '2', '2', NULL, '3'), ('3', '2', '3', NULL, '5'), ('3', '2', '4', NULL, '4'), 
('3', '3', '1', NULL, NULL), ('3', '3', '2', 'blue', NULL), ('3', '3', '3', NULL, '6'), ('3', '3', '4', NULL, NULL), 
('3', '4', '1', NULL, NULL), ('3', '4', '2', NULL, NULL), ('3', '4', '3', NULL, '2'), ('3', '4', '4', NULL, '1'), 
('3', '5', '1', 'yellow', NULL), ('3', '5', '2', NULL, NULL), ('3', '5', '3', NULL, NULL), ('3', '5', '4', 'green', NULL), 
('4', '1', '1', NULL, NULL), ('4', '1', '2', NULL, NULL), ('4', '1', '3', NULL, NULL), ('4', '1', '4', 'green', NULL), 
('4', '2', '1', 'blue', NULL), ('4', '2', '2', NULL, '4'), ('4', '2', '3', NULL, NULL), ('4', '2', '4', NULL, '3'), 
('4', '3', '1', NULL, '2'), ('4', '3', '2', NULL, NULL), ('4', '3', '3', NULL, '5'), ('4', '3', '4', NULL, NULL), 
('4', '4', '1', NULL, NULL), ('4', '4', '2', 'red', NULL), ('4', '4', '3', 'yellow', NULL), ('4', '4', '4', NULL, NULL), 
('4', '5', '1', 'yellow', NULL), ('4', '5', '2', NULL, NULL), ('4', '5', '3', NULL, NULL), ('4', '5', '4', 'purple', NULL), 
('5', '1', '1', NULL, NULL), ('5', '1', '2', NULL, '2'), ('5', '1', '3', NULL, NULL), ('5', '1', '4', NULL, '1'), 
('5', '2', '1', NULL, NULL), ('5', '2', '2', 'yellow', NULL), ('5', '2', '3', 'red', NULL), ('5', '2', '4', NULL, NULL), 
('5', '3', '1', 'green', NULL), ('5', '3', '2', NULL, '5'), ('5', '3', '3', NULL, '3'), ('5', '3', '4', NULL, '6'), 
('5', '4', '1', NULL, NULL), ('5', '4', '2', 'blue', NULL), ('5', '4', '3', 'purple', NULL), ('5', '4', '4', NULL, NULL), 
('5', '5', '1', NULL, NULL), ('5', '5', '2', NULL, '1'), ('5', '5', '3', NULL, NULL), ('5', '5', '4', NULL, '4'), 
('6', '1', '1', 'yellow', NULL), ('6', '1', '2', NULL, NULL), ('6', '1', '3', NULL, '3'), ('6', '1', '4', NULL, NULL), 
('6', '2', '1', NULL, NULL), ('6', '2', '2', NULL, '1'), ('6', '2', '3', 'yellow', NULL), ('6', '2', '4', NULL, NULL), 
('6', '3', '1', NULL, '6'), ('6', '3', '2', NULL, '5'), ('6', '3', '3', 'red', NULL), ('6', '3', '4', NULL, '4'), 
('6', '4', '1', NULL, NULL), ('6', '4', '2', NULL, NULL), ('6', '4', '3', 'purple', NULL), ('6', '4', '4', NULL, '3'), 
('6', '5', '1', NULL, NULL), ('6', '5', '2', NULL, '2'), ('6', '5', '3', NULL, NULL), ('6', '5', '4', 'red', NULL), 
('7', '1', '1', 'yellow', NULL), ('7', '1', '2', 'green', NULL), ('7', '1', '3', NULL, '3'), ('7', '1', '4', NULL, '2'), 
('7', '2', '1', 'blue', NULL), ('7', '2', '2', NULL, NULL), ('7', '2', '3', NULL, NULL), ('7', '2', '4', NULL, NULL), 
('7', '3', '1', NULL, NULL), ('7', '3', '2', NULL, '5'), ('7', '3', '3', 'red', NULL), ('7', '3', '4', NULL, NULL), 
('7', '4', '1', NULL, NULL), ('7', '4', '2', NULL, NULL), ('7', '4', '3', NULL, NULL), ('7', '4', '4', 'blue', NULL), 
('7', '5', '1', NULL, '1'), ('7', '5', '2', NULL, '4'), ('7', '5', '3', 'green', NULL), ('7', '5', '4', 'yellow', NULL), 
('8', '1', '1', 'red', NULL), ('8', '1', '2', NULL, '4'), ('8', '1', '3', NULL, NULL), ('8', '1', '4', NULL, NULL), 
('8', '2', '1', NULL, NULL), ('8', '2', '2', 'purple', NULL), ('8', '2', '3', NULL, '1'), ('8', '2', '4', NULL, NULL), 
('8', '3', '1', 'blue', NULL), ('8', '3', '2', NULL, '3'), ('8', '3', '3', NULL, NULL), ('8', '3', '4', NULL, '6'), 
('8', '4', '1', NULL, NULL), ('8', '4', '2', 'green', NULL), ('8', '4', '3', NULL, '5'), ('8', '4', '4', NULL, NULL), 
('8', '5', '1', 'yellow', NULL), ('8', '5', '2', NULL, '2'), ('8', '5', '3', NULL, NULL), ('8', '5', '4', NULL, NULL), 
('9', '1', '1', NULL, '1'), ('9', '1', '2', NULL, NULL), ('9', '1', '3', NULL, '6'), ('9', '1', '4', 'blue', NULL), 
('9', '2', '1', NULL, NULL), ('9', '2', '2', NULL, '2'), ('9', '2', '3', 'blue', NULL), ('9', '2', '4', NULL, '5'), 
('9', '3', '1', NULL, '3'), ('9', '3', '2', 'blue', NULL), ('9', '3', '3', NULL, NULL), ('9', '3', '4', NULL, '2'), 
('9', '4', '1', 'blue', NULL), ('9', '4', '2', NULL, NULL), ('9', '4', '3', NULL, '4'), ('9', '4', '4', NULL, NULL), 
('9', '5', '1', NULL, NULL), ('9', '5', '2', NULL, NULL), ('9', '5', '3', NULL, NULL), ('9', '5', '4', NULL, '1'), 
('10', '1', '1', NULL, NULL), ('10', '1', '2', NULL, NULL), ('10', '1', '3', NULL, NULL), ('10', '1', '4', 'yellow', NULL), 
('10', '2', '1', NULL, NULL), ('10', '2', '2', NULL, NULL), ('10', '2', '3', 'blue', NULL), ('10', '2', '4', NULL, '2'), 
('10', '3', '1', NULL, NULL), ('10', '3', '2', 'purple', NULL), ('10', '3', '3', NULL, '3'), ('10', '3', '4', 'green', NULL), 
('10', '4', '1', 'red', NULL), ('10', '4', '2', NULL, '4'), ('10', '4', '3', 'yellow', NULL), ('10', '4', '4', NULL, '1'), 
('10', '5', '1', NULL, '5'), ('10', '5', '2', 'blue', NULL), ('10', '5', '3', NULL, '6'), ('10', '5', '4', 'red', NULL), 
('11', '1', '1', 'yellow', NULL), ('11', '1', '2', NULL, NULL), ('11', '1', '3', NULL, NULL), ('11', '1', '4', NULL, '1'), 
('11', '2', '1', NULL, NULL), ('11', '2', '2', NULL, '4'), ('11', '2', '3', NULL, NULL), ('11', '2', '4', NULL, '2'), 
('11', '3', '1', NULL, '2'), ('11', '3', '2', NULL, NULL), ('11', '3', '3', NULL, NULL), ('11', '3', '4', 'yellow', NULL), 
('11', '4', '1', NULL, NULL), ('11', '4', '2', NULL, '5'), ('11', '4', '3', 'yellow', NULL), ('11', '4', '4', NULL, '3'), 
('11', '5', '1', NULL, '6'), ('11', '5', '2', 'yellow', NULL), ('11', '5', '3', NULL, '5'), ('11', '5', '4', NULL, NULL), 
('12', '1', '1', NULL, '1'), ('12', '1', '2', NULL, '5'), ('12', '1', '3', NULL, NULL), ('12', '1', '4', NULL, NULL), 
('12', '2', '1', 'red', NULL), ('12', '2', '2', NULL, '4'), ('12', '2', '3', NULL, NULL), ('12', '2', '4', NULL, NULL), 
('12', '3', '1', NULL, '3'), ('12', '3', '2', 'red', NULL), ('12', '3', '3', NULL, '5'), ('12', '3', '4', NULL, NULL), 
('12', '4', '1', NULL, NULL), ('12', '4', '2', NULL, '2'), ('12', '4', '3', 'red', NULL), ('12', '4', '4', NULL, '3'), 
('12', '5', '1', NULL, '6'), ('12', '5', '2', NULL, NULL), ('12', '5', '3', NULL, '1'), ('12', '5', '4', 'red', NULL), 
('13', '1', '1', NULL, NULL), ('13', '1', '2', NULL, NULL), ('13', '1', '3', 'blue', NULL), ('13', '1', '4', NULL, '6'), 
('13', '2', '1', 'blue', NULL), ('13', '2', '2', NULL, '4'), ('13', '2', '3', NULL, '2'), ('13', '2', '4', 'red', NULL), 
('13', '3', '1', 'red', NULL), ('13', '3', '2', NULL, '5'), ('13', '3', '3', NULL, NULL), ('13', '3', '4', NULL, '3'), 
('13', '4', '1', NULL, NULL), ('13', '4', '2', NULL, NULL), ('13', '4', '3', 'red', NULL), ('13', '4', '4', NULL, '1'), 
('13', '5', '1', NULL, NULL), ('13', '5', '2', 'blue', NULL), ('13', '5', '3', NULL, '5'), ('13', '5', '4', NULL, NULL), 
('14', '1', '1', NULL, '3'), ('14', '1', '2', NULL, NULL), ('14', '1', '3', NULL, NULL), ('14', '1', '4', NULL, '5'), 
('14', '2', '1', NULL, '4'), ('14', '2', '2', NULL, '6'), ('14', '2', '3', NULL, NULL), ('14', '2', '4', NULL, NULL), 
('14', '3', '1', NULL, '1'), ('14', '3', '2', NULL, '2'), ('14', '3', '3', NULL, NULL), ('14', '3', '4', 'yellow', NULL), 
('14', '4', '1', NULL, '5'), ('14', '4', '2', NULL, NULL), ('14', '4', '3', 'yellow', NULL), ('14', '4', '4', 'red', NULL), 
('14', '5', '1', NULL, NULL), ('14', '5', '2', 'yellow', NULL), ('14', '5', '3', 'red', NULL), ('14', '5', '4', NULL, '6'), 
('15', '1', '1', 'purple', NULL), ('15', '1', '2', NULL, '5'), ('15', '1', '3', NULL, NULL), ('15', '1', '4', NULL, NULL), 
('15', '2', '1', NULL, '6'), ('15', '2', '2', 'purple', NULL), ('15', '2', '3', NULL, '2'), ('15', '2', '4', NULL, '1'), 
('15', '3', '1', NULL, NULL), ('15', '3', '2', NULL, '3'), ('15', '3', '3', 'purple', NULL), ('15', '3', '4', NULL, '5'), 
('15', '4', '1', NULL, NULL), ('15', '4', '2', NULL, NULL), ('15', '4', '3', NULL, '1'), ('15', '4', '4', 'purple', NULL), 
('15', '5', '1', NULL, '3'), ('15', '5', '2', NULL, NULL), ('15', '5', '3', NULL, NULL), ('15', '5', '4', NULL, '4'), 
('16', '1', '1', NULL, '5'), ('16', '1', '2', 'purple', NULL), ('16', '1', '3', 'yellow', NULL), ('16', '1', '4', NULL, '1'), 
('16', '2', '1', 'green', NULL), ('16', '2', '2', NULL, NULL), ('16', '2', '3', NULL, NULL), ('16', '2', '4', NULL, NULL), 
('16', '3', '1', 'blue', NULL), ('16', '3', '2', NULL, NULL), ('16', '3', '3', NULL, '6'), ('16', '3', '4', NULL, NULL), 
('16', '4', '1', 'purple', NULL), ('16', '4', '2', NULL, NULL), ('16', '4', '3', NULL, NULL), ('16', '4', '4', 'green', NULL), 
('16', '5', '1', NULL, '2'), ('16', '5', '2', 'yellow', NULL), ('16', '5', '3', 'purple', NULL), ('16', '5', '4', NULL, '4'), 
('17', '1', '1', NULL, NULL), ('17', '1', '2', NULL, NULL), ('17', '1', '3', NULL, '3'), ('17', '1', '4', NULL, '1'), 
('17', '2', '1', NULL, NULL), ('17', '2', '2', NULL, '5'), ('17', '2', '3', 'green', NULL), ('17', '2', '4', NULL, '4'), 
('17', '3', '1', NULL, '6'), ('17', '3', '2', 'blue', NULL), ('17', '3', '3', 'yellow', NULL), ('17', '3', '4', 'red', NULL), 
('17', '4', '1', NULL, NULL), ('17', '4', '2', NULL, '4'), ('17', '4', '3', 'purple', NULL), ('17', '4', '4', NULL, '5'), 
('17', '5', '1', NULL, NULL), ('17', '5', '2', NULL, NULL), ('17', '5', '3', NULL, '2'), ('17', '5', '4', NULL, '3'), 
('18', '1', '1', NULL, NULL), ('18', '1', '2', NULL, '6'), ('18', '1', '3', NULL, '1'), ('18', '1', '4', NULL, NULL), 
('18', '2', '1', NULL, '1'), ('18', '2', '2', 'purple', NULL), ('18', '2', '3', 'green', NULL), ('18', '2', '4', NULL, NULL), 
('18', '3', '1', 'green', NULL), ('18', '3', '2', NULL, '2'), ('18', '3', '3', NULL, '5'), ('18', '3', '4', NULL, NULL), 
('18', '4', '1', 'purple', NULL), ('18', '4', '2', NULL, '5'), ('18', '4', '3', NULL, '3'), ('18', '4', '4', NULL, NULL), 
('18', '5', '1', NULL, '4'), ('18', '5', '2', 'green', NULL), ('18', '5', '3', 'purple', NULL), ('18', '5', '4', NULL, NULL), 
('19', '1', '1', NULL, '6'), ('19', '1', '2', NULL, '5'), ('19', '1', '3', 'red', NULL), ('19', '1', '4', 'yellow', NULL), 
('19', '2', '1', 'purple', NULL), ('19', '2', '2', NULL, NULL), ('19', '2', '3', NULL, '6'), ('19', '2', '4', 'red', NULL), 
('19', '3', '1', NULL, NULL), ('19', '3', '2', 'purple', NULL), ('19', '3', '3', NULL, NULL), ('19', '3', '4', NULL, '5'), 
('19', '4', '1', NULL, NULL), ('19', '4', '2', NULL, NULL), ('19', '4', '3', 'purple', NULL), ('19', '4', '4', NULL, '4'), 
('19', '5', '1', NULL, '5'), ('19', '5', '2', NULL, NULL), ('19', '5', '3', NULL, NULL), ('19', '5', '4', NULL, '3'), 
('20', '1', '1', NULL, '4'), ('20', '1', '2', NULL, NULL), ('20', '1', '3', NULL, NULL), ('20', '1', '4', NULL, '5'), 
('20', '2', '1', NULL, NULL), ('20', '2', '2', NULL, NULL), ('20', '2', '3', NULL, '3'), ('20', '2', '4', 'green', NULL), 
('20', '3', '1', NULL, '2'), ('20', '3', '2', NULL, '6'), ('20', '3', '3', 'green', NULL), ('20', '3', '4', NULL, '1'), 
('20', '4', '1', NULL, '5'), ('20', '4', '2', 'green', NULL), ('20', '4', '3', NULL, '4'), ('20', '4', '4', NULL, NULL), 
('20', '5', '1', 'green', NULL), ('20', '5', '2', NULL, '2'), ('20', '5', '3', NULL, NULL), ('20', '5', '4', NULL, NULL), 
('21', '1', '1', NULL, '6'), ('21', '1', '2', NULL, NULL), ('21', '1', '3', NULL, '4'), ('21', '1', '4', 'green', NULL), 
('21', '2', '1', 'blue', NULL), ('21', '2', '2', NULL, '5'), ('21', '2', '3', 'red', NULL), ('21', '2', '4', NULL, '6'), 
('21', '3', '1', NULL, NULL), ('21', '3', '2', 'blue', NULL), ('21', '3', '3', NULL, '2'), ('21', '3', '4', 'yellow', NULL), 
('21', '4', '1', NULL, NULL), ('21', '4', '2', NULL, NULL), ('21', '4', '3', 'blue', NULL), ('21', '4', '4', NULL, '3'), 
('21', '5', '1', NULL, '1'), ('21', '5', '2', NULL, NULL), ('21', '5', '3', NULL, NULL), ('21', '5', '4', 'purple', NULL), 
('22', '1', '1', NULL, '1'), ('22', '1', '2', 'purple', NULL), ('22', '1', '3', 'yellow', NULL), ('22', '1', '4', NULL, NULL), 
('22', '2', '1', 'purple', NULL), ('22', '2', '2', 'yellow', NULL), ('22', '2', '3', NULL, NULL), ('22', '2', '4', NULL, '5'), 
('22', '3', '1', 'yellow', NULL), ('22', '3', '2', NULL, NULL), ('22', '3', '3', NULL, NULL), ('22', '3', '4', NULL, '4'), 
('22', '4', '1', NULL, NULL), ('22', '4', '2', NULL, NULL), ('22', '4', '3', NULL, '5'), ('22', '4', '4', NULL, '2'), 
('22', '5', '1', NULL, '4'), ('22', '5', '2', NULL, '6'), ('22', '5', '3', NULL, '3'), ('22', '5', '4', NULL, '1'), 
('23', '1', '1', NULL, NULL), ('23', '1', '2', NULL, '1'), ('23', '1', '3', 'blue', NULL), ('23', '1', '4', NULL, NULL), 
('23', '2', '1', NULL, NULL), ('23', '2', '2', 'green', NULL), ('23', '2', '3', NULL, '5'), ('23', '2', '4', 'blue', NULL), 
('23', '3', '1', NULL, '1'), ('23', '3', '2', NULL, '3'), ('23', '3', '3', NULL, '4'), ('23', '3', '4', NULL, '5'), 
('23', '4', '1', NULL, NULL), ('23', '4', '2', 'blue', NULL), ('23', '4', '3', NULL, '6'), ('23', '4', '4', 'green', NULL), 
('23', '5', '1', NULL, NULL), ('23', '5', '2', NULL, '2'), ('23', '5', '3', 'green', NULL), ('23', '5', '4', NULL, NULL), 
('24', '1', '1', NULL, '2'), ('24', '1', '2', 'yellow', NULL), ('24', '1', '3', NULL, NULL), ('24', '1', '4', NULL, NULL), 
('24', '2', '1', NULL, NULL), ('24', '2', '2', NULL, '6'), ('24', '2', '3', 'blue', NULL), ('24', '2', '4', NULL, '3'), 
('24', '3', '1', NULL, '5'), ('24', '3', '2', 'purple', NULL), ('24', '3', '3', NULL, '4'), ('24', '3', '4', NULL, NULL), 
('24', '4', '1', NULL, NULL), ('24', '4', '2', NULL, '2'), ('24', '4', '3', 'green', NULL), ('24', '4', '4', NULL, '5'), 
('24', '5', '1', NULL, '1'), ('24', '5', '2', 'red', NULL), ('24', '5', '3', NULL, NULL), ('24', '5', '4', NULL, NULL);

COMMIT;

-- Create Triggers

-- Let op: Triggers krijgen jullie officieel pas in het vak Databases 3 in het tweede jaar van de opleiding. Jullie hoeven deze dus niet te begrijpen.
-- De triggers zorgen ervoor dat wanneer iemand bepaalde verkeerde waarden in de database probeert te plaatsen (INSERT/UPDATE), er een error wordt gegeven.

-- convention: TR_{tablename}_{when}
-- example: TR_Customer_U

-- Errorcode ; Description
-- 9001 ; Lookup-table cannot be adjusted.
-- 9002 ; Cards cannot be adjusted after having been drawn.
-- 9005 ; Value must be between [X] and [Y]. (including X and Y)
-- 9006 ; No more than 4 players can play the game.
-- 9007 ; Seqnr has a specific order, adhere to this
-- 9008 ; Player who has the turn must be consistent in the database
-- 9010 ; It is not allowed to adjust stored patterncards in 'patterncard'. Nonstandard random patterncards can be added.
-- 9011 ; Nonstandard random cards must have field 'standard' set to 0.
-- 9012 ; A player can draw up to 4 patterncardoptions.
-- 9020 ; Accountdetails must consist of at least 3 characters.
-- 9021 ; Accountdetails must consist of only letters and numbers.

DELIMITER $$
CREATE TRIGGER tr_player_insert BEFORE INSERT 
ON player
for each row
begin
	if (SELECT COUNT(*) FROM player WHERE idgame = new.idgame) > 4
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9006,
			MESSAGE_TEXT = 'No more than 4 players can play the game.';
		END;
	end if;
end$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_round_update BEFORE UPDATE 
ON round
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'round\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_round_insert BEFORE INSERT 
ON round
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'round\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_color_update BEFORE UPDATE 
ON color
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'color\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_color_insert BEFORE INSERT 
ON color
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'color\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_playstatus_update BEFORE UPDATE 
ON playstatus
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'playstatus\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_playstatus_insert BEFORE INSERT 
ON playstatus
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'playstatus\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_position_update BEFORE UPDATE 
ON position
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'position\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_position_insert BEFORE INSERT 
ON position
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'position\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_die_update BEFORE UPDATE 
ON die
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'die\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_die_insert BEFORE INSERT 
ON die
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'die\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_public_objectivecard_update BEFORE UPDATE 
ON public_objectivecard
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'public_objectivecard\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_public_objectivecard_insert BEFORE INSERT 
ON public_objectivecard
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'public_objectivecard\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_toolcard_update BEFORE UPDATE 
ON toolcard
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'toolcard\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_toolcard_insert BEFORE INSERT 
ON toolcard
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9001,
			MESSAGE_TEXT = 'lookup-table \'toolcard\' cannot be adjusted.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_patterncardoption_update BEFORE UPDATE 
ON patterncardoption
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9002,
			MESSAGE_TEXT = 'Options for patterncards in \'patterncardoption\' cannot be adjusted after having been drawn.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_patterncardoption_insert BEFORE INSERT 
ON patterncardoption
for each row
begin
	if (SELECT COUNT(*) FROM patterncardoption WHERE idplayer = new.idplayer) > 4
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9012,
			MESSAGE_TEXT = 'A player can draw up to 4 cards in patterncardoption.';
		END;
	end if;
end$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_patterncard_update BEFORE UPDATE 
ON patterncard
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9010,
			MESSAGE_TEXT = 'It is not allowed to adjust stored patterncards in \'patterncard\'. Nonstandard random patterncards can be added.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_patterncard_insert BEFORE INSERT 
ON patterncard
for each row
begin
	if new.standard != 0
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9011,
			MESSAGE_TEXT = 'Nonstandard random cards must have field \'standard\' set to 0.';
		END;
	end if;
end$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_gameobjectivecard_public_update BEFORE UPDATE 
ON gameobjectivecard_public
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9002,
			MESSAGE_TEXT = 'Objectivecards in \'sharedpublic_objectivecard\' cannot be adjusted after having been drawn.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_gametoolcard_update BEFORE UPDATE 
ON gametoolcard
for each row
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9002,
			MESSAGE_TEXT = 'Toolcards in \'gametoolcard\' cannot be adjusted after having been drawn.';
		END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_value_patterncardfield_insert BEFORE INSERT 
ON patterncardfield
for each row
begin
	if new.value < 1 or new.value > 6
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9005,
			MESSAGE_TEXT = 'value in \'patterncardfield\' must be a value between 1 and 6';
		END;
	end if;
end$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_value_patterncardfield_update BEFORE UPDATE 
ON patterncardfield
for each row
begin
	if new.value < 1 or new.value > 6
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9005,
			MESSAGE_TEXT = 'value in \'patterncardfield\' must be a value between 1 and 6';
		END;
	end if;
end$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_eyes_gamedie_insert BEFORE INSERT 
ON gamedie
for each row
begin
	if new.eyes < 1 or new.eyes > 6
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9005,
			MESSAGE_TEXT = 'eyes in \'gamedie\' must be a value between 1 and 6';
		END;
	end if;
end$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_eyes_gamedie_update BEFORE UPDATE
ON gamedie
for each row
begin
	if new.eyes < 1 or new.eyes > 6
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9005,
			MESSAGE_TEXT = 'eyes in \'gamedie\' must be a value between 1 and 6';
		END;
	end if;
end$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_player_update AFTER UPDATE 
ON player
for each row
begin
 if old.seqnr != new.seqnr 
 then 
	if new.seqnr < 1 or new.seqnr > (SELECT COUNT(*) from player where idgame = new.idgame)
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9005,
			MESSAGE_TEXT = 'seqnr must be a value between 1 and numberOfPlayers';
		END;
	end if;
	if  (old.seqnr = 1 and new.seqnr != (SELECT COUNT(*) from player where idgame = new.idgame))
		or
        (old.seqnr = 2 and new.seqnr != 1)
        or
        ((SELECT COUNT(*) from player where idgame = new.idgame) > 2 and old.seqnr = 3 and new.seqnr != 2)
        or
        ((SELECT COUNT(*) from player where idgame = new.idgame) > 3 and old.seqnr = 4 and new.seqnr != 3)
    then 
        BEGIN
			set @message_text = CONCAT("In a new round, seqnr ",new.seqnr," is not the correct seqnr when player previous round ended with seqnr ",old.seqnr);
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9007,
			MESSAGE_TEXT = @message_text;
		END;
	end if;
 end if;
end$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_account_username_insert BEFORE INSERT
ON account
for each row
begin
	if (SELECT LENGTH(new.username) < 3)
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9020,
			MESSAGE_TEXT = 'username in \'account\' must consist of at least 3 characters.';
		END;
	end if;    
	if (select new.username REGEXP '[^[:alnum:]]' = 1)
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9021,
			MESSAGE_TEXT = 'username in \'account\' must consist of only letters and numbers.';
		END;
	end if;    
	if (SELECT LENGTH(new.password) < 3)
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9020,
			MESSAGE_TEXT = 'password in \'account\' must consist of at least 3 characters.';
		END;
	end if;
	if (select new.password REGEXP '[^[:alnum:]]' = 1)
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9021,
			MESSAGE_TEXT = 'password in \'account\' must consist of only letters and numbers.';
		END;
	end if;
end$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tr_account_username_update BEFORE UPDATE
ON account
for each row
begin
	if (SELECT LENGTH(new.username) < 3)
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9020,
			MESSAGE_TEXT = 'username in \'account\' must consist of at least 3 characters.';
		END;
	end if;
	if (select new.username REGEXP '[^[:alnum:]]' = 1)
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9021,
			MESSAGE_TEXT = 'username in \'account\' must consist of only letters and numbers.';
		END;
	end if;
	if (SELECT LENGTH(new.password) < 3)
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9020,
			MESSAGE_TEXT = 'password in \'account\' must consist of at least 3 characters.';
		END;
	end if;
	if (select new.password REGEXP '[^[:alnum:]]' = 1)
    then 
        BEGIN
			SIGNAL SQLSTATE '45000' SET 
			MYSQL_ERRNO = 9021,
			MESSAGE_TEXT = 'password in \'account\' must consist of only letters and numbers.';
		END;
	end if;
end$$
DELIMITER ;