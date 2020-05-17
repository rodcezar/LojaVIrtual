CREATE TABLE IF NOT EXISTS `produto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `birth_date` date NOT null,
  `amount` int NOT null,
  
  PRIMARY KEY(`id`),
  UNIQUE(`name`, `email`, `birth_date`, `amount`)
) engine=InnoDB default charset=utf8;