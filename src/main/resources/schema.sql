CREATE TABLE `monitor_type` (
                          `id` int(10) unsigned NOT NULL COMMENT '',
                          `userId` int(10) unsigned NOT NULL COMMENT '',
                          `name` varchar (255) NOT NULL COMMENT '',
                          `created_at` timestamp NOT NULL DEFAULT NOW(),
                          PRIMARY KEY (`id`)
);

CREATE TABLE `monitor` (
                                `id` int(10) unsigned NOT NULL COMMENT '',
                                `userId` int(10) unsigned NOT NULL COMMENT '',
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

INSERT INTO `monitor_type` (id, userId, name) VALUES (1, 1, 'period');
INSERT INTO `monitor` (id, userId, name) VALUES (1, 1, 'period');
INSERT INTO `users` (id, username) VALUES ('blah', 'blah');
