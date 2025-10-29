/**
 * 
 */
package com.guillermo.productservice.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.guillermo.productservice.model.Product;

/**
 * @author G 29 oct 2025
 */
public interface ProductDAO {

    /**
     * Obtiene un producto por su ID.
     * * @param conn La conexión JDBC activa
     * @param id   El ID del producto a buscar
     * @return El objeto Product, o null si no se encuentra
     * @throws SQLException si ocurre un error de SQL
     */
    Product getProductById(Connection conn, int id) throws SQLException;

    /**
     * Obtiene un producto por su ID, bloqueando la fila para actualización.
     * * @param conn La conexión JDBC activa
     * @param id   El ID del producto a bloquear y buscar
     * @return El objeto Product, o null si no se encuentra
     * @throws SQLException si ocurre un error de SQL
     */
    Product getProductByIdForUpdate(Connection conn, int id) throws SQLException;

    /**
     * Actualiza un producto existente en la base de datos.
     * * @param conn    La conexión JDBC activa
     * @param product El producto con los datos actualizados
     * @throws SQLException si ocurre un error de SQL
     */
    void updateProduct(Connection conn, Product product) throws SQLException;

}

