/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ryzen 7 5700g
 */
public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/pedidos_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = "Celestina2907863.";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}