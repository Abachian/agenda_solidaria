-- Tabla de tipos de documento
CREATE TABLE document_type (
  iddocument INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(45) NOT NULL
);

-- Tabla de tipos de organización
CREATE TABLE organization_type (
  idorganizationtype INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(45) NOT NULL
);

-- Tabla de tipos de evento
CREATE TABLE event_type (
  idevent_type INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(125) NOT NULL
);

-- Tabla de profesiones
CREATE TABLE profession (
  idprofession INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(50) NOT NULL
);

-- Tabla de usuarios
CREATE TABLE user (
  iduser INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL UNIQUE,
  password VARCHAR(45) NOT NULL,
  permisos TEXT,
  enabled INT DEFAULT 1,
  INDEX login_index (username, password) USING HASH
);

-- Tabla de voluntarios
CREATE TABLE volunteer (
  idvolunteer INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  lastname VARCHAR(45) NOT NULL,
  document VARCHAR(8) NOT NULL,
  mail VARCHAR(45) NOT NULL,
  iddocumenttype INT NOT NULL,
  iduser INT NOT NULL,
  telephones VARCHAR(150),
  FOREIGN KEY (iddocumenttype) REFERENCES document_type(iddocument),
  FOREIGN KEY (iduser) REFERENCES user(iduser) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla de relación voluntario-profesión
CREATE TABLE volunteer_profession (
  idvolunteer_profession INT PRIMARY KEY AUTO_INCREMENT,
  idvolunteer INT NOT NULL,
  idprofession INT NOT NULL,
  FOREIGN KEY (idprofession) REFERENCES profession(idprofession),
  FOREIGN KEY (idvolunteer) REFERENCES volunteer(idvolunteer) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla de organizaciones
CREATE TABLE organization (
  idorganization INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(45) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  direction VARCHAR(100) NOT NULL,
  gmap_cordinate VARCHAR(50),
  mail VARCHAR(45) NOT NULL,
  publish_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  abstract TEXT NOT NULL COMMENT 'HTML',
  telephones VARCHAR(45),
  idorganizationtype INT NOT NULL,
  iduser INT NOT NULL,
  logo VARCHAR(100),
  FOREIGN KEY (idorganizationtype) REFERENCES organization_type(idorganizationtype),
  FOREIGN KEY (iduser) REFERENCES user(iduser) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla de eventos
CREATE TABLE event (
  idevent INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(125) NOT NULL,
  description TEXT NOT NULL,
  intro VARCHAR(75) NOT NULL,
  idevent_type INT NOT NULL,
  publish_date DATETIME NOT NULL,
  event_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  direction VARCHAR(100) NOT NULL,
  gmap_cordinate VARCHAR(50) COMMENT 'Almacena json con coordenadas obtenidas desde google maps',
  mail VARCHAR(45) NOT NULL,
  responsable VARCHAR(45) NOT NULL,
  pressrelease TEXT,
  FOREIGN KEY (idevent_type) REFERENCES event_type(idevent_type)
);

-- Tabla de relación evento-organización
CREATE TABLE event_organization (
  ideventorganization INT PRIMARY KEY AUTO_INCREMENT,
  idevent INT NOT NULL,
  idorganization INT NOT NULL,
  FOREIGN KEY (idevent) REFERENCES event(idevent) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (idorganization) REFERENCES organization(idorganization) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla de imágenes de eventos
CREATE TABLE images (
  idimages INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(45),
  image BLOB NOT NULL,
  idevent INT NOT NULL,
  mime VARCHAR(45),
  name VARCHAR(45),
  FOREIGN KEY (idevent) REFERENCES event(idevent) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla de necesidades
CREATE TABLE need (
  idneed INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(65) NOT NULL,
  description TEXT NOT NULL,
  publish_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enabled INT DEFAULT 1,
  idorganization INT NOT NULL,
  FOREIGN KEY (idorganization) REFERENCES organization(idorganization) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla de postulaciones
CREATE TABLE postulate (
  idpostulate INT PRIMARY KEY AUTO_INCREMENT,
  acepted INT DEFAULT 0,
  iduser INT NOT NULL,
  idneed INT NOT NULL,
  postulated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (idneed) REFERENCES need(idneed) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (iduser) REFERENCES user(iduser) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla de calificaciones
CREATE TABLE rating (
  idrating INT PRIMARY KEY AUTO_INCREMENT,
  ratingorganization INT,
  ratingvolunteer INT,
  idpostulate INT NOT NULL,
  FOREIGN KEY (idpostulate) REFERENCES postulate(idpostulate) ON DELETE CASCADE ON UPDATE CASCADE
);