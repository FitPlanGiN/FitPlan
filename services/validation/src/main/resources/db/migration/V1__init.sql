CREATE TABLE `t_validation`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name`  varchar(64),
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);