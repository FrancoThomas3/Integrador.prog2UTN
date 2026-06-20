/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrador.prog2.services;
import dao.UsuarioDAO;
import integrador.prog2.entities.Usuario;
import enums.Rol;

import java.util.List;

/**
 *
 * @author Ryzen 7 5700g
 */

public class UsuarioService {
    
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    public void listarUsuarios(){
        try {
            List<Usuario> usuariosBD = usuarioDAO.listarActivos();
            if (usuariosBD.isEmpty()) {
                System.out.println("No hay usuarios para listar.");
            } else {
                for (Usuario usuario : usuariosBD) {
                    System.out.println(
                            "ID: " + usuario.getId() +
                            " | Nombre: " + usuario.getNombre() + 
                            " | Apellido: " + usuario.getApellido() +
                            " | Mail: " + usuario.getMail()
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar usuarios de la BD: " + e.getMessage());
        }
    }
    
    public Usuario crearUsuario(String nombre, String apellido, String mail, String celular, String contraseña) throws Exception {
 
        if (usuarioDAO.buscarPorMail(mail) != null) {
            throw new IllegalArgumentException("ERROR: Ya existe un usuario con el mail " + mail);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setCelular(celular);
        usuario.setContraseña(contraseña); 
        usuario.setRol(Rol.USUARIO);   
        
        Usuario creado = usuarioDAO.crear(usuario);
        System.out.println("Usuario creado con ID: " + creado.getId());
        return creado;
    }
    
    public void editarUsuario(Long id, String nombre, String apellido, String mail, String celular) throws Exception {
        
        Usuario usuario = findUsuarioById(id);
        
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: El usuario no fue encontrado o esta eliminado.");
        }
        
      
        if (usuario.getMail() == null || !usuario.getMail().equalsIgnoreCase(mail)) {
          
            if (usuarioDAO.buscarPorMail(mail) != null) {
                throw new IllegalArgumentException("ERROR: El mail " + mail + " ya está en uso.");
            }
        }

     
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setCelular(celular);
        
       
        usuarioDAO.actualizar(usuario);
        System.out.println("Usuario actualizado correctamente");
    }
    
    public void eliminarUsuario(Long id) throws Exception {
        Usuario usuario = findUsuarioById(id);
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: El usuario no fue encontrado o ya fue eliminado.");
        }
        usuarioDAO.eliminarLogico(id);
        System.out.println("Usuario eliminado correctamente.");
    }
    
    public Usuario findUsuarioById(Long id) throws Exception {
        return usuarioDAO.buscarPorId(id);
    }
}