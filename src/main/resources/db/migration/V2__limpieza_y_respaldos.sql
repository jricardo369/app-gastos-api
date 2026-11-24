-- =====================================================================
-- V2: deja solo usuarios, membresias y respaldos.
-- Ejecuta sobre una BD creada con el init.sql anterior: elimina las tablas
-- del control de gastos que la web ya no usa y agrega la tabla de respaldos.
-- En una BD creada con el init.sql nuevo no hace nada (IF EXISTS / IF NOT EXISTS).
-- ADVERTENCIA: borra los datos de categorias, dashboard, ingresos y gastos.
-- =====================================================================

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS gastos;
DROP TABLE IF EXISTS ingresos;
DROP TABLE IF EXISTS dashboard;
DROP TABLE IF EXISTS categorias;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS respaldos (
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    id_user        BIGINT       NOT NULL,
    payload_json   LONGTEXT     NOT NULL,
    schema_version INT          NOT NULL,
    created_at     DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at     DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_respaldos PRIMARY KEY (id),
    CONSTRAINT uq_respaldos_usuario UNIQUE (id_user),
    CONSTRAINT fk_respaldos_usuario FOREIGN KEY (id_user) REFERENCES usuarios (id_user) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
