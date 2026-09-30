-- 1. Desactivar chequeo de claves foráneas para permitir limpieza
SET FOREIGN_KEY_CHECKS = 0;

-- 2. Vaciar tablas en caso de reinicios para evitar duplicación de claves primarias
TRUNCATE TABLE transacciones;
TRUNCATE TABLE cuentas_titulares;
TRUNCATE TABLE cajas_ahorro;
TRUNCATE TABLE cuentas_corrientes;
TRUNCATE TABLE cuentas_financieras;
TRUNCATE TABLE clientes;

-- 3. Reactivar chequeo de integridad
SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================================
-- 1. INSERCIÓN DE CLIENTES
-- =============================================================================
INSERT INTO clientes (id, nombre_razon_social, cuil, email, fecha_creacion, fecha_modificacion) VALUES
                                                                                                    (1, 'Juan Pérez', '20301112229', 'juan.perez@email.com', NOW(), NOW()),
                                                                                                    (2, 'María Gómez', '27312223334', 'maria.gomez@email.com', NOW(), NOW()),
                                                                                                    (3, 'Carlos López', '20323334445', 'carlos.lopez@email.com', NOW(), NOW()),
                                                                                                    (4, 'Ana Martínez', '27334445556', 'ana.martinez@email.com', NOW(), NOW()),
                                                                                                    (5, 'Luis Rodríguez', '20345556667', 'luis.rodriguez@email.com', NOW(), NOW()),
                                                                                                    (6, 'Sofía Fernández', '27356667778', 'sofia.fernandez@email.com', NOW(), NOW()),
                                                                                                    (7, 'Diego Sánchez', '20367778889', 'diego.sanchez@email.com', NOW(), NOW()),
                                                                                                    (8, 'Laura Pérez', '27378889990', 'laura.perez@email.com', NOW(), NOW()),
                                                                                                    (9, 'Martín González', '20389990001', 'martin.gonzalez@email.com', NOW(), NOW()),
                                                                                                    (10, 'Lucía Romero', '27390001112', 'lucia.romero@email.com', NOW(), NOW());

-- =============================================================================
-- 2. INSERCIÓN DE CUENTAS FINANCIERAS (Tabla Base)
-- =============================================================================
-- =============================================================================
-- 2. INSERCIÓN DE CUENTAS FINANCIERAS (Tabla Base con su Cliente Titular)
-- =============================================================================
INSERT INTO cuentas_financieras (id, cbu, alias, saldo_operativo, estado, cliente_id, fecha_creacion, fecha_modificacion) VALUES
                                                                                                                              (1, '0000003100000000000001', 'JUAN.PEREZ.ARS', 150000.50, 'ACTIVA', 1, NOW(), NOW()),
                                                                                                                              (2, '0000003100000000000002', 'JUAN.PEREZ.USD', 85000.00, 'ACTIVA', 1, NOW(), NOW()),
                                                                                                                              (3, '0000003100000000000003', 'MARIA.GOMEZ.ARS', 320000.75, 'ACTIVA', 2, NOW(), NOW()),
                                                                                                                              (4, '0000003100000000000004', 'CARLOS.LOPEZ.ARS', 45000.00, 'SUSPENDIDA', 3, NOW(), NOW()),
                                                                                                                              (5, '0000003100000000000005', 'ANA.MARTINEZ.ARS', 920000.00, 'ACTIVA', 4, NOW(), NOW()),
                                                                                                                              (6, '0000003100000000000006', 'LUIS.RODRIGUEZ.ARS', 12500.20, 'BLOQUEADA', 5, NOW(), NOW()),
                                                                                                                              (7, '0000003100000000000007', 'SOFIA.FERNANDEZ.ARS', 67000.00, 'ACTIVA', 6, NOW(), NOW()),
                                                                                                                              (8, '0000003100000000000008', 'DIEGO.SANCHEZ.ARS', 540000.10, 'ACTIVA', 7, NOW(), NOW()),
                                                                                                                              (9, '0000003100000000000009', 'LAURA.PEREZ.ARS', 23000.00, 'ACTIVA', 8, NOW(), NOW()),
                                                                                                                              (10, '0000003100000000000010', 'MARTIN.GONZALEZ.ARS', 115000.80, 'ACTIVA', 9, NOW(), NOW()),
                                                                                                                              (11, '0000003100000000000011', 'LUCIA.ROMERO.ARS', 890000.00, 'ACTIVA', 10, NOW(), NOW());

