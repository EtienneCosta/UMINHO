-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema LivrariaBello
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema LivrariaBello
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `LivrariaBello` DEFAULT CHARACTER SET utf8 ;
USE `LivrariaBello` ;

-- -----------------------------------------------------
-- Table `LivrariaBello`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LivrariaBello`.`Cliente` (
  `idCliente` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `DataNascimento` DATE NOT NULL,
  `Localidade` VARCHAR(45) NOT NULL,
  `Rua` VARCHAR(45) NOT NULL,
  `CodigoPostal` VARCHAR(45) NOT NULL,
  `Telefone` VARCHAR(15) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCliente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LivrariaBello`.`Funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LivrariaBello`.`Funcionario` (
  `idFuncionario` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `DataNascimento` DATE NOT NULL,
  `Localidade` VARCHAR(45) NOT NULL,
  `Rua` VARCHAR(45) NOT NULL,
  `CodigoPostal` VARCHAR(45) NOT NULL,
  `Telefone` VARCHAR(15) NOT NULL,
  `Vencimento` FLOAT NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idFuncionario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LivrariaBello`.`Venda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LivrariaBello`.`Venda` (
  `idVenda` INT NOT NULL,
  `Data` DATE NOT NULL,
  `Cupao` DECIMAL(4,2) NOT NULL,
  `Total` DECIMAL(8,2) NOT NULL,
  `Funcionario` INT NOT NULL,
  `Cliente` INT NOT NULL,
  PRIMARY KEY (`idVenda`),
  INDEX `fk_Venda_Cliente1_idx` (`Cliente` ASC),
  INDEX `fk_Venda_Funcionario1_idx` (`Funcionario` ASC),
  CONSTRAINT `fk_Venda_Cliente1`
    FOREIGN KEY (`Cliente`)
    REFERENCES `LivrariaBello`.`Cliente` (`idCliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Venda_Funcionario1`
    FOREIGN KEY (`Funcionario`)
    REFERENCES `LivrariaBello`.`Funcionario` (`idFuncionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LivrariaBello`.`Categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LivrariaBello`.`Categoria` (
  `idCategoria` INT NOT NULL,
  `Genero` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idCategoria`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LivrariaBello`.`Escritor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LivrariaBello`.`Escritor` (
  `idEscritor` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Nacionalidade` VARCHAR(45) NOT NULL,
  `DataNascimento` DATE NOT NULL,
  `Biografia` VARCHAR(45) NULL,
  PRIMARY KEY (`idEscritor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LivrariaBello`.`Livro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LivrariaBello`.`Livro` (
  `Isbn` INT NOT NULL,
  `Titulo` VARCHAR(75) NOT NULL,
  `Editora` VARCHAR(45) NOT NULL,
  `Edicao` INT NOT NULL,
  `Ano` INT NOT NULL,
  `Stock` INT NOT NULL,
  `Paginas` INT NOT NULL,
  `Preco` DECIMAL(6,2) NOT NULL,
  `Idioma` VARCHAR(45) NOT NULL,
  `Categoria` INT NOT NULL,
  `Escritor` INT NOT NULL,
  PRIMARY KEY (`Isbn`),
  INDEX `fk_Livro_Categoria1_idx` (`Categoria` ASC),
  INDEX `fk_Livro_Escritor1_idx` (`Escritor` ASC),
  CONSTRAINT `fk_Livro_Categoria1`
    FOREIGN KEY (`Categoria`)
    REFERENCES `LivrariaBello`.`Categoria` (`idCategoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Livro_Escritor1`
    FOREIGN KEY (`Escritor`)
    REFERENCES `LivrariaBello`.`Escritor` (`idEscritor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LivrariaBello`.`LivroVenda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LivrariaBello`.`LivroVenda` (
  `Livro` INT NOT NULL,
  `Venda` INT NOT NULL,
  `Quantidade` INT NOT NULL,
  `Preco` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`Livro`, `Venda`),
  INDEX `fk_Livro_has_Venda_Venda1_idx` (`Venda` ASC),
  INDEX `fk_Livro_has_Venda_Livro1_idx` (`Livro` ASC),
  CONSTRAINT `fk_Livro_has_Venda_Livro1`
    FOREIGN KEY (`Livro`)
    REFERENCES `LivrariaBello`.`Livro` (`Isbn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Livro_has_Venda_Venda1`
    FOREIGN KEY (`Venda`)
    REFERENCES `LivrariaBello`.`Venda` (`idVenda`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LivrariaBello`.`LivroVenda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LivrariaBello`.`LivroVenda` (
  `Livro` INT NOT NULL,
  `Venda` INT NOT NULL,
  `Quantidade` INT NOT NULL,
  `Preco` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`Livro`, `Venda`),
  INDEX `fk_Livro_has_Venda_Venda1_idx` (`Venda` ASC),
  INDEX `fk_Livro_has_Venda_Livro1_idx` (`Livro` ASC),
  CONSTRAINT `fk_Livro_has_Venda_Livro1`
    FOREIGN KEY (`Livro`)
    REFERENCES `LivrariaBello`.`Livro` (`Isbn`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Livro_has_Venda_Venda1`
    FOREIGN KEY (`Venda`)
    REFERENCES `LivrariaBello`.`Venda` (`idVenda`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
