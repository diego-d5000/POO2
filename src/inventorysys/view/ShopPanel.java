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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 *
 * @author diego-d
 */
public class ShopPanel extends JPanel {

    JTable shoppingCartTable;
    Purchase purchase;
    JLabel countProductsLabel;
    JLabel totalLabel;

    public ShopPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        purchase = new Purchase();
        setupView();
    }

    private void setupView() {
        ArrayList<Sale> sales = new ArrayList<Sale>();
        purchase.setSales(sales);
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
                JOptionPane.showMessageDialog(ShopPanel.this.getParent(), "Total a pagar: " + purchase.getTotal());
                finishAndCommitPurchase();
                eraseAll();
            }
        });
        buttonsPanel.add(buyButton);

        JPanel purchaseInfoPanel = new JPanel(new FlowLayout());
        countProductsLabel = new JLabel("Carrito: 0");
        totalLabel = new JLabel("Total: $0");
        purchaseInfoPanel.add(countProductsLabel);
        purchaseInfoPanel.add(Box.createHorizontalStrut(10));
        purchaseInfoPanel.add(totalLabel);

        this.add(tableScrollPane);
        this.add(purchaseInfoPanel);
        this.add(buttonsPanel);
    }

    public void updateShoppingCartInfo() {
        countProductsLabel.setText("Carrito: " + purchase.getProductsCount());
        totalLabel.setText("Total: $" + purchase.getTotal());
    }

    public void finishAndCommitPurchase() {
        purchase.setDate(new Date());;
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
        updateShoppingCartInfo();
    }

    public void updateProductQuantity(int n) {
        ShoppingCartTable model = (ShoppingCartTable) shoppingCartTable.getModel();
        ArrayList<Sale> modelSales = model.getSales();
        int selectedRow = shoppingCartTable.getSelectedRow();
        modelSales.get(selectedRow).incrementQuantity(n);
        model.updateSales(modelSales);
        updateShoppingCartInfo();
    }

    public void eraseProduct() {
        ShoppingCartTable model = (ShoppingCartTable) shoppingCartTable.getModel();
        ArrayList<Sale> modelSales = model.getSales();
        int selectedRow = shoppingCartTable.getSelectedRow();
        modelSales.remove(selectedRow);
        model.updateSales(modelSales);
        updateShoppingCartInfo();
    }

    public void eraseAll() {
        ShoppingCartTable model = (ShoppingCartTable) shoppingCartTable.getModel();
        ArrayList<Sale> sales = new ArrayList<Sale>();
        model.updateSales(sales);
        updateShoppingCartInfo();
    }

}
