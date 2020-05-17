CREATE TABLE IF NOT EXISTS `Produto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `amount` int NOT NULL
  
  PRIMARY KEY(`id`),
  UNIQUE(`name`)
) engine=InnoDB default charset=utf8;