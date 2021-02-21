CREATE TABLE `field_types` (
                               `id` int unsigned AUTO_INCREMENT NOT NULL,
                               `type` int unsigned NOT NULL,
                               PRIMARY KEY (`id`)
);

CREATE TABLE `field_type` (
                                `id` varchar (255) NOT NULL COMMENT '',
                                `name` varchar (255) NOT NULL COMMENT '',
                                `type` int unsigned NOT NULL COMMENT '',
                                `created_at` timestamp NOT NULL DEFAULT NOW(),
                                PRIMARY KEY (`id`),
                                FOREIGN  KEY (type)
                                    REFERENCES field_types(type)
);

CREATE TABLE `fields` (
                               `id` varchar (255) NOT NULL COMMENT '',
                               `monitorTypeId` varchar (255) NOT NULL COMMENT '',
                               `fieldTypeId` varchar (255) NOT NULL COMMENT '',
                               `created_at` timestamp NOT NULL DEFAULT NOW(),
                               PRIMARY KEY (`id`)
);

CREATE TABLE `monitor_type` (
                          `id` varchar (255) NOT NULL COMMENT '',
                          `userId` varchar (255) NOT NULL COMMENT '',
                          `name` varchar (255) NOT NULL COMMENT '',
                          `created_at` timestamp NOT NULL DEFAULT NOW(),
                          PRIMARY KEY (`id`)
);

CREATE TABLE `monitor` (
                                `id` varchar (255)  NOT NULL COMMENT '',
                                `userId` varchar (255) NOT NULL COMMENT '',
                                `name` varchar (255) NOT NULL COMMENT '',
                                `created_at` timestamp NOT NULL DEFAULT NOW(),
                                PRIMARY KEY (`id`)
);

CREATE TABLE `users` (
                        `id` varchar (255) NOT NULL COMMENT '',
                        `username` varchar (255) NOT NULL,
                        `created_at` timestamp NOT NULL DEFAULT NOW(),
                        PRIMARY KEY (`id`)
);

INSERT INTO `monitor` (id, userId, name) VALUES (1, 1, 'period');

INSERT INTO `field_types` (type) VALUES (1);
INSERT INTO `field_types` (type) VALUES (2);
INSERT INTO `field_types` (type) VALUES (3);
INSERT INTO `field_types` (type) VALUES (4);
INSERT INTO `field_types` (type) VALUES (5);

INSERT INTO `field_type` (id, name, type) VALUES ('1', 'Date', 1);
INSERT INTO `field_type` (id, name, type) VALUES ('2', 'Pain Level', 2);
INSERT INTO `field_type` (id, name, type) VALUES ('3', 'Flow Level', 3);
INSERT INTO `field_type` (id, name, type) VALUES ('4', 'Notes', 4);
INSERT INTO `field_type` (id, name, type) VALUES ('5', 'Tags', 5);
