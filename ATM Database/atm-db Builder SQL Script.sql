DROP TABLE operators;
DROP TABLE customers;
DROP TABLE atm_access_status;
DROP TABLE account_recovery_requests;
DROP TABLE accounts;
DROP TABLE deposits;
DROP TABLE transactions;
DROP TABLE transfers;
DROP TABLE withdrawals;

CREATE TABLE `operators` (
  `OperatorID` int NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(25) NOT NULL,
  `LastName` varchar(25) NOT NULL,
  `Password` varchar(25) NOT NULL,
  PRIMARY KEY (`OperatorID`),
  UNIQUE KEY `OperatorID_UNIQUE` (`OperatorID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customers` (
  `CustomerID` int NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(25) NOT NULL,
  `LastName` varchar(25) NOT NULL,
  `DOB` date NOT NULL,
  `Address` varchar(45) NOT NULL,
  `Phone` varchar(16) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `PIN` int NOT NULL,
  PRIMARY KEY (`CustomerID`),
  UNIQUE KEY `CustomerID_UNIQUE` (`CustomerID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `atm_access_status` (
  `StatusID` int NOT NULL AUTO_INCREMENT,
  `CustomerID` int NOT NULL,
  `Locked` tinyint NOT NULL,
  PRIMARY KEY (`StatusID`),
  UNIQUE KEY `StatusID_UNIQUE` (`StatusID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `account_recovery_requests` (
  `RequestID` int NOT NULL AUTO_INCREMENT,
  `AccountID` int NOT NULL,
  PRIMARY KEY (`RequestID`),
  UNIQUE KEY `RequestID_UNIQUE` (`RequestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `accounts` (
  `AccountID` int NOT NULL AUTO_INCREMENT,
  `CustomerID` int NOT NULL,
  `CreationDate` datetime NOT NULL,
  `AccountType` varchar(12) NOT NULL DEFAULT 'Checking',
  `CardID` int NOT NULL,
  `Balance` float NOT NULL DEFAULT '0',
  `Frozen` tinyint NOT NULL DEFAULT '0',
  `AccountNickname` varchar(25) NOT NULL,
  PRIMARY KEY (`AccountID`),
  UNIQUE KEY `AccountID_UNIQUE` (`AccountID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `deposits` (
  `DepositID` int NOT NULL AUTO_INCREMENT,
  `TransactionID` int NOT NULL,
  `Amount` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`DepositID`),
  UNIQUE KEY `DepositID_UNIQUE` (`DepositID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `transactions` (
  `TransactionID` int NOT NULL AUTO_INCREMENT,
  `AccountID` int NOT NULL,
  `Date` date NOT NULL,
  `Type` varchar(12) NOT NULL DEFAULT 'Withdrawal',
  PRIMARY KEY (`TransactionID`),
  UNIQUE KEY `TransactionID_UNIQUE` (`TransactionID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `transfers` (
  `TransferID` int NOT NULL AUTO_INCREMENT,
  `TransactionID` int NOT NULL,
  `Amount` float NOT NULL DEFAULT '0',
  `DestinationAccount` int NOT NULL,
  PRIMARY KEY (`TransferID`),
  UNIQUE KEY `TransferID_UNIQUE` (`TransferID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `withdrawals` (
  `WithdrawalID` int NOT NULL AUTO_INCREMENT,
  `TransactionID` int NOT NULL,
  `Amount` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`WithdrawalID`),
  UNIQUE KEY `WithdrawalID_UNIQUE` (`WithdrawalID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci