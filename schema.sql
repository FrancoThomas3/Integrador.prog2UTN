CREATE DATABASE IF NOT EXISTS pedidos_db;
USE pedidos_db;

-- 2. Tabla Categoria
CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    eliminado BOOLEAN DEFAULT FALSE,
    createdAt DATETIME NOT NULL
);

-- 3. Tabla Producto
CREATE TABLE producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DOUBLE NOT NULL,
    descripcion VARCHAR(255),
    stock INT NOT NULL,
    imagen VARCHAR(255),
    disponible BOOLEAN DEFAULT TRUE,
    categoria_id BIGINT,
    eliminado BOOLEAN DEFAULT FALSE,
    createdAt DATETIME NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

-- 4. Tabla Usuario
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    mail VARCHAR(150) NOT NULL UNIQUE,
    celular VARCHAR(20),
    contraseña VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'USUARIO') NOT NULL,
    eliminado BOOLEAN DEFAULT FALSE,
    createdAt DATETIME NOT NULL
);

-- 5. Tabla Pedido
CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    estado ENUM('PENDIENTE', 'CONFIRMADO', 'TERMINADO', 'CANCELADO') NOT NULL,
    total DOUBLE NOT NULL,
    formaPago ENUM('TARJETA', 'TRANSFERENCIA', 'EFECTIVO') NOT NULL,
    usuario_id BIGINT NOT NULL,
    eliminado BOOLEAN DEFAULT FALSE,
    createdAt DATETIME NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- 6. Tabla DetallePedido 
CREATE TABLE detalle_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    eliminado BOOLEAN DEFAULT FALSE,
    createdAt DATETIME NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

-- =====================================================================
-- REPORTES GERENCIALES Y CONSULTAS DE PRUEBA
-- =====================================================================

-- 1. REPORTE DE USUARIOS (Con estado y nombre completo)
SELECT 
    id AS 'ID',
    CONCAT(nombre, ' ', apellido) AS 'Cliente',
    mail AS 'Correo Electrónico',
    celular AS 'Teléfono',
    rol AS 'Rol Asignado',
    CASE WHEN eliminado = 0 THEN 'Activo' ELSE 'Baja Lógica' END AS 'Estado'
FROM usuario;


-- 2. REPORTE DE CATEGORÍAS
SELECT 
    id AS 'ID',
    nombre AS 'Categoría',
    descripcion AS 'Descripción',
    CASE WHEN eliminado = 0 THEN 'Activa' ELSE 'Baja Lógica' END AS 'Estado'
FROM categoria;


-- 3. REPORTE DE PRODUCTOS (Cruzado con su Categoría)
SELECT 
    p.id AS 'ID',
    p.nombre AS 'Producto',
    c.nombre AS 'Categoría',
    CONCAT('$', p.precio) AS 'Precio Unitario',
    p.stock AS 'Unidades en Stock',
    CASE WHEN p.disponible = 1 THEN 'Sí' ELSE 'No' END AS 'Disponible para Venta',
    CASE WHEN p.eliminado = 0 THEN 'Activo' ELSE 'Baja Lógica' END AS 'Estado'
FROM producto p
JOIN categoria c ON p.categoria_id = c.id;


-- 4. REPORTE DE PEDIDOS (Resumen General)
SELECT 
    p.id AS 'Nro Pedido',
    p.fecha AS 'Fecha de Compra',
    CONCAT(u.nombre, ' ', u.apellido) AS 'Cliente',
    p.formaPago AS 'Forma de Pago',
    p.estado AS 'Estado Actual',
    CONCAT('$', p.total) AS 'Importe Total'
FROM pedido p
JOIN usuario u ON p.usuario_id = u.id;


-- 5. REPORTE DETALLADO: "EL TICKET COMPLETO" (Ideal para el dueño del local)
-- Muestra el detalle renglón por renglón con los productos y cantidades
SELECT 
    p.id AS 'Nro Pedido',
    CONCAT(u.nombre, ' ', u.apellido) AS 'Cliente',
    prod.nombre AS 'Producto Comprado',
    dp.cantidad AS 'Cantidad',
    CONCAT('$', dp.subtotal) AS 'Subtotal Renglón',
    CONCAT('$', p.total) AS 'Total del Pedido',
    p.estado AS 'Estado'
FROM pedido p
JOIN detalle_pedido dp ON p.id = dp.pedido_id
JOIN producto prod ON dp.producto_id = prod.id
JOIN usuario u ON p.usuario_id = u.id
ORDER BY p.id;