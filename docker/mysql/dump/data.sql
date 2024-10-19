-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: mybodyfunding
-- ------------------------------------------------------
-- Server version	8.0.27
CREATE DATABASE  IF NOT EXISTS `mybodyfunding` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mybodyfunding`;

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
-- Table structure for table `actu`
--

DROP TABLE IF EXISTS `actu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actu` (
  `idactu` int NOT NULL,
  `titre` varchar(255) NOT NULL,
  `contenu` varchar(255) NOT NULL,
  `dateAjout` date NOT NULL,
  `nomAuteur` varchar(255) NOT NULL,
  `prenomAuteur` varchar(255) NOT NULL,
  `langue` varchar(255) DEFAULT NULL,
  `projet_idprojet` int NOT NULL,
  `photos` varchar(255) DEFAULT NULL,
  `videos` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idactu`),
  KEY `fk_actu_projet1_idx` (`projet_idprojet`),
  CONSTRAINT `fk_actu_projet1` FOREIGN KEY (`projet_idprojet`) REFERENCES `projet` (`idprojet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actu`
--

LOCK TABLES `actu` WRITE;
/*!40000 ALTER TABLE `actu` DISABLE KEYS */;
/*!40000 ALTER TABLE `actu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adresse`
--

DROP TABLE IF EXISTS `adresse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adresse` (
  `idadresse` int NOT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `rue` varchar(255) DEFAULT NULL,
  `ville` varchar(255) NOT NULL,
  `code_postal` varchar(255) DEFAULT NULL,
  `pays` varchar(255) NOT NULL,
  `user_idUser` int NOT NULL,
  PRIMARY KEY (`idadresse`,`user_idUser`),
  KEY `fk_adresse_user_idx` (`user_idUser`),
  CONSTRAINT `fk_adresse_user` FOREIGN KEY (`user_idUser`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adresse`
--

LOCK TABLES `adresse` WRITE;
/*!40000 ALTER TABLE `adresse` DISABLE KEYS */;
/*!40000 ALTER TABLE `adresse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `app_user_role` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (1,'USER','maroua@gmail.com',_binary '',_binary '\0','Benkhedir','$2a$10$ZiZa8gG5CAOxnO7MZ8g64.ND3X959SVCUQ9xn4PrVJv2MQ/cCIy9q','Maroua'),(2,'USER','fatma@gmail.com',NULL,NULL,'hadj hocine','$2a$10$WCbAFJ3d62niVIPya8OWgepD4w1mxOpbQDIsAe8xoj.Y59v7ZdnUu','fatma'),(3,'USER','manel@gmail.com',NULL,NULL,'benkhedir','$2a$10$F3qdAHlNQscdb0UXAsc/XO7YGySaTAslLgm5gCnIoc4Pet9mwCZwG','manel'),(4,'USER','mahfoud@gmail.com',NULL,NULL,'gamraoui','$2a$10$Gxf8IJrM59oK0GYtZlBCEeajdiQ5jm6.UKoQC6IER5i3CX2WoZkXO','mahfoud'),(5,'USER','mama@gmail.com',NULL,NULL,'benkhedir','$2a$10$QisaTPCU3jbWEFxn/PeXq.aa9rc7vbQyA7yXqxji5qi0.LpV336mG','mama'),(6,'USER','papa@gmail.com',NULL,NULL,'benkhedir','$2a$10$sHAYKAKa9ICXGUK7WeU9Heo39PhtqKkMBzlVHANPjkvPnljp9K7wi','papa'),(7,'USER','dahmen@gmail.com',NULL,NULL,'benkhedir','$2a$10$J5PmC6WkRsIq7dsktwE9UeR5E9MbVGIbTZ11OUXJVtYqlr8ScvLpW','dahmen'),(8,'USER','bissan@gmail.com',NULL,NULL,'bissan','$2a$10$W2MCofiOGzc1A6L2Hicm9.5xaFWKZMyIajRnce9hSVCA2A25ZFXgW','bissan'),(9,'USER','bissan2@gmail.com',NULL,NULL,'gamraoui','$2a$10$P8R1pUHmHwVDMAo2Ll6VXOQU5HisLnyMvvvMsDmUkiq9M/C8DQV0.','bissan'),(10,'USER','bissan23@gmail.com',NULL,NULL,'gamraoui','$2a$10$td7kEADbnWBWgXHuTjrvbOsZ2ks.tRRVbTcIWK2V/LyRhkak5qsfu','bissan'),(11,'USER','bissan234@gmail.com',NULL,NULL,'gamraoui','$2a$10$XlQ2/RU97fXSzt/tbdEmLeoxof5K0U2UCdvyixiGkXQbFCE5EDLTy','bissan'),(12,'USER','hicham@gmail.com',_binary '\0',_binary '\0','gamraoui','$2a$10$uPr/1Av3byfwK.hhUtF6HeFCrTUZ9/XNahOYHaysn/YoH/GBsjh4C','hicham'),(13,'USER','hamid@gmail.com',_binary '\0',_binary '\0','gamraoui','$2a$10$faz3uxwPr0WYjA9yBNZWA.TRkYb8mHQTlvi/xlifFbtKMhW41hsAC','hamid'),(14,'USER','hamid2@gmail.com',_binary '\0',_binary '\0','gamraoui','$2a$10$9mJI.WKRRFZ4tFTrIMtfUuP8UmrHJE8ycAvMO2.4tntb7KVvw7aJq','hamid'),(15,'USER','djamila@gmail.com',_binary '',_binary '\0','gamraoui','$2a$10$u2x.7.YRsJs4NwLUelAo5ODNRzsIovGJ0H/WGV6Vl5AljMeRXKntm','djamila'),(27,'USER','benkhedir.maroua@gmail.com',_binary '\0',_binary '\0','gamraoui','$2a$10$2e79TowP4Sje.C/AbFpu5Oz1WwAg/U2L0JDBjyYUG5p0vfi1L6i.2','maroua');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartepaiement`
--

DROP TABLE IF EXISTS `cartepaiement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartepaiement` (
  `idcartePaiement` int NOT NULL,
  `nomProprietaire` varchar(255) NOT NULL,
  `PrenomProprietaire` varchar(255) NOT NULL,
  `numero` tinyblob NOT NULL,
  `dateFinValidite` date NOT NULL,
  `cryptogramme` tinyblob NOT NULL,
  `user_idUser` int NOT NULL,
  PRIMARY KEY (`idcartePaiement`,`user_idUser`),
  KEY `fk_cartePaiement_user1_idx` (`user_idUser`),
  CONSTRAINT `fk_cartePaiement_user1` FOREIGN KEY (`user_idUser`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartepaiement`
--

LOCK TABLES `cartepaiement` WRITE;
/*!40000 ALTER TABLE `cartepaiement` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartepaiement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorie` (
  `idcategorie` int NOT NULL,
  `nom` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idcategorie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorie`
--

LOCK TABLES `categorie` WRITE;
/*!40000 ALTER TABLE `categorie` DISABLE KEYS */;
/*!40000 ALTER TABLE `categorie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commentaireactu`
--

DROP TABLE IF EXISTS `commentaireactu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commentaireactu` (
  `idcommentaireActu` int NOT NULL,
  `text` varchar(255) NOT NULL,
  `user_idUser` int NOT NULL,
  `dateAjout` date NOT NULL,
  `actu_idactu` int NOT NULL,
  PRIMARY KEY (`idcommentaireActu`),
  KEY `fk_commentaire_user1_idx` (`user_idUser`),
  KEY `fk_commentaireActu_actu1_idx` (`actu_idactu`),
  CONSTRAINT `fk_commentaire_user10` FOREIGN KEY (`user_idUser`) REFERENCES `user` (`idUser`),
  CONSTRAINT `fk_commentaireActu_actu1` FOREIGN KEY (`actu_idactu`) REFERENCES `actu` (`idactu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commentaireactu`
--

LOCK TABLES `commentaireactu` WRITE;
/*!40000 ALTER TABLE `commentaireactu` DISABLE KEYS */;
/*!40000 ALTER TABLE `commentaireactu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commentaireprojet`
--

DROP TABLE IF EXISTS `commentaireprojet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commentaireprojet` (
  `idcommentaire` int NOT NULL,
  `text` varchar(255) NOT NULL,
  `projet_idprojet` int NOT NULL,
  `user_idUser` int NOT NULL,
  `dateAjout` date NOT NULL,
  PRIMARY KEY (`idcommentaire`),
  KEY `fk_commentaire_projet1_idx` (`projet_idprojet`),
  KEY `fk_commentaire_user1_idx` (`user_idUser`),
  CONSTRAINT `fk_commentaire_projet1` FOREIGN KEY (`projet_idprojet`) REFERENCES `projet` (`idprojet`),
  CONSTRAINT `fk_commentaire_user1` FOREIGN KEY (`user_idUser`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commentaireprojet`
--

LOCK TABLES `commentaireprojet` WRITE;
/*!40000 ALTER TABLE `commentaireprojet` DISABLE KEYS */;
/*!40000 ALTER TABLE `commentaireprojet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `confirmation_token`
--

DROP TABLE IF EXISTS `confirmation_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `confirmation_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `confirmed_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `expires_at` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `app_user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo9fl25wqyh7w7mnfkdqen1rcm` (`app_user_id`),
  CONSTRAINT `FKo9fl25wqyh7w7mnfkdqen1rcm` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmation_token`
--

LOCK TABLES `confirmation_token` WRITE;
/*!40000 ALTER TABLE `confirmation_token` DISABLE KEYS */;
INSERT INTO `confirmation_token` VALUES (1,NULL,'2023-05-02 12:01:42.945889','2023-05-02 12:16:42.946887','bea29fe3-9c16-45e8-a48e-e59132264216',14),(2,'2023-05-02 12:36:06.254933','2023-05-02 12:33:00.135767','2023-05-02 12:48:00.135767','d861fb65-579c-48c1-86a5-b3184114df1b',15),(14,NULL,'2023-05-03 14:57:06.656403','2023-05-03 15:12:06.658404','6be8e76e-08b3-4b1d-9308-749660e35937',27);
/*!40000 ALTER TABLE `confirmation_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faq`
--

DROP TABLE IF EXISTS `faq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faq` (
  `idfaq` int NOT NULL,
  `question` varchar(255) NOT NULL,
  `reponse` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `projet_idprojet` int NOT NULL,
  PRIMARY KEY (`idfaq`),
  UNIQUE KEY `question_UNIQUE` (`question`),
  KEY `fk_faq_projet1_idx` (`projet_idprojet`),
  CONSTRAINT `fk_faq_projet1` FOREIGN KEY (`projet_idprojet`) REFERENCES `projet` (`idprojet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faq`
--

LOCK TABLES `faq` WRITE;
/*!40000 ALTER TABLE `faq` DISABLE KEYS */;
/*!40000 ALTER TABLE `faq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `investissemet`
--

DROP TABLE IF EXISTS `investissemet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `investissemet` (
  `projet_idprojet` int NOT NULL,
  `user_idUser` int NOT NULL,
  `montant` float NOT NULL,
  `typeInvestissement` varchar(255) NOT NULL,
  `dateInvestissement` date NOT NULL,
  PRIMARY KEY (`projet_idprojet`,`user_idUser`),
  KEY `fk_projet_has_user_user1_idx` (`user_idUser`),
  KEY `fk_projet_has_user_projet1_idx` (`projet_idprojet`),
  CONSTRAINT `fk_projet_has_user_projet1` FOREIGN KEY (`projet_idprojet`) REFERENCES `projet` (`idprojet`),
  CONSTRAINT `fk_projet_has_user_user1` FOREIGN KEY (`user_idUser`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `investissemet`
--

LOCK TABLES `investissemet` WRITE;
/*!40000 ALTER TABLE `investissemet` DISABLE KEYS */;
/*!40000 ALTER TABLE `investissemet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like`
--

DROP TABLE IF EXISTS `like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like` (
  `user_idUser` int NOT NULL,
  `projet_idprojet` int NOT NULL,
  PRIMARY KEY (`user_idUser`,`projet_idprojet`),
  KEY `fk_user_has_projet_projet1_idx` (`projet_idprojet`),
  KEY `fk_user_has_projet_user1_idx` (`user_idUser`),
  CONSTRAINT `fk_user_has_projet_projet1` FOREIGN KEY (`projet_idprojet`) REFERENCES `projet` (`idprojet`),
  CONSTRAINT `fk_user_has_projet_user1` FOREIGN KEY (`user_idUser`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like`
--

LOCK TABLES `like` WRITE;
/*!40000 ALTER TABLE `like` DISABLE KEYS */;
/*!40000 ALTER TABLE `like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likeactu`
--

DROP TABLE IF EXISTS `likeactu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likeactu` (
  `user_idUser` int NOT NULL,
  `actu_idactu` int NOT NULL,
  PRIMARY KEY (`user_idUser`,`actu_idactu`),
  KEY `fk_user_has_actu_actu1_idx` (`actu_idactu`),
  KEY `fk_user_has_actu_user1_idx` (`user_idUser`),
  CONSTRAINT `fk_user_has_actu_actu1` FOREIGN KEY (`actu_idactu`) REFERENCES `actu` (`idactu`),
  CONSTRAINT `fk_user_has_actu_user1` FOREIGN KEY (`user_idUser`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likeactu`
--

LOCK TABLES `likeactu` WRITE;
/*!40000 ALTER TABLE `likeactu` DISABLE KEYS */;
/*!40000 ALTER TABLE `likeactu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `idmessage` int NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `objet` varchar(255) DEFAULT NULL,
  `date_envoi` date DEFAULT NULL,
  `user_idSource` int NOT NULL,
  `user_idDestination` int NOT NULL,
  PRIMARY KEY (`idmessage`),
  KEY `fk_message_user1_idx` (`user_idSource`),
  KEY `fk_message_user2_idx` (`user_idDestination`),
  CONSTRAINT `fk_message_user1` FOREIGN KEY (`user_idSource`) REFERENCES `user` (`idUser`),
  CONSTRAINT `fk_message_user2` FOREIGN KEY (`user_idDestination`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projet`
--

DROP TABLE IF EXISTS `projet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projet` (
  `idprojet` int NOT NULL,
  `titre` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `date_ajout` date NOT NULL,
  `visibilite` bit(1) NOT NULL,
  `investissement` float NOT NULL DEFAULT '0',
  `objectif` float NOT NULL,
  `dateFinancement` date NOT NULL,
  `photos` varchar(255) NOT NULL,
  `videos` varchar(255) NOT NULL,
  `isActif` bit(1) NOT NULL,
  `pays` varchar(255) NOT NULL,
  `ville` varchar(255) NOT NULL,
  `categorie_idcategorie` int NOT NULL,
  PRIMARY KEY (`idprojet`),
  KEY `fk_projet_categorie1_idx` (`categorie_idcategorie`),
  CONSTRAINT `fk_projet_categorie1` FOREIGN KEY (`categorie_idcategorie`) REFERENCES `categorie` (`idcategorie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projet`
--

LOCK TABLES `projet` WRITE;
/*!40000 ALTER TABLE `projet` DISABLE KEYS */;
/*!40000 ALTER TABLE `projet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `risque`
--

DROP TABLE IF EXISTS `risque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risque` (
  `idrisque` int NOT NULL,
  `risque` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `projet_idprojet` int NOT NULL,
  PRIMARY KEY (`idrisque`),
  KEY `fk_risque_projet1_idx` (`projet_idprojet`),
  CONSTRAINT `fk_risque_projet1` FOREIGN KEY (`projet_idprojet`) REFERENCES `projet` (`idprojet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `risque`
--

LOCK TABLES `risque` WRITE;
/*!40000 ALTER TABLE `risque` DISABLE KEYS */;
/*!40000 ALTER TABLE `risque` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `idUser` int NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `date_naissance` date NOT NULL,
  `isActif` bit(1) NOT NULL,
  `password` tinyblob NOT NULL,
  `profil` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telephone` varchar(255) NOT NULL,
  `sexe` varchar(45) NOT NULL,
  `typeAbonnement` varchar(255) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `telephone_UNIQUE` (`telephone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-07 21:42:48
