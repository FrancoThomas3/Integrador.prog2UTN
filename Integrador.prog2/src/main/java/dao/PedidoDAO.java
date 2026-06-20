/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Ryzen 7 5700g
 */
import config.ConexionDB;
import integrador.prog2.entities.DetallePedido;
import integrador.prog2.entities.Pedido;
import integrador.prog2.entities.Producto;
import integrador.prog2.entities.Usuario;
import enums.Estado;
import enums.FormaPago;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

  
    public Pedido guardarPedidoCompleto(Pedido pedido, List<Producto> productosModificados) throws SQLException {
        Connection conn = null;

        try {
            conn = ConexionDB.getConexion();
     
            conn.setAutoCommit(false); 

            
            String sqlPedido = "INSERT INTO pedido (fecha, estado, total, formaPago, usuario_id, eliminado, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                psPedido.setDate(1, java.sql.Date.valueOf(pedido.getFecha()));
                psPedido.setString(2, pedido.getEstado().name());
                psPedido.setDouble(3, pedido.getTotal());
                psPedido.setString(4, pedido.getFormaPago().name());
                psPedido.setLong(5, pedido.getUsuario().getId());
                psPedido.setBoolean(6, pedido.isEliminado());
                psPedido.setTimestamp(7, Timestamp.valueOf(pedido.getCreatedAt()));
                
                psPedido.executeUpdate();

               
                try (ResultSet rs = psPedido.getGeneratedKeys()) {
                    if (rs.next()) {
                        pedido.setId(rs.getLong(1));
                    }
                }
            }

            String sqlDetalle = "INSERT INTO detalle_pedido (cantidad, subtotal, pedido_id, producto_id, eliminado, createdAt) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle)) {
                for (DetallePedido dp : pedido.getDetalles()) {
                    psDetalle.setInt(1, dp.getCantidad());
                    psDetalle.setDouble(2, dp.getSubtotal());
                    psDetalle.setLong(3, pedido.getId()); 
                    psDetalle.setLong(4, dp.getProducto().getId());
                    psDetalle.setBoolean(5, dp.isEliminado());
                    psDetalle.setTimestamp(6, Timestamp.valueOf(dp.getCreatedAt()));
                    psDetalle.addBatch();
                }
                psDetalle.executeBatch();
            }

            String sqlStock = "UPDATE producto SET stock = ? WHERE id = ?";
            try (PreparedStatement psStock = conn.prepareStatement(sqlStock)) {
                for (Producto p : productosModificados) {
                    psStock.setInt(1, p.getStock());
                    psStock.setLong(2, p.getId());
                    psStock.addBatch();
                }
                psStock.executeBatch();
            }

            conn.commit();
            return pedido;

        } catch (SQLException e) {
           
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException ex) {
                    System.out.println("Error al intentar hacer rollback: " + ex.getMessage());
                }
            }
            throw new SQLException("Error en la transacción, se canceló el pedido en memoria y BD: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); 
                conn.close();
            }
        }
    }

    public List<Pedido> listarActivos() throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE eliminado = false";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getLong("id"));
                p.setFecha(rs.getDate("fecha").toLocalDate());
                p.setEstado(Estado.valueOf(rs.getString("estado")));
                p.setTotal(rs.getDouble("total"));
                p.setFormaPago(FormaPago.valueOf(rs.getString("formaPago")));
                p.setEliminado(rs.getBoolean("eliminado"));
                p.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                
                Usuario u = usuarioDAO.buscarPorId(rs.getLong("usuario_id"));
                p.setUsuario(u);
                
                lista.add(p);
            }
        }
        return lista;
    }
    
    public Pedido buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE id = ? AND eliminado = false";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Pedido p = new Pedido();
                    p.setId(rs.getLong("id"));
                    p.setFecha(rs.getDate("fecha").toLocalDate());
                    p.setEstado(Estado.valueOf(rs.getString("estado")));
                    p.setTotal(rs.getDouble("total"));
                    p.setFormaPago(FormaPago.valueOf(rs.getString("formaPago")));
                    p.setEliminado(rs.getBoolean("eliminado"));
                    p.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                    
                    Usuario u = usuarioDAO.buscarPorId(rs.getLong("usuario_id"));
                    p.setUsuario(u);
                    
                    return p;
                }
            }
        }
        return null;
    }

    public void actualizar(Pedido pedido) throws SQLException {
     
        String sql = "UPDATE pedido SET estado = ?, formaPago = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pedido.getEstado().name());
            pstmt.setString(2, pedido.getFormaPago().name());
            pstmt.setLong(3, pedido.getId());
            
            pstmt.executeUpdate();
        }
    }

    public void eliminarLogico(Long id) throws SQLException {
        String sql = "UPDATE pedido SET eliminado = true WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }
}
