/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import inventorysys.model.Product;
import inventorysys.model.ProductTable;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author diego-d
 */
public class ProductWindow {

    JFrame frame;
    private Product product;
    private InventoryPanel panelParent;

    public ProductWindow(InventoryPanel panelParent) {
        this.panelParent = panelParent;
    }

    public ProductWindow(InventoryPanel panelParent, Product product) {
        this.panelParent = panelParent;
        this.product = product;
    }

    public void setupViewAndShow() {
        SpringLayout layout = new SpringLayout();

        frame = new JFrame("Inventario");
        frame.setSize(600, 400);
        frame.setLayout(layout);

        JLabel codeLabel = new JLabel("Codigo:");
        JLabel nameLabel = new JLabel("Nombre:");
        JLabel brandLabel = new JLabel("Marca:");
        JLabel providerPriceLabel = new JLabel("Precio Proveedor:");
        JLabel publicPriceLabel = new JLabel("Precio Publico:");
        JLabel minStockLabel = new JLabel("Stock Minimo:");
        JLabel maxStockLabel = new JLabel("Stock Maximo:");
        JLabel stockLabel = new JLabel("Existencias:");
        JLabel categoryLabel = new JLabel("Categoria:");

        JTextField codeTextField = new JTextField(25);
        JTextField nameTextField = new JTextField(25);
        JTextField brandTextField = new JTextField(25);
        JTextField providerPriceTextField = new JTextField(25);
        JTextField publicPriceTextField = new JTextField(25);
        JTextField minStockTextField = new JTextField(25);
        JTextField maxStockTextField = new JTextField(25);
        JTextField stockTextField = new JTextField(25);
        JComboBox categoryComboBox = new JComboBox(Product.Categories.NAMES);
        
        JLabel codeValueLabel = new JLabel();

        if (product != null) {
            codeValueLabel = new JLabel(product.getCode());
            nameTextField.setText(product.getName());
            brandTextField.setText(product.getBrand());
            providerPriceTextField.setText(String.valueOf(product.getProviderPrice()));
            publicPriceTextField.setText(String.valueOf(product.getPublicPrice()));
            minStockTextField.setText(String.valueOf(product.getMinStock()));
            maxStockTextField.setText(String.valueOf(product.getMaxStock()));
            stockTextField.setText(String.valueOf(product.getStock()));
            categoryComboBox.setSelectedIndex(product.getCategory());
        }

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeTextField.getText();
                String name = nameTextField.getText();
                String brand = brandTextField.getText();
                float providerPrice = Float.valueOf(providerPriceTextField.getText());
                float publicPrice = Float.valueOf(publicPriceTextField.getText());
                int minStock = Integer.valueOf(minStockTextField.getText());
                int maxStock = Integer.valueOf(maxStockTextField.getText());
                int stock = Integer.valueOf(stockTextField.getText());
                int category = categoryComboBox.getSelectedIndex();

                Product nextProduct;
                boolean isEditing = false;

                if (product != null) {
                    product.setName(name);
                    product.setBrand(brand);
                    product.setProviderPrice(providerPrice);
                    product.setPublicPrice(publicPrice);
                    product.setMinStock(minStock);
                    product.setMaxStock(maxStock);
                    product.setStock(stock);
                    product.setCategory(category);
                    nextProduct = product;
                    isEditing = true;
                } else {
                    nextProduct = new Product(code,
                            name,
                            brand,
                            providerPrice,
                            publicPrice,
                            minStock,
                            maxStock,
                            stock,
                            category);
                    try {
                        nextProduct.create();
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(ProductWindow.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(ProductWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                panelParent.notifyProductWindowResponse(nextProduct, isEditing);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

            }
        });
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        frame.add(codeLabel);
        codeLabel.setLabelFor(codeTextField);
        if(product != null){
            frame.add(codeValueLabel);
        } else {
            frame.add(codeTextField);
        }
        
        frame.add(nameLabel);
        nameLabel.setLabelFor(nameTextField);
        frame.add(nameTextField);
        frame.add(brandLabel);
        brandLabel.setLabelFor(brandTextField);
        frame.add(brandTextField);
        frame.add(providerPriceLabel);
        providerPriceLabel.setLabelFor(providerPriceTextField);
        frame.add(providerPriceTextField);
        frame.add(publicPriceLabel);
        publicPriceLabel.setLabelFor(publicPriceTextField);
        frame.add(publicPriceTextField);
        frame.add(minStockLabel);
        minStockLabel.setLabelFor(minStockTextField);
        frame.add(minStockTextField);
        frame.add(maxStockLabel);
        maxStockLabel.setLabelFor(maxStockTextField);
        frame.add(maxStockTextField);
        frame.add(stockLabel);
        stockLabel.setLabelFor(stockTextField);
        frame.add(stockTextField);
        frame.add(categoryLabel);
        stockLabel.setLabelFor(categoryComboBox);
        frame.add(categoryComboBox);

        frame.add(new JPanel());
        frame.add(buttonPanel);

        SpringUtilities.makeCompactGrid(frame.getContentPane(), 10, 2, 12, 12, 20, 20);

        frame.setVisible(true);
    }
}
