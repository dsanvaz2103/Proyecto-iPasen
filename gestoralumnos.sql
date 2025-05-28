DROP DATABASE IF exists gestoralumnos;
CREATE DATABASE IF NOT EXISTS gestoralumnos;
USE gestoralumnos;

CREATE TABLE IF NOT EXISTS alumnos (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    edad INT,
    curso VARCHAR(100),
    nota_media DOUBLE,
    horario TEXT,
    agenda TEXT,
    faltas TEXT
);
