/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author diego-d
 */
public class Purchase {

    private ArrayList<Sale> sales;
    private Date date;
    
    public ArrayList<Sale> getSales() {
        return sales;
    }

    public void setSales(ArrayList<Sale> sales) {
        this.sales = sales;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotal() {
        float total = 0;
        for (Sale sale : sales) {
            total += sale.getSubtotal();
        }
        return total;
    }    

    public int getProductsCount() {
        int counter = 0;
        for (Sale sale : sales) {
            counter += sale.getQuantity();
        }
        return counter;
    }

    public void addSale(Sale sale) {
        sales.add(sale);
    }

    public Sale getSale(int index) {
        return sales.get(index);
    }

    public void updateSale(int index, Sale sale) {
        sales.set(index, sale);
    }

    public void deleteSale(int index) {
        sales.remove(index);
    }

    public void create() {
        SQLInterface sql = SQLInterface.getInstance();
        String cols = "date, total";
        String values = "";
        values += "'now'" + ", ";
        values += this.getTotal();
        int saleTableIndex = sql.insert("Purchase", cols, values);
        
        for (Sale sale : sales) {
            String saleCols = "productCode, adquisitionPrice, quantity, subtotal, purchaseId";
            String saleValues = "";
            saleValues += sale.getCode() + ", ";
            saleValues += sale.getAdquisitionPrice() + ", ";
            saleValues += sale.getQuantity() + ", ";
            saleValues += sale.getSubtotal() + ", ";
            saleValues += saleTableIndex;
            sql.insert("Sale", saleCols, saleValues);
        }
    }
}
