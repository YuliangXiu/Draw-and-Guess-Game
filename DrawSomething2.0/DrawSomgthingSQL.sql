# MySQL-Front 5.1  (Build 4.13)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 127.0.0.1    Database: game_information
# ------------------------------------------------------
# Server version 5.1.62-community

#
# Source for table game_questions
#

CREATE TABLE `game_questions` (
  `questionOne` varchar(255) DEFAULT NULL,
  `questionTwo` varchar(255) DEFAULT NULL,
  `questionThree` varchar(255) DEFAULT NULL,
  `questionForth` varchar(255) DEFAULT NULL,
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gb2312;

#
# Dumping data for table game_questions
#

LOCK TABLES `game_questions` WRITE;
/*!40000 ALTER TABLE `game_questions` DISABLE KEYS */;
INSERT INTO `game_questions` VALUES ('Æ»¹û','·çóÝ','ÐÜÃ¨','»¨¶ä',1);
INSERT INTO `game_questions` VALUES ('ÆÏÌÑ','ÊÖ»ú','ÍÃ×Ó',' °×²Ë',2);
INSERT INTO `game_questions` VALUES ('Î÷¹Ï','Æû³µ','ÂÜ²·',' ÁÔ±ª',3);
INSERT INTO `game_questions` VALUES ('éÙ×Ó','·É»ú','Ð¡¹·',' ¶ú¶ä',4);
INSERT INTO `game_questions` VALUES ('ÀóÖ¦','³µÂÖ','¶¹³æ',' ÑÛ¾µ',5);
INSERT INTO `game_questions` VALUES ('²¤ÂÜ','Ä¦ÍÐ','´óÏó',' °Ë½ä',6);
/*!40000 ALTER TABLE `game_questions` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
