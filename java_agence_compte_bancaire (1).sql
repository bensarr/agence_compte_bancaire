-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 12 mai 2020 à 00:43
-- Version du serveur :  10.4.11-MariaDB
-- Version de PHP : 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `java_agence_compte_bancaire`
--

-- --------------------------------------------------------

--
-- Structure de la table `affectation`
--

CREATE TABLE `affectation` (
  `id` int(11) NOT NULL,
  `datedebut` date NOT NULL,
  `datefin` date NOT NULL,
  `caissier_id` int(11) NOT NULL,
  `guichet_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `affectation`
--

INSERT INTO `affectation` (`id`, `datedebut`, `datefin`, `caissier_id`, `guichet_id`) VALUES
(1, '2020-05-04', '2020-05-07', 4, 1),
(2, '2020-05-08', '2020-05-10', 5, 2);

-- --------------------------------------------------------

--
-- Structure de la table `affectationagence`
--

CREATE TABLE `affectationagence` (
  `id` int(11) NOT NULL,
  `caissier_id` int(11) NOT NULL,
  `agence_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `affectationagence`
--

INSERT INTO `affectationagence` (`id`, `caissier_id`, `agence_id`) VALUES
(1, 4, 1),
(2, 5, 2);

-- --------------------------------------------------------

--
-- Structure de la table `agence`
--

CREATE TABLE `agence` (
  `id` int(11) NOT NULL,
  `libelle` varchar(30) NOT NULL,
  `adresse` varchar(100) NOT NULL,
  `tel` varchar(15) NOT NULL,
  `datecreation` datetime NOT NULL DEFAULT current_timestamp(),
  `responsable_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `agence`
--

INSERT INTO `agence` (`id`, `libelle`, `adresse`, `tel`, `datecreation`, `responsable_id`) VALUES
(1, 'FANN', 'FANN', '77 777 77 77', '2020-05-08 00:00:00', 2),
(2, 'SAHM', 'SAHM', '77 777 77 77', '2020-05-08 00:00:00', 3);

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `nci` varchar(20) NOT NULL,
  `adresse` varchar(100) NOT NULL,
  `email` varchar(20) NOT NULL,
  `tel` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `nci`, `adresse`, `email`, `tel`) VALUES
(6, 'Wane', 'Wane', 'Wane@isi.sn', '77 777 77 77'),
(7, 'diop', 'diop', 'diop', 'diop');

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE `compte` (
  `id` int(11) NOT NULL,
  `numero` varchar(25) NOT NULL,
  `solde` double NOT NULL,
  `datecreation` datetime NOT NULL DEFAULT current_timestamp(),
  `etat` varchar(20) NOT NULL,
  `client_id` int(11) NOT NULL,
  `agence_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`id`, `numero`, `solde`, `datecreation`, `etat`, `client_id`, `agence_id`) VALUES
(1, 'd7wEi', 1000000, '2020-05-08 21:57:18', 'Ouvert', 6, 1),
(2, 'CVQk9', 1000000, '2020-05-08 21:59:17', 'Bloqué', 6, 1),
(3, 'ubxeO', 2000000, '2020-05-08 22:18:25', 'Bloqué', 7, 1),
(4, '8KTjC', 2100000, '2020-05-08 22:27:06', 'Ouvert', 7, 1);

-- --------------------------------------------------------

--
-- Structure de la table `courant`
--

CREATE TABLE `courant` (
  `id` int(11) NOT NULL,
  `frais_tenue` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `courant`
--

INSERT INTO `courant` (`id`, `frais_tenue`) VALUES
(4, 1050);

-- --------------------------------------------------------

--
-- Structure de la table `epargne`
--

CREATE TABLE `epargne` (
  `id` int(11) NOT NULL,
  `taux_interet` double NOT NULL,
  `date_fin_blocage` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `guichet`
--

CREATE TABLE `guichet` (
  `id` int(11) NOT NULL,
  `numero` varchar(11) NOT NULL,
  `etat` varchar(20) NOT NULL,
  `agence_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `guichet`
--

INSERT INTO `guichet` (`id`, `numero`, `etat`, `agence_id`) VALUES
(1, 'G1', 'Disponible', 1),
(2, 'G1', 'Disponible', 2);

-- --------------------------------------------------------

--
-- Structure de la table `profil`
--

CREATE TABLE `profil` (
  `id` int(11) NOT NULL,
  `libelle` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `profil`
--

INSERT INTO `profil` (`id`, `libelle`) VALUES
(1, 'Administrateur'),
(2, 'Responsable'),
(3, 'Caissier'),
(4, 'Client');

-- --------------------------------------------------------

--
-- Structure de la table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `type` varchar(20) NOT NULL,
  `montant` double NOT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp(),
  `compte_source` int(11) NOT NULL,
  `compte_cible` int(11) NOT NULL,
  `guichet_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `transaction`
--

INSERT INTO `transaction` (`id`, `type`, `montant`, `date`, `compte_source`, `compte_cible`, `guichet_id`) VALUES
(4, 'Dépot', 1000000, '2020-05-08 22:27:06', 4, 0, 0),
(5, 'Dépot', 100000, '2020-05-08 22:29:36', 4, 0, 2);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `login` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `etat` varchar(15) NOT NULL,
  `avoiragence` varchar(20) NOT NULL,
  `profil_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `prenom`, `login`, `password`, `etat`, `avoiragence`, `profil_id`) VALUES
(1, 'SARR', 'El Hadji Hady', 'admin', 'admin', 'Actif', '', 1),
(2, 'Res', 'Fann', 'resfann', 'resfann', 'Actif', 'Non', 2),
(3, 'Res', 'Sahm', 'ressahm', 'ressahm', 'Inactif', 'Non', 2),
(4, 'Cais', 'Fann', 'caisfann', 'caisfann', 'Actif', 'Non', 3),
(5, 'Cais', 'Sahm', 'caissahm', 'caissahm', 'Actif', 'Non', 3),
(6, 'Wane', 'Wane', 'Wane', 'Wane', 'Inactif', '', 4),
(7, 'diop', 'diop', 'diop', 'diop', 'Inactif', '', 4);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `affectation`
--
ALTER TABLE `affectation`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `affectationagence`
--
ALTER TABLE `affectationagence`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `agence`
--
ALTER TABLE `agence`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `courant`
--
ALTER TABLE `courant`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `epargne`
--
ALTER TABLE `epargne`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `guichet`
--
ALTER TABLE `guichet`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `profil`
--
ALTER TABLE `profil`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `affectation`
--
ALTER TABLE `affectation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `affectationagence`
--
ALTER TABLE `affectationagence`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `agence`
--
ALTER TABLE `agence`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `compte`
--
ALTER TABLE `compte`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `courant`
--
ALTER TABLE `courant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `epargne`
--
ALTER TABLE `epargne`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `guichet`
--
ALTER TABLE `guichet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `profil`
--
ALTER TABLE `profil`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
