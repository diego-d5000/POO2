/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author diego-d
 */
public class ShoppingCartTable extends AbstractTableModel {

    private String[] colNames = {"Codigo", "Nombre", "Precio", "Cantidad", "Subtotal"};
    private ArrayList<Sale> sales;

    public ShoppingCartTable(ArrayList<Sale> sales) {
        this.sales = sales;
    }

    public void updateSales(ArrayList<Sale> sales) {
        this.sales = sales;
        fireTableDataChanged();
    }

    public ArrayList<Sale> getSales() {
        return sales;
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
        return sales.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value;
        Sale sale = sales.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = sale.getCode();
                break;
            case 1:
                value = sale.getName();
                break;
            case 2:
                value = sale.getPrice();
                break;
            case 3:
                value = sale.getQuantity();
                break;
            case 4:
                value = sale.getSubtotal();
                break;
            default:
                value = "N/A";
        }
        
        return value;
    }

}