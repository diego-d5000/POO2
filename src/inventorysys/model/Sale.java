/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.model;

/**
 *
 * @author diego-d
 */
public class Sale {
    private Product product;
    private float adquisitionPrice;
    private int quantity;

    public Sale() {
    }

    public Sale(Product product, float price, int quantity) {
        this.product = product;
        this.adquisitionPrice = price;
        this.quantity = quantity;
    }

    public String getCode() {
        return product.getCode();
    }

    public String getName() {
        return product.getName();
    }

    public float getAdquisitionPrice() {
        return adquisitionPrice;
    }

    public void setAdquisitionPrice(float adquisitionPrice) {
        this.adquisitionPrice = adquisitionPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void incrementQuantity(int n){
        this.quantity += n;
    }
    
    public float getSubtotal() {
        return quantity * adquisitionPrice;
    }
}
