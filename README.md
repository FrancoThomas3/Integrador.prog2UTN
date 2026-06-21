# 🍔 Food Store - Sistema de Gestión de Pedidos

Sistema de gestión para un negocio de comidas desarrollado en Java como aplicación de consola. Este proyecto implementa Programación Orientada a Objetos (POO) y persistencia de datos relacional utilizando el patrón de diseño DAO y JDBC con MySQL.

Este proyecto corresponde al Trabajo Práctico Integrador de la materia Programación 2 (Tecnicatura Universitaria en Programación - UTN).

## 🛠️ Tecnologías y Requisitos

* **Lenguaje:** Java 21
* **Base de Datos:** MySQL Server 8.0+
* **Gestor de Dependencias:** Maven
* **Librerías:** MySQL Connector/J (Configurado en `pom.xml`)

## 🚀 Instalación y Ejecución

Para poder ejecutar este proyecto y evaluar la persistencia de datos, seguí estos pasos:

### 1. Configurar la Base de Datos y Probar Datos
El proyecto requiere una base de datos MySQL con las tablas y relaciones ya establecidas.
1. Abrí MySQL Workbench (o tu gestor de base de datos preferido).
2. Abrí el archivo `schema.sql` provisto en la raíz de este repositorio.
3. Ejecutá el script completo. Esto creará la base de datos `pedidos_db`, todas las tablas necesarias y cargará algunos datos de prueba iniciales.
4. **Verificación de Datos:** Al final del archivo `schema.sql` se incluyeron varias consultas `SELECT` (incluyendo un reporte con `JOIN` del ticket completo). Podés ejecutarlas de a una para comprobar cómo se guarda e impacta la información desde la consola de Java.

### 2. Configurar la Conexión JDBC
Es necesario enlazar el proyecto Java con tu servidor MySQL local.
1. En tu IDE, navegá hasta el paquete `integrador.prog2.config`.
2. Abrí el archivo `ConexionDB.java`.
3. Modificá las variables `USER` y `PASSWORD` con las credenciales de tu servidor MySQL local.

### 3. Compilar y Ejecutar
1. Abrí el proyecto en Apache NetBeans (o IDE compatible).
2. Hacé clic en **"Clean and Build"** (Limpiar y Construir). Esto permitirá que Maven descargue automáticamente el driver de MySQL especificado en el archivo `pom.xml`.
3. Ejecutá la clase principal `Main.java` ubicada en el paquete `integrador.prog2`.

## 🏛️ Arquitectura del Proyecto

El código está estructurado en capas para separar responsabilidades y facilitar su mantenimiento:

* **`config/`**: Contiene la clase `ConexionDB` que gestiona la conexión a MySQL.
* **`entities/`**: Modelo de dominio. Clases que representan las entidades del negocio (heredando de una clase `Base`).
* **`enums/`**: Enumeraciones para Estados, Roles y Formas de Pago.
* **`dao/`**: Data Access Objects. Única capa que interactúa con la base de datos mediante sentencias SQL y maneja transacciones relacionales con *Rollback*.
* **`services/`**: Contiene la lógica de negocio y validaciones antes de enviar los datos al DAO.
* **`exception/`**: Excepciones personalizadas para el manejo de errores (ej: `StockInvalidoException`).
* **`Main`**: Interfaz de usuario por consola mediante menú interactivo.

## 🔗 Enlaces Importantes

A continuación, se encuentran los enlaces a los recursos obligatorios de la entrega:

* **Documentación Técnica (PDF):** [El archivo se encuentra en la raíz del repositorio como `informe-integrador-programacion-2.pdf`"]
* **Video Demostrativo:** [https://drive.google.com/file/d/101Q__uqBz5LSbMPsnbOKDtn69wgremfC/view?usp=sharing]
