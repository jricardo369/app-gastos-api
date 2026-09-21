-- Migration V1: Create all tables and seed data
-- Usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BINARY(16) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_usuarios_email ON usuarios(email);

-- Hogares
CREATE TABLE IF NOT EXISTS hogares (
    id BINARY(16) PRIMARY KEY,
    user_id BINARY(16) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    moneda CHAR(3) NOT NULL DEFAULT 'MXN',
    zona_horaria VARCHAR(100) NOT NULL DEFAULT 'America/Mexico_City',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES usuarios(id)
);

CREATE INDEX idx_hogares_user_id ON hogares(user_id);

-- Categorias
CREATE TABLE IF NOT EXISTS categorias (
    id BINARY(16) PRIMARY KEY,
    hogar_id BINARY(16) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    color VARCHAR(20) NOT NULL,
    icono VARCHAR(100),
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hogar_id) REFERENCES hogares(id)
);

CREATE INDEX idx_categorias_hogar_id ON categorias(hogar_id);
CREATE INDEX idx_categorias_estado ON categorias(estado);

-- Presupuestos
CREATE TABLE IF NOT EXISTS presupuestos (
    id BINARY(16) PRIMARY KEY,
    hogar_id BINARY(16) NOT NULL,
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

CREATE INDEX idx_presupuestos_hogar_id ON presupuestos(hogar_id);

-- Ingresos
CREATE TABLE IF NOT EXISTS ingresos (
    id BINARY(16) PRIMARY KEY,
    presupuesto_id BINARY(16) NOT NULL,
    quincena VARCHAR(3) NOT NULL,
    concepto VARCHAR(255) NOT NULL,
    monto DECIMAL(15,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (presupuesto_id) REFERENCES presupuestos(id)
);

CREATE INDEX idx_ingresos_presupuesto_id ON ingresos(presupuesto_id);
CREATE INDEX idx_ingresos_quincena ON ingresos(quincena);

-- Gastos
CREATE TABLE IF NOT EXISTS gastos (
    id BINARY(16) PRIMARY KEY,
    presupuesto_id BINARY(16) NOT NULL,
    quincena VARCHAR(3) NOT NULL,
    categoria_id BINARY(16) NOT NULL,
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

CREATE INDEX idx_gastos_presupuesto_id ON gastos(presupuesto_id);
CREATE INDEX idx_gastos_quincena ON gastos(quincena);
CREATE INDEX idx_gastos_estado_pago ON gastos(estado_pago);
CREATE INDEX idx_gastos_tipo ON gastos(tipo);
CREATE INDEX idx_gastos_fecha ON gastos(fecha);

-- Imprevistos
CREATE TABLE IF NOT EXISTS imprevistos (
    id BINARY(16) PRIMARY KEY,
    presupuesto_id BINARY(16) NOT NULL,
    quincena VARCHAR(3) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    importe DECIMAL(15,2) NOT NULL,
    fecha DATE NOT NULL,
    estado_pago VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (presupuesto_id) REFERENCES presupuestos(id)
);

CREATE INDEX idx_imprevistos_presupuesto_id ON imprevistos(presupuesto_id);

-- Deudas
CREATE TABLE IF NOT EXISTS deudas (
    id BINARY(16) PRIMARY KEY,
    hogar_id BINARY(16) NOT NULL,
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

CREATE INDEX idx_deudas_hogar_id ON deudas(hogar_id);
CREATE INDEX idx_deudas_estado ON deudas(estado);

-- Pagos de deuda
CREATE TABLE IF NOT EXISTS pagos_deuda (
    id BINARY(16) PRIMARY KEY,
    deuda_id BINARY(16) NOT NULL,
    importe DECIMAL(15,2) NOT NULL,
    fecha DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (deuda_id) REFERENCES deudas(id)
);

CREATE INDEX idx_pagos_deuda_deuda_id ON pagos_deuda(deuda_id);

-- Tarjetas
CREATE TABLE IF NOT EXISTS tarjetas (
    id BINARY(16) PRIMARY KEY,
    hogar_id BINARY(16) NOT NULL,
    titular VARCHAR(255) NOT NULL,
    numero VARCHAR(100),
    banco VARCHAR(100),
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hogar_id) REFERENCES hogares(id)
);

CREATE INDEX idx_tarjetas_hogar_id ON tarjetas(hogar_id);

-- Movimientos de tarjeta
CREATE TABLE IF NOT EXISTS movimientos_tarjeta (
    id BINARY(16) PRIMARY KEY,
    tarjeta_id BINARY(16) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    fecha_compra DATE NOT NULL,
    fecha_corte DATE,
    importe DECIMAL(15,2) NOT NULL,
    tipo VARCHAR(20) NOT NULL DEFAULT 'COMPRA',
    categoria_id BINARY(16),
    cuotas INT,
    cuota_actual INT,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (tarjeta_id) REFERENCES tarjetas(id),
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE INDEX idx_movimientos_tarjeta_id ON movimientos_tarjeta(tarjeta_id);
CREATE INDEX idx_movimientos_tarjeta_tipo ON movimientos_tarjeta(tipo);
CREATE INDEX idx_movimientos_tarjeta_estado ON movimientos_tarjeta(estado);
CREATE INDEX idx_movimientos_tarjeta_fecha ON movimientos_tarjeta(fecha_compra);