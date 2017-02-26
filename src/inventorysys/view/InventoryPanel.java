/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import inventorysys.model.Product;
import inventorysys.model.ProductTable;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author diego-d
 */
public class InventoryPanel extends JPanel {

    private JTable inventoryTable;

    public InventoryPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setupView();
    }

    public void setupView() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Inventario");
        Border border = BorderFactory.createCompoundBorder(titledBorder, new EmptyBorder(10, 8, 8, 5));
        this.setBorder(border);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton newButton = new JButton("Nuevo...");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductWindow productWindow = new ProductWindow(InventoryPanel.this);
                productWindow.setupViewAndShow();
            }
        });
        JButton editButton = new JButton("Editar...");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductWindow productWindow = new ProductWindow(InventoryPanel.this, getSelectedProduct());
                productWindow.setupViewAndShow();
            }
        });
        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = inventoryTable.getSelectedRow();
                Product product = getSelectedProduct();
                String[] options = {"Si", "No"};
                int answer = JOptionPane.showOptionDialog(InventoryPanel.this.getParent(),
                        "El producto '" + product.getName() + "' se borrara por completo. \n"
                        + "¿Deseas continuar?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]);
                if (answer == JOptionPane.YES_OPTION) {
                    product.delete();
                    getTableProductList().remove(selectedRow);

                    fireTableChange();
                }
            }
        });
        buttonsPanel.add(newButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        ArrayList<Product> products = new ArrayList();
        try {
            products = Product.findAll();
        } catch (SQLException ex) {
            Logger.getLogger(InventoryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        inventoryTable = new JTable(new ProductTable(products));
        JScrollPane tableScrollPane = new JScrollPane(inventoryTable);
        inventoryTable.setFillsViewportHeight(true);

        this.add(tableScrollPane);
        this.add(buttonsPanel);
    }

    public void notifyProductWindowResponse(Product product, boolean isEdit) {
        ProductTable tableModel = (ProductTable) inventoryTable.getModel();
        ArrayList<Product> products = tableModel.getProducts();
        if (isEdit) {
            int selectedRow = inventoryTable.getSelectedRow();
            try {
                product.update();
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(InventoryPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(InventoryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            products.set(selectedRow, product);
        } else {
            products.add(product);
        }

        tableModel.fireTableDataChanged();
    }

    public void notifySearchPanelAction(ArrayList<Product> products) {
        ProductTable productTable = (ProductTable) inventoryTable.getModel();
        productTable.updateProducts(products);
    }

    public ArrayList<Product> getTableProductList() {
        ProductTable productTable = (ProductTable) inventoryTable.getModel();
        return productTable.getProducts();
    }

    private Product getSelectedProduct() {
        int selectedRow = inventoryTable.getSelectedRow();
        ArrayList<Product> products = getTableProductList();
        return products.get(selectedRow);
    }

    private void fireTableChange() {
        ProductTable productTable = (ProductTable) inventoryTable.getModel();
        productTable.fireTableDataChanged();
    }
}
