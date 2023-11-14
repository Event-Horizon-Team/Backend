DROP TABLE IF EXISTS information_tbl;
CREATE TABLE information_tbl (
  id int NOT NULL AUTO_INCREMENT,
   user_name varchar(45) DEFAULT NULL,
   password varchar(45) DEFAULT NULL,
   active int DEFAULT 1,
   first_name varchar(45) DEFAULT NULL,
   last_name varchar(45) DEFAULT NULL,
   email varchar(45) DEFAULT NULL,
   gender varchar(45) DEFAULT NULL,
   pay_pal_Account varchar(45) DEFAULT NULL,
   role varchar(45) DEFAULT NULL,
   sign_in_with_email int DEFAULT 0,
  PRIMARY KEY (id)
);

CREATE TABLE `client_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `information_id` int,
  PRIMARY KEY (`id`),
  foreign key (information_id) references information_tbl (id)
);

DROP TABLE IF EXISTS `organizer_tbl`;
CREATE TABLE `organizer_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `information_id` int,
  `rate` float ,
  PRIMARY KEY (`id`),
  foreign key (information_id) references information_tbl (id)
);

CREATE TABLE `sponsor_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `information_id` int,
  PRIMARY KEY (`id`),
  foreign key (information_id) references information_tbl (id)
);

CREATE TABLE `moderator_tbl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `information_id` int,
  PRIMARY KEY (`id`),
  foreign key (information_id) references information_tbl (id)
);
