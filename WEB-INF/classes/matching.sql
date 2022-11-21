CREATE TABLE Users (
    idUser VARCHAR PRIMARY KEY,
    nom VARCHAR,
    password VARCHAR
);
CREATE SEQUENCE seq_user
INCREMENT 1
MINVALUE 21 
MAXVALUE 9999
CYCLE;
CREATE OR REPLACE FUNCTION getSeqUser()
    RETURNS integer AS $users$
    declare users integer;
BEGIN
    SELECT nextval('seq_user') INTO users FROM DUAL;
    RETURN users;
END;
$users$ LANGUAGE plpgsql;
ALTER SEQUENCE seq_user RESTART WITH 1;

CREATE TABLE Annexes (
    idAnnexe VARCHAR PRIMARY KEY,
    nom VARCHAR
);

CREATE SEQUENCE seq_annexe
INCREMENT 10
MINVALUE 10 
MAXVALUE 200
START 10
CYCLE;

CREATE OR REPLACE FUNCTION getSeqAnnexe()
    RETURNS integer AS $annexe$
    declare annexe integer;
BEGIN
    SELECT nextval('seq_annexe') INTO annexe FROM DUAL;
    RETURN annexe;
END;
$annexe$ LANGUAGE plpgsql;
ALTER SEQUENCE seq_annexe RESTART WITH 10;

CREATE TABLE Informations (
    idInfo VARCHAR PRIMARY KEY,
    idAnnexe VARCHAR REFERENCES Annexes (idAnnexe),
    idUser VARCHAR REFERENCES Users (idUser),
    note DECIMAL
);

CREATE SEQUENCE seq_information
INCREMENT 1
MINVALUE 1;

CREATE OR REPLACE FUNCTION getSeqInformation()
    RETURNS integer AS $information$
    declare information integer;
BEGIN
    SELECT nextval('seq_information') INTO information FROM DUAL;
    RETURN information;
END;
$information$ LANGUAGE plpgsql;
ALTER SEQUENCE seq_information RESTART WITH 1;

CREATE TABLE Critere (
    idCritere VARCHAR PRIMARY KEY,
    idAnnexe VARCHAR REFERENCES Annexes (idAnnexe),
    idUser VARCHAR REFERENCES Users (idUser),
    coefficient INTEGER
);

CREATE SEQUENCE seq_critere
INCREMENT 1
MINVALUE 1;

CREATE OR REPLACE FUNCTION getSeqCritere()
    RETURNS integer AS $critere$
    declare critere integer;
BEGIN
    SELECT nextval('seq_critere') INTO critere FROM DUAL;
    RETURN critere;
END;
$critere$ LANGUAGE plpgsql;
ALTER SEQUENCE seq_information RESTART WITH 1;

CREATE TABLE Raikitra (
    idRaikitra SERIAL PRIMARY KEY,
    idUserB VARCHAR REFERENCES Users (idUser),
    idUserV VARCHAR REFERENCES Users (idUser)
);

CREATE SEQUENCE seq_raikitra
INCREMENT 1
MINVALUE 1;
CREATE OR REPLACE FUNCTION getSeqRaikitra()
    RETURNS integer AS $raikitra$
    declare raikitra integer;
BEGIN
    SELECT nextval('seq_raikitra') INTO raikitra FROM DUAL;
    RETURN raikitra;
END;
$raikitra$ LANGUAGE plpgsql;

CREATE TABLE Indisponible (
    idIndispo SERIAL PRIMARY KEY,
    idUser VARCHAR REFERENCES Users (idUser)
);

CREATE SEQUENCE seq_indisponible
INCREMENT 1
MINVALUE 1;

CREATE OR REPLACE FUNCTION getSeqIndisponible()
    RETURNS integer AS $indisponible$
    declare indisponible integer;
BEGIN
    SELECT nextval('seq_indisponible') INTO indisponible FROM DUAL;
    RETURN indisponible;
END;
$indisponible$ LANGUAGE plpgsql;

ALTER SEQUENCE seq_annexe RESTART WITH 10;
ALTER SEQUENCE seq_user RESTART WITH 1;
ALTER SEQUENCE seq_information RESTART WITH 1;
ALTER SEQUENCE seq_critere RESTART WITH 1;
ALTER TABLE users
ADD COLUMN genre VARCHAR;

UPDATE informations SET note=14.0 WHERE idinfo='INF0008';
UPDATE informations SET note=18.0 WHERE idinfo='INF0006';
UPDATE indisponible SET iduser='USR0003' WHERE idindispo=2;
INSERT INTO raikitra (iduserb, iduserv) VALUES
    ('USR0006', 'USR0017');
INSERT INTO indisponible (idIndispo, idUser) VALUES
    ('USR0001', 'USR0002');
