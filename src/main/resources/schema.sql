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

-- AUTH SERVER

CREATE TABLE oauth2_authorization (
                                      id varchar(100) NOT NULL,
                                      registered_client_id varchar(100) NOT NULL,
                                      principal_name varchar(200) NOT NULL,
                                      authorization_grant_type varchar(100) NOT NULL,
                                      authorized_scopes varchar(1000) DEFAULT NULL,
                                      attributes text DEFAULT NULL,
                                      state varchar(500) DEFAULT NULL,
                                      authorization_code_value text DEFAULT NULL,
                                      authorization_code_issued_at timestamp DEFAULT NULL,
                                      authorization_code_expires_at timestamp DEFAULT NULL,
                                      authorization_code_metadata text DEFAULT NULL,
                                      access_token_value text DEFAULT NULL,
                                      access_token_issued_at timestamp DEFAULT NULL,
                                      access_token_expires_at timestamp DEFAULT NULL,
                                      access_token_metadata text DEFAULT NULL,
                                      access_token_type varchar(100) DEFAULT NULL,
                                      access_token_scopes varchar(1000) DEFAULT NULL,
                                      oidc_id_token_value text DEFAULT NULL,
                                      oidc_id_token_issued_at timestamp DEFAULT NULL,
                                      oidc_id_token_expires_at timestamp DEFAULT NULL,
                                      oidc_id_token_metadata text DEFAULT NULL,
                                      refresh_token_value text DEFAULT NULL,
                                      refresh_token_issued_at timestamp DEFAULT NULL,
                                      refresh_token_expires_at timestamp DEFAULT NULL,
                                      refresh_token_metadata text DEFAULT NULL,
                                      PRIMARY KEY (id)
);

CREATE TABLE oauth2_authorization_consent (
                                              registered_client_id varchar(100) NOT NULL,
                                              principal_name varchar(200) NOT NULL,
                                              authorities varchar(1000) NOT NULL,
                                              PRIMARY KEY (registered_client_id, principal_name)
);

CREATE TABLE oauth2_registered_client (
                                          id varchar(100) NOT NULL,
                                          client_id varchar(100) NOT NULL,
                                          client_id_issued_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                          client_secret varchar(200) DEFAULT NULL,
                                          client_secret_expires_at timestamp DEFAULT NULL,
                                          client_name varchar(200) NOT NULL,
                                          client_authentication_methods varchar(1000) NOT NULL,
                                          authorization_grant_types varchar(1000) NOT NULL,
                                          redirect_uris varchar(1000) DEFAULT NULL,
                                          scopes varchar(1000) NOT NULL,
                                          client_settings varchar(2000) NOT NULL,
                                          token_settings varchar(2000) NOT NULL,
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


CREATE TABLE users
(
    user_id      UUID   DEFAULT gen_random_uuid() PRIMARY     KEY,
    username     VARCHAR (   255 ) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE periods
(
    period_id    UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id      INT REFERENCES users (user_id) ON DELETE CASCADE,
    start_date   DATE NOT NULL,
    end_date     DATE NOT NULL,
    cycle_length INT, -- Optional: This can be calculated based on prior periods
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pain_levels
(
    pain_level_id    UUID   DEFAULT gen_random_uuid() PRIMARY KEY,
    pain_level_value INT CHECK (pain_level_value >= 0 AND pain_level_value <= 10), -- Pain level from 0 to 10
    pain_description VARCHAR(255)                                                  -- Optional description of the pain
);

CREATE TABLE period_pain
(
    period_id     UUID REFERENCES periods (period_id) ON DELETE CASCADE,
    pain_level_id UUID REFERENCES pain_levels (pain_level_id) ON DELETE CASCADE,
    pain_date     DATE NOT NULL,
    PRIMARY KEY (period_id, pain_date)
);

CREATE TABLE flow_levels
(
    flow_level_id    UUID   DEFAULT gen_random_uuid() PRIMARY KEY,
    flow_level_value INT CHECK (flow_level_value >= 0 AND flow_level_value <= 5), -- Flow level from 0 to 5
    flow_description VARCHAR(255)                                                 -- Optional description of the flow
);

CREATE TABLE period_flow
(
    period_id     UUID REFERENCES periods (period_id) ON DELETE CASCADE,
    flow_level_id UUID REFERENCES flow_levels (flow_level_id) ON DELETE CASCADE,
    flow_date     DATE NOT NULL,
    PRIMARY KEY (period_id, flow_date)
);

CREATE TABLE moods
(
    mood_id          UUID   DEFAULT gen_random_uuid() PRIMARY KEY,
    mood_name        VARCHAR(100) NOT NULL, -- Name of the mood (e.g., Happy, Sad, Anxious)
    mood_description VARCHAR(255)           -- Optional description
);

CREATE TABLE period_mood
(
    period_id UUID REFERENCES periods (period_id) ON DELETE CASCADE,
    mood_id   UUID REFERENCES moods (mood_id) ON DELETE CASCADE,
    mood_date DATE NOT NULL,
    PRIMARY KEY (period_id, mood_date)
);

CREATE TABLE migraines
(
    migraine_id          UUID   DEFAULT gen_random_uuid() PRIMARY KEY,
    migraine_level       INT CHECK (migraine_level >= 0 AND migraine_level <= 10), -- Migraine severity from 0 to 10
    migraine_description VARCHAR(255)                                              -- Optional description of the migraine
);

CREATE TABLE period_migraine
(
    period_id     UUID REFERENCES periods (period_id) ON DELETE CASCADE,
    migraine_id   UUID REFERENCES migraines (migraine_id) ON DELETE CASCADE,
    migraine_date DATE NOT NULL,
    PRIMARY KEY (period_id, migraine_date)
);

CREATE TABLE symptom_details
(
    symptom_id          UUID   DEFAULT gen_random_uuid() PRIMARY KEY,
    period_id           INT REFERENCES periods (period_id) ON DELETE CASCADE,
    symptom_type        VARCHAR(100) NOT NULL,                                  -- E.g., pain, mood, migraine, etc.
    symptom_value       INT CHECK (symptom_value >= 0 AND symptom_value <= 10), -- Generic value for tracking
    symptom_description VARCHAR(255),
    symptom_date        DATE         NOT NULL
);
