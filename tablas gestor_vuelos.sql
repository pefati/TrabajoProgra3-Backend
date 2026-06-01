CREATE DATABASE IF NOT EXISTS pf_prog3_gestor_aeropuerto
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE pf_prog3_gestor_aeropuerto;

CREATE TABLE aeropuerto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    codigo_iata CHAR(3) NOT NULL UNIQUE,
    ciudad VARCHAR(100) NOT NULL,
    pais VARCHAR(100) NOT NULL
);

CREATE TABLE avion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    identificador VARCHAR(50) NOT NULL UNIQUE,
    modelo VARCHAR(100) NOT NULL,
    capacidad_pasajeros INT NOT NULL,
    capacidad_bodega DECIMAL(10,2) NOT NULL,
    estado ENUM(
        'DISPONIBLE',
        'MANTENIMIENTO',
        'ACTIVO',
        'BAJA'
    ) NOT NULL
);

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    tipo_documento ENUM(
        'DNI',
        'PASAPORTE'
    ) NOT NULL,
    numero_documento VARCHAR(50) NOT NULL UNIQUE,
    sexo ENUM(
        'MASCULINO',
        'FEMENINO',
        'OTRO'
    ) NOT NULL
);

CREATE TABLE empleado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL
);

CREATE TABLE vuelo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    aeropuerto_origen_id INT NOT NULL,
    aeropuerto_destino_id INT NOT NULL,
    avion_id INT NOT NULL,
    fecha_salida DATETIME NOT NULL,
    fecha_llegada DATETIME NOT NULL,
    escala BOOLEAN NOT NULL DEFAULT FALSE,
    estado ENUM(
        'PROGRAMADO',
        'BOARDING',
        'ACTIVO',
        'CANCELADO',
        'REPROGRAMADO'
    ) NOT NULL,
    CONSTRAINT fk_vuelo_origen FOREIGN KEY (aeropuerto_origen_id) REFERENCES aeropuerto(id),
    CONSTRAINT fk_vuelo_destino FOREIGN KEY (aeropuerto_destino_id) REFERENCES aeropuerto(id),
    CONSTRAINT fk_vuelo_avion FOREIGN KEY (avion_id) REFERENCES avion(id)
);

CREATE TABLE asignacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vuelo_id INT NOT NULL,
    empleado_id INT NOT NULL,
    rol ENUM(
        'PILOTO',
        'COPILOTO',
        'AZAFATA'
    ) NOT NULL,
    CONSTRAINT fk_asignacion_vuelo FOREIGN KEY (vuelo_id) REFERENCES vuelo(id),
    CONSTRAINT fk_asignacion_empleado FOREIGN KEY (empleado_id) REFERENCES empleado(id));

CREATE TABLE equipaje (
    id INT AUTO_INCREMENT PRIMARY KEY,
    peso DECIMAL(8,2) NOT NULL,
    tipo ENUM(
        'MANO',
        'CABINA',
        'BODEGA'
    ) NOT NULL,
    estado ENUM(
        'DESPACHADO',
        'ENTREGADO',
        'DAÑADO',
        'EXTRAVIADO'
    ) NOT NULL
);

CREATE TABLE reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    fecha_reserva DATETIME NOT NULL,
    cantidad_pasajes INT NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    estado ENUM(
        'ACTIVO',
        'PROCESANDO',
        'BAJA'
    ) NOT NULL,
    CONSTRAINT fk_reserva_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);


CREATE TABLE pasaje (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    asiento VARCHAR(10) NOT NULL,
    clase ENUM(
        'TURISTA',
        'ECONOMICA',
        'BUSINESS',
        'PRIMERA'
    ) NOT NULL,
    vuelo_id INT NOT NULL,
    equipaje_id INT NOT NULL,
    reserva_id INT NOT NULL,
    CONSTRAINT fk_pasaje_vuelo FOREIGN KEY (vuelo_id) REFERENCES vuelo(id),
    CONSTRAINT fk_pasaje_equipaje FOREIGN KEY (equipaje_id) REFERENCES equipaje(id),
    CONSTRAINT fk_pasaje_reserva FOREIGN KEY (reserva_id) REFERENCES reserva(id)
);

CREATE TABLE factura (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reserva_id INT NOT NULL,
    situacion_fiscal VARCHAR(100) NOT NULL,
    cuil VARCHAR(20) NOT NULL,
    fecha_emision DATETIME NOT NULL,
    metodo_pago ENUM(
        'TARJETA_CREDITO',
        'TARJETA_DEBITO',
        'TRANSFERENCIA'
    ) NOT NULL, CONSTRAINT fk_factura_reserva FOREIGN KEY (reserva_id) REFERENCES reserva(id));

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT fk_usuario_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id));
