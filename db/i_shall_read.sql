-- MySQL dump 10.13  Distrib 5.5.43, for Win64 (x86)
--
-- Host: localhost    Database: i_shall_read
-- ------------------------------------------------------
-- Server version	5.5.43-log

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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `books` (
  `ID` bigint(20) NOT NULL DEFAULT '0',
  `BOOK_NAME` varchar(100) COLLATE utf8_bin NOT NULL COMMENT 'Название книги',
  `BOOK_AUTHOR` varchar(100) COLLATE utf8_bin NOT NULL COMMENT 'Автор',
  `PUBLICATION_YEAR` int(11) DEFAULT NULL,
  `ANNOTATION` text COLLATE utf8_bin NOT NULL COMMENT 'Аннотация',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Книги';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'book','author',2000,'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),(2,'Привет','Александр',1986,'шгтцукагтушцкгтаукцагтцу'),(3,'лалала','ололо',0,'тымдтмодрилормивлом'),(4,'Вино из одуванчиков','Рей Бредбери',1956,'опывлпрлврмл'),(5,'Триумфальная арка','Ремарк',1955,'йцукенгш'),(6,'Кря','Утка',1999,'Кря-Кря-Кря');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `i_user`
--

DROP TABLE IF EXISTS `i_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `i_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(15) NOT NULL,
  `password` varchar(100) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `enabled` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `i_user`
--

LOCK TABLES `i_user` WRITE;
/*!40000 ALTER TABLE `i_user` DISABLE KEYS */;
INSERT INTO `i_user` VALUES (1,'user','$2a$10$D.6zBCNCuGtwnvn.8eQGAe/AbLJekXWWFBuwhQpkuxDYPjzXDyhNi','User','User',''),(2,'ivanov','$2a$10$D.6zBCNCuGtwnvn.8eQGAe/AbLJekXWWFBuwhQpkuxDYPjzXDyhNi','User','User',''),(3,'petrov','$2a$10$D.6zBCNCuGtwnvn.8eQGAe/AbLJekXWWFBuwhQpkuxDYPjzXDyhNi','User','User',''),(4,'sidorov','$2a$10$D.6zBCNCuGtwnvn.8eQGAe/AbLJekXWWFBuwhQpkuxDYPjzXDyhNi','User','User',''),(5,'simakov','$2a$10$D.6zBCNCuGtwnvn.8eQGAe/AbLJekXWWFBuwhQpkuxDYPjzXDyhNi','User','User',''),(6,'makakov','$2a$10$D.6zBCNCuGtwnvn.8eQGAe/AbLJekXWWFBuwhQpkuxDYPjzXDyhNi','User','User','');
/*!40000 ALTER TABLE `i_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор',
  `user_id` bigint(20) NOT NULL COMMENT 'Идентификатор пользователя',
  `BOOK_id` bigint(20) NOT NULL COMMENT 'Идентификатор книги',
  `rate` int(10) NOT NULL COMMENT 'Оценка',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Оценки';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (1,1,1,5),(2,1,2,4),(3,1,3,5),(4,2,1,4),(5,2,3,5),(6,3,2,3),(7,3,3,5),(8,3,5,4),(9,4,4,3),(10,4,5,4),(11,5,3,4),(12,5,4,2),(13,5,5,4),(14,6,1,3),(15,6,6,5);
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-09 23:26:47
