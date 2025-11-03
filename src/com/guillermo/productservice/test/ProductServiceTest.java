/**
 * 
 */
package com.guillermo.productservice.test;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.guillermo.productservice.dao.ProductDAO;
import com.guillermo.productservice.dao.ProductDAOImpl;
import com.guillermo.productservice.model.Product;

/**
 * @author G 29 oct 2025
 */
public class ProductServiceTest {
    private static final int STOCK_INI = 100;
    private static final int DEDUCT_QTY = 1;
    private static final int THREADS = 100;
    private static final int STOCK_FINAL_ESP = STOCK_INI - (DEDUCT_QTY * THREADS);

    private final ProductDAO dao = new ProductDAOImpl();

    public static void main(String[] args) throws InterruptedException, SQLException {
        ProductServiceTest test = new ProductServiceTest();
        System.out.println("== DEMOSTRACIÓN DE CONCURRENCIA: Inseguro vs. Atómico");
        System.out.printf("Stock Inicial: %d | Total Deducciones: %d | Stock Esperado: %d%n", STOCK_INI, THREADS, STOCK_FINAL_ESP);
        
        test.runUnsafeDeduction();
        test.runSafeDeduction();
    }

    private void runDeduction(Product p, Runnable task, String mode, boolean expectFail) throws InterruptedException, SQLException {
        dao.dropTable();
        dao.createTable();
        dao.addProduct(p);
        int id = p.getId();

        System.out.println("\n--- SIMULACIÓN: " + mode + " ---");
        System.out.println("Inicio con ID " + id + ": " + p.getStock());

        var executor = Executors.newFixedThreadPool(THREADS);
        for (int i = 0; i < THREADS; i++) executor.execute(task);
        
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);

        Product finalP = dao.getProductById(id);
        System.out.println("Stock Final Obtenido: " + finalP.getStock());
        
        if (finalP.getStock() == STOCK_FINAL_ESP) {
            System.out.println(">>> ÉXITO: Consistencia Mantenida (Resultado Atómico).");
        } else if (expectFail) {
            System.err.println("!!! FALLO ESPERADO: Inconsistencia Detectada (Race Condition) !!!");
        } else {
             System.err.println("!!! FALLO INESPERADO: Se perdió stock o fue incorrecto !!!");
        }
    }
    
    private void runUnsafeDeduction() throws InterruptedException, SQLException {
        Product product = new Product(0, "P001", STOCK_INI, "Central");
        runDeduction(product, new UnsafeStockDeductor(product.getId()), "INSEGURO", true);
    }
    
    private void runSafeDeduction() throws InterruptedException, SQLException {
        Product product = new Product(0, "P002", STOCK_INI, "Oeste");
        runDeduction(product, new SafeStockDeductor(product.getId()), "SEGURO (Atómico)", false);
    }

    // Hilo Inseguro: Read-Modify-Write (Vulnerable a Race Condition)
    private class UnsafeStockDeductor implements Runnable {
        private final int id;
        public UnsafeStockDeductor(int id) { this.id = id; }
        
        @Override
        public void run() {
            ProductDAO dao = new ProductDAOImpl();
            Product p = dao.getProductById(id);
            if (p == null) return;
            try { Thread.sleep(1); } catch (InterruptedException ignored) {} // Simula retardo
            p.setStock(p.getStock() - DEDUCT_QTY);
            dao.updateProduct(p);
        }
    }

    // Hilo Seguro: Llama al UPDATE SQL atómico del DAO
    private class SafeStockDeductor implements Runnable {
        private final int id;
        public SafeStockDeductor(int id) { this.id = id; }
        
        @Override
        public void run() {
            new ProductDAOImpl().deductStock(id, DEDUCT_QTY);
        }
    }
}
