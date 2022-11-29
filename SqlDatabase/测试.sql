-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: atlantis
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `join_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'testadmin','25d55ad283aa400af464c76d713c07ad','admin account for test','2022-10-13 15:28:45',1),(2,'Frank_CS','4ff92f62f1f29a2fd5c9228eaca019bf','admin Frank','2022-11-29 09:02:57',1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apply`
--

DROP TABLE IF EXISTS `apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apply` (
  `id` int NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apply`
--

LOCK TABLES `apply` WRITE;
/*!40000 ALTER TABLE `apply` DISABLE KEYS */;
INSERT INTO `apply` VALUES (1,'testuser01');
/*!40000 ALTER TABLE `apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `index` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  `route` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='category in tutorial that includes ''basic'', ''premium'', ''rules'' and so on.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'入门','basic'),(2,'进阶','premium'),(3,'联机','online'),(4,'守则','rules'),(5,'分类测试','test');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `realname` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `join_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int DEFAULT '0',
  `position` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'队长','队长','2022-11-29 09:23:09',1,'队长'),(2,'群主','群主','2022-11-29 09:23:20',0,'群主'),(3,'管理01','管理','2022-11-29 09:23:42',0,'管理'),(4,'管理02','管理','2022-11-29 09:23:49',0,'管理'),(5,'成员01','成员\n','2022-11-29 09:24:04',0,'成员'),(6,'成员02','成员','2022-11-29 09:24:12',0,'成员'),(7,'测试01','测试','2022-11-29 09:24:26',0,'测试'),(8,'测试02','测试','2022-11-29 09:24:31',0,'测试');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news_article`
--

DROP TABLE IF EXISTS `news_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news_article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index` int DEFAULT '1' COMMENT '新闻资讯类别\n1 = 游戏新闻；2 = 游戏公告',
  `title` varchar(100) DEFAULT NULL,
  `content` longtext,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `thumbnail` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新闻资讯的数据储存表。\n其中id为主键，某篇文章的唯一标识；\nthumbnail为缩略图，暂时不启用，可能后期加；\nindex为新闻资讯的类别：1 = 游戏新闻；2 = 游戏公告。\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news_article`
--

LOCK TABLES `news_article` WRITE;
/*!40000 ALTER TABLE `news_article` DISABLE KEYS */;
INSERT INTO `news_article` VALUES (1,1,'游戏新闻测试','# 游戏新闻测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```\n\n* 图片测试\n![04.Evangelion 3.01.01 14.jpg](http://172.16.10.210:8081/newsArticles/download/2a1bd0bed3ae53b177b2962880c1fe11.jpg)\n![04.Evangelion 3.01.01 16.jpg](http://172.16.10.210:8081/newsArticles/download/887d0ec7e9f13bc46a4b9872f2541fdd.jpg)\n![04.Evangelion 3.01.01 17.jpg](http://172.16.10.210:8081/newsArticles/download/4fbaf9f6f6667b375800b7cf72ec9b84.jpg)','2022-11-29 09:17:02',NULL),(2,2,'游戏公告测试','# 游戏新闻测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```\n* 图片测试\n![04.Evangelion 3.01.01 10.jpg](http://172.16.10.210:8081/newsArticles/download/bcc6ffcbefeeaf728277b6880a0ecb9c.jpg)![04.Evangelion 3.01.01 11.jpg](http://172.16.10.210:8081/newsArticles/download/4f86f6f4d53881a2bad60353b5511ded.jpg)![04.Evangelion 3.01.01 12.jpg](http://172.16.10.210:8081/newsArticles/download/d1a4073043c26f2686afb0877548daa4.jpg)','2022-11-29 09:18:34',NULL);
/*!40000 ALTER TABLE `news_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reset`
--

DROP TABLE IF EXISTS `reset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reset` (
  `id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='users that want to reset password';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reset`
--

LOCK TABLES `reset` WRITE;
/*!40000 ALTER TABLE `reset` DISABLE KEYS */;
/*!40000 ALTER TABLE `reset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial_article`
--

DROP TABLE IF EXISTS `tutorial_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutorial_article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index` int DEFAULT '1',
  `title` varchar(100) DEFAULT NULL,
  `content` longtext,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `thumbnail` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='跟news_article一样，index键需要跟catergory表进行联查';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_article`
--

LOCK TABLES `tutorial_article` WRITE;
/*!40000 ALTER TABLE `tutorial_article` DISABLE KEYS */;
INSERT INTO `tutorial_article` VALUES (1,1,'入门测试','# 入门测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```','2022-11-29 09:20:29',NULL),(2,2,'进阶测试','# 进阶测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```','2022-11-29 09:20:52',NULL),(3,3,'联机测试','# 联机测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```','2022-11-29 09:21:19',NULL),(4,4,'守则测试','# 守则测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```','2022-11-29 09:21:33',NULL),(5,5,'分类测试01','# 分类测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```','2022-11-29 09:21:58',NULL),(6,5,'分类测试02','# 分类测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```','2022-11-29 09:22:06',NULL);
/*!40000 ALTER TABLE `tutorial_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `join_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'testuser01','e10adc3949ba59abbe56e057f20f883e','测试用户','uer account for test','male','2002-05-22','2022-11-29 09:04:29',1),(2,'testuser02','e10adc3949ba59abbe56e057f20f883e','测试用户','user account for test','female','2002-05-22','2022-11-29 09:05:43',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_admin`
--

DROP TABLE IF EXISTS `user_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_admin` (
  `user_id` int NOT NULL,
  `admin_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='A table that contains the relationship between userid, adminid, and memberid\n陈实';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_admin`
--

LOCK TABLES `user_admin` WRITE;
/*!40000 ALTER TABLE `user_admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_message`
--

DROP TABLE IF EXISTS `user_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_message` (
  `index` int NOT NULL AUTO_INCREMENT,
  `source` varchar(45) DEFAULT NULL COMMENT '可以是admin name，可以是username，可以为空',
  `destination` int DEFAULT NULL COMMENT '目标用户id',
  `title` varchar(45) DEFAULT NULL,
  `content` mediumtext,
  `send_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int DEFAULT '0' COMMENT '是否已读',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='message to each user';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_message`
--

LOCK TABLES `user_message` WRITE;
/*!40000 ALTER TABLE `user_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_message` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-29  9:30:19
