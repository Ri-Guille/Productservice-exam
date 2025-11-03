/**
 * 
 */
package com.guillermo.productservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.guillermo.productservice.model.Product;
import com.guillermo.productservice.util.DBConnection;

/**
 * @author G 29 oct 2025
 */
public class ProductDAOImpl implements ProductDAO {
    // Usamos el valor entero 1 que equivale a Statement.RETURN_GENERATED_KEYS
    private static final int RETURN_GENERATED_KEYS_FLAG = 1;
    
    @FunctionalInterface
    private interface Setter { void set(PreparedStatement pstmt) throws SQLException; }

    private void exec(String sql, Setter setter, String error) {
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setter.set(pstmt); 
            pstmt.executeUpdate();
        } catch (SQLException e) { 
            System.err.println(error + ": " + e.getMessage()); 
        }
    }

    @Override public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS product (id INT PRIMARY KEY AUTO_INCREMENT, productCode VARCHAR(50) NOT NULL, stock INT NOT NULL, warehouse VARCHAR(100))";
        // Usamos PreparedStatement para DDL
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.execute();
        } catch (SQLException e) { throw e; }
    }
    
    @Override public void dropTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS product";
        // Usamos PreparedStatement para DDL
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) { pstmt.execute(); }
    }

    @Override
    public void addProduct(Product product) {
        String sql = "INSERT INTO product (productCode, stock, warehouse) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             // Reemplazamos Statement.RETURN_GENERATED_KEYS por su valor constante (1)
             PreparedStatement pstmt = conn.prepareStatement(sql, RETURN_GENERATED_KEYS_FLAG)) {
            pstmt.setString(1, product.getProductCode());
            pstmt.setInt(2, product.getStock());
            pstmt.setString(3, product.getWarehouse());
            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) { 
                    if (rs.next()) product.setId(rs.getInt(1)); 
                }
            }
        } catch (SQLException e) { System.err.println("Error al añadir producto: " + e.getMessage()); }
    }

    @Override
    public Product getProductById(int id) {
        String sql = "SELECT id, productCode, stock, warehouse FROM product WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(rs.getInt("id"), rs.getString("productCode"), rs.getInt("stock"), rs.getString("warehouse"));
                }
            }
        } catch (SQLException e) { System.err.println("Error al obtener producto: " + e.getMessage()); }
        return null;
    }

    @Override public void updateProduct(Product product) {
        String sql = "UPDATE product SET productCode = ?, stock = ?, warehouse = ? WHERE id = ?";
        exec(sql, pstmt -> {
            pstmt.setString(1, product.getProductCode());
            pstmt.setInt(2, product.getStock());
            pstmt.setString(3, product.getWarehouse());
            pstmt.setInt(4, product.getId());
        }, "Error al actualizar producto");
    }

    @Override public void deleteProduct(int id) {
        exec("DELETE FROM product WHERE id = ?", pstmt -> pstmt.setInt(1, id), "Error al eliminar producto");
    }

    @Override public void deductStock(int id, int quantity) { 
        // Lógica de deducción no implementada aquí; la simulación usa su propia lógica en ProductServiceTest.
    }
}