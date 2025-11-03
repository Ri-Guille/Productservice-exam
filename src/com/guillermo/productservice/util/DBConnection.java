/**
 * 
 */
package com.guillermo.productservice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author G 29 oct 2025
 */
public class DBConnection {

    // --- CONFIGURACIÓN DE BASE DE DATOS ---
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/exam";
    private static final String USER = "root"; 
    private static final String PASSWORD = "admin"; 
    
   
    static {
        try {
            // Cargar el driver de MySQL
            // El nombre de la clase del driver es diferente
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se pudo encontrar el driver de MySQL", e);
        }
    }

    /**
     * @return una conexión JDBC
     * @throws SQLException si hay un error al conectar
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}
