-- Script para crear la base de datos y usuario
-- Ejecutar esto en MySQL primero:
-- mysql -u root -p < setup-database.sql

CREATE DATABASE IF NOT EXISTS gasto_hogar CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'gasto_user'@'localhost' IDENTIFIED BY 'gasto_password';
GRANT ALL PRIVILEGES ON gasto_hogar.* TO 'gasto_user'@'localhost';
FLUSH PRIVILEGES;

-- Si no usas Docker, las tablas se crean automáticamente con Flyway al iniciar la app.
-- Si quieres crearlas manualmente sin Flyway, descomenta las siguientes líneas:

USE gasto_hogar;

CREATE TABLE IF NOT EXISTS usuarios (
    id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS hogares (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    moneda CHAR(3) NOT NULL DEFAULT 'MXN',
    zona_horaria VARCHAR(100) NOT NULL DEFAULT 'America/Mexico_City',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS categorias (
    id VARCHAR(36) PRIMARY KEY,
    hogar_id VARCHAR(36) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    color VARCHAR(20) NOT NULL,
    icono VARCHAR(100),
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hogar_id) REFERENCES hogares(id)
);

CREATE TABLE IF NOT EXISTS presupuestos (
    id VARCHAR(36) PRIMARY KEY,
    hogar_id VARCHAR(36) NOT NULL,
    anio INT NOT NULL,
    mes INT NOT NULL,
    efectivo_q1 DECIMAL(15,2) NOT NULL DEFAULT 0,
    vales_q1 DECIMAL(15,2) NOT NULL DEFAULT 0,
    nomina_q1 DECIMAL(15,2) NOT NULL DEFAULT 0,
    efectivo_q2 DECIMAL(15,2) NOT NULL DEFAULT 0,
    vales_q2 DECIMAL(15,2) NOT NULL DEFAULT 0,
    nomina_q2 DECIMAL(15,2) NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hogar_id) REFERENCES hogares(id),
    UNIQUE(hogar_id, anio, mes)
);

CREATE TABLE IF NOT EXISTS ingresos (
    id VARCHAR(36) PRIMARY KEY,
    presupuesto_id VARCHAR(36) NOT NULL,
    quincena VARCHAR(3) NOT NULL,
    concepto VARCHAR(255) NOT NULL,
    monto DECIMAL(15,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (presupuesto_id) REFERENCES presupuestos(id)
);

CREATE TABLE IF NOT EXISTS gastos (
    id VARCHAR(36) PRIMARY KEY,
    presupuesto_id VARCHAR(36) NOT NULL,
    quincena VARCHAR(3) NOT NULL,
    categoria_id VARCHAR(36) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    importe DECIMAL(15,2) NOT NULL,
    fecha DATE NOT NULL,
    tipo VARCHAR(20) NOT NULL DEFAULT 'FIJO',
    estado_pago VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    observaciones TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (presupuesto_id) REFERENCES presupuestos(id),
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE IF NOT EXISTS imprevistos (
    id VARCHAR(36) PRIMARY KEY,
    presupuesto_id VARCHAR(36) NOT NULL,
    quincena VARCHAR(3) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    importe DECIMAL(15,2) NOT NULL,
    fecha DATE NOT NULL,
    estado_pago VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (presupuesto_id) REFERENCES presupuestos(id)
);

CREATE TABLE IF NOT EXISTS deudas (
    id VARCHAR(36) PRIMARY KEY,
    hogar_id VARCHAR(36) NOT NULL,
    deudor VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    importe_total DECIMAL(15,2) NOT NULL,
    importe_pagado DECIMAL(15,2) NOT NULL DEFAULT 0,
    saldo_pendiente DECIMAL(15,2) NOT NULL,
    fecha_vencimiento DATE,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hogar_id) REFERENCES hogares(id)
);

CREATE TABLE IF NOT EXISTS pagos_deuda (
    id VARCHAR(36) PRIMARY KEY,
    deuda_id VARCHAR(36) NOT NULL,
    importe DECIMAL(15,2) NOT NULL,
    fecha DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (deuda_id) REFERENCES deudas(id)
);

CREATE TABLE IF NOT EXISTS tarjetas (
    id VARCHAR(36) PRIMARY KEY,
    hogar_id VARCHAR(36) NOT NULL,
    titular VARCHAR(255) NOT NULL,
    numero VARCHAR(100),
    banco VARCHAR(100),
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hogar_id) REFERENCES hogares(id)
);

CREATE TABLE IF NOT EXISTS movimientos_tarjeta (
    id VARCHAR(36) PRIMARY KEY,
    tarjeta_id VARCHAR(36) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    fecha_compra DATE NOT NULL,
    fecha_corte DATE,
    importe DECIMAL(15,2) NOT NULL,
    tipo VARCHAR(20) NOT NULL DEFAULT 'COMPRA',
    categoria_id VARCHAR(36),
    cuotas INT,
    cuota_actual INT,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (tarjeta_id) REFERENCES tarjetas(id),
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_hogares_user_id ON hogares(user_id);
CREATE INDEX idx_categorias_hogar_id ON categorias(hogar_id);
CREATE INDEX idx_presupuestos_hogar_id ON presupuestos(hogar_id);
CREATE INDEX idx_gastos_quincena ON gastos(quincena);
CREATE INDEX idx_deudas_hogar_id ON deudas(hogar_id);
CREATE INDEX idx_tarjetas_hogar_id ON tarjetas(hogar_id);
CREATE INDEX idx_movimientos_tarjeta_id ON movimientos_tarjeta(tarjeta_id);

SELECT 'Base de datos creada exitosamente!' AS mensaje;
