-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 06, 2024 at 10:15 PM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `se_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `adminID` int NOT NULL,
  `firstName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lastName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registerDate` date NOT NULL,
  `salary` double NOT NULL,
  PRIMARY KEY (`adminID`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`adminID`, `firstName`, `lastName`, `email`, `password`, `address`, `registerDate`, `salary`) VALUES
(1, 'Mohamed', 'Sameh', 'Mohamed@gmail.com', '12345', 'New Cairo', '2024-05-07', 200);

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
CREATE TABLE IF NOT EXISTS `appointment` (
  `appointmentID` int NOT NULL,
  `dateTime` date NOT NULL,
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `c_id` int NOT NULL,
  `Tech_ID` int NOT NULL,
  PRIMARY KEY (`appointmentID`),
  KEY `fk_app_cid` (`c_id`),
  KEY `fk_app_tid` (`Tech_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`appointmentID`, `dateTime`, `address`, `c_id`, `Tech_ID`) VALUES
(1, '2024-06-10', 'Maadi', 1, 1),
(2, '2024-07-04', 'Zamalek', 2, 3),
(3, '2024-08-09', 'Zamalek', 2, 3);

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
CREATE TABLE IF NOT EXISTS `bill` (
  `Bill_ID` int NOT NULL,
  `amount` double NOT NULL,
  `issueDate` date NOT NULL,
  `status` enum('Pending','Paid') NOT NULL DEFAULT 'Pending',
  `ElectricMeterNo` int NOT NULL,
  `consumedWatt` double NOT NULL,
  `wattPrice` double NOT NULL,
  `client_ID` int NOT NULL,
  `deadline` date NOT NULL,
  `payDate` date DEFAULT NULL,
  `pastDue` tinyint(1) NOT NULL,
  PRIMARY KEY (`Bill_ID`),
  KEY `fk_bill_cid` (`client_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`Bill_ID`, `amount`, `issueDate`, `status`, `ElectricMeterNo`, `consumedWatt`, `wattPrice`, `client_ID`, `deadline`, `payDate`, `pastDue`) VALUES
(1, 1000, '2024-05-07', 'Paid', 1, 100, 10, 1, '2024-06-07', '2024-05-07', 0),
(2, 2000, '2024-05-07', 'Paid', 1, 200, 10, 1, '2024-06-07', '2024-05-07', 0),
(3, 500, '2024-05-07', 'Paid', 1, 50, 10, 1, '2024-06-07', '2024-05-07', 0),
(4, 700, '2024-05-07', 'Paid', 1, 70, 10, 1, '2024-06-07', '2024-05-07', 0),
(5, 1200, '2024-05-07', 'Paid', 1, 120, 10, 1, '2024-06-07', '2024-05-07', 0),
(6, 1300, '2024-05-07', 'Paid', 1, 130, 10, 1, '2024-06-07', '2024-05-07', 0),
(7, 1200, '2024-05-07', 'Pending', 1, 150, 10, 1, '2024-06-07', NULL, 0),
(8, 1100, '2024-05-07', 'Pending', 1, 110, 10, 2, '2024-06-07', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `clientID` int NOT NULL,
  `firstName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lastName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registerDate` date NOT NULL,
  `billOnTimeCounter` int NOT NULL,
  `billPastTimeCounter` int NOT NULL,
  `isInWhitelist` tinyint(1) NOT NULL,
  `isInBlacklist` tinyint(1) NOT NULL,
  `subsEnded` tinyint(1) NOT NULL,
  PRIMARY KEY (`clientID`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`clientID`, `firstName`, `lastName`, `email`, `password`, `address`, `registerDate`, `billOnTimeCounter`, `billPastTimeCounter`, `isInWhitelist`, `isInBlacklist`, `subsEnded`) VALUES
(1, 'Sameer', 'Mohamed', 'Sameer@gmail.com', '12345', 'Maadi', '2024-05-07', 6, 0, 1, 0, 0),
(2, 'Eyad', 'Tamer', 'Eyad@gmail.com', '12345', 'Zamalek', '2024-05-07', 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
CREATE TABLE IF NOT EXISTS `complaint` (
  `complaintID` int NOT NULL,
  `dateTime` date NOT NULL,
  `message` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `C_ID` int NOT NULL,
  PRIMARY KEY (`complaintID`),
  KEY `fk_complaint_cid` (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `complaint`
--

INSERT INTO `complaint` (`complaintID`, `dateTime`, `message`, `C_ID`) VALUES
(1, '2024-05-07', 'Usage is a bit higher than usual!', 1);

-- --------------------------------------------------------

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
CREATE TABLE IF NOT EXISTS `discount` (
  `discountID` int NOT NULL,
  `percentage` double NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `clientID` int NOT NULL,
  `promoCode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `activated` tinyint(1) NOT NULL,
  PRIMARY KEY (`discountID`),
  UNIQUE KEY `promoCode` (`promoCode`),
  KEY `fk_discount_cid` (`clientID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `discount`
--

INSERT INTO `discount` (`discountID`, `percentage`, `startDate`, `endDate`, `clientID`, `promoCode`, `activated`) VALUES
(1, 10, '2024-05-07', '2024-06-07', 1, 'S9IHH', 1),
(2, 15, '2024-05-07', '2024-06-07', 1, '9HTM3', 1);

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
CREATE TABLE IF NOT EXISTS `feedback` (
  `feedback_ID` int NOT NULL,
  `dateTime` date NOT NULL,
  `message` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `rate` double NOT NULL,
  `CID` int NOT NULL,
  PRIMARY KEY (`feedback_ID`),
  KEY `fk_feedback_cid` (`CID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`feedback_ID`, `dateTime`, `message`, `rate`, `CID`) VALUES
(1, '2024-05-07', '', 5, 1),
(2, '2024-05-07', 'Nice app', 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
CREATE TABLE IF NOT EXISTS `manager` (
  `managerID` int NOT NULL,
  `firstName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lastName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registerDate` date NOT NULL,
  `salary` double NOT NULL,
  PRIMARY KEY (`managerID`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `manager`
--

INSERT INTO `manager` (`managerID`, `firstName`, `lastName`, `email`, `password`, `address`, `registerDate`, `salary`) VALUES
(1, 'Khaled', 'Kamal', 'Khaled@gmail.com', '12345', 'Madinet Nasr', '2024-05-07', 300);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `paymentID` int NOT NULL,
  `cardNo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cardHolder` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cvv` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `amount` double NOT NULL,
  `clientID` int NOT NULL,
  `billID` int NOT NULL,
  PRIMARY KEY (`paymentID`),
  KEY `fk_payment_cid` (`clientID`),
  KEY `fk_payment_billID` (`billID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`paymentID`, `cardNo`, `cardHolder`, `cvv`, `amount`, `clientID`, `billID`) VALUES
(1, '1234', 'Sameer', '123', 1000, 1, 1),
(2, '1234', 'Sameer', '123', 2000, 1, 2),
(3, '1234', 'Sameer', '123', 1000, 1, 1),
(4, '1234', 'Sameer', '123', 2000, 1, 2),
(5, '1234', 'Sameer', '123', 500, 1, 3),
(6, '1234', 'Sameer', '123', 700, 1, 4),
(7, '1234', 'Sameer', '123', 1200, 1, 5),
(8, '1234', 'Sameer', '123', 1300, 1, 6);

-- --------------------------------------------------------

--
-- Table structure for table `technician`
--

DROP TABLE IF EXISTS `technician`;
CREATE TABLE IF NOT EXISTS `technician` (
  `technicianID` int NOT NULL,
  `firstName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lastName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registerDate` date NOT NULL,
  `salary` double NOT NULL,
  PRIMARY KEY (`technicianID`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `technician`
--

INSERT INTO `technician` (`technicianID`, `firstName`, `lastName`, `email`, `password`, `address`, `registerDate`, `salary`) VALUES
(1, 'Ahmed', 'Omar', 'Ahmed@gmail.com', '12345', 'Maadi', '2024-05-07', 100),
(2, 'Seif', 'Amr', 'Seif@gmail.com', '12345', 'Zamalek', '2024-05-07', 100),
(3, 'Shady', 'Karam', 'Shady@gmil.com', '12345', 'Zamalek', '2024-05-07', 100);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `fk_app_cid` FOREIGN KEY (`c_id`) REFERENCES `client` (`clientID`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_app_tid` FOREIGN KEY (`Tech_ID`) REFERENCES `technician` (`technicianID`) ON DELETE CASCADE;

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `fk_bill_cid` FOREIGN KEY (`client_ID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE;

--
-- Constraints for table `complaint`
--
ALTER TABLE `complaint`
  ADD CONSTRAINT `fk_complaint_cid` FOREIGN KEY (`C_ID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE;

--
-- Constraints for table `discount`
--
ALTER TABLE `discount`
  ADD CONSTRAINT `fk_discount_cid` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE;

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `fk_feedback_cid` FOREIGN KEY (`CID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE;

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `fk_payment_billID` FOREIGN KEY (`billID`) REFERENCES `bill` (`Bill_ID`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_payment_cid` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
