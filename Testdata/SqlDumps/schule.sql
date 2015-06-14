-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 14. Jun 2015 um 13:57
-- Server Version: 5.6.16
-- PHP-Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `schule`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `fach`
--

CREATE TABLE IF NOT EXISTS `fach` (
  `id` int(11) NOT NULL,
  `titel` varchar(32) COLLATE utf8_bin NOT NULL,
  `unterrichtetVon` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lehrer` (`unterrichtetVon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `fach`
--

INSERT INTO `fach` (`id`, `titel`, `unterrichtetVon`) VALUES
(1, 'Physik', 1),
(2, 'Mathe', 2),
(3, 'Englisch', 1),
(4, 'Bio', 3);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `fach_schueler`
--

CREATE TABLE IF NOT EXISTS `fach_schueler` (
  `fach` int(11) NOT NULL,
  `schueler` int(11) NOT NULL,
  KEY `fach` (`fach`),
  KEY `schueler` (`schueler`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `fach_schueler`
--

INSERT INTO `fach_schueler` (`fach`, `schueler`) VALUES
(1, 1),
(1, 2),
(1, 4),
(2, 2),
(3, 5),
(4, 3),
(4, 6),
(3, 5),
(3, 123),
(1, 123);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `klasse`
--

CREATE TABLE IF NOT EXISTS `klasse` (
  `id` int(11) NOT NULL,
  `titel` varchar(32) COLLATE utf8_bin NOT NULL,
  `klassenlehrer` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lehrer` (`klassenlehrer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `klasse`
--

INSERT INTO `klasse` (`id`, `titel`, `klassenlehrer`) VALUES
(1, '1A', 1),
(2, '1B', 1),
(3, '4C', 2),
(4, '5A', 3);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `lehrer`
--

CREATE TABLE IF NOT EXISTS `lehrer` (
  `id` int(11) NOT NULL,
  `name` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `lehrer`
--

INSERT INTO `lehrer` (`id`, `name`) VALUES
(1, 'Maerz'),
(2, 'Funke'),
(3, 'Heinz');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `schueler`
--

CREATE TABLE IF NOT EXISTS `schueler` (
  `id` int(11) NOT NULL,
  `name` varchar(64) COLLATE utf8_bin NOT NULL,
  `inKlasse` int(11) NOT NULL,
  `lieblingsLehrer` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `klasse` (`inKlasse`),
  KEY `lieblingsLehrer` (`lieblingsLehrer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `schueler`
--

INSERT INTO `schueler` (`id`, `name`, `inKlasse`, `lieblingsLehrer`) VALUES
(1, 'Heinz', 1, NULL),
(2, 'Peter', 1, 3),
(3, 'Franz', 2, NULL),
(4, 'Max', 1, NULL),
(5, 'John', 3, NULL),
(6, 'Erich', 4, NULL),
(12, 'Joerg', 4, 1),
(123, 'Boris', 4, NULL);

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `fach`
--
ALTER TABLE `fach`
  ADD CONSTRAINT `fach_ibfk_1` FOREIGN KEY (`unterrichtetVon`) REFERENCES `lehrer` (`id`);

--
-- Constraints der Tabelle `fach_schueler`
--
ALTER TABLE `fach_schueler`
  ADD CONSTRAINT `fach_schueler_ibfk_1` FOREIGN KEY (`fach`) REFERENCES `fach` (`id`),
  ADD CONSTRAINT `fach_schueler_ibfk_2` FOREIGN KEY (`schueler`) REFERENCES `schueler` (`id`);

--
-- Constraints der Tabelle `klasse`
--
ALTER TABLE `klasse`
  ADD CONSTRAINT `klasse_ibfk_1` FOREIGN KEY (`klassenlehrer`) REFERENCES `lehrer` (`id`);

--
-- Constraints der Tabelle `schueler`
--
ALTER TABLE `schueler`
  ADD CONSTRAINT `schueler_ibfk_2` FOREIGN KEY (`lieblingsLehrer`) REFERENCES `lehrer` (`id`),
  ADD CONSTRAINT `schueler_ibfk_1` FOREIGN KEY (`inKlasse`) REFERENCES `klasse` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
