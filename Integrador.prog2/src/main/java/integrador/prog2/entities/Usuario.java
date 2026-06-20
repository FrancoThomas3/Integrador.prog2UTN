/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrador.prog2.entities;

/**
 *
 * @author Ryzen 7 5700g
 */
import enums.Rol;
import integrador.prog2.exception.MailDuplicadoExcepcion;

public class Usuario extends Base{
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contraseña;
    private Rol rol;
    private Pedido pedido;

    public Usuario() {
        super();
    }

    public Usuario(String nombre, String apellido, String mail, String celular, String contraseña, Rol rol, Long id) {
        super(id);
        this.nombre = nombre;
        this.apellido = apellido;
        setMail(mail);
        this.celular = celular;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR: El nombre no puede ser nulo ni estar vacio");
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("ERROR: El apellido no puede ser nulo ni estar vacio");
        }
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if (mail == null || mail.isBlank()) {
            throw new IllegalArgumentException("ERROR: El mail no puede estar vacio.");
        }
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        if (celular==null || celular.isBlank()) {
            throw new IllegalArgumentException("ERROR: El celular no puede estar vacio.");
        } else if (!celular.matches("\\d+")){
            throw new IllegalArgumentException("ERROR: El celular solo puede tener numeros.");
        }
        this.celular = celular;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        if (contraseña==null || contraseña.isBlank()) {
            throw new IllegalArgumentException("ERROR: La contraseña no puede estar vacia.");
        } else if (contraseña.length()<8){
            throw new IllegalArgumentException("ERROR: La contraseña debe tener al menos 8 digitos.");
        }
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", apellido=" + apellido + ", mail=" + mail + ", celular=" + celular + ", contrase\u00f1a=" + contraseña + ", rol=" + rol + ", pedido=" + pedido + '}';
    }
}
