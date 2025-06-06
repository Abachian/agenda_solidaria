-- phpMyAdmin SQL Dump
-- version 4.4.11
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-04-2024 a las 20:43:39
-- Versión del servidor: 5.5.39
-- Versión de PHP: 5.4.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `agendasolidaria`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `document_type`
--

CREATE TABLE IF NOT EXISTS `document_type` (
  `iddocument` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`iddocument`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `event_type`
--

CREATE TABLE IF NOT EXISTS `event_type` (
  `idevent_type` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(125) NOT NULL,
  PRIMARY KEY (`idevent_type`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `organization_type`
--

CREATE TABLE IF NOT EXISTS `organization_type` (
  `idorganizationtype` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`idorganizationtype`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profession`
--

CREATE TABLE IF NOT EXISTS `profession` (
  `idprofession` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`idprofession`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `permisos` text,
  `enabled` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `login_index` (`username`,`password`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `organization`
--

CREATE TABLE IF NOT EXISTS `organization` (
  `idorganization` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `direction` varchar(100) NOT NULL,
  `gmap_cordinate` varchar(50) DEFAULT NULL COMMENT 'Coordenadas de Google Maps',
  `mail` varchar(45) NOT NULL,
  `publish_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `abstract` text NOT NULL COMMENT 'HTML',
  `telephones` varchar(45) DEFAULT NULL,
  `idorganizationtype` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `logo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idorganization`),
  KEY `fk_organization_1` (`idorganizationtype`),
  KEY `fk_organization_2` (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `volunteer`
--

CREATE TABLE IF NOT EXISTS `volunteer` (
  `idvolunteer` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `document` varchar(8) NOT NULL,
  `mail` varchar(45) NOT NULL,
  `iddocumenttype` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `telephones` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idvolunteer`),
  KEY `fk_volunteer_1` (`iddocumenttype`),
  KEY `fk_volunteer_2` (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `idevent` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(125) NOT NULL,
  `description` text NOT NULL,
  `intro` varchar(75) NOT NULL,
  `idevent_type` int(11) NOT NULL,
  `publish_date` datetime NOT NULL,
  `event_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `direction` varchar(100) NOT NULL,
  `gmap_cordinate` varchar(50) DEFAULT NULL COMMENT 'Almacena json con coordenadas obtenidas desde google maps',
  `mail` varchar(45) NOT NULL,
  `responsable` varchar(45) NOT NULL,
  `pressrelease` text,
  PRIMARY KEY (`idevent`),
  KEY `fk_event_1` (`idevent_type`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `event_organization`
--

CREATE TABLE IF NOT EXISTS `event_organization` (
  `ideventorganization` int(11) NOT NULL AUTO_INCREMENT,
  `idevent` int(11) NOT NULL,
  `idorganization` int(11) NOT NULL,
  PRIMARY KEY (`ideventorganization`),
  KEY `fk_event_organization_1` (`idevent`),
  KEY `fk_event_organization_2` (`idorganization`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `images`
--

CREATE TABLE IF NOT EXISTS `images` (
  `idimages` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  `image` blob NOT NULL,
  `idevent` int(11) NOT NULL,
  `mime` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idimages`),
  KEY `fk_images_1` (`idevent`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `need`
--

CREATE TABLE IF NOT EXISTS `need` (
  `idneed` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(65) NOT NULL,
  `description` text NOT NULL,
  `publish_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enabled` int(1) NOT NULL DEFAULT '1',
  `idorganization` int(11) NOT NULL,
  PRIMARY KEY (`idneed`),
  KEY `fk_need_organization` (`idorganization`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `volunteer_profession`
--

CREATE TABLE IF NOT EXISTS `volunteer_profession` (
  `idvolunteer_profession` int(11) NOT NULL AUTO_INCREMENT,
  `idvolunteer` int(11) NOT NULL,
  `idprofession` int(11) NOT NULL,
  PRIMARY KEY (`idvolunteer_profession`),
  KEY `fk_volunteer_profession_1` (`idprofession`),
  KEY `fk_volunteer_profession_2` (`idvolunteer`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `postulate`
--

CREATE TABLE IF NOT EXISTS `postulate` (
  `idpostulate` int(11) NOT NULL AUTO_INCREMENT,
  `acepted` int(1) NOT NULL DEFAULT '0',
  `iduser` int(11) NOT NULL,
  `idneed` int(11) NOT NULL,
  `postulated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpostulate`),
  KEY `fk_postulate_user` (`iduser`),
  KEY `fk_postulate_need` (`idneed`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rating`
--

CREATE TABLE IF NOT EXISTS `rating` (
  `idrating` int(11) NOT NULL AUTO_INCREMENT,
  `ratingorganization` int(11) DEFAULT NULL,
  `ratingvolunteer` int(11) DEFAULT NULL,
  `idpostulate` int(11) NOT NULL,
  PRIMARY KEY (`idrating`),
  KEY `fk_rating_postulate` (`idpostulate`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `organization`
--
ALTER TABLE `organization`
  ADD CONSTRAINT `fk_organization_1` FOREIGN KEY (`idorganizationtype`) REFERENCES `organization_type` (`idorganizationtype`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_organization_2` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `volunteer`
--
ALTER TABLE `volunteer`
  ADD CONSTRAINT `fk_volunteer_1` FOREIGN KEY (`iddocumenttype`) REFERENCES `document_type` (`iddocument`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_volunteer_2` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `fk_event_1` FOREIGN KEY (`idevent_type`) REFERENCES `event_type` (`idevent_type`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `event_organization`
--
ALTER TABLE `event_organization`
  ADD CONSTRAINT `fk_event_organization_1` FOREIGN KEY (`idevent`) REFERENCES `event` (`idevent`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_event_organization_2` FOREIGN KEY (`idorganization`) REFERENCES `organization` (`idorganization`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `fk_images_1` FOREIGN KEY (`idevent`) REFERENCES `event` (`idevent`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `need`
--
ALTER TABLE `need`
  ADD CONSTRAINT `fk_need_organization` FOREIGN KEY (`idorganization`) REFERENCES `organization` (`idorganization`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `volunteer_profession`
--
ALTER TABLE `volunteer_profession`
  ADD CONSTRAINT `fk_volunteer_profession_1` FOREIGN KEY (`idprofession`) REFERENCES `profession` (`idprofession`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_volunteer_profession_2` FOREIGN KEY (`idvolunteer`) REFERENCES `volunteer` (`idvolunteer`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `postulate`
--
ALTER TABLE `postulate`
  ADD CONSTRAINT `fk_postulate_need` FOREIGN KEY (`idneed`) REFERENCES `need` (`idneed`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_postulate_user` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `rating`
--
ALTER TABLE `rating`
  ADD CONSTRAINT `fk_rating_postulate` FOREIGN KEY (`idpostulate`) REFERENCES `postulate` (`idpostulate`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;