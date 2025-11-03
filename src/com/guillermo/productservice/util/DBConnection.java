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
    
   
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver JDBC (no es estrictamente necesario desde Java 8, pero es buena práctica)
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Manejar la excepción si el driver no está disponible (e.g., falta el JAR en el classpath)
            System.err.println("Error: Driver JDBC de MySQL no encontrado. Asegúrate de tener el JAR en el classpath.");
            throw new SQLException("Driver JDBC de MySQL no encontrado.", e);
        }
    }
}
