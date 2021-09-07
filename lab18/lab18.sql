SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DELIMITER ;;

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `refId` int NOT NULL,
                           `sessionId` varchar(200) NOT NULL,
                           `stamp` date NOT NULL,
                           `comment` varchar(500) NOT NULL,
                           PRIMARY KEY (`id`),
                           KEY `refId` (`refId`),
                           CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`refId`) REFERENCES `references` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `comment` (`id`, `refId`, `sessionId`, `stamp`, `comment`) VALUES
(1,	1,	'test',	'2018-12-20',	'test123456'),
(6,	2,	'653697C5E0131EF87ED4F6A6F77583FB',	'2020-12-18',	'Yeah, that is realyy good.'),
(7,	2,	'653697C5E0131EF87ED4F6A6F77583FB',	'2020-12-18',	'Cool.'),
(8,	1,	'B5DD2A51956187F14D0CB8D59EC16463',	'2020-12-19',	'test123'),
(9,	3,	'B5DD2A51956187F14D0CB8D59EC16463',	'2020-12-19',	'test 789');

DROP TABLE IF EXISTS `references`;
CREATE TABLE `references` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `url` varchar(200) NOT NULL,
                              `description` varchar(200) NOT NULL,
                              `minus` int NOT NULL,
                              `plus` int NOT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `references` (`id`, `url`, `description`, `minus`, `plus`) VALUES
(1,	'https://hub.docker.com/_/mysql',	'Docker Official Images: MySQL',	5,	15),
(2,	'https://duckduckgo.com/',	'Search safely.',	1,	20),
(3,	'https://yandex.by/',	'yandex',	7,	18);

DROP PROCEDURE IF EXISTS `AddComment`;;
CREATE PROCEDURE `AddComment`(IN `id` int, IN `refId` int, IN `sessionId` varchar(200), IN `stamp` date, IN `comment` varchar(500))
begin
    insert into linksManager.comment(refId,sessionId,stamp,comment) values (refId,sessionId,stamp,comment);
end;;

DROP PROCEDURE IF EXISTS `AddReference`;;
CREATE PROCEDURE `AddReference`(IN `id` int, IN `url` varchar(200), IN `description` varchar(200), IN `minus` int, IN `plus` int)
begin
    insert into linksManager.references(url,description,minus,plus) values (url,description,minus,plus);
end;;

DELIMITER ;

