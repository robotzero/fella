CREATE TABLE `period` (
                                        `idperiod` int(10) unsigned NOT NULL COMMENT '',
                                        `iduser` int(10) unsigned NOT NULL COMMENT '',
                                        PRIMARY KEY (`idperiod`,`iduser`)
);

INSERT INTO `period` VALUES (1, 3);

