CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL UNIQUE,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `notebook` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `title` varchar(150) NOT NULL,
  `description` varchar(300) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `note` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `notebook_id` INT NOT NULL,
  `title` varchar(150) NOT NULL,
  `body` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `note_mark` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `note_id` INT NOT NULL,
  `mark_id` INT NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `mark` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE `notebook` ADD CONSTRAINT `notebook_fk0` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);

ALTER TABLE `note` ADD CONSTRAINT `note_fk0` FOREIGN KEY (`notebook_id`) REFERENCES `notebook`(`id`);

ALTER TABLE `note_mark` ADD CONSTRAINT `note_mark_fk0` FOREIGN KEY (`note_id`) REFERENCES `note`(`id`);

ALTER TABLE `note_mark` ADD CONSTRAINT `note_mark_fk1` FOREIGN KEY (`mark_id`) REFERENCES `mark`(`id`);
