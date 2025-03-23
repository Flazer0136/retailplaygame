USE `retailplaygame`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userID` BIGINT NOT NULL AUTO_INCREMENT,  -- Added AUTO_INCREMENT
  `username` VARCHAR(50) NOT NULL UNIQUE,  -- Added UNIQUE constraint
  `password` VARCHAR(255) NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `userID` BIGINT NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`userID`, `authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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