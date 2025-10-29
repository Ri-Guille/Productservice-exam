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
	 private static final String DB_URL = "jdbc:mysql://localhost:3306/exam"; 
	    private static final String USERNAME = "root"; 
	    private static final String PASSWORD = "admin"; 
	    
	    /**
	     * Devuelve una nueva conexión a la base de datos.
	     */
	    public static Connection getConnection() throws SQLException {
	        try {
	            // Carga el driver JDBC
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            throw new SQLException("Driver JDBC no encontrado.", e);
	        }
	        // Retorna la nueva conexión
	        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	    }
}
