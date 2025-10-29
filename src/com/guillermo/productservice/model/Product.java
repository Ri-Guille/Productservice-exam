/**
 * 
 */
package com.guillermo.productservice.model;

/**
 * @author G 29 oct 2025
 */
public class Product {
	private int id;
    private String productCode;
    private int stock;
    private String warehouse;

    /**
     * Constructor para inicializar un nuevo producto.
     */
    public Product(int id, String productCode, int stock, String warehouse) {
        this.id = id;
        this.productCode = productCode;
        this.stock = stock;
        this.warehouse = warehouse;
    }

    // --- Getters ---
    public int getId() { return id; }
    public String getProductCode() { return productCode; }
    public int getStock() { return stock; }
    public String getWarehouse() { return warehouse; }

    // --- Setters ---
    public void setId(int id) { this.id = id; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public void setStock(int stock) { this.stock = stock; }
    public void setWarehouse(String warehouse) { this.warehouse = warehouse; }

    @Override
    public String toString() {
        return "Product{id=" + id + ", code='" + productCode + "', stock=" + stock + '}';
    }
}
