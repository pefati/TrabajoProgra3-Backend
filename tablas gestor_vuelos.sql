CREATE DATABASE pf_prog3_gestorAeropuerto;
USE pf_prog3_gestorAeropuerto;
DROP DATABASE  pf_prog3_gestorAeropuerto;

CREATE TABLE aerolinea (
aerolinea_id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR (100),
codigo_iata VARCHAR (5)
);

CREATE TABLE pista (
pista_id INT AUTO_INCREMENT PRIMARY KEY,
numero INT NOT NULL,
estado ENUM ('LIBRE', 'OCUPADA', 'MANTENIMIENTO')
);

CREATE TABLE aeropuerto (
aeropuerto_id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR (200),
codigo_iata VARCHAR (5),
ciudad VARCHAR (150),
pais VARCHAR (100)
);

CREATE TABLE puerta_embarque (
puerta_embarque_id INT AUTO_INCREMENT PRIMARY KEY,
numero INT,
aeropuerto_id INT,

FOREIGN KEY (aeropuerto_id) REFERENCES aeropuerto (aeropuerto_id)
);

CREATE TABLE cliente (
cliente_id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR (100),
apellido VARCHAR(100),
mail VARCHAR (100),
telefono VARCHAR (20),
identificador ENUM ('PASAPORTE', 'DNI'),
sexo ENUM ('FEMENINO', 'MASCULINO', 'OTRO')
);

CREATE TABLE empleado (
empleado_id INT AUTO_INCREMENT PRIMARY KEY,
fecha_nacimiento DATE, 
nombre VARCHAR (100),
apellido VARCHAR (100)
);

CREATE TABLE avion (

avion_id INT AUTO_INCREMENT PRIMARY KEY,
identificador VARCHAR(100),
capacidad_tanque DECIMAL (10,7),
capacidad_pasajeros INT,
modelo VARCHAR (50),
aerolinea_id INT,
estado ENUM ('DISPONIBLE', 'MANTENIMIENTO', 'ACTIVO', 'BAJA'),

FOREIGN KEY (aerolinea_id) REFERENCES aerolinea (aerolinea_id)
);

CREATE TABLE vuelo (
vuelo_id INT AUTO_INCREMENT PRIMARY KEY,
aeropuerto_origen_id INT NOT NULL,
aeropuerto_destino_id INT NOT NULL,
avion_id INT NOT NULL,
pista_id INT NOT NULL,
fecha_salida DATE,
fecha_llegada DATE,
hora_salida TIME,
hora_salida_real TIME,
hora_llegada TIME,
hora_llegada_real TIME,
estado ENUM ('PROGRAMADO', 'BOARDING', 'ACTIVO', 'CANCELADO', 'REPROGRAMADO'),
puerta_embarque_id int,
escala BOOLEAN DEFAULT 0,
FOREIGN KEY (pista_id) REFERENCES pista(pista_id),
FOREIGN KEY (aeropuerto_origen_id) REFERENCES aeropuerto (aeropuerto_id),
FOREIGN KEY (aeropuerto_destino_id) REFERENCES aeropuerto (aeropuerto_id),
FOREIGN KEY (avion_id) REFERENCES avion (avion_id),
FOREIGN KEY (puerta_embarque_id) REFERENCES puerta_embarque (puerta_embarque_id)
);

CREATE TABLE reserva (
reserva_id INT AUTO_INCREMENT PRIMARY KEY,
cliente_id INT,
vuelo_id INT,
valor DECIMAL (15,7),
cantidad_pasajes INT,
clase ENUM ('ECONOMICA', 'TURISTA', 'BUSINESS', 'PRIMERA'),
estado ENUM('ACTIVO', 'BAJA', 'PROCESANDO'),
numero_ticket VARCHAR (30),
fecha_reserva DATETIME,

FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id),
FOREIGN KEY (vuelo_id) REFERENCES vuelo (vuelo_id)
);

CREATE TABLE asignacion( -- tabla vueloxempleado
asignacion_id INT AUTO_INCREMENT PRIMARY KEY,
vuelo_id INT,
empleado_id INT,
rolEnVuelo ENUM ('AZAFATA', 'PILOTO', 'COPILOTO'),

FOREIGN KEY (vuelo_id) REFERENCES vuelo (vuelo_id),
FOREIGN KEY (empleado_id) REFERENCES empleado (empleado_id)
);

CREATE TABLE equipaje (
equipaje_id INT AUTO_INCREMENT PRIMARY KEY,
reserva_id INT,
peso DECIMAL (10,7),
tipo ENUM ('CABINA', 'BODEGA', 'MANO'),
estado ENUM ('DESPACHADO', 'ENTREGADO', 'DAÑADO', 'EXTRAVIADO'),

FOREIGN KEY (reserva_id) REFERENCES reserva (reserva_id)
);

CREATE TABLE factura (
factura_id INT AUTO_INCREMENT PRIMARY KEY,
reserva_id INT, 
fecha_pago DATE, 
metodo_pago ENUM ('CREDITO', 'DEBITO', 'CRIPTOMONEDA', 'TRANSFERENCIA', 'PAYPAL', 'OTRO'),
situacion_fiscal VARCHAR (100),
CUIL VARCHAR (20),
FOREIGN KEY (reserva_id) REFERENCES reserva(reserva_id)
);
