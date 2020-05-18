CREATE TABLE IF NOT EXISTS `produto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `amount` int NOT null,
  
  PRIMARY KEY(`id`),
  UNIQUE(`name`)
) engine=InnoDB default charset=utf8;