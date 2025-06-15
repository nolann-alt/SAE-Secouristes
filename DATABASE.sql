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

-- Insert d’un admin de test
INSERT INTO Admin (nom, prenom, email, motDePasse) VALUES
('Gouelo', 'Matthieu', 'matthieu.gouelo@ambulympics.fr', 'rootroot');
