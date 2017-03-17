/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import inventorysys.model.Product;
import inventorysys.model.Product.Categories;
import inventorysys.model.Sale;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author diego-d
 */
public class ProductLargeInfoWindow {

    ShopPanel shopPanelParent;
    Product product;
    JFrame frame;

    public ProductLargeInfoWindow(ShopPanel shopPanelParent, Product product) {
        this.shopPanelParent = shopPanelParent;
        this.product = product;
        setupAndShowView();
    }

    private void setupAndShowView() {
        frame = new JFrame(product.getName());
        frame.setSize(200, 500);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ImagePanel imagePanel = new ImagePanel("gansito.jpg", 1, 1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(imagePanel, c);

        JTextArea descriptionText = new JTextArea("Nombre: " + product.getName() + "\n"
                + "Marca: " + product.getBrand() + "\n"
                + "Categoria: " + Categories.NAMES[product.getCategory()]+ "\n"
                + "Precio: " + product.getPublicPrice() + "\n"
                + "Disponibles: " + product.getStock() + "\n"
                + "Codigo: " + product.getCode());
        descriptionText.setEditable(false);

        descriptionText.setLineWrap(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        panel.add(descriptionText, c);

        JButton buyButton = new JButton("Comprar");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanelParent.registerBoughtProduct(new Sale(product, product.getPublicPrice(), 1));
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridy = 2;
        c.gridx = 0;
        c.insets = new Insets(0, 5, 0, 5);
        panel.add(buyButton, c);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        panel.add(closeButton, c);

        frame.add(panel);
        frame.setVisible(true);
    }

}
