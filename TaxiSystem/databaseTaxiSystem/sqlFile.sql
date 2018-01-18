CREATE DATABASE  IF NOT EXISTS `taxisystem` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `taxisystem`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: taxisystem
-- ------------------------------------------------------
-- Server version	5.5.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car` (
  `number` char(7) NOT NULL COMMENT 'Государственный номер машины формата, пример: 3333AD7.',
  `car` varchar(45) NOT NULL COMMENT 'Марка и модель автомобиля. Не разбивал на отдельные таблицы марку и модель из-за того что в моей системе клиенту будут предъявлять марку и модель вместе. ',
  `colour` varchar(45) NOT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Используется для учета всех машин, имеющихся у таксопарка';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES ('1111AH1','AUDI 8','white'),('2222AH7','BMW X5','black'),('3333AH7','Mazda 28','red'),('4444AH7','Porsche 918','grey'),('5555AH7','Audi A6','white'),('6666AH7','Lexus RX 400h','black'),('7777AH7','Nissan Navara','red'),('8888AH7','ГАЗ-69','Green');
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный номер клиента. Введен из-за того что альтернативный ключ логин может быть слишком большим.',
  `login` varchar(45) NOT NULL COMMENT 'Логин для входа в систему',
  `password` varchar(45) NOT NULL COMMENT 'Пароль для входа',
  `name` varchar(45) DEFAULT NULL COMMENT 'Имя клиента. ',
  `surname` varchar(45) DEFAULT NULL COMMENT 'Фамилия клиента.',
  `bonusPoints` int(11) NOT NULL DEFAULT '0' COMMENT 'Колличество бонусных баллов у клиента. За бонусные баллы можно оплатить поездку. По умолчанию бонусов - 0.',
  `banStatus` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Поле показывающее имеет ли бан данный клиент. true - имеет, false - не имеет.',
  `role` varchar(45) NOT NULL DEFAULT 'client',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='Используется для хранения записей о клиентах.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'root','63a9f0ea7bb98050796b649e85481845','Admin',NULL,170,0,'admin'),(2,'client','63a9f0ea7bb98050796b649e85481845','Андрей','Саха',1,0,'client'),(3,'Ivan','3d14138fa92c14e3f7a0146fc1939477','Иван','Иванов',2,0,'client'),(4,'SaSha','63a9f0ea7bb98050796b649e85481845','Рома','Ильшин',50,0,'client'),(5,'Angel','63a9f0ea7bb98050796b649e85481845','Денис','Карпат',50,0,'client'),(6,'Ybivec','63a9f0ea7bb98050796b649e85481845','Дементий',NULL,55,1,'client'),(7,'Kamera','63a9f0ea7bb98050796b649e85481845','Катерина',NULL,60,0,'client'),(8,'Roni','63a9f0ea7bb98050796b649e85481845',NULL,NULL,34,1,'client'),(9,'SAxeg','63a9f0ea7bb98050796b649e85481845','Денис',NULL,48,0,'client'),(10,'quenn','63a9f0ea7bb98050796b649e85481845','John','Subli',55,0,'client'),(11,'PETRO','63a9f0ea7bb98050796b649e85481845','Евгений','Жевен',190,1,'client'),(13,'Ivan123','193c67a77c7dc81f0cd3085abd959132','','',0,0,'client'),(14,'Mitsh123','827ccb0eea8a706c4c34a16891f84e7b',NULL,NULL,0,0,'client'),(15,'meow','24e4b3ecb6310d47d38c68da297c5c5b',NULL,NULL,0,0,'client'),(16,'vitbor777','c7ecb139bba4bf4e544c8c40c1adef18',NULL,NULL,0,0,'client'),(17,'Korry','202cb962ac59075b964b07152d234b70',NULL,NULL,0,0,'client');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный номер (ключ) заказа, создавалься из-за того что cliend_id и taxi_id в качестве составного ключа не давали бы уникальность записей так как клиент может еще раз попасть к такому же таксисту. ',
  `client_id` int(11) NOT NULL COMMENT 'Внешний ключ клиента.',
  `taxi_id` int(11) NOT NULL COMMENT 'Внешний ключ таксиста.',
  `orderStatus` enum('rejected','accepted','processed','completed','archive') NOT NULL DEFAULT 'processed' COMMENT 'processed - лжидает подтверждения\nrejected - отклонен таксистом\naccepted - приянт таксистом (чтобы перейти дальже нужно оплатить)\ncompleted - оплачен, можно оставить отзыв, чтобы перейти дальше нужно оставить отзыв\narchive - с отзывом, больше никому не нужен',
  `source_coord` varchar(35) NOT NULL COMMENT 'Исходные координаты клиента.\n',
  `destiny_coord` varchar(35) NOT NULL COMMENT 'Кординаты пункта назначения клиента.',
  `price` decimal(10,2) NOT NULL COMMENT 'Цена поездки.',
  PRIMARY KEY (`order_id`),
  KEY `fk_Client_has_Taxi_Taxi1_idx` (`taxi_id`),
  KEY `fk_Client_has_Taxi_Client1_idx` (`client_id`),
  KEY `order` (`client_id`,`taxi_id`),
  CONSTRAINT `fk_Client_has_Taxi_Client1` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Client_has_Taxi_Taxi1` FOREIGN KEY (`taxi_id`) REFERENCES `taxi` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='Используется для хранения заказов клиентов. Является промежуточной таблицей, между Client и Taxi.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,2,4,'completed','65.58585,-20.88819','53.90453,27.56152',1994.00),(2,6,6,'processed','53.91222,27.50281','53.92233,27.61474',6.12),(3,11,4,'accepted','53.90453,27.56152','53.90554,27.46539',4.35),(4,5,4,'rejected','53.91161,27.46505','53.90938,27.59826',6.65),(5,4,6,'accepted','53.91019,27.49286','53.90979,27.61714',5.14),(6,3,9,'accepted','53.93466,27.49869','53.87661,27.56633',4.46),(7,7,6,'accepted','53.92718,27.63568','53.87823,27.59585',4.75),(8,8,10,'accepted','53.89482,27.59929','53.88855,27.53543',4.19),(13,2,4,'archive','53.92556,27.46917','53.91242,27.62469',6.24),(14,2,4,'accepted','53.90453,27.56152','53.90453,27.56152',0.00),(54,2,6,'processed','53.89320,27.56152','53.92192,27.62126',3.58),(55,2,10,'processed','53.91788,27.60959','53.88349,27.50590',6.25);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review` (
  `review_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный номер (ключ) отзыва, создавалься из-за того что cliend_id и taxi_id в качестве составного ключа не давали бы уникальность записей так как клиент может еще раз попасть к такому же таксисту.',
  `client_id` int(11) NOT NULL COMMENT 'Внишний ключ клиента.',
  `taxi_id` int(11) NOT NULL COMMENT 'Внешний ключ таксиста',
  `comment` text COMMENT 'Комментарий (отзыв) к таксисту. Может отсуствовать.',
  PRIMARY KEY (`review_id`),
  KEY `fk_Client_has_Taxi_Taxi2_idx` (`taxi_id`),
  KEY `fk_client_has_Taxi_client_idx` (`client_id`),
  KEY `rewiew` (`client_id`,`taxi_id`),
  CONSTRAINT `fk_client_has_Taxi_Client2` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Client_has_Taxi_Taxi2` FOREIGN KEY (`taxi_id`) REFERENCES `taxi` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='Используется для хранения отзывов о таксистах. Является промежуточной таблицей, между Client и Taxi.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,2,4,'3 Супер!!11!!'),(2,2,4,'Круть'),(3,2,4,'неоч'),(4,2,4,'test\r\n'),(5,2,4,'123456'),(6,2,4,'achive');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxi`
