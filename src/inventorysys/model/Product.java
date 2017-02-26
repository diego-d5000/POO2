/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.model;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego-d
 */
public class Product {

    private String code;
    private String name;
    private String brand;
    private float providerPrice;
    private float publicPrice;
    private int minStock;
    private int maxStock;
    private int stock;

    public Product() {
    }

    public Product(String code, String name, String brand, float providerPrice, float publicPrice, int minStock, int maxStock, int stock) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.providerPrice = providerPrice;
        this.publicPrice = publicPrice;
        this.minStock = minStock;
        this.maxStock = maxStock;
        this.stock = stock;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getProviderPrice() {
        return providerPrice;
    }

    public void setProviderPrice(float providerPrice) {
        this.providerPrice = providerPrice;
    }

    public float getPublicPrice() {
        return publicPrice;
    }

    public void setPublicPrice(float publicPrice) {
        this.publicPrice = publicPrice;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void create() throws IllegalArgumentException, IllegalAccessException {
        SQLInterface sql = SQLInterface.getInstance();
        String cols = "";
        String values = "";
        Field[] fields = getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            cols += field.getName().toString() + (i == fields.length - 1 ? "" : ",");
            String value = field.get(this).toString();
            value = field.getType().isAssignableFrom(String.class)
                    ? "'" + value + "'" : value;
            values += value + (i == fields.length - 1 ? "" : ",");
        }
        sql.insert("Product", cols, values);
    }

    public void update() throws IllegalArgumentException, IllegalAccessException {
        SQLInterface sql = SQLInterface.getInstance();
        String set = "";
        Field[] fields = getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName().toString();
            String value = field.get(this).toString();
            value = field.getType().isAssignableFrom(String.class)
                    ? "'" + value + "'" : value;
            set += fieldName + " = " + value + (i == fields.length - 1 ? "" : ",");
        }
        sql.update("Product", set, "code = '" + this.code + "'");
    }

    public void delete() {
        SQLInterface sql = SQLInterface.getInstance();
        sql.delete("Product", "code = '" + this.code + "'");
        this.code = null;
    }

    public static ArrayList<Product> findAll() throws SQLException {
        return find(null);
    }

    public static ArrayList<Product> find(String where) throws SQLException {
        SQLInterface sql = SQLInterface.getInstance();
        ArrayList<Product> list = new ArrayList();
        sql.select("*", "Product", where, new SQLInterface.SQLAction() {
            @Override
            public void onResult(ResultSet rs) {
                try {
                    while (rs.next()) {
                        String code = rs.getString("code");
                        String name = rs.getString("name");
                        String brand = rs.getString("brand");
                        float providerPrice = rs.getFloat("providerPrice");
                        float publicPrice = rs.getFloat("publicPrice");
                        int minStock = rs.getInt("minStock");
                        int maxStock = rs.getInt("maxStock");
                        int stock = rs.getInt("stock");

                        Product product = new Product(code, name, brand, providerPrice, publicPrice, minStock, maxStock, stock);
                        list.add(product);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        return list;
    }

    public static Product findByCode(String code) throws SQLException {
        ArrayList<Product> list = find("code = " + code);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public String toXsv(char separation) {
        return "\"" + code + "\"" + separation
                + "\"" + name + "\"" + separation
                + "\"" + brand + "\"" + separation
                + String.valueOf(providerPrice) + separation
                + String.valueOf(publicPrice) + separation
                + String.valueOf(minStock) + separation
                + String.valueOf(maxStock) + separation
                + String.valueOf(stock);
    }
}
