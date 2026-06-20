/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrador.prog2.entities;

/**
 *
 * @author Ryzen 7 5700g
 */
import enums.Estado;
import enums.FormaPago;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario; 
    private List<DetallePedido> detalles;

    public Pedido() {
        super();
        this.detalles = new ArrayList<>();
        this.fecha = LocalDate.now();
        this.total = 0.0;
    }

    public Pedido(Long id, FormaPago formaPago, Usuario usuario) {
        super(id);
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE; 
        this.total = 0.0;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
    }

  
    public void addDetallePedido(int cantidad, Double precio, Producto producto) {
        
        DetallePedido detalle = new DetallePedido(System.nanoTime(), cantidad, producto);
        this.detalles.add(detalle);
        calcularTotal();
       
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido dp : detalles) {
            if (dp.getProducto().getId().equals(producto.getId()) && !dp.isEliminado()) {
                return dp;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido dp = findDetallePedidoByProducto(producto);
        if (dp != null) {
            dp.setEliminado(true);
            this.detalles.remove(dp);
            calcularTotal();
        }
    }

    @Override
    public void calcularTotal() {
        this.total = 0.0;
        for (DetallePedido dp : detalles) {
            if (!dp.isEliminado()) {
                this.total += dp.getSubtotal();
            }
        }
    }

    // Getters y Setters
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<DetallePedido> getDetalles() { return detalles; }

    @Override
    public String toString() {
        String texto = "ID Pedido: " + getId() + " | Fecha: " + fecha 
                     + "\nUsuario: " + usuario.getNombre() 
                     + " | Estado: " + estado + " | Pago: " + formaPago 
                     + "\nTOTAL: $" + total + "\nDetalles:";
        
        if (detalles.isEmpty()) {
            texto += " (Sin detalles)";
        } else {
            for (DetallePedido dp : detalles) {
                texto += "\n  -> " + dp.toString();
            }
        }
        return texto + "\n-----------------------------------";
    }
}