--

DROP TABLE IF EXISTS `taxi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taxi` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный номер таксиста. Введен из-за того что альтернативный ключ логин может быть слишком большим.',
  `carNumber` char(7) NOT NULL COMMENT 'Государственный номер машины, на которой ездит текущий таксист, является внешним ключем к таблице car. ',
  `login` varchar(45) NOT NULL COMMENT 'логин для входа в систему',
  `password` varchar(45) NOT NULL COMMENT 'пароль',
  `name` varchar(45) NOT NULL COMMENT 'Имя таксиста.',
  `surname` varchar(45) NOT NULL COMMENT 'Фамилия таксиста.',
  `availableStatus` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Поле показывающее доступен ли в данный момент таксист. Если в данный момент у него заказон нет, то значение true, если таксист не в системе или на заказе, то значение false.',
  `banStatus` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'есть ли бан у тасиста',
  `role` varchar(45) NOT NULL DEFAULT 'taxi',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  KEY `fk_Taxi_car1_idx` (`carNumber`),
  CONSTRAINT `fk_Taxi_car1` FOREIGN KEY (`carNumber`) REFERENCES `car` (`number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='используется для хранения записей о таксистах.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taxi`
--

LOCK TABLES `taxi` WRITE;
/*!40000 ALTER TABLE `taxi` DISABLE KEYS */;
INSERT INTO `taxi` VALUES (4,'1111AH1','taxi','63a9f0ea7bb98050796b649e85481845','Егор','Егоров',0,0,'taxi'),(5,'2222AH7','JeeOpp','63a9f0ea7bb98050796b649e85481845','Илья','Ленин',0,0,'taxi'),(6,'3333AH7','nane','63a9f0ea7bb98050796b649e85481845','Александр','Белый',1,0,'taxi'),(7,'4444AH7','qwerty','63a9f0ea7bb98050796b649e85481845','Сергей','Черный',0,0,'taxi'),(8,'5555AH7','sortTe','63a9f0ea7bb98050796b649e85481845','Денис','Долгий',0,0,'taxi'),(9,'6666AH7','Rino','63a9f0ea7bb98050796b649e85481845','Павел','Мудрый',1,0,'taxi'),(10,'7777AH7','KaMaz','63a9f0ea7bb98050796b649e85481845','Влад','Никитин',1,0,'taxi'),(38,'8888AH7','Gaz','aae61ed6f09d5877bfb5863b4bb9b79e','Дмитрий','Шелен',0,0,'taxi');
/*!40000 ALTER TABLE `taxi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'taxisystem'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-19  1:23:00
