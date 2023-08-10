-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema homeflix
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema homeflix
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `homeflix` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `homeflix` ;

-- -----------------------------------------------------
-- Table `homeflix`.`Calificacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `homeflix`.`Calificacion` (
  `idCalificacion` INT NOT NULL AUTO_INCREMENT,
  `idVideo` INT NULL DEFAULT NULL,
  `idUsuario` INT NULL DEFAULT NULL,
  `estado` TINYINT NULL DEFAULT NULL,
  PRIMARY KEY (`idCalificacion`))
ENGINE = InnoDB
AUTO_INCREMENT = 38
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `homeflix`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `homeflix`.`Usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(500) NOT NULL,
  `apellido` VARCHAR(500) NOT NULL,
  `nombreUsuario` VARCHAR(500) NOT NULL,
  `contrasenna` VARCHAR(500) NOT NULL,
  `archivoImagen` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nombreUsuario_UNIQUE` (`nombreUsuario` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `homeflix`.`ListaVideos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `homeflix`.`ListaVideos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(1000) NOT NULL,
  `enlaceImagen` VARCHAR(500) NULL DEFAULT NULL,
  `idUsuario` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKIdUsuario_idx` (`idUsuario` ASC) VISIBLE,
  CONSTRAINT `FKIdUsuario`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `homeflix`.`Usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `homeflix`.`Video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `homeflix`.`Video` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(400) NOT NULL,
  `categoria` VARCHAR(400) NOT NULL,
  `fecha` DATE NOT NULL,
  `Descripcion` VARCHAR(1000) NOT NULL,
  `calificacion` INT NULL DEFAULT NULL,
  `enlaceVideo` VARCHAR(500) NOT NULL,
  `enlaceImagen` VARCHAR(500) NULL DEFAULT NULL,
  `idUsuario` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idUsuario_idx` (`idUsuario` ASC) VISIBLE,
  CONSTRAINT `idUsuario`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `homeflix`.`Usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `homeflix`.`listaVideos_video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `homeflix`.`listaVideos_video` (
  `idListaVideos` INT NULL DEFAULT NULL,
  `idVideo` INT NULL DEFAULT NULL,
  INDEX `FKidVideo_idx` (`idVideo` ASC) VISIBLE,
  INDEX `FKidListaVideos` (`idListaVideos` ASC) VISIBLE,
  CONSTRAINT `FKidListaVideos`
    FOREIGN KEY (`idListaVideos`)
    REFERENCES `homeflix`.`ListaVideos` (`id`),
  CONSTRAINT `FKidVideo`
    FOREIGN KEY (`idVideo`)
    REFERENCES `homeflix`.`Video` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
