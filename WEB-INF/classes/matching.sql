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
    SELECT nextval('seq_user') INTO users;
    RETURN users;
END;
$users$ LANGUAGE plpgsql;
ALTER SEQUENCE seq_user RESTART WITH 1;

CREATE TABLE axes (
    idAxe VARCHAR PRIMARY KEY,
    nom VARCHAR
);

CREATE SEQUENCE seq_Axe
INCREMENT 10
MINVALUE 10 
MAXVALUE 200
START 10
CYCLE;

CREATE OR REPLACE FUNCTION getSeqAxe()
    RETURNS integer AS $Axe$
    declare Axe integer;
BEGIN
    SELECT nextval('seq_Axe') INTO Axe FROM DUAL;
    RETURN Axe;
END;
$Axe$ LANGUAGE plpgsql;
ALTER SEQUENCE seq_Axe RESTART WITH 10;

CREATE TABLE Informations (
    idInfo VARCHAR PRIMARY KEY,
    idAxe VARCHAR REFERENCES axes (idAxe),
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
    SELECT nextval('seq_information') INTO information;
    RETURN information;
END;
$information$ LANGUAGE plpgsql;
ALTER SEQUENCE seq_information RESTART WITH 1;

CREATE TABLE Critere (
    idCritere VARCHAR PRIMARY KEY,
    idAxe VARCHAR REFERENCES axes (idAxe),
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
    SELECT nextval('seq_critere') INTO critere;
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
    idIndispo VARCHAR REFERENCES Users (idUser),
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

ALTER SEQUENCE seq_Axe RESTART WITH 10;
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
) AS c ON info.idAxe = c.idAxe
JOIN axes AS axe ON info.idAxe = axe.idAxe;

SELECT SUM(note) / SUM(coefficient)
FROM (
    SELECT (note*coefficient) as note, c.coefficient
    FROM (
        SELECT *
        FROM informations
        WHERE idUser = 'USR0039'
    ) AS info JOIN (
        SELECT *
        FROM criteres
        WHERE idUser = 'USR0020'
    ) AS c ON info.idAxe = c.idAxe
    JOIN axes AS axe ON info.idAxe = axe.idAxe
) AS note;
SELECT SUM(note) / SUM(coefficient) FROM (SELECT (note*coefficient) as note, c.coefficient FROM (SELECT * FROM informations WHERE idUser = 'USR0004') AS info JOIN (SELECT * FROM criteres WHERE idUser = 'USR0006') AS c ON info.idAxe = c.idAxe JOIN axes AS axe ON info.idAxe = axe.idAxe) AS note;
SELECT * FROM Criteres AS c JOIN axes AS a ON c.idAxe = a.idAxe; 
ALTER TABLE axes
RENAME TO Axes; 
ALTER TABLE criteres 
RENAME COLUMN idAnnexe TO idaxe;
ALTER TABLE informations 
RENAME COLUMN idAnnexe TO idaxe;
SELECT info.iduser, note, coefficient  FROM informations AS info JOIN (
    SELECT *
    FROM criteres
    WHERE idUser = 'USR0039'
) AS c ON info.idAxe = c.idAxe;
SELECT SUM(coefficient) FROM (
    SELECT *
    FROM criteres
    WHERE idUser = 'USR0039'
) AS criteres;
SELECT iduser
FROM (
    SELECT info.iduser, note, coefficient  FROM informations AS info JOIN (
        SELECT *
        FROM criteres
        WHERE idUser = 'USR0039'
    ) AS c ON info.idAxe = c.idAxe
) AS info
GROUP BY idUser
ORDER BY SUM((coefficient * note)) / SUM(coefficient) DESC;
SELECT Classement.iduser, nom, password, genre, note FROM
(SELECT * FROM get_users_disponible('USR0019') AS f(idUser, nom, password, genre)) AS users JOIN (
    SELECT info.iduser, SUM((coefficient * note)) / SUM(coefficient) as note
    FROM (
        SELECT info.iduser, coefficient, note  FROM informations AS info JOIN (
            SELECT *
            FROM criteres
            WHERE idUser = 'USR0019'
        ) AS c ON info.idAxe = c.idAxe
    ) AS info JOIN users ON info.iduser = users.idUser
    GROUP BY info.idUser
) AS Classement ON Classement.idUser = users.idUser
WHERE genre = 'feminin' AND note >=14 AND get_note(Classement.idUser, 'USR0019') >= 14
ORDER BY note DESC;

CREATE OR REPLACE FUNCTION get_note (idUser1 VARCHAR, idUser2 VARCHAR) 
    RETURNS DOUBLE PRECISION
AS $$
declare
   note_moyenne DOUBLE PRECISION;
BEGIN
    SELECT (SUM(note) / SUM(coefficient)) into note_moyenne
    FROM (
        SELECT (note * coefficient) as note, c.coefficient
        FROM (
            SELECT *
            FROM informations
            WHERE idUser = idUser2
        ) AS info JOIN (
            SELECT *
            FROM criteres
            WHERE idUser = idUser1
        ) AS c ON info.idAxe = c.idAxe
        JOIN axes AS axe ON info.idAxe = axe.idAxe
    ) AS note;

    RETURN note_moyenne;
END; $$ 

LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION get_classement (idCurrentUser VARCHAR, genre_user VARCHAR) 
    RETURNS TABLE (
        idUser_disponible VARCHAR,
        nom_disponible VARCHAR,
        password_disponible VARCHAR,
        genre_disponible VARCHAR,
        note_disponible NUMERIC
    ) 
AS $$
BEGIN
    RETURN QUERY SELECT Classement.iduser, nom, password, genre, note FROM
    (SELECT * FROM get_users_disponible(idCurrentUser) AS f(idUser, nom, password, genre)) AS users JOIN (
        SELECT info.iduser, SUM((coefficient * note)) / SUM(coefficient) as note
        FROM (
            SELECT info.iduser, coefficient, note  FROM informations AS info JOIN (
                SELECT *
                FROM criteres
                WHERE idUser = idCurrentUser
            ) AS c ON info.idAxe = c.idAxe
        ) AS info JOIN users ON info.iduser = users.idUser
        GROUP BY info.idUser
    ) AS Classement ON Classement.idUser = users.idUser
    WHERE genre = genre_user AND note >=14 AND get_note(Classement.idUser, idCurrentUser) >= 14 AND Classement.idUser != idCurrentUser
    ORDER BY note DESC;
END; $$ 

LANGUAGE 'plpgsql';

CREATE TABLE Precision (
    idPrecision VARCHAR PRIMARY KEY,
    idIntervalle VARCHAR REFERENCES Intervalle (idIntervalle),
    valeur VARCHAR REFERENCES Valeur (valeur)
);

CREATE TABLE Valeur (
    valeur VARCHAR PRIMARY KEY,
    note BIGINT
);

INSERT INTO Valeur (valeur, note) VALUES
    ('Souhaite', 5),
    ('Moyen', 4),
    ('Passable', 3),
    ('Mauvais', 1);

CREATE TABLE Intervalle (
    idIntervalle VARCHAR PRIMARY KEY,
    idAxe VARCHAR REFERENCES Axes (idAxe),
    intervalle VARCHAR
);

CREATE SEQUENCE seq_intervalle
INCREMENT 1
MINVALUE 1;
CREATE OR REPLACE FUNCTION getSeqIntervalle()
    RETURNS integer AS $intervalle$
    declare intervalle integer;
BEGIN
    SELECT nextval('seq_intervalle') INTO intervalle FROM DUAL;
    RETURN intervalle;
END;
$intervalle$ LANGUAGE plpgsql;
CREATE SEQUENCE seq_precision
INCREMENT 1
MINVALUE 1;

CREATE OR REPLACE FUNCTION getSeqPrecision()
    RETURNS integer AS $precision$
    declare precision integer;
BEGIN
    SELECT nextval('seq_precision') INTO precision;
    RETURN precision;
END;
$precision$ LANGUAGE plpgsql;

ALTER TABLE precisions
ADD COLUMN idUser VARCHAR REFERENCES users (idUser);
ALTER TABLE precision 
RENAME COLUMN note TO valeur;
ALTER TABLE precision ALTER COLUMN valeur TYPE VARCHAR;
ALTER TABLE informations 
DROP COLUMN note;
SELECT idprecision, p.idintervalle, p.valeur, idUser, intervalle, idAxe, note FROM precisions AS p JOIN intervalle AS i ON p.idIntervalle = i.idIntervalle JOIN valeur AS v ON p.valeur = v.valeur;
INSERT INTO criteres (idcritere, idaxe, iduser, coefficient) VALUES
    ('CRI0104', 'A070', 'USR0058', 5);