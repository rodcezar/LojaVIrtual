CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `amount` int NOT NULL
  
  PRIMARY KEY(`id`),
  UNIQUE(`name`)
) engine=InnoDB default charset=utf8;