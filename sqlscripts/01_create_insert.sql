USE `retailplaygame`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userID` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `enabled` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username_UNIQUE` (`username`)
);

--if an older table already exists it won't delete so use this'
ALTER TABLE `users`
MODIFY COLUMN `password` VARCHAR(100) NOT NULL,
MODIFY COLUMN `enabled` TINYINT NOT NULL DEFAULT 1,
ADD COLUMN IF NOT EXISTS `email` VARCHAR(100),  -- Optional: add if you need email
DROP INDEX IF EXISTS `username_UNIQUE`,  -- Only if the index name is different
ADD UNIQUE KEY `username_UNIQUE` (`username`),
ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Inserting data for table `users`
--

INSERT INTO `users` (`username`, `password`, `enabled`)
VALUES
('customer', '{noop}test123', 1),
('owner', '{noop}test123', 1),
('admin', '{noop}test123', 1);

--
-- Table structure for table `authorities`
--

CREATE TABLE `authorities` (
  `authorityID` bigint NOT NULL AUTO_INCREMENT,
  `userID` bigint NOT NULL,
  `authority` varchar(50) NOT NULL,
  PRIMARY KEY (`authorityID`),
  UNIQUE KEY `ix_auth_user` (`userID`,`authority`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
);

--
-- Inserting data for table `authorities`
--

INSERT INTO `authorities` (`userID`, `authority`)
VALUES 
(1, 'ROLE_CUSTOMER'),
(2, 'ROLE_CUSTOMER'),
(2, 'ROLE_OWNER'),
(3, 'ROLE_CUSTOMER'),
(3, 'ROLE_OWNER'),
(3, 'ROLE_ADMIN');