CREATE TABLE IF NOT EXISTS `users`  (
       id int(11) AUTO_INCREMENT PRIMARY KEY,
       email varchar(255) NOT NULL UNIQUE,
       account_name varchar(255) CHARSET utf8mb4 NOT NULL UNIQUE,
       password varchar(255) NOT NULL,
       created_at DATETIME NOT NULL,
       updated_at DATETIME NOT NULL
) DEFAULT CHARSET = utf8 ENGINE = InnoDB;
