/**
 * 
 */
package com.guillermo.productservice.dao;

import java.sql.SQLException;

import com.guillermo.productservice.model.Product;

/**
 * @author G 29 oct 2025
 */
public interface ProductDAO {
    
    /**
     * Inserta un nuevo producto en la base de datos.
     * @param product El producto a agregar.
     */
    void addProduct(Product product);
    
    /**
     * Obtiene un producto por su ID.
     * @param id ID del producto.
     * @return El objeto Product encontrado o null.
     */
    Product getProductById(int id);
    
    /**
     * Actualiza todos los campos de un producto existente.
     * @param product El producto con los datos actualizados.
     */
    void updateProduct(Product product);
    
    /**
     * Elimina un producto por su ID.
     * @param id ID del producto a eliminar.
     */
    void deleteProduct(int id);
    
    /**
     * Deduce una cantidad del stock de un producto.
     * La implementación de este método será la base para la simulación de concurrencia.
     *
     * @param id ID del producto.
     * @param quantity Cantidad a deducir.
     */
    void deductStock(int id, int quantity);
    
    /**
     * Método de utilidad para crear la tabla de productos si no existe.
     * @throws SQLException Si ocurre un error de SQL.
     */
    void createTable() throws SQLException;
    
    /**
     * Método de utilidad para eliminar la tabla de productos.
     * @throws SQLException Si ocurre un error de SQL.
     */
    void dropTable() throws SQLException;
}