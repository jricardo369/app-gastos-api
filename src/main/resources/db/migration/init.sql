-- =====================================================================
-- Gasto Hogar API - Script unico de base de datos
-- Base de datos: seguimiento_gastos
--
-- Ejecutar como root:
--   mysql -u root -p < src/main/resources/db/migration/init.sql
--
-- Crea la base (si no existe) y las unicas tres tablas que usa la API:
-- usuarios, membresias y respaldos.
-- El usuario de conexion de la API debe existir previamente y tener permisos
-- sobre seguimiento_gastos (ver la configuracion externa de cada entorno).
--
-- Es reejecutable: elimina las tablas existentes (en orden seguro) y las
-- vuelve a crear. Esto borra los datos previos.
-- =====================================================================

CREATE DATABASE IF NOT EXISTS seguimiento_gastos
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE seguimiento_gastos;
SET NAMES utf8mb4;

-- ---------------------------------------------------------------------
-- Limpieza (orden hijo -> padre) para poder reejecutar el script
-- ---------------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS respaldos;
DROP TABLE IF EXISTS membresias;
DROP TABLE IF EXISTS usuarios;

SET FOREIGN_KEY_CHECKS = 1;

-- ---------------------------------------------------------------------
-- Usuarios y membresias
-- ---------------------------------------------------------------------

CREATE TABLE usuarios (
    id_user    BIGINT       NOT NULL AUTO_INCREMENT,
    `user`     VARCHAR(50)  NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `name`     VARCHAR(120) NOT NULL,
    `address`  VARCHAR(200) NULL,
    `phone`    VARCHAR(20)  NULL,
    `email`    VARCHAR(120) NOT NULL,
    created_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_usuarios PRIMARY KEY (id_user),
    CONSTRAINT uq_usuarios_user UNIQUE (`user`),
    CONSTRAINT uq_usuarios_email UNIQUE (`email`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE membresias (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    id_user      BIGINT       NOT NULL,
    `type`       VARCHAR(10)  NOT NULL,
    fecha_inicio DATE         NOT NULL,
    fecha_fin    DATE         NOT NULL,
    estado       VARCHAR(20)  NOT NULL DEFAULT 'ACTIVA',
    created_at   DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at   DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_membresias PRIMARY KEY (id),
    CONSTRAINT fk_membresias_usuario FOREIGN KEY (id_user) REFERENCES usuarios (id_user) ON DELETE CASCADE,
    CONSTRAINT chk_membresias_type CHECK (`type` IN ('MONTH', 'YEAR')),
    CONSTRAINT chk_membresias_estado CHECK (estado IN ('ACTIVA', 'INACTIVA')),
    KEY idx_membresias_usuario (id_user),
    KEY idx_membresias_vigencia (id_user, estado, fecha_inicio, fecha_fin)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- Respaldo del estado de la app web (un JSON por usuario)
-- ---------------------------------------------------------------------

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

SELECT 'Base de datos seguimiento_gastos inicializada correctamente!' AS mensaje;