SELECT iduserb FROM raikitra UNION SELECT iduserv FROM raikitra UNION SELECT iduser FROM indisponible;
SELECT * FROM Users WHERE iduser NOT IN (SELECT iduserb FROM raikitra UNION SELECT iduserv FROM raikitra UNION SELECT iduser FROM indisponible);
CREATE VIEW users_disponible AS
SELECT *
FROM Users
WHERE iduser NOT IN (
    SELECT iduser1
    FROM raikitra 
    UNION 
    SELECT iduser2
    FROM raikitra
    UNION
    SELECT idUser
    FROM Indisponible 
    WHERE idIndispo='USR0004'
);

CREATE TABLE Match (
    idMatch VARCHAR PRIMARY KEY,
    idUser VARCHAR REFERENCES Users (idUser),
    idUserMatch VARCHAR REFERENCES Users (idUser),
    dateMatch DATE
);

CREATE SEQUENCE seq_match
INCREMENT 1
MINVALUE 1;

CREATE OR REPLACE FUNCTION getSeqMatch()
    RETURNS integer AS $match$
    declare match integer;
BEGIN
    SELECT nextval('seq_match') INTO match FROM DUAL;
    RETURN match;
END;
$match$ LANGUAGE plpgsql;
ALTER TABLE Raikitra
ADD COLUMN idMatch VARCHAR;
CREATE VIEW match_dispo AS (
    SELECT *
    FROM match WHERE idMatch NOT IN (
    SELECT idMatch 
    FROM Raikitra
    )
);

SELECT *
FROM match WHERE idMatch NOT IN (
    SELECT idMatch 
    FROM Raikitra
);

CREATE SEQUENCE seq_raikitra
INCREMENT 1
MINVALUE 1;

CREATE OR REPLACE FUNCTION getSeqMatch()
    RETURNS integer AS $match$
    declare match integer;
BEGIN
    SELECT nextval('seq_match') INTO match FROM DUAL;
    RETURN match;
END;
$match$ LANGUAGE plpgsql;

ALTER TABLE raikitra ALTER COLUMN idRaikitra TYPE VARCHAR;
ALTER TABLE raikitra 
RENAME COLUMN iduserb TO iduser1;
ALTER TABLE raikitra 
RENAME COLUMN iduserv TO iduser2;
SELECT * FROM Users WHERE idUser NOT IN (
    SELECT idUser1 FROM raikitra
    UNION 
    SELECT idUser2 FROM raikitra
);

SELECT *
FROM Indisponible 
WHERE idIndispo='USR0004';
SELECT *
FROM users
WHERE idUser NOT IN (
    SELECT *
    FROM Indisponible 
    WHERE idIndispo='USR0004'
);

CREATE OR REPLACE FUNCTION get_users_disponible (idCurrentUser VARCHAR) 
    RETURNS TABLE (
        idUser_disponible VARCHAR,
        nom_disponible VARCHAR,
        password_disponible VARCHAR,
        genre_disponible VARCHAR
    ) 
AS $$
BEGIN
    RETURN QUERY SELECT idUser, nom, password, genre
    FROM Users
    WHERE iduser NOT IN (
        SELECT iduser1
        FROM raikitra 
        UNION 
        SELECT iduser2
        FROM raikitra
        UNION
        SELECT idUser
        FROM Indisponible 
        WHERE idIndispo=idCurrentUser
    );
END; $$ 

LANGUAGE 'plpgsql';

ALTER TABLE Indisponible ALTER COLUMN idIndispo TYPE VARCHAR;

SELECT * FROM get_users_disponible('USR0001') AS f(idUser, nom, password, genre);
SELECT *
FROM informations
WHERE idUser = 'USR0004';
SELECT *
FROM criteres
WHERE idUser = 'USR0006';

SELECT (note*coefficient) as note, c.coefficient
FROM (
    SELECT *
    FROM informations
    WHERE idUser = 'USR0004'
) AS info JOIN (
    SELECT *
    FROM criteres
    WHERE idUser = 'USR0006'
) AS c ON info.idAnnexe = c.idAnnexe
JOIN Annexes AS axe ON info.idAnnexe = axe.idAnnexe;

SELECT SUM(note) / SUM(coefficient)
FROM (
    SELECT (note*coefficient) as note, c.coefficient
    FROM (
        SELECT *
        FROM informations
        WHERE idUser = 'USR0001'
    ) AS info JOIN (
        SELECT *
        FROM criteres
        WHERE idUser = 'USR0004'
    ) AS c ON info.idAnnexe = c.idAnnexe
    JOIN Annexes AS axe ON info.idAnnexe = axe.idAnnexe
) AS note;
SELECT SUM(note) / SUM(coefficient) FROM (SELECT (note*coefficient) as note, c.coefficient FROM (SELECT * FROM informations WHERE idUser = 'USR0004') AS info JOIN (SELECT * FROM criteres WHERE idUser = 'USR0006') AS c ON info.idAnnexe = c.idAnnexe JOIN Annexes AS axe ON info.idAnnexe = axe.idAnnexe) AS note;
SELECT * FROM Criteres AS c JOIN Annexes AS a ON c.idAnnexe = a.idAnnexe; 