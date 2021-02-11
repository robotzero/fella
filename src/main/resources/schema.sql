CREATE TABLE `period` (
                                        `idperiod` int(10) unsigned NOT NULL COMMENT '',
                                        `iduser` int(10) unsigned NOT NULL COMMENT '',
                                        PRIMARY KEY (`idperiod`,`iduser`)
);

INSERT INTO `period` VALUES (1, 3);

CREATE TABLE `monitor_type` (
                          `id` int(10) unsigned NOT NULL COMMENT '',
                          `name` varchar (255) NOT NULL COMMENT '',
                          PRIMARY KEY (`id`)
);

INSERT INTO `monitor_type` (id, name) VALUES (1, 'period');

