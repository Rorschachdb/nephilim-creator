-- INCARNATION EPOCH
-- 1 Atlantis
INSERT INTO T_INCARNATION_EPOCH (id, cost, name, era)
VALUES (NEXTVAL('SEQ_INCARNATION_EPOCH'), 0, 'La chute de l''atlantide', 'LOST_ERA');
-- 2 Deluge
INSERT INTO T_INCARNATION_EPOCH (id, cost, name, era)
VALUES (NEXTVAL('SEQ_INCARNATION_EPOCH'), 2, 'Le Déluge', 'ELEMENTARY_WARS');

-- LOCATIONS
INSERT INTO ET_EPOCH_LOCATION (INCARNATION_EPOCH_ID, location)
VALUES (1, 'L''Atlantide');

-- MAGICAL EFFECTS
INSERT INTO ET_MAGIC_EFFECTS (INCARNATION_EPOCH_ID, occult_Science, quantity)
VALUES (1, 'MAGIC', 2);

INSERT INTO ET_MAGIC_EFFECTS (INCARNATION_EPOCH_ID, occult_Science, quantity)
VALUES (2, 'MAGIC', 2);

-- DEGREES
-- ESOTERIC QUEST starts at 1001
ALTER SEQUENCE SEQ_DEGREE RESTART WITH 1001;
-- 1001 Le sentier dor
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'ESOTERIC_QUEST', 'Le sentier d''or', '');
--1002 Hyperborée
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'ESOTERIC_QUEST', 'Hyperborée', '');

-- ARCANA QUEST starts at 1101
ALTER SEQUENCE SEQ_DEGREE RESTART WITH 1101;

-- OCCULT ART starts at 1201
ALTER SEQUENCE SEQ_DEGREE RESTART WITH 1201;
-- 1201 Magie
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'OCCULT_ART', 'Magie', '');
-- 1202 Kabbale
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'OCCULT_ART', 'Kabbale', '');
-- 1203 Alchimie
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'OCCULT_ART', 'Alchimie', '');

-- ESOTERIC KNOWLEDGE starts at 1301
ALTER SEQUENCE SEQ_DEGREE RESTART WITH 1301;
-- 1301 Kaïm
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'ESOTERIC_KNOWLEDGE', 'Kaïm', '');
-- 1302 Champs Magiques
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'ESOTERIC_KNOWLEDGE', 'Champs Magiques', '');
-- 1303 Nephilim
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'ESOTERIC_KNOWLEDGE', 'Nephilim', '');
-- 1304 Orichalque
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'ESOTERIC_KNOWLEDGE', 'Orichalque', '');
-- 1305 Mystères
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'ESOTERIC_KNOWLEDGE', 'Mystères', '');
-- 1306 Histoire Invisible
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'ESOTERIC_KNOWLEDGE', 'Histoire Invisible', '');
-- 1307 666
INSERT INTO T_DEGREE (id, type, name, description)
VALUES (NEXTVAL('SEQ_DEGREE'), 'ESOTERIC_KNOWLEDGE', '666', '');

-- SIMULACRUM starts at 2001
ALTER SEQUENCE SEQ_DEGREE RESTART WITH 2001;
-- Atlantis
-- 2001 Heros
INSERT INTO T_DEGREE (id, type, name, description, affinity)
VALUES (NEXTVAL('SEQ_DEGREE'), 'SIMULACRUM', 'Héros', '', 'FIRE');
-- 2002 Sage
INSERT INTO T_DEGREE (id, type, name, description, affinity)
VALUES (NEXTVAL('SEQ_DEGREE'), 'SIMULACRUM', 'Sage', '', 'AIR');
-- 2003 Messagere
INSERT INTO T_DEGREE (id, type, name, description, affinity)
VALUES (NEXTVAL('SEQ_DEGREE'), 'SIMULACRUM', 'Messagère', '', 'WATER');
-- 2004 Decepteur
INSERT INTO T_DEGREE (id, type, name, description, affinity)
VALUES (NEXTVAL('SEQ_DEGREE'), 'SIMULACRUM', 'Décepteur', '', 'MOON');
-- 2005 Soigneur
INSERT INTO T_DEGREE (id, type, name, description, affinity)
VALUES (NEXTVAL('SEQ_DEGREE'), 'SIMULACRUM', 'Soigneur', '', 'EARTH');
-- Deluge
-- 2006 Heros
INSERT INTO T_DEGREE (id, type, name, description, affinity)
VALUES (NEXTVAL('SEQ_DEGREE'), 'SIMULACRUM', 'Oracle', '', 'FIRE');
-- 2007 Sage
INSERT INTO T_DEGREE (id, type, name, description, affinity)
VALUES (NEXTVAL('SEQ_DEGREE'), 'SIMULACRUM', 'Matriarche', '', 'AIR');
-- 2008 Messagere
INSERT INTO T_DEGREE (id, type, name, description, affinity)
VALUES (NEXTVAL('SEQ_DEGREE'), 'SIMULACRUM', 'Navigateur', '', 'WATER');
-- 2009 Decepteur
INSERT INTO T_DEGREE (id, type, name, description, affinity)
VALUES (NEXTVAL('SEQ_DEGREE'), 'SIMULACRUM', 'Meurtrier', '', 'MOON');
-- 2010 Soigneur
INSERT INTO T_DEGREE (id, type, name, description, affinity)
VALUES (NEXTVAL('SEQ_DEGREE'), 'SIMULACRUM', 'Charpentier', '', 'EARTH');

-- DEGREE VALUES
-- Atlantis
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (1, 1001, 2);

INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (1, 1201, 2);

INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (1, 1301, 2);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (1, 1302, 2);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (1, 1303, 2);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)

VALUES (1, 2001, 1);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (1, 2002, 1);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (1, 2003, 1);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (1, 2004, 1);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (1, 2005, 1);

-- Deluge
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 1001, 2);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 1002, 1);

INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 1201, 3);

INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 1301, 1);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 1302, 1);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 1303, 2);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 1304, 2);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 1305, 2);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 1306, 1);

INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 2006, 1);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 2007, 1);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 2008, 1);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 2009, 1);
INSERT INTO AT_INCARNATION_EPOCH_DEGREE_VALUE (fk_incarnation_epoch_id, fk_degree_id, level)
VALUES (2, 2010, 1);