-- =============================================================================
-- 3. INSERCIÓN DE TABLAS HIJAS (Estrategia JOINED)
-- =============================================================================
-- Cajas de Ahorro (Cuentas 1 a 6)
INSERT INTO cajas_ahorro (id, interes_anual, cupo_extraccion) VALUES
                                                                  (1, 40.00, 5),
                                                                  (2, 2.50, 3),
                                                                  (3, 40.00, 5),
                                                                  (4, 38.00, 4),
                                                                  (5, 45.00, 10),
                                                                  (6, 35.00, 2);

-- Cuentas Corrientes (Cuentas 7 a 11 con margen y mantenimiento)
INSERT INTO cuentas_corrientes (id, margen, mantenimiento) VALUES
                                                               (7, 100000.00, 2500.00),
                                                               (8, 250000.00, 3500.00),
                                                               (9, 50000.00, 1500.00),
                                                               (10, 80000.00, 2000.00),
                                                               (11, 300000.00, 4000.00);

-- =============================================================================
-- 4. CO-TITULARIDAD DE CUENTAS (Tabla Intermedia cuentas_titulares)
-- =============================================================================
INSERT INTO cuentas_titulares (cuenta_id, cliente_id) VALUES
                                                          (1, 1),
                                                          (2, 1),
                                                          (3, 2),
                                                          (4, 3),
                                                          (5, 4),
                                                          (6, 5),
                                                          (7, 6),
                                                          (8, 7),
                                                          (9, 8),
                                                          (10, 9),
                                                          (11, 10);

-- =============================================================================
-- 5. INSERCIÓN DE TRANSACCIONES
-- =============================================================================
INSERT INTO transacciones (id, monto, tipo, estado, cuenta_id, fecha_hora, fecha_creacion, fecha_modificacion) VALUES
                                                                                                                   (1, 50000.00, 'DEPOSITO', 'COMPLETADA', 1, NOW(), NOW(), NOW()),
                                                                                                                   (2, 12000.50, 'EXTRACCION', 'COMPLETADA', 1, NOW(), NOW(), NOW()),
                                                                                                                   (3, 100000.00, 'TRANSFERENCIA_RECIBIDA', 'COMPLETADA', 2, NOW(), NOW(), NOW()),
                                                                                                                   (4, 150000.00, 'DEPOSITO', 'COMPLETADA', 3, NOW(), NOW(), NOW()),
                                                                                                                   (5, 5000.00, 'EXTRACCION', 'PENDIENTE', 4, NOW(), NOW(), NOW()),
                                                                                                                   (6, 250000.00, 'TRANSFERENCIA_RECIBIDA', 'COMPLETADA', 5, NOW(), NOW(), NOW()),
                                                                                                                   (7, 2500.00, 'EXTRACCION', 'RECHAZADA', 6, NOW(), NOW(), NOW()),
                                                                                                                   (8, 30000.00, 'DEPOSITO', 'COMPLETADA', 7, NOW(), NOW(), NOW()),
                                                                                                                   (9, 100000.00, 'TRANSFERENCIA_ENVIADA', 'COMPLETADA', 8, NOW(), NOW(), NOW()),
                                                                                                                   (10, 5000.00, 'DEPOSITO', 'COMPLETADA', 9, NOW(), NOW(), NOW()),
                                                                                                                   (11, 45000.00, 'TRANSFERENCIA_RECIBIDA', 'COMPLETADA', 10, NOW(), NOW(), NOW()),
                                                                                                                   (12, 120000.00, 'DEPOSITO', 'COMPLETADA', 11, NOW(), NOW(), NOW());

