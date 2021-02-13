CREATE TABLE `monitor_type` (
                          `id` int(10) unsigned NOT NULL COMMENT '',
                          `userId` int(10) unsigned NOT NULL COMMENT '',
                          `name` varchar (255) NOT NULL COMMENT '',
                          PRIMARY KEY (`id`)
);

INSERT INTO `monitor_type` (id, userId, name) VALUES (1, 1, 'period');

