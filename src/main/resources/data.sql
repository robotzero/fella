INSERT INTO pain_level (pain_level, description) VALUES (1, 'Very mild');
INSERT INTO pain_level (pain_level, description) VALUES (2, 'Discomforting');
INSERT INTO pain_level (pain_level, description) VALUES (3, 'Tolerable');
INSERT INTO pain_level (pain_level, description) VALUES (4, 'Distressing');
INSERT INTO pain_level (pain_level, description) VALUES (5, 'Very distressing');
INSERT INTO pain_level (pain_level, description) VALUES (6, 'Intense');
INSERT INTO pain_level (pain_level, description) VALUES (7, 'Very intense');
INSERT INTO pain_level (pain_level, description) VALUES (8, 'Utterly horrible');
INSERT INTO pain_level (pain_level, description) VALUES (9, 'Excruciating, unbearable');
INSERT INTO pain_level (pain_level, description) VALUES (10, 'Unimaginable, unspeakable');

INSERT INTO period_flow_level (period_flow_level, description) VALUES (1, 'Spotting');
INSERT INTO period_flow_level (period_flow_level, description) VALUES (2, 'Very Light');
INSERT INTO period_flow_level (period_flow_level, description) VALUES (3, 'Light');
INSERT INTO period_flow_level (period_flow_level, description) VALUES (4, 'Medium');
INSERT INTO period_flow_level (period_flow_level, description) VALUES (5, 'Heavy');
INSERT INTO period_flow_level (period_flow_level, description) VALUES (6, 'Very Heavy');
INSERT INTO period_flow_level (period_flow_level, description) VALUES (7, 'Extremely Heavy');


INSERT INTO field_types (type) VALUES (1);
INSERT INTO field_types (type) VALUES (2);
INSERT INTO field_types (type) VALUES (3);
INSERT INTO field_types (type) VALUES (4);
INSERT INTO field_types (type) VALUES (5);

INSERT INTO field_type (id, name, type) VALUES ('1', 'Date', 1);
INSERT INTO field_type (id, name, type) VALUES ('2', 'Pain Level', 2);
INSERT INTO field_type (id, name, type) VALUES ('3', 'Flow Level', 3);
INSERT INTO field_type (id, name, type) VALUES ('4', 'Notes', 4);
INSERT INTO field_type (id, name, type) VALUES ('5', 'Tags', 5);
