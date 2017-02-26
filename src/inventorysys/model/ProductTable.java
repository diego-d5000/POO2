/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.model;

import java.awt.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author diego-d
 */
public class ProductTable extends AbstractTableModel {

    private String[] colNames = {"Codigo", "Nombre",
        "Marca", "Precio Proveedor",
        "Precio Publico", "Stock Minimo",
        "Stock Maximo", "Existencias"};

    private ArrayList<Product> products;

    public ProductTable(ArrayList<Product> products) {
        this.products = products;
    }

    public void updateProducts(ArrayList<Product> products) {
        this.products = products;
        fireTableDataChanged();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
    

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value;
        Product product = products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = product.getCode();
                break;
            case 1:
                value = product.getName();
                break;
            case 2:
                value = product.getBrand();
                break;
            case 3:
                value = product.getProviderPrice();
                break;
            case 4:
                value = product.getPublicPrice();
                break;
            case 5:
                value = product.getMinStock();
                break;
            case 6:
                value = product.getMaxStock();
                break;
            case 7:
                value = product.getStock();
                break;
            default:
                value = "N/A";
        }
        
        return value;
    }

}
