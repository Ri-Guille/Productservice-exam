/**
 * 
 */
package com.guillermo.productservice.dao;

/**
 * @author G 29 oct 2025
 */
public class ProductDAO {
	private int id;
    private String name;
    private int stock;

    public ProductDAO(int id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}

