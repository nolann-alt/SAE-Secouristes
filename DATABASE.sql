-- Supprimmer la base de donnée
DROP DATABASE secours2030;

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS secours2030;
USE secours2030;

-- Table des secouristes
CREATE TABLE IF NOT EXISTS Secouriste (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(255) UNIQUE NOT NULL,
    motDePasse VARCHAR(255) NOT NULL,
    telephone VARCHAR(20)
);

-- Table des admins
CREATE TABLE IF NOT EXISTS Admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(255) UNIQUE NOT NULL,
    motDePasse VARCHAR(255) NOT NULL
);

--Table possède (compétences) :

CREATE TABLE Possede (
     idSecouriste INT,
     intitule VARCHAR(255),
     PRIMARY KEY (idSecouriste, intitule),
     FOREIGN KEY (idSecouriste) REFERENCES Secouriste(id)
);

-- Table DPS :

CREATE TABLE DPS (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     label VARCHAR(255) NOT NULL,
                     date DATE NOT NULL,
                     heure_debut TIME NOT NULL,
                     heure_fin TIME NOT NULL,
                     sportAssocie VARCHAR(100),
                     codeSite INT,
                     lieu VARCHAR(255),
                     description TEXT,
                     couleur VARCHAR(20),
                     FOREIGN KEY (sportAssocie) REFERENCES Sport(nom)
);

-- Insert DPS :

INSERT INTO DPS (label, date, heure_debut, heure_fin, couleur)
VALUES ('DPS test du 18 juin', '2025-06-18', '08:00:00', '09:00:00', 'BLUE');

-- Table Affectation

CREATE TABLE Affectation (
                             idSecouriste BIGINT,
                             idDPS BIGINT,
                             PRIMARY KEY (idSecouriste, idDPS),
                             FOREIGN KEY (idSecouriste) REFERENCES Secouriste(id),
                             FOREIGN KEY (idDPS) REFERENCES DPS(id)
);

-- Insert Affactation

INSERT INTO Affectation (idSecouriste, idDPS)
VALUES (1, 1);

INSERT INTO DPS (label, date, heure_debut, heure_fin, couleur)
VALUES ('DPS test du 18 juin', '2025-06-18', '16:00:00', '22:00:00', 'RED');


-- Table Sport :

CREATE TABLE Sport (
                       nom VARCHAR(100) PRIMARY KEY,
                       niveau_de_risque VARCHAR(50),
                       competences_requises TEXT
);



-- Insert d’un admin de test
INSERT INTO Admin (nom, prenom, email, motDePasse) VALUES
('Gouelo', 'Matthieu', 'matthieu.gouelo@ambulympics.fr', 'rootroot');

INSERT INTO Admin (nom, prenom, email, motDePasse) VALUES ('a', 'a', 'a@ambulympics.fr', 'a');


CREATE TABLE Affectation (
                             idSecouriste INT,
                             idDPS INT,
                             PRIMARY KEY (idSecouriste, idDPS),
                             FOREIGN KEY (idSecouriste) REFERENCES Secouriste(id),
                             FOREIGN KEY (idDPS) REFERENCES DPS(id)
);