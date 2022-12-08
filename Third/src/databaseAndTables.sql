/*
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.25 : Database - weathersys
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`weathersys` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `weathersys`;

/*Table structure for table `cities` */

DROP TABLE IF EXISTS `cities`;

CREATE TABLE `cities` (
  `cityid` int NOT NULL,
  `cityname` varchar(200) NOT NULL,
  `cityWei` varchar(200) NOT NULL,
  `cityJing` varchar(200) NOT NULL,
  PRIMARY KEY (`cityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `cities` */

insert  into `cities`(`cityid`,`cityname`,`cityWei`,`cityJing`) values (101010100,'北京','39.90498','116.40528'),(101020100,'上海','31.2317','121.47264'),(101230101,'福州','26.0753','119.30623');

/*Table structure for table `stores` */

DROP TABLE IF EXISTS `stores`;

CREATE TABLE `stores` (
  `cityID` int NOT NULL,
  `weatherID` int NOT NULL,
  PRIMARY KEY (`cityID`,`weatherID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `stores` */

insert  into `stores`(`cityID`,`weatherID`) values (101010100,1),(101010100,2),(101010100,3),(101020100,4),(101020100,5),(101020100,6),(101230101,7),(101230101,8),(101230101,9);

/*Table structure for table `weather` */

DROP TABLE IF EXISTS `weather`;

CREATE TABLE `weather` (
  `weatherid` int NOT NULL,
  `fxDate` date DEFAULT NULL,
  `tempMax` varchar(200) NOT NULL,
  `tempMin` varchar(200) NOT NULL,
  `textDay` text,
  PRIMARY KEY (`weatherid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `weather` */

insert  into `weather`(`weatherid`,`fxDate`,`tempMax`,`tempMin`,`textDay`) values (1,'2022-01-17','4','-8','晴'),(2,'2022-01-18','3','-7','晴'),(3,'2022-01-19','0','-8','多云'),(4,'2022-01-17','11','6','阴'),(5,'2022-01-18','12','5','晴'),(6,'2022-01-19','14','6','多云'),(7,'2022-01-17','12','11','阴'),(8,'2022-01-18','13','11','小雨'),(9,'2022-01-19','19','11','多云');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
