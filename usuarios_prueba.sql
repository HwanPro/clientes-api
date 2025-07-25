-- Script para crear usuarios de prueba
-- Ejecutar después de que la aplicación haya creado las tablas

-- Usuario Gratuito
INSERT INTO usuarios (email, password, nombres, apellidos, plan, busquedas_restantes, fecha_registro, activo) 
VALUES (
    'usuario@test.com', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', -- password: 123456
    'Usuario', 
    'Gratuito', 
    'GRATUITO', 
    5, 
    NOW(), 
    true
);

-- Usuario Premium para pruebas
INSERT INTO usuarios (email, password, nombres, apellidos, plan, busquedas_restantes, fecha_registro, activo) 
VALUES (
    'premium@test.com', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', -- password: 123456
    'Usuario Premium', 
    'Prueba', 
    'PREMIUM', 
    500, 
    NOW(), 
    true
);

-- Usuario Administrador (Ilimitado)
INSERT INTO usuarios (email, password, nombres, apellidos, plan, busquedas_restantes, fecha_registro, activo) 
VALUES (
    'admin@test.com', 
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', -- password: 123456
    'Administrador', 
    'Sistema', 
    'ILIMITADO', 
    999999, 
    NOW(), 
    true
);