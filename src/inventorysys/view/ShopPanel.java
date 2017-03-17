/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import inventorysys.model.ProductTable;
import inventorysys.model.Purchase;
import inventorysys.model.Sale;
import inventorysys.model.ShoppingCartTable;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author diego-d
 */
public class ShopPanel extends JPanel {

    JTable shoppingCartTable;

    public ShopPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setupView();
    }

    private void setupView() {
        ArrayList<Sale> sales = new ArrayList<Sale>();
        shoppingCartTable = new JTable(new ShoppingCartTable(sales));
        JScrollPane tableScrollPane = new JScrollPane(shoppingCartTable);
        shoppingCartTable.setFillsViewportHeight(true);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton incrementButton = new JButton("+");
        incrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductQuantity(1);
            }
        });
        buttonsPanel.add(incrementButton);
        JButton decrementincrementButton = new JButton("-");
        decrementincrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductQuantity(-1);
            }
        });
        buttonsPanel.add(decrementincrementButton);
        JButton eraseButton = new JButton("Borrar");
        eraseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eraseProduct();
            }
        });
        buttonsPanel.add(eraseButton);
        JButton buyButton = new JButton("COMPRAR");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ShopPanel.this.getParent(), "Total a pagar: " + getTotal());
                finishAndCommitPurchase();
                eraseAll();
            }
        });
        buttonsPanel.add(buyButton);

        this.add(tableScrollPane);
        this.add(buttonsPanel);
    }

    public void finishAndCommitPurchase() {
        ShoppingCartTable model = (ShoppingCartTable) shoppingCartTable.getModel();
        ArrayList<Sale> modelSales = model.getSales();

        Purchase purchase = new Purchase();
        purchase.setDate(new Date());
        purchase.setSales(modelSales);
        purchase.create();
    }

    public void registerBoughtProduct(Sale sale) {
        int inCartIndex = -1;
        ShoppingCartTable model = (ShoppingCartTable) shoppingCartTable.getModel();
        ArrayList<Sale> modelSales = model.getSales();

        for (int i = 0; i < modelSales.size(); i++) {
            if (modelSales.get(i).getCode().equals(sale.getCode())) {
                inCartIndex = i;
                break;
            }
        }
        if (inCartIndex >= 0) {
            modelSales.get(inCartIndex).incrementQuantity(1);
        } else {
            modelSales.add(sale);
        }

        model.updateSales(modelSales);
    }

    public void updateProductQuantity(int n) {
        ShoppingCartTable model = (ShoppingCartTable) shoppingCartTable.getModel();
        ArrayList<Sale> modelSales = model.getSales();
        int selectedRow = shoppingCartTable.getSelectedRow();
        modelSales.get(selectedRow).incrementQuantity(n);
        model.updateSales(modelSales);
    }

    public void eraseProduct() {
        ShoppingCartTable model = (ShoppingCartTable) shoppingCartTable.getModel();
        ArrayList<Sale> modelSales = model.getSales();
        int selectedRow = shoppingCartTable.getSelectedRow();
        modelSales.remove(selectedRow);
        model.updateSales(modelSales);
    }

    public void eraseAll() {
        ShoppingCartTable model = (ShoppingCartTable) shoppingCartTable.getModel();
        ArrayList<Sale> sales = new ArrayList<Sale>();
        model.updateSales(sales);
    }

    public float getTotal() {
        float total = 0;
        ShoppingCartTable model = (ShoppingCartTable) shoppingCartTable.getModel();
        ArrayList<Sale> modelSales = model.getSales();

        for (Sale sale : modelSales) {
            total += sale.getSubtotal();
        }

        return total;
    }
}
