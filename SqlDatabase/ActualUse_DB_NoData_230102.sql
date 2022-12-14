

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='category in tutorial that includes ''basic'', ''premium'', ''rules'' and so on.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `index` int NOT NULL AUTO_INCREMENT,
  `id` int DEFAULT '0' COMMENT '?????????article??????',
  `login_type` varchar(45) DEFAULT 'users' COMMENT 'users or admins',
  `name` varchar(100) DEFAULT '??????',
  `content` longtext,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `pre_index` int DEFAULT '-1' COMMENT '???????????????????????????????????????????????????????????????',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='???????????????forum_article????????????????????????????????????id???article??????';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `forum_article`
--

DROP TABLE IF EXISTS `forum_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum_article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index` int DEFAULT '1',
  `title` varchar(100) DEFAULT NULL,
  `content` longtext,
  `author` varchar(100) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `thumbnail` varchar(200) DEFAULT NULL,
  `view` int DEFAULT '0' COMMENT '?????????????????????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='???tutorial_article?????????index????????????topic???????????????';
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `status` int DEFAULT '1',
  `position` varchar(100) DEFAULT NULL,
  `has_photo` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `news_article`
--

DROP TABLE IF EXISTS `news_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news_article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index` int DEFAULT '1' COMMENT '??????????????????\n1 = ???????????????2 = ????????????',
  `title` varchar(100) DEFAULT NULL,
  `content` longtext,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `thumbnail` varchar(200) DEFAULT NULL,
  `view` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='?????????????????????????????????\n??????id??????????????????????????????????????????\nthumbnail???????????????????????????????????????????????????\nindex???????????????????????????1 = ???????????????2 = ???????????????\n';
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic` (
  `index` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  `route` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='??????????????????????????????category???????????????????????????route?????????topicIndex';
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `view` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='???news_article?????????index????????????catergory???????????????';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='A table that contains the relationship between userid, adminid, and memberid\n??????';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_message`
--

DROP TABLE IF EXISTS `user_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_message` (
  `index` int NOT NULL AUTO_INCREMENT,
  `source` varchar(45) DEFAULT NULL COMMENT '?????????admin name????????????username???????????????',
  `destination` int DEFAULT NULL COMMENT '????????????id',
  `title` varchar(45) DEFAULT NULL,
  `content` mediumtext,
  `send_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int DEFAULT '0' COMMENT '????????????',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='message to each user';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-02 11:46:29
