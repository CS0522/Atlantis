

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
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'testadmin','e10adc3949ba59abbe56e057f20f883e','testadmin update','2022-10-13 15:28:45',1),(2,'Frank_CS','4ff92f62f1f29a2fd5c9228eaca019bf','admin Frank','2022-11-29 09:02:57',1),(3,'testadmin01','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:24:16',1),(4,'testadmin02','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:24:24',0),(5,'testadmin03','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:24:38',0),(6,'testadmin04','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:24:51',0),(7,'testadmin05','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:25:05',0),(8,'testadmin06','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:25:13',0),(9,'testadmin07','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:25:21',0),(10,'testadmin08','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:25:27',0),(11,'testadmin09','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:25:35',0),(12,'testadmin13','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:25:42',0),(13,'testadmin13','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:33:38',0),(14,'testadmin13','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-11-29 19:33:46',1),(16,'testadmin13','e10adc3949ba59abbe56e057f20f883e',NULL,'2022-12-01 08:03:47',1);
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
INSERT INTO `apply` VALUES (1,'testuser01'),(14,'testuser100');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='category in tutorial that includes ''basic'', ''premium'', ''rules'' and so on.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'入门','basic'),(2,'进阶','premium'),(3,'联机','online'),(4,'守则','rules'),(5,'分类测试','test'),(7,'test1','testone');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `index` int NOT NULL AUTO_INCREMENT,
  `id` int DEFAULT '0' COMMENT '用于和article对应',
  `login_type` varchar(45) DEFAULT 'users' COMMENT 'users or admins',
  `name` varchar(100) DEFAULT '匿名',
  `content` longtext,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `pre_index` int DEFAULT '-1' COMMENT '上一级留言，用于存在盖楼的情况（后期添加）',
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='这个表记录forum_article每篇帖子的留言信息，通过id和article对应';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,'users','测试名1','这是测试留言','2022-12-30 17:16:02',-1),(2,1,'users','测试名2','这也是测试留言','2022-12-30 17:16:02',-1),(3,2,'users','测试名3','这还是测试留言','2022-12-30 17:16:02',-1),(4,1,'users','匿名','这是匿名测试留言','2022-12-31 15:58:59',-1),(5,3,'users','匿名','这也是匿名测试留言','2022-12-31 15:58:59',-1),(6,1,'admins','testadmin','添加留言测试','2022-12-31 19:26:22',-1),(7,1,'users','匿名','匿名添加留言测试','2022-12-31 19:26:43',-1),(8,1,'users','匿名','匿名添加测试2','2022-12-31 19:28:17',-1),(9,1,'admins','testadmin','添加留言测试2','2022-12-31 19:28:29',-1),(11,1,'admins','testadmin','分页测试','2022-12-31 19:28:37',-1),(12,1,'admins','testadmin','分页测试','2022-12-31 19:28:40',-1),(13,1,'admins','testadmin','分页测试2','2022-12-31 19:28:44',-1),(14,1,'users','匿名','分页测试','2022-12-31 19:28:54',-1),(15,1,'users','匿名','匿名测试','2022-12-31 19:45:25',-1),(19,1,'users','匿名','未登录发送留言测试','2023-01-01 11:06:02',-1),(20,1,'users','匿名','未登录发送留言测试2','2023-01-01 11:06:47',-1),(22,1,'users','匿名','登录匿名发送留言','2023-01-01 11:07:43',-1),(23,3,'admins','testadmin','管理员身份发送留言测试','2023-01-01 11:29:59',-1),(24,3,'users','匿名','管理员匿名身份发送留言测试','2023-01-01 11:30:31',-1),(25,3,'users','匿名','未登录发送留言','2023-01-01 11:31:31',-1),(28,1,'admins','testadmin','管理员身份发送 标记','2023-01-01 11:38:31',-1),(29,5,'admins','testadmin','你好','2023-01-01 17:38:22',-1),(30,6,'users','testuser01','芜湖，留言测试','2023-01-01 17:40:34',-1),(31,6,'users','匿名','留言测试','2023-01-01 17:40:40',-1);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

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
  `view` int DEFAULT '0' COMMENT '帖子的浏览次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='跟tutorial_article一样，index键需要跟topic表进行联查';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum_article`
