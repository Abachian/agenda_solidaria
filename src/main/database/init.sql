--
-- Base de datos: `agendasolidaria`
--

-- --------------------------------------------------------

CREATE TYPE document_type AS ENUM ('DNI', 'LC', 'LE');
CREATE TYPE item_type AS ENUM ('EVENT', 'ORGANIZATION', 'VOLUNTEER', 'NEED');
CREATE TYPE rol AS ENUM ('ADMINISTRATOR','INSTITUTION','VOLUNTEER');
--
-- Estructura de tabla para la tabla `event_type`
--

CREATE TABLE IF NOT EXISTS event_types (
  id_event_type BIGINT PRIMARY KEY,
  description VARCHAR NOT NULL
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `organization_type`
--

CREATE TABLE IF NOT EXISTS organization_types (
  id_organization_type BIGINT PRIMARY KEY,
  description VARCHAR NOT NULL
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profession`
--

CREATE TABLE IF NOT EXISTS professions (
  id_profession BIGINT PRIMARY KEY,
  description VARCHAR NOT NULL
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS users (
  id_user BIGINT PRIMARY KEY,
  username VARCHAR(45) NOT NULL UNIQUE,
  password VARCHAR(45) NOT NULL,
  first_name VARCHAR NOT NULL,
  last_name VARCHAR NOT NULL,
  rol rol NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  email VARCHAR NOT NULL,
  login_Intentos INTEGER
);


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `organization`
--

CREATE TABLE IF NOT EXISTS organizations (
  id_organization BIGINT PRIMARY KEY,
  description VARCHAR(45) NOT NULL,
  direction VARCHAR(100) NOT NULL,
  gmap_cordinate VARCHAR(50) DEFAULT NULL,
  publish_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  content TEXT NOT NULL,
  id_organization_type INTEGER NOT NULL,
  id_user INTEGER NOT NULL,
  logo VARCHAR(100) DEFAULT NULL,
  CONSTRAINT fk_organization_1 FOREIGN KEY (id_organization_type) REFERENCES organization_types (id_organization_type),
  CONSTRAINT fk_organization_2 FOREIGN KEY (id_user) REFERENCES users (id_user)
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `volunteer`
--

CREATE TABLE IF NOT EXISTS volunteers (
  id_volunteer BIGINT PRIMARY KEY,
  document VARCHAR(8) NOT NULL,
  document_type document_type NOT NULL,
  id_user INTEGER NOT NULL,
  CONSTRAINT fk_volunteer_2 FOREIGN KEY (id_user) REFERENCES users (id_user)
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `event`
--

CREATE TABLE IF NOT EXISTS events (
  id_event BIGINT PRIMARY KEY,
  title VARCHAR(125) NOT NULL,
  description TEXT NOT NULL,
  intro VARCHAR(75) NOT NULL,
  id_event_type INTEGER NOT NULL,
  publish_date TIMESTAMP NOT NULL,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  direction VARCHAR(100) NOT NULL,
  gmap_cordinate VARCHAR(50) DEFAULT NULL,
  pressrelease TEXT,
  CONSTRAINT fk_event_1 FOREIGN KEY (id_event_type) REFERENCES event_types (id_event_type)
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `event_organization`
--

CREATE TABLE IF NOT EXISTS event_organization (
  id_event BIGINT NOT NULL,
  id_organization BIGINT NOT NULL,
  PRIMARY KEY (id_event, id_organization),
  CONSTRAINT fk_event_organization_event FOREIGN KEY (id_event) REFERENCES events (id_event),
  CONSTRAINT fk_event_organization_organization FOREIGN KEY (id_organization) REFERENCES organizations (id_organization)
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `images`
--

CREATE TABLE IF NOT EXISTS images (
  id_image BIGINT PRIMARY KEY,
  description VARCHAR(45) DEFAULT NULL,
  url VARCHAR NOT NULL,
  item_type item_type NOT NULL,
  id_item INTEGER NOT NULL,
  image_type VARCHAR(45) DEFAULT NULL
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `need`
--

CREATE TABLE IF NOT EXISTS needs (
  id_need BIGINT PRIMARY KEY,
  description TEXT NOT NULL,
  publish_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  id_event INTEGER NOT NULL,
  CONSTRAINT fk_need_event FOREIGN KEY (id_event) REFERENCES events (id_event)
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `volunteer_profession`
--

CREATE TABLE IF NOT EXISTS volunteer_profession (
  id_volunteer_profession BIGINT PRIMARY KEY,
  id_volunteer INTEGER NOT NULL,
  id_profession INTEGER NOT NULL,
  CONSTRAINT fk_volunteer_profession_1 FOREIGN KEY (id_profession) REFERENCES professions (id_profession),
  CONSTRAINT fk_volunteer_profession_2 FOREIGN KEY (id_volunteer) REFERENCES volunteers (id_volunteer)
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `postulate`
--

CREATE TABLE IF NOT EXISTS postulates (
  id_postulate BIGINT PRIMARY KEY,
  acepted BOOLEAN NOT NULL DEFAULT FALSE,
  id_volunteer INTEGER NOT NULL,
  id_need INTEGER NOT NULL,
  postulated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_postulate_volunteer FOREIGN KEY (id_volunteer) REFERENCES volunteers (id_volunteer),
  CONSTRAINT fk_postulate_need FOREIGN KEY (id_need) REFERENCES needs (id_need)
);


CREATE TABLE IF NOT EXISTS phone_numbers (
   id_phone BIGINT PRIMARY KEY,
   id_user BIGINT NOT NULL REFERENCES users(id_user) ON DELETE CASCADE,
   type VARCHAR(50),
   number VARCHAR(20) NOT NULL
);
-- --------------------------------------------------------
--
-- Datos para las tablas
--

-- Inserts para event_type
INSERT INTO event_types (id_event_type, description) VALUES
(1, 'Correcaminata'),
(2, 'Desfile'),
(3, 'Concierto'),
(4, 'Taller'),
(5, 'Conferencia'),
(6, 'Festival'),
(7, 'Remate'),
(8, 'Campeonato'),
(9, 'Torneo'),
(10, 'Lotería'),
(11, 'Tallarinada'),
(12, 'Obra de Teatro'),
(13, 'Feria'),
(14, 'Colecta'),
(15, 'Maratón'),
(16, 'Espectáculo'),
(17, 'Inauguración'),
(18, 'Congreso'),
(19, 'Seminario'),
(20, 'Desayuno'),
(21, 'Almuerzo'),
(22, 'Cena'),
(23, 'Entrevista'),
(24, 'Cierre Anual'),
(25, 'Show'),
(29, 'social'),
(30, 'Prueba'),
(31, 'Evento de prueba'),
(33, 'Torneo de Golf "Educere"'),
(34, 'Copa Solidaria 2016'),
(35, 'Lanzamiento'),
(37, 'Expo');

-- Inserts para organization_type
INSERT INTO organization_types (id_organization_type, description) VALUES
(1, 'Fundación'),
(3, 'Asociación Civil'),
(5, 'Biblioteca Popular'),
(6, 'Asociación Cooperadora'),
(7, 'Asociación Sindical'),
(8, 'Asociación Profesional'),
(9, 'Club Social y Deportivo'),
(10, 'Sociedad de Fomento'),
(13, 'Junta de Acción Comunal'),
(14, 'Cooperativa'),
(15, 'Empresas B'),
(16, 'Otros'),
(17, 'Personería en trámite'),
(18, 'Unidad Académica'),
(20, 'Asociación de padres'),
(21, 'Federación');

-- Inserts para profession
INSERT INTO professions (id_profession, description) VALUES
(1, 'Médico'),
(2, 'Enfermero'),
(3, 'Psicólogo'),
(4, 'Docente'),
(5, 'Abogado'),
(6, 'Contador'),
(7, 'Ingeniero'),
(8, 'Arquitecto'),
(9, 'Programador'),
(10, 'Diseñador'),
(11, 'Administrativo'),
(12, 'Comunicador Social'),
(13, 'Trabajador Social'),
(14, 'Nutricionista'),
(15, 'Fisioterapeuta');

-- Inserts para user
-- 1) Recrear los usuarios con email tomado de los inserts de organizations
INSERT INTO users (
    id_user,
    username,
    password,
    first_name,
    last_name,
    rol,
    enabled,
    email
) VALUES
      (54,  'fundacionhospitalninos',   'password', 'Fundacion',      'Hospital Ninos',        'INSTITUTION', TRUE, 'fundacionhospitalninos@gmail.com'),
      (55,  'mariobaniles',             'password', 'Mario',          'Baniles',               'INSTITUTION', TRUE, 'mariobaniles@bat.org.ar'),
      (56,  'mesasolidariatandil',      'password', 'Mesa Solidaria', 'Tandil',                'INSTITUTION', TRUE, 'contacto@mesasolidariatandil.com.ar'),
      (58,  'janoportodos',             'password', 'Jano',           'Por Todos',             'INSTITUTION', TRUE, 'janoportodos@gmail.com'),
      (60,  'practicas.solidarias',     'password', 'Practicas',      'Solidarias',            'INSTITUTION', TRUE, 'practicas.solidarias@econ.unicen.edu.ar');



-- Inserts para organization
-- 2) Inserts actualizados para organizations (se quita la columna mail)
INSERT INTO organizations (
    id_organization,
    description,
    direction,
    gmap_cordinate,
    publish_date,
    content,
    id_organization_type,
    id_user,
    logo
) VALUES
      (54, 'FUNDACION DEL HOSPITAL DE NIÑOS DE TANDIL',
       'Leandro N. Alem 1350, Tandil, Buenos Aires Province, Argentina',
       '(-37.3164601, -59.139188200000035)',
       '2014-02-12 13:31:49',
       'Impulsar el desarrollo integral del Hospital de Niños de Tandil en todas sus areas bajo la supervicion de las autoridades de la Institucion',
       1, 54, ''),
      (55, 'Fundacion Banco de Alimentos Tandil',
       'Rosales 383, Tandil, Buenos Aires Province, Argentina',
       '(-37.3074308, -59.14522720000002)',
       '2017-02-21 14:17:08',
       'Funcionamos como un puente entre aquellos que sufren hambre y quienes desean colaborar a través de un canal transparente y eficiente que garantice que las donaciones llegarán a quienes más lo necesiten.',
       1, 55, ''),
      (56, 'Mesa Solidaria Tandil',
       'Tandil, Buenos Aires Province, Argentina',
       '(-37.3178101, -59.15039060000004)',
       '2013-12-12 14:12:29',
       'Representamos la unión de las Ongs y trabajamos articuladamente junto, a los sectores público y privado, sobre las acciones hacia la comunidad.',
       16, 56, ''),
      (58, 'Jano por Todos',
       'Pasteur 302, Tandil, Buenos Aires Province, Argentina',
       '(-37.3074308, -59.14522720000002)',
       '2013-12-12 15:07:58',
       'Jano Por Todos es una Asociación Civil cuya misión es la de ayudar a los niños oncohematológicos de Tandil y la región.',
       3, 58, ''),
      (60, 'Área de Vinculación Social y Práctica',
       'Paraje Arroyo Seco S/Nº',
       '',
       '2014-02-13 15:01:34',
       'Coordinación de proyectos sociales en vinculación con el territorio.',
       16, 60, '');

-- Inserts para volunteer
INSERT INTO volunteers (
    id_volunteer,
    document,
    document_type,
    id_user
) VALUES
      (1, '12345678', 'DNI', 54),
      (2, '87654321', 'DNI', 55),
      (3, '45678912', 'DNI', 56),
      (4, '78912345', 'DNI', 58),
      (5, '32165498', 'DNI', 60);

-- Inserts para event
INSERT INTO events (id_event, title, description, intro, id_event_type, publish_date, start_date, end_date , direction, gmap_cordinate, pressrelease) VALUES
(1, 'Maratón Solidaria', 'Maratón benéfica para recaudar fondos para el hospital de niños', 'Maratón solidaria', 1, '2024-03-20 10:00:00', '2024-03-25 10:00:00','2024-03-25 10:00:00', 'Parque Municipal, Tandil', '(-37.3164601, -59.139188200000035)',  'Maratón solidaria para recaudar fondos'),
(2, 'Concierto Benéfico', 'Concierto a beneficio de la fundación', 'Concierto benéfico', 3, '2024-03-21 10:00:00', '2024-03-26 10:00:00','2024-03-25 10:00:00', 'Teatro Municipal, Tandil', '(-37.3074308, -59.14522720000002)',  'Concierto benéfico a beneficio'),
(3, 'Taller de Nutrición', 'Taller sobre alimentación saludable', 'Taller nutrición', 4, '2024-03-22 10:00:00', '2024-03-27 10:00:00','2024-03-25 10:00:00', 'Centro Comunitario, Tandil', '(-37.3178101, -59.15039060000004)',  'Taller de nutrición para la comunidad');

-- Inserts para event_organization
INSERT INTO event_organization (id_event, id_organization) VALUES
(1, 54),
(1, 55),
(2, 56),
(2, 58),
(3, 60);

-- Inserts para images
INSERT INTO images (
    id_image,
    description,
    url,
    item_type,
    id_item,
    image_type
) VALUES
      (1, 'Imagen del evento 1', 'evento1.png',  'EVENT', 1, 'image/png'),
      (2, 'Imagen del evento 2', 'evento2.png',  'EVENT', 2, 'image/png'),
      (3, 'Imagen del evento 3', 'evento3.png',  'EVENT', 3, 'image/png');

-- Inserts para need
INSERT INTO needs (id_need, title, description, publish_date, enabled, id_organization) VALUES
(1, 'Necesitamos médicos', 'Se necesitan médicos para la maratón', '2024-03-20 10:00:00', TRUE, 54),
(2, 'Necesitamos enfermeros', 'Se necesitan enfermeros para el concierto', '2024-03-21 10:00:00', TRUE, 55),
(3, 'Necesitamos psicólogos', 'Se necesitan psicólogos para el taller', '2024-03-22 10:00:00', TRUE, 56);

-- Inserts para volunteer_profession
INSERT INTO volunteer_profession (idvolunteer_profession, idvolunteer, idprofession) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 3),
(4, 2, 4),
(5, 3, 5),
(6, 3, 6),
(7, 4, 7),
(8, 4, 8),
(9, 5, 9),
(10, 5, 10);

-- Inserts para postulate
INSERT INTO postulates (id_postulate, acepted, id_user, id_need, postulated) VALUES
(1, TRUE, 54, 1, '2024-03-20 10:00:00'),
(2, FALSE, 55, 2, '2024-03-21 10:00:00'),
(3, TRUE, 56, 3, '2024-03-22 10:00:00'),
(4, FALSE, 58, 1, '2024-03-23 10:00:00'),
(5, TRUE, 60, 2, '2024-03-24 10:00:00');

