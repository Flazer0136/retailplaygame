use `retailplaygame`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;


CREATE TABLE `users` (
  `userID` BIGINT NOT NULL,         -- Changed from long(3) to BIGINT for larger ID values
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,  -- Increased length to 255 to accommodate hashed passwords
  `enabled` TINYINT(1) NOT NULL,     -- TINYINT(1) is used to store boolean values (0 or 1)
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `users`
--

INSERT INTO `users` (`userID`, `username`, `password`, `enabled`)
VALUES
(1, 'customer', '{noop}test123', 1),
(2, 'owner', '{noop}test123', 1),
(3, 'admin', '{noop}test123', 1);

--
-- Table structure for table `authorities`
--

CREATE TABLE `authorities` (
  `userID` BIGINT NOT NULL,  -- Match the data type with the 'users' table
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`userID`, `authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `authorities`
--

INSERT INTO `authorities` 
VALUES 
(1, 'ROLE_CUSTOMER'),
(2, 'ROLE_CUSTOMER'),
(2, 'ROLE_OWNER'),
(3, 'ROLE_CUSTOMER'),
(3, 'ROLE_OWNER'),
(3, 'ROLE_ADMIN');
