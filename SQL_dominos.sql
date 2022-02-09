-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema dominos
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dominos
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dominos` DEFAULT CHARACTER SET utf8 ;
USE `dominos` ;

-- -----------------------------------------------------
-- Table `dominos`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dominos`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `dominos`.`addresses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dominos`.`addresses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `address_name` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `users_addresses_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `users_addresses_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `dominos`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `dominos`.`ingredients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dominos`.`ingredients` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(15,2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `dominos`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dominos`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `ordered_at` DATE NOT NULL,
  `address_id` INT NOT NULL,
  `instructions` VARCHAR(150) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE,
  INDEX `orders_addresses_fk_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `orders_addresses_fk`
    FOREIGN KEY (`address_id`)
    REFERENCES `dominos`.`addresses` (`id`),
  CONSTRAINT `orders_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `dominos`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `dominos`.`other_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dominos`.`other_products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(15,2) NOT NULL,
  `image` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `dominos`.`orders_have_other_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dominos`.`orders_have_other_products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `other_product_id` INT NOT NULL,
  `amount` INT NOT NULL,
  `price` DECIMAL(15,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `order_to_other_products_fk_idx` (`order_id` ASC) VISIBLE,
  INDEX `other_products_fk_idx` (`other_product_id` ASC) VISIBLE,
  CONSTRAINT `order_to_other_products_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `dominos`.`orders` (`id`),
  CONSTRAINT `other_products_fk`
    FOREIGN KEY (`other_product_id`)
    REFERENCES `dominos`.`other_products` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `dominos`.`pizza_breads`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dominos`.`pizza_breads` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bread_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `dominos`.`pizzas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dominos`.`pizzas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  `price` DECIMAL(15,2) NOT NULL,
  `image` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `dominos`.`orders_have_pizzas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dominos`.`orders_have_pizzas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `pizza_id` INT NOT NULL,
  `amount` INT NOT NULL,
  `price` DECIMAL(15,2) NOT NULL,
  `pizza_breads_id` INT NOT NULL,
  `pizza_sizes_id` DECIMAL(15,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `orders_pizzas_fk_idx` (`order_id` ASC) VISIBLE,
  INDEX `pizzas_fk_idx` (`pizza_id` ASC) VISIBLE,
  INDEX `pizza_breads_fk_idx` (`pizza_breads_id` ASC) VISIBLE,
  INDEX `pizza_sizes_fk_idx` (`pizza_sizes_id` ASC) VISIBLE,
  CONSTRAINT `orders_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `dominos`.`orders` (`id`),
  CONSTRAINT `pizza_breads_fk`
    FOREIGN KEY (`pizza_breads_id`)
    REFERENCES `dominos`.`pizza_breads` (`id`),
  CONSTRAINT `pizzas_fk`
    FOREIGN KEY (`pizza_id`)
    REFERENCES `dominos`.`pizzas` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `dominos`.`pizza_sizes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dominos`.`pizza_sizes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `size` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `dominos`.`pizzas_have_ingredients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dominos`.`pizzas_have_ingredients` (
  `pizza_id` INT NOT NULL,
  `ingredient_id` INT NOT NULL,
  INDEX `pizzas_have_ingredients_pizza_fk_idx` (`pizza_id` ASC) VISIBLE,
  INDEX `pizzas_have_ingredients_ingredient_fk_idx` (`ingredient_id` ASC) VISIBLE,
  CONSTRAINT `pizzas_have_ingredients_ingredient_fk`
    FOREIGN KEY (`ingredient_id`)
    REFERENCES `dominos`.`ingredients` (`id`),
  CONSTRAINT `pizzas_have_ingredients_pizza_fk`
    FOREIGN KEY (`pizza_id`)
    REFERENCES `dominos`.`pizzas` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
