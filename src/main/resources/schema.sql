CREATE TABLE IF NOT EXISTS field_types (
                               id int GENERATED ALWAYS AS IDENTITY NOT NULL,
                               type int NOT NULL UNIQUE,
                               PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS field_type (
                                id varchar (255) NOT NULL,
                                name varchar (255) NOT NULL,
                                type int NOT NULL,
                                created_at timestamp NOT NULL DEFAULT NOW(),
                                PRIMARY KEY (id),
                                FOREIGN KEY (type)
                                    REFERENCES field_types(type)
);

CREATE TABLE IF NOT EXISTS fields (
                               id varchar (255) NOT NULL,
                               monitorTypeId varchar (255) NOT NULL,
                               fieldTypeId varchar (255) NOT NULL,
                               created_at timestamp NOT NULL DEFAULT NOW(),
                               PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS monitor_type (
                          id varchar (255) NOT NULL,
                          userId varchar (255) NOT NULL,
                          name varchar (255) NOT NULL,
                          created_at timestamp NOT NULL,
                          PRIMARY KEY (id)
);

--
CREATE TABLE IF NOT EXISTS users (
                        id varchar (255) NOT NULL,
                        username varchar (255) NOT NULL,
                        created_at timestamp NOT NULL DEFAULT NOW(),
                        PRIMARY KEY (id)
);
--
-- INSERT INTO field_types (type) VALUES (1);
-- INSERT INTO field_types (type) VALUES (2);
-- INSERT INTO field_types (type) VALUES (3);
-- INSERT INTO field_types (type) VALUES (4);
-- INSERT INTO field_types (type) VALUES (5);
--
-- INSERT INTO field_type (id, name, type) VALUES ('1', 'Date', 1);
-- INSERT INTO field_type (id, name, type) VALUES ('2', 'Pain Level', 2);
-- INSERT INTO field_type (id, name, type) VALUES ('3', 'Flow Level', 3);
-- INSERT INTO field_type (id, name, type) VALUES ('4', 'Notes', 4);
-- INSERT INTO field_type (id, name, type) VALUES ('5', 'Tags', 5);
--
-- CREATE TABLE IF NOT EXISTS users (
--                         id varchar (255) NOT NULL,
--                         userdescription varchar (255) NOT NULL,
--                         created_at timestamp NOT NULL DEFAULT NOW(),
--                         PRIMARY KEY (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS tablets_monitor (
--     id varchar (255) NOT NULL,
--     userId varchar (255) NOT NULL,
--     monitorTypeId varchar (255) NOT NULL,
--     dateTaken timestamp NOT NULL DEFAULT NOW(),
--     tabletName varchar(255) NOT NULL,
--     notes varchar(255) NOT NULL,
--     created_at timestamp NOT NULL DEFAULT NOW(),
--     PRIMARY KEY (id)
--     FOREIGN KEY (userId),
--         REFERENCES users (id)
-- );
--
CREATE TABLE IF NOT EXISTS period_monitor (
    id varchar (255) NOT NULL,
    user_id varchar (255) NOT NULL,
    monitor_type_id varchar (255) NOT NULL,
    period_date timestamp NOT NULL DEFAULT NOW(),
    pain_level smallint NOT NULL,
    flow_level smallint NOT NULL,
    notes varchar(255) NOT NULL,
    tags varchar(255) DEFAULT NULL,
    created_at timestamp NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pain_level (
    pain_level smallint NOT NULL,
    description varchar (255) NOT NULL,
    created_at timestamp NOT NULL DEFAULT NOW(),
    PRIMARY KEY (pain_level)
);

CREATE TABLE IF NOT EXISTS period_flow_level (
    period_flow_level smallint NOT NULL,
    description varchar(255) NOT NULL,
    created_at timestamp NOT NULL DEFAULT NOW(),
    PRIMARY KEY (period_flow_level)
);

ALTER TABLE period_monitor ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE period_monitor ADD CONSTRAINT pain_level FOREIGN KEY (pain_level) REFERENCES pain_level (pain_level);
ALTER TABLE period_monitor ADD CONSTRAINT flow_level FOREIGN KEY (flow_level) REFERENCES period_flow_level (period_flow_level);
--
-- INSERT INTO pain_level (pain_level, description) VALUES (1, 'Very mild');
-- INSERT INTO pain_level (pain_level, description) VALUES (2, 'Discomforting');
-- INSERT INTO pain_level (pain_level, description) VALUES (3, 'Tolerable');
-- INSERT INTO pain_level (pain_level, description) VALUES (4, 'Distressing');
-- INSERT INTO pain_level (pain_level, description) VALUES (5, 'Very distressing');
-- INSERT INTO pain_level (pain_level, description) VALUES (6, 'Intense');
-- INSERT INTO pain_level (pain_level, description) VALUES (7, 'Very intense');
-- INSERT INTO pain_level (pain_level, description) VALUES (8, 'Utterly horrible');
-- INSERT INTO pain_level (pain_level, description) VALUES (9, 'Excruciating, unbearable');
-- INSERT INTO pain_level (pain_level, description) VALUES (10, 'Unimaginable, unspeakable');
--
-- INSERT INTO period_flow_level (period_flow_level, description) VALUES (1, 'Spotting');
-- INSERT INTO period_flow_level (period_flow_level, description) VALUES (2, 'Very Light');
-- INSERT INTO period_flow_level (period_flow_level, description) VALUES (3, 'Light');
-- INSERT INTO period_flow_level (period_flow_level, description) VALUES (4, 'Medium');
-- INSERT INTO period_flow_level (period_flow_level, description) VALUES (5, 'Heavy');
-- INSERT INTO period_flow_level (period_flow_level, description) VALUES (6, 'Very Heavy');
-- INSERT INTO period_flow_level (period_flow_level, description) VALUES (7, 'Extremely Heavy');
