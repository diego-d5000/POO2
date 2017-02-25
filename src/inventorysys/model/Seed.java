/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego-d
 */
public class Seed {

    // constante con el nombre de archivo para sqlite
    public static void createTable() throws IllegalArgumentException, IllegalAccessException {
        SQLInterface sql = SQLInterface.getInstance();
        sql.createTable("Product", "code TEXT PRIMARY KEY NOT NULL, "
                + "name TEXT, "
                + "brand TEXT, "
                + "providerPrice REAL, "
                + "publicPrice REAL, "
                + "minStock INT, "
                + "maxStock INT, "
                + "stock INT");
        
        try {
            ArrayList<Product> products = Product.find("brand = 'Seeder SA'");
            if(products.size() <= 0){
                Product product = new Product("3165625360", "Helado de fresa", "Seeder SA", 8.0f, 10.0f, 3, 10, 5);
                product.create();
                product = new Product("3265487895", "Agua 1L", "Seeder SA", 8.50f, 10.0f, 3, 10, 5);
                product.create();
                product = new Product("3265441185", "Atun", "Seeder SA", 9.50f, 13.50f, 3, 10, 5);
                product.create();
                product = new Product("3265983219", "Chocolate Oscuro", "Seeder SA", 8.50f, 10.0f, 3, 10, 5);
                product.create();
                product = new Product("3265983220", "Chocolate Blanco", "Seeder SA", 9.50f, 11.0f, 3, 10, 5);
                product.create();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}