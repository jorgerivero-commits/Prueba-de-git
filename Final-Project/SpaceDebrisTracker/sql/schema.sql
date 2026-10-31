-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS space_debris_db;
USE space_debris_db;

-- Tabla para almacenar los registros TLE crudos de la basura espacial
CREATE TABLE IF NOT EXISTS space_debris (
    id INT AUTO_INCREMENT PRIMARY KEY,
    norad_cat_id INT UNIQUE NOT NULL,       -- ID único del satélite/escombro asignado por NORAD
    object_name VARCHAR(100) NOT NULL,      -- Nombre del objeto (ej. COSMOS 2251 DEBRIS)
    tle_line1 VARCHAR(100) NOT NULL,        -- Línea 1 del TLE
    tle_line2 VARCHAR(100) NOT NULL,        -- Línea 2 del TLE
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla para almacenar el historial de coordenadas calculadas (por si quieres hacer consultas temporales)
CREATE TABLE IF NOT EXISTS orbital_positions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    norad_cat_id INT NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    altitude DOUBLE NOT NULL,               -- En kilómetros
    calculated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (norad_cat_id) REFERENCES space_debris(norad_cat_id) ON DELETE CASCADE
);

-- Tabla para alertas de reentrada o proximidad
CREATE TABLE IF NOT EXISTS reentry_alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    norad_cat_id INT NOT NULL,
    alert_message VARCHAR(255) NOT NULL,
    severity_level VARCHAR(50) NOT NULL,    -- Ejemplo: 'WARNING', 'CRITICAL'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (norad_cat_id) REFERENCES space_debris(norad_cat_id) ON DELETE CASCADE
);
