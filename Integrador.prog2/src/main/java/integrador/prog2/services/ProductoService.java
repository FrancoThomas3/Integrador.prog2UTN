/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrador.prog2.services;

/**
 *
 * @author nahue
 */
import dao.ProductoDAO;
import integrador.prog2.entities.Categoria;
import integrador.prog2.entities.Producto;
import integrador.prog2.exception.EntidadNoEncontradaException;
import integrador.prog2.exception.StockInvalidoException;

import java.util.List;

public class ProductoService {

    private final ProductoDAO productoDAO = new ProductoDAO();

    public List<Producto> listarProductos() {
        try {
            return productoDAO.listarActivos();
        } catch (Exception e) {
            System.out.println("Error al obtener productos de la BD: " + e.getMessage());
            return List.of();
        }
    }

    public Producto crearProducto(String nombre, Double precio, String descripcion, int stock, String imagen, boolean disponible, Categoria categoria) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El nombre del producto no puede estar vacio.");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("Error: El precio no puede ser negativo.");
        }
        if (stock < 0) {
            throw new StockInvalidoException("Error: El stock no puede ser negativo.");
        }

        Producto nuevo = new Producto();
        nuevo.setNombre(nombre);
        nuevo.setPrecio(precio);
        nuevo.setDescripcion(descripcion);
        nuevo.setStock(stock);
        nuevo.setImagen(imagen);
        nuevo.setDisponible(disponible);
        nuevo.setCategoria(categoria);

        return productoDAO.crear(nuevo);
    }
    
    public void editarProducto(Long id, String nombre, Double precio, String descripcion, int stock, String imagen, boolean disponible, Categoria categoria) throws Exception {
        Producto producto = buscarPorId(id);

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El nombre no puede estar vacio.");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("Error: El precio no puede ser negativo.");
        }
        if (stock < 0) {
            throw new StockInvalidoException("Error: El stock no puede ser negativo.");
        }

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        producto.setStock(stock);
        producto.setImagen(imagen);
        producto.setDisponible(disponible);
        producto.setCategoria(categoria);
        
        productoDAO.actualizar(producto);
    }

    public void eliminarProducto(Long id) throws Exception {
        buscarPorId(id); // Validamos que exista
        productoDAO.eliminarLogico(id);
    }

    public Producto buscarPorId(Long id) throws EntidadNoEncontradaException {
        try {
            Producto p = productoDAO.buscarPorId(id);
            if (p == null) {
                throw new EntidadNoEncontradaException("Error: No existe un producto activo con ID " + id);
            }
            return p;
        } catch (Exception e) {
            throw new EntidadNoEncontradaException("Error en BD al buscar producto: " + e.getMessage());
        }
    }
}