/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrador.prog2.services;

/**
 *
 * @author Ryzen 7 5700g
 */

import dao.CategoriaDAO;
import integrador.prog2.entities.Categoria;
import integrador.prog2.exception.EntidadNoEncontradaException;

import java.util.List;

public class CategoriaService {

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public List<Categoria> listarCategorias() {
        try {
            return categoriaDAO.listarActivas();
        } catch (Exception e) {
            System.out.println("Error en BD al listar categorias: " + e.getMessage());
            return List.of();
        }
    }

    public Categoria crearCategoria(String nombre, String descripcion) throws IllegalArgumentException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El nombre de la categoria no puede estar vacio.");
        }

        List<Categoria> activas = listarCategorias();
        for (Categoria cat : activas) {
            if (cat.getNombre().equalsIgnoreCase(nombre.trim())) {
                throw new IllegalArgumentException("Error: Ya existe una categoria activa con el nombre '" + nombre + "'.");
            }
        }

        Categoria nueva = new Categoria();
        nueva.setNombre(nombre.trim());
        nueva.setDescripcion(descripcion);

        try {
            return categoriaDAO.crear(nueva);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al guardar en la BD: " + e.getMessage());
        }
    }

    public void editarCategoria(Long id, String nuevoNombre, String nuevaDescripcion)
            throws EntidadNoEncontradaException, IllegalArgumentException {

        Categoria cat = buscarPorId(id); 

        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El nombre no puede estar vacio.");
        }

        if (!cat.getNombre().equalsIgnoreCase(nuevoNombre.trim())) {
            List<Categoria> activas = listarCategorias();
            for (Categoria c : activas) {
                if (c.getNombre().equalsIgnoreCase(nuevoNombre.trim())) {
                    throw new IllegalArgumentException("Error: Ya existe otra categoria llamada '" + nuevoNombre + "'.");
                }
            }
        }

        cat.setNombre(nuevoNombre.trim());
        cat.setDescripcion(nuevaDescripcion);

        try {
         
            categoriaDAO.actualizar(cat);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar en la BD: " + e.getMessage());
        }
    }

    public void eliminarCategoria(Long id) throws EntidadNoEncontradaException {
        buscarPorId(id); 
        try {
            categoriaDAO.eliminarLogico(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar en BD: " + e.getMessage());
        }
    }

    public Categoria buscarPorId(Long id) throws EntidadNoEncontradaException {
        try {
            Categoria cat = categoriaDAO.buscarPorId(id);
            if (cat == null) {
                throw new EntidadNoEncontradaException("Error: No se encontro la categoria activa con ID " + id);
            }
            return cat;
        } catch (Exception e) {
            throw new EntidadNoEncontradaException("Error de BD al buscar ID: " + e.getMessage());
        }
    }
}