--

LOCK TABLES `forum_article` WRITE;
/*!40000 ALTER TABLE `forum_article` DISABLE KEYS */;
INSERT INTO `forum_article` VALUES (1,1,'测试1','### 测试1内容','testadmin','2022-12-29 15:47:45',NULL,1),(2,1,'测试2','# 测试2内容','testadmin','2022-12-29 15:47:45',NULL,1),(3,2,'测试3','### 测试3内容','11111','2022-12-29 15:47:45',NULL,1),(4,2,'发布帖子测试','# 发布帖子测试  \n![wallpaper 44.png](http://localhost:8081/api/newsArticles/download/a8a7145f4a9974d686597ff15317c40d.png)','testadmin','2023-01-01 17:36:57',NULL,0),(5,2,'贴子发布测试2','1','testadmin','2023-01-01 17:38:01',NULL,0),(6,2,'用户身份发帖测试','你好','testuser01','2023-01-01 17:40:16',NULL,0);
/*!40000 ALTER TABLE `forum_article` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'队长','队长','2022-11-29 09:23:09',1,'队长'),(2,'群主','群主','2022-11-29 09:23:20',1,'群主'),(3,'管理01','管理','2022-11-29 09:23:42',1,'管理'),(4,'管理02','管理','2022-11-29 09:23:49',1,'管理'),(5,'成员01','成员\n','2022-11-29 09:24:04',0,'成员'),(6,'成员02','成员','2022-11-29 09:24:12',0,'成员'),(7,'测试01','测试','2022-11-29 09:24:26',0,'测试'),(8,'测试02','测试','2022-11-29 09:24:31',0,'测试'),(9,'成员03',NULL,'2022-11-29 19:32:05',0,'成员'),(10,'成员04',NULL,'2022-11-29 19:32:10',0,'成员'),(11,'成员05',NULL,'2022-11-29 19:32:15',0,'成员'),(12,'成员06',NULL,'2022-11-29 19:32:27',0,'成员'),(13,'成员07',NULL,'2022-11-29 19:32:36',0,'成员'),(14,'成员08',NULL,'2022-11-29 19:32:42',0,'成员'),(15,'测试03','update','2022-12-01 08:15:42',0,'测试');
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
  `view` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新闻资讯的数据储存表。\n其中id为主键，某篇文章的唯一标识；\nthumbnail为缩略图，暂时不启用，可能后期加；\nindex为新闻资讯的类别：1 = 游戏新闻；2 = 游戏公告。\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news_article`
--

LOCK TABLES `news_article` WRITE;
/*!40000 ALTER TABLE `news_article` DISABLE KEYS */;
INSERT INTO `news_article` VALUES (1,1,'游戏新闻测试 editor','# 游戏新闻测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```\n\n* 图片测试\n![04.Evangelion 3.01.01 14.jpg](http://localhost:8081/api/newsArticles/download/2a1bd0bed3ae53b177b2962880c1fe11.jpg)\n![04.Evangelion 3.01.01 16.jpg](http://localhost:8081/api/newsArticles/download/887d0ec7e9f13bc46a4b9872f2541fdd.jpg)\n![04.Evangelion 3.01.01 17.jpg](http://localhost:8081/api/newsArticles/download/4fbaf9f6f6667b375800b7cf72ec9b84.jpg)\n\n![ROG X EVANGELION_3_2.png](http://localhost:8081/api/newsArticles/download/7db32a41090dbefbe6e58c4947017123.png)\n\n![ROG X EVANGELION.png](http://localhost:8080/api/newsArticles/download/6b3107b11c45f1d86a962c24d1c49078.png)','2022-11-29 09:17:02',NULL,0),(2,2,'游戏公告测试','# 游戏新闻测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```\n* 图片测试\n![04.Evangelion 3.01.01 10.jpg](http://localhost:8081/api/newsArticles/download/bcc6ffcbefeeaf728277b6880a0ecb9c.jpg)![04.Evangelion 3.01.01 11.jpg](http://localhost:8081/api/newsArticles/download/4f86f6f4d53881a2bad60353b5511ded.jpg)![04.Evangelion 3.01.01 12.jpg](http://localhost:8081/api/newsArticles/download/d1a4073043c26f2686afb0877548daa4.jpg)','2022-11-29 09:18:34',NULL,0),(4,2,'游戏公告测试 添加','## 游戏公告测试','2023-01-01 14:52:35',NULL,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='论坛话题的表，类似于category表作用，这次不设置route，改用topicIndex';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (1,'测试话题1',NULL),(2,'测试话题2',NULL);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
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
  `view` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='跟news_article一样，index键需要跟catergory表进行联查';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_article`
--

LOCK TABLES `tutorial_article` WRITE;
/*!40000 ALTER TABLE `tutorial_article` DISABLE KEYS */;
INSERT INTO `tutorial_article` VALUES (1,1,'入门测试','# 入门测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```','2022-11-29 09:20:29',NULL,0),(2,2,'进阶测试','# 进阶测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```','2022-11-29 09:20:52',NULL,0),(3,3,'联机测试','# 联机测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```','2022-11-29 09:21:19',NULL,0),(4,4,'守则测试','# 守则测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```','2022-11-29 09:21:33',NULL,0),(5,5,'分类测试01','# 分类测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```\n![wallpaper 38.jpg](http://172.16.10.210:8081/newsArticles/download/0021a1a16636674c5fc31f86631be369.jpg)','2022-11-29 09:21:58',NULL,0),(6,5,'分类测试02','# 分类测试\n* 文章测试\n```cpp\nint main()\n{\n	cout << \"hello\" << endl;\n	return 0;\n}\n```','2022-11-29 09:22:06',NULL,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'testuser01','e10adc3949ba59abbe56e057f20f883e','测试用户01','uer account for test','male','2002-05-22','2022-11-29 09:04:29',1),(2,'testuser02','e10adc3949ba59abbe56e057f20f883e','测试用户','user account for test','female','2002-05-22','2022-11-29 09:05:43',1),(3,'testuser03','e10adc3949ba59abbe56e057f20f883e','testuser03',NULL,'male',NULL,'2022-11-29 19:26:55',1),(4,'testuser04','e10adc3949ba59abbe56e057f20f883e','testuser04',NULL,'male',NULL,'2022-11-29 19:27:10',1),(5,'testuser05','e10adc3949ba59abbe56e057f20f883e','testuser05',NULL,'male',NULL,'2022-11-29 19:27:28',1),(6,'testuser06','e10adc3949ba59abbe56e057f20f883e','testuser06',NULL,'male',NULL,'2022-11-29 19:27:40',1),(7,'testuser07','e10adc3949ba59abbe56e057f20f883e','testuser07',NULL,'female',NULL,'2022-11-29 19:27:52',1),(8,'testuser08','e10adc3949ba59abbe56e057f20f883e','testuser08',NULL,'female',NULL,'2022-11-29 19:28:06',1),(9,'testuser09','e10adc3949ba59abbe56e057f20f883e','testuser09',NULL,'female',NULL,'2022-11-29 19:28:20',1),(10,'testuser10','e10adc3949ba59abbe56e057f20f883e','testuser10',NULL,'female',NULL,'2022-11-29 19:28:31',1),(11,'testuser11','e10adc3949ba59abbe56e057f20f883e','testuser11',NULL,'female',NULL,'2022-11-29 19:28:45',1),(12,'testuser19','e10adc3949ba59abbe56e057f20f883e','testuser12','update','male',NULL,'2022-11-29 19:30:42',1),(13,'testuser20','e10adc3949ba59abbe56e057f20f883e','testuser20',NULL,'male',NULL,'2022-11-30 18:23:54',1),(14,'testuser100','e10adc3949ba59abbe56e057f20f883e','frank111',NULL,NULL,NULL,'2022-12-29 11:15:47',1);
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

-- Dump completed on 2023-01-01 20:17:50
