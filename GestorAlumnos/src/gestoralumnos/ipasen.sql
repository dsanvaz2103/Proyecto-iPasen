CREATE OR REPLACE USER fran@localhost IDENTIFIED BY 'Fr4n';
GRANT ALL PRIVILEGES ON  *.* to 'admin'@'localhost';
CREATE OR REPLACE DATABASE ipasen;
USE ipasen;
CREATE TABLE IF NOT EXISTS alumnos (
    dni VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    edad INT CHECK (edad >= 0),
    curso VARCHAR(50),
    nota_media DECIMAL(4,2) CHECK (nota_media BETWEEN 0 AND 10),