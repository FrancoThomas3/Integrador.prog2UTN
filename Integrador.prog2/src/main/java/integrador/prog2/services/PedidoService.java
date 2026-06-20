/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrador.prog2.services;

/**
 *
 * @author Ryzen 7 5700g
 */
import dao.PedidoDAO;
import integrador.prog2.entities.Pedido;
import integrador.prog2.entities.Producto;
import integrador.prog2.entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import integrador.prog2.exception.EntidadNoEncontradaException;
import integrador.prog2.exception.StockInvalidoException;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private final PedidoDAO pedidoDAO = new PedidoDAO();

    public List<Pedido> listarPedidos() {
        try {
            return pedidoDAO.listarActivos(); 
        } catch (Exception e) {
            System.out.println("Error al listar pedidos en BD: " + e.getMessage());
            return List.of();
        }
    }

    public Pedido crearPedido(Usuario usuario, FormaPago formaPago, List<Producto> productosSeleccionados, List<Integer> cantidadesSeleccionadas) throws Exception {
        
        if (usuario == null || usuario.isEliminado()) {
            throw new IllegalArgumentException("Error: No se permite crear un pedido sin un usuario válido.");
        }

        if (productosSeleccionados.isEmpty() || cantidadesSeleccionadas.isEmpty()) {
            throw new IllegalArgumentException("Error: El pedido debe tener al menos un producto.");
        }

        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setUsuario(usuario);
        nuevoPedido.setFormaPago(formaPago);
        nuevoPedido.setEstado(Estado.PENDIENTE);
        
        List<Producto> productosModificados = new ArrayList<>();

        for (int i = 0; i < productosSeleccionados.size(); i++) {
            Producto prod = productosSeleccionados.get(i);
            int cant = cantidadesSeleccionadas.get(i);

            if (cant <= 0) {
                throw new IllegalArgumentException("Error: La cantidad debe ser mayor a 0.");
            }
            if (prod.getStock() < cant) {
                throw new StockInvalidoException("Error crítico: Falta de stock para '" + prod.getNombre() + "'.");
            }

            prod.setStock(prod.getStock() - cant);
            productosModificados.add(prod);
            nuevoPedido.addDetallePedido(cant, prod.getPrecio(), prod);
        }

    
        pedidoDAO.guardarPedidoCompleto(nuevoPedido, productosModificados);
        
        return nuevoPedido;
    }

    public void actualizarEstadoYPago(Long id, Estado nuevoEstado, FormaPago nuevaFormaPago) throws Exception {
               Pedido pedido = pedidoDAO.buscarPorId(id); 
        if (pedido == null) throw new EntidadNoEncontradaException("Pedido no encontrado");
        
        if (nuevoEstado != null) pedido.setEstado(nuevoEstado);
        if (nuevaFormaPago != null) pedido.setFormaPago(nuevaFormaPago);
        
        pedidoDAO.actualizar(pedido);
    }

    public void eliminarPedido(Long id) throws Exception {
        pedidoDAO.eliminarLogico(id);
    }
}