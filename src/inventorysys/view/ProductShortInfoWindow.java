/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import inventorysys.model.Sale;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author diego-d
 */
public class ProductShortInfoWindow {

    ShopPanel shopPanelParent;
    JFrame frame;

    public ProductShortInfoWindow(ShopPanel shopPanelParent) {
        this.shopPanelParent = shopPanelParent;
        setupAndShowView();
    }

    private void setupAndShowView() {
        frame = new JFrame();
        frame.setSize(350, 210);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ImagePanel imagePanel = new ImagePanel("gansito.jpg", 1, 1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        panel.add(imagePanel, c);
        
        JLabel priceLabel = new JLabel("Precio: $180");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 2;
        c.insets = new Insets(0, 10, 0, 10);
        panel.add(priceLabel, c);
        
        JButton buyButton = new JButton("Comprar");
         buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanelParent.registerBoughtProduct(new Sale("0", "Gansito", 180, 1));
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
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
        c.gridx = 1;
        panel.add(closeButton, c);
        
        JButton moreInfoButton = new JButton("Mas información");
        moreInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductLargeInfoWindow productLargeInfoWindow = new ProductLargeInfoWindow(shopPanelParent);
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        panel.add(moreInfoButton, c);
        
        frame.add(panel);
        frame.setVisible(true);
    }

}